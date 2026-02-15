package br.com.biosecure.dto;

import jakarta.validation.constraints.NotBlank;

public record CnaeInputDto(
        @NotBlank(message = "CNAE number is required")
        String code,

        String description
) {}
