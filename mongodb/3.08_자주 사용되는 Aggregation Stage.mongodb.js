use("test");

// 실습을 위한 데이터 삽입
db.orders.insertMany([
  {
    _id: 0,
    name: "Pepperoni",
    size: "small",
    price: 19,
    quantity: 10,
    date: ISODate("2021-03-13T08:14:30Z"),
  },
  {
    _id: 1,
    name: "Pepperoni",
    size: "medium",
    price: 20,
    quantity: 20,
    date: ISODate("2021-03-13T09:13:24Z"),
  },
  {
    _id: 2,
    name: "Pepperoni",
    size: "large",
    price: 21,
    quantity: 30,
    date: ISODate("2021-03-17T09:22:12Z"),
  },
  {
    _id: 3,
    name: "Cheese",
    size: "small",
    price: 12,
    quantity: 15,
    date: ISODate("2021-03-13T11:21:39.736Z"),
  },
  {
    _id: 4,
    name: "Cheese",
    size: "medium",
    price: 13,
    quantity: 50,
    date: ISODate("2022-01-12T21:23:13.331Z"),
  },
  {
    _id: 5,
    name: "Cheese",
    size: "large",
    price: 14,
    quantity: 10,
    date: ISODate("2022-01-12T05:08:13Z"),
  },
  {
    _id: 6,
    name: "Vegan",
    size: "small",
    price: 17,
    quantity: 10,
    date: ISODate("2021-01-13T05:08:13Z"),
  },
  {
    _id: 7,
    name: "Vegan",
    size: "medium",
    price: 18,
    quantity: 10,
    date: ISODate("2021-01-13T05:10:13Z"),
  },
]);

// 이름별 총 판매된 수량 조회
db.orders.aggregate(
  {
    $match: {
      size: "medium",
    },
  },
  {
    $group: {
      _id: { $getField: "name" },
      totalQuantity: {
        $sum: { $getField: "quantity" },
      },
    },
  }
);

// $getField => $ sign으로 대신할 수 있음
db.orders.aggregate(
  {
    $match: {
      size: "medium",
    },
  },
  {
    $group: {
      _id: "$name",
      totalQuantity: { $sum: "$quantity" },
    },
  }
);

// 2021 년 부터 2년간의 데이터에 대해 날짜 별 매출과 평균 판매 수량을 출력하고, 데이터를 매출에 대하여 내림차순 정렬
db.orders.aggregate([
  {
    $match: {
      date: {
        $gte: new ISODate("2021-01-30"),
        $lt: new ISODate("2023-01-30"),
      },
    },
  },
  {
    $group: {
      _id: {
        $dateToString: {
          format: "%Y-%m-%d",
          date: "$date",
        },
      },
      totalOrderValue: {
        $sum: {
          $multiply: ["$price", "$quantity"],
        },
      },
      averageOrderQuantity: {
        $avg: "$quantity",
      },
    },
  },
  {
    $sort: {
      totalOrderValue: -1,
    },
  },
]);

// 책들에 대한 데이터를 삽입
db.books.insertMany([
  { _id: 8761, title: "The Banquet", author: "Dante", copies: 2 },
  { _id: 8752, title: "Divine Comedy", author: "Dante", copies: 1 },
  { _id: 8645, title: "Eclogues", author: "Dante", copies: 2 },
  { _id: 7000, title: "The Odyssey", author: "Homer", copies: 10 },
  { _id: 7020, title: "Iliad", author: "Homer", copies: 10 },
]);

// 저자를 기준으로 그룹을 지어 저자가 쓴 책을 배열로 조회
db.books.aggregate({
  $group: {
    _id: "$author",
    books: {
      $push: "$title",
    },
  },
});

// 필드에 대한 값이 아니라 Document 를 삽입
db.books.aggregate({
  $group: {
    _id: "$author",
    books: {
      $push: "$$ROOT", // 변수 값에 액세스하려면 변수 이름 앞에 이중 달러 기호($) 사용
    },
  },
});

// totalCopies 조회
db.books.aggregate({
  $group: {
    _id: "$author",
    books: {
      $push: "$$ROOT", // 변수 값에 액세스하려면 변수 이름 앞에 이중 달러 기호($) 사용
    },
    totalCopies: {
      $sum: "$copies",
    },
  },
});

// totalCopies 조회시 따로 stage 를 분리해서 사용
db.books.aggregate(
  {
    $group: {
      _id: "$author",
      books: {
        $push: "$$ROOT", // 변수 값에 액세스하려면 변수 이름 앞에 이중 달러 기호($) 사용
      },
    },
  },
  {
    $addFields: {
      totalCopies: { $sum: "$books.copies" },
    },
  }
);

