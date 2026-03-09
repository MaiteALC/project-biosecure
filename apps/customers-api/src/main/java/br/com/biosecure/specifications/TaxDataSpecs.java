package br.com.biosecure.specifications;

import br.com.biosecure.model.TaxData;
import br.com.biosecure.model.TaxData_;
import br.com.biosecure.queryfilters.TaxDataQueryFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class TaxDataSpecs {

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
