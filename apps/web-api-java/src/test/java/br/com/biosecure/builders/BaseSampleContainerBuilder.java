package br.com.biosecure.builders;

import br.com.biosecure.model.product.SampleContainer;
import br.com.biosecure.model.product.SampleContainer.*;

abstract class BaseSampleContainerBuilder<T extends BaseSampleContainerBuilder<T, P>, P extends SampleContainer> extends BaseProductBuilder<T, P> {
    // Specific attributes of Sample Container
    protected ClosingMethod closingMethod = ClosingMethod.CELLULOSE_STOPPER;
    protected SterilizationMethod sterilizationMethod = SterilizationMethod.ETHYLENE_OXIDE;
    protected Material material = Material.BOROSILICATE_GLASS;
    protected double capacityMiliLiters = 1;

    public T withClosingMethod(ClosingMethod closingMethod) {
        this.closingMethod = closingMethod;

        return self();
    }

    public T withSterilizationMethod(SterilizationMethod sterilizationMethod) {
        this.sterilizationMethod = sterilizationMethod;

        return self();
    }

    public T withMaterial(Material material) {
        this.material = material;

        return self();
    }

    public T withCapacityMiliLiters(double capacity) {
        this.capacityMiliLiters = capacity;

        return self();
    }
}
