
Create table short_urls(

   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   short_code VARCHAR(10) UNIQUE NOT NULL,
   original_url TEXT NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   expires_at TIMESTAMP NULL

);