package br.com.biosecure.dto.update;

import br.com.biosecure.model.RegistrationStatus;

import java.time.LocalDateTime;

public record TaxDataUpdateDto(
        LocalDateTime lastSearchDate,

        RegistrationStatus registrationStatus,

        String registrationStatusDescription,

        CnaeUpdateDto cnae
) {}
