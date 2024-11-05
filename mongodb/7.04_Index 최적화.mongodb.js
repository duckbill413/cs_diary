use("sample_supplies");

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
