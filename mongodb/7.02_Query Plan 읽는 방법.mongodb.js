use("sample_restaurants");

// queryPlanner mode
db.restaurants.find({ borough: "Brooklyn" }).explain();

// executionStats mode
db.restaurants.find({ borough: "Brooklyn" }).explain("executionStats");

// executionStats mode 실행 결과중 실행 계획
```
executionStats: {
  executionSuccess: true,
  nReturned: 6086,
  executionTimeMillis: 31,
  totalKeysExamined: 0,
  totalDocsExamined: 25359,
  executionStages: {
    stage: 'COLLSCAN',
    filter: {
      borough: {
        '$eq': 'Brooklyn'
      }
    },
    nReturned: 6086,
    executionTimeMillisEstimate: 6,
    works: 25360,
    advanced: 6086,
    needTime: 19273,
    needYield: 0,
    saveState: 25,
    restoreState: 25,
    isEOF: 1,
    direction: 'forward',
    docsExamined: 25359
  }
}
```;

db.restaurants
  .find({ borough: "Brooklyn" }, { name: 1, borough: 1 })
  .sort({ name: 1 })
  .explain("executionStats");

```
executionStats: {
  executionSuccess: true,
  nReturned: 6086,
  executionTimeMillis: 85,
  totalKeysExamined: 0,
  totalDocsExamined: 25359,
  executionStages: {
    stage: 'SORT',
    nReturned: 6086,
    executionTimeMillisEstimate: 52,
    works: 31447,
    advanced: 6086,
    needTime: 25360,
    needYield: 0,
    saveState: 32,
    restoreState: 32,
    isEOF: 1,
    sortPattern: {
      name: 1
    },
    memLimit: 33554432,
    type: 'simple',
    totalDataSizeSorted: 778083,
    usedDisk: false,
    spills: 0,
    spilledDataStorageSize: 0,
    inputStage: {
      stage: 'PROJECTION_SIMPLE',
      nReturned: 6086,
      executionTimeMillisEstimate: 15,
      works: 25360,
      advanced: 6086,
      needTime: 19273,
      needYield: 0,
      saveState: 32,
      restoreState: 32,
      isEOF: 1,
      transformBy: {
        name: 1,
        borough: 1
      },
      inputStage: {
        stage: 'COLLSCAN',
        filter: {
          borough: {
            '$eq': 'Brooklyn'
          }
        },
        nReturned: 6086,
        executionTimeMillisEstimate: 13,
        works: 25360,
        advanced: 6086,
        needTime: 19273,
        needYield: 0,
        saveState: 32,
        restoreState: 32,
        isEOF: 1,
        direction: 'forward',
        docsExamined: 25359
      }
    }
  }
}
```;
// COLLSCAN 을 사용하고 있음 25360 개중 실제 다음 스테이지로 넘어간 데이터는 6086

// 인덱스 생성
db.restaurants.createIndex({ borough: -1 });

// 다시 실행
db.restaurants
  .find({ borough: "Brooklyn" }, { name: 1, borough: 1 })
  .sort({ name: 1 })
  .explain("executionStats");
// 인덱스 스캔(IXSCAN)을 사용하여 정확히 사용할 데이터만 뽑아온 것이 확인 가능하다.

// rejectedPlans 확인
db.restaurants.createIndex({ name: 1, borough: -1 });

db.restaurants
  .find({ borough: "Brooklyn" }, { name: 1, borough: 1 })
  .sort({ name: 1 })
  .explain("allPlansExecution");

db.restaurants
  .aggregate([
    {
      $match: { borough: "Brooklyn" },
    },
    {
      $group: {
        _id: "$cuisine",
        cnt: { $sum: 1 },
      },
    },
    {
      $sort: {
        name: 1,
      },
    },
  ])
  .explain("executionStats");

db.restaurants
  .aggregate([
    {
      $match: { borough: "Brooklyn", cuisine: "American" },
    },
  ])
  .explain("executionStats");

db.restaurants
  .aggregate([
    {
      $group: {
        _id: { cuisine: "$cuisine", borough: "$borough" },
        cnt: { $sum: 1 },
      },
    },
    {
      $match: { "_id.borough": "Queens" },
    },
    {
      $sort: {
        "_id.borough": 1,
      },
    },
  ])
  .explain("executionStats");
