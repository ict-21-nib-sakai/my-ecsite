DROP TABLE order_details;
DROP TABLE orders;
DROP TABLE carts;
DROP TABLE items;
DROP TABLE categories;
DROP TABLE users;

CREATE TABLE carts
(
    id         integer                     NOT NULL,
    user_id    integer                     NOT NULL,
    quantity   integer                     NOT NULL,
    item_id    integer                     NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone
);

COMMENT ON COLUMN carts.user_id IS 'カートの所有者';

COMMENT ON COLUMN carts.quantity IS '数量';

COMMENT ON COLUMN carts.item_id IS '商品ID';

CREATE SEQUENCE carts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE carts_id_seq OWNED BY carts.id;

CREATE TABLE categories
(
    id         integer                     NOT NULL,
    name       character varying(255)      NOT NULL,
    suspended  boolean DEFAULT false       NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone,
    deleted_at timestamp without time zone,
    sequence   integer DEFAULT 0           NOT NULL
);

COMMENT ON COLUMN categories.suspended IS '一時停止中のカテゴリか否か';

COMMENT ON COLUMN categories.sequence IS '並び順';

CREATE SEQUENCE categories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE categories_id_seq OWNED BY categories.id;

CREATE TABLE items
(
    id          integer                     NOT NULL,
    name        character varying(255)      NOT NULL,
    maker       character varying(255)      NOT NULL,
    category_id integer                     NOT NULL,
    color       character varying(255),
    price       integer                     NOT NULL,
    stock       integer DEFAULT 0           NOT NULL,
    recommended boolean DEFAULT false       NOT NULL,
    suspended   boolean DEFAULT false       NOT NULL,
    created_at  timestamp without time zone NOT NULL,
    updated_at  timestamp without time zone,
    deleted_at  timestamp without time zone
);

COMMENT ON TABLE items IS '商品';

COMMENT ON COLUMN items.name IS '商品名';

COMMENT ON COLUMN items.maker IS 'メーカー名';

COMMENT ON COLUMN items.price IS '単価';

COMMENT ON COLUMN items.stock IS '残り在庫数';

COMMENT ON COLUMN items.recommended IS 'おすすめ商品';

COMMENT ON COLUMN items.suspended IS '取り扱い停止中';

CREATE SEQUENCE items_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE items_id_seq OWNED BY items.id;

CREATE TABLE order_details
(
    id          integer                NOT NULL,
    order_id    integer                NOT NULL,
    item_id     integer                NOT NULL,
    item_name   character varying(255) NOT NULL,
    item_price  integer                NOT NULL,
    item_maker  character varying(255) NOT NULL,
    quantity    integer                NOT NULL,
    canceled_at timestamp without time zone
);

COMMENT ON TABLE order_details IS '注文詳細';

COMMENT ON COLUMN order_details.order_id IS '注文ID';

COMMENT ON COLUMN order_details.item_id IS '商品ID';

COMMENT ON COLUMN order_details.item_name IS '注文した当時の商品名';

COMMENT ON COLUMN order_details.item_price IS '注文した当時の単価';

COMMENT ON COLUMN order_details.quantity IS '注文した数量';

COMMENT ON COLUMN order_details.canceled_at IS 'キャンセル日時';

COMMENT ON COLUMN order_details.item_maker IS '注文した当時のメーカー名';

CREATE SEQUENCE order_details_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE order_details_id_seq OWNED BY order_details.id;

CREATE TABLE orders
(
    id                    integer                     NOT NULL,
    user_id               integer                     NOT NULL,
    payment_method        character varying(31)       NOT NULL,
    shipping_address_type character varying(16)       NOT NULL,
    shipping_address      character varying(255)      NOT NULL,
    user_name             character varying(31)       NOT NULL,
    created_at            timestamp without time zone NOT NULL,
    updated_at            timestamp without time zone
);

COMMENT ON TABLE orders IS '注文';

COMMENT ON COLUMN orders.shipping_address_type IS '配送先の種類';

COMMENT ON COLUMN orders.shipping_address IS '配送先住所';

COMMENT ON COLUMN orders.user_name IS '配送先の氏名 (注文を請けた当時の名前を記録)';

CREATE SEQUENCE orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE orders_id_seq OWNED BY orders.id;

CREATE TABLE users
(
    id           integer                     NOT NULL,
    email        character varying(255)      NOT NULL,
    name         character varying(31)       NOT NULL,
    home_address character varying(255)      NOT NULL,
    password     text                        NOT NULL,
    suspended    boolean                     NOT NULL,
    created_at   timestamp without time zone NOT NULL,
    updated_at   timestamp without time zone,
    deleted_at   timestamp without time zone
);

COMMENT ON TABLE users IS '利用者';

