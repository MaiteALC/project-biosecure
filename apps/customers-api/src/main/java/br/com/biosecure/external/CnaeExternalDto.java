package br.com.biosecure.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record CnaeExternalDto(

        @JsonProperty(value = "codigo")
        @NotBlank(message = "CNAE number is required")
        String code,

        @JsonProperty(value = "descricao")
        @NotBlank(message = "CNAE description is required")
        String description
) {}
