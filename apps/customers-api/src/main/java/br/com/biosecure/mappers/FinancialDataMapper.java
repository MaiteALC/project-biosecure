package br.com.biosecure.mappers;

import br.com.biosecure.dto.FinancialDataInputDto;
import br.com.biosecure.dto.FinancialDataResponseDto;
import br.com.biosecure.model.FinancialData;

public class FinancialDataMapper {

    public static FinancialData toEntity(FinancialDataInputDto dto) {
        if (dto == null) {
            throw new NullPointerException("A financial data DTO is required");
        }

        return new  FinancialData(dto.shareCapital());
    }

    public static FinancialDataResponseDto toDto(FinancialData entity) {
        if (entity == null) {
            throw new NullPointerException("A financial data entity is required");
        }

        return new FinancialDataResponseDto(entity.getShareCapital(), entity.getTotalCredit(), entity.getUtilizedCredit());
    }
}
