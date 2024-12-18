-- Insert Users
INSERT INTO users (username, password, email, role) VALUES
('admin', 'admin123', 'admin@cinema.com', 'ADMIN'),
('john_doe', 'pass123', 'john@example.com', 'USER'),
('jane_smith', 'pass456', 'jane@example.com', 'USER'),
('manager1', 'manager123', 'manager@cinema.com', 'ADMIN');

-- Insert Movies
INSERT INTO movies (title, description, duration, release_date, rating, poster_url, status) VALUES
('Dune: Part Two', 'Epic sci-fi sequel following Paul Atreides on his journey', 166, '2024-03-01', 8.5, 'dune2.jpg', 'NOW_SHOWING'),
('Poor Things', 'A gothic science fiction comedy-drama film', 141, '2024-02-15', 8.2, 'poorthings.jpg', 'NOW_SHOWING'),
('Kung Fu Panda 4', 'Po must train a new Dragon Warrior', 94, '2024-03-08', 7.8, 'kungfupanda4.jpg', 'NOW_SHOWING'),
('Godzilla x Kong', 'The mighty Kong and fearsome Godzilla team up', 115, '2024-03-29', 0.0, 'godzillakong.jpg', 'COMING_SOON');

-- Insert Theaters
INSERT INTO theaters (name, capacity, status) VALUES
('IMAX Theater', 200, 'ACTIVE'),
('Standard Hall 1', 150, 'ACTIVE'),
('Standard Hall 2', 150, 'ACTIVE'),
('VIP Theater', 80, 'ACTIVE'),
('Premium Hall', 120, 'ACTIVE');

-- Insert Shows
INSERT INTO shows (movie_id, theater_id, show_time, price, available_seats) VALUES
(1, 1, '2024-03-20 10:00:00', 15.99, 200),
(1, 1, '2024-03-20 13:30:00', 15.99, 200),
(1, 2, '2024-03-20 11:00:00', 12.99, 150),
(2, 3, '2024-03-20 14:00:00', 12.99, 150),
(2, 4, '2024-03-20 16:30:00', 19.99, 80),
(3, 2, '2024-03-20 17:00:00', 12.99, 150),
(3, 5, '2024-03-20 19:30:00', 14.99, 120),
(1, 1, '2024-03-21 10:00:00', 15.99, 200),
(2, 3, '2024-03-21 13:00:00', 12.99, 150),
(3, 4, '2024-03-21 15:30:00', 19.99, 80);





