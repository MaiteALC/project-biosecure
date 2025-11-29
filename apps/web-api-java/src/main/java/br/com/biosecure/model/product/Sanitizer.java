package br.com.biosecure.model.product;

import java.util.ArrayList;
import java.time.LocalDate;

public class Sanitizer extends Product {
    private final ChemicalBase activeIngredient;
    private final PhysicalForm form;
    private final String registerNumber;
    private final String useIndications;
    private final double phLevel;
    private final double concentration;
    private final ConcentrationUnit concentrationUnit;
    private final boolean flammable;
    private final boolean requiresDilution;

    public Sanitizer(String name, double price, ChemicalBase activeIngredient, PhysicalForm form, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, MeasureUnit measureUnit, int qtdPerPackage, String registerNumber, String useIndications, double phLevel, boolean isFlammable, double concentration, ConcentrationUnit concentrationUnit, boolean requiresDilution) {
        super(name, price, manufacturer, batchNumber, expirationDate, packagingType, measureUnit, qtdPerPackage);

        if (phLevel < 0 || phLevel > 14) {
            throw new InvalidProductAttributeException("ph level");
        }

        if (concentration < 0 || (concentrationUnit == ConcentrationUnit.PERCENTAGE && concentration > 100) || (concentrationUnit == ConcentrationUnit.PPM && concentration > 1000000) ){
            throw new InvalidProductAttributeException("concentration");
        }

        validateBioSafetyRules(activeIngredient, isFlammable, phLevel);

        this.activeIngredient = activeIngredient;
        this.form = form;
        this.registerNumber = registerNumber;
        this.useIndications = useIndications;
        this.phLevel = phLevel;
        this.flammable = isFlammable;
        this.concentration = concentration;
        this.concentrationUnit = concentrationUnit;
        this.requiresDilution = requiresDilution;
    }

    public enum ChemicalBase {
        ETHANOL,
        ALCOHOL_ISOPROPYL,
        SODIUM_HYPOCHLORITE,
        QUATERNARY_AMMONIUM,
        PERACETIC_ACID,
        ENZYMATIC_DETERGENT,
        CHLORHEXIDINE
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

    public enum ConcentrationUnit {
        PERCENTAGE("%"),
        PPM("ppm"),
        GL("°GL"),
        MG_L("mg/L");

        private final String symbol;

        ConcentrationUnit(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    private void validateBioSafetyRules(ChemicalBase chemicalBase, boolean isFlammable, double phLevel) {
        ArrayList<String> invalids = new ArrayList<>();
        invalids.add("Active ingredient");

        if ((chemicalBase == ChemicalBase.ETHANOL || chemicalBase == ChemicalBase.ALCOHOL_ISOPROPYL) && this.concentration > 40 && !isFlammable) {
            invalids.add("Concentration");

            throw new BioSecurityException(
                    "Alcohols with 40% (or more concentration) MUST be flammable.", invalids
            );
        }

        if (chemicalBase == ChemicalBase.SODIUM_HYPOCHLORITE && phLevel < 8.0) {
            invalids.add("Ph level");

            throw new BioSecurityException(
                    "Sodium hypochlorite must be alkaline pH level (>8). Acid pH makes toxic gas.", invalids
            );
        }

        if (chemicalBase == ChemicalBase.ENZYMATIC_DETERGENT && (phLevel < 5 || phLevel > 9)) {
            invalids.add("Ph level");

            throw new BioSecurityException(
                    "Enzymatic detergents requires neutral pH to work correctly.", invalids
            );
        }
    }

    public ChemicalBase getActiveIngredient() {
        return activeIngredient;
    }

    public PhysicalForm getPhysicalForm() {
        return form;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public String getUseIndication() {
        return useIndications;
    }

    public double getPhLevel() {
        return phLevel;
    }

    public double getConcentration() {
        return concentration;
    }

    public ConcentrationUnit getConcentrationUnit() {
        return concentrationUnit;
    }

    public boolean isFlammable() {
        return flammable;
    }

    public boolean isRequiresDilution() {
        return requiresDilution;
    }
}
