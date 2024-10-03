use("shape");

// 좌표 데이터 삽입
db.grids.insertMany([
  {
    _id: 1,
    loc: [0, 0],
  },
  {
    _id: 2,
    loc: [3, 4],
  },
  {
    _id: 3,
    loc: [15, 2],
  },
  {
    _id: 4,
    loc: [7, 8],
  },
  {
    _id: 5,
    loc: {
      type: "Point",
      coordinates: [5, 5],
    },
  },
  {
    _id: 6,
    loc: {
      type: "Point",
      coordinates: [14, 8],
    },
  },
  {
    _id: 7,
    loc: {
      type: "LineString",
      coordinates: [
        [6, 6],
        [15, 13],
      ],
    },
  },
  {
    _id: 8,
    loc: {
      type: "LineString",
      coordinates: [
        [0, 12],
        [5, 12],
      ],
    },
  },
  {
    _id: 9,
    loc: {
      type: "Polygon",
      coordinates: [
        [
          [2, 2],
          [3, 3],
          [4, 2],
          [2, 2], // 첫번째 좌표로 막아주는 작업 필요
        ],
      ],
    },
  },
  {
    _id: 10,
    loc: {
      type: "Polygon",
      coordinates: [
        [
          [9, 0],
          [5, 13],
          [14, 6],
          [9, 0],
        ],
      ],
    },
  },
]);

// 10 x 10 안의 좌표에 교차된 좌표 찾기
db.grids.find({
  loc: {
    $geoIntersects: {
      $geometry: {
        type: "Polygon",
        coordinates: [
          [
            [0, 0],
            [0, 10],
            [10, 10],
            [10, 0],
            [0, 0],
          ],
        ],
      },
    },
  },
});

// 10 x 10 안의 좌표에 포함된 좌표 찾기
db.grids.find({
  loc: {
    $geoWithin: {
      $geometry: {
        type: "Polygon",
        coordinates: [
          [
            [0, 0],
            [0, 10],
            [10, 10],
            [10, 0],
            [0, 0],
          ],
        ],
      },
    },
  },
});

// 점 근처에 있는 지리 공간적 객체
db.grids.find({
  loc: {
    $near: {
      $geometry: {
        type: "Point",
        coordinates: [5, 5],
      },
      $maxDistance: 3
    },
  },
});
// 바로 실행하게 되면 `unable to find index for $geoNear` 에러가 발생