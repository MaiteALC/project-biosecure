package br.com.biosecure.dto;

import jakarta.validation.constraints.NotBlank;

public record CnaeResponseDto(
        @NotBlank(message = "CNAE number is required")
        String code,

        @NotBlank(message = "CNAE description is required")
        String description
) {}
