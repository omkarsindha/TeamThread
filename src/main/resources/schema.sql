DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS threads;
DROP TABLE IF EXISTS topics;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(120) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE topics (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE threads (
    id INT AUTO_INCREMENT PRIMARY KEY,
    topicid INT,
    username VARCHAR(50),
    role VARCHAR(50),
    comment VARCHAR(255) NOT NULL,
    cmttime DATE,
    FOREIGN KEY (topicid) REFERENCES topics(id),
    FOREIGN KEY (username) REFERENCES users(username)
);
