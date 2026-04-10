package br.com.biosecure.specifications;

import br.com.biosecure.model.Address;
import br.com.biosecure.model.Customer;
import br.com.biosecure.queryfilters.AddressQueryFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class providing static methods to construct Jakarta Persistence {@link Predicate}s
 * for the {@link Address} value object.
 * <p>
 * <strong>Design Principle:</strong> By leveraging Jakarta Predicates, the API enables
 * dynamic and type-safe query generation. This allows the root {@link Specification}
 * to combine multiple filtering criteria, significantly improving data retrieval flexibility.
 * <p>
 * <strong>Architectural Constraint:</strong> Because {@code Address} operates as a Value Object
 * whose lifecycle is strictly bound to the {@link Customer} aggregate root, it is not an
 * independent JPA entity. Consequently, it relies on delegating {@code Predicate} generation
 * for database joins rather than supporting standalone {@code Specification} instances.
 *
 * @see Address
 * @see Customer
 * @see CustomerSpecs
 */
public class AddressSpecs {

    /**
     * Builds a list of Jakarta Persistence {@link Predicate}s based on the provided
     * {@link AddressQueryFilter}.
     *
     * @param path the navigational {@link Path} representing the {@code Address} value
     * object within the JPA entity graph
     * @param cb the {@link CriteriaBuilder} factory used to construct the query expressions
     * @param filter the data transfer object containing the criteria to be applied
     * during predicate creation
     * @return a list of constructed {@code Predicate}s matching the filter criteria
     */
    public static List<Predicate>  buildPredicates(Path<Address> path, CriteriaBuilder cb, AddressQueryFilter filter) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.conjunction());

        if (filter == null) {
            return predicates;
        }

        if (filter.state() != null) {
            predicates.add(
                    cb.equal(cb.lower(path.get("state")), filter.state().toLowerCase())
            );
        }

        if (filter.city() != null) {
            predicates.add(
                    cb.equal(cb.lower(path.get("city")), filter.city().toLowerCase())
            );
        }

        if (filter.neighborhood() != null) {
            predicates.add(
                    cb.equal(cb.lower(path.get("neighborhood")), filter.neighborhood().toLowerCase())
            );
        }

        if (filter.street() != null) {
            predicates.add(
                    cb.equal(cb.lower(path.get("street")), filter.street().toLowerCase())
            );
        }

        if (filter.number() != null) {
            predicates.add(
                    cb.equal(cb.lower(path.get("number")), filter.number().toLowerCase())
            );
        }

        if (filter.postalCode() != null) {
            predicates.add(
                    cb.equal(cb.lower(path.get("postalCode")), filter.postalCode().toLowerCase())
            );
        }

        if (filter.deliveryAddress() != null) {
            predicates.add(
                    cb.equal(path.get("deliveryAddress"), filter.deliveryAddress())
            );
        }

        return predicates;
    }
}
