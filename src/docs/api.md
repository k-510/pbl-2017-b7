FORMAT: 1A
HOST: https://team2017-7.spiral.cloud/api


<!-- JSON データの構造を定義 -->

# Data Structures

## RequestBase (object)

+ shop_id: `k682891` (string) - ぐるナビの店舗ID

+ datetime: `2017-11-01T12:00:00.000Z` (string) - 代行人に到着して欲しい時間

+ condition: `テーブルマナーがある人` (string) - 募集する代行人の条件

+ deadline: `2017-10-27T15:00:00.000Z` (string) - 代行人の募集期限

+ budget: 1000 (number) - 予算


<!-- 以下から API 仕様を定義 -->

# Group 非ユーザ依存リソース

セッション管理を必要としない，ログインユーザに依存しないリソースです．
どのユーザがリクセストしても同じレスポンスが返ります．


## すべての依頼リソース [/requests]

### 依頼一覧の取得 [GET]

サービスに登録されているすべての依頼を取得します．

レスポンス中の `status` は依頼の進捗状態を表します：

| 状態 | `status` の値 |
|-|-|
| 代行人が決まっていない | `new` |
| 代行人が決まっている | `accepted` |
| 依頼が完了している | `completed` |

+ Request

    + Headers

            Accept: application/json

+ Response 200 (application/json)

    + Attributes (array)

        + (RequestBase)

            + request_id: 1 (number) - 依頼 ID

            + client_id: 1 (number) - 依頼人のユーザ ID

            + surrogate_id: 2 (number) - 代行人のユーザ ID

            + status: `accepted` (string) - 依頼の進捗状態


## 単一の依頼リソース [/requests/{id}]

### 指定した依頼の取得 [GET]

サービスに登録されている依頼のうち，`{id}` で指定した依頼を取得します．

レスポンス中の `status` は依頼の進捗状態を表します：

| 状態 | `status` の値 |
|-|-|
| 代行人が決まっていない | `new` |
| 代行人が決まっている | `accepted` |
| 依頼が完了している | `completed` |

+ Parameters

    + id: 1 (number, required) - 依頼 ID

+ Request

    + Headers

            Accept: application/json

+ Response 200 (application/json)

    + Attributes (RequestBase)

        + request_id: 1 (number) - 依頼 ID

        + client_id: 1 (number) - 依頼人のユーザ ID

        + surrogate_id: 2 (number) - 代行人のユーザ ID

        + status: `accepted` (string) - 依頼の進捗状態

+ Response 404 (application/json)

    指定した `{id}` の依頼が存在しなかった場合．

    + Attributes

        + error: `The resource you were looking for could not be found.` (string)


# Group ユーザ依存リソース

セッション管理を必要とする，ログインユーザに依存したリソースです．
リクエストを送るユーザにより，レスポンスの内容が変化します．

