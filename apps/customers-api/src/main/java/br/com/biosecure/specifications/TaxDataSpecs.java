package br.com.biosecure.specifications;

import br.com.biosecure.model.Customer;
import br.com.biosecure.model.TaxData;
import br.com.biosecure.model.TaxData_;
import br.com.biosecure.queryfilters.TaxDataQueryFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class providing static methods to construct Jakarta Persistence {@link Predicate}s
 * for the {@link TaxData} value object.
 * <p>
 * <strong>Design Principle:</strong> By leveraging Jakarta Predicates, the API enables
 * dynamic and type-safe query generation. This allows the root {@link Specification}
 * to combine multiple filtering criteria, significantly improving data retrieval flexibility.
 * <p>
 * <strong>Architectural Constraint:</strong> Because {@code TaxData} operates as a Value Object
 * whose lifecycle is strictly bound to the {@link Customer} aggregate root, it is not an
 * independent JPA entity. Consequently, it relies on delegating {@code Predicate} generation
 * for database joins rather than supporting standalone {@code Specification} instances.
 *
 * @see TaxData
 * @see Customer
 * @see CustomerSpecs
 */
public class TaxDataSpecs {

    /**
     * Builds a list of Jakarta Persistence {@link Predicate}s based on the provided
     * {@link TaxDataQueryFilter}.
     *
     * @param path the navigational {@link Path} representing the {@code Address} value
     * object within the JPA entity graph
     * @param cb the {@link CriteriaBuilder} factory used to construct the query expressions
     * @param filter the data transfer object containing the criteria to be applied
     * during predicate creation
     * @return a list of constructed {@code Predicate}s matching the filter criteria
     */
    public static List<Predicate> buildPredicates(Path<TaxData> path, CriteriaBuilder cb, TaxDataQueryFilter filter) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.conjunction());

        if (filter == null) {
            return predicates;
        }

        if (filter.lastSearchDate() != null) {
            predicates.add(
                    cb.equal(path.get(TaxData_.LAST_SEARCH_DATE), filter.lastSearchDate())
            );
        }
        else {
            if (filter.lastSearchDateAfter() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(path.get(TaxData_.LAST_SEARCH_DATE), filter.lastSearchDateAfter())
                );
            }

            if (filter.lastSearchDateBefore() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(path.get(TaxData_.LAST_SEARCH_DATE), filter.lastSearchDateBefore())
                );
            }
        }

        if (filter.activitiesStartDate() != null) {
            predicates.add(
                    cb.equal(path.get(TaxData_.ACTIVITIES_START_DATE), filter.activitiesStartDate())
            );
        }
        else {
            if (filter.activitiesStartDateAfter() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(path.get(TaxData_.ACTIVITIES_START_DATE), filter.activitiesStartDateAfter())
                );
            }

            if (filter.activitiesStartDateBefore() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(path.get(TaxData_.ACTIVITIES_START_DATE), filter.activitiesStartDateBefore())
                );
            }
        }

        if (filter.registrationStatus() != null) {
            predicates.add(
                    cb.equal(path.get(TaxData_.REGISTRATION_STATUS), filter.registrationStatus())
            );
        }

        if (filter.statusDescription() != null) {
            predicates.add(cb.like(
                    cb.lower(path.get(TaxData_.STATUS_DESCRIPTION)),
                    "%" + filter.statusDescription().toLowerCase() + "%")
            );
        }

        if (filter.cnae() != null) {
            predicates.add(
                    cb.equal(path.get(TaxData_.CNAE), filter.cnae())
            );
        }

        return predicates;
    }
}
