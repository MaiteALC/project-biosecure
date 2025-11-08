package br.com.biosecure.domain.client;

public class Cnpj {
    public final String number; 
    public final String formatedNumber;

    public static final int size = 14;
    
    public Cnpj(String number) {
        if (!isValid(number)) {
            throw new InvalidCnpjException("The entered CNPJ isn't valid");
        }

        this.number = clearFormat(number);
        this.formatedNumber = number;
    }

    private String clearFormat(String formatted) {
        return formatted.trim().replace("/", "").replace("-", "").replace(".", "");
    }
    
    private boolean isValid(String number) {
        // TODO Since July 2026 the CNPJ format going to be modified. Remember to change the logic of this method.

        String unformated = clearFormat(number);

        if (unformated.equals("") || unformated == null || unformated.length() != 14) {
            return false;
        }

        if (!Character.isDigit(unformated.charAt(13)) || !Character.isDigit(unformated.charAt(12))) {
            return false;
        }
        
        int[] firstWeights = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] secondWeights = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    
        int[] cnpjInts = new int[14];
        
        int result = 0;
        int rest = 0;
        int digit = 0;

        for (int i = 0; i < size; i++) {
            cnpjInts[i] =  unformated.charAt(i) - '0'; // Fast way to convert char to int (using the numeric values of characters in ASCII table)
        }

        // Validation of first verifier digit
        for (int i = 0; i < 12; i++) {
           result += firstWeights[i] * cnpjInts[i];
        }

        rest = result % 11;
        digit = (rest < 2) ? 0 : (11 - rest);
        
        if (cnpjInts[12] != digit) {
            return false;
        }

        // Validation of second verifier digit
        result = digit = rest = 0;     

        for (int i = 0; i < 13; i++) {
           result += secondWeights[i] * cnpjInts[i];
        }

        rest = result % 11;
        digit = (rest < 2) ? 0 : (11 - rest);
        
        if (cnpjInts[13] != digit) {
            return false;
        }
        
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
    
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Cnpj other = (Cnpj) obj;

        if (!number.equals(other.number)) {
            return false;
        }

        return true;
    }
}