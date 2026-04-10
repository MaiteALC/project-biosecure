CREATE SCHEMA IF NOT EXISTS sales;

CREATE TABLE IF NOT EXISTS sales.clients (
    id UUID PRIMARY KEY,
    corporate_name VARCHAR(150) NOT NULL,
    cnpj_number VARCHAR(18) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL,
    registration_date DATE DEFAULT CURRENT_DATE
);

CREATE TABLE IF NOT EXISTS sales.client_addresses (
    client_id UUID NOT NULL,
    state_name VARCHAR(2) NOT NULL,
    city_name VARCHAR(50) NOT NULL,
    neighborhood_name VARCHAR(50) NOT NULL,
    street_name VARCHAR(50) NOT NULL,
    address_number VARCHAR(7),
    postal_code VARCHAR(9) NOT NULL,
    delivery_address BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_client_addresses_client_id
    FOREIGN KEY (client_id) REFERENCES sales.clients(id),

    CONSTRAINT uq_client_address_unique
    UNIQUE (client_id, postal_code, street_name, address_number)
);

CREATE TABLE IF NOT EXISTS sales.client_financial_data (
    client_id UUID NOT NULL,
    activities_start_date DATE NOT NULL,
    cnae_number VARCHAR(9) NOT NULL,
    cnpj_number VARCHAR(18) NOT NULL UNIQUE,
    share_capital NUMERIC(19,2),
    total_credit NUMERIC(19,2),
    utilized_credit NUMERIC(19,2)
)
