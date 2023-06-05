INSERT INTO app_user(email, name, password)
VALUES ('test@mail.ru', 'test', '{noop}test'),
       ('user@gmail.com', 'user', '{noop}user');

INSERT INTO role(user_id, role)
VALUES (1, 'USER'),
       (2, 'USER');