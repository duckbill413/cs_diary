Unique 제약 조건을 추가하면 자동으로 인덱스가 생성된다.

1. 테이블 생성
    ```sql
    DROP TABLE IF EXISTS users; # 기존 테이블 삭제

    CREATE TABLE users (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(100) UNIQUE
    );
    ```
2. 인덱스 확인하기
    ```sql
    $ SHOW INDEX FROM users;
    ```

- 이런 특징 때문에 UNIQUE 특징으로 인해 생성되는 인덱스를 보고 **고유 인덱스(Unique Index)**라고 부른다.
- `Unique` 옵션을 사용하면 인덱스가 같이 생성되기 때문에 조회 성능이 향상된다.