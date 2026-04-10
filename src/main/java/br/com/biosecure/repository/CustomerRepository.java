package br.com.biosecure.repository;

import br.com.biosecure.model.Customer;
import br.com.biosecure.specifications.CustomerSpecs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

/**
 * The primary persistence gateway for the {@link Customer} aggregate root.
 * <p>
 * This interface abstracts the underlying data access logic, providing standard
 * CRUD (Create, Read, Update, Delete) operations and transaction management.
 * <p>
 * <strong>Query Capabilities:</strong> By extending {@link JpaSpecificationExecutor},
 * this repository enables dynamic, type-safe query execution. It is designed to work
 * in tandem with the {@link CustomerSpecs} to handle
 * complex filtering and conditional database fetches.
 * <p>
 * <strong>Architectural Note:</strong> As the repository for an aggregate root, it
 * implicitly manages the persistence lifecycle of its internally bound value objects
 * (such as {@code Address} and {@code TaxData}).
 *
 * @see Customer
 * @see CustomerSpecs
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public interface CustomerRepository extends
        JpaRepository<Customer, UUID>,
        JpaSpecificationExecutor<Customer> {
}
