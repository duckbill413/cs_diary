// 6.06_Change Streams.py 를 실행한 상태에서
// 아래의 쿼리를 실행해 보며
// Change Streams 의 동작을 확인

use("test")

// test db 에 데이터 삽입 테스트
db.test.insertOne({a: 2})

// pipeline 을 이용한 aggregation 사용
db.test.insertOne({a:2, status: "argent"})