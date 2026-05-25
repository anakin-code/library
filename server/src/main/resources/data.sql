INSERT INTO app_user (hrid, email, division, is_admin) VALUES
('A001', 'a001@example.com', '開発部', false),
('A002', 'a002@example.com', '金融ソリューション部', false),
('ADMIN01', 'admin@example.com', '管理部', true);

INSERT INTO category (id, number, name) VALUES
                                            (1, 1, '開発技術'),
                                            (2, 2, 'ソフトウェア工学'),
                                            (4, 4, 'インフラ'),
                                            (5, 5, 'ビジネススキル'),
                                            (7, 7, '産業');

INSERT INTO sub_category (category_id, number, name) VALUES
(1, 1, 'プログラミング一般'),
(1, 2, 'Java'),
(1, 3, 'JavaScript / TypeScript'),
(1, 4, 'Web'),
(1, 5, 'ツール'),
(1, 6, '生成AI'),
(2, 1, '設計 / アーキテクチャ'),
(2, 2, 'テスト'),
(2, 3, 'PM'),
(2, 4, 'アジャイル'),
(4, 1, 'ネットワーク'),
(4, 2, 'OS'),
(4, 3, 'AWS'),
(4, 4, 'SQL / RDB'),
(5, 2, 'コミュニケーション'),
(7, 1, '金融');

INSERT INTO book_title (sub_category_id, title) VALUES
(2, 'スッキリわかるJava入門'),
(4, 'Spring Boot実践入門'),
(6, 'ReactとTypeScriptではじめるWeb開発'),
(14, '達人に学ぶSQL徹底指南書'),
(13, 'AWSの基本と仕組み'),
(16, '金融システム入門');

INSERT INTO book_collection (book_title_id, serial_number, state) VALUES
(1, '1-02-0001', 'AVAILABLE'),
(1, '1-02-0002', 'AVAILABLE'),
(2, '1-04-0001', 'AVAILABLE'),
(3, '1-06-0001', 'AVAILABLE'),
(4, '4-04-0001', 'AVAILABLE'),
(5, '4-03-0001', 'AVAILABLE'),
(6, '7-01-0001', 'DEACCESSIONED');

INSERT INTO tag (name) VALUES
('Java'), ('Spring Boot'), ('React'), ('SQL'), ('AWS'), ('金融'), ('初心者向け');

INSERT INTO book_title_tag (book_title_id, tag_id) VALUES
(1, 1), (1, 7),
(2, 1), (2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6);
