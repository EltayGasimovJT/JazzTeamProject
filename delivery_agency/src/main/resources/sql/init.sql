USE jazzteamproject;

SHOW TABLES;

CREATE TABLE clients
(
    id           bigint(11)         NOT NULL primary key auto_increment,
    name         varchar(50)        NULL,
    surname      VARCHAR(50)        NULL,
    passportId   VARCHAR(80) unique NULL,
    phoneNumber VARCHAR(50)        NULL
);

CREATE TABLE orderStateChangeType
(
    id            bigint(10)   NOT NULL primary key auto_increment,
    changedState VARCHAR(100) NOT NULL
);

CREATE TABLE roles
(
    id   bigint(11)  NOT NULL primary key auto_increment,
    role varchar(50) NULL
);

CREATE TABLE coefficientForPriceCalculation
(
    id                  bigint(11)  NOT NULL primary key auto_increment,
    parcelSizeLimit   INT         NOT NULL,
    countryCoefficient DOUBLE      NOT NULL,
    country             varchar(50) NULL
);

CREATE TABLE parcelParameters
(
    id     bigint(10) NOT NULL primary key auto_increment,
    weight int(10)    NOT NULL,
    height int(10)    NOT NULL,
    length int(10)    NOT NULL,
    width  int(10)    NOT NULL
);

CREATE TABLE abstractLocation
(
    id bigint(10) NOT NULL primary key auto_increment
);

CREATE TABLE orderState
(
    id    bigint(10)   NOT NULL primary key auto_increment,
    state varchar(100) NULL
);

CREATE TABLE abstractBuilding
(
    id       bigint(10)  NOT NULL primary key auto_increment,
    location VARCHAR(50) NULL,
    CONSTRAINT
        FOREIGN KEY (id) REFERENCES abstractLocation (id)
);

CREATE TABLE warehouse
(
    id       bigint(10)  NOT NULL primary key auto_increment,
    location VARCHAR(50) NULL,
    CONSTRAINT
        FOREIGN KEY (id) REFERENCES abstractBuilding (id)
);

CREATE TABLE orderProcessingPoint
(
    id           bigint(10)  NOT NULL primary key auto_increment,
    location     VARCHAR(50) NULL,
    warehouseId bigint(11)  NOT NULL,
    CONSTRAINT
        FOREIGN KEY (warehouseId) REFERENCES warehouse (id),
    CONSTRAINT
        FOREIGN KEY (id) REFERENCES abstractBuilding (id)
);

CREATE TABLE voyage
(
    id                bigint(10)  NOT NULL primary key auto_increment,
    departurePoint   VARCHAR(50) NULL,
    destinationPoint VARCHAR(50) NULL,
    sendingTime      DATETIME    NOT NULL,
    arrivingTime     DATETIME    NOT NULL,
    CONSTRAINT
        FOREIGN KEY (id) REFERENCES abstractLocation (id)
);

CREATE TABLE users
(
    id                   bigint(11)  NOT NULL primary key auto_increment,
    name                 varchar(50) NULL,
    surname              VARCHAR(50) NULL,
    abstractBuildingId bigint(11)  NOT NULL,
    CONSTRAINT
        FOREIGN KEY (abstractBuildingId) REFERENCES abstractBuilding (id)
);

CREATE TABLE orders
(
    id                    bigint(10) NOT NULL primary key auto_increment,
    price                 DECIMAL,
    orderStateId        bigint     NOT NULL,
    parcelParametersId bigint     NOT NULL,
    senderId             bigint     NOT NULL,
    recipientId          bigint     NOT NULL,
    destinationPointId  bigint     NOT NULL,
    currentLocationId   bigint     NOT NULL,
    CONSTRAINT
        FOREIGN KEY (orderStateId) REFERENCES orderState (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT
        FOREIGN KEY (parcelParametersId) REFERENCES parcelParameters (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT
        FOREIGN KEY (senderId) REFERENCES clients (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT
        FOREIGN KEY (recipientId) REFERENCES clients (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT
        FOREIGN KEY (destinationPointId) REFERENCES orderProcessingPoint (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT
        FOREIGN KEY (currentLocationId) REFERENCES abstractLocation (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE orderHistory
(
    id          bigint(10)   NOT NULL primary key auto_increment,
    comment     VARCHAR(200) NULL,
    changedAt  DATETIME     NOT NULL,
    orderId    bigint       NOT NULL,
    userId     bigint       NOT NULL,
    changeType bigint       NOT NULL,
    CONSTRAINT
        FOREIGN KEY (userId) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT
        FOREIGN KEY (changeType) REFERENCES orderStateChangeType (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT
        FOREIGN KEY (orderId) REFERENCES orders (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE rolesAllowedWithdrawFromState
(
    orderStateId bigint NOT NULL,
    roleId        bigint NOT NULL,
    PRIMARY KEY (orderStateId, roleId),
    CONSTRAINT
        FOREIGN KEY (orderStateId) REFERENCES orderState (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT
        FOREIGN KEY (roleId) REFERENCES roles (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE userRoles
(
    userId bigint NOT NULL,
    roleId bigint NOT NULL,
    PRIMARY KEY (userId, roleId),
    CONSTRAINT
        FOREIGN KEY (userId) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT
        FOREIGN KEY (roleId) REFERENCES roles (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE rolesAllowedPutToState
(
    orderStateId bigint NOT NULL,
    roleId        bigint NOT NULL,
    PRIMARY KEY (orderStateId, roleId),
    CONSTRAINT
        FOREIGN KEY (orderStateId) REFERENCES orderState (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT
        FOREIGN KEY (roleId) REFERENCES roles (id) ON UPDATE CASCADE ON DELETE CASCADE
);