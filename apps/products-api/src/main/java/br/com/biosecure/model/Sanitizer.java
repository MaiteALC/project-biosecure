package br.com.biosecure.model;

import br.com.biosecure.utils.ErrorAggregator;
import br.com.biosecure.utils.NumberUtils;
import br.com.biosecure.utils.StringUtils;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Sanitizer extends Product {
    @OneToMany(mappedBy = "sanitizer", cascade = CascadeType.ALL)
    private List<Ingredient> composition;
    @Enumerated(EnumType.STRING)
    private PhysicalForm physicalForm;
    private String registryNumber;
    private String useIndications;
    private double phLevel;
    private boolean requiresDilution;
    private double densityGramsPerMilliLiter;
    private boolean flammable;
    @Enumerated(EnumType.STRING)
    private Ingredient.ChemicalFamily mainChemicalFamily;

    private Sanitizer(SanitizerBuilder builder) {
        super(builder);

        this.composition = builder.composition;
        this.physicalForm = builder.physicalForm;
        this.registryNumber = builder.registryNumber;
        this.useIndications = builder.useIndications;
        this.phLevel = builder.phLevel;
        this.requiresDilution = builder.requiresDilution;
        this.densityGramsPerMilliLiter = builder.densityGramsPerMilliLiter;
        this.flammable = builder.flammable;
        this.mainChemicalFamily = builder.mainChemicalFamily;
    }

    public static SanitizerBuilder builder() {
        return new SanitizerBuilder();
    }

    @Setter
    @Accessors(fluent = true, chain = true)
    public static final class SanitizerBuilder extends ProductBuilder<Sanitizer, SanitizerBuilder> {
        private List<Ingredient> composition;
        private PhysicalForm physicalForm;
        private String registryNumber;
        private String useIndications;
        private double phLevel;
        private boolean requiresDilution;
        private double densityGramsPerMilliLiter;
        private boolean flammable;
        private Ingredient.ChemicalFamily mainChemicalFamily;

        @Override
        protected SanitizerBuilder self() {
            return this;
        }

        @Override
        public Sanitizer build() {
            if (composition == null || composition.isEmpty()) {
                productNotification.addError("active ingredients", "active ingredients list mustn't be null/empty");
            }
            else {
                double sum = 0;
                for (Ingredient ingredient : composition) {
                    sum += ingredient.getConcentrationPercentual();
                }

                if (sum > 100) {
                    productNotification.addError("active ingredients", "total concentration of all active ingredients cannot be greater than 100%");
                }
            }

            ErrorAggregator.verifyNull(physicalForm, "form", productNotification);

            ErrorAggregator.verifyNull(mainChemicalFamily, "main chemical family", productNotification);

            StringUtils.validateString(registryNumber, 8, "register number", 14, true, productNotification);

            NumberUtils.validateNumericalAttribute(phLevel, 0, "ph level", 14, productNotification);

            NumberUtils.validateNumericalAttribute(densityGramsPerMilliLiter, 0, "density (g/mL)", 23, productNotification); // 23 is (a bit greater than) the value of density of the "Osmium", the most dense substance in the world

            StringUtils.validateString(useIndications, "use indications", true, productNotification);

            if (productNotification.hasErrors()) {
                throw new InvalidProductAttributeException(productNotification.getErrors());
            }

            return new Sanitizer(this);
        }
    }

    @Getter
    @AllArgsConstructor
    public enum PhysicalForm {
        LIQUID("LQ"),
        SOLID("SO"),
        POWDER("PD"),
        GEL("GL"),
        SPRAY("SP"),
        FOAM("FM"),
        WIPES("WP");

        private final String code;
    }
}
