package br.com.biosecure.model;

import java.time.LocalDate;
import java.util.List;
import br.com.biosecure.utils.NotificationContext;
import br.com.biosecure.utils.NumberUtils;
import br.com.biosecure.utils.StringUtils;

public class Sanitizer extends Product {
    private final List<Ingredient> composition;
    private final PhysicalForm form;
    private final String registryNumber;
    private final String useIndications;
    private final double phLevel;
    private final boolean requiresDilution;
    private final double densityGramsPerMilliLiter;
    private final boolean flammable;
    private final Ingredient.ChemicalFamily mainChemicalFamily;

    public Sanitizer(String name, double price, List<Ingredient> composition, PhysicalForm form, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, MeasureUnit measureUnit, double quantityPerPackage, String registryNumber, String useIndications, double phLevel, boolean requiresDilution, double densityGramsPerMilliLiter, boolean isFlammable, Ingredient.ChemicalFamily mainChemicalFamily) {

        super(name, price, manufacturer, batchNumber, expirationDate, packagingType, measureUnit, quantityPerPackage);

        NotificationContext notification = new NotificationContext();

        StringUtils.validateString(registryNumber, 8, "register number", 14, true, notification);
        StringUtils.validateString(useIndications, "use indications", true, notification);

        NumberUtils.validateNumericalAttribute(phLevel, 0, "ph level", 14, notification);
        NumberUtils.validateNumericalAttribute(densityGramsPerMilliLiter, 0, "density (g/mL)", 23, notification); // 23 is (a bit greater than) the value of density of the "Osmium", the most dense substance in the world

        if (composition == null || composition.isEmpty()) {
            notification.addError("active ingredients", "active ingredients list cannot be empty/null");
        }

        else {
            double sum = 0;
            for (Ingredient ingredient : composition) {
                sum += ingredient.getConcentrationPercentual();
            }

            if (sum > 100) {
                notification.addError("active ingredients", "total concentration of all active ingredients cannot be greater than 100%");
            }
        }

        if (notification.hasErrors()) {
            throw new InvalidProductAttributeException(notification.getErrors());
        }

        this.composition = composition;
        this.form = form;
        this.registryNumber = registryNumber;
        this.useIndications = useIndications;
        this.phLevel = phLevel;
        this.requiresDilution = requiresDilution;
        this.densityGramsPerMilliLiter = densityGramsPerMilliLiter;
        this.flammable = isFlammable;
        this.mainChemicalFamily = mainChemicalFamily;
    }

    public enum PhysicalForm {
        LIQUID("LQ"),
        SOLID("SO"),
        POWDER("PD"),
        GEL("GL"),
        SPRAY("SP"),
        FOAM("FM"),
        WIPES("WP");

        private final String code;

        PhysicalForm(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public List<Ingredient> getComposition() {
        return composition;
    }

    public PhysicalForm getPhysicalForm() {
        return form;
    }

    public String getRegistryNumber() {
        return registryNumber;
    }

    public String getUseIndication() {
        return useIndications;
    }

    public double getPhLevel() {
        return phLevel;
    }

    public boolean isRequiresDilution() {
        return requiresDilution;
    }
    
    public double getDensity() {
        return densityGramsPerMilliLiter;
    }

    public boolean isFlammable() {
        return flammable;
    }

    public Ingredient.ChemicalFamily getMainChemicalFamily() {
        return mainChemicalFamily;
    }
}
