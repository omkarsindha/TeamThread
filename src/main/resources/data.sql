insert into users (username, password, enabled) values ('hasan',
'$2a$10$dai1e6p40b91TZ0VBRj5G.DIx0IIH58JNAPEaYVWQS9JSXmrtV1dy', TRUE);
insert into authorities (username, authority) values ('hasan', 'ROLE_WORKER');
insert into users (username, password, enabled) values ('pranjal',
'$2a$10$dai1e6p40b91TZ0VBRj5G.DIx0IIH58JNAPEaYVWQS9JSXmrtV1dy', TRUE);
insert into authorities (username, authority) values ('pranjal', 'ROLE_MANAGER');
insert into users (username, password, enabled) values ('omkar',
'$2a$10$dai1e6p40b91TZ0VBRj5G.DIx0IIH58JNAPEaYVWQS9JSXmrtV1dy', TRUE);
insert into authorities (username, authority) values ('omkar', 'ROLE_ADMIN');

INSERT INTO topics (title,description) VALUES
    ('Working condition','Tslk sbout your work conditions here'),
    ('Pay Rate','You can discuss about your pay here(probably against non-disclosure agreement)');
    
    
INSERT INTO threads (topicid, username, role, comment,cmttime)
VALUES
    (1, 'hasan', 'ROLE_WORKER','They need to give us less work',NOW()),
    (1, 'pranjal', 'ROLE_MANAGER','We are getting too many projects overwork the low wage workers',NOW()),
    (1, 'omkar', 'ROLE_ADMIN','We are getting too many projects overwork the low wage workers',NOW());
