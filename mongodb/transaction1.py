# MongoDB 4.0 이하 버전에서의 Transaction 구현 방식
# 상당히 복잡하다는 것을 확인할 수 있음
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

def run_transaction_with_retry(transaction_func, session):
    while True:
        try:
            transaction_func(session)
            break
        except (ConnectionFailure, OperationFailure) as err:
            if err.has_error_label("TransientTransactionError"):
                print("TransientTransactionError, retrying trasaction...")
                continue
            else:
                raise

def update_orders_and_inventory(session):
    orders = session.client.test.orders
    inventory = session.client.test.inventory

    with session.start_transaction(read_concern=ReadConcern('majority'), write_concern=WriteConcern(w='majority')):
        order = 100
        orders.insert_one(
            {"name":"pencil", "qty": order},
            session = session
        )
        inventory.update_one(
            {
                "name": "pencil",
                "qty": {"$gte": order}
            },
            {
                "$inc": {"qty": order * -1}
            }
        )
        commit_with_retry(session=session)

def commit_with_retry(session):
    while True:
        try:
            session.commit_transaction()
            print("Transaction Commited")
            print(session.client.test.orders.find_one({"name": "pencil"}))
            print(session.client.test.inventory.find_one({"name": "pencil"}))
            break
        except (ConnectionFailure, OperationFailure) as err:
            if err.has_error_label("UnknownTransactionCommitResult"):
                print("UnknownTransactionCommit, retry commit operation...")
                continue
            else:
                print("Error during commit...")
                raise

with client.start_session() as session:
    try:
        run_transaction_with_retry(update_orders_and_inventory, session)
    except:
        raise