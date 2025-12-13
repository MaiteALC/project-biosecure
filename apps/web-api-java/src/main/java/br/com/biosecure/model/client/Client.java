package br.com.biosecure.model.client;

import java.util.UUID;
import br.com.biosecure.utils.NotificationContext;
import br.com.biosecure.utils.StringUtils;

public class  Client {
    private String corporateName;
    private final UUID id;
    private final Cnpj cnpj;
    private Address address;
    private String email;

    private static int MIN_NAME_LENGTH = 2;
    private static int MAX_NAME_LENGTH = 55;
    
    public Client(String corporateName, Cnpj cnpj, Address address, String email) {
        NotificationContext notificationContext = new NotificationContext();

        StringUtils.validateString(corporateName, MIN_NAME_LENGTH, "name", MAX_NAME_LENGTH, notificationContext);

        StringUtils.validateCorporateEmail(email, notificationContext);

        if (notificationContext.hasErrors()) {
            throw new InvalidClientAttributeException(notificationContext.getErrors());
        }

        this.corporateName = corporateName;
        this.cnpj = cnpj;
        this.id = UUID.randomUUID();
        this.address = address;
        this.email = email;
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
        return "Client [corporate name: " + corporateName + ", CNPJ: " + cnpj.getFormattedNumber() + ", ID: " + id + ", address: " + address + ", email: " + email + "]";
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

    public void setCorporateName(String newName) {
        if (newName.isBlank()) {
            throw new InvalidClientAttributeException("corporate name");
        }

        this.corporateName = newName;
    }
    public void setEmail(String newEmail) {
        NotificationContext notificationContext = new NotificationContext();
        
        StringUtils.validateCorporateEmail(newEmail, notificationContext);
        
        if (notificationContext.hasErrors()) {
            throw new InvalidClientAttributeException("email");
        }

        this.email = newEmail;
    }

    public void setAddress(Address newAddress) {
        this.address = newAddress;
    }
}