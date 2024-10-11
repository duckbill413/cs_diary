// 1. TTL Indexes
use("test");

// 예제 데이터 삽입
db.ttl.insertMany([
  {
    msg: "Hello!",
    time: new ISODate(),
  },
  {
    msg: "HelloWorld!",
    time: new ISODate(),
  },
]);

// 1분이 지나면 삭제되도록 TTL index 생성
db.ttl.createIndex({ time: 1 }, { expireAfterSeconds: 60 });

// ttl index 생성 확인
db.ttl.getIndexes();

/**
 * 데이터 확인
 * ttl 백그라운드 작업은 60s 에 한번씩 데이터를 확인 및
 * 지워야할 데이터를 삭제한다.
 */
db.ttl.find();

//-------------------------------------------------
// 2. Unique Indexes

// Unique Index 생성
db.unique.createIndex({ name: 1 }, { unique: true });

// 예제 데이터 추가
db.unique.insertMany([{ name: "tom" }, { name: "john" }]);

// Non-Unique 예제 데이터 추가
db.unique.insertOne({ name: "tom" });
/**
 * 실행 결과
 * MongoServerError: E11000 duplicate key error collection: test.unique index: name_1 dup key: { name: "tom" }
 * 위와 같은 에러가 발생
 */

// 인덱스 삭제
db.unique.dropIndex({ name: 1 });

// Compound Index 에 대한 Unique Index 생성
db.unique.createIndex(
  {
    name: 1,
    age: 1,
  },
  {
    unique: true,
  }
);

// 데이터 추가
db.unique.insertOne({
  name: "james",
  age: 23,
});

// age 만 변경하여 데이터 추가(성공)
db.unique.insertOne({
  name: "james",
  age: 28,
});

//-------------------------------------------------
// 3. Sparse Indexes

// 예제 데이터 생성
db.sparse.insertMany([{ x: 1 }, { x: 2 }, { y: 1 }]);

// sparse index 생성
// y에 대해서는 indexing이 되지 않음
db.sparse.createIndex({ x: 1 }, { sparse: true });

db.sparse.find().hint({ x: 1 }); // x 인덱스를 강제로 사용
// 결과적으로 x 속성이 있는 데이터만 출력됨

// index drop
db.sparse.dropIndex({ x: 1 });
// Partial Indexes
db.sparse.createIndex(
  { x: 1 },
  { partialFilterExpression: { x: { $exists: true } } }
);

// hint() 로 index 사용
db.sparse.find().hint({ x: 1 });

// 기존의 index drop
db.sparse.dropIndex({ x: 1 });

// Partial Index 의 추가 속성 활용하기
db.sparse.createIndex(
  { x: 1 },
  {
    partialFilterExpression: {
      x: { $exists: true },
      x: { $gte: 2 },
    },
  }
);

// hint() 로 index 사용
db.sparse.find().hint({ x: 1 });

//-------------------------------------------------
// 4. Hidden Indexes

// 예제 데이터 생성
db.hidden.insertOne({ a: 1 });
db.hidden.insertOne({ a: 2 });

// hidden index 생성
db.hidden.createIndex({ a: 1 }, { hidden: true });

// 실행 계획 확인
db.hidden.find().explain("executionStats");
// COLLSCAN 을 사용하는 것을 확인

// Hidden index 해제
db.hidden.unhideIndex({ a: 1 });
// possibility: collMod 권한 문제가 발생할 수 있음
// Specific Privilege 의 권한으로 dbAdmin, backup 권한을 부여하면 가능
