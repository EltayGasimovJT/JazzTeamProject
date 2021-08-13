USE jazzteamproject;

show tables;

CREATE TABLE clients
(
    id           bigint(11)         NOT NULL primary key auto_increment,
    name         varchar(50)        NULL,
    surname      VARCHAR(50)        NULL,
    passportId   VARCHAR(80) unique NULL,
    phone_number VARCHAR(50)        NULL
);

CREATE TABLE order_state_change_type
(
    id            bigint(10)   NOT NULL primary key auto_increment,
    changed_state VARCHAR(100) NOT NULL
);

CREATE TABLE roles
(
    id   bigint(11)  NOT NULL primary key auto_increment,
    role varchar(50) NULL
);

CREATE TABLE coefficient_for_price_calculation
(
    id                  bigint(11)  NOT NULL primary key auto_increment,
    parcel_size_limit   INT         NOT NULL,
    country_coefficient DOUBLE      NOT NULL,
    country             varchar(50) NULL
);

CREATE TABLE parcel_parameters
(
    id     bigint(10) NOT NULL primary key auto_increment,
    weight int(10)    NOT NULL,
    height int(10)    NOT NULL,
    length int(10)    NOT NULL,
    width  int(10)    NOT NULL
);

CREATE TABLE abstract_location
(
    id bigint(10) NOT NULL primary key auto_increment
);

CREATE TABLE order_state
(
    id    bigint(10)   NOT NULL primary key auto_increment,
    state varchar(100) NULL
);

CREATE TABLE abstract_building
(
    id       bigint(10)  NOT NULL primary key auto_increment,
    location VARCHAR(50) NULL,
    CONSTRAINT
        FOREIGN KEY (id) REFERENCES abstract_location (id)
);

CREATE TABLE warehouse
(
    id       bigint(10)  NOT NULL primary key auto_increment,
    location VARCHAR(50) NULL,
    CONSTRAINT
        FOREIGN KEY (id) REFERENCES abstract_building (id)
);

CREATE TABLE order_processing_point
(
    id           bigint(10)  NOT NULL primary key auto_increment,
    location     VARCHAR(50) NULL,
    warehouse_id bigint(11)  NOT NULL,
    CONSTRAINT
        FOREIGN KEY (warehouse_id) REFERENCES warehouse (id),
    CONSTRAINT
        FOREIGN KEY (id) REFERENCES abstract_building (id)
);

CREATE TABLE voyage
(
    id                bigint(10)  NOT NULL primary key auto_increment,
    depatrure_point   VARCHAR(50) NULL,
    destination_point VARCHAR(50) NULL,
    sending_time      DATETIME    NOT NULL,
    arriving_time     DATETIME    NOT NULL,
    CONSTRAINT
        FOREIGN KEY (id) REFERENCES abstract_location (id)
);

CREATE TABLE users
(
    id                   bigint(11)  NOT NULL primary key auto_increment,
    name                 varchar(50) NULL,
    surname              VARCHAR(50) NULL,
    abstract_building_id bigint(11)  NOT NULL,
    CONSTRAINT
        FOREIGN KEY (abstract_building_id) REFERENCES abstract_building (id)
);

CREATE TABLE orders
(
    id                    bigint(10) NOT NULL primary key auto_increment,
    sending_time          DATETIME   NOT NULL,
    price                 DECIMAL,
    order_state_id        bigint     NOT NULL,
    parclel_parameters_id bigint     NOT NULL,
    client_id             bigint     NOT NULL,
    destination_point_id  bigint     NOT NULL,
    current_location_id   bigint     NOT NULL,
    CONSTRAINT
        FOREIGN KEY (order_state_id) REFERENCES order_state (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT
        FOREIGN KEY (parclel_parameters_id) REFERENCES parcel_parameters (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT
        FOREIGN KEY (client_id) REFERENCES clients (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT
        FOREIGN KEY (destination_point_id) REFERENCES order_processing_point (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT
        FOREIGN KEY (current_location_id) REFERENCES abstract_location (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE order_history
(
    id          bigint(10)   NOT NULL primary key auto_increment,
    comment     VARCHAR(200) NULL,
    changed_at  DATETIME     NOT NULL,
    order_id    bigint       NOT NULL,
    user_id     bigint       NOT NULL,
    change_type bigint       NOT NULL,
    CONSTRAINT
        FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT
        FOREIGN KEY (change_type) REFERENCES order_state_change_type (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT
        FOREIGN KEY (order_id) REFERENCES orders (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE roles_allowed_withdraw_from_state
(
    order_state_id bigint NOT NULL,
    role_id        bigint NOT NULL,
    PRIMARY KEY (order_state_id, role_id),
    CONSTRAINT
        FOREIGN KEY (order_state_id) REFERENCES order_state (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT
        FOREIGN KEY (role_id) REFERENCES roles (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE user_roles
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT
        FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT
        FOREIGN KEY (role_id) REFERENCES roles (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE roles_allowed_put_to_state
(
    order_state_id bigint NOT NULL,
    role_id        bigint NOT NULL,
    PRIMARY KEY (order_state_id, role_id),
    CONSTRAINT
        FOREIGN KEY (order_state_id) REFERENCES order_state (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT
        FOREIGN KEY (role_id) REFERENCES roles (id) ON UPDATE CASCADE ON DELETE CASCADE
);