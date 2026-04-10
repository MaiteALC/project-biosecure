package br.com.biosecure.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Defines the accepted values for the registration status of a {@link TaxData}
 * belonging to a {@link Customer} entity.
 * <p>
 * These statuses directly map to the official classifications provided by the
 * <strong>Brazilian Federal Revenue Service</strong> (Receita Federal do Brasil - RFB).
 * <p>
 * In the context of the BioSecure domain, evaluating this status is critical to
 * ensure that only legally compliant and active corporations are permitted to
 * be registered and operate within the system.
 *
 * @see TaxData
 * @see Customer
 *
 * @since 1.0.0
 * @author MaiteALC
 */
@AllArgsConstructor
@Getter
public enum RegistrationStatus {

    /** The legal entity is fully operational and in compliance with its
     * basic registration requirements.
     */
    ACTIVE("ATIVA"),

    /** The entity is active but has pending tax obligations, missing declarations,
     * or other compliance issues with the Federal Revenue.
     */
    ACTIVE_IRREGULAR("ATIVA NÃO REGULAR"),

    /** The entity's activities are temporarily halted. This can be voluntarily
     * requested by the company or enforced by the government due to investigations.
     */
    SUSPENDED("SUSPENSA"),

    /** The entity is deemed unfit for operations, typically because it failed
     * to submit mandatory tax and accounting statements for consecutive years.
     */
    UNFIT("INAPTA"),

    /** A broader status indicating non-compliance with statutory regulations,
     * often restricting the entity from issuing invoices or obtaining credit.
     */
    IRREGULAR("IRREGULAR"),

    /** The entity is inactive and has temporarily ceased its operational activities.
     */
    INACTIVE("INATIVA"),

    /** The legal entity is formally closed, dissolved, or extinct.
     */
    DISSOLVED("BAIXADA");

    private final String portugueseEquivalent;
}
