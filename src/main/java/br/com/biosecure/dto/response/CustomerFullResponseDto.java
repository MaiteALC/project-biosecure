package br.com.biosecure.dto.response;

import br.com.biosecure.mappers.CustomerMapper;
import br.com.biosecure.model.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * A Data Transfer Object (DTO) designed to encapsulate data for API response payloads.
 * <p>
 * <strong>Architectural Role:</strong> This record serves as the contract for
 * authorized {@code GET} operations, implementing <strong>Information Hiding</strong> by limiting
 * the data exposed to the API client. It projects the {@link Customer} aggregate root
 * state into a format optimized for consumption, ensuring that sensitive internal
 * details are not leaked.
 * <p>
 * <strong>Data Consistency:</strong> It ensures that the returned payload adheres to
 * the established API contract. By using annotations, it documents and guarantees the
 * presence of mandatory fields, providing a reliable and "clean" data structure to
 * the requester.
 *
 * @param customerId the unique identifier for this customer, used for subsequent API calls
 * @param registrationDate The ISO-8601 registration date
 * @param corporateName the full legal name of the entity as stored in the system
 * @param cnpj the 14-digit Brazilian tax identification number, typically returned in a formatted state
 * @param email the registered business contact email
 * @param addresses the collection of nested address information associated with this customer
 * @param financialData the projected financial profile for the client's view
 * @param taxData the public statutory tax information
 *
 * @see CustomerMapper
 * @see CustomerSummaryResponseDto
 * @see AddressResponseDto
 * @see FinancialDataResponseDto
 * @see TaxDataResponseDto
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public record CustomerFullResponseDto(

        @NotNull(message = "Customer ID is required")
        @JsonProperty("customer_id")
        UUID customerId,

        @NotNull(message = "Registration date is required")
        @JsonProperty("registration_date")
        LocalDate registrationDate,

        @NotBlank(message = "Corporate name is required")
        @JsonProperty("corporate_name")
        String corporateName,

        @NotBlank(message = "CNPJ number is required")
        String cnpj,

        @NotBlank(message = "Email is required")
        String email,

        @Valid
        @NotNull
        Set<AddressResponseDto> addresses,

        @Valid
        @NotNull
        @JsonProperty("financial_data")
        FinancialDataResponseDto financialData,

        @Valid
        @NotNull
        @JsonProperty("tax_data")
        List<TaxDataResponseDto> taxData

) implements CustomerResponseDto {}
