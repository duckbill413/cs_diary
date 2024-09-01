use("sample_training");

// class_id 를 기준으로 grouping 을 하고 exam과 quiz에 대해서만 평균 점수를 산출
// 결과값
[
  {
    _id: 500,
    scores: [
      { k: "exam", v: 45.04082924638036 },
      { k: "quiz", v: 51.35603805996617 },
    ],
  },
  {
    _id: 499,
    scores: [
      { k: "exam", v: 52.234488613263466 },
      { k: "quiz", v: 48.96268506750967 },
    ],
  },
  {
    _id: 498,
    scores: [
      { k: "exam", v: 48.51775335555769 },
      { k: "quiz", v: 53.827492248151465 },
    ],
  },
  {
    _id: 497,
    scores: [
      { k: "exam", v: 50.80561533355925 },
      { k: "quiz", v: 51.27682967858154 },
    ],
  },
  {
    _id: 496,
    scores: [
      { k: "exam", v: 47.28546854417578 },
      { k: "quiz", v: 50.30975687853305 },
    ],
  },
];

// 방법 1
db.grades.aggregate([
  {
    $unwind: "$scores",
  },
  {
    $match: {
      "scores.type": {
        $in: ["exam", "quiz"],
      },
    },
  },
  {
    $group: {
      // 멀티 필드에 대한 grouping
      _id: {
        class_id: "$class_id",
        type: "$scores.type",
      },
      score: {
        $avg: "$scores.score",
      },
    },
  },
  {
    $group: {
      _id: "$_id.class_id",
      scores: {
        $push: {
          type: "$_id.type",
          score: "$score",
        },
      },
    },
  },
  {
    $sort: {
      _id: -1,
    },
  },
  {
    $limit: 5,
  },
]);

// 방법 2
// 하나의 배열에 score 점수를 삽입
db.grades.aggregate([
  {
    $addFields: {
      tmp_scores: {
        $filter: {
          input: "$scores",
          as: "scores_var",
          cond: {
            $or: [{ $eq: ["$$scores_var.type", "exam"] }, { $eq: ["$$scores_var.type", "quiz"] }],
          },
        },
      },
    },
  },
  {
    $unset: ["scores", "student_id"],
  },
  {
    $unwind: "$tmp_scores",
  },
  {
    $group: {
      _id: "$class_id",
      exam_scores: {
        $push: {
          $cond: {
            if: {
              $eq: ["$tmp_scores.type", "exam"],
            },
            then: "$tmp_scores.score",
            else: "$$REMOVE",
          },
        },
      },
      quiz_scores: {
        $push: {
          $cond: {
            if: {
              $eq: ["$tmp_scores.type", "quiz"],
            },
            then: "$tmp_scores.score",
            else: "$$REMOVE",
          },
        },
      },
    },
  },
  {
    $project: {
      _id: 1,
      scores: {
        $objectToArray: {
          exam: {
            $avg: "$exam_scores",
          },
          quiz: {
            $avg: "$quiz_scores",
          },
        },
      },
    },
  },
  {
    $sort: {
      _id: 1,
    },
  },
]);
