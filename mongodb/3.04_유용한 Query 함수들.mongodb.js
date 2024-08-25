// using bulk operations (insertOne, deleteOne, updateOne)
db.bulk.bulkWrite([
  { insertOne: { doc: 1, order: 1 } },
  { insertOne: { doc: 2, order: 2 } },
  { insertOne: { doc: 3, order: 3 } },
  { insertOne: { doc: 4, order: 4 } },
  { insertOne: { doc: 4, order: 5 } },
  { insertOne: { doc: 5, order: 6 } },
  {
    deleteOne: {
      filter: { doc: 3 },
    },
  },
  {
    updateOne: {
      filter: { doc: 2 },
      update: {
        $set: { doc: 12 },
      },
    },
  },
]);

// ordered property
// 순서와 상관없이 수행 (성능 우선)
db.bulk.bulkWrite([
  { insertOne: { doc: 1, order: 1 } },
  { insertOne: { doc: 2, order: 2 } },
  { insertOne: { doc: 3, order: 3 } },
  { insertOne: { doc: 4, order: 4 } },
  { insertOne: { doc: 4, order: 5 } },
  { insertOne: { doc: 5, order: 6 } },
  {
    updateOne: {
      filter: { doc: 2 },
      update: {
        $set: { doc: 12 },
      },
    },
  },
  {
    deleteOne: {
      filter: { doc: 3 },
    },
  },
  { ordered: false },
]);

// count Document
db.bulk.countDocuments();
db.bulk.count(); // count() is deprecated
db.bulk.estimatedDocumentCount(); // 정확도 보다 성능을 우선시한 Count

// distinct (중복 제거된 조회)
db.bulk.distinct("doc");

// findAndModify() 하나씩만 수정됨
db.bulk.findAndModify({
  query: { doc: 5 },
  sort: { order: -1 },
  update: { $inc: { doc: 1 } },
});

// findAndModify 는 동시성 처리를 위해서 사용될 수 있음
/**
 * 마지막 번호를 가져오고 싶을 때
 * - 만약, max 를 이용하여 조회를 하고 max + 1 로 마지막 번호를 한다고 가정할때
 * - 여러 사람이 동시에 max 를 조회한다면 같은 max 값을 가지게 된다.
 * - 문제를 해결하기 위해서 `sequence` 와 같은 별도 collection 을 생성하고 findAndModify() 로
 * - 마지막 번호를 불러올 수 있다.
 * - findAndModify 로 마지막 번호를 불러오고 값을 +1 해주면 된다.
 */

// sequence collection 생성 및 초기값 생성
db.sequence.insertOne({ seq: 0 });
// 마지막 번호 조회 및 값 증가 수행
db.sequence.findAndModify({
  query: {},
  sort: { order: -1 },
  update: { $inc: { seq: 1 } },
});

// getIndex() 해당 컬렉션에 생성된 인덱스를 조회
db.bulk.getIndexes();

// index 생성 명령
// bulk collection 에서 doc 컬럼을 인덱스로 생성
db.bulk.createIndex({ doc: 1 });

// replaceOne()
// update 는 field 를 수정 replace 는 전체를 수정 (_id 는 변경되지 않음)
db.bulk.replaceOne({ doc: 1 }, { doc: 13 });
