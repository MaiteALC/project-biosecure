package br.com.biosecure.mappers;

import br.com.biosecure.builders.TaxDataTestBuilder;
import br.com.biosecure.dto.TaxDataInputDto;
import br.com.biosecure.dto.TaxDataResponseDto;
import br.com.biosecure.model.Cnae;
import br.com.biosecure.model.RegistrationStatus;
import br.com.biosecure.model.TaxData;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TaxDataMapperTest {

    @Test
    void shouldConvertToTaxDataDtoCorrectly() {
        TaxData td = TaxDataTestBuilder.aTaxData().withCnae(new Cnae("2121101", "test")).build();

        TaxDataResponseDto dto = TaxDataMapper.toDto(td);

        assertEquals("2121-1/01", dto.cnae().getFormattedCode());
    }

    @Test
    void shouldConvertToTaxDataEntityCorrectly() {
        TaxDataInputDto dto = new TaxDataInputDto(LocalDateTime.now(), LocalDate.of(2014, 4, 24), RegistrationStatus.ACTIVE, "null", new Cnae("2121101", "test"));

        TaxData td = TaxDataMapper.toEntity(dto);

        assertEquals("2121-1/01", td.getCnae().getFormattedCode());
    }

    @Test
    void shouldThrowException_WhenInputIsNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> TaxDataMapper.toDto(null));

        NullPointerException exception2 = assertThrows(NullPointerException.class, () -> TaxDataMapper.toEntity(null));

        assertEquals("A tax data entity is required", exception.getMessage());
        assertEquals("A tax data DTO is required", exception2.getMessage());
    }
}
