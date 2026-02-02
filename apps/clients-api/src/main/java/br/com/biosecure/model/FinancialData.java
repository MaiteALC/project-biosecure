package br.com.biosecure.model;

import br.com.biosecure.utils.NotificationContext;
import br.com.biosecure.utils.NumberUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
@Table(name = "TB_CLIENTS_FINANCIAL_DATA")
@NoArgsConstructor
@Getter
public class FinancialData {
    private LocalDate startDateActivities;
    private Cnae cnae;
    private Cnpj cnpj;
    private BigDecimal shareCapital;
    private BigDecimal totalCredit;
    private BigDecimal utilizedCredit = BigDecimal.ZERO;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    @Setter
    private Client client;

    public FinancialData(LocalDate startDateActivities, Cnae cnae, Cnpj cnpj, String registrationStatus, BigDecimal shareCapital) {
        validateInstantiationRules(shareCapital, cnpj, cnae, startDateActivities, registrationStatus);

        this.startDateActivities = startDateActivities;
        this.cnae = cnae;
        this.cnpj = cnpj;
        this.shareCapital = shareCapital;
        this.totalCredit = this.shareCapital.multiply(BigDecimal.valueOf(0.3)).setScale(4, RoundingMode.HALF_EVEN);
    }

    private static void validateInstantiationRules(BigDecimal shareCapital, Cnpj cnpj, Cnae cnaeNumber, LocalDate startDateActivities, String registrationStatus) {
        NotificationContext notification = new NotificationContext();

        NumberUtils.validateNumericalAttribute(shareCapital, BigDecimal.ZERO, "social capital", BigDecimal.valueOf(Long.MAX_VALUE), notification);

        if (cnpj == null) {
            notification.addError("CNPJ number", "CNPJ number mustn't be null.");
        }

        if (!registrationStatus.equalsIgnoreCase("ATIVA")) {
            notification.addError("Registration status", "Registration status must be 'ATIVA'.");
        }

        if (!Cnae.isAllowedCnae(cnaeNumber)) {
            notification.addError("CNAE number", "CNAE number isn't allowed.");
        }

        if (startDateActivities == null || startDateActivities.isAfter(LocalDate.now())) {
            notification.addError("Starting date activities", "The entered date shouldn't be in the future.");
        }

        if (notification.hasErrors()) {
            throw new InvalidFinancialDataException(notification.getErrors());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Financial Data = ");
        return sb.append("[start date activity=").append(startDateActivities)
                .append(", economic activity code (CNAE)=").append(cnae)
                .append(", social capital=R$").append(shareCapital.toString())
                .append(", total credit=R$").append(totalCredit.toString())
                .append(", utilized credit=R$").append(utilizedCredit.toString())
                .append(", remainder credit=R$").append(getRemainderCredit().toString())
                .append(']').toString();
    }

    public BigDecimal getRemainderCredit() {
        return totalCredit.subtract(utilizedCredit);
    }

    public void updateShareCapital(BigDecimal shareCapital) {
        if  (shareCapital == null || shareCapital.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidFinancialDataException("share capital", "Share capital cannot be null or negative");
        }

        this.shareCapital = shareCapital.setScale(4, RoundingMode.HALF_EVEN);
    }

    public void updateTotalCredit(BigDecimal totalCredit) {
        if (totalCredit == null || totalCredit.compareTo(shareCapital) > 0) {
            throw new InvalidFinancialDataException("total credit", "Total credit cannot be null or greater than social capital");
        }

        this.totalCredit = totalCredit.setScale(4, RoundingMode.HALF_EVEN);
    }

    public void payDebit(BigDecimal paymentValue) {
        if (paymentValue == null || paymentValue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidFinancialDataException("payment value", "Payment value cannot be null, zero or negative");
        }

        if (paymentValue.compareTo(utilizedCredit) > 0) {
            throw new InvalidFinancialDataException("payment value", "Payment value cannot be greater than debit");
        }

        this.utilizedCredit = utilizedCredit.subtract(paymentValue).setScale(4, RoundingMode.HALF_EVEN);
    }

    public void utilizeCredit(BigDecimal valueToUse) {
        if (valueToUse == null || valueToUse.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidFinancialDataException("credit value", "Credit value to use cannot be null, zero or negative");
        }

        if  (valueToUse.compareTo(getRemainderCredit()) > 0) {
            throw new InvalidFinancialDataException("credit value", "Credit value to use cannot be greater than available credit");
        }

        this.utilizedCredit = utilizedCredit.add(valueToUse).setScale(4, RoundingMode.HALF_EVEN);
    }
}
