# SQL문 튜닝 연습하기
- [SQL문 튜닝 연습하기](#sql문-튜닝-연습하기)
  - [한 번에 너무 많은 데이터를 조회하는 SQL문 튜닝하기](#한-번에-너무-많은-데이터를-조회하는-sql문-튜닝하기)

## 한 번에 너무 많은 데이터를 조회하는 SQL문 튜닝하기
1. 테이블 생성
    ```sql  
    DROP TABLE IF EXISTS users; # 기존 테이블 삭제

    CREATE TABLE users (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(100),
        age INT
    );
    ```
2. 100만 건의 랜덤 데이터 삽입
```sql
-- 높은 재귀(반복) 횟수를 허용하도록 설정
-- (아래에서 생성할 더미 데이터의 개수와 맞춰서 작성하면 된다.)
SET SESSION cte_max_recursion_depth = 1000000; 

-- 더미 데이터 삽입 쿼리
INSERT INTO users (name, age)
WITH RECURSIVE cte (n) AS
(
  SELECT 1
  UNION ALL
  SELECT n + 1 FROM cte WHERE n < 1000000 -- 생성하고 싶은 더미 데이터의 개수
)
SELECT 
    CONCAT('User', LPAD(n, 7, '0')),   -- 'User' 다음에 7자리 숫자로 구성된 이름 생성
    FLOOR(1 + RAND() * 1000) AS age    -- 1부터 1000 사이의 난수로 나이 생성
FROM cte;
```
3. 데이터 조회해 보기
    - 데이터 10,000개 조회하기
      ```sql
      select * from users limit 10000;
      ```
      - 대략 `600ms` 소요
    - 데이터 10개 조회하기
      ```sql
      select * from users limit 10;
      ```
      - 대략 `200ms` 소요

- 조회하는 데이터의 개수가 줄어들 경우 소요시간도 함께 줄어듬을 확인할 수 있다.
- 실제 대부분의 서비스는 한번에 모든 데이터를 불러오는 대신 페이지네이션을 적용하여 일부 데이터만 조회한다.
- 조회하는 데이터의 개수가 성능에 많은 영향을 끼치기 때문이다.
- `LIMIT`, `WHERE` 문 등을 활용하여 조회하는 데이터의 수를 줄이는 방법을 고려해보자.