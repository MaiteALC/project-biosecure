package br.com.biosecure.specifications;

import br.com.biosecure.model.FinancialData;
import br.com.biosecure.model.FinancialData_;
import br.com.biosecure.queryfilters.FinancialDataQueryFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class providing static methods to construct Spring Data {@link Specification}s
 * for the {@link FinancialData} entity.
 * <p>
 * <strong>Design Principle:</strong> By leveraging Spring Specifications, the API enables
 * dynamic and type-safe query generation. This allows clients to combine multiple filtering
 * criteria into a single database request, significantly improving data retrieval flexibility.
 *
 * @see FinancialData
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public class FinancialDataSpecs {

    /**
     * Builds a list of Jakarta Persistence {@link Predicate}s based on the provided
     * {@link FinancialDataQueryFilter}.
     *
     * @param path the navigational {@link Path} representing the {@code Address} value
     * object within the JPA entity graph
     * @param cb the {@link CriteriaBuilder} factory used to construct the query expressions
     * @param filter the data transfer object containing the criteria to be applied
     * during predicate creation
     * @return a list of constructed {@code Predicate}s matching the filter criteria
     */
    public static List<Predicate> buildPredicates(Path<FinancialData> path, CriteriaBuilder cb, FinancialDataQueryFilter filter) {

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.conjunction());

        if (filter == null) {
            return predicates;
        }

        if (filter.customerId() != null) {
            predicates.add(
                    cb.equal(path.get(FinancialData_.CUSTOMER_ID), filter.customerId())
            );
        }

        if (filter.shareCapital() != null) {
            predicates.add(
                    cb.equal(path.get(FinancialData_.SHARE_CAPITAL), filter.shareCapital())
            );
        }
        else {
            if (filter.shareCapitalLowerLimit() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(path.get(FinancialData_.SHARE_CAPITAL), filter.shareCapitalLowerLimit())
                );
            }

            if (filter.shareCapitalUpperLimit() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(path.get(FinancialData_.SHARE_CAPITAL), filter.shareCapitalUpperLimit())
                );
            }
        }

        if (filter.totalCredit() != null) {
            predicates.add(
                    cb.equal(path.get(FinancialData_.TOTAL_CREDIT), filter.totalCredit())
            );
        }
        else {
            if (filter.totalCreditLowerLimit() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(path.get(FinancialData_.TOTAL_CREDIT), filter.totalCreditLowerLimit())
                );
            }

            if (filter.totalCreditUpperLimit() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(path.get(FinancialData_.TOTAL_CREDIT), filter.totalCreditUpperLimit())
                );
            }
        }

        if  (filter.utilizedCredit() != null) {
            predicates.add(
                    cb.equal(path.get(FinancialData_.UTILIZED_CREDIT), filter.utilizedCredit())
            );
        }
        else {
            if (filter.utilizedCreditLowerLimit() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(path.get(FinancialData_.UTILIZED_CREDIT), filter.utilizedCreditLowerLimit())
                );
            }

            if (filter.utilizedCreditUpperLimit() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(path.get(FinancialData_.UTILIZED_CREDIT), filter.utilizedCreditUpperLimit())
                );
            }
        }

        return predicates;
    }

    /**
     * Constructs a dynamic {@link Specification} for the {@link FinancialData} entity
     * based on the provided {@link FinancialDataQueryFilter}.
     * <p>
     * This method translates the filtering criteria defined in the data transfer object
     * into a Spring Data {@code Specification}, enabling flexible and type-safe
     * data retrieval.
     *
     * @param filter the data transfer object containing the criteria to be applied
     * against the entity graph
     * @return a fully constructed {@code Specification} ready for repository execution
     */
    public static Specification<FinancialData> buildSpecification(FinancialDataQueryFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = buildPredicates(root, criteriaBuilder, filter);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
