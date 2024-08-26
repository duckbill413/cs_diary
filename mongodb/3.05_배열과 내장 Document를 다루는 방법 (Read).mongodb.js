use("sample_supplies");

// 조건에 대한 검색 (검색 ok)
db.sales.findOne({
  customer: {
    gender: "M",
    age: 50,
    email: "keecade@hem.uy",
    satisfaction: 5,
  },
});

// 순서를 바꾸어서 검색 (검색 fail)
db.sales.findOne({
  customer: {
    age: 50,
    gender: "M",
    email: "keecade@hem.uy",
    satisfaction: 5,
  },
});

// inner document search
db.sales.findOne({
  "customer.email": "keecade@hem.uy",
});

// 비교 연산자로 검색
db.sales.findOne({
  "customer.age": { $lt: 20 },
});

use("test");

// 배열을 다루는 방법

// 데이터 삽입
db.inventory.insertMany([
  {
    item: "journal",
    qty: 25,
    tags: ["blank", "red"],
    dim_cm: [14, 21],
  },
  {
    item: "notebook",
    qty: 50,
    tags: ["red", "blank"],
    dim_cm: [14, 21],
  },
  {
    item: "paper",
    qty: 100,
    tags: ["red", "blank", "plain"],
    dim_cm: [14, 21],
  },
  {
    item: "planner",
    qty: 75,
    tags: ["blank", "red"],
    dim_cm: [22.85, 30],
  },
  {
    item: "postcard",
    qty: 45,
    tags: ["blue"],
    dim_cm: [10, 15.25],
  },
  {
    item: "postcard",
    qty: 45,
    tags: ["blue", "red"],
    dim_cm: [13, 14],
  },
]);

// 데이터 검색 (순서가 필요)
db.inventory.find({
  tags: ["blue", "red"],
});

// 순서 고려 X 시 검색 안됨
db.inventory.find({
  tags: ["red", "blue"],
});

// all operator 를 이용하여 모두 포함 검색
db.inventory.find({
  tags: { $all: ["red", "blue"] },
});

// in operator 로 포함하는 것 검색
db.inventory.find({
  tags: { $in: ["red", "blue"] },
});

// 따로 연산자를 사용하지 않은 경우
// blue 를 포함하는 데이터가 출력
db.inventory.find({
  tags: "blue",
});

// 조건절에 비교 연산자를 넣어 검색
// 배열 요소 중 하나라도 만족하면 출력
db.inventory.find({
  dim_cm: { $gt: 15 },
});

// 조건을 추가 (gt, lt)
// 전체 배열이 해당 조건을 충족하는지 여부를 확인
// dim_cm 의 데이터가 [14, 21] 이면
// $gt: 15 조건에 대하여 21이 만족하므로 true
// $lt: 20 조건에 대하여 14가 만족하므로 true
// 결과는 true && true 이므로 true 가 되어 출력 됨
db.inventory.find({
  dim_cm: { $gt: 15, $lt: 20 },
});

// elemMatch
// 즉, 15 < dim_cm < 20 사이를 만족하는 데이터가 하나라도 조건에 모두 만족되면 검색 됨
// dim_cm: [10, 15.25] 이면
// 15 < dim_cm < 20 조건에서 10 -> false 이지만, 20 -> true 이므로 출력
// dim_cm: [14, 21] 이면
// 14 -> false, 21 -> false 이므로 false
db.inventory.find({
  dim_cm: { $elemMatch: { $gt: 15, $lt: 20 } },
});

// 배열의 특정 위치에 대한 조건
// 첫번째 요소가 20 보다 작은 경우
db.inventory.find({
  "dim_cm.1": { $lt: 20 },
});

// 크기에 대한 검색
db.inventory.find({
  tags: { $size: 3 },
});

use("sample_supplies");
// 배열의 요소로 내장 도규먼트를 가진 경우
// elemMatch 를 이용하여 items 의 name 이 binder 이고,
// quantity 가 6 보다 작은 값이 있는 데이터를 검색
db.sales.find({
  items: {
    $elemMatch: {
      name: "binder",
      quantity: { $lt: 6 },
    },
  },
});

// positioning operator
// $ sign 을 positioning operator 이라고 함
// projection 시 positioning operator 를 사용하면 배열의 조건에 만족하는 첫번째 요소만 반환
// 결과를 검색하면 items 의 데이터 중 하나만 출력됨
db.sales.find(
  {
    items: {
      $elemMatch: {
        name: "binder",
        quantity: { $lt: 6 },
      },
    },
  },
  {
    saleDate: 1,
    "items.$": 1, // projection 의 사용
    storeLocation: 1,
    customer: 1,
  }
);
