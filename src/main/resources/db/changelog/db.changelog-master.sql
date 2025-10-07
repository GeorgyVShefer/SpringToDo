CREATE TABLE IF NOT EXISTS todo (
                                    id BIGSERIAL PRIMARY KEY,
                                    title VARCHAR(255) NOT NULL,
                                    description TEXT,
                                    completed BOOLEAN DEFAULT FALSE,
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Создание функции для обновления updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Удаляем старый триггер, если есть
DROP TRIGGER IF EXISTS set_updated_at ON todo;

-- Создание триггера на обновление записи
CREATE TRIGGER set_updated_at
    BEFORE UPDATE ON todo
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();