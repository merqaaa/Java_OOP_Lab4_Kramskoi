CREATE TABLE IF NOT EXISTS clothes_inventory (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL, -- Дискримінатор класу
    brand VARCHAR(100) NOT NULL,
    size VARCHAR(20) NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    
    -- Специфічні поля (можуть бути NULL)
    length INT,
    wash_type VARCHAR(50),
    sleeve_type VARCHAR(50),
    collar_size NUMERIC(5, 2)
);
