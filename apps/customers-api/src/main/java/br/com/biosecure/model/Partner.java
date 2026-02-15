package br.com.biosecure.model;

import java.time.LocalDate;

public record Partner (
        int identifierCode,
        String name,
        String cpfOrCnpj,
        int typeCode,
        String type,
        LocalDate entryDate,
        int ageRangeCode
) {}
