BEGIN;

ALTER TABLE sales.customer_tax_data
    ALTER COLUMN last_search_date TYPE TIMESTAMP;

COMMIT;