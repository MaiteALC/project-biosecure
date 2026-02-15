package br.com.biosecure.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.Getter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Embeddable
@Getter
public class Cnae {
    @Transient
    private final String unformattedCode;
    @Transient
    private String description;
    private final String formattedCode;

    private final static Pattern CNAE_REGEX = Pattern.compile(
            "^[0-9]{2}\\.?[0-9]{2}-?[0-9]/?[0-9]{2}$"
    );

    public final static Map<String, String> ALLOWED_CNAE_NUMBERS = new HashMap<>();

    {
        ALLOWED_CNAE_NUMBERS.put("8610-1/01", "Hospital care activities (General hospitals).");
        ALLOWED_CNAE_NUMBERS.put("8610-1/02", "Emergency room and hospital unit services for urgent care.");
        ALLOWED_CNAE_NUMBERS.put("8640-2/02", "Services for collection of clinical laboratory tests, clinical tests and services for clinical analysis laboratory");
        ALLOWED_CNAE_NUMBERS.put("8630-5/01", "Outpatient medical activity with resources for performing surgical procedures.");
        ALLOWED_CNAE_NUMBERS.put("8630-5/02", "Outpatient medical activity with resources for carrying out complementary examinations.");
        ALLOWED_CNAE_NUMBERS.put("8650-0/01", "Nursing activities (Vaccination clinics, for example).");
        ALLOWED_CNAE_NUMBERS.put("7210-0/00", "Research and experimental development in physical and natural sciences (Encompasses most biotech startups and university research laboratories).");
        ALLOWED_CNAE_NUMBERS.put("7120-1/00", "Technical tests and analyses (Quality control laboratories, industrial biological tests).");
        ALLOWED_CNAE_NUMBERS.put("2110-6/00", "Manufacture of pharmaceutical products.");
        ALLOWED_CNAE_NUMBERS.put("2121-1/01", "Manufacture of allopathic medicines for human use.");
        ALLOWED_CNAE_NUMBERS.put("2121-1/03", "Manufacture of homeopathic medicines for human use.");
        ALLOWED_CNAE_NUMBERS.put("2123-8/00", "Manufacture of pharmaceutical preparations.");
        ALLOWED_CNAE_NUMBERS.put("7500-1/00", "Veterinary activities (Veterinary clinics and hospitals).");
        ALLOWED_CNAE_NUMBERS.put("2122-0/00", "Manufacture of medicines for veterinary use.");
        ALLOWED_CNAE_NUMBERS.put("3812-2/00", "Collection of hazardous waste (Includes infectious/biological waste).");
        ALLOWED_CNAE_NUMBERS.put("3822-0/00", "Treatment and disposal of hazardous waste.");
        ALLOWED_CNAE_NUMBERS.put("8129-0/00", "Cleaning and pest control activities (Hospital sanitation companies).");
    }

    public Cnae(String number, String description) {
        if (number == null || !CNAE_REGEX.matcher(number).matches()) {
            throw new IllegalArgumentException("CNAE number format is invalid");
        }

        this.unformattedCode = removeFormating(number);
        this.description = description;
        this.formattedCode = formatCnae(number);
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description is null or blank");
        }

        this.description = description;
    }

    private static String removeFormating(String cnaeFormatted) {
        return cnaeFormatted.replace(".", "").replace("-", "").replace("/", "");
    }

    private static String formatCnae(String cnae) {
        StringBuilder sb = new StringBuilder(removeFormating(cnae));
        return sb.insert(4, "-")
                .insert(6, "/")
                .toString();
    }

    public static boolean isAllowedCnae(Cnae cnaeToVerify) {
        if (cnaeToVerify == null || !CNAE_REGEX.matcher(cnaeToVerify.formattedCode).matches()) {
            return false;
        }
        return ALLOWED_CNAE_NUMBERS.containsKey(cnaeToVerify.formattedCode);
    }

    public static boolean isAllowedCnae(String cnaeToVerify) {
        if (cnaeToVerify == null || !CNAE_REGEX.matcher(cnaeToVerify).matches()) {
            return false;
        }
        return ALLOWED_CNAE_NUMBERS.containsKey(formatCnae(cnaeToVerify));
    }
}
