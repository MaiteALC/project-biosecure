package br.com.biosecure.dto.response;

import br.com.biosecure.mappers.CustomerMapper;
import br.com.biosecure.model.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * A minimalist Data Transfer Object (DTO) designed for high-performance API response payloads.
 * <p>
 * <strong>Architectural Role:</strong> This record serves as the <strong>primary public contract</strong>
 * for {@code GET} operations. It implements strict <strong>Information Hiding</strong> by
 * projecting only the essential identity of a {@link Customer}, optimized for high-volume
 * list views and summary reports.
 * <p>
 * <strong>Security Context:</strong> By design, this DTO redacts sensitive business and
 * financial details. It is intended for public or low-privilege consumers, ensuring that
 * full entity exposure is reserved for authorized requests backed by elevated credentials.
 * <p>
 * <strong>Data Consistency:</strong> It adheres to a reduced schema, guaranteeing
 * that the requester receives a lightweight and consistent data structure for
 * rapid processing and rendering.
 *
 * @param corporateName the full legal name of the entity
 * @param cnpj the 14-digit Brazilian tax identification number, formatted for public display
 * @param email the primary contact email for business inquiries
 * @param registrationDate the ISO-8601 registration date (YYYY-MM-DD)
 *
 * @see CustomerFullResponseDto
 * @see CustomerMapper
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public record CustomerSummaryResponseDto(
        @NotBlank(message = "Corporate name is required")
        @JsonProperty("corporate_name")
        String corporateName,

        @NotBlank(message = "Email is required")
        String email,

        @NotNull(message = "CNPJ number is required")
        String cnpj,

        @NotNull(message = "Registration date is required")
        @JsonProperty("registration_date")
        LocalDate registrationDate

) implements CustomerResponseDto {}
