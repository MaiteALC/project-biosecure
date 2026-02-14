package br.com.biosecure.builders;

import br.com.biosecure.model.*;

import java.util.Set;

public class CustomerBuilder {
    private String corporateName = "Oracle";
    private Cnpj cnpj = new Cnpj("59.456.277/0001-76");
    private Set<Address> address = Set.of(AddressBuilder.anAddress().build());
    private String email = "oracle@biosecure.test.com";
    private FinancialData financialData = FinancialDataBuilder.aFinancialData().build();
    private TaxData taxData = TaxDataTestBuilder.aTaxData().build();

    public CustomerBuilder withCorporateName(String corporateName) {
        this.corporateName = corporateName;
        return this;
    }

    public CustomerBuilder withCnpj(Cnpj cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public CustomerBuilder withAddress(Address address) {
        this.address = Set.of(address);
        return this;
    }

    public CustomerBuilder withAddress(Set<Address> address) {
        this.address = address;
        return this;
    }

    public CustomerBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public CustomerBuilder withFinancialData(FinancialData financialData) {
        this.financialData = financialData;
        return this;
    }

    public CustomerBuilder withTaxData(TaxData taxData) {
        this.taxData = taxData;
        return this;
    }

    public static CustomerBuilder aCustomer() {
        return new CustomerBuilder();
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
