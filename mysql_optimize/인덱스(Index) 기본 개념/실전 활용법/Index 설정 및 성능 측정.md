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
       FLOOR(1 + RAND() * 1000) AS age    -- 1부터 1000 사이의 랜덤 값으로 나이 생성
   FROM cte;

   -- 잘 생성됐는 지 확인
   SELECT COUNT(*) FROM users;
   ```

3. 데이터 조회해보기

   ```sql
   SELECT * FROM USERS
   WHERE age = 23;
   ```

4. SQL 실행시 걸리는 시간 측정
   ![SQL TIME LATE](<select 성능 확인.jpg>)

   - 대략 `520ms` 소요

5. 인덱스 설정하기
   - 인덱스 생성
     ```SQL
     # 인덱스 생성
     # CREATE INDEX 인덱스명 ON 테이블명 (컬럼명);
     CREATE INDEX idx_age ON users(age);
     ```
   - 인덱스 조회
     ```SQL
     # SHOW INDEX FROM 테이블명;
     SHOW INDEX FROM users;
     ```

6. 데이터 다시 조회해보고 SQL 실행 소요 시간 측정
   ```SQL
   SELECT * FROM users
   WHERE age = 23;
   ```
   ![index 성능 확인](<index 성능 확인.png>)
   - `22ms` 소요 기존 (`520ms`) 보다 확연하게 성능이 향상됨

- index 직접 생성하게 되면 인덱스 표가 내부적으로 생성됨
- 나이를 기준으로 정렬한 표를 가지고 있기 때문에, `나이`를 기준으로 조회할 때 빠르게 찾을 수 있음
