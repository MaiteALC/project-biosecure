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
    private final double densityGramsPerMiliLiter;

    public Sanitizer(String name, double price, ChemicalBase activeIngredient, PhysicalForm form, String manufacturer, String batchNumber, LocalDate expirationDate, PackagingType packagingType, MeasureUnit measureUnit, double quantityPerPackage, String registerNumber, String useIndications, double phLevel, boolean isFlammable, double concentration, ConcentrationUnit concentrationUnit, boolean requiresDilution, double densityGramsPerMiliLiter) {
        
        super(name, price, manufacturer, batchNumber, expirationDate, packagingType, measureUnit, quantityPerPackage);

        if (phLevel < 0 || phLevel > 14) {
            throw new InvalidProductAttributeException("ph level");
        }

        if (concentration < 0 || (concentration > 100 && 
        (concentrationUnit == ConcentrationUnit.PERCENTAGE || concentrationUnit == ConcentrationUnit.GAY_LUSSAC)) || 
        (concentrationUnit == ConcentrationUnit.PARTS_PER_MILION && concentration > 1000000) ){
            throw new InvalidProductAttributeException("concentration");
        }
        
        if (densityGramsPerMiliLiter < 0 || densityGramsPerMiliLiter > 23) { 
            throw new InvalidProductAttributeException("density (grams per mili liter"); // 23 is (a bit greater than) the value of density of the "Osmium", the most dense substance in the world
        } 

        if (activeIngredient != ChemicalBase.ALCOHOL_ISOPROPYL && activeIngredient != ChemicalBase.ETHANOL && concentrationUnit == ConcentrationUnit.GAY_LUSSAC) {
            throw new InvalidProductAttributeException("concentration unit");
        }

        validateBioSafetyRules(activeIngredient, isFlammable, phLevel, concentration, concentrationUnit);

        validateString(registerNumber, "register number");
        validateString(useIndications, "use indications");

        this.activeIngredient = activeIngredient;
        this.form = form;
        this.registerNumber = registerNumber;
        this.useIndications = useIndications;
        this.phLevel = phLevel;
        this.flammable = isFlammable;
        this.concentration = concentration;
        this.concentrationUnit = concentrationUnit;
        this.requiresDilution = requiresDilution;
        this.densityGramsPerMiliLiter = densityGramsPerMiliLiter;
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
        PARTS_PER_MILION("ppm"),
        GAY_LUSSAC("°GL"),
        MILIGRAMS_PER_LITER("mg/L");

        private final String symbol;

        ConcentrationUnit(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    private void validateBioSafetyRules(ChemicalBase chemicalBase, boolean isFlammable, double phLevel, double concentration, ConcentrationUnit unit) {
        ArrayList<String> invalids = new ArrayList<>();
        
        invalids.add("Active ingredient");

        if ((chemicalBase == ChemicalBase.ETHANOL || chemicalBase == ChemicalBase.ALCOHOL_ISOPROPYL) && !isFlammable) {
            if (concentration > 40 && 
            (unit == ConcentrationUnit.PERCENTAGE || unit == ConcentrationUnit.GAY_LUSSAC ) || 
            (concentration > 400000 && unit == ConcentrationUnit.PARTS_PER_MILION)) {

                invalids.add("Concentration");
                invalids.add("Is flammable");

                throw new BioSecurityException(
                    "Alcohols with 40% (or more concentration) MUST be flammable.", invalids
                );
            }
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

    public double convertConcentration(ConcentrationUnit fromUnit) {
        if (this.concentrationUnit == fromUnit) {
            return concentration;
        }

        ConcentrationUnit objConcentrationUnit = this.concentrationUnit;

        final int PERC_TO_PPM = 10000;

        switch (fromUnit) {
            case ConcentrationUnit.MILIGRAMS_PER_LITER:
                if (objConcentrationUnit == ConcentrationUnit.PERCENTAGE || objConcentrationUnit == ConcentrationUnit.GAY_LUSSAC) {
                    return this.concentration * PERC_TO_PPM * this.densityGramsPerMiliLiter;
                }
        
                else if (objConcentrationUnit == ConcentrationUnit.PARTS_PER_MILION) {
                    return this.concentration * this.densityGramsPerMiliLiter;
                }

                break;
        
            case ConcentrationUnit.PARTS_PER_MILION:
                if ((objConcentrationUnit == ConcentrationUnit.PERCENTAGE || objConcentrationUnit == ConcentrationUnit.GAY_LUSSAC)) {
                    return this.concentration * PERC_TO_PPM;
                }

                else if (objConcentrationUnit == ConcentrationUnit.MILIGRAMS_PER_LITER) {
                    return this.concentration / this.densityGramsPerMiliLiter;
                }
                
                break;

            case ConcentrationUnit.PERCENTAGE:
                if (objConcentrationUnit == ConcentrationUnit.PARTS_PER_MILION) {
                    return this.concentration / PERC_TO_PPM;
                }

                else if (objConcentrationUnit == ConcentrationUnit.MILIGRAMS_PER_LITER) {
                    return this.concentration / (this.densityGramsPerMiliLiter * PERC_TO_PPM);
                }

                else if (objConcentrationUnit == ConcentrationUnit.GAY_LUSSAC) {
                    return this.concentration; // Direct conversion °GL -> %
                }
                
                break;

            case ConcentrationUnit.GAY_LUSSAC:
                if (objConcentrationUnit == ConcentrationUnit.PARTS_PER_MILION) {
                    return this.concentration / PERC_TO_PPM;
                }

                else if (objConcentrationUnit == ConcentrationUnit.MILIGRAMS_PER_LITER) {
                    return this.concentration / (this.densityGramsPerMiliLiter * PERC_TO_PPM);
                }
                
                else if (objConcentrationUnit == ConcentrationUnit.PERCENTAGE) {
                    return this.concentration; // Direct conversion % -> °GL
                }

                break;
        }
        
        throw new IllegalArgumentException(
            "Unsupported conversion: " + this.concentrationUnit + " to " + fromUnit
        );
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
    
    public double getDensity() {
        return densityGramsPerMiliLiter;
    }
}
