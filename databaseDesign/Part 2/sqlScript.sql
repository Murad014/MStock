-- SHOW DATABASES;
-- CREATE DATABASE mstock;
CREATE DATABASE mstock;
USE mstock;
-- DROP DATABASE mstock;
CREATE TABLE products (
    id BIGINT PRIMARY KEY,
    productName VARCHAR(100),
    description TEXT,
    wholesale DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    sellingPrice DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    sellingPrice2 DECIMAL(10, 2) DEFAULT 0.00,
    unit ENUM('kg', 'liters', 'pieces', 'packs', 'grams', 'milliliters', 'litr') NOT NULL,
    quantity DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    currentQuantity DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    expiredDate TIMESTAMP DEFAULT NULL,
    categoryId BIGINT NOT NULL,
    companyId BIGINT,
    picture BLOB,
    createdDate TIMESTAMP,
    updatedDate TIMESTAMP,
    isActive TINYINT NOT NULL DEFAULT 1
);

-- Table for Product Barcodes
CREATE TABLE productBarcodes (
    id BIGINT PRIMARY KEY,
    barcode TEXT,
    productId BIGINT,
    createdDate TIMESTAMP,
    updatedDate TIMESTAMP,
    isActive TINYINT NOT NULL DEFAULT 1,
    UNIQUE(id, productId)
);

-- Table for Product Sale Prices
CREATE TABLE productSalePrices (
    id BIGINT PRIMARY KEY,
    sellingPrice DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    productId BIGINT,
    createdDate TIMESTAMP,
    updatedDate TIMESTAMP,
    isActive TINYINT NOT NULL DEFAULT 1
);


CREATE TABLE productCategories (
    id BIGINT PRIMARY KEY,
    categoryName VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    createDate TIMESTAMP,
    updatedDate TIMESTAMP,
    isActive TINYINT NOT NULL DEFAULT 1
);

CREATE TABLE supplieriesOfProdcuts (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(100) UNIQUE,
    description TEXT,
    address TEXT DEFAULT NULL,
    companyId BIGINT NOT NULL,
    createdDate TIMESTAMP,
    updatedDate TIMESTAMP,
    isActive TINYINT NOT NULL DEFAULT 1
);

CREATE TABLE companies (
    id BIGINT PRIMARY KEY,
    companyName VARCHAR(100) UNIQUE NOT NULL,
    createdDate TIMESTAMP,
    updatedDate TIMESTAMP,
    isActive TINYINT DEFAULT 1
);

CREATE TABLE invoices (
    id BIGINT PRIMARY KEY,
    invoiceCode VARCHAR(100) UNIQUE NOT NULL,
    supplierId BIGINT NOT NULL,
    invoiceDate TIMESTAMP,
    initialTotalAmount DECIMAL(10, 2) DEFAULT 0.00,
    createdDate TIMESTAMP,
    updatedDate TIMESTAMP,
    isActive TINYINT DEFAULT 1
);

CREATE TABLE invoiceItems (
    id BIGINT PRIMARY KEY,
    invoiceId BIGINT,
    productId BIGINT,
    quantity DECIMAL(10, 2),
    totalPrice DECIMAL(10, 2),
    createdDate TIMESTAMP,
    updatedDate TIMESTAMP,
    isActive TINYINT DEFAULT 1
);

CREATE TABLE payInvoices (
    id BIGINT PRIMARY KEY,
    invoiceId BIGINT NOT NULL,
    plusPay DECIMAL(10, 2) NOT NULL,
    minusPay DECIMAL(10, 2) NOT NULL,
    createdDate TIMESTAMP NOT NULL,
    updatedDate TIMESTAMP NOT NULL,
    isActive TINYINT DEFAULT 1
);

-- FOREIGN KEYS
ALTER TABLE products ADD FOREIGN KEY (categoryId) REFERENCES productCategories(id);
ALTER TABLE products ADD FOREIGN KEY (companyId) REFERENCES companies(id);
ALTER TABLE supplieriesOfProdcuts ADD FOREIGN KEY (companyId) REFERENCES companies(id);
ALTER TABLE invoiceItems ADD FOREIGN KEY (invoiceId) REFERENCES invoices(id);
ALTER TABLE payInvoices ADD FOREIGN KEY (invoiceId) REFERENCES invoices(id);
ALTER TABLE invoices ADD FOREIGN KEY (supplierId) REFERENCES supplieriesOfProdcuts(id);

ALTER TABLE productBarcodes ADD FOREIGN KEY (productId) REFERENCES products(id);
ALTER TABLE productSalePrices ADD FOREIGN KEY (productId) REFERENCES products(id);
