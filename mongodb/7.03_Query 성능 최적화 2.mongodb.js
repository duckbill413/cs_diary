use("test");

// 데이터 삽입
db.survey.insertMany([
  { item: "ABC", ratings: [2, 9], category_id: 10 },
  { item: "XYZ", ratings: [4, 3], category_id: 10 },
  { item: "ABC", ratings: [9], category_id: 20 },
  { item: "ABC", ratings: [9, 10], category_id: 30 },
  { item: "ABC", ratings: [2, 4], category_id: 30 },
]);

for (var i = 0; i < 15; i++) {
  arr = [];
  db.survey.find({}, { _id: 0 }).forEach(function (document) {
    arr.push(document);
  });
  db.survey.insertMany(arr);
}

// 삽입된 데이터의 개수 카운팅
db.survey.estimatedDocumentCount();
// 약, 163840 개의 데이터가 삽입됨

// 인덱스 생성
db.survey.createIndex({
  category_id: 1,
});

// 예제 쿼리에 대한 실행 계획 확인
db.survey
  .find({
    category_id: {
      $gt: 15,
      $lt: 25,
    },
  })
  .explain("executionStats");

```
실행계획의 indexBound를 살펴보면
15~25의 값을 확인한느 것을 알 수 있음

indexBounds: {
  category_id: [
    '(15, 25)'
  ]
},
```;

// multikey index 에서는 다르게 작동하는 문제가 있음
db.survey.createIndex({ ratings: 1 });

db.survey
  .find({
    ratings: {
      $gte: 3,
      $lte: 6,
    },
  })
  .explain("executionStats");

```
이전과 다르게 -inf를 확인하기 때문에 
의도치 않은 탐색이 발생한다.

indexBounds: {
  ratings: [
    '[-inf.0, 6)'
  ]
},
```;

// elem match 를 이용하여 index bound 문제 해결하기
db.survey
  .find({
    ratings: {
      $elemMatch: {
        $gte: 3,
        $lte: 6,
      },
    },
  })
  .explain("executionStats");

```
올바르게 탐색이 발생한다.

indexBounds: {
  ratings: [
    '(3, 6)'
  ]
},

하지만, ratings 에 조건을 거는 것과 elemMatch에 조건을 거는 것은
결과가 다르다는 심각한 문제가 있다.
```;

// 확인을 위해서 collection 축소
// 기존 collection 삭제 및 재생성
db.survey.drop();

// 데이터 재삽입
db.survey.insertMany([
  { item: "ABC", ratings: [2, 9], category_id: 10 },
  { item: "XYZ", ratings: [4, 3], category_id: 10 },
  { item: "ABC", ratings: [9], category_id: 20 },
  { item: "ABC", ratings: [9, 10], category_id: 30 },
  { item: "ABC", ratings: [2, 4], category_id: 30 },
]);

// 인덱스 재생성
db.survey.createIndex({ ratings: 1 });

// 다시 테스트
db.survey.find({
  ratings: {
    $gte: 3,
    $lte: 6,
  },
});

```실행결과
{
  _id: ObjectId('6728db8fb8911e43c63fc354'),
  item: 'ABC',
  ratings: [
    2,
    9
  ],
  category_id: 10
},
{
  _id: ObjectId('6728db8fb8911e43c63fc358'),
  item: 'ABC',
  ratings: [
    2,
    4
  ],
  category_id: 30
},
{
  _id: ObjectId('6728db8fb8911e43c63fc355'),
  item: 'XYZ',
  ratings: [
    4,
    3
  ],
  category_id: 10
}

- [2, 9]  : 2는 6보다 작으며, 9는 3보다 크므로 만족
- [4, 3]  : 4는 3보다 크며, 3은 6보다 작으므로 만족
- [9]     : 9는 3보다 크지만, 6보다 작지 않으므로 불만족
- [9, 10] : 3보다 큰값은 있지만, 6보다 작은 값이 없으므로 불만족
- [2, 4]  : 4는 3보다 크며, 2는 6보다 작으므로 만족

원하지 않는 결과가 나와버린다.
```;

db.survey.find({
  ratings: {
    $elemMatch: {
      $gte: 3,
      $lte: 6,
    },
  },
});

```실행결과
{
  _id: ObjectId('6728db8fb8911e43c63fc358'),
  item: 'ABC',
  ratings: [
    2,
    4
  ],
  category_id: 30
},
{
  _id: ObjectId('6728db8fb8911e43c63fc355'),
  item: 'XYZ',
  ratings: [
    4,
    3
  ],
  category_id: 10
}

- 각각 요소에 대하여 하나라도 만족할 때 반환을 하게 된다.
- [2, 4]: 3 <= 4 <= 6 반환
- [4, 3]: 3 <= 4 <= 6 반환
```;

// 모든 요소가 3보다 크거나 같고 6보다 작거나 같은 경우
db.survey.find({
  $and: [{ ratings: { $not: { $lt: 3 } } }, { ratings: { $not: { $gt: 6 } } }],
});

// 실행 계획 확인
db.survey
  .find({
    $and: [
      { ratings: { $not: { $lt: 3 } } },
      { ratings: { $not: { $gt: 6 } } },
    ],
  })
  .explain("executionStats");

```실행계획
indexBounds: {
  ratings: [
    '[MinKey, 6]',
    '(inf.0, MaxKey]'
  ]
},

- index bound 문제가 발생한다.
```;

// 인덱스 바운드 문제 해결
db.survey.find({
  $and: [
    { ratings: { $elemMatch: { $gte: 3, $lte: 6 } } },
    { ratings: { $not: { $lt: 3 } } },
    { ratings: { $not: { $gt: 6 } } },
  ],
});

// 실행 계획 확인
db.survey
  .find({
    $and: [
      { ratings: { $elemMatch: { $gte: 3, $lte: 6 } } },
      { ratings: { $not: { $lt: 3 } } },
      { ratings: { $not: { $gt: 6 } } },
    ],
  })
  .explain("executionStats");

```실행계획
indexBounds: {
  ratings: [
    '[3, 6]'
  ]
},
- 정상적으로 처리된 것을 확인할 수 있다.
```;

// multikey 를 사용할때 index bound 문제를 고려하자!!
