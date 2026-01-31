package br.com.biosecure.model;

import br.com.biosecure.utils.NumberUtils;
import br.com.biosecure.utils.ErrorAggregator;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LabCoat extends PPE {
    @Enumerated(EnumType.STRING)
    private FabricType fabricType;
    private int grammage;
    @Enumerated(EnumType.STRING)
    private CuffStyle cuffStyle;
    @Enumerated(EnumType.STRING)
    private CollarType collarType;

    private LabCoat(LabCoatBuilder builder) {
        super(builder);

        this.fabricType = builder.fabricType;
        this.grammage = builder.grammage;
        this.cuffStyle = builder.cuffStyle;
        this.collarType = builder.collarType;
    }

    public static LabCoatBuilder builder() {
        return new LabCoatBuilder();
    }

    @Setter
    @Accessors(fluent = true, chain = true)
    public static final class LabCoatBuilder extends PpeBuilder<LabCoat, LabCoatBuilder> {
        private FabricType fabricType;
        private int grammage;
        private CuffStyle cuffStyle;
        private CollarType collarType;

        @Override
        protected LabCoatBuilder self() {
            return this;
        }

        @Override
        public LabCoat build() {
            NumberUtils.validateNumericalAttribute(grammage, 20, "grammage (g/cm²)", 350, productNotification);

            ErrorAggregator.aggregateValidationExceptions(
                    List.of(
                            ErrorAggregator.verifyNull(fabricType, "fabric type"),
                            ErrorAggregator.verifyNull(cuffStyle, "cuff style"),
                            ErrorAggregator.verifyNull(collarType, "collar type")
                    ),
                    productNotification
            );

            if (productNotification.hasErrors()) {
                throw new InvalidProductAttributeException(productNotification.getErrors());
            }

            return new LabCoat(this);
        }
    }

    @Getter
    @AllArgsConstructor
    public enum FabricType {
        COTTON_100("Cotton", "CT"),
        POLYESTER("Polyester", "PO"),
        POLYPROPYLENE("SPP", "PP"),
        MIX_COTTON_POLYESTER("Cotton and polyester", "CP");

        private final String commercialName;
        private final String code;
    }

    public enum CuffStyle {
        OPEN_CUFF,
        KNITTED_CUFF,
        ELASTIC_CUFF
    }

    public enum CollarType {
        V_NECK,
        HIGH_NECK
    }
}
