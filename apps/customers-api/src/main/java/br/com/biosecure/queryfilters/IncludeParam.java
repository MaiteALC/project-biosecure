package br.com.biosecure.queryfilters;

import br.com.biosecure.model.Customer;
import br.com.biosecure.model.Address;
import br.com.biosecure.model.TaxData;
import br.com.biosecure.model.FinancialData;
import br.com.biosecure.dto.response.CustomerSummaryDto;

/**
 * This enum defines the accepted values for the {@code include} query parameter used in Customer-related endpoints.
 * <p>
 * This enum allows for <strong>Payload Optimization</strong>, letting the clients
 * decide which nested resources they want to include in the response.
 * </p>
 * @see Customer
 * @see Address
 * @see TaxData
 * @see FinancialData
 *
 * @since 1.0.0
 *
 * @author MaiteALC
 */
public enum IncludeParam {

    /** Include the complete entity and all its value objects */
    FULL,

    /** Include only basic fields present in {@link CustomerSummaryDto} */
    SUMMARIZED,

    /** Include the {@link Address} value object in the response */
    ADDRESS,

    /** Include the {@link TaxData} value object in the response */
    TAX_DATA,

    /** Include the {@link FinancialData} entity in the response */
    FINANCIAL_DATA
}
