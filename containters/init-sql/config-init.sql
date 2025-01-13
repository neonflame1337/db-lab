CREATE USER developer WITH PASSWORD 'developer';
ALTER USER developer WITH SUPERUSER;
CREATE DATABASE db_postgres;
ALTER DATABASE db_postgres OWNER TO developer;

CREATE TABLE users (
                       id uuid PRIMARY KEY,
                       first_name TEXT NOT NULL,
                       last_name TEXT NOT NULL,
                       is_active BOOLEAN NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                       updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
                       deleted_at TIMESTAMP
);


CREATE TABLE accounts (
                          id uuid  PRIMARY KEY,
                          user_id uuid NOT NULL,
                          amount INTEGER,
                          public_id TEXT NOT NULL UNIQUE,
                          created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                          updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
                          deleted_at TIMESTAMP,
                          CONSTRAINT fk_user_account FOREIGN KEY (user_id) REFERENCES users (id)
);


CREATE TABLE documents (
                           id uuid PRIMARY KEY,
                           user_id uuid NOT NULL,
                           file_path TEXT NOT NULL,
                           is_verified BOOLEAN NOT NULL DEFAULT FALSE,
                           type TEXT NOT NULL,
                           number TEXT NOT NULL,
                           expired_at TIMESTAMP NOT NULL,
                           created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                           updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
                           deleted_at TIMESTAMP,
                           CONSTRAINT fk_user_document FOREIGN KEY (user_id) REFERENCES users (id)
);


CREATE TABLE transactions (
                              id uuid PRIMARY KEY,
                              initiator_user_id uuid NOT NULL,
                              from_account_id uuid NOT NULL,
                              to_account_id uuid NOT NULL,
                              status varchar NOT NULL DEFAULT 'created',
                              amount INTEGER,
                              metadata TEXT,
                              created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                              CONSTRAINT fk_user_transaction FOREIGN KEY (initiator_user_id) REFERENCES users (id),
                              CONSTRAINT fk_from_account_transaction FOREIGN KEY (from_account_id) REFERENCES accounts (id),
                              CONSTRAINT fk_to_account_transaction FOREIGN KEY (to_account_id) REFERENCES accounts (id)
);


CREATE INDEX idx_user_id_account ON accounts (user_id);
CREATE INDEX idx_user_id_transaction ON transactions (initiator_user_id);
CREATE INDEX idx_from_account_transaction ON transactions (from_account_id);
CREATE INDEX idx_to_account_transaction ON transactions (to_account_id);
CREATE INDEX idx_user_id_document ON documents (user_id);