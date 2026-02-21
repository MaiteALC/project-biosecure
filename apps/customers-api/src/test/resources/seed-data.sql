CREATE SCHEMA IF NOT EXISTS sales;

CREATE TABLE IF NOT EXISTS sales.customers (
  id UUID PRIMARY KEY,
  corporate_name VARCHAR(100) NOT NULL,
  cnpj_number VARCHAR(18) NOT NULL UNIQUE,
  email VARCHAR(60) NOT NULL,
  registration_date DATE DEFAULT CURRENT_DATE
);

CREATE TABLE IF NOT EXISTS sales.customer_addresses (
  customer_id UUID NOT NULL,
  state_name VARCHAR(2) NOT NULL,
  city_name VARCHAR(50) NOT NULL,
  neighborhood_name VARCHAR(50) NOT NULL,
  street_name VARCHAR(50) NOT NULL,
  address_number VARCHAR(7),
  postal_code VARCHAR(9) NOT NULL,
  delivery_address BOOLEAN NOT NULL DEFAULT FALSE,

  CONSTRAINT uq_customer_address_unique
    UNIQUE (customer_id, postal_code, street_name, address_number),

  CONSTRAINT fk_client_addresses_client_id
    FOREIGN KEY (customer_id) REFERENCES sales.customers(id)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS sales.customer_financial_data (
  customer_id UUID NOT NULL,
  activities_start_date DATE NOT NULL,
  cnae_number VARCHAR(9) NOT NULL,
  cnpj_number VARCHAR(18) NOT NULL UNIQUE,
  share_capital NUMERIC(19,2),
  total_credit NUMERIC(19,2),
  utilized_credit NUMERIC(19,2),

  CONSTRAINT pk_client_id
    PRIMARY KEY (customer_id),

  CONSTRAINT fk_client_financial_data_client_id
    FOREIGN KEY (customer_id) REFERENCES sales.customers(id)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS sales.customer_tax_data (
  last_search_date TIMESTAMP NOT NULL,
  activities_start_date DATE NOT NULL,
  registration_status  VARCHAR(20) NOT NULL ,
  status_description VARCHAR(255),
  cnae_number VARCHAR(10) NOT NULL,
  customer_id UUID,

  CONSTRAINT fk_customer_tax_data_customer_id
    FOREIGN KEY (customer_id) REFERENCES sales.customers(id)
    ON DELETE CASCADE
);

-- Defining test customers
INSERT INTO sales.customers
VALUES ('73064e16-e2bb-4aae-b7ed-05ecf294611a', 'TOTVS S.A', '53.113.791/0001-22', 'test@corporation1.com', current_date);

INSERT INTO sales.customers
VALUES ('a4439687-bee8-41c5-ac30-ffafd38ada70', 'SQUARE IT TECNOLOGIA DA INFORMACAO LTDA', '40.052.477/0001-35', 'test@corporation2.com', current_date);

INSERT INTO sales.customers
VALUES ('bfa5bca0-f671-476c-8b96-4736f28219b8', 'ORACLE DO BRASIL LTDA', '59.456.277/0001-76', 'test@corporation3.com', current_date);

INSERT INTO sales.customers
VALUES ('2141d74c-9daf-40a4-af39-99d8fd364036', 'SAMSUNG ELETRONICA LTDA', '00.280.273/0007-22', 'test@corporation4.com', current_date);

INSERT INTO sales.customers
VALUES ('4e1ee8a3-309b-406d-88bd-f2b674bc3dba', 'IZU COMERCIO DE ELETRONICOS LTDA', '34.852.937/0001-07', 'test@corporation5.com', current_date);


-- Setting random addresses for the customers
INSERT INTO sales.customer_addresses
VALUES ('73064e16-e2bb-4aae-b7ed-05ecf294611a', 'SP', 'São Paulo', 'Casa Verde', 'Avenida Braz Leme', '1000', '02511-000', false);

INSERT INTO sales.customer_addresses
VALUES ('73064e16-e2bb-4aae-b7ed-05ecf294611a', 'ES', 'Vila Velha', 'Velha', 'Vila', '542', '12345-678', false);

INSERT INTO sales.customer_addresses
VALUES ('a4439687-bee8-41c5-ac30-ffafd38ada70', 'SP', 'Sao Paulo', 'Se', 'Praca Da Se', '465', '01001-000', true);

INSERT INTO sales.customer_addresses
VALUES ('bfa5bca0-f671-476c-8b96-4736f28219b8', 'SP', 'São Paulo', 'Vila São Francisco', 'Rua Doutor José Áureo Bustamante', '455', '04710-090', true);

INSERT INTO sales.customer_addresses
VALUES ('2141d74c-9daf-40a4-af39-99d8fd364036', 'SP', 'São Paulo', 'Vila Cordeiro', 'Avenida Doutor Chucri Zaidan', '1240', '04711-130', true);

INSERT INTO sales.customer_addresses
VALUES ('2141d74c-9daf-40a4-af39-99d8fd364036', 'PE', 'Floresta', 'Floresta', 'R. Manoel Cornelio da Silva', '12','56400-000', true);

INSERT INTO sales.customer_addresses
VALUES ('4e1ee8a3-309b-406d-88bd-f2b674bc3dba', 'SP', 'São Paulo', 'Bela Vista', 'Avenida Paulista', '171', '01311-000', false);


-- Setting tax data for customers
INSERT INTO sales.customer_tax_data
VALUES (current_date, current_date, 'ACTIVE', 'none', '6201-5/01', '73064e16-e2bb-4aae-b7ed-05ecf294611a');

INSERT INTO sales.customer_tax_data
VALUES (current_date, current_date, 'ACTIVE', 'none', '6202-3/00', 'a4439687-bee8-41c5-ac30-ffafd38ada70');

INSERT INTO sales.customer_tax_data
VALUES (current_date, current_date, 'ACTIVE', 'none', '6209-1/00', 'bfa5bca0-f671-476c-8b96-4736f28219b8');

INSERT INTO sales.customer_tax_data
VALUES (current_date, current_date, 'ACTIVE', 'none', '4649-4/02', '2141d74c-9daf-40a4-af39-99d8fd364036');

INSERT INTO sales.customer_tax_data
VALUES (current_date, current_date, 'ACTIVE', 'none', '4753-9/00', '4e1ee8a3-309b-406d-88bd-f2b674bc3dba');


-- Setting financial data for the customers
INSERT INTO sales.customer_financial_data
VALUES ('73064e16-e2bb-4aae-b7ed-05ecf294611a', '1983-12-13', '6201501', '53113791000122', 2962584600.00, 888775380.00, 120488.00);

INSERT INTO sales.customer_financial_data
VALUES ('a4439687-bee8-41c5-ac30-ffafd38ada70', '2020-12-08', '6202300', '40052477000135', 1000.00, 300.00, 100.00);

INSERT INTO sales.customer_financial_data
VALUES ('bfa5bca0-f671-476c-8b96-4736f28219b8', '1988-09-01', '6209100', '59456277000176', 344790530.00, 103437159.00, 2068772.5);

INSERT INTO sales.customer_financial_data
VALUES ('2141d74c-9daf-40a4-af39-99d8fd364036', '2004-04-30', '4649402', '00280273000722', 1038408450.00, 415363380.00, 1236122.3);

INSERT INTO sales.customer_financial_data
VALUES ('4e1ee8a3-309b-406d-88bd-f2b674bc3dba', '2019-09-12', '4753900', '34852937000107', 500000.00, 180000.00, 38764.00)
