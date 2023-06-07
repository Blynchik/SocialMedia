INSERT INTO app_user(email, name, password)
VALUES ('test@mail.ru', 'test', '{noop}test'),
       ('user@gmail.com', 'user', '{noop}user');

INSERT INTO role(user_id, role)
VALUES (1, 'USER'),
       (2, 'USER');

INSERT INTO binary_content(type, img_as_bytes)
VALUES ('jpg', '/9j/4AAQSkZJRgABAQEASABIAAD/'),
       ('png', '2wBDAAYEBQYFBAYGBQYHBwYIChAKCgkJChQODwwQFx');

INSERT INTO post(binary_content_id, created_at, owner_id, header, text)
VALUES (1, '2023-05-12 12:34:51.000000', 1, 'HEADER', 'My usual text'),
       (2,  '2023-06-12 09:13:43.000000', 1, 'TALE', 'Once upon time...'),
       (null,  '2023-07-12 19:44:23.000000', 2, 'MY FAVOURITE DISH', 'Fried rats');