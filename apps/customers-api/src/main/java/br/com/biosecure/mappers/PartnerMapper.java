package br.com.biosecure.mappers;

import br.com.biosecure.dto.response.PartnerResponseDto;
import br.com.biosecure.external.PartnerExternalDto;
import br.com.biosecure.model.Partner;

public class PartnerMapper {

    public static Partner toEntity(PartnerExternalDto dto) {
        if (dto == null) {
            throw new NullPointerException("A partner DTO is required");
        }

        return new Partner(dto.identifierCode(), dto.name(), dto.cpfOrCnpj(), dto.typeCode(), dto.type(), dto.entryDate(), dto.ageRangeCode());
    }

    public static PartnerResponseDto toDto(Partner entity) {
        if (entity == null) {
            throw new NullPointerException("A partner entity is required");
        }

        return new PartnerResponseDto(entity.identifierCode(), entity.name(), entity.type(), entity.entryDate());
    }
}
