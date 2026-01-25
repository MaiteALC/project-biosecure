package br.com.biosecure.builders;

import br.com.biosecure.model.Cnae;
import br.com.biosecure.model.Cnpj;
import br.com.biosecure.model.FinancialData;
import java.math.BigDecimal;
import java.time.LocalDate;

public class FinancialDataBuilder {
    private LocalDate startDateActivities = LocalDate.of(2020, 1, 1);
    private Cnae cnae = new Cnae("7120-1/00");
    private BigDecimal shareCapital = BigDecimal.valueOf(6_000_000);
    private BigDecimal totalCredit;
    private BigDecimal utilizedCredit;
    private Cnpj cnpj = new Cnpj("06.990.590/0001-23");
    private String registrationStatus = "ATIVA";

    public FinancialDataBuilder withStartDateActivities(LocalDate startDateActivities) {
        this.startDateActivities = startDateActivities;
        return this;
    }

    public FinancialDataBuilder withCnae(Cnae cnae) {
        this.cnae = cnae;
        return this;
    }

    public FinancialDataBuilder withShareCapital(BigDecimal shareCapital) {
        this.shareCapital = shareCapital;
        return this;
    }

    public FinancialDataBuilder withTotalCredit(BigDecimal totalCredit) {
        this.totalCredit = totalCredit;
        return this;
    }

    public FinancialDataBuilder withUtilizedCredit(BigDecimal utilizedCredit) {
        this.utilizedCredit = utilizedCredit;
        return this;
    }

    public FinancialDataBuilder withCnpj(Cnpj cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public FinancialDataBuilder withRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
        return this;
    }

    public static FinancialDataBuilder aFinancialData() {
        return new FinancialDataBuilder();
    }

    public FinancialData build() {
         FinancialData fd = new FinancialData(startDateActivities, cnae, cnpj, registrationStatus, shareCapital);

         if (totalCredit != null) {
            fd.updateTotalCredit(totalCredit);
        }

         if (utilizedCredit != null) {
             fd.utilizeCredit(utilizedCredit);
         }

        return fd;
    }
}
