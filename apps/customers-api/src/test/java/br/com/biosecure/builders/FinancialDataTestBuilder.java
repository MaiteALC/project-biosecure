package br.com.biosecure.builders;

import br.com.biosecure.model.Cnpj;
import br.com.biosecure.model.FinancialData;
import java.math.BigDecimal;

public class FinancialDataTestBuilder {
    private BigDecimal shareCapital = BigDecimal.valueOf(6_000_000);
    private BigDecimal totalCredit;
    private BigDecimal utilizedCredit;
    private Cnpj cnpj = new Cnpj("06.990.590/0001-23");

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

    public FinancialDataTestBuilder withCnpj(Cnpj cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public static FinancialDataTestBuilder aFinancialData() {
        return new FinancialDataTestBuilder();
    }

    public FinancialData build() {
         FinancialData fd = new FinancialData(cnpj, shareCapital);

         if (totalCredit != null) {
            fd.updateTotalCredit(totalCredit);
        }

         if (utilizedCredit != null) {
             fd.utilizeCredit(utilizedCredit);
         }

        return fd;
    }
}
