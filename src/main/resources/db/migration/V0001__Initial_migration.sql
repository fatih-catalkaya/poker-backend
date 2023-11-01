CREATE TABLE player(
    id VARCHAR(36),
    name TEXT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE transactions(
    id VARCHAR(36),
    player_id VARCHAR(36) NOT NULL,
    amount REAL NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    comment TEXT NOT NULL DEFAULT "",
    FOREIGN KEY(player_id) REFERENCES player(id),
    PRIMARY KEY (id)
);