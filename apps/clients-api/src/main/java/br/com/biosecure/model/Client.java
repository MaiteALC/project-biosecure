package br.com.biosecure.model;

import java.time.LocalDateTime;
import java.util.UUID;
import br.com.biosecure.utils.NotificationContext;
import br.com.biosecure.utils.StringUtils;

public class  Client {
    private String corporateName;
    private final UUID id;
    private final Cnpj cnpj;
    private Address address;
    private String email;
    private final FinancialData financialData;
    private final LocalDateTime registrationDate;

    private static final  int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 55;
    
    public Client(String corporateName, Cnpj cnpj, Address address, String email, FinancialData financialData) {
        NotificationContext notificationContext = new NotificationContext();

        StringUtils.validateString(corporateName, MIN_NAME_LENGTH, "name", MAX_NAME_LENGTH, true, notificationContext);

        StringUtils.validateCorporateEmail(email, notificationContext);

        if (financialData == null)
            notificationContext.addError("financial data", "financial data mustn't be null");

        if (notificationContext.hasErrors()) {
            throw new InvalidClientAttributeException(notificationContext.getErrors());
        }

        this.corporateName = corporateName;
        this.cnpj = cnpj;
        this.id = UUID.randomUUID();
        this.address = address;
        this.email = email;
        this.financialData = financialData;
        this.registrationDate = LocalDateTime.now();
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
                .append(", ").append(address.toString())
                .append(", ").append(financialData.toString())
                .append(']').toString();
    }

    public String getCorporateName() {
        return corporateName;
    }

    public UUID getId() {
        return id;
    }

    public Cnpj getCnpj() {
        return cnpj;
    }

    public Address getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public FinancialData getFinancialData() {
        return financialData;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
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

    public void setAddress(Address newAddress) {
        if (newAddress == null) {
            throw new  NullPointerException("address mustn't be null");
        }

        this.address = newAddress;
    }
}