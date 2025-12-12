package br.com.biosecure.builders;

import br.com.biosecure.model.product.PPE;
import br.com.biosecure.model.product.PPE.Size;
 
abstract class BasePpeBuilder<T extends BasePpeBuilder<T, P>, P extends PPE> extends BaseProductBuilder<T, P> {
    // Specific attributes of Personal Protective Equipment (PPE)
    protected Size size = Size.UNIVERSAL;
    protected String certificateOfApproval = "CA-54967";
    protected boolean isDisposable = false;

    public T withSize(Size size) {
        this.size = size;

        return self();
    }

    public T withCertificateOfApproval(String certificateOfApproval) {
        this.certificateOfApproval = certificateOfApproval;

        return self();
    }

    public T withDisposable(boolean isDisposable) {
        this.isDisposable = isDisposable;

        return self();
    }
}
