# 실행 계획(EXPAIN)을 활용해 성능 저하 요인 찾아내기
- [실행 계획(EXPAIN)을 활용해 성능 저하 요인 찾아내기](#실행-계획expain을-활용해-성능-저하-요인-찾아내기)
  - [SQL문의 '실행 계획' 사용해 보기 (EXPAIN)](#sql문의-실행-계획-사용해-보기-expain)
    - [실행 계획이란?](#실행-계획이란)
    - [실행 계획을 확인하는 방법](#실행-계획을-확인하는-방법)
    - [실행 계획 사용해보기](#실행-계획-사용해보기)

## SQL문의 '실행 계획' 사용해 보기 (EXPAIN)
### 실행 계획이란?
![alt text](explain-optimizer.png)
- *옵티마이저가 SQL문을 어떤 방식으로 어떻게 처리할 지를 계획한 것*

### 실행 계획을 확인하는 방법
```sql
# 실행 계획 조회
EXPAIN [SQL문]

# 실행 계획에 대한 자세한 정보 조회
EXPAIN ANALYZE [SQL문]
```

### 실행 계획 사용해보기
1. 테이블 생성
    ```sql
    DROP TABLE IF EXISTS users; # 기존 테이블 삭제

    CREATE TABLE users (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(100),
        age INT
    );
    ```
2. 데이터 삽입
    ```sql
    INSERT INTO users (name, age) VALUES
    ('박미나', 26),
    ('김미현', 23),
    ('김민재', 21),
    ('이재현', 24),
    ('조민규', 23),
    ('하재원', 22),
    ('최지우', 22);
    ```
3. 실행 계획 조회하기
  - 실행 계획 조회하기
      ```sql
      EXPLAIN SELECT * FROM users
      WHERE age = 23;
      ```
    - `id`          : 실행계획
    - `select_type` : (처음에는 몰라도 됨)
    - `table`       : 조회한 테이블 명
    - `partitions`  : (처음에는 몰라도 됨)
    - `type`        : **테이블의 데이터를 어떤 방식으로 조회하는 지**
    - `possible_keys` : **사용할 수 있는 인덱스 목록을 출력**
    - `key`         : **데이터 조회할 때 실제로 사용한 인덱스 값**
    - `key_len`     : (처음에는 몰라도 됨)
    - `ref`         : 테이블 조인 상황에서 어떤 값을 기준으로 데이터를 조회했는 지
    - `rows`        : **SQL문 수행을 위해 접근하는 데이터의 모든 행의 수**
    - `filtered`    : 필터 조건에 따라 어느 정도의 비율로 데이터를 제거했는지 의미
      - filtered 값이 30이라면 100개의 데이터 중 30개의 데이터를 실제로 응답한 것
      - filtered 값이 낮을 수록 불필요한 데이터를 많이 불러온 것
    - `Extra`       : **부가적인 정보를 제공**
      - `using where`, `using index`
  - 실행 계획에 대한 자세한 정보 조회
      ```sql
      EXPLAIN ANALYZE SELECT * FROM users
      WHERE age = 23;
      ```
    - `Table scan on users`: users 테이블을 풀 스캔했다.
      - `rows`: 접근한 데이터 행의 수
      - `actual time=0.0437..0.0502`
        - `0.0437`: 첫 번째 데이터에 접근하기까지의 시간
        - `0.0502`: 마지막 데이터에 접근하기까지의 시간
    - `Filter: (users.age = 23)`: 필터링을 통해 데이터를 추출. 필터링 조건은 `users.age = 23`