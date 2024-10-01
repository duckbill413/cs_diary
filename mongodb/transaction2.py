# MongoDB 4.0 이상 버전에서의 Transaction 구현 방식
from pymongo import MongoClient
from pymongo.read_concern import ReadConcern
from pymongo.write_concern import WriteConcern
from pymongo.errors import ConnectionFailure, OperationFailure
import certifi

conn = "mongodb://localhost:27017"

client = MongoClient(conn, tlsCAFile=certifi.where())

# 한번 실행 시킨 후 아래를 주석 처리
client.test.orders.drop()
client.test.inventory.drop()
client.test.inventory.insert_one({"name": "pencil", "qty": 400})
#################################################

def callback(session):
    orders = session.client.test.orders
    inventory = session.client.test.inventory
    order = 200
	
    orders.insert_one(
        {"name": "pencil", "qty": order},
        session=session
    )

    inventory.update_one(
        {
			"name": "pencil",
			"qty": {"$gte": order}
		},
        {
            "$inc": {"qty": order * -1}
        },
        session=session
    )

with client.start_session() as session:
	session.with_transaction(callback, read_concern=ReadConcern('majority'), write_concern=WriteConcern('majority'))
	print(session.client.test.orders.find_one({"name": "pencil"}))
	print(session.client.test.inventory.find_one({"name": "pencil"}))
