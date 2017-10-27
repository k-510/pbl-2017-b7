FORMAT: 1A
HOST: https://team2017-7.spiral.cloud/api



# Group 最低限の API


## 依頼情報リソース [/requests{?id}]


### 依頼情報の登録 [POST]

#### 処理概要

データベースに依頼情報を新しく登録します．

+ Request (application/json)

	+ Headers

			Accept: application/json

	+ Attributes

		+ session_id: agk4ea6vrf1v2kav32 (string, required) - セッションID

		+ shop_id: k682891 (string, required) - ぐるナビの店舗ID

		+ datetime: `2017-10-27T15:00:00.000Z` (string, required) - 代行人に到着して欲しい時間

		+ condition: テーブルマナーがある人 (string, optional) - 募集する代行人の条件

		+ deadline: `2017-11-01T12:00:00.000Z` (string, optional) - 代行人の募集期限

+ Response 201

	+ Headers

			Location: /requests?id=1

+ Response 400


### 依頼情報の一覧の取得 [GET]

#### 処理概要

データベースに登録されている依頼情報の一覧を取得します．

+ Request (application/json)

	+ Headers

			Accept: application/json

+ Response 200 (application/json)

	+ Attributes

		+ requests (array)

			+ request_id: 1 (number) - 依頼情報 ID

			+ shop_id: k682891 (string) - ぐるナビの店舗ID

			+ datetime: `2017-10-27T15:00:00.000Z` (string) - 代行人に到着して欲しい時間

			+ condition: テーブルマナーがある人 (string) - 募集する代行人の条件

			+ deadline: `2017-11-01T12:00:00.000Z` (string) - 代行人の募集期限


### 依頼情報の取得 [GET]

#### 処理概要

データベースに登録されている依頼情報を取得します．

+ Parameters

	+ id: 1 (number, required) - A path variable that is required for a valid URL

+ Requests

	+ Headers

			Accept: application/json

+ Response 200 (application/json)

	+ Attributes

		+ request_id: 1 (number) - 依頼情報 ID

		+ shop_id: k682891 (string) - ぐるナビの店舗ID

		+ datetime: `2017-10-27T15:00:00.000Z` (string) - 代行人に到着して欲しい時間

		+ condition: テーブルマナーがある人 (string) - 募集する代行人の条件

		+ deadline: `2017-11-01T12:00:00.000Z` (string) - 代行人の募集期限
