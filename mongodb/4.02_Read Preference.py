from pymongo import MongoClient
from pymongo.read_preferences import ReadPreference
import certifi

conn = "mongodb+srv://duckbill:Hkug0AtjqWc7gGEG@cluster0.6mmuwjy.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
conn = "mongodb+srv://duckbill:Hkug0AtjqWc7gGEG@cluster0.6mmuwjy.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0&readPreference=secondary" # readPreference=secondary 옵션 추가

client = MongoClient(conn, tlsCaFile=certifi.where())
db = client.test

db.fruits.insert_many([
  {
    "name": "melon",
    "qty": 1000,
    "price": 15000,
  },
    {
    "name": "grape",
    "qty": 100,
    "price": 10000,
  },
    {
    "name": "apple",
    "qty": 2000,
    "price": 3000,
  }
])

query_filter = {
  "name": "melon"
}

# Primary 로 조회
# while True:
#   res = db.fruits.find_one(query_filter)
#   print(res)

# Secondary 로 조회
# while True:
#   res = db.fruits.with_options(read_preference=ReadPreference.SECONDARY).find_one(query_filter)
#   print(res)

# Connection String 으로 처리
while True:
  res = db.fruits.find_one(query_filter)
  print(res)