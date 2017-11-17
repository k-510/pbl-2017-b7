# 食代（くいしろ）

<img src="https://github.com/Cloud-Spiral/pbl-2017-b7/blob/master/src/main/webapp/images/logo-main.png?raw=true" width="200px">

食事代行サービスです．

* インスタ映えする写真を撮ったけどご飯食べきれない！
* 有名なあのお店のメニューを安く食べてみたい！

そんなアナタ．うまくマッチングすれば，希望が叶うかもしれません．


## サービスへのアクセス

AWS 上にデプロイしています．以下の URL にアクセスしてください：

https://team2017-7.spiral.cloud/pbl-2017-b7/

Cloud Spiral の授業が終了し，AWS のインスタンスが削除された後は，  
後述の[ローカル環境へのデプロイ](#ローカル環境へのデプロイ)に従ってお手元でお楽しみください．


### 使い方

[<img src="https://github.com/Cloud-Spiral/pbl-2017-b7/blob/master/screenshots/my-top.png?raw=true" width="500px">](https://github.com/Cloud-Spiral/pbl-2017-b7/wiki/%E3%82%B5%E3%83%BC%E3%83%93%E3%82%B9%E3%81%AE%E4%BD%BF%E3%81%84%E6%96%B9)

**詳しいサービスの利用方法は，[Wiki のサービスの使い方](https://github.com/Cloud-Spiral/pbl-2017-b7/wiki/%E3%82%B5%E3%83%BC%E3%83%93%E3%82%B9%E3%81%AE%E4%BD%BF%E3%81%84%E6%96%B9)をご覧ください．**


## ローカル環境へのデプロイ

1. このリポジトリを clone します．

1. [Tomcat](http://tomcat.apache.org/) および [MongoDB](https://www.mongodb.com/) を起動します．

1. [database/dump](https://github.com/Cloud-Spiral/pbl-2017-b7/tree/master/database/dump) ディレクトリにあるテスト用データで DB を初期化します：

    ```sh
    $ mongorestore --drop database/dump
    ```

1. Eclipse に Gradle プロジェクトとしてインポートします．

1. Gradle タスクから deployLocal します．

1. `http://localhost:8080/pbl-2017-b7/` に Web ブラウザからアクセスします．

[当リポジトリの Wiki](https://github.com/Cloud-Spiral/pbl-2017-b7/wiki/%E3%83%AD%E3%83%BC%E3%82%AB%E3%83%AB%E9%96%8B%E7%99%BA%E7%92%B0%E5%A2%83%E3%81%AE%E6%A7%8B%E7%AF%89%E6%96%B9%E6%B3%95) の情報も参照してください．


## (参考) AWS へのデプロイ

諸事情により Jenkins 等の CI ツールは設定していません．  
手動デプロイになります．

`/usr/share/tomcat8/webapps/` に `pbl-2017-b7.war` を配置するだけです．  
`pbl-2017-b7.war` はローカル環境にデプロイした際に `build/libs/` に作成されます．  
`scp` 等で AWS 側にコピーしてください．

またテストデータも同様にコピーし，`mongorestore` してください．
