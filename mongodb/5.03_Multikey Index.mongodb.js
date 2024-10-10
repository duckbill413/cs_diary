use("sample_weatherdata")

// sections 필드에 대하여 index 생성
db.data.createIndex({sections: -1})

// AG1 필드를 검색
db.data.find({sections: 'AG1'})

// 실행 계획 확인
db.data.find({sections: 'AG1'}).explain('executionStats')

```
indexName: 'sections_-1',
isMultiKey: true,

실행 결과를 보면 sections index 가
multikey index로 생성되어 활용됨을 확인할 수 있음
```

// 배열 안의 내장 document에 index 추가
use('sample_training')

db.grades.findOne()
```result
{
  _id: ObjectId('56d5f7eb604eb380b0d8d8d5'),
  student_id: 0,
  scores: [
    {
      type: 'exam',
      score: 85.08219987294274
    },
    {
      type: 'quiz',
      score: 97.95868307329667
    },
    {
      type: 'homework',
      score: 37.01609289831771
    },
    {
      type: 'homework',
      score: 7.934055059943413
    }
  ],
  class_id: 466
}
```

// 인덱스 생성
db.grades.createIndex({"scores.type": 1})

// 인덱스 생성 조회
db.grades.getIndexes()

// 실행 계획 확인
db.grades.find({
    "scores.type": "exam"
}).explain('executionStats')

// Compound Index or Multikey Index
db.grades.createIndex({class_id: 1, "scores.type": 1})

// index 삭제
db.grades.dropIndex({"scores.type": 1})

db.grades.find(
    {
        "scores.type": "exam",
        class_id: {
            $gte: 350
        }
    }
).explain('executionStats')

```
indexName: 'class_id_1_scores.type_1',
isMultiKey: true,

multikey index는 Compound Index로도 사용할 수 있다.
```