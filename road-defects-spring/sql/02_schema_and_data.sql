-- Шаг 2. Создание таблиц и заполнение данными.
-- Выполняется, подключившись к базе road_defects.
SET client_encoding = 'UTF8';

-- Удаляем таблицы, если скрипт запускается повторно
DROP TABLE IF EXISTS defect;
DROP TABLE IF EXISTS defect_type;

-- Сущность 1: тип дефекта (справочник)
CREATE TABLE defect_type (
    code VARCHAR(10)  PRIMARY KEY,   -- код типа: d01, d10, d20, d40
    name VARCHAR(100) NOT NULL       -- название типа
);

-- Сущность 2: обнаруженный дефект
CREATE TABLE defect (
    id          BIGSERIAL        PRIMARY KEY,                                     -- уникальный id (генерирует БД)
    type_code   VARCHAR(10)      NOT NULL REFERENCES defect_type (code),          -- тип дефекта
    latitude    DOUBLE PRECISION NOT NULL CHECK (latitude  BETWEEN -90  AND 90),  -- широта
    longitude   DOUBLE PRECISION NOT NULL CHECK (longitude BETWEEN -180 AND 180), -- долгота
    detected_at TIMESTAMP        NOT NULL                                         -- дата и время обнаружения
);

-- Заполнение справочника типов
INSERT INTO defect_type (code, name) VALUES
    ('d01', 'Яма'),
    ('d10', 'Продольная трещина'),
    ('d20', 'Поперечная трещина'),
    ('d40', 'Серия трещин');

-- Заполнение таблицы дефектов тестовыми данными
INSERT INTO defect (type_code, latitude, longitude, detected_at) VALUES
    ('d01', 55.751244, 37.618423, '2024-05-12 14:30:15'),
    ('d10', 55.752010, 37.620110, '2024-05-12 14:31:02'),
    ('d20', 55.753120, 37.622540, '2024-05-12 14:32:47'),
    ('d40', 55.754300, 37.624900, '2024-05-12 14:33:10'),
    ('d01', 55.755870, 37.627300, '2024-05-13 09:05:44'),
    ('d10', 55.757020, 37.629810, '2024-05-13 09:06:30'),
    ('d20', 55.758440, 37.632150, '2024-05-13 09:07:58'),
    ('d01', 55.759600, 37.634700, '2024-05-14 17:20:05');
