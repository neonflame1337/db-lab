CREATE USER developer WITH PASSWORD 'developer';
ALTER USER developer WITH SUPERUSER;
CREATE DATABASE db_postgres;
ALTER DATABASE db_postgres OWNER TO developer;

CREATE TYPE transaction_status AS ENUM (
    'created',
    'processing',
    'verifing',
    'success',
    'failed'
    );

CREATE EXTENSION pg_uuidv7;

CREATE TABLE user (
                      id UUID DEFAULT uuid_generate_v7() PRIMARY KEY,
                      first_name TEXT NOT NULL,
                      last_name TEXT NOT NULL,
                      is_active BOOLEAN NOT NULL,
                      created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                      updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
                      deleted_at TIMESTAMP
);


CREATE TABLE account (
                         id UUID DEFAULT uuid_generate_v7() PRIMARY KEY,
                         user_id UUID NOT NULL,
                         public_id TEXT NOT NULL UNIQUE,
                         created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                         updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
                         deleted_at TIMESTAMP,
                         CONSTRAINT fk_user_account FOREIGN KEY (user_id) REFERENCES user (id)
);


CREATE TABLE document (
                          id UUID DEFAULT uuid_generate_v7() PRIMARY KEY,
                          user_id UUID NOT NULL,
                          file_path TEXT NOT NULL,
                          is_verified BOOLEAN NOT NULL DEFAULT FALSE,
                          type TEXT NOT NULL,
                          number TEXT NOT NULL,
                          expired_at TIMESTAMP NOT NULL,
                          created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                          updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
                          deleted_at TIMESTAMP,
                          CONSTRAINT fk_user_document FOREIGN KEY (user_id) REFERENCES user (id)
);


CREATE TABLE transaction (
                             id UUID DEFAULT uuid_generate_v7() PRIMARY KEY,
                             initiator_user_id UUID NOT NULL,
                             from_account_id UUID NOT NULL,
                             to_account_id UUID NOT NULL,
                             status transaction_status NOT NULL DEFAULT 'created',
                             amount INTEGER,
                             metadata TEXT,
                             created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                             CONSTRAINT fk_user_transaction FOREIGN KEY (initiator_user_id) REFERENCES user (id),
                             CONSTRAINT fk_from_account_transaction FOREIGN KEY (from_account_id) REFERENCES account (id),
                             CONSTRAINT fk_to_account_transaction FOREIGN KEY (to_account_id) REFERENCES account (id)
);


CREATE INDEX idx_user_id_account ON account (user_id);
CREATE INDEX idx_user_id_transaction ON transaction (initiator_user_id);
CREATE INDEX idx_from_account_transaction ON transaction (from_account_id);
CREATE INDEX idx_to_account_transaction ON transaction (to_account_id);
CREATE INDEX idx_user_id_document ON document (user_id);
