package br.com.biosecure.builders;

import br.com.biosecure.model.Cnae;
import br.com.biosecure.model.TaxData;
import br.com.biosecure.model.RegistrationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaxDataTestBuilder {
    private LocalDateTime lastSearchDate =  LocalDateTime.now().minusYears(4);
    private Cnae cnae = new Cnae("7120-1/00", "Technical tests and analyses");
    private String registrationStatusDescription = "Random test description";
    private LocalDate activitiesStartDate = LocalDate.of(2020, 1, 1);
    private RegistrationStatus registrationStatus = RegistrationStatus.ACTIVE;

    public TaxDataTestBuilder withLastSearchDate(LocalDateTime lastSearchDate) {
        this.lastSearchDate = lastSearchDate;
        return this;
    }

    public TaxDataTestBuilder withRegistrationStatusDescription(String registrationStatusDescription) {
        this.registrationStatusDescription = registrationStatusDescription;
        return this;
    }

    public TaxDataTestBuilder withRegistrationStatus(RegistrationStatus registrationStatus) {
        this.registrationStatus = registrationStatus;
        return this;
    }

    public TaxDataTestBuilder withActivitiesStartDate(LocalDate activitiesStartDate) {
        this.activitiesStartDate = activitiesStartDate;
        return this;
    }

    public TaxDataTestBuilder withCnae(Cnae cnae) {
        this.cnae = cnae;
        return this;
    }

    public static TaxDataTestBuilder aFiscalData() {
        return new TaxDataTestBuilder();
    }

    public TaxData build() {
        return TaxData.builder()
                .cnae(cnae)
                .activitiesStartDate(activitiesStartDate)
                .lastSearchDate(lastSearchDate)
                .registrationStatus(registrationStatus)
                .registrationStatusDescription(registrationStatusDescription)
                .build();
    }
}