COMMENT ON COLUMN users.email IS 'ログイン用メールアドレス';

COMMENT ON COLUMN users.name IS '氏名';

COMMENT ON COLUMN users.suspended IS 'アカウントの一時停止';

COMMENT ON COLUMN users.created_at IS '登録日時';

COMMENT ON COLUMN users.updated_at IS '更新日時';

COMMENT ON COLUMN users.deleted_at IS '退会日時';

COMMENT ON COLUMN users.home_address IS '自宅の住所';

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE users_id_seq OWNED BY users.id;

ALTER TABLE ONLY carts
    ALTER COLUMN id SET DEFAULT nextval('carts_id_seq'::regclass);

ALTER TABLE ONLY categories
    ALTER COLUMN id SET DEFAULT nextval('categories_id_seq'::regclass);

ALTER TABLE ONLY items
    ALTER COLUMN id SET DEFAULT nextval('items_id_seq'::regclass);

ALTER TABLE ONLY order_details
    ALTER COLUMN id SET DEFAULT nextval('order_details_id_seq'::regclass);

ALTER TABLE ONLY orders
    ALTER COLUMN id SET DEFAULT nextval('orders_id_seq'::regclass);

ALTER TABLE ONLY users
    ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);

INSERT INTO carts
VALUES (5, 2, 1, 34, '2021-10-12 15:04:22', '2021-10-12 15:04:22');
INSERT INTO carts
VALUES (2, 1, 1, 29, '2021-10-12 15:04:50', '2021-10-12 15:04:50');
INSERT INTO carts
VALUES (1, 1, 2, 9, '2021-10-12 15:04:22', '2021-10-12 15:04:22');
INSERT INTO carts
VALUES (3, 1, 1, 19, '2021-10-12 15:05:08', '2021-10-12 15:05:08');
INSERT INTO carts
VALUES (4, 2, 1, 15, '2021-10-12 15:04:22', '2021-10-12 15:04:22');

SELECT pg_catalog.setval('carts_id_seq', 5, true);

INSERT INTO categories
VALUES (1, 'アクセサリ', false, '2021-10-03 12:13:50', NULL, NULL, 30);
INSERT INTO categories
VALUES (5, 'アパレル', false, '2021-10-03 12:13:52', NULL, NULL, 40);
INSERT INTO categories
VALUES (2, '家電', false, '2021-10-03 12:12:33', NULL, NULL, 50);
INSERT INTO categories
VALUES (3, '書籍', false, '2021-10-03 12:11:33', NULL, NULL, 20);
INSERT INTO categories
VALUES (4, 'ペット用品', false, '2021-10-03 12:11:59', NULL, NULL, 10);
INSERT INTO categories
VALUES (7, 'スイーツ・お菓子', false, '2021-10-11 15:27:44', NULL, NULL, 60);

SELECT pg_catalog.setval('categories_id_seq', 7, true);

INSERT INTO items
VALUES (9, '破滅の刃', '集A社', 3, NULL, 440, 33, false, false, '2021-10-03 12:21:52',
        NULL, NULL);
