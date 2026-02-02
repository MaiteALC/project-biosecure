package br.com.biosecure.builders;

import br.com.biosecure.model.Address;
import br.com.biosecure.model.Cnpj;
import br.com.biosecure.model.Client;
import br.com.biosecure.model.FinancialData;

import java.util.List;

public class ClientBuilder {
    private String corporateName = "Oracle";
    private Cnpj cnpj = new Cnpj("59.456.277/0001-76");
    private List<Address> address = List.of(AddressBuilder.anAddress().build());
    private String email = "oracle@biosecure.test.com";
    private FinancialData financialData = FinancialDataBuilder.aFinancialData().build();

    public  ClientBuilder withCorporateName(String corporateName) {
        this.corporateName = corporateName;
        return this;
    }

    public  ClientBuilder withCnpj(Cnpj cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public  ClientBuilder withAddress(Address address) {
        this.address = List.of(address);
        return this;
    }

    public  ClientBuilder withAddress(List<Address> address) {
        this.address = address;
        return this;
    }

    public  ClientBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public  ClientBuilder withFinancialData(FinancialData financialData) {
        this.financialData = financialData;
        return this;
    }

    public static ClientBuilder aClient() {
        return new ClientBuilder();
    }

    public Client build() {
        return Client.builder()
                .corporateName(corporateName)
                .cnpj(cnpj)
                .addresses(address)
                .email(email)
                .financialData(financialData)
                .build();
    }
}
