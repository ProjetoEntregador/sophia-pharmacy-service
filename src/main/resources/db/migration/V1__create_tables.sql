CREATE TABLE tb_users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255),
    provider VARCHAR(50)
);

CREATE TABLE tb_pharmacies (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(50),
    city VARCHAR(255),
    address VARCHAR(255),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION
);

CREATE TABLE tb_permissions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    pharmacy_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL,

    CONSTRAINT fk_permission_user
        FOREIGN KEY (user_id)
            REFERENCES tb_users(id),

    CONSTRAINT fk_permission_pharmacy
        FOREIGN KEY (pharmacy_id)
            REFERENCES tb_pharmacies(id)
);

CREATE TABLE tb_invitations (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    token VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,
    expiration TIMESTAMP NOT NULL,
    pharmacy_id BIGINT,
    invited_by BIGINT,

    CONSTRAINT fk_invitation_pharmacy
        FOREIGN KEY (pharmacy_id)
            REFERENCES tb_pharmacies(id),

    CONSTRAINT fk_invitation_user
        FOREIGN KEY (invited_by)
            REFERENCES tb_users(id)
);
