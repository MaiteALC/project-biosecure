package br.com.biosecure.model;

import br.com.biosecure.utils.ErrorAggregator;
import br.com.biosecure.utils.NotificationContext;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Encapsulates the public statutory and legal tax profile of a {@link Customer}.
 * <p>
 * This class is strictly dedicated to externally verifiable governmental information
 * required for legal compliance and registration validation (e.g., share capital,
 * activities start date, and economic activity).
 * <p>
 * <strong>Architectural Boundary:</strong> It deliberately excludes proprietary commercial
 * data regarding the customer's operations within <strong>BioSecure</strong>. For internal
 * financial metrics and billing, refer to the {@link FinancialData} class.
 *
 * @see Customer
 * @see FinancialData
 *
 * @since 1.0.0
 * @author MaiteALC
 */
@Getter
@NoArgsConstructor
@Table(name = "customer_tax_data", schema = "sales")
@Embeddable
public class TaxData {

    @Column(name = "last_search_date", nullable = false)
    private LocalDateTime lastSearchDate;

    @Column(name = "activities_start_date", nullable = false)
    private LocalDate activitiesStartDate;

    @Column(name = "registration_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RegistrationStatus registrationStatus;

    @Column(name = "status_description")
    private String statusDescription;

    @Embedded
    @AttributeOverride(
            name = "formattedCode",
            column = @Column(name = "cnae_number", nullable = false, length = 10)
    )
    private Cnae cnae;

    private TaxData(LocalDateTime lastSearchDate, LocalDate activitiesStartDate, RegistrationStatus registrationStatus, String statusDescription, Cnae cnae) {
        this.lastSearchDate = lastSearchDate;
        this.activitiesStartDate = activitiesStartDate;
        this.registrationStatus = registrationStatus;
        this.statusDescription = statusDescription;
        this.cnae = cnae;
    }

    /**
     * Creates a new {@link TaxDataBuilder} instance.
     * <p>
     * This builder provides a fluent and chainable API to construct a {@link TaxData}
     * domain entity step-by-step.
     *
     * @return a new, empty instance of {@link TaxDataBuilder}
     */
    public static TaxDataBuilder builder() {
        return new TaxDataBuilder();
    }

    @Setter
    @Accessors(fluent = true, chain = true)
    public static final class TaxDataBuilder {
        private LocalDateTime lastSearchDate;
        private LocalDate activitiesStartDate;
        private RegistrationStatus registrationStatus;
        private String registrationStatusDescription;
        private Cnae cnae;

        /**
         * Completes the instantiation process and enforces domain invariants.
         *
         * @return the fully instantiated and validated {@link TaxData} value object
         * @throws InvalidTaxDataException if any provided field fails
         * domain validation rules during the final construction phase
         */
        public TaxData build() {
            NotificationContext notification = new NotificationContext();

            ErrorAggregator.verifyNull(lastSearchDate, "last search date", notification);
            ErrorAggregator.verifyNull(registrationStatus, "registration status", notification);
            ErrorAggregator.verifyNull(registrationStatusDescription, "status description", notification);

            if (!Cnae.isAllowedCnae(cnae)) {
                notification.addError("CNAE", "CNAE number unallowed");
            }

            if (activitiesStartDate == null || activitiesStartDate.isAfter(LocalDate.now()) || activitiesStartDate.getYear() < 1800) {
                notification.addError("activities start date", "the entered date is invalid");
            }

            if (notification.hasErrors()) {
                throw new InvalidTaxDataException(notification.getErrors());
            }

            return new TaxData(lastSearchDate, activitiesStartDate, registrationStatus, registrationStatusDescription, cnae);
        }
    }

    @Override
    public String toString() {
        return new StringBuilder("Tax Data = ")
                .append("[last search date=").append(lastSearchDate)
                .append(", activities start date=").append(activitiesStartDate)
                .append(", registration status=").append(registrationStatus.toString())
                .append(", status description=").append(statusDescription)
                .append(", CNAE=").append(cnae.getFormattedCode())
                .append("]").toString();
    }
}

