package br.com.biosecure.model;

import br.com.biosecure.utils.NotificationContext;
import br.com.biosecure.utils.StringUtils;
import br.com.biosecure.utils.ErrorAggregator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "customers", schema = "sales")
@NoArgsConstructor
@Getter
public class Customer {

    @Column(name = "corporate_name", nullable = false, length = 100)
    private String corporateName;

    @Id @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    @Embedded
    @AttributeOverride(
            name = "formattedNumber",
            column = @Column(name = "cnpj_number",  nullable = false, unique = true, length = 18)
    )
    private Cnpj cnpj;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "customer_addresses",
            schema = "sales",
            joinColumns = @JoinColumn(name = "customer_id")
    )
    private Set<Address> addresses;

    @Column(nullable = false, length = 60)
    private String email;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private FinancialData financialData;

    @ElementCollection(fetch =  FetchType.LAZY)
    @CollectionTable(
            name = "customer_tax_data",
            schema = "sales",
            joinColumns = @JoinColumn(name = "customer_id")
    )
    private List<TaxData> taxData;

    private LocalDate registrationDate;

    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 100;

    private Customer(String corporateName, Cnpj cnpj, Set<Address> addresses, String email, FinancialData financialData, List<TaxData> taxData) {
        this.corporateName = corporateName;
        this.cnpj = cnpj;
        this.addresses = addresses;
        this.email = email;
        this.financialData = financialData;
        this.taxData = taxData;
        this.registrationDate = LocalDate.now();
    }

    public static CustomerBuilder builder() {
        return new CustomerBuilder();
    }

    @Setter
    @Accessors(chain = true, fluent = true)
    public static final class CustomerBuilder {
        private String corporateName;
        private Cnpj cnpj;
        private Set<Address> addresses;
        private String email;
        private FinancialData financialData;
        private TaxData taxData;

        public Customer build() {
            NotificationContext clientNotification = new NotificationContext();

            StringUtils.validateString(corporateName, MIN_NAME_LENGTH, "name", MAX_NAME_LENGTH, true, clientNotification);

            StringUtils.validateCorporateEmail(email, clientNotification);

            if (addresses == null || addresses.isEmpty()) {
                clientNotification.addError("addresses", "at least one address is required");
            }

            ErrorAggregator.verifyNull(financialData, "financial data", clientNotification);
            ErrorAggregator.verifyNull(cnpj, "CNPJ", clientNotification);
            ErrorAggregator.verifyNull(taxData, "tax data", clientNotification);

            if (clientNotification.hasErrors()) {
                throw new InvalidCustomerAttributeException(clientNotification.getErrors());
            }

            Customer customer = new Customer(corporateName, cnpj, addresses, email, financialData, List.of(taxData));

            customer.getFinancialData().setCustomer(customer);

            return customer;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
    
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
    
        Customer other = (Customer) obj;

        return id.equals(other.id) && cnpj.equals(other.cnpj);
    }

    @Override
    public String toString() {
        return new StringBuilder("Customer = ")
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
            throw new InvalidCustomerAttributeException(notification.getErrors());
        }

        this.corporateName = newName;
    }

    public void setEmail(String newEmail) {
        NotificationContext notificationContext = new NotificationContext();

        StringUtils.validateCorporateEmail(newEmail, notificationContext);

        if (notificationContext.hasErrors()) {
            throw new InvalidCustomerAttributeException(notificationContext.getErrors());
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