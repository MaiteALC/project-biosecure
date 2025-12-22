package br.com.biosecure.model.product;

import java.util.ArrayList;
import java.time.LocalDate;
import br.com.biosecure.utils.NotificationContext;
import br.com.biosecure.utils.NumberUtils;
import br.com.biosecure.utils.StringUtils;

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

        NotificationContext notification = new NotificationContext();
        
        StringUtils.validateString(registerNumber, 8, "register number", 14, notification);
        StringUtils.validateString(useIndications, "use indications", notification);

        NumberUtils.validateNumericalAttribute(phLevel, 0, "ph level", 14, notification);
        NumberUtils.validateNumericalAttribute(densityGramsPerMiliLiter, 0, "density (g/mL)", 23, notification); // 23 is (a bit greater than) the value of density of the "Osmium", the most dense substance in the world

        NumberUtils.validateNumericalAttribute(concentration, 0, "concentration", getMaxConcentrationValue(concentrationUnit), notification); 

        if (activeIngredient != ChemicalBase.ALCOHOL_ISOPROPYL && activeIngredient != ChemicalBase.ETHANOL && concentrationUnit == ConcentrationUnit.GAY_LUSSAC) {
            notification.addError("concentration unit", "A non-alcoholic substance can't use Gay-Lussac as concentration unit.");
        }

        if (notification.hasErrors()) {
            throw new InvalidProductAttributeException(notification.getErrors());
        }
        
        validateBioSafetyRules(activeIngredient, isFlammable, phLevel, concentration, concentrationUnit);
        
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
        ETHANOL("ET"),
        ALCOHOL_ISOPROPYL("AI"),
        SODIUM_HYPOCHLORITE("SH"),
        QUATERNARY_AMMONIUM("QA"),
        PERACETIC_ACID("PA"),
        CHLORHEXIDINE("CH");

        private String code;

        ChemicalBase(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
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
        MILIGRAMS_PER_LITER("mg/L"),
        MILILITERS_PER_LITER("mL/L");   

        private final String symbol;

        ConcentrationUnit(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    private int getMaxConcentrationValue(ConcentrationUnit cUnit) {
        int maxConcentrationValue;
        
        switch (cUnit) {
            case ConcentrationUnit.PERCENTAGE:
                maxConcentrationValue = 100;
                break;
                
            case ConcentrationUnit.GAY_LUSSAC:
                maxConcentrationValue = 100;
                break;

            case ConcentrationUnit.PARTS_PER_MILION:
                maxConcentrationValue = 1000000;
                break;

            case ConcentrationUnit.MILIGRAMS_PER_LITER:
                maxConcentrationValue = 23000000;
                break;

            case ConcentrationUnit.MILILITERS_PER_LITER:
                maxConcentrationValue = 1000;
                break;

            default:        
                maxConcentrationValue = 24000000;
                break;
        }

        return maxConcentrationValue;
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

    public double convertConcentrationUnit(ConcentrationUnit targetUnit) {
        if (this.concentrationUnit == targetUnit) {
            return concentration;
        }

        ConcentrationUnit objConcentrationUnit = this.concentrationUnit;

        final int PERC_TO_PPM = 10000;

        switch (targetUnit) {
            case ConcentrationUnit.MILIGRAMS_PER_LITER:
                if (objConcentrationUnit == ConcentrationUnit.PERCENTAGE || objConcentrationUnit == ConcentrationUnit.GAY_LUSSAC) {
                    return this.concentration * PERC_TO_PPM * this.densityGramsPerMiliLiter;
                }
        
                else if (objConcentrationUnit == ConcentrationUnit.PARTS_PER_MILION) {
                    return this.concentration * this.densityGramsPerMiliLiter;
                }

                else if (objConcentrationUnit == ConcentrationUnit.MILILITERS_PER_LITER) {
                    return this.concentration * this.densityGramsPerMiliLiter * 1000;
                }

                break;
        
            case ConcentrationUnit.PARTS_PER_MILION:
                if ((objConcentrationUnit == ConcentrationUnit.PERCENTAGE || objConcentrationUnit == ConcentrationUnit.GAY_LUSSAC)) {
                    return this.concentration * PERC_TO_PPM;
                }

                else if (objConcentrationUnit == ConcentrationUnit.MILIGRAMS_PER_LITER) {
                    return this.concentration / this.densityGramsPerMiliLiter;
                }

                else if (objConcentrationUnit == ConcentrationUnit.MILILITERS_PER_LITER) {
                    return this.concentration * 1000;
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

                else if (objConcentrationUnit == ConcentrationUnit.MILILITERS_PER_LITER) {
                    return this.concentration / 10;
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

            case ConcentrationUnit.MILILITERS_PER_LITER:
                if (objConcentrationUnit == ConcentrationUnit.MILIGRAMS_PER_LITER) {
                    return (this.concentration / this.densityGramsPerMiliLiter) / 1000;
                }

                else if (objConcentrationUnit == ConcentrationUnit.PERCENTAGE || objConcentrationUnit == ConcentrationUnit.GAY_LUSSAC) {
                    return this.concentration * 10;
                }

                else if (objConcentrationUnit == ConcentrationUnit.PARTS_PER_MILION) {
                    return this.concentration / 1000;
                }
                
                break;
        }
        
        throw new IllegalArgumentException(
            "Unsupported conversion: " + this.concentrationUnit + " --> " + targetUnit
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
