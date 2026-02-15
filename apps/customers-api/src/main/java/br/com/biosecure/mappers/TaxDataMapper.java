package br.com.biosecure.mappers;

import br.com.biosecure.dto.TaxDataInputDto;
import br.com.biosecure.dto.TaxDataResponseDto;
import br.com.biosecure.model.TaxData;

public class TaxDataMapper {

    public static TaxData toEntity(TaxDataInputDto dto) {
        if  (dto == null) {
            throw new NullPointerException("A tax data DTO is required");
        }

        return TaxData.builder()
                .lastSearchDate(dto.lastSearchDate())
                .registrationStatusDescription(dto.registrationStatusDescription())
                .registrationStatus(dto.registrationStatus())
                .cnae(dto.cnae())
                .activitiesStartDate(dto.activitiesStartDate())
                .build();
    }

    public static TaxDataResponseDto toDto(TaxData entity) {
        if (entity == null) {
            throw new NullPointerException("A tax data entity is required");
        }

        return new TaxDataResponseDto(entity.getLastSearchDate(), entity.getActivitiesStartDate(), entity.getRegistrationStatus(), entity.getStatusDescription(), entity.getCnae());
    }
}
