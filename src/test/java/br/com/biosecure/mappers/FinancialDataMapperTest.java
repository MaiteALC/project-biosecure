package br.com.biosecure.mappers;

import br.com.biosecure.builders.FinancialDataTestBuilder;
import br.com.biosecure.dto.input.FinancialDataInputDto;
import br.com.biosecure.dto.response.FinancialDataResponseDto;
import br.com.biosecure.model.FinancialData;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FinancialDataMapperTest {

    @Test
    void shouldConvertToFinancialDataDtoCorrectly() {
        FinancialData fd = FinancialDataTestBuilder.aFinancialData().withShareCapital(BigDecimal.valueOf(1_600_000_000)).build();

        FinancialDataResponseDto dto = FinancialDataMapper.toDto(fd);

        assertEquals(fd.getShareCapital(), dto.shareCapital());
    }

    @Test
    void shouldConvertToFinancialDataEntityCorrectly() {
        FinancialDataInputDto dto = new FinancialDataInputDto(BigDecimal.valueOf(200_000_000));

        FinancialData fd = FinancialDataMapper.toEntity(dto);

        assertEquals(200_000_000, fd.getShareCapital().intValue());
    }

    @Test
    void shouldThrowException_WhenInputIsNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> FinancialDataMapper.toDto(null));

        NullPointerException exception2 = assertThrows(NullPointerException.class, () -> FinancialDataMapper.toEntity(null));

        assertEquals("A financial data entity is required", exception.getMessage());
        assertEquals("A financial data DTO is required", exception2.getMessage());
    }
}
