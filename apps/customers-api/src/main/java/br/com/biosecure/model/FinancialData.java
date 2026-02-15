package br.com.biosecure.model;

import br.com.biosecure.utils.NotificationContext;
import br.com.biosecure.utils.NumberUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Entity
@Table(name = "customer_financial_data", schema = "sales")
@NoArgsConstructor
@Getter
public class FinancialData {

    @Column(name = "share_capital", nullable = false, precision = 19, scale = 2)
    private BigDecimal shareCapital;

    @Column(name = "total_credit", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalCredit;

    @Column(name = "utilized_credit", nullable = false, precision = 19, scale = 2)
    private BigDecimal utilizedCredit = BigDecimal.ZERO;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @Setter
    @MapsId
    private Customer customer;

    @Id
    private UUID customerId;

    public FinancialData(BigDecimal shareCapital) {
        validateInstantiationRules(shareCapital);

        this.shareCapital = shareCapital;
        this.totalCredit = this.shareCapital.multiply(BigDecimal.valueOf(0.3)).setScale(4, RoundingMode.HALF_EVEN);
    }

    private static void validateInstantiationRules(BigDecimal shareCapital) {
        NotificationContext notification = new NotificationContext();

        NumberUtils.validateNumericalAttribute(shareCapital, BigDecimal.ZERO, "social capital", BigDecimal.valueOf(Long.MAX_VALUE), notification);

        if (notification.hasErrors()) {
            throw new InvalidFinancialDataException(notification.getErrors());
        }
    }

    @Override
    public String toString() {
        return new StringBuilder("Financial Data = ")
                .append("[share capital=R$").append(shareCapital.toString())
                .append(", total credit=R$").append(totalCredit.toString())
                .append(", utilized credit=R$").append(utilizedCredit.toString())
                .append(", remainder credit=R$").append(getRemainderCredit().toString())
                .append(']')
                .toString();
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
