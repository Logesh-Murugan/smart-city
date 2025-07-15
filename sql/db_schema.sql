CREATE DATABASE smartcity;

USE smartcity;

-- Users table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100),
    role ENUM('admin', 'citizen', 'staff')
);

-- Areas table
CREATE TABLE areas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    zone VARCHAR(100)
);

-- Services table
CREATE TABLE services (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    type VARCHAR(50),
    area_id INT,
    status VARCHAR(50),
    FOREIGN KEY (area_id) REFERENCES areas(id)
);

-- Complaints table
CREATE TABLE complaints (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    type VARCHAR(100),
    description TEXT,
    status VARCHAR(50) DEFAULT 'Pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Staff table
CREATE TABLE staff (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    area_id INT,
    FOREIGN KEY (area_id) REFERENCES areas(id)
);

-- Tasks table
CREATE TABLE tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    complaint_id INT,
    staff_id INT,
    status VARCHAR(50) DEFAULT 'Assigned',
    due_date DATE,
    FOREIGN KEY (complaint_id) REFERENCES complaints(id),
    FOREIGN KEY (staff_id) REFERENCES staff(id)
);

-- Emergency Contacts table
CREATE TABLE emergency_contacts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(100),
    contact_no VARCHAR(20),
    location VARCHAR(100)
);

-- Feedback table
CREATE TABLE feedback (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    content TEXT,
    rating INT,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
