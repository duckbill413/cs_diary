use('sample_training')

// index 조회
db.zips.getIndexes()

// 데이터 조회 쿼리
db.zips.find(
    {
        state: "LA",
        pop: {
            $gte: 40000
        }
    }
).sort({city: 1})

// 앞선 쿼리의 실행 계획 확인
db.zips.find(
    {
        state: "LA",
        pop: {
            $gte: 40000
        }
    }
).sort({city: 1}).explain('executionStats')

``` 실행 결과 중 일부
executionStats: {
    executionSuccess: true,
    nReturned: 13,
    executionTimeMillis: 24,
    totalKeysExamined: 0,
    totalDocsExamined: 29470,
    executionStages: {
      stage: 'SORT',
      sortPattern: {
        city: 1
      },
      inputStage: {
        stage: 'COLLSCAN',
        filter: {
          '$and': [
            {
              state: {
                '$eq': 'LA'
              }
            },
            {
              pop: {
                '$gte': 40000
              }
            }
          ]
        },
      }
    }
  }

- 13개의 데이터를 return 하기 위해서 30000개의 데이터를 스캔함
```