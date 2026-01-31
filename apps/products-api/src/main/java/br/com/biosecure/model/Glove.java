package br.com.biosecure.model;

import br.com.biosecure.utils.NumberUtils;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.Accessors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Glove extends PPE {
    private boolean powderFree;
    private boolean longBarrel;
    @Enumerated(EnumType.STRING)
    private GloveMaterial material;
    private boolean textured;
    private double thicknessMils;

    private Glove(GloveBuilder builder) {
        super(builder);

        this.powderFree = builder.powderFree;
        this.longBarrel = builder.longBarrel;
        this.material = builder.material;
        this.textured = builder.textured;
        this.thicknessMils = builder.thicknessMils;
    }

    public static GloveBuilder builder() {
        return new GloveBuilder();
    }

    @Setter
    @Accessors(fluent = true, chain = true)
    public static final class GloveBuilder extends PpeBuilder<Glove, GloveBuilder> {
        private boolean powderFree;
        private boolean longBarrel;
        private GloveMaterial material;
        private boolean textured;
        private double thicknessMils;

        @Override
        protected GloveBuilder self() {
            return this;
        }

        @Override
        public Glove build() {

            if (material == null) {
                productNotification.addError("glove material", "glove material mustn't be null");
            }

            NumberUtils.validateNumericalAttribute(thicknessMils, 3, "thickness (mils)", 10, productNotification);

            if (productNotification.hasErrors()) {
                throw new InvalidProductAttributeException(productNotification.getErrors());
            }

            return new Glove(this);
        }
    }

    @Getter
    @AllArgsConstructor
    public enum GloveMaterial {
        LATEX("LA"),
        NITRILE("NT"), // The gold standard for labs
        VINYL("VI"),
        NEOPRENE("NP");

        final String code;
    }
}
