package br.com.biosecure.specifications;

import br.com.biosecure.model.Address;
import br.com.biosecure.queryfilters.AddressQueryFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class AddressSpecs {

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
