use("sample_supplies");

// 실습1. Covering Index (=Covered Index) 사용하기
db.sales.find({
  saleDate: {
    $gte: ISODate("2015-01-01"),
  },
});

// 인덱스 생성
db.sales.createIndex({ saleDate: 1 });

db.sales
  .find({
    saleDate: {
      $gte: ISODate("2015-01-01"),
    },
  })
  .explain("executionStats");

// 키만 사용해서도 데이터를 조회할 수 있는데
// 실행 계획을 보면 stage: 'FETCH' 단계가 일어나고 있다.
// Covering 인덱스를 사용해서 이 단계를 생략할 수 있으며, Projection 을 이용

db.sales
  .find(
    {
      saleDate: {
        $gte: ISODate("2015-01-01"),
      },
    },
    { _id: 0, saleDate: 1 }
  )
  .explain("executionStats");

// Stage 를 보면 'PROJECTION_COVERED' stage로 FETCH stage가 대체됨

// 실습2. 복합 필드를 생성하여 Cardinality 높이기
// storeLocation이 'Denver' 인 지역에서 75세인 사람들이 얼마나 방문했는지 확인하는 쿼리
db.sales
  .find({
    storeLocation: "Denver",
    "customer.age": { $eq: 75 },
  })
  .explain("executionStats");

```
COLLSCAN이 발생했으며
5000개의 데이터를 조회하여 11개의 데이터를 리턴함
```;

// 복합 인덱스 생성
db.sales.createIndex({
  storeLocation: 1,
  "customer.age": 1,
});

// 다시 실행 계획 확인
db.sales
  .find({
    storeLocation: "Denver",
    "customer.age": { $eq: 75 },
  })
  .explain("executionStats");

// 인덱스 스캔을 사용했으며
// 11개의 데이터를 읽고 그대로 반환한 것을 확인할 수 있음

// 실습 3. 지역기반 쿼리 사용하기
use("sample_restaurants");

db.restaurants.aggregate([
  {
    $geoNear: {
      near: { type: "Point", coordinates: [-73.8601152, 40.7311739] },
      key: "address.coord",
      maxDistance: 30000,
      query: {
        cuisine: "Jewish/Kosher",
      },
      distanceField: "dist",
    },
  },
  {
    $project: {
      name: 1,
      cuisine: 1,
      dist: 1, // 현 위치와 사이의 거리
    },
  },
]);

// 실행 계획 확인
explain = db.restaurants
  .aggregate([
    {
      $geoNear: {
        near: { type: "Point", coordinates: [-73.8601152, 40.7311739] },
        key: "address.coord",
        maxDistance: 30000,
        query: {
          cuisine: "Jewish/Kosher",
        },
        distanceField: "dist",
      },
    },
    {
      $project: {
        name: 1,
        cuisine: 1,
        dist: 1, // 현 위치와 사이의 거리
      },
    },
  ])
  .explain("executionStats");

// 인덱스를 이용하여 성능 개선해보기
// 1. cuisine 필드를 복합 인덱스로 생성
// 2. 인덱스 키로 필터링하는 범위를 줄이기
db.restaurants.createIndex({
  cuisine: 1,
  "address.coord": "2dsphere",
});

// 다시 실행 계획을 조회했을때 성능이 확실히 개선됨
// 2dsphere을 사용할때 복합인덱스를 사용하는 것이 성능 향상에 매우 도움이 된다는 것을 알 수 있음
