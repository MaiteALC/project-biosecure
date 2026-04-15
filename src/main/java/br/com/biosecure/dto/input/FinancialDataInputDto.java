package br.com.biosecure.dto.input;

import br.com.biosecure.model.FinancialData;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * A Data Transfer Object (DTO) designed to encapsulate input data from API request payloads.
 * <p>
 * <strong>Architectural Role:</strong> This record serves as the primary contract for
 * {@code POST} requests. It securely transports raw input data from the web layer to the
 * service layer, where it is used to instantiate the {@link FinancialData} entity.
 * <p>
 * <strong>Validation:</strong> It enforces strict input constraints using standard
 * annotations, acting as the first line of defense by rejecting malformed or missing
 * mandatory fields before they reach the domain logic.
 *
 * @param shareCapital the monetary amount in BRL; must be a positive value
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public record FinancialDataInputDto(

        @NotNull(message = "Share capital is required")
        @JsonProperty("share_capital")
        BigDecimal shareCapital
) {}
