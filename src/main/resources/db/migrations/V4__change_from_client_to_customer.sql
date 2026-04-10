ALTER TABLE sales.clients RENAME TO customers;

ALTER TABLE sales.client_addresses RENAME TO customer_addresses;

ALTER TABLE sales.client_financial_data RENAME TO customer_financial_data;

ALTER TABLE sales.customer_addresses
    RENAME COLUMN client_id TO customer_id;

ALTER TABLE sales.customer_financial_data
    RENAME COLUMN client_id TO customer_id;