以下のリクエストでは，`Authorization` ヘッダに次の形式でセッションのトークンを付与しなければなりません．
トークンは[ログイン](#セッション管理リソース-ログインリソース)処理で取得します．
トークンがない場合は `403 Forbidden` が返ります．

```
Authorization: Session {token}
```


## ユーザの依頼リソース [/user/requests{?type}]

### 依頼の登録 [POST]

ログインユーザとして，サービスに依頼を新しく登録します．

+ Request

    + Headers

            Accept: application/json

            Authorization: Session {token}

    + Attributes (RequestBase)

+ Response 201 (application/json)

    + Headers

            Location: /api/requests/1

    + Attributes (RequestBase)

        + request_id: 1 (number) - 依頼 ID

        + client_id: 1 (number) - 依頼人のユーザ ID

+ Response 400 (application/json)

    リクエストに必要なパラメータが含まれていなかった場合．

    + Attributes

        + error: `The request you sent lacks a requied parameter.` (string)

### 登録した依頼一覧の取得 [GET]

ログインユーザが依頼人として登録した，すべての依頼を取得します．
代行人がまだ存在しない場合，レスポンスの `surrogate_id` は `null` となります．

+ Parameters

    + type: `registered` (string, required)

+ Request

    + Headers

            Accept: application/json

            Authorization: Session {token}

+ Response 200 (application/json)

    + Attributes (array)

        + (RequestBase)

            + request_id: 1 (number) - 依頼 ID

            + client_id: 1 (number) - 依頼人のユーザ ID

            + surrogate_id: 2 (number, nullable) - 代行人のユーザ ID

+ Response 400 (application/json)

    リクエストに不正なパラメータが含まれていた場合．

    + Attributes

        + error: `The request you sent contents an invalid parameter.` (string)

### 受諾した依頼一覧の取得 [GET]

ログインユーザが代行人として受諾した，すべての依頼を取得します．

+ Parameters

    + type: `accepted` (string, required)

+ Request

    + Headers

            Accept: application/json

            Authorization: Session {token}

+ Response 200 (application/json)

    + Attributes (array)

        + (RequestBase)

            + request_id: 1 (number) - 依頼 ID

            + client_id: 1 (number) - 依頼人のユーザ ID

            + surrogate_id: 2 (number) - 代行人のユーザ ID

+ Response 400 (application/json)

    リクエストに不正なパラメータが含まれていた場合．

    + Attributes

        + error: `The request you sent contents an invalid parameter.` (string)


## 依頼の受諾リソース [/user/requests/{id}/acceptance]

### 指定した依頼を受諾する [PUT]

ログインユーザが代行人として，`{id}` で指定した依頼を受諾します．

+ Parameters

    + id: 1 (number, required) - 依頼 ID

+ Request

    + Headers

            Authorization: Session {token}

+ Response 204

+ Response 404 (application/json)

    指定した `{id}` の依頼が存在しなかった場合．

    + Attributes

        + error: `The resource you were looking for could not be found.` (string)

+ Response 409 (application/json)

    指定した `{id}` の依頼にすでに他の代行人が登録されていた場合．

    + Attributes

        + error: `The request you want to accept has another surrogate already.` (string)


## 依頼の完了リソース [/user/requests/{id}/completion]

### 指定した依頼を完了させる [PUT]

ログインユーザが依頼人として，`{id}` で指定した依頼を完了させます．

+ Parameters

    + id: 1 (number, required) - 依頼 ID

+ Request

    + Headers

            Authorization: Session {token}

+ Response 204

+ Response 403 (application/json)

    ログインユーザが，`{id}` の依頼を登録したユーザではない場合．

    + Attributes

        + error: `The request you want to make complete is not registered by you.` (string)

+ Response 404 (application/json)

    指定した `{id}` の依頼が存在しなかった場合．

    + Attributes

        + error: `The resource you were looking for could not be found.` (string)


## チャットリソース [/user/requests/{id}/chats]

### 指定した依頼のチャットログを取得する [GET]

`{id}` で指定した依頼のチャットログを取得します．

ログインユーザは，この依頼を登録したユーザか，受諾したユーザでなければいけません．

+ Parameters

    + id: 1 (number, required) - 依頼 ID

+ Request

    + Headers

            Accept: application/json

            Authorization: Session {token}

+ Response 200 (application/json)

    + Attributes (array)

        + (object)

            + user_id: 1 (number) - 発言したユーザのユーザ ID

            + message: `Hello.` (string) - チャットメッセージ

+ Response 403 (application/json)

    ログインユーザが，`{id}` の依頼を登録したユーザあるいは受諾したユーザのどちらでもない場合．

    + Attributes

        + error: `The request whose chat log you want to get is neither registered nor accepted by you.` (string)

+ Response 404 (application/json)

    指定した `{id}` の依頼が存在しなかった場合．

    + Attributes

        + error: `The resource you were looking for could not be found.` (string)

### 指定した依頼のチャットに投稿する [POST]

`{id}` で指定した依頼のチャットに投稿します．

ログインユーザは，この依頼を登録したユーザか，受諾したユーザでなければいけません．

+ Parameters

    + id: 1 (number, required) - 依頼 ID

+ Request

    + Headers

            Accept: application/json

            Authorization: Session {token}

    + Attributes

        + message: `Hello.` (string) - チャットメッセージ

+ Response 200 (application/json)

    + Attributes

        + message: `Hello.` (string) - チャットメッセージ

+ Response 403 (application/json)

    ログインユーザが，`{id}` の依頼を登録したユーザあるいは受諾したユーザのどちらでもない場合．

    + Attributes

        + error: `The request whose chat log you want to get is neither registered nor accepted by you.` (string)

+ Response 404 (application/json)

    指定した `{id}` の依頼が存在しなかった場合．

    + Attributes

        + error: `The resource you were looking for could not be found.` (string)


# Group セッション管理リソース

セッション周りの API です．
以下の API を用いてセッション管理を行います．

ログインで発行したセッショントークンは，もう一度ログインするか，明示的にログアウトするまで有効です．

## ログインリソース [/login]

### ログインする [POST]

ログインし，ログインユーザ用のセッショントークンを発行します．

当該ユーザに発行済みのセッショントークンが存在する場合は，発行済みのトークンは無効となり，以降は新しく発行するトークンが有効になります．

+ Request

    + Headers

            Accept: application/json

    + Attributes

        + user_id: 1 (number) - ユーザ ID

        + password: `my_password` (string) - パスワード

+ Response 201 (application/json)

    + Attributes

        + session_token: `1k5h10348dsaf5321...` (string) - セッショントークン

+ Response 400 (application/json)

    リクエストに必要なパラメータが含まれていなかった場合．

    + Attributes

        + error: `The request you sent lacks a requied parameter.` (string)

+ Response 403 (application/json)

    ユーザ ID またはパスワードが一致しなかった場合．

    + Attributes

        + error: `Incorrect username or password.` (string)


## ログアウトリソース [/logout]

### ログアウトする [DELETE]

明示的にログアウトし，セッションを破棄します．

+ Request

    + Headers

            Authorization: Session {token}

+ Response 204


# Group サーバエラー発生時

すべての API について，サーバ側でエラーが発生した場合，`500 Internal Server Error` を返します．
