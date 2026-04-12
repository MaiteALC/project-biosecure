package br.com.biosecure.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PartnerResponseDto(

        @NotNull(message = "Partner identifier code is required")
        Integer identifierCode,

        @NotBlank(message = "Partner name is required")
        String name,

        @NotBlank(message = "Partner type is required")
        String type,

        @NotNull(message = "Entry date is required")
        LocalDate entryDate
) {}
