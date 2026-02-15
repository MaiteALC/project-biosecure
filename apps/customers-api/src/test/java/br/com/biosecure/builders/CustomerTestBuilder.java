package br.com.biosecure.builders;

import br.com.biosecure.model.*;

import java.util.Set;

public class CustomerTestBuilder {
    private String corporateName = "Oracle";
    private Cnpj cnpj = new Cnpj("59.456.277/0001-76");
    private Set<Address> address = Set.of(AddressTestBuilder.anAddress().build());
    private String email = "oracle@biosecure.test.com";
    private FinancialData financialData = FinancialDataTestBuilder.aFinancialData().build();
    private TaxData taxData = TaxDataTestBuilder.aTaxData().build();

    public CustomerTestBuilder withCorporateName(String corporateName) {
        this.corporateName = corporateName;
        return this;
    }

    public CustomerTestBuilder withCnpj(Cnpj cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public CustomerTestBuilder withAddress(Address address) {
        this.address = Set.of(address);
        return this;
    }

    public CustomerTestBuilder withAddress(Set<Address> address) {
        this.address = address;
        return this;
    }

    public CustomerTestBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public CustomerTestBuilder withFinancialData(FinancialData financialData) {
        this.financialData = financialData;
        return this;
    }

    public CustomerTestBuilder withTaxData(TaxData taxData) {
        this.taxData = taxData;
        return this;
    }

    public static CustomerTestBuilder aCustomer() {
        return new CustomerTestBuilder();
    }

    public Customer build() {
        return Customer.builder()
                .corporateName(corporateName)
                .cnpj(cnpj)
                .addresses(address)
                .email(email)
                .financialData(financialData)
                .taxData(taxData)
                .build();
    }
}
