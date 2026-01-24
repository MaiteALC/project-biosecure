package br.com.biosecure.model;

import br.com.biosecure.utils.NotificationContext;
import br.com.biosecure.utils.NumberUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class FinancialData {
    private final LocalDate startDateActivities;
    private final String economicActivityCode;
    private final Cnpj cnpj;
    private BigDecimal shareCapital;
    private BigDecimal totalCredit;
    private BigDecimal utilizedCredit = BigDecimal.ZERO;

    private final static Pattern CNAE_REGEX = Pattern.compile(
        "^[0-9]{4}-[0-9]/[0-9]{2}$"
    );
    private final static String[] ALLOWED_CNAES = {
            "8610-1/01", "8610-1/02", "8640-2/02", "8630-5/01", "8630-5/02", "8650-0/01", // human health and clinical analysis
            "7210-0/00", "7120-1/00", // research, development and biotechnology
            "2110-6/00", "2121-1/01", "2121-1/02", "2121-1/03", "2123-8/00", // pharmaceutical and inputs industry
            "7500-1/00", "2122-0/00", // animal health
            "3812-2/00", "3822-0/00", "8129-0/00" // waste management and support services
    };

    public FinancialData(LocalDate startDateActivities, String cnaeNumber, Cnpj cnpj, String registrationStatus, BigDecimal shareCapital) {
        NotificationContext notification = new NotificationContext();

        NumberUtils.validateNumericalAttribute(shareCapital, BigDecimal.ZERO, "social capital", BigDecimal.valueOf(Long.MAX_VALUE), notification);

        if (cnpj == null) {
            notification.addError("CNPJ number", "CNPJ number mustn't be null.");
        }

        if (!registrationStatus.equalsIgnoreCase("ATIVA")) {
            notification.addError("Registration status", "Registration status must be 'ATIVA'.");
        }

        if (cnaeNumber == null || !CNAE_REGEX.matcher(cnaeNumber).matches()) {
            notification.addError("CNAE number", "CNAE number format is incorrect.");
        }
        else {
            boolean allowed = false;
            for (String cnae : ALLOWED_CNAES) {
                if (cnaeNumber.equals(cnae)) {
                    allowed = true;
                    break;
                }
            }

            if (!allowed) {
                notification.addError("CNAE number", "The entered CNAE number doesn't matches with an allowed economical activity.");
            }
        }

        if (notification.hasErrors()) {
            throw new InvalidFinancialDataException(notification.getErrors());
        }

        this.startDateActivities = startDateActivities;
        this.economicActivityCode = cnaeNumber;
        this.cnpj = cnpj;
        this.shareCapital = shareCapital;
        this.totalCredit = this.shareCapital.multiply(BigDecimal.valueOf(0.3)).setScale(4, RoundingMode.HALF_EVEN);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Financial Data = ");
        return sb.append("[start date activitie=").append(startDateActivities)
                .append(", economic activity code (CNAE)=").append(economicActivityCode)
                .append(", social capital=R$").append(shareCapital.toString())
                .append(", total credit=R$").append(totalCredit.toString())
                .append(", utilized credit=R$").append(utilizedCredit.toString())
                .append(", remainder credit=R$").append(getRemainderCredit().toString())
                .append(']').toString();
    }

    public LocalDate getStartDateActivities() {
        return startDateActivities;
    }

    public String getEconomicActivityCode() {
        return economicActivityCode;
    }

    public BigDecimal getShareCapital() {
        return shareCapital;
    }

    public BigDecimal getTotalCredit() {
        return totalCredit;
    }

    public BigDecimal getUtilizedCredit() {
        return utilizedCredit;
    }

    public BigDecimal getRemainderCredit() {
        return totalCredit.subtract(utilizedCredit);
    }

    public Cnpj getCnpj() {
        return cnpj;
    }

    public void updateSocialCapital(BigDecimal socialCapital) {
        if  (socialCapital == null || socialCapital.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidFinancialDataException("share capital", "Share capital cannot be null or negative");
        }

        this.shareCapital = socialCapital.setScale(4, RoundingMode.HALF_EVEN);
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
