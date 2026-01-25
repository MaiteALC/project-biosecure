package br.com.biosecure.model;

import java.util.regex.Pattern;

public class Cnae {
    private final String code;
    private String description;
    private final String formattedCode;

    private final static Pattern CNAE_REGEX = Pattern.compile(
            "^[0-9]{4}-?[0-9]/?[0-9]{2}$"
    );

    public Cnae(String number, String description) {
        if (number == null || !CNAE_REGEX.matcher(number).matches()) {
            throw new IllegalArgumentException("CNAE number format is invalid");
        }

        this.code = removeFormating(number);
        this.description = description;
        this.formattedCode = formatCnae(number);
    }

    public Cnae(String number) {
        if (number == null || !CNAE_REGEX.matcher(number).matches()) {
            throw new IllegalArgumentException("CNAE number format is invalid");
        }

        this.code = removeFormating(number);
        this.description = "Description unavailable";
        this.formattedCode = formatCnae(number);
    }

    public String getUnformattedCnaeCode() {
        return code;
    }

    public String getFormattedCnaeCode() {
        return formattedCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description is null or blank");
        }

        this.description = description;
    }

    private static String removeFormating(String cnaeFormatted) {
        return cnaeFormatted.replace("-", "").replace("/", "");
    }

    private static String formatCnae(String cnae) {
        StringBuilder sb = new StringBuilder(removeFormating(cnae));
        return sb.insert(4, "-")
                .insert(6, "/")
                .toString();
    }

    public static boolean isAllowedCnae(Cnae cnaeToVerify) {
        if (cnaeToVerify == null || !CNAE_REGEX.matcher(cnaeToVerify.getFormattedCnaeCode()).matches()) {
            return false;
        }

        for (String allowed : ALLOWED_CNAE_NUMBERS) {
            if (cnaeToVerify.getFormattedCnaeCode().equals(allowed)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAllowedCnae(String cnaeToVerify) {
        if (cnaeToVerify == null || !CNAE_REGEX.matcher(cnaeToVerify).matches()) {
            return false;
        }

        for (String allowed : ALLOWED_CNAE_NUMBERS) {
            if (formatCnae(cnaeToVerify).equals(allowed)) {
                return true;
            }
        }
        return false;
    }

    private final static String[] ALLOWED_CNAE_NUMBERS = {
            "8610-1/01", "8610-1/02", "8640-2/02", "8630-5/01", "8630-5/02", "8650-0/01", // human health and clinical analysis
            "7210-0/00", "7120-1/00", // research, development and biotechnology
            "2110-6/00", "2121-1/01", "2121-1/02", "2121-1/03", "2123-8/00", // pharmaceutical and inputs industry
            "7500-1/00", "2122-0/00", // animal health
            "3812-2/00", "3822-0/00", "8129-0/00" // waste management and support services
    };
}
