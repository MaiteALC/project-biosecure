package br.com.biosecure.mappers;

import br.com.biosecure.dto.input.CnaeInputDto;
import br.com.biosecure.dto.response.CnaeResponseDto;
import br.com.biosecure.model.Cnae;

public class CnaeMapper {

    public static Cnae toEntity(CnaeInputDto dto) {
        if (dto == null) {
            throw new NullPointerException("A CNAE DTO is required");
        }

        return new Cnae(dto.code(),  dto.description());
    }

    public static CnaeResponseDto toDto(Cnae entity) {
        if (entity == null) {
            throw new NullPointerException("A CNAE entity is required");
        }

        return new CnaeResponseDto(entity.getFormattedCode(), entity.getDescription());
    }
}
