package br.com.biosecure.dto.response;

import br.com.biosecure.model.Customer;

/**
 * A sealed marker interface representing the unified response contract for
 * the {@link Customer} aggregate root.
 * <p>
 * <strong>Architectural Role:</strong> By defining a strictly sealed hierarchy,
 * it ensures exhaustive type safety at compile time. This guarantees that only
 * explicitly permitted projections (Full or Summary) can be processed as valid
 * API responses.
 * <p>
 * Furthermore, this allows the Web layer (Controllers) to rely on safe polymorphism.
 * A controller method can return this abstract representation, delegating the decision
 * of which specific DTO structure to build to the internal Service layer logic.
 *
 * @see CustomerFullResponseDto
 * @see CustomerSummaryResponseDto
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public sealed interface CustomerResponseDto permits
        CustomerFullResponseDto,
        CustomerSummaryResponseDto
{}
