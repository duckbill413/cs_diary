use("test");

// students collection 에 데이터 삽입
db.students.insertMany([
  { _id: 1, grades: [85, 80, 80] },
  { _id: 2, grades: [88, 90, 92] },
  { _id: 3, grades: [85, 100, 90] },
]);

// 첫번째 데이터의 grades 80을 82로 update
db.students.updateOne({ _id: 1, grades: 80 }, { $set: { "grades.$": 82 } });

// 모든 document의 grades 를 10씩 증가
db.students.updateMany({}, { $inc: { "grades.$[]": 10 } });

db.students.insertMany([
  {
    _id: 4,
    grades: [
      { grade: 80, mean: 75, std: 8 },
      { grade: 85, mean: 90, std: 5 },
      { grade: 85, mean: 85, std: 8 },
    ],
  },
]);

// document 의 std 값을 6으로 변경
db.students.updateOne(
  { _id: 4, "grades.grade": 85 },
  {
    $set: { "grades.$.std": 6 },
  }
);

// elemMatch 와의 사용
db.students.updateOne(
  {
    _id: 4,
    grades: {
      $elemMatch: {
        grade: { $gte: 85 },
      },
    },
  },
  {
    $set: { "grades.$[].grade": 100 },
  }
);

// 조건에 맞는 배열 요소만 변경
// grade 가 87 이상이면 100으로 변경
// 우선 데이터 삽입
db.students.insertMany([
  {
    _id: 6,
    grades: [
      { grade: 90, mean: 75, std: 8 },
      { grade: 87, mean: 90, std: 5 },
      { grade: 85, mean: 85, std: 8 },
    ],
  },
]);

// element 를 사용 (변수와 같은 역할)
// 조건에 맞는 데이터 (grade가 90, 87 인 경우) update
db.students.updateOne(
  { _id: 6 },
  { $set: { "grades.$[element].grade": 100 } },
  { arrayFilters: [{ "element.grade": { $gte: 87 } }] }
);

// 조건 업데이트 추가
// 데이터 삽입
db.students.insertMany([
  {
    _id: 7,
    grades: [
      { type: "quiz", questions: [10, 8, 5] },
      { type: "quiz", questions: [8, 9, 6] },
      { type: "hw", questions: [5, 4, 3] },
      { type: "exam", questions: [25, 10, 23, 0] },
    ],
  },
]);

// questions 요소중 8보다 큰 경우 2씩 증가
db.students.updateOne(
  { _id: 7 },
  { $inc: { "grades.$[].questions.$[score]": 2 } },
  {
    arrayFilters: [{ score: { $gte: 8 } }],
  }
);

// 데이터에 값 넣고 빼기
db.shopping.insertMany([
  {
    _id: 1,
    cart: ["banana", "cheeze", "milk"],
    coupons: ["10%", "20%", "30%"],
  },
  {
    _id: 2,
    cart: [],
    coupons: [],
  },
]);

// 데이터에 값이 없는 경우만 삽입
// 여러번 실행해도 한번만 삽입됨
db.shopping.updateOne(
  { _id: 1 },
  {
    $addToSet: { cart: "beer" },
  }
);

// 배열안에 각각의 데이터를 배열을 이용해서 추가 (each)
db.shopping.updateOne(
  { _id: 1 },
  {
    $addToSet: {
      cart: {
        $each: ["beer", "candy"],
      },
    },
  }
);

// pull 을 이용한 데이터 삭제
db.shopping.updateOne(
  { _id: 1 },
  {
    $pull: {
      cart: "beer",
    },
  }
);

// 배열을 이용한 삭제
db.shopping.updateOne(
  { _id: 1 },
  {
    $pull: {
      cart: {
        $in: ["beer", "candy", "milk"],
      },
    },
  }
);

// pop: 1 은 맨뒤의 값 삭제, -1 은 첫번째 값 삭제
db.shopping.updateOne(
  { _id: 1 },
  {
    $pop: {
      cart: -1,
    },
  }
);

// push
db.shopping.updateOne(
  { _id: 1 },
  {
    $push: {
      cart: "popcorn",
    },
  }
);

// 배열안에 각각의 데이터를 배열을 이용해서 추가 (push, each)
db.shopping.updateOne(
  { _id: 1 },
  {
    $push: {
      coupons: {
        $each: ["25%", "35%"],
      },
    },
  }
);

// position 을 이용하여 삽입 위치 지정
db.shopping.updateMany(
  {},
  {
    $push: {
      coupons: {
        $each: ["90%", "75%"],
        $position: 0,
      },
    },
  }
);

// slice
// 데이터를 추가하고 크기를 5개 까지만 유지
db.shopping.updateMany(
  {},
  {
    $push: {
      coupons: {
        $each: ["15%", "20%"],
        $position: 0,
        $slice: 5,
      },
    },
  }
);

// sort 배열 정렬
db.shopping.updateMany(
  {},
  {
    $push: {
      coupons: {
        $each: ["90%", "99%"],
        $position: -1, // 가장 마지막 위치 삽입
        $sort: -1,
        $slice: 5,
      },
    },
  }
);

// push 를 이용한 방법은 배열을 처음 혹은 마지막만 탐색하기에 부하가 적다.
// 반면, pull 이나 addToSet 은 배열의 전체를 탐색하므로 부하가 크다
