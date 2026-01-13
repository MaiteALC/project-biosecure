package br.com.biosecure.model;

import br.com.biosecure.utils.NumberUtils;
import br.com.biosecure.utils.StringUtils;
import br.com.biosecure.utils.NotificationContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ingredient {
    private final String name;
    private final String casNumber;
    private final ChemicalFamily chemicalFamily;
    private final double concentrationPercentual;
    private final IngredientType type;

    public Ingredient(String name, String casNumber, ChemicalFamily chemicalFamily, double concentrationPercentual, IngredientType type) {
        NotificationContext notification = new NotificationContext();

        if (!isValidCasNumber(casNumber)) {
            notification.addError("CAS Registry Number", "CAS number is invalid");
        }

        StringUtils.validateString(name, 2,"active ingredient name", 60, notification);
        NumberUtils.validateNumericalAttribute(concentrationPercentual, 0.001, "concentration quantity", 100, notification);

        if (notification.hasErrors()) {
            throw new InvalidProductAttributeException(notification.getErrors());
        }

        this.name = name;
        this.casNumber = casNumber;
        this.chemicalFamily = chemicalFamily;
        this.concentrationPercentual = concentrationPercentual;
        this.type = type;
    }

    public enum ChemicalFamily {
        QUATERNARY_AMMONIUM("QA"),
        CHLORINE_RELEASING_AGENT("CR"),
        PEROXIDES("PE"),
        ALCOHOL("AL"),
        ALDEHYDE("AD"),
        IODOPHOR("IO"),
        PHENOLIC("PN"),
        BIGUANIDE("BG");

        private final String code;

        ChemicalFamily(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public enum IngredientType {
        ACTIVE_INGREDIENT,
        VEHICLE,
        SURFACTANT,
        STABILIZER,
        DYE,
        FRAGRANCE
    }

    public String getName() {
        return name;
    }

    public String getCasNumber() {
        return casNumber;
    }

    public ChemicalFamily getChemicalFamily() {
        return chemicalFamily;
    }

    public double getConcentrationPercentual() {
        return concentrationPercentual;
    }

    public IngredientType getType() {
        return type;
    }

    public static boolean isValidCasNumber(String number) {
        if (number == null || number.isBlank()) {
            return false;
        }

        final String CAS_REGEX = "^[0-9]{2,7}-[0-9]{2}-[0-9]$";

        Pattern pattern = Pattern.compile(CAS_REGEX);
        Matcher matcher = pattern.matcher(number);

        if (!matcher.matches()) {
            return false;
        }

        int sum = 0;
        int multiplier = 1;
        int verifierDigit = Integer.parseInt(String.valueOf(number.charAt(number.length()-1)));

        for (int i = number.length()-2; i >= 0; i--) {
            char c = number.charAt(i);

            if (c != '-') {
                sum += Integer.parseInt(String.valueOf(c)) * multiplier;
                multiplier++;
            }
        }
        return (sum % 10) == verifierDigit;
    }
}
