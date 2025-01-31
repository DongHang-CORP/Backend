-- USER 테이블 더미 데이터
INSERT INTO users (username, email)
VALUES ('john_doe', 'john@example.com'),
       ('jane_doe', 'jane@example.com'),
       ('sam_smith', 'sam@example.com'),
       ('alex_jones', 'alex@example.com'),
       ('emily_davis', 'emily@example.com');


-- BOARD 테이블 더미 데이터
INSERT INTO board (user_id, restaurant_name, content_url, content, category, longitude, latitude,
                   street, city, country, like_count)
VALUES (1, 'Pasta Palace', 'http://pasta.com', 'Best pasta in town!', 'westenFood', 40.7128, -74.0060, '123 Pasta St',
        'New York', 'USA', 0),
       (2, 'Dragon’s Den', 'http://dragonsden.com', 'Authentic Chinese cuisine!', 'chinese', 34.0522, -118.2437,
        '456 Dragon Rd', 'Los Angeles', 'USA', 0),
       (3, 'Taco Tower', 'http://tacotower.com', 'Delicious tacos!', 'schoolFood', 51.5074, -0.1278, '789 Taco Blvd',
        'London', 'UK', 0),
       (4, 'Curry Corner', 'http://currycorner.com', 'Spicy and flavorful curries!', 'korea', 48.8566, 2.3522,
        '101 Curry Ave', 'Paris', 'France', 0),
       (5, 'Burger Barn', 'http://burgerbarn.com', 'Juicy burgers and fries!', 'hamburger', 37.7749, -122.4194,
        '202 Burger Ln', 'San Francisco', 'USA', 0),
       (1, 'Fusion Feast', 'http://fusionfeast.com', 'A fusion of global flavors!', 'noodle', 40.7128, -74.0060,
        '123 Pasta St', 'New York', 'USA', 0),
       (2, 'Sushi Spot', 'http://sushispot.com', 'Fresh sushi and sashimi!', 'japan', 34.0522, -118.2437,
        '456 Dragon Rd', 'Los Angeles', 'USA', 0),
       (3, 'Veggie Delight', 'http://veggiedelight.com', 'Healthy and delicious salads!', 'salad', 51.5074, -0.1278,
        '789 Taco Blvd', 'London', 'UK', 0),
       (4, 'Steak House', 'http://steakhouse.com', 'Perfectly grilled steaks!', 'meat', 48.8566, 2.3522,
        '101 Curry Ave', 'Paris', 'France', 0),
       (5, 'Cafe Bliss', 'http://cafebliss.com', 'Cozy cafe with great coffee!', 'cafeAndDesert', 37.7749, -122.4194,
        '202 Burger Ln', 'San Francisco', 'USA', 0);


-- COMMENT 테이블 더미 데이터
INSERT INTO comment (content, user_id, board_id)
VALUES ('This is amazing!', 1, 1),
       ('Great board! Thanks for sharing.', 2, 1),
       ('I love this restaurant!', 3, 2),
       ('Can you share more details?', 4, 2),
       ('Looks delicious!', 5, 3),
       ('I’ve been there, it’s fantastic!', 1, 3),
       ('What a great recommendation!', 2, 4),
       ('Thanks for the suggestion!', 3, 4),
       ('I’ll definitely try this out.', 4, 5),
       ('Such a helpful board!', 5, 5);

--
-- INSERT INTO likes (like_id, user_id, board_id)
-- VALUES (1, 1, 1),
--        (2, 2, 1),
--        (3, 3, 2),
--        (4, 4, 3),
--        (5, 5, 4),
--        (6, 1, 2),
--        (7, 2, 3),
--        (8, 3, 4),
--        (9, 4, 5),
--        (10, 5, 1);