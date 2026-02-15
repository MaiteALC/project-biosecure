package br.com.biosecure.external;

import jakarta.validation.constraints.NotBlank;

public record CnaeExternalDto(
        @NotBlank(message = "CNAE number is required")
        String code,

        @NotBlank(message = "CNAE description is required")
        String description
) {}
