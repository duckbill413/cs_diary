use("shape");

// 인덱스 생성
db.grids.createIndex({ loc: "2d" }); // legacy 한 방법 지원안 함
db.grids.createIndex({ loc: "2dsphere" }); //legacy 한 방법도 지원함. 단, 평명 형태에서 잘 지원이 안된다.

// 기존 데이터 삭제
db.grids.drop();

// 데이터 추가
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
]);

// legacy 한 데이터에 대하여 인덱스 생성
db.grids.createIndex({ loc: "2d" });

// 2d, 2dsphere 의 차이는 범위에 대한 기준이 위도, 경도의 계산법이 아니라 2차 평명도 형태의 계산임을 고려(2d)
db.grids.find({
  loc: {
    $near: [5, 5],
    $maxDistance: 10,
  },
});

use("sample_restaurants");

// 1. 음식점이 있는 동네 조회
var restaurant = db.restaurants.findOne();
db.neighborhoods.find(
  {
    geometry: {
      $geoIntersects: {
        $geometry: {
          type: "Point",
          coordinates: restaurant.address.coord,
        },
      },
    },
  },
  {
    name: 1,
  }
);

// 2. 특정 동네의 음식점을 조회
var neighborhood = db.neighborhoods.findOne();
db.restaurants.find(
  {
    "address.coord": {
      $geoWithin: {
        $geometry: neighborhood.geometry,
      },
    },
  },
  {
    name: 1,
    _id: 0,
  }
);

db.restaurants.createIndex({"address.coord": "2dsphere"})

db.restaurants.aggregate([
	{
		$geoNear: {
			near: {
				type: "Point",
				coordinates: [ -73.8845166, 40.744772 ]
			},
			key: "address.coord",
			maxDistance: 3000,
			query: {
				cuisine: "Hamburgers"
			},
			distanceField: "dist"
		}
	},
	{
		$project: {
			name: 1,
			cuisine: 1,
			dist: 1 // 현 위치와 중심 위치 사이의 거리
		}
	},
])