CREATE TABLE users
(
    id       BIGSERIAL,
    created  TIMESTAMP   NOT NULL DEFAULT timezone('UTC', now()),
    updated  TIMESTAMP,
    username VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(80) NOT NULL,
    email    VARCHAR(50) UNIQUE,
    status   VARCHAR(30),
    PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id      SERIAL,
    created TIMESTAMP   NOT NULL DEFAULT timezone('UTC', now()),
    updated TIMESTAMP,
    name    VARCHAR(50) NOT NULL,
    status  VARCHAR(30),
    PRIMARY KEY (id)
);

CREATE TABLE users_roles
(
    user_id BIGINT NOT NULL,
    role_id INT    NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE tasks
(
    id          BIGSERIAL,
    created     TIMESTAMP   NOT NULL DEFAULT timezone('UTC', now()),
    status      VARCHAR(255),
    updated     TIMESTAMP ,
    description TEXT,
    task_status VARCHAR(255),
    title       TEXT NOT NULL,
    user_id     BIGINT REFERENCES users (id)
);

INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

INSERT INTO users (username, password, email)
VALUES ('user', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user@mail.com'),
       ('admin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'admin@mail.com');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (2, 2);