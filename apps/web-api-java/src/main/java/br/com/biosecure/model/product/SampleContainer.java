package br.com.biosecure.model.product;

import java.time.LocalDate;

public abstract class SampleContainer extends  Product {
    private final ClosingMethod closingMethod;
    private final SterilizationMethod sterilizationMethod;
    private final Material material;
    private final double capacity;

    public SampleContainer(String name, double price, String manufacturer, SterilizationMethod sterilizationMethod, String batchNumber, LocalDate expirationDate, PackagingType packagingType, MeasureUnity measureUnity, int qtdPerPackage, ClosingMethod closingMethod, Material materialType, double capacity) {
        super(name, price, manufacturer, batchNumber, expirationDate, packagingType, measureUnity, qtdPerPackage);

        if (capacity < 1) {
            throw new InvalidProductAttributeException("capacity");
        }

        validateBioSafetyRules(materialType, sterilizationMethod);

        this.sterilizationMethod = sterilizationMethod;
        this.closingMethod = closingMethod;
        this.material = materialType;
        this.capacity = capacity;
    }

    private void validateBioSafetyRules(Material material, SterilizationMethod sterilization) {
        if (!material.isSupportsAutoclave() && sterilization == SterilizationMethod.AUTOCLAVE) {
            throw new InvalidProductAttributeException("material and sterilization method (autoclave) has incoherent");
        }
    }

    public enum Material {
        PP("Polypropylene", true),
        PS("Polystyrene", false),
        PE("Polyethylene", false),
        PC("Polycarbonate", true),
        BOROSILICATE_GLASS("Borosilicate Glass", true);

        private final String commercialName;
        private final boolean supportsAutoclave;

        Material(String commercialName, boolean supportsAutoclave) {
            this.commercialName = commercialName;
            this.supportsAutoclave = supportsAutoclave;
        }

        public String getCommercialName() {
            return commercialName;
        }

        public boolean isSupportsAutoclave() {
            return supportsAutoclave;
        }
    }

    public enum ClosingMethod {
        SCREW_CAP_SIMPLE(true),
        SCREW_WITH_FILTER(false),
        SCREW_CAP_ORING(true),
        SNAP_CAP(true),
        WIRE_TAB(true),
        COTTON_STOPPER(false),
        CELLULOSE_STOPPER(false),
        ZIP_LOCK(true),
        HEAT_SEALABLE(true),
        LID_OVERLAY(false);

        private final boolean hermetic;

        ClosingMethod(boolean isHermetic) {
            this.hermetic = isHermetic;
        }

        public boolean isHermetic() {
            return hermetic;
        }
    }

    public enum SterilizationMethod {
        GAMMA_RAYS,
        E_BEAM,
        AUTOCLAVE,
        ETHYLENE_OXIDE,
        NO_STERILE;
    }

    public SterilizationMethod getSterilizationMethod() {
        return sterilizationMethod;
    }

    public ClosingMethod getClosingMethod() {
        return closingMethod;
    }

    public Material getMaterial() {
        return material;
    }

    public boolean isSterile() {
        return this.sterilizationMethod != SterilizationMethod.NO_STERILE;
    }

    public double getCapacity() {
        return capacity;
    }
}
