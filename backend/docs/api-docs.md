FORMAT: 1A
HOST: https://team2017-7.spiral.cloud/api



# Group リソースグループ


## エンドポイント [/path/to/endpoint]

### 簡単な説明 [GET]

#### 処理概要

hoge

* fuga

+ Request (application/json)

	+ Headers

			Accept: application/json

	+ Attributes

		+ hoge: fuga (string, required) - 説明

+ Response 200 (application/json)

	+ Attributes

		+ hoge: fuga (string) - 説明


## エンドポイント2 [/path/to/endpoint2]

### 簡単な説明 [POST]

#### 処理概要

hoge

* fuga

+ Response 201 (application/json)

	+ Headers

			Location: /path/to/resource

	+ Attributes

		+ hoge: fuga (string) - 説明

### 簡単な説明 [PUT]

#### 処理概要

hoge

* fuga

+ Response 201 (application/json)

	+ Headers

			Location: /path/to/resource

	+ Attributes

		+ hoge: fuga (string) - 説明



# Group リソースグループ2


## エンドポイント3 [/path/to/endpoint3]

### 簡単な説明 [DELETE]

#### 処理概要

hoge

* fuga

+ Response 200

+ Response 400 (application/json)
