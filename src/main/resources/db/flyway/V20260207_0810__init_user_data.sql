CREATE TABLE users (
    id VARCHAR(36) NOT NULL,
    name VARCHAR(255),
    email VARCHAR(255),
    last_accessed DATETIME,
    PRIMARY KEY (id)
);

CREATE TABLE audit_log (
    id VARCHAR(36) NOT NULL,
    action VARCHAR(255) NOT NULL,
    user_id CHAR(36) NOT NULL,
    details VARCHAR(1000),
    timestamp DATETIME NOT NULL,
    PRIMARY KEY (id)
);


INSERT INTO users (id, name, email, last_accessed) VALUES ('1f4a6a2e-0ad3-4a3a-9f12-6b6f1c6f7a01', 'Aiden Parker', 'aiden.parker01@example.com', '2025-01-01 08:15:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('2b6f3d8a-45a7-4f0a-8e8a-3c6b0a1a8a02', 'Bianca Flores', 'bianca.flores02@example.com', '2025-01-02 09:20:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('3c7b4e91-5b0a-4c7b-9f12-4d7c2b3c9b03', 'Caleb Ortiz', 'caleb.ortiz03@example.com', '2025-01-03 10:05:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('4d8c5f02-6c1b-4d8c-a123-5e8d3c4d0c04', 'Diana Kent', 'diana.kent04@example.com', '2025-01-04 11:10:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('5e9d6a13-7d2c-4e9d-b234-6f9e4d5e1d05', 'Ethan Reed', 'ethan.reed05@example.com', '2025-01-05 12:25:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('6fae7b24-8e3d-4fae-c345-70af5e6f2e06', 'Fiona Briggs', 'fiona.briggs06@example.com', '2025-01-06 13:30:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('70bf8c35-9f4e-40bf-d456-81b06f703f07', 'Gavin Shaw', 'gavin.shaw07@example.com', '2025-01-07 14:45:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('81c09d46-a05f-41c0-e567-92c170814008', 'Hannah Miles', 'hannah.miles08@example.com', '2025-01-08 15:50:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('92d1ae57-b160-42d1-f678-a3d281925109', 'Ivan Cole', 'ivan.cole09@example.com', '2025-01-09 16:05:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('a3e2bf68-c261-43e2-0789-b4e392a3620a', 'Jasmine Ng', 'jasmine.ng10@example.com', '2025-01-10 17:15:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('b4f3c079-d362-44f3-189a-c5f4a3b4730b', 'Kai Bennett', 'kai.bennett11@example.com', '2025-01-11 18:20:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('c504d18a-e463-45a0-29ab-d6a5b4c5840c', 'Lena Cruz', 'lena.cruz12@example.com', '2025-01-12 19:30:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('d615e29b-f564-46b1-3abc-e7b6c5d6950d', 'Mason Hart', 'mason.hart13@example.com', '2025-01-13 08:05:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('e726f3ac-0665-47c2-4bcd-f8c7d6e7a60e', 'Nora Patel', 'nora.patel14@example.com', '2025-01-14 09:10:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('f83704bd-1766-48d3-5cde-09d8e7f8b70f', 'Owen Price', 'owen.price15@example.com', '2025-01-15 10:20:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('0a4815ce-2867-49e4-6def-1ae9f809c810', 'Priya Iqbal', 'priya.iqbal16@example.com', '2025-01-16 11:25:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('1b5926df-3968-4af5-7f01-2bfa091ad911', 'Quinn Sharp', 'quinn.sharp17@example.com', '2025-01-17 12:35:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('2c6a37f0-4a69-4b06-8f12-3c0b1a2bea12', 'Riley Xu', 'riley.xu18@example.com', '2025-01-18 13:40:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('3d7b4801-5b6a-4c17-9f23-4d1c2b3cfb13', 'Sofia Lane', 'sofia.lane19@example.com', '2025-01-19 14:50:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('4e8c5912-6c7b-4d28-a034-5e2d3c4d0c14', 'Theo Grant', 'theo.grant20@example.com', '2025-01-20 15:55:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('5f9d6a23-7d8c-4e39-b145-6f3e4d5e1d15', 'Uma Silva', 'uma.silva21@example.com', '2025-01-21 16:05:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('60ae7b34-8e9d-4f4a-c256-704f5e6f2e16', 'Victor Hale', 'victor.hale22@example.com', '2025-01-22 17:15:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('71bf8c45-9faf-405b-d367-81506f703f17', 'Willa Fox', 'willa.fox23@example.com', '2025-01-23 18:20:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('82c09d56-a0b0-416c-e478-926170814018', 'Xander Wu', 'xander.wu24@example.com', '2025-01-24 19:25:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('93d1ae67-b1c1-427d-f589-a3d281925119', 'Yara Khan', 'yara.khan25@example.com', '2025-01-25 08:10:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('a4e2bf78-c2d2-438e-069a-b4e392a3621a', 'Zane Brooks', 'zane.brooks26@example.com', '2025-01-26 09:20:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('b5f3c089-d3e3-449f-17ab-c5f4a3b4731b', 'Aria Nolan', 'aria.nolan27@example.com', '2025-01-27 10:30:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('c604d19a-e4f4-45b0-28bc-d6a5b4c5841c', 'Brett Mercer', 'brett.mercer28@example.com', '2025-01-28 11:35:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('d715e2ab-f505-46c1-39cd-e7b6c5d6951d', 'Cassidy Holt', 'cassidy.holt29@example.com', '2025-01-29 12:45:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('e826f3bc-0616-47d2-4ade-f8c7d6e7a61e', 'Derek Moss', 'derek.moss30@example.com', '2025-01-30 13:50:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('f93704cd-1727-48e3-5bef-09d8e7f8b71f', 'Elena Park', 'elena.park31@example.com', '2025-01-31 14:55:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('0b4815de-2838-49f4-6cf0-1ae9f809c820', 'Felix Boone', 'felix.boone32@example.com', '2025-02-01 15:05:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('1c5926ef-3949-4a05-7d01-2bfa091ad921', 'Gia Torres', 'gia.torres33@example.com', '2025-02-02 16:15:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('2d6a37ff-4a5a-4b16-8e12-3c0b1a2bea22', 'Harper Lowe', 'harper.lowe34@example.com', '2025-02-03 17:20:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('3e7b480a-5b6b-4c26-9f23-4d1c2b3cfb23', 'Iris Doyle', 'iris.doyle35@example.com', '2025-02-04 18:30:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('4f8c591b-6c7c-4d36-a034-5e2d3c4d0c24', 'Jude Flynn', 'jude.flynn36@example.com', '2025-02-05 19:35:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('5a9d6a2c-7d8d-4e46-b145-6f3e4d5e1d25', 'Keira Wynn', 'keira.wynn37@example.com', '2025-02-06 08:05:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('6bae7b3d-8e9e-4f56-c256-704f5e6f2e26', 'Logan Pierce', 'logan.pierce38@example.com', '2025-02-07 09:15:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('7cbf8c4e-9faf-4066-d367-81506f703f27', 'Mira Sand', 'mira.sand39@example.com', '2025-02-08 10:25:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('8dc09d5f-a0b0-4176-e478-926170814028', 'Nico Wells', 'nico.wells40@example.com', '2025-02-09 11:30:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('9ed1ae60-b1c1-4286-f589-a3d281925129', 'Olive Tate', 'olive.tate41@example.com', '2025-02-10 12:40:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('afe2bf71-c2d2-4396-069a-b4e392a3622a', 'Pavel Yang', 'pavel.yang42@example.com', '2025-02-11 13:45:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('b0f3c082-d3e3-44a6-17ab-c5f4a3b4732b', 'Quincy Ford', 'quincy.ford43@example.com', '2025-02-12 14:55:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('c1a4d193-e4f4-45b6-28bc-d6a5b4c5842c', 'Rosa Lin', 'rosa.lin44@example.com', '2025-02-13 15:05:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('d2b5e2a4-f505-46c6-39cd-e7b6c5d6952d', 'Samir Wolfe', 'samir.wolfe45@example.com', '2025-02-14 16:15:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('e3c6f3b5-0616-47d6-4ade-f8c7d6e7a62e', 'Talia Reed', 'talia.reed46@example.com', '2025-02-15 17:20:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('f4d704c6-1727-48e6-5bef-09d8e7f8b72f', 'Uriel Frost', 'uriel.frost47@example.com', '2025-02-16 18:30:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('05e815d7-2838-49f6-6cf0-1ae9f809c830', 'Vera Stone', 'vera.stone48@example.com', '2025-02-17 19:35:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('16f926e8-3949-4a06-7d01-2bfa091ad931', 'Wyatt Cole', 'wyatt.cole49@example.com', '2025-02-18 08:10:00');
INSERT INTO users (id, name, email, last_accessed) VALUES ('270a37f9-4a5a-4b16-8e12-3c0b1a2bea32', 'Zoe Blake', 'zoe.blake50@example.com', '2025-02-19 09:20:00');
