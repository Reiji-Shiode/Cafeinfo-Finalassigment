# カフェ情報管理システム

![Cafe picture and famous quote](..%2F..%2FLibrary%2FContainers%2Fcom.apple.Notes%2FData%2Ftmp%2FTemporaryItems%2FNSIRD_%E3%83%A1%E3%83%A2_MpdDX3%2FHardLinkURLTemp%2F2DB36684-9E74-4D63-86CE-FD7EDFD0EBAE%2F1718524736%2FThe%20Warden.jpeg)

***

## 概要

勉強や作業、友人とカフェを利用をする際などその場の状況に適したカフェ情報を効率的に管理するためのツールです。  
本システムはCRUD処理（CREATE、READ、UPDATE、DELETE）を用いたAPIを中心に構築されており、カフェ情報を登録、更新、取得、削除することができます。  
更に、直感的なインターフェースにより簡単にカフェ情報を管理でき、カフェ探しのストレスを軽減します。

## 構成要件

* Java 17
* SpringBoot 3.2.5
* MySQL 8.0.37
* Docker 26.0.0
* JUnit
* Mockito

## 機能一覧

- CREATE処理
    - 新規取得
        - 新しいカフェ情報を登録する
- READ処理
    - 全件取得
        - レコードを全件取得する
    - 絞り込み検索
        - クエリ文字列を使用して特定の場所を記入すると該当するレコードを取得できる
        - IDを検索し該当するレコードを取得できる
    - 例外処理
        - 存在しない場所を記入した際に空の配列がレスポンスされる
        - 存在しないIDをリクエストした場合の例外ハンドリング
- UPDATE処理
    - レコードの更新
        - 指定したIDのカフェ情報を更新する
    - 例外処理
        - 存在しないIDのカフェ情報を更新しようとした場合の例外ハンドリング
- DELETE処理
    - レコードの削除
        - 指定したIDのカフェ情報を削除する
    - 例外処理
        - 存在しないIDのカフェ情報を削除しようとした場合の例外ハンドリング

## DBテーブル

|    **カラム名**     |   **データ型**   | **NotNull** | **キー** | **備考**  |
|:---------------:|:------------:|:-----------:|:------:|:-------:|
|       id        |     int      |  NOT NULL   |  主キー   | ID,自動生成 |
|      name       | VARCHAR(100) |  NOT NULL   |        |   店名    |
|      place      | VARCHAR(40)  |  NOT NULL   |        |  最寄り駅   |
| regular_holiday | VARCHAR(50)  |  NOT NULL   |        |   定休日   |
|  opening_hour   | VARCHAR(50)  |  NOT NULL   |        |  営業時間   |
| number_of_seat  |     int      |  NOT NULL   |        |   席数    |
|   birthplace    | VARCHAR(30)  |  NOT NULL   |        |   発祥地   |

## 実行結果

### CREATE処理

- **POST / cafes** 新しいカフェ情報を登録する **(HTTPステータスコード201)**  
  ![スクリーンショット 2024-05-30 22.38.19.png](..%2F..%2FDesktop%2F%E3%82%B9%E3%82%AF%E3%83%AA%E3%83%BC%E3%83%B3%E3%82%B7%E3%83%A7%E3%83%83%E3%83%88%202024-05-30%2022.38.19.png)

### READ処理

- **GET / cafes** レコードを全件取得する **(HTTPステータスコード200)**  
  ![スクリーンショット 2024-05-27 19.36.53.png](..%2F..%2FDesktop%2F%E3%82%B9%E3%82%AF%E3%83%AA%E3%83%BC%E3%83%B3%E3%82%B7%E3%83%A7%E3%83%83%E3%83%88%202024-05-27%2019.36.53.png)

- **GET / cafes?place=◯** クエリ文字列を使用して特定の場所を記入すると該当するレコードを取得できる **(
  HTTPステータスコード200)**  
  ![スクリーンショット 2024-05-11 16.27.29.png](..%2F..%2FDesktop%2F%E3%82%B9%E3%82%AF%E3%83%AA%E3%83%BC%E3%83%B3%E3%82%B7%E3%83%A7%E3%83%83%E3%83%88%202024-05-11%2016.27.29.png)

- **GET / cafes/{id}** IDを検索し該当するレコードを取得できる **(HTTPステータスコード200)**  
  ![334075033-45d21055-2a4a-44f3-a420-5e85b28aeeb6.png](..%2F334075033-45d21055-2a4a-44f3-a420-5e85b28aeeb6.png)

- 存在しない場所を記入した際に空の配列がレスポンスされる **(HTTPステータスコード200)**  
  ![334143858-1fdbc498-538e-40e0-8db4-1eaa6f599b5a-2.png](..%2F334143858-1fdbc498-538e-40e0-8db4-1eaa6f599b5a-2.png)

- 存在しないIDをリクエストした場合の例外ハンドリング **(HTTPステータスコード404)**  
  ![334075318-de49e745-de1c-4525-83a8-a38bd9f610c2.png](..%2F334075318-de49e745-de1c-4525-83a8-a38bd9f610c2.png)

### UPDATE処理

- **PATCH / cafes/{id}** 指定したIDのカフェ情報を更新する **(HTTPステータスコード200)**  
  ![スクリーンショット 2024-05-31 19.12.04.png](..%2F..%2FDesktop%2F%E3%82%B9%E3%82%AF%E3%83%AA%E3%83%BC%E3%83%B3%E3%82%B7%E3%83%A7%E3%83%83%E3%83%88%202024-05-31%2019.12.04.png)

- 存在しないIDのカフェ情報を更新しようとした場合の例外ハンドリング **(HTTPステータスコード404)**  
  ![スクリーンショット 2024-05-31 19.12.22.png](..%2F..%2FDesktop%2F%E3%82%B9%E3%82%AF%E3%83%AA%E3%83%BC%E3%83%B3%E3%82%B7%E3%83%A7%E3%83%83%E3%83%88%202024-05-31%2019.12.22.png)

### DELETE処理

- **DELETE / cafes/{id}** 指定したIDのカフェ情報を削除する **(HTTPステータスコード200)**  
  ![スクリーンショット 2024-06-01 16.26.59.png](..%2F..%2FDesktop%2F%E3%82%B9%E3%82%AF%E3%83%AA%E3%83%BC%E3%83%B3%E3%82%B7%E3%83%A7%E3%83%83%E3%83%88%202024-06-01%2016.26.59.png)

- 存在しないIDのカフェ情報を削除しようとした場合の例外ハンドリング **(HTTPステータスコード404)**  
  ![スクリーンショット 2024-06-01 16.29.26.png](..%2F..%2FDesktop%2F%E3%82%B9%E3%82%AF%E3%83%AA%E3%83%BC%E3%83%B3%E3%82%B7%E3%83%A7%E3%83%83%E3%83%88%202024-06-01%2016.29.26.png)

（以下課題が進むにつれて随時内容を記載していきます）
