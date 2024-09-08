from pymongo import MongoClient
from pymongo.read_concern import ReadConcern
from pymongo.write_concern import WriteConcern


# local 환경에서 실습
conn = "mongodb://localhost:27017,localhost:27018,localhost:27019"
# writeConcern 사용시: mongodb://localhost:27017/?w=majority
# readConcern 사용시: mongodb://localhost:27017/?readConcernLevel=linearizable

client = MongoClient(conn)
db = client.test

# 실습1 (실행 후 주석)
db.sales.insert_many([
  {
    "name": "pencil",
    "price": 1000,
  },
  {
    "name": "paper",
    "price": 100,
  },
  {
    "name": "pen",
    "price": 2000,
  }
])

query_filter = {
  "price": {"$gt": 1500}
}

while True:
  res = db.sales.find_one(query_filter)
  print(res)

"""
3개 중 두개의 db에 대한 write 막기
- writeConcern 미설정시 default value: majority
- readConcern 미설정시 default value: local
- 3개의 DB가 있으므로 이중 2개에 들어가면 성공으로 처리
1. mongodb 접속
  - mongosh "mongodb://localhost:27019/"
2. write 막기 명령
  - db.fsyncLock()
3. 27018 포트의 DB도 동일하게 설정
4. 이후 조회를 해보면 3개의 DB 중 2개에서 fsyncLock 이 걸려있어 조회가 이루어지지 않음
"""
#------------------------------------------------------------------

# 실습2 (실행 후 주석)
# 여러개의 DB 중 하나만 되면 실행되도록 설정
db.sales.with_options(write_concern=WriteConcern(w=1)).insert_many([
  {
    "name": "pencil",
    "price": 1000,
  },
  {
    "name": "paper",
    "price": 100,
  },
  {
    "name": "pen",
    "price": 2000,
  }
])

query_filter = {
  "price": {"$gt": 1500}
}

while True:
  res = db.sales.find_one(query_filter)
  print(res)

#------------------------------------------------------------------
# 실습3 (실행 후 주석)
db.sales.with_options(write_concern=WriteConcern(w=1)).insert_many([
  {
    "name": "pencil",
    "price": 1000,
  },
  {
    "name": "paper",
    "price": 100,
  },
  {
    "name": "pen",
    "price": 2000,
  }
])

query_filter = {
  "price": {"$gt": 1500}
}

# majority 로 설정한 경우
# 조회 성공
# 특정 시점에 대하여 동일한 데이터를 갖고 있으면 조회
while True:
  res = db.sales.read_concern(read_concern=ReadConcern('majority')).find_one(query_filter)
  print(res)

# linearizable 로 설정한 경우
# 조회 실패
# 현재 진행중인 majority write 에 대하여 모두 반영된 이후 조회가 이루어짐
# 현재 최신 데이터는 복제가 안되고 있기 때문에 발생하는 문제
while True:
  res = db.sales.read_concern(read_concern=ReadConcern('linearizable')).find_one(query_filter)
  print(res)

#------------------------------------------------------------------
# 실습4 (실행 후 주석)
# 데이터를 수정 후 majority 조회
db.sales.with_options(write_concern=WriteConcern(w=1)).insert_many([
  {
    "name": "pencil",
    "price": 10000,
  },
  {
    "name": "paper",
    "price": 100,
  },
  {
    "name": "pen",
    "price": 1600,
  }
])

query_filter = {
  "price": {"$gt": 3000}
}

# majority 로 설정한 경우
# majority 이므로 값을 가져오기는 하지만 현재 primary 밖에 업데이트가 이루어지지 않았으므로
# 3000 을 넘는 majority 한 값이 없기 때문에 아무런 데이터도 조회되지 않음
while True:
  res = db.sales.read_concern(read_concern=ReadConcern('majority')).find_one(query_filter)
  print(res)