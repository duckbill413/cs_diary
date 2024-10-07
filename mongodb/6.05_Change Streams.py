import pymongo
import certifi

conn = "mongodb://localhost:27017"
conn = "mongodb+srv://duckbill:Hkug0AtjqWc7gGEG@cluster0.6mmuwjy.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
client = pymongo.MongoClient(conn, tlsCAFile=certifi.where())

db = client.test

# collection 에 대한 watch
# stream = db.test.watch() # test collection에 change stream 걸기
# stream = db.watch() # database 에 대한 watch
# stream = client.watch() # 전체 client 에 대한 watch

# watch 는 조건을 사용하여 aggregation 가능
pipeline = [
    {
        "$match": {"fullDocument.status": "argent"}
    }
]
stream  = client.watch(pipeline=pipeline)

for doc in stream:
    print(doc)