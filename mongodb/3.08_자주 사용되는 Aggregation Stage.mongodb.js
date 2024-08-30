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
