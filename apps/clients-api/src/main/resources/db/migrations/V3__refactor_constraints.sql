BEGIN;

ALTER TABLE sales.client_financial_data
    ADD CONSTRAINT pk_client_id
        PRIMARY KEY (client_id),

    ADD CONSTRAINT fk_client_financial_data_client_id
        FOREIGN KEY (client_id) REFERENCES sales.clients(id)
        ON DELETE CASCADE;

COMMIT;


BEGIN;

ALTER TABLE sales.client_addresses
    DROP CONSTRAINT fk_client_addresses_client_id;

ALTER TABLE sales.client_addresses
    ADD CONSTRAINT fk_client_addresses_client_id
        FOREIGN KEY (client_id) REFERENCES sales.clients(id)
        ON DELETE CASCADE;

COMMIT;


BEGIN;

ALTER TABLE sales.clients
    RENAME CONSTRAINT clients_pkey TO pk_client;

COMMIT;
