package br.com.biosecure.model;

import java.time.LocalDate;

public record Partner (
        String partnerIdentifier,
        String partnerName,
        String partnerCpfOrCnpj,
        String partnerTypeCode,
        String partnerType,
        LocalDate entryDate,
        int ageRangeCode
) {}
