INSERT INTO app_user(email, name, password)
VALUES ('test@mail.ru', 'test', '{noop}test'),
       ('user@gmail.com', 'user', '{noop}user'),
       ('user1@example.com', 'User 2', '{noop}password'),
       ('user2@example.com', 'User 3', '{noop}password'),
       ('user3@example.com', 'User 4', '{noop}password'),
       ('user4@example.com', 'User 5', '{noop}password'),
       ('user5@example.com', 'User 6', '{noop}password'),
       ('user6@example.com', 'User 7', '{noop}password'),
       ('user7@example.com', 'User 8', '{noop}password'),
       ('user8@example.com', 'User 9', '{noop}password');


INSERT INTO role(user_id, role)
VALUES (1, 'USER'),
       (2, 'USER'),
       (3, 'USER'),
       (4, 'USER'),
       (5, 'USER'),
       (6, 'USER'),
       (7, 'USER'),
       (8, 'USER'),
       (9, 'USER'),
       (10, 'USER');


INSERT INTO binary_content(type, img_as_bytes)
VALUES ('jpg', '/9j/4AAQSkZJRgABAQEASABIAAD/'),
       ('png', '2wBDAAYEBQYFBAYGBQYHBwYIChAKCgkJChQODwwQFx'),
       ('jpg', 'SAHsdHDHsdHSDHAgagJsKDFHKdfGFdkFkdkdkgkdfk'),
       ('png', 'ASHSFHAhsdgjLUlDaeFQGBskuKFnYKdsdwJTaTjwRjs'),
       ('jpg', '!aHjSDjAkLnbvVagbTNMvbntNbNfQrNtMNBwrnjKnEaTE'),
       ('png', 'AjeNBVBqnm<M qwM,embbNmW<MNBBnmw<EMNBVbnam,E'),
       ('jpg', 'A:ou"y<mnBVCEWbranTSMYD<UF><MNBVcVBatnms,AN'),
       ('png', 'aejrklryt><mnbvCeBRANTMY<utmNBVBWRNAETmaNTBESD'),
       ('jpg', 'JKL.f,DMSNABvBnatm,dmVvwrbtnm BDSdkMNSBvavRAtnM'),
       ('png', 'ASk,MsnabVVanmy,L,mNBVCEg$%e^rkly:ou><m nbvcegrhJYEK'),
       ('jpg', '2wjrektlymnbvcj6K&DTLYf,mdNSBAVFFj#aKSMN'),
       ('png', 'l<mnABVcVRAS<DMN vqvwey<>op/.MNB'),
       ('jpg', 'Arsyktlu<mdnbvCe    gweldksjahgF');

INSERT INTO post(binary_content_id, created_at, owner_id, header, text)
VALUES (1, '2023-05-12 12:34:51.000000', 1, 'HEADER', 'My usual text'),
       (2, '2023-06-12 09:13:43.000000', 1, 'TALE', 'Once upon time...'),
       (null, '2023-07-12 19:44:23.000000', 2, 'MY FAVOURITE DISH', 'Fried rats'),
       (3, current_timestamp, 3, 'Первый пост', 'Привет всем, это мой первый пост!'),
       (4, current_timestamp, 5, 'Кулинарный пост', 'Сегодня готовим ...'),
       (5, current_timestamp, 8, 'Научный пост', 'Британский ученые открыли ...'),
       (6, current_timestamp, 10, 'Экономический пост', 'Госдолг США превысил ...'),
       (null, current_timestamp, 2, 'Поток сознания', 'Сегодня я сходил в туалет и ...'),
       (7, current_timestamp, 6, 'Обзор открывашек', 'Кто выйдет из игры быстрее? Открывашка, я или 99 банок пива?'),
       (8, current_timestamp, 7, 'Топ 10 пакетов из Пятерочки', '10 место. Прозрачный пакет ...'),
       (null, current_timestamp, 4, 'Самый главный вопрос', 'Главный вопрос жизни, вселенной и всего такого ...'),
       (9, current_timestamp, 7, 'Самый главный ответ', 'Это старый советский ...'),
       (10, current_timestamp, 2, 'Мечтают ли андроиды об электроовцах?',
        'Если это встроено в их базовую программу ...');

INSERT INTO message (recipient_id, sender_id, message)
VALUES (2, 1, 'Привет, как дела?'),
       (1, 2, 'Нормально'),
       (2, 1, 'Чем занимаешься?'),
       (1, 2, 'Ничем'),
       (2, 1, 'Какие планы?'),
       (1, 2, 'Никаких'),
       (1, 6, 'Ребята, вы совсем уже? Даю готовый бизнес в 2023'),
       (1, 3, 'Это спам рассылка, пожалуйста, не отвечайте на нее'),
       (1, 7, 'За сколько продадите подтяжки?'),
       (2, 8, 'Куплю слона. ДОРОГО'),
       (2, 6, 'Это спам рассылка, пожалуйста, не отвечайте на нее');

INSERT INTO friend_request (initiator_id, target_id, initiator_status, target_status)
VALUES (1, 2, 'APPROVED', 'APPROVED'),
       (1, 3, 'APPROVED', 'REQUESTED'),
       (4, 1, 'APPROVED', 'REQUESTED'),
       (1, 5, 'DECLINED', 'APPROVED'),
       (1, 6, 'APPROVED', 'DECLINED'),
       (1, 9, 'APPROVED', 'REQUESTED');

INSERT INTO chat_permission (u1, u2, status)
VALUES (1, 6, 'ENABLE'),
       (1, 3, 'ENABLE'),
       (1, 7, 'ENABLE'),
       (2, 8, 'ENABLE'),
       (2, 6, 'ENABLE'),
       (1, 5, 'REQUESTED'),
       (8, 1, 'REQUESTED'),
       (9, 1, 'REQUESTED');