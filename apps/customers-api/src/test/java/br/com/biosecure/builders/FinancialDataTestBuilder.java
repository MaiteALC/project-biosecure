package br.com.biosecure.builders;

import br.com.biosecure.model.FinancialData;
import java.math.BigDecimal;

public class FinancialDataTestBuilder {
    private BigDecimal shareCapital = BigDecimal.valueOf(6_000_000);
    private BigDecimal totalCredit;
    private BigDecimal utilizedCredit;

    public FinancialDataTestBuilder withShareCapital(BigDecimal shareCapital) {
        this.shareCapital = shareCapital;
        return this;
    }

    public FinancialDataTestBuilder withTotalCredit(BigDecimal totalCredit) {
        this.totalCredit = totalCredit;
        return this;
    }

    public FinancialDataTestBuilder withUtilizedCredit(BigDecimal utilizedCredit) {
        this.utilizedCredit = utilizedCredit;
        return this;
    }

    public static FinancialDataTestBuilder aFinancialData() {
        return new FinancialDataTestBuilder();
    }

    public FinancialData build() {
         FinancialData fd = new FinancialData(shareCapital);

         if (totalCredit != null) {
            fd.updateTotalCredit(totalCredit);
        }

         if (utilizedCredit != null) {
             fd.utilizeCredit(utilizedCredit);
         }

        return fd;
    }
}
