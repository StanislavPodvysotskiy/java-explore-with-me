create table if not exists CATEGORIES
(
    ID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NAME VARCHAR(50) NOT NULL UNIQUE
);

create table if not exists USERS
(
    ID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    EMAIL VARCHAR(50) NOT NULL UNIQUE,
    NAME VARCHAR(50) NOT NULL
);

create table if not exists LOCATIONS
(
    ID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    LAT INTEGER NOT NULL,
    LON INTEGER NOT NULL
);

create table if not exists EVENTS
(
    ID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ANNOTATION VARCHAR(2000) NOT NULL,
    CATEGORY_ID INTEGER NOT NULL,
    CREATED TIMESTAMP NOT NULL,
    DESCRIPTION VARCHAR(7000) NOT NULL,
    EVENT_DATE TIMESTAMP NOT NULL,
    LAT FLOAT,
    LON FLOAT,
    PAID BOOLEAN NOT NULL,
    PARTICIPANT_LIMIT INTEGER NOT NULL,
    PUBLISHED_ON TIMESTAMP,
    REQUEST_MODERATION BOOLEAN NOT NULL,
    STATE VARCHAR(25),
    TITLE VARCHAR(120) NOT NULL,
    USER_ID INTEGER NOT NULL,
    RATE FLOAT,
    constraint EVENTS_CATEGORY_ID_FK
        foreign key (CATEGORY_ID) references CATEGORIES (ID),
    constraint EVENTS_USER_ID_FK
        foreign key (USER_ID) references USERS (ID)
);

create table if not exists COMPILATIONS
(
    ID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    PINNED BOOLEAN,
    TITLE VARCHAR(120)
);

create table if not exists COMPILATIONS_EVENTS
(
    COMPILATION_ID INTEGER,
    EVENT_ID INTEGER,
    PRIMARY KEY (COMPILATION_ID, EVENT_ID),
    constraint COMPILATIONS_EVENTS_COMPILATION_ID_FK
        foreign key (COMPILATION_ID) references COMPILATIONS (ID) on delete cascade,
    constraint COMPILATIONS_EVENTS_EVENT_ID_FK
        foreign key (EVENT_ID) references EVENTS (ID) on delete cascade
);

create table if not exists PARTICIPATIONS
(
    ID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    CREATED TIMESTAMP NOT NULL,
    EVENT_ID INTEGER NOT NULL,
    USER_ID INTEGER NOT NULL,
    STATUS VARCHAR(25),
    constraint PARTICIPATIONS_EVENT_ID_FK
        foreign key (EVENT_ID) references EVENTS (ID),
    constraint PARTICIPATIONS_USER_ID_FK
        foreign key (USER_ID) references USERS (ID)
);

create table if not exists LIKES
(
    USER_ID INTEGER,
    EVENT_ID INTEGER,
    PRIMARY KEY (USER_ID, EVENT_ID),
    constraint LIKES_USER_ID_FK
        foreign key (USER_ID) references USERS (ID) on delete cascade,
    constraint LIKES_EVENT_ID_FK
        foreign key (EVENT_ID) references EVENTS (ID) on delete cascade
);

create table if not exists DISLIKES
(
    USER_ID INTEGER,
    EVENT_ID INTEGER,
    PRIMARY KEY (USER_ID, EVENT_ID),
    constraint DISLIKES_USER_ID_FK
        foreign key (USER_ID) references USERS (ID) on delete cascade,
    constraint DISLIKES_EVENT_ID_FK
        foreign key (EVENT_ID) references EVENTS (ID) on delete cascade
);