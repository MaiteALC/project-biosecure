package br.com.biosecure.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@Getter
public class Cnpj {
    @Transient
    private String unformattedNumber;
    private String formattedNumber;

    private static final int SIZE_UNFORMATTED = 14;
    private static final Pattern CNPJ_REGEX = Pattern.compile("^[0-9]{2}\\.?[0-9]{3}\\.?[0-9]{3}/?[0-9]{4}-?[0-9]{2}$");

    public Cnpj(String unformattedNumber) {
        if (unformattedNumber == null || unformattedNumber.isBlank()) {
            throw new InvalidCnpjException("CNPJ number is null/blank");
        }

        if (!CNPJ_REGEX.matcher(unformattedNumber).matches()) {
            throw new InvalidCnpjException("CNPJ with invalid format");
        }

        String cleanNumber = clearFormat(unformattedNumber);

        validateVerifierDigits(cleanNumber);

        this.unformattedNumber = cleanNumber;
        this.formattedNumber = formatCnpj(cleanNumber);
    }

    private String clearFormat(String formatted) {
        return formatted.replaceAll("\\D", "");
    }

    private String formatCnpj(String nonFormatted) {
        if (nonFormatted.length() != SIZE_UNFORMATTED) {
            throw new InvalidCnpjException("Invalid size for non formatted CNPJ (" + nonFormatted.length() + ")");
        }

        StringBuilder sb = new StringBuilder(nonFormatted);
    
        sb.insert(2, ".")
        .insert(6, ".")
        .insert(10, "/")
        .insert(15, "-");
        
        return sb.toString();
    }
    
    private void validateVerifierDigits(String number) {
        // TODO Since July 2026 the CNPJ format going to be modified. Remember to change the logic of this method.

        String unformatted = clearFormat(number);

        if (!Character.isDigit(unformatted.charAt(13)) || !Character.isDigit(unformatted.charAt(12))) {
            throw new InvalidCnpjException("The two last digits must be numbers");
        }
        
        int[] firstWeights = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] secondWeights = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    
        int[] cnpjInts = new int[14];
        
        int rest;
        int digit;
        int result = 0;

        for (int i = 0; i < SIZE_UNFORMATTED; i++) {
            cnpjInts[i] =  unformatted.charAt(i) - '0'; // Fast way to convert char to int (using the numeric values of characters in ASCII table)
        }

        // Validation of first verifier digit
        for (int i = 0; i < 12; i++) {
           result += firstWeights[i] * cnpjInts[i];
        }

        rest = result % 11;
        digit = (rest < 2) ? 0 : (11 - rest);
        
        if (cnpjInts[12] != digit) {
            throw new InvalidCnpjException("Invalid verifier digits");
        }

        // Validation of second verifier digit
        result = 0;

        for (int i = 0; i < SIZE_UNFORMATTED - 1; i++) {
           result += secondWeights[i] * cnpjInts[i];
        }

        rest = result % 11;
        digit = (rest < 2) ? 0 : (11 - rest);

        if (cnpjInts[13] != digit) {
            throw new InvalidCnpjException("Invalid verifier digits");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
    
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Cnpj other = (Cnpj) obj;

        return unformattedNumber.equals(other.unformattedNumber);
    }
}