INSERT INTO items
VALUES (1, '週刊少林ジャンプ', '集A社', 3, NULL, 390, 30, false, false,
        '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items
VALUES (8, 'ONE PEACH', '集A社', 3, NULL, 480, 28, false, false,
        '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items
VALUES (10, 'シルバー(SV)ネックレス', '5℃', 1, NULL, 8800, 12, false, false,
        '2021-10-03 12:41:30', NULL, NULL);
INSERT INTO items
VALUES (11, 'ダイヤモンドK10ピンクゴールド(PG)ネックレス', '5℃', 1, 'ピンクゴールド', 25300, 15, false,
        false, '2021-10-03 12:41:30', NULL, NULL);
INSERT INTO items
VALUES (12, 'K10ピンクゴールド(PG) ピアス', '5℃', 1, 'ピンクゴールド', 7700, 13, false, false,
        '2021-10-03 12:41:30', NULL, NULL);
INSERT INTO items
VALUES (13, 'ダイヤモンド バイ ザ ヤード™ ピアス', 'TIFFAMY', 1, '', 148500, 4, false, false,
        '2021-10-03 12:45:56', NULL, NULL);
INSERT INTO items
VALUES (17, 'ルンヴァ i3 ロボット掃除機', 'エイロボット', 2, NULL, 99800, 8, false, false,
        '2021-10-03 12:56:19', NULL, NULL);
INSERT INTO items
VALUES (20, 'スチーム式加湿器', 'ZOOJIRUSHI', 2, 'ホワイト', 19500, 20, false, false,
        '2021-10-03 13:03:53', NULL, NULL);
INSERT INTO items
VALUES (15, 'スキーグローブ', 'BIOR', 5, NULL, 102300, 6, false, false,
        '2021-10-03 12:50:58', NULL, NULL);
INSERT INTO items
VALUES (14, 'オーバーシャツ', 'BIOR', 5, NULL, 148500, 5, false, false,
        '2021-10-03 12:49:23', NULL, NULL);
INSERT INTO items
VALUES (16, 'シャプカ', 'BIOR', 5, NULL, 108900, 12, false, false,
        '2021-10-03 12:53:06', NULL, NULL);
INSERT INTO items
VALUES (21, 'ジャンボ グレー 猫用トイレ ディープパン', 'オーエーティー', 4, NULL, 3500, 20, false, false,
        '2021-10-03 13:07:09', NULL, NULL);
INSERT INTO items
VALUES (22, 'おからの猫砂 6リットル (x 4)', '常夏化工', 4, NULL, 2044, 18, false, false,
        '2021-10-03 13:10:56', NULL, NULL);
INSERT INTO items
VALUES (23, '猫用おやつ 焼かつお 高齢猫用 5本入×4個', 'NYAO (ニャオ)', 4, NULL, 1289, 30, false,
        false, '2021-10-03 13:12:05', NULL, NULL);
INSERT INTO items
VALUES (24, '猫用おやつ にゃ～る グルメ まぐろ海鮮バラエティ 14グラム (x 120)', 'NYAO (ニャオ)', 4, NULL,
        4480, 23, false, false, '2021-10-03 13:13:08', NULL, NULL);
INSERT INTO items
VALUES (25, 'キャットフード インドアキャット シニア 7歳以上', 'ビルズ・菜園', 4, NULL, 2473, 28, false,
        false, '2021-10-03 13:14:59', NULL, NULL);
INSERT INTO items
VALUES (19, 'ルンヴァ e5 ロボット掃除機 エイロボット', 'エイロボット', 2, NULL, 49800, 11, false,
        false, '2021-10-03 13:00:18', NULL, NULL);
INSERT INTO items
VALUES (18, 'ブラーバジェット m6 エイロボット', 'エイロボット', 2, NULL, 76860, 10, false, false,
        '2021-10-03 12:59:33', NULL, NULL);
INSERT INTO items
VALUES (26, '3種ミックスナッツ700g', '有限会社 スイーツ', 7, NULL, 1380, 30, false, false,
        '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items
VALUES (27, '砂糖不使用 無添加 しかも6種類★ドライフルーツ＆素焼き 無塩 ミックスナッツ 300g', '有限会社 くだもの', 7,
        NULL, 1480, 40, false, false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items
VALUES (28, '干し芋 無添加 館の熟成干し芋  600g(200g×3袋) 季節限定', '有限会社 スイーツ', 7, NULL, 1000,
        25, false, false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items
VALUES (29, '瀬戸内海産焼海苔(全角/全型)50枚入り[訳あり]', '(株) 海苔', 7, NULL, 1000, 33, false,
        false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items
VALUES (30, '北海道.チーズケーキ 2個セット. 訳あり スイーツ ギフト お菓子', '有限会社 スイーツ', 7, NULL, 2160,
        45, false, false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items
VALUES (31, '訳あり庄内柿　5kg前後', '○○農園', 7, NULL, 1980, 50, false, false,
        '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items
VALUES (32, 'チーズケーキ ケーキ SUPERチーズケーキバー', 'おいしいケーキ屋さん', 7, NULL, 1000, 44, false,
        false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items
VALUES (33, '熊本 みかん 訳あり みかん 蜜柑 ポイント消化 1セット 4kg', '○○農園', 7, NULL, 1880, 35,
        false, false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items
VALUES (34, 'さつまいも 熊本県産 訳あり 紅はるか べにはるか 1kg', '○○農園', 7, NULL, 1222, 40, false,
        false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items
VALUES (35, '朝摘み もぎたて青汁 国産 送料無料 大麦若葉 粉末 90g(3g×30包) 飲みやすい', '後藤園', 7, NULL,
        1554, 70, false, false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items
VALUES (36, '熊本県産 訳あり 利平栗 1.5kg 2L～Lサイズ', '○○農園', 7, NULL, 3480, 20, false,
        false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items
VALUES (37, 'チーズケーキ 1個 希少 な ジャージー牛乳使用', '有限会社 スイーツ', 7, NULL, 1000, 25, false,
        false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items
VALUES (38, 'マスクメロン 2玉 【秀品：2Lサイズ】 1玉約1.2kg以上', '○○農園', 7, NULL, 4980, 20, false,
        false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items
VALUES (39, 'ギフト まるごとみかん大福 9個入り', '有限会社 スイーツ', 7, NULL, 3750, 33, false, false,
        '2021-10-11 15:30:52', NULL, NULL);

SELECT pg_catalog.setval('items_id_seq', 39, true);

INSERT INTO order_details VALUES (1, 1, 9, '破滅の刃', 440, '集A社', 1, NULL);
INSERT INTO order_details VALUES (3, 1, 18, 'ブラーバジェット m6 エイロボット', 76860, 'エイロボット', 1, NULL);
INSERT INTO order_details VALUES (2, 1, 20, 'スチーム式加湿器', 19500, 'ZOOJIRUSHI', 1, NULL);
INSERT INTO order_details VALUES (4, 2, 1, '週刊少林ジャンプ', 390, '集A社', 1, NULL);
INSERT INTO order_details VALUES (5, 2, 8, 'ONE PEACH', 480, '集A社', 1, NULL);
INSERT INTO order_details VALUES (6, 2, 17, 'ルンヴァ i3 ロボット掃除機', 99800, 'エイロボット', 1, NULL);
INSERT INTO order_details VALUES (7, 3, 21, 'ジャンボ グレー 猫用トイレ ディープパン', 3500, 'オーエーティー', 2, NULL);
INSERT INTO order_details VALUES (8, 3, 25, 'キャットフード インドアキャット シニア 7歳以上', 2473, 'ビルズ・菜園', 1, NULL);
INSERT INTO order_details VALUES (9, 3, 10, 'シルバー(SV)ネックレス', 8800, '5℃', 1, NULL);
INSERT INTO order_details VALUES (10, 3, 15, 'スキーグローブ', 102300, 'BIOR', 1, NULL);
INSERT INTO order_details VALUES (11, 3, 24, '猫用おやつ にゃ～る グルメ まぐろ海鮮バラエティ 14グラム (x 120)', 4480, 'NYAO (ニャオ)', 2, NULL);
SELECT pg_catalog.setval('order_details_id_seq', 11, true);

INSERT INTO orders
VALUES (1, 1, 'cash_on_delivery', 'optional', '鳥取県鳥取市●●123-1', '鳥取 太郎',
        '2021-10-04 12:05:37', NULL);
INSERT INTO orders
VALUES (2, 2, 'credit_card', 'home', '島根県安来市●●1-2-3', '島根 花子',
        '2021-10-04 12:06:52', NULL);
INSERT INTO orders
VALUES (3, 1, 'credit_card', 'home', '鳥取県鳥取市●●12', '鳥取 太郎',
        '2021-10-04 12:08:03', NULL);

SELECT pg_catalog.setval('orders_id_seq', 3, true);

INSERT INTO users
VALUES (2,
        'hanako@example.com',
        '島根 花子',
        '島根県○○市○○1-2-3',
        '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8',
        false,
        '2021-10-02 09:56:42',
        NULL,
        NULL
);
INSERT INTO users
VALUES (1,
        'taro@example.com',
        '鳥取 太郎',
        '鳥取県○○郡○○町○○1234',
        '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8',
        false,
        '2021-10-02 09:56:05',
        NULL,
        NULL
);

SELECT pg_catalog.setval('users_id_seq', 2, true);

ALTER TABLE ONLY carts
    ADD CONSTRAINT carts_pk PRIMARY KEY (id);

ALTER TABLE ONLY categories
    ADD CONSTRAINT categories_pk PRIMARY KEY (id);

ALTER TABLE ONLY items
    ADD CONSTRAINT items_pk PRIMARY KEY (id);

ALTER TABLE ONLY order_details
    ADD CONSTRAINT order_details_pk PRIMARY KEY (id);

ALTER TABLE ONLY orders
    ADD CONSTRAINT orders_pk PRIMARY KEY (id);

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);

CREATE UNIQUE INDEX carts_index_1 ON carts USING btree (user_id, item_id);

CREATE INDEX categories_index_1 ON categories USING btree (sequence);

CREATE UNIQUE INDEX users_idx_1 ON users USING btree (email);

ALTER TABLE ONLY carts
    ADD CONSTRAINT carts_fk_1 FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE ONLY carts
    ADD CONSTRAINT carts_fk_2 FOREIGN KEY (item_id) REFERENCES items (id);

ALTER TABLE ONLY items
    ADD CONSTRAINT items_fk_1 FOREIGN KEY (category_id) REFERENCES categories (id);

ALTER TABLE ONLY order_details
    ADD CONSTRAINT order_details_fk_1 FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE ONLY order_details
    ADD CONSTRAINT order_details_fk_2 FOREIGN KEY (item_id) REFERENCES items (id);

ALTER TABLE ONLY orders
    ADD CONSTRAINT orders_fk_1 FOREIGN KEY (user_id) REFERENCES users (id);


