package br.com.biosecure.dto.input;

import br.com.biosecure.model.Cnae;
import jakarta.validation.constraints.NotBlank;

/**
 * A Data Transfer Object (DTO) designed to encapsulate input data from API request payloads.
 * <p>
 * <strong>Architectural Role:</strong> This record serves as the primary contract for
 * {@code POST} requests. It securely transports raw input data from the web layer to the
 * service layer, where it is used to instantiate the {@link Cnae} value object.
 * <p>
 * <strong>Validation:</strong> It enforces strict input constraints using standard
 * annotations, acting as the first line of defense by rejecting malformed or missing
 * mandatory fields before they reach the domain logic.
 *
 * @param code the plain or formatted Brazilian CNAE code; must not be blank
 * @param description the CNAE description; can be blank
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public record CnaeInputDto(
        @NotBlank(message = "CNAE number is required")
        String code,

        String description
) {}
