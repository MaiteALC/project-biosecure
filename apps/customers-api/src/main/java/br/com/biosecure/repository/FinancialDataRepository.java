package br.com.biosecure.repository;

import br.com.biosecure.model.Customer;
import br.com.biosecure.model.FinancialData;
import br.com.biosecure.specifications.FinancialDataSpecs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

/**
 * The persistence gateway dedicated to the {@link FinancialData} entity.
 * <p>
 * This interface abstracts the underlying data access logic, providing standard
 * CRUD (Create, Read, Update, Delete) operations and transaction management.
 * <p>
 * <strong>Query Capabilities:</strong> By extending {@link JpaSpecificationExecutor},
 * this repository enables dynamic, type-safe query execution. It is designed to work
 * in tandem with the {@link FinancialDataSpecs} to handle
 * complex filtering and conditional database fetches.
 * <p>
 * <strong>Domain Constraint:</strong> While {@code FinancialData} is an independent
 * entity with its own lifecycle, it is strictly bound to a parent {@link Customer}.
 * Any operations executed through this repository must ensure that this relational
 * integrity is preserved.
 *
 * @see FinancialData
 * @see Customer
 * @see FinancialDataSpecs
 */
public interface FinancialDataRepository extends
        JpaRepository<FinancialData, UUID>,
        JpaSpecificationExecutor<FinancialData> {
}
