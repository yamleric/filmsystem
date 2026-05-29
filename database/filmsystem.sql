CREATE DATABASE IF NOT EXISTS filmsystem DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE filmsystem;
SET NAMES utf8mb4;

CREATE TABLE IF NOT EXISTS fs_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    role VARCHAR(30),
    enabled BIT NOT NULL DEFAULT b'1',
    create_time DATETIME,
    update_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS fs_news (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(120) NOT NULL,
    summary VARCHAR(255),
    content LONGTEXT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PUBLISHED',
    create_time DATETIME,
    update_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS fs_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    sort_no INT DEFAULT 0,
    create_time DATETIME,
    update_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS fs_film (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    category_id BIGINT,
    director VARCHAR(80),
    actor VARCHAR(255),
    region VARCHAR(50),
    language VARCHAR(50),
    release_date DATE,
    duration_minutes INT,
    description LONGTEXT,
    poster_path VARCHAR(255),
    video_path VARCHAR(255),
    create_time DATETIME,
    update_time DATETIME,
    CONSTRAINT fk_fs_film_category FOREIGN KEY (category_id) REFERENCES fs_category(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO fs_user (username, password, nickname, role, enabled, create_time, update_time)
SELECT 'admin', '21232f297a57a5a743894a0e4a801fc3', '系统管理员', 'ADMIN', b'1', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM fs_user WHERE username = 'admin');

INSERT INTO fs_category (name, description, sort_no, create_time, update_time)
SELECT '动作', '动作类型影片', 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM fs_category WHERE name = '动作');
INSERT INTO fs_category (name, description, sort_no, create_time, update_time)
SELECT '喜剧', '喜剧类型影片', 2, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM fs_category WHERE name = '喜剧');
INSERT INTO fs_category (name, description, sort_no, create_time, update_time)
SELECT '爱情', '爱情类型影片', 3, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM fs_category WHERE name = '爱情');
INSERT INTO fs_category (name, description, sort_no, create_time, update_time)
SELECT '科幻', '科幻类型影片', 4, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM fs_category WHERE name = '科幻');
INSERT INTO fs_category (name, description, sort_no, create_time, update_time)
SELECT '剧情', '剧情类型影片', 5, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM fs_category WHERE name = '剧情');
INSERT INTO fs_category (name, description, sort_no, create_time, update_time)
SELECT '悬疑', '悬疑类型影片', 6, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM fs_category WHERE name = '悬疑');
INSERT INTO fs_category (name, description, sort_no, create_time, update_time)
SELECT '战争', '战争类型影片', 7, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM fs_category WHERE name = '战争');
INSERT INTO fs_category (name, description, sort_no, create_time, update_time)
SELECT '动画', '动画类型影片', 8, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM fs_category WHERE name = '动画');
