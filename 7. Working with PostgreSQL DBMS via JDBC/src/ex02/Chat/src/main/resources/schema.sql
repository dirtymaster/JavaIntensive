CREATE TABLE "User"
(
    id       BIGINT PRIMARY KEY,
    login    VARCHAR,
    password VARCHAR
);

CREATE TABLE Chatroom
(
    id    BIGINT PRIMARY KEY,
    name  VARCHAR,
    owner BIGINT,

    FOREIGN KEY (owner) REFERENCES "User" (id)
);

CREATE TABLE Message
(
    id        BIGINT PRIMARY KEY,
    author_id BIGINT,
    room_id   BIGINT,
    text      TEXT,
    time      TIMESTAMP,

    FOREIGN KEY (author_id) REFERENCES "User" (id),
    FOREIGN KEY (room_id) REFERENCES Chatroom (id)
);