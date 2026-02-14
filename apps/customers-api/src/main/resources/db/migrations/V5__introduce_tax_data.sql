CREATE TABLE IF NOT EXISTS sales.customer_tax_data (
    last_search_date DATE NOT NULL,
    activities_start_date DATE NOT NULL,
    registration_status  VARCHAR(20) NOT NULL ,
    status_description VARCHAR(255),
    cnae_number VARCHAR(10) NOT NULL,
    customer_id UUID,

    CONSTRAINT fk_customer_tax_data_customer_id
        FOREIGN KEY (customer_id) REFERENCES sales.customers(id)
        ON DELETE CASCADE
);
