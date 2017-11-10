# API Documentation

バックエンド側で用意する API の仕様をドキュメント化したものです．  
モックサーバによるダミーデータの返却にも対応しています．


## ドキュメントについて

### 確認方法

`api.html` を適当なブラウザで開いてください．

### 編集方法

[API Blueprint](https://apiblueprint.org/) 形式で記述し，[aglio](https://github.com/danielgtaylor/aglio) でレンダリングします．

#### 環境構築

1. [Node.js](https://nodejs.org/ja/) のインストール

    最新の Node.js だと挙動がおかしくなります．
    **バージョン 4.x** をインストールしてください．

1. [Python](https://www.python.org/) のインストール

    こちらも，最新の Python だとエラーが出ます．
    **バージョン 2.7.x** をインストールしてください．

1. [aglio](https://github.com/danielgtaylor/aglio) のインストール

    `npm` コマンドで入ります．

    ```sh
    $ npm install -g aglio
    ```

    warning がいっぱい出ますがあまり気にしなくてよい（らしい）です．

#### レンダリング

1. `api.md` を編集する

1. aglio でレンダリングする

    ```sh
    $ aglio -i api.md -o api.html
    ```


## モックサーバについて

[drakov](https://github.com/Aconex/drakov) を用います．

#### 環境構築

1. [drakov](https://github.com/Aconex/drakov) のインストール

    `npm` コマンドで入ります．

    ```sh
    $ npm install -g drakov
    ```

#### モックサーバの利用

1. モックサーバの立ち上げ

    ```sh
    $ drakov -f api.md
    ```

1. cURL 等でリクエストを送信

    ```sh
    $ curl localhost:3000/requests -H 'Accept: application/json'
    [
      {
        "shop_id": "k682891",
        "datetime": "2017-10-27T15:00:00.000Z",
        "condition": "テーブルマナーがある人",
        "deadline": "2017-11-01T12:00:00.000Z",
        "request_id": 1,
        "client_id": 1,
        "surrogate_id": 2
      }
    ]
    ```

    ```sh
    $ curl localhost:3000/user/requests -X POST \
    -H 'Content-Type: application/json' \
    -H 'Accept: application/json' \
    -H 'Authorization: Session {token}' \
    -d '{"shop_id": "k682891", "datetime": "2017-10-27T15:00:00.000Z", "condition": "テーブルマナーがある人", "deadline": "2017-11-01T12:00:00.000Z"}'
    {
      "shop_id": "k682891",
      "datetime": "2017-10-27T15:00:00.000Z",
      "condition": "テーブルマナーがある人",
      "deadline": "2017-11-01T12:00:00.000Z",
      "request_id": 1,
      "client_id": 1
    }
    ```
