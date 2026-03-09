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

public class FinancialDataSpecs {

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

    public static Specification<FinancialData> buildSpecification(FinancialDataQueryFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = buildPredicates(root, criteriaBuilder, filter);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
