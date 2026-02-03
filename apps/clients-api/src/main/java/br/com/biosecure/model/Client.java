package br.com.biosecure.model;

import br.com.biosecure.utils.NotificationContext;
import br.com.biosecure.utils.StringUtils;
import br.com.biosecure.utils.ErrorAggregator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_CLIENTS")
@NoArgsConstructor
@Getter
public class  Client {
    private String corporateName;
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Embedded
    private Cnpj cnpj;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses;
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    private FinancialData financialData;
    private LocalDateTime registrationDate;

    private static final  int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 55;

    private Client(String corporateName, Cnpj cnpj, List<Address> addresses, String email, FinancialData financialData) {
        this.corporateName = corporateName;
        this.cnpj = cnpj;
        this.id = UUID.randomUUID();
        this.addresses = addresses;
        this.email = email;
        this.financialData = financialData;
        this.registrationDate = LocalDateTime.now();
    }

    public static ClientBuilder builder() {
        return new ClientBuilder();
    }

    @Setter
    @Accessors(chain = true, fluent = true)
    public static final class ClientBuilder {
        private String corporateName;
        private Cnpj cnpj;
        private List<Address> addresses;
        private String email;
        private FinancialData financialData;

        public Client build() {
            NotificationContext clientNotification = new NotificationContext();

            StringUtils.validateString(corporateName, MIN_NAME_LENGTH, "name", MAX_NAME_LENGTH, true, clientNotification);

            StringUtils.validateCorporateEmail(email, clientNotification);

            if (addresses == null || addresses.isEmpty()) {
                clientNotification.addError("addresses", "at least one address is required");
            }

            ErrorAggregator.verifyNull(financialData, "financial data", clientNotification);
            ErrorAggregator.verifyNull(cnpj, "CNPJ", clientNotification);

            if (clientNotification.hasErrors()) {
                throw new InvalidClientAttributeException(clientNotification.getErrors());
            }

            Client client = new Client(corporateName, cnpj, addresses, email, financialData);

            client.getFinancialData().setClient(client);

            return client;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
    
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
    
        Client other = (Client) obj;

        return id.equals(other.id) && cnpj.equals(other.cnpj);
    }

    @Override
    public String toString() {
        return new StringBuilder("Client = ")
                .append("[corporate name=").append(corporateName)
                .append("uuid=").append(id)
                .append(", CNPJ=").append(cnpj)
                .append(", email=").append(email)
                .append(", registrationDate=").append(registrationDate)
                .append(", ").append(addresses.toString())
                .append(", ").append(financialData.toString())
                .append(']').toString();
    }

    public void setCorporateName(String newName) {
        NotificationContext notification = new NotificationContext();

        StringUtils.validateString(newName, MIN_NAME_LENGTH, "corporate name", MAX_NAME_LENGTH, true, notification);

        if (notification.hasErrors()) {
            throw new InvalidClientAttributeException(notification.getErrors());
        }

        this.corporateName = newName;
    }

    public void setEmail(String newEmail) {
        NotificationContext notificationContext = new NotificationContext();

        StringUtils.validateCorporateEmail(newEmail, notificationContext);

        if (notificationContext.hasErrors()) {
            throw new InvalidClientAttributeException(notificationContext.getErrors());
        }

        this.email = newEmail;
    }

    public void addAddress(Address newAddress) {
        if (newAddress == null) {
            throw new  NullPointerException("address mustn't be null");
        }

        this.addresses.add(newAddress);
    }

    public void addAddresses(List<Address> newAddresses) {
        if (newAddresses == null || newAddresses.isEmpty()) {
            throw new IllegalArgumentException("at least one address is required");
        }

        this.addresses.addAll(newAddresses);
    }
}