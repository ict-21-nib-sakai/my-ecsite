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
    item_color  character varying(255),
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

COMMENT ON COLUMN order_details.item_color IS '注文した当時の商品の色';

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

INSERT INTO items VALUES (10, 'シルバー(SV)ネックレス', '5℃', 1, NULL, 8800, 12, false, false, '2021-10-03 12:41:30', NULL, NULL);
INSERT INTO items VALUES (11, 'ダイヤモンドK10ピンクゴールド(PG)ネックレス', '5℃', 1, 'ピンクゴールド', 25300, 15, false, false, '2021-10-03 12:41:30', NULL, NULL);
INSERT INTO items VALUES (12, 'K10ピンクゴールド(PG) ピアス', '5℃', 1, 'ピンクゴールド', 7700, 13, false, false, '2021-10-03 12:41:30', NULL, NULL);
INSERT INTO items VALUES (13, 'ダイヤモンド バイ ザ ヤード™ ピアス', 'TIFFAMY', 1, '', 148500, 4, false, false, '2021-10-03 12:45:56', NULL, NULL);
INSERT INTO items VALUES (17, 'ルンヴァ i3 ロボット掃除機', 'エイロボット', 2, NULL, 99800, 8, false, false, '2021-10-03 12:56:19', NULL, NULL);
INSERT INTO items VALUES (20, 'スチーム式加湿器', 'ZOOJIRUSHI', 2, 'ホワイト', 19500, 20, false, false, '2021-10-03 13:03:53', NULL, NULL);
INSERT INTO items VALUES (15, 'スキーグローブ', 'BIOR', 5, NULL, 102300, 6, false, false, '2021-10-03 12:50:58', NULL, NULL);
INSERT INTO items VALUES (14, 'オーバーシャツ', 'BIOR', 5, NULL, 148500, 5, false, false, '2021-10-03 12:49:23', NULL, NULL);
INSERT INTO items VALUES (16, 'シャプカ', 'BIOR', 5, NULL, 108900, 12, false, false, '2021-10-03 12:53:06', NULL, NULL);
INSERT INTO items VALUES (21, 'ジャンボ グレー 猫用トイレ ディープパン', 'オーエーティー', 4, NULL, 3500, 20, false, false, '2021-10-03 13:07:09', NULL, NULL);
INSERT INTO items VALUES (22, 'おからの猫砂 6リットル (x 4)', '常夏化工', 4, NULL, 2044, 18, false, false, '2021-10-03 13:10:56', NULL, NULL);
INSERT INTO items VALUES (23, '猫用おやつ 焼かつお 高齢猫用 5本入×4個', 'NYAO (ニャオ)', 4, NULL, 1289, 30, false, false, '2021-10-03 13:12:05', NULL, NULL);
INSERT INTO items VALUES (24, '猫用おやつ にゃ～る グルメ まぐろ海鮮バラエティ 14グラム (x 120)', 'NYAO (ニャオ)', 4, NULL, 4480, 23, false, false, '2021-10-03 13:13:08', NULL, NULL);
INSERT INTO items VALUES (25, 'キャットフード インドアキャット シニア 7歳以上', 'ビルズ・菜園', 4, NULL, 2473, 28, false, false, '2021-10-03 13:14:59', NULL, NULL);
INSERT INTO items VALUES (19, 'ルンヴァ e5 ロボット掃除機 エイロボット', 'エイロボット', 2, NULL, 49800, 11, false, false, '2021-10-03 13:00:18', NULL, NULL);
INSERT INTO items VALUES (18, 'ブラーバジェット m6 エイロボット', 'エイロボット', 2, NULL, 76860, 10, false, false, '2021-10-03 12:59:33', NULL, NULL);
INSERT INTO items VALUES (26, '3種ミックスナッツ700g', '有限会社 スイーツ', 7, NULL, 1380, 30, false, false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items VALUES (27, '砂糖不使用 無添加 しかも6種類★ドライフルーツ＆素焼き 無塩 ミックスナッツ 300g', '有限会社 くだもの', 7, NULL, 1480, 40, false, false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items VALUES (28, '干し芋 無添加 館の熟成干し芋  600g(200g×3袋) 季節限定', '有限会社 スイーツ', 7, NULL, 1000, 25, false, false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items VALUES (29, '瀬戸内海産焼海苔(全角/全型)50枚入り[訳あり]', '(株) 海苔', 7, NULL, 1000, 33, false, false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items VALUES (30, '北海道.チーズケーキ 2個セット. 訳あり スイーツ ギフト お菓子', '有限会社 スイーツ', 7, NULL, 2160, 45, false, false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items VALUES (31, '訳あり庄内柿　5kg前後', '○○農園', 7, NULL, 1980, 50, false, false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items VALUES (32, 'チーズケーキ ケーキ SUPERチーズケーキバー', 'おいしいケーキ屋さん', 7, NULL, 1000, 44, false, false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items VALUES (33, '熊本 みかん 訳あり みかん 蜜柑 ポイント消化 1セット 4kg', '○○農園', 7, NULL, 1880, 35, false, false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items VALUES (34, 'さつまいも 熊本県産 訳あり 紅はるか べにはるか 1kg', '○○農園', 7, NULL, 1222, 40, false, false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items VALUES (35, '朝摘み もぎたて青汁 国産 送料無料 大麦若葉 粉末 90g(3g×30包) 飲みやすい', '後藤園', 7, NULL, 1554, 70, false, false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items VALUES (36, '熊本県産 訳あり 利平栗 1.5kg 2L～Lサイズ', '○○農園', 7, NULL, 3480, 20, false, false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items VALUES (37, 'チーズケーキ 1個 希少 な ジャージー牛乳使用', '有限会社 スイーツ', 7, NULL, 1000, 25, false, false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items VALUES (38, 'マスクメロン 2玉 【秀品：2Lサイズ】 1玉約1.2kg以上', '○○農園', 7, NULL, 4980, 20, false, false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items VALUES (39, 'ギフト まるごとみかん大福 9個入り', '有限会社 スイーツ', 7, NULL, 3750, 33, false, false, '2021-10-11 15:30:52', NULL, NULL);
INSERT INTO items VALUES (9, '破滅の刃 1', '集A社', 3, NULL, 440, 33, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (40, '破滅の刃 2', '集A社', 3, NULL, 440, 30, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (41, '破滅の刃 3', '集A社', 3, NULL, 440, 40, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (42, '破滅の刃 4', '集A社', 3, NULL, 440, 25, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (43, '破滅の刃 5', '集A社', 3, NULL, 440, 20, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (44, '破滅の刃 6', '集A社', 3, NULL, 440, 15, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (45, '破滅の刃 7', '集A社', 3, NULL, 440, 10, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (46, '破滅の刃 8', '集A社', 3, NULL, 440, 20, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (47, '破滅の刃 9', '集A社', 3, NULL, 440, 5, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (48, '破滅の刃 10', '集A社', 3, NULL, 440, 8, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (49, '破滅の刃 11', '集A社', 3, NULL, 440, 6, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (50, '破滅の刃 12', '集A社', 3, NULL, 440, 12, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (51, '破滅の刃 13', '集A社', 3, NULL, 440, 22, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (52, '破滅の刃 14', '集A社', 3, NULL, 440, 29, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (53, '破滅の刃 15', '集A社', 3, NULL, 440, 27, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (54, '破滅の刃 16', '集A社', 3, NULL, 440, 24, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (55, '破滅の刃 17', '集A社', 3, NULL, 440, 13, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (56, '破滅の刃 18', '集A社', 3, NULL, 440, 12, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (57, '破滅の刃 19', '集A社', 3, NULL, 440, 22, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (58, '破滅の刃 20', '集A社', 3, NULL, 440, 11, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (59, '破滅の刃 21', '集A社', 3, NULL, 440, 18, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (60, '破滅の刃 22', '集A社', 3, NULL, 440, 19, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (61, '破滅の刃 23', '集A社', 3, NULL, 440, 21, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (62, '破滅の刃 1~23巻セット', '集A社', 3, NULL, 10680, 24, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (1, '週刊少林ジャンプ 2021年 21/22号', '集A社', 3, NULL, 390, 30, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (63, '週刊少林ジャンプ 2021年 23号', '集A社', 3, NULL, 290, 10, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (64, '週刊少林ジャンプ 2021年 24号', '集A社', 3, NULL, 290, 8, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (65, '週刊少林ジャンプ 2021年 25号', '集A社', 3, NULL, 290, 5, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (66, '週刊少林ジャンプ 2021年 26号', '集A社', 3, NULL, 290, 4, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (67, '週刊少林ジャンプ 2021年 27号', '集A社', 3, NULL, 290, 9, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (68, '週刊少林ジャンプ 2021年 28号', '集A社', 3, NULL, 290, 3, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (69, '週刊少林ジャンプ 2021年 29号', '集A社', 3, NULL, 290, 10, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (70, '週刊少林ジャンプ 2021年 30号', '集A社', 3, NULL, 290, 20, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (71, '週刊少林ジャンプ 2021年 31号', '集A社', 3, NULL, 290, 15, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (72, '週刊少林ジャンプ 2021年 32号', '集A社', 3, NULL, 290, 7, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (73, '週刊少林ジャンプ 2021年 33/34号', '集A社', 3, NULL, 290, 6, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (8, 'ONE PEACH 1', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (74, '週刊少林ジャンプ 2021年 35号', '集A社', 3, NULL, 290, 8, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (75, '週刊少林ジャンプ 2021年 36/37号', '集A社', 3, NULL, 290, 9, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (76, '週刊少林ジャンプ 2021年 38号', '集A社', 3, NULL, 290, 7, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (77, '週刊少林ジャンプ 2021年 39号', '集A社', 3, NULL, 290, 5, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (78, '週刊少林ジャンプ 2021年 40号', '集A社', 3, NULL, 290, 4, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (79, '週刊少林ジャンプ 2021年 41号', '集A社', 3, NULL, 290, 8, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (80, '週刊少林ジャンプ 2021年 42号', '集A社', 3, NULL, 290, 9, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (81, 'ONE PEACH 2', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (82, 'ONE PEACH 3', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (83, 'ONE PEACH 4', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (84, 'ONE PEACH 5', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (85, 'ONE PEACH 6', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (86, 'ONE PEACH 7', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (87, 'ONE PEACH 8', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (88, 'ONE PEACH 9', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (89, 'ONE PEACH 10', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (90, 'ONE PEACH 11', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (91, 'ONE PEACH 12', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (92, 'ONE PEACH 13', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (93, 'ONE PEACH 14', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (94, 'ONE PEACH 15', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (95, 'ONE PEACH 16', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (96, 'ONE PEACH 17', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (97, 'ONE PEACH 18', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (98, 'ONE PEACH 19', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (99, 'ONE PEACH 20', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (100, 'ONE PEACH 21', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (101, 'ONE PEACH 22', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (102, 'ONE PEACH 23', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (103, 'ONE PEACH 24', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (104, 'ONE PEACH 25', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (105, 'ONE PEACH 26', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (106, 'ONE PEACH 27', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (107, 'ONE PEACH 28', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (108, 'ONE PEACH 29', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (109, 'ONE PEACH 30', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (110, 'ONE PEACH 31', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (111, 'ONE PEACH 32', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (112, 'ONE PEACH 33', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (113, 'ONE PEACH 34', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (114, 'ONE PEACH 35', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (115, 'ONE PEACH 36', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (116, 'ONE PEACH 37', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (117, 'ONE PEACH 38', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (118, 'ONE PEACH 39', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (119, 'ONE PEACH 40', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (120, 'ONE PEACH 41', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (121, 'ONE PEACH 42', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (122, 'ONE PEACH 43', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (123, 'ONE PEACH 44', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (124, 'ONE PEACH 45', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (125, 'ONE PEACH 46', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (126, 'ONE PEACH 47', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (127, 'ONE PEACH 48', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (128, 'ONE PEACH 49', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (129, 'ONE PEACH 50', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (130, 'ONE PEACH 51', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (131, 'ONE PEACH 52', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (132, 'ONE PEACH 53', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (133, 'ONE PEACH 54', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (134, 'ONE PEACH 55', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (135, 'ONE PEACH 56', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (136, 'ONE PEACH 57', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (137, 'ONE PEACH 58', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (138, 'ONE PEACH 59', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (139, 'ONE PEACH 60', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (140, 'ONE PEACH 61', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (141, 'ONE PEACH 62', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (142, 'ONE PEACH 63', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (143, 'ONE PEACH 64', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (144, 'ONE PEACH 65', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (145, 'ONE PEACH 66', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (146, 'ONE PEACH 67', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (147, 'ONE PEACH 68', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (148, 'ONE PEACH 69', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (149, 'ONE PEACH 70', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (150, 'ONE PEACH 71', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (151, 'ONE PEACH 72', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (152, 'ONE PEACH 73', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (153, 'ONE PEACH 74', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (154, 'ONE PEACH 75', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (155, 'ONE PEACH 76', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (156, 'ONE PEACH 77', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (157, 'ONE PEACH 78', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (158, 'ONE PEACH 79', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (159, 'ONE PEACH 80', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (160, 'ONE PEACH 81', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (161, 'ONE PEACH 82', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (162, 'ONE PEACH 83', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (163, 'ONE PEACH 84', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (164, 'ONE PEACH 85', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (165, 'ONE PEACH 86', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (166, 'ONE PEACH 87', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (167, 'ONE PEACH 88', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (168, 'ONE PEACH 89', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (169, 'ONE PEACH 90', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (170, 'ONE PEACH 91', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (171, 'ONE PEACH 92', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (172, 'ONE PEACH 93', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (173, 'ONE PEACH 94', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (174, 'ONE PEACH 95', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (175, 'ONE PEACH 96', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (176, 'ONE PEACH 97', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (177, 'ONE PEACH 98', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (178, 'ONE PEACH 99', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (179, 'ONE PEACH 100', '集A社', 3, NULL, 480, 28, false, false, '2021-10-03 12:21:52', NULL, NULL);
INSERT INTO items VALUES (180, 'ONE PEACH 1~100巻セット', '集A社', 3, NULL, 16000, 3, false, false, '2021-10-03 12:21:52', NULL, NULL);

SELECT pg_catalog.setval('items_id_seq', 180, true);
INSERT INTO order_details VALUES (1, 1, 9, '破滅の刃', 440, '集A社', NULL, 1, NULL);
INSERT INTO order_details VALUES (3, 1, 18, 'ブラーバジェット m6 エイロボット', 76860, 'エイロボット', NULL, 1, NULL);
INSERT INTO order_details VALUES (2, 1, 20, 'スチーム式加湿器', 19500, 'ZOOJIRUSHI', NULL, 1, NULL);
INSERT INTO order_details VALUES (4, 2, 1, '週刊少林ジャンプ', 390, '集A社', NULL, 1, NULL);
INSERT INTO order_details VALUES (5, 2, 8, 'ONE PEACH', 480, '集A社', NULL, 1, NULL);
INSERT INTO order_details VALUES (6, 2, 17, 'ルンヴァ i3 ロボット掃除機', 99800, 'エイロボット', NULL, 1, NULL);
INSERT INTO order_details VALUES (7, 3, 21, 'ジャンボ グレー 猫用トイレ ディープパン', 3500, 'オーエーティー', NULL, 2, NULL);
INSERT INTO order_details VALUES (8, 3, 25, 'キャットフード インドアキャット シニア 7歳以上', 2473, 'ビルズ・菜園', NULL, 1, NULL);
INSERT INTO order_details VALUES (9, 3, 10, 'シルバー(SV)ネックレス', 8800, '5℃', NULL, 1, NULL);
INSERT INTO order_details VALUES (10, 3, 15, 'スキーグローブ', 102300, 'BIOR', NULL, 1, NULL);
INSERT INTO order_details VALUES (11, 3, 24, '猫用おやつ にゃ～る グルメ まぐろ海鮮バラエティ 14グラム (x 120)', 4480, 'NYAO (ニャオ)', NULL, 2, NULL);
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

INSERT INTO users VALUES (2, 'hanako@example.com', '島根 花子', '島根県○○市○○1-2-3', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', false, '2021-10-02 09:56:42', NULL, NULL);
INSERT INTO users VALUES (1, 'taro@example.com', '鳥取 太郎', '鳥取県○○郡○○町○○1234', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', false, '2021-10-02 09:56:05', NULL, NULL);
INSERT INTO users VALUES (3, 'jiro@example.com', '鳥取 二郎', '鳥取県○○郡○○町○○123-1', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', false, '2021-10-02 09:56:42', NULL, NULL);

SELECT pg_catalog.setval('users_id_seq', 3, true);

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


