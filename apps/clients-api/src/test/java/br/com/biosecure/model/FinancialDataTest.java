package br.com.biosecure.model;

import br.com.biosecure.builders.FinancialDataBuilder;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class FinancialDataTest {

    @Test
    void shouldUpdateInfosCorrectly_WhenDataIsValid() {
        FinancialData financialData = FinancialDataBuilder.aFinancialData().build();

        financialData.updateShareCapital(BigDecimal.valueOf(10_000_000));
        financialData.updateTotalCredit(BigDecimal.valueOf(5_400_000));

        assertTrue(financialData.getShareCapital().compareTo(BigDecimal.valueOf(10_000_000)) == 0);
        assertTrue(financialData.getTotalCredit().compareTo(BigDecimal.valueOf(5_400_000)) == 0);
    }

    @Test
    void shouldNotUpdateInfos_WhenDataIsInvalid() {
        FinancialData financialData = FinancialDataBuilder.aFinancialData().build();

        InvalidFinancialDataException socialCapitalException = assertThrows(InvalidFinancialDataException.class, () -> financialData.updateShareCapital(BigDecimal.valueOf(-1)) );

        InvalidFinancialDataException creditException = assertThrows(InvalidFinancialDataException.class, () -> financialData.updateTotalCredit(BigDecimal.valueOf(100_000_000)) );

       assertEquals("Total credit cannot be null or greater than social capital", creditException.getMessage());
       assertEquals("Share capital cannot be null or negative", socialCapitalException.getMessage());
    }

    @Test
    void shouldPayDebitAndUseCreditCorrectly_WhenDataIsValid() {
        FinancialData financialData = FinancialDataBuilder.aFinancialData()
                .withShareCapital(BigDecimal.valueOf(60_000_000))
                .withUtilizedCredit(BigDecimal.valueOf(10_000_000))
                .build();

        assertTrue(financialData.getTotalCredit().compareTo(BigDecimal.valueOf(18_000_000)) == 0); // considering that default value for total credit is 30% of social capital value

        assertTrue(financialData.getRemainderCredit().compareTo(BigDecimal.valueOf(8_000_000)) == 0);

        financialData.payDebit(BigDecimal.valueOf(10_000_000));

        assertTrue(financialData.getRemainderCredit().compareTo(BigDecimal.valueOf(18_000_000)) == 0);
    }

    @Test
    void shouldThrowException_WhenPaymentAndCreditValuesAreInvalid() {
        InvalidFinancialDataException socialCapitalException = assertThrows(InvalidFinancialDataException.class, () -> FinancialDataBuilder.aFinancialData()
                .withShareCapital(BigDecimal.valueOf(-1))
                .build());

        InvalidFinancialDataException creditException = assertThrows(InvalidFinancialDataException.class, () -> FinancialDataBuilder.aFinancialData()
                .withShareCapital(BigDecimal.valueOf(10_000_000))
                .withTotalCredit(BigDecimal.valueOf(100_000_000))
                .build());

        assertEquals("These attributes are invalids:\n\t - social capital | The number is less than allowed\n", socialCapitalException.getMessage());

        assertEquals("Total credit cannot be null or greater than social capital", creditException.getMessage());
    }
}
