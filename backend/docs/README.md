# API Documentation

バックエンド側で用意する API の仕様をドキュメント化したものです．

## How to use

`api-doc.html` を適当なブラウザで開いてください．

## Contributing

[API Blueprint](https://apiblueprint.org/) 形式で記述し，[aglio](https://github.com/danielgtaylor/aglio) でレンダリングします．

### 環境構築

1. [Node.js](https://nodejs.org/ja/) をインストールする

1. aglio をインストールする

	```sh
	$ npm install -g aglio
	```

1. `api-doc.md` を編集する

1. aglio でレンダリングする

	```sh
	$ aglio -i api-doc.md -o api-doc.html
	```
