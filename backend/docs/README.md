# API Documentation

バックエンド側で用意する API の仕様をドキュメント化したものです．


## How to use

`api.html` を適当なブラウザで開いてください．


## Contributing

[API Blueprint](https://apiblueprint.org/) 形式で記述し，[aglio](https://github.com/danielgtaylor/aglio) でレンダリングします．


### 環境構築

1. [Node.js](https://nodejs.org/ja/) のインストール

	最新の Node.js だと挙動がおかしくなります。
	**バージョン 4.x** をインストールしてください。

	Node.js のバージョン管理は適宜 [nvm](https://github.com/creationix/nvm) などを利用すると良いでしょう。

1. [Python](https://www.python.org/) のインストール

	こちらも，最新の Python だとエラーが出ます。
	**バージョン 2.7.x** をインストールしてください。

	Python のバージョン管理は適宜 [pyenv](https://github.com/pyenv/pyenv) などを利用すると良いでしょう。

1. [aglio](https://github.com/danielgtaylor/aglio) のインストール

	`npm` コマンドで入ります。

	```sh
	$ npm install -g aglio
	```

	warning がいっぱい出ますがあまり気にしなくてよい（らしい）です。


### レンダリング

1. `api.md` を編集する

1. aglio でレンダリングする

	```sh
	$ aglio -i api.md -o api.html
	```