// 데이터 추가
db.orders.drop(); // 기존의 orders collection 삭제
// 새로운 orders 생성
db.orders.insertMany([
  { productId: 1, price: 12 },
  { productId: 2, price: 20 },
  { productId: 3, price: 80 },
]);
// 새로운 products 생성
db.products.insertMany([
  { id: 1, instock: 120 },
  { id: 2, instock: 80 },
  { id: 3, instock: 60 },
  { id: 4, instock: 70 },
]);

// 간단한 look-up 조회 (sql의 join과 유사)
db.orders.aggregate([
  {
    $lookup: {
      from: "products", // products collection과 수행
      localField: "productId", // 현재 collection(orders)에서 기준이 되는 필드
      foreignField: "id", // 다른 collection(products)에서 기준이 되는 필드
      as: "data", // data 라는 list 필드에 안에 join 된 products 필드를 추가
    },
  },
]);

// 자주 사용되는 operation 중에 expr operator 를 사용
// 같은 필드를 기준으로 비교를 할때 사용

// instock 이 price 보다 큰 경우를 조회
db.orders.aggregate([
  {
    $lookup: {
      from: "products", // products collection과 수행
      localField: "productId", // 현재 collection(orders)에서 기준이 되는 필드
      foreignField: "id", // 다른 collection(products)에서 기준이 되는 필드
      as: "data", // data 라는 list 필드에 안에 join 된 products 필드를 추가
    },
  },
  {
    $match: {
      $expr: {
        // expr 의 경우 배열을 사용하면 정상적인 결과를 얻을 수 없음 (필드인 경우는 가능)
        $gt: ["$data.instock", "$price"],
      },
    },
  },
]);

// 위의 코드를 수정
db.orders.aggregate([
  {
    $lookup: {
      from: "products", // products collection과 수행
      localField: "productId", // 현재 collection(orders)에서 기준이 되는 필드
      foreignField: "id", // 다른 collection(products)에서 기준이 되는 필드
      as: "data", // data 라는 list 필드에 안에 join 된 products 필드를 추가
    },
  },
  {
    $unwind: "$data", // unwind 를 이용하여 배열을 object 로 풀기
  },
  {
    $match: {
      $expr: {
        // expr 의 경우 배열을 사용하면 정상적인 결과를 얻을 수 없음 (필드인 경우는 가능)
        $gt: ["$data.instock", "$price"], // data.instock 가 price 보다 큰 경우
      },
    },
  },
]);

// unwind 를 추가로 확인해 보자
// 기존의 코드
db.books.aggregate(
  {
    $group: {
      _id: "$author",
      books: {
        $push: "$$ROOT", // 변수 값에 액세스하려면 변수 이름 앞에 이중 달러 기호($) 사용
      },
    },
  },
  {
    $addFields: {
      totalCopies: { $sum: "$books.copies" },
    },
  }
);

// books field 에 대하여 unwind 실행
// 실행 결과를 확인해 보면 books field가 배열이 아닌 object로 조회됨
db.books.aggregate(
  {
    $group: {
      _id: "$author",
      books: {
        $push: "$$ROOT", // 변수 값에 액세스하려면 변수 이름 앞에 이중 달러 기호($) 사용
      },
    },
  },
  {
    $unwind: "$books",
  },
  {
    $addFields: {
      totalCopies: { $sum: "$books.copies" },
    },
  }
);

// database 변경 (sample_airbnb)
use("sample_airbnb");

// 데이터를 몇개만 확인하고 싶을때
db.listingsAndReviews.aggregate([
  {
    $sample: {
      size: 3,
    },
  },
  {
    $project: {
      name: 1,
      summary: 1,
    },
  },
]);

// skip 과 limit
// paging 을 위해 사용될 수 있음
db.listingsAndReviews.aggregate([
  {
    $match: {
      property_type: "Apartment",
    },
  },
  {
    $sort: {
      number_of_reviews: -1,
    },
  },
  {
    $skip: 2, // 2 개의 데이터를 건너띔
  },
  {
    $limit: 5, // 5 개의 데이터만 출력
  },
  {
    $project: {
      name: 1,
      number_of_reviews: 1,
    },
  },
]);

// use test collection
use("test");

// test 의 데이터를 이용하여 다른 collection 에 저장
db.books.aggregate([
  {
    $group: {
      _id: "$author",
      books: {
        $push: "$title",
      },
    },
  },
  {
    $out: "authors", // 같은 database 의 collection 에 데이터 출력
  },
  // 다른 database 의 collection에 데이터 출력
  // {
  //   $out: {
  //     db: "test",
  //     coll: "authors",
  //   },
  // },
]);
