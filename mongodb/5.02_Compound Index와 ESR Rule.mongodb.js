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

- COLLSCAN -> SORT 작업이 이루어짐
- 13개의 데이터를 return 하기 위해서 30000개의 데이터를 스캔함
```

// index 생성하기
// 1: 오름차순, -1: 내림차순
db.zips.createIndex({state: 1})

db.zips.find(
    {
        state: "LA",
        pop: {
            $gte: 40000
        }
    }
).sort({city: 1}).explain('executionStats')

``` 실행결과 중 일부
executionStats: {
    executionSuccess: true,
    nReturned: 13,
    executionTimeMillis: 2,
    totalKeysExamined: 469,
    totalDocsExamined: 469,
    executionStages: {
      stage: 'SORT',
      nReturned: 13,
      executionTimeMillisEstimate: 0,
      works: 484,
      advanced: 13,
      needTime: 470,
      needYield: 0,
      saveState: 0,
      restoreState: 0,
      isEOF: 1,
      sortPattern: {
        city: 1
      },
      memLimit: 33554432,
      type: 'simple',
      totalDataSizeSorted: 1885,
      usedDisk: false,
      spills: 0,
      spilledDataStorageSize: 0,
      inputStage: {
        stage: 'FETCH',
        filter: {
          pop: {
            '$gte': 40000
          }
        },
        nReturned: 13,
        executionTimeMillisEstimate: 0,
        works: 470,
        advanced: 13,
        needTime: 456,
        needYield: 0,
        saveState: 0,
        restoreState: 0,
        isEOF: 1,
        docsExamined: 469,
        alreadyHasObj: 0,
        inputStage: {
          stage: 'IXSCAN',
          nReturned: 469,
          executionTimeMillisEstimate: 0,
          works: 470,
          advanced: 469,
          needTime: 0,
          needYield: 0,
          saveState: 0,
          restoreState: 0,
          isEOF: 1,
          keyPattern: {
            state: 1
          },
          indexName: 'state_1',
          isMultiKey: false,
          multiKeyPaths: {
            state: []
          },
          isUnique: false,
          isSparse: false,
          isPartial: false,
          indexVersion: 2,
          direction: 'forward',
          indexBounds: {
            state: [
              '["LA", "LA"]'
            ]
          },
          keysExamined: 469,
          seeks: 1,
          dupsTested: 0,
          dupsDropped: 0
        }
      }
    }
  }

- 탐색하는 데이터가 많이 줄어듬
- 탐색시 키를 활용하는 것을 확인 가능
```

// compound index 생성
db.zips.createIndex({state: 1, city: 1, pop: 1})

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
    executionTimeMillis: 3,
    totalKeysExamined: 419,
    totalDocsExamined: 13,
    executionStages: {
      stage: 'FETCH',
      nReturned: 13,
      executionTimeMillisEstimate: 0,
      works: 420,
      advanced: 13,
      needTime: 405,
      needYield: 0,
      saveState: 0,
      restoreState: 0,
      isEOF: 1,
      docsExamined: 13,
      alreadyHasObj: 0,
      inputStage: {
        stage: 'IXSCAN',
        nReturned: 13,
        executionTimeMillisEstimate: 0,
        works: 419,
        advanced: 13,
        needTime: 405,
        needYield: 0,
        saveState: 0,
        restoreState: 0,
        isEOF: 1,
        keyPattern: {
          state: 1,
          city: 1,
          pop: 1
        },
        indexName: 'state_1_city_1_pop_1',
        isMultiKey: false,
        multiKeyPaths: {
          state: [],
          city: [],
          pop: []
        },
        isUnique: false,
        isSparse: false,
        isPartial: false,
        indexVersion: 2,
        direction: 'forward',
        indexBounds: {
          state: [
            '["LA", "LA"]'
          ],
          city: [
            '[MinKey, MaxKey]'
          ],
          pop: [
            '[40000, inf.0]'
          ]
        },
        keysExamined: 419,
        seeks: 406,
        dupsTested: 0,
        dupsDropped: 0
      }
    }
  }

- 인덱스로 탐색이 이루어짐
- city에 대한 sort 작업이 없어짐
```

// 추가적으로 projection 사용
db.zips.find(
    {
        state: "LA",
        pop: {
            $gte: 40000
        }
    },
    {
        _id: 0,
        state: 1,
        pop: 1,
        city: 1
    }
).sort({city: 1}).explain('executionStats')

``` 실행결과 중 일부
 executionStats: {
    executionSuccess: true,
    nReturned: 13,
    executionTimeMillis: 2,
    totalKeysExamined: 419,
    totalDocsExamined: 0,
    executionStages: {
      stage: 'PROJECTION_COVERED',
      nReturned: 13,
      executionTimeMillisEstimate: 0,
      works: 420,
      advanced: 13,
      needTime: 405,
      needYield: 0,
      saveState: 0,
      restoreState: 0,
      isEOF: 1,
      transformBy: {
        _id: 0,
        state: 1,
        pop: 1,
        city: 1
      },
      inputStage: {
        stage: 'IXSCAN',
        nReturned: 13,
        executionTimeMillisEstimate: 0,
        works: 420,
        advanced: 13,
        needTime: 405,
        needYield: 0,
        saveState: 0,
        restoreState: 0,
        isEOF: 1,
        keyPattern: {
          state: 1,
          city: 1,
          pop: 1
        },
        indexName: 'state_1_city_1_pop_1',
        isMultiKey: false,
        multiKeyPaths: {
          state: [],
          city: [],
          pop: []
        },
        isUnique: false,
        isSparse: false,
        isPartial: false,
        indexVersion: 2,
        direction: 'forward',
        indexBounds: {
          state: [
            '["LA", "LA"]'
          ],
          city: [
            '[MinKey, MaxKey]'
          ],
          pop: [
            '[40000, inf.0]'
          ]
        },
        keysExamined: 419,
        seeks: 406,
        dupsTested: 0,
        dupsDropped: 0
      }
    }
  },

- totalDocsExamined이 0건임
- PROJECTION_COVERED stage 가 추가됨
- 사용한 인덱스에 대해서만 show가 이루어지므로 FETCH 작업이 발생하지 않음
```