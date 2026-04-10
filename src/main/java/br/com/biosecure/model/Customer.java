package br.com.biosecure.model;

import br.com.biosecure.utils.ErrorAggregator;
import br.com.biosecure.utils.NotificationContext;
import br.com.biosecure.utils.StringUtils;
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

/**
 * An aggregate root that unifies various value objects and entities to represent
 * a complete and valid customer within the <strong>BioSecure</strong> domain.
 * <p>
 * This class acts as a consistency boundary. It guarantees aggregate invariants,
 * ensuring that any instantiated {@code Customer} is always in a strictly valid
 * business state, with all its internal relationships properly established.
 * <p>
 * In the context of BioSecure, a customer must strictly be a legal entity (corporation)
 * with a compliant tax status and an authorized {@link Cnae}, as enforced by its
 * underlying {@link TaxData}.
 *
 * @see Address
 * @see FinancialData
 * @see TaxData
 * @see Cnpj
 *
 * @since 1.0.0
 * @author MaiteALC
 */
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

    /**
     * Creates a new {@link CustomerBuilder} instance.
     * <p>
     * This builder provides a fluent and chainable API to construct a {@link Customer}
     * domain entity step-by-step.
     *
     * @return a new, empty instance of {@link CustomerBuilder}
     */
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

        /**
         * Completes the instantiation process and enforces domain invariants.
         *
         * @return the fully instantiated and validated {@link Customer} entity
         * @throws InvalidCustomerAttributeException if any provided field fails
         * domain validation rules during the final construction phase
         */
        public Customer build() {
            NotificationContext customerNotification = new NotificationContext();

            StringUtils.validateString(corporateName, MIN_NAME_LENGTH, "name", MAX_NAME_LENGTH, true, customerNotification);

            StringUtils.validateCorporateEmail(email, customerNotification);

            if (addresses == null || addresses.isEmpty()) {
                customerNotification.addError("addresses", "at least one address is required");
            }

            ErrorAggregator.verifyNull(financialData, "financial data", customerNotification);
            ErrorAggregator.verifyNull(cnpj, "CNPJ", customerNotification);
            ErrorAggregator.verifyNull(taxData, "tax data", customerNotification);

            if (customerNotification.hasErrors()) {
                throw new InvalidCustomerAttributeException(customerNotification.getErrors());
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
                .append("{corporate name=").append(corporateName)
                .append(", uuid=").append(id)
                .append(", CNPJ=").append(cnpj.getFormattedNumber())
                .append(", email=").append(email)
                .append(", registrationDate=").append(registrationDate)
                .append(", ").append(addresses.toString())
                .append(", ").append(financialData.toString())
                .append(", ").append(taxData.toString())
                .append('}').toString();
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