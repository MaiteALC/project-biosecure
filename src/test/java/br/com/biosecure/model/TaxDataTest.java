package br.com.biosecure.model;

import br.com.biosecure.builders.TaxDataTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaxDataTest {

    @Test
    void shouldNotBuildFiscalData_WhenValuesAreNull() {
        String expectedMessage = "These attributes are invalids:\n\t - last search date | last search date mustn't be null\n\t - registration status | registration status mustn't be null\n\t - status description | status description mustn't be null\n\t - CNAE | CNAE number unallowed\n\t - activities start date | the entered date is invalid\n";

        InvalidTaxDataException exception = assertThrows(InvalidTaxDataException.class, () -> TaxData.builder()
                .activitiesStartDate(null)
                .lastSearchDate(null)
                .registrationStatus(null)
                .registrationStatusDescription(null)
                .cnae(null)
                .build());

        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"9602-5/01", "47.81-4/00", "73.19002", "4399-101", "45307/03"})
    void shouldThrowException_WhenCnaeNumberIsUnallowed(String cnaeNum) {
        assertThrows(InvalidTaxDataException.class, () -> TaxDataTestBuilder.aTaxData().withCnae(new Cnae(cnaeNum, "test description")).build());
    }


    @Test
    void shouldThrowException_WhenDateIsInvalid() {
        InvalidTaxDataException exception = assertThrows(InvalidTaxDataException.class, () -> TaxDataTestBuilder
                .aTaxData()
                .withActivitiesStartDate(LocalDate.of(1790, 5, 20))
                .build());

        InvalidTaxDataException exception2 = assertThrows(InvalidTaxDataException.class, () -> TaxDataTestBuilder
                .aTaxData()
                .withActivitiesStartDate(LocalDate.now().plusDays(1))
                .build());

        assertEquals("These attributes are invalids:\n\t - activities start date | the entered date is invalid\n", exception.getMessage());

        assertEquals("These attributes are invalids:\n\t - activities start date | the entered date is invalid\n", exception2.getMessage());
    }
}
