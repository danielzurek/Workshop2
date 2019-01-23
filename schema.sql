CREATE DATABASE workshop2;
USE workshop2;

CREATE TABLE IF NOT EXISTS user_groups(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE exercises(
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE users(
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    user_group_id INT,
    FOREIGN KEY (user_group_id) REFERENCES user_groups(id)
);

CREATE TABLE solutions(
    id INT PRIMARY KEY AUTO_INCREMENT,
    created DATETIME,
    updated DATETIME,
    description TEXT,
    exercise_id INT,
    user_id BIGINT(20),
    FOREIGN KEY (exercise_id) REFERENCES exercises(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
