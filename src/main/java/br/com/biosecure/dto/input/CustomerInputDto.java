package br.com.biosecure.dto.input;

import br.com.biosecure.model.Cnpj;
import br.com.biosecure.model.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

/**
 * A Data Transfer Object (DTO) designed to encapsulate input data from API request payloads.
 * <p>
 * <strong>Architectural Role:</strong> This record serves as the primary contract for
 * {@code POST} requests. It securely transports raw input data from the web layer to the
 * service layer, where it is used to instantiate the {@link Customer} aggregate root.
 * <p>
 * <strong>Validation:</strong> It enforces strict input constraints using standard
 * annotations, acting as the first line of defense by rejecting malformed or missing
 * mandatory fields before they reach the domain logic.
 *
 * @param corporateName the full legal name of the entity; must not be blank
 * @param cnpj the 14-digit Brazilian tax identification number (plain or formatted)
 * @param email the primary business contact email; must follow a valid electronic mail format
 * @param addresses the nested address information to be associated with this customer
 * @param financialData the nested financial profile required for initial customer assessment
 * @param taxData the statutory tax information required for legal compliance
 *
 * @see AddressInputDto
 * @see FinancialDataInputDto
 * @see TaxDataInputDto
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public record CustomerInputDto(
        @NotBlank(message = "Corporate name is required")
        @JsonProperty("corporate_name")
        String corporateName,

        @NotNull(message = "CNPJ number is required")
        Cnpj cnpj,

        @NotBlank(message = "Email is required")
        String email,

        @Valid
        @NotNull
        Set<AddressInputDto> addresses,

        @Valid
        @NotNull
        @JsonProperty("financial_data")
        FinancialDataInputDto financialData,

        @Valid
        @NotNull
        @JsonProperty("tax_data")
        TaxDataInputDto taxData
) {}
