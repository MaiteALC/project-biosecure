BEGIN;
ALTER TABLE sales.customer_financial_data
    DROP COLUMN activities_start_date;
COMMIT;