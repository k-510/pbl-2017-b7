FORMAT: 1A
HOST: https://team2017-7.spiral.cloud/api


# Group Future works

時間があれば実装したい API．
サービス動作に必須ではないため，優先順位は低め．


## ユーザの依頼情報リソース [/user/requests/{id}]

### 依頼情報の削除 [DELETE]

`{id}` で指定した依頼情報を削除します．

+ Parameters

    + id: 1 (number, required) - 依頼情報 ID

+ Request

    + Headers

            Authorization: Session {token}

+ Response 204

+ Response 403 (application/json)

    指定した `{id}` の依頼情報を登録したユーザがログインユーザではない場合．

    + Attributes

        + error: `The request is not yours.` (string)

+ Response 404 (application/json)

    指定した `{id}` の依頼情報が存在しなかった場合．

    + Attributes

        + error: `The resource you were looking for could not be found.` (string)


## ユーザ登録リソース [/users/signin]

### ユーザ情報の登録 [POST]

サービスに新しくユーザを登録します．

詳細はまだ考えてません．
