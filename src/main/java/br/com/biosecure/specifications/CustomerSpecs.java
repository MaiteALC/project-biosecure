package br.com.biosecure.specifications;

import br.com.biosecure.model.*;
import br.com.biosecure.model.Customer_;
import br.com.biosecure.queryfilters.*;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A utility class providing static methods to construct Spring Data {@link Specification}s
 * for the {@link Customer} aggregate root.
 * <p>
 * <strong>Design Principle:</strong> By leveraging Spring Specifications, the API enables
 * dynamic and type-safe query generation. This allows clients to combine multiple filtering
 * criteria into a single database request, significantly improving data retrieval flexibility.
 * <p>
 * To handle the complexity of nested objects and database joins, this class orchestrates
 * the logic by delegating specific {@code Specification} builds to specialized classes,
 * such as {@link AddressSpecs}.
 *
 * @see Customer
 * @see AddressSpecs
 * @see TaxDataSpecs
 * @see FinancialDataSpecs
 * @see Specification
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public class CustomerSpecs {

    /**
     * Constructs a dynamic {@link Specification} for the {@link Customer} entity
     * based on the provided {@link CustomerQueryFilter}.
     * <p>
     * It conditionally executes database fetch joins for nested objects (such as
     * {@code Address} or {@code TaxData}) only if they are explicitly requested
     * within the {@code includeParams} set. This strategy optimizes database performance
     * by preventing the N+1 query problem and avoiding unnecessary data retrieval.
     * @param includeParam a {@link Set} of {@link IncludeParam} determining whether
     * a nested resource should be eagerly included or strictly used to filter the query results
     * @param customerFilter the data transfer object containing the filtering criteria
     * to be applied against the entity graph
     * @return a fully constructed {@code Specification} ready for repository execution
     */
    public static Specification<Customer> buildSpecification(Set<IncludeParam> includeParam, CustomerQueryFilter customerFilter) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.conjunction());

            if (customerFilter == null) {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }

            if (customerFilter.corporateName() != null) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get(br.com.biosecure.model.Customer_.CORPORATE_NAME)),
                         "%" + customerFilter.corporateName().toLowerCase() + "%"
                        )
                );
            }

            if (customerFilter.id() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get(Customer_.ID), customerFilter.id())
                );
            }

            if (customerFilter.email() != null) {
                predicates.add(
                    criteriaBuilder.equal(root.get(Customer_.EMAIL), customerFilter.email())
                );
            }

            if (customerFilter.cnpj() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get(Customer_.CNPJ), customerFilter.cnpj())
                );
            }

            if (customerFilter.registrationDate() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get(Customer_.REGISTRATION_DATE), customerFilter.registrationDate())
                );
            }
            else {
                if (customerFilter.registrationDateAfter() != null) {
                    predicates.add(
                            criteriaBuilder.greaterThanOrEqualTo(root.get(Customer_.REGISTRATION_DATE), customerFilter.registrationDateAfter())
                    );
                }

                if (customerFilter.registrationDateBefore() != null) {
                    predicates.add(
                            criteriaBuilder.lessThanOrEqualTo(root.get(Customer_.REGISTRATION_DATE), customerFilter.registrationDateBefore())
                    );
                }
            }

            boolean isCountQuery = Long.class == query.getResultType() || long.class == query.getResultType();

            AddressQueryFilter addressFilter = customerFilter.addressFilter();
            if (addressFilter != null) {
                predicates.addAll(
                        resolveAddressSpecs(isCountQuery, addressFilter, root, criteriaBuilder, includeParam.contains(IncludeParam.ADDRESS))
                );
            }

            FinancialDataQueryFilter fdFilter = customerFilter.financialDataFilter();
            if (fdFilter != null) {
                predicates.addAll(
                        resolveFinancialDataSpecs(isCountQuery, fdFilter, root, criteriaBuilder, includeParam.contains(IncludeParam.FINANCIAL_DATA))
                );
            }

            TaxDataQueryFilter tdFilter = customerFilter.taxDataFilter();
            if (tdFilter != null) {
                predicates.addAll(
                        resolveTaxDataSpecs(isCountQuery, tdFilter, root, criteriaBuilder, includeParam.contains(IncludeParam.TAX_DATA))
                );
            }

            query.distinct(true);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static List<Predicate> resolveAddressSpecs(boolean isCountQuery, AddressQueryFilter filter, Root<Customer> root, CriteriaBuilder cb, boolean requiresFetch) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.conjunction());

        Join<Customer, Address> addressJoin;

        if (isCountQuery || !requiresFetch) {
            addressJoin = root.join(Customer_.ADDRESSES, JoinType.LEFT);
        }
        else {
            Fetch<Customer, Address> addressFetch = root.fetch(Customer_.ADDRESSES, JoinType.LEFT);

            addressJoin = (Join<Customer, Address>) addressFetch;
        }

        predicates.addAll(
                AddressSpecs.buildPredicates(addressJoin, cb, filter)
        );

        return predicates;
    }

    private static List<Predicate> resolveFinancialDataSpecs(boolean isCountQuery, FinancialDataQueryFilter filter, Root<Customer> root, CriteriaBuilder cb, boolean requiresFetch) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.conjunction());

        Join<Customer, FinancialData> financialDataJoin;

        if (isCountQuery || !requiresFetch) {
            financialDataJoin = root.join(Customer_.FINANCIAL_DATA, JoinType.LEFT);
        }
        else {
            Fetch<Customer, FinancialData> financialDataFetch = root.fetch(Customer_.FINANCIAL_DATA, JoinType.LEFT);

            financialDataJoin = (Join<Customer, FinancialData>) financialDataFetch;
        }

        predicates.addAll(
                FinancialDataSpecs.buildPredicates(financialDataJoin, cb, filter)
        );

        return predicates;
    }

    private static List<Predicate> resolveTaxDataSpecs(boolean isCountQuery, TaxDataQueryFilter filter, Root<Customer> root, CriteriaBuilder cb, boolean requiresFetch) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.conjunction());

        Join<Customer, TaxData> taxDataJoin;

        if (isCountQuery || !requiresFetch) {
            taxDataJoin = root.join(Customer_.TAX_DATA, JoinType.LEFT);
        }
        else {
            Fetch<Customer, TaxData> taxDataFetch = root.fetch(Customer_.TAX_DATA, JoinType.LEFT);

            taxDataJoin = (Join<Customer, TaxData>) taxDataFetch;
        }

        predicates.addAll(
                TaxDataSpecs.buildPredicates(taxDataJoin, cb, filter)
        );

        return predicates;
    }
}
