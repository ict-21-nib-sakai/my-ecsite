# 模擬的なECサイト
Java言語の学習およびその成果物であり、備忘録です。

ソースコード等に登場する商品名やメーカー名は架空のものです。
なお、実在する名称および類似する名称が含まれる場合がありますが、意図したものではありません。

## 環境
- Java SE 8
- PostgreSQL

## 恒久的な制約
- MVC パターンは死守する

## 当面の制約
- 素のJava言語の学習のためフレームワークは用いない。（正直データベース周りはけっこう苦しい。）

## 目標
- 商品一覧
- 検索
  - キーワード
  - カテゴリー
- 商品詳細
- ログイン
- カート内一覧
- 支払い方法、配達先の選択と入力
- 購入前の確認ページ
- お買い上げありがとうございます

店舗側は未定。
ひとまずの目標はこれらを、ひとりで実装する。

## 意識していること
- 簡潔なソースコード
  - 腐敗せず異臭のしないソースコード。
  - 書いていて、ひとつのメソッドに詰め込みがちになったら、粒度を小さくすること。
- オリジナルなフレームワークを書くような感じ。
  - と言ってもあまり精巧に作りすぎないこと。(世の中には Spring フレームワークという素晴らしいものがあるので。)
- やや Laravel Framework を懐かしく思い、そして意識している。

## こんな機能がほしい
より実用的なものに近づけるための提案があれば、ご遠慮なく issue に記載してください。
ただし、実装できるとは限りません。

## お手伝い・助言をいただける人
どうぞご遠慮なく。コメントください。
