// 실습 1
// account 에 대해서 symbol로 그룹핑
// 회사 거래별 누적 수량
// 그중에서 상위 3개
// msft 에 대한 값만 추출
// customer 정보와 account 정보도 함께 출력 (lookup)

use("sample_analytics");

db.transactions.aggregate([
  {
    // transactions 를 unwinding
    $unwind: "$transactions",
  },
  {
    // account 별로 symbol 에 따라서 그룹핑하고 그룹에 대하여 transaction_code 에 따라 합 연산 수행
    $group: {
      _id: {
        account_id: "$account_id",
        symbol: "$transactions.symbol",
      },
      currentHolding: {
        $sum: {
          $cond: [
            {
              $eq: ["$transactions.transaction_code", "buy"],
            },
            "$transactions.amount",
            {
              $multiply: ["$transactions.amount", -1],
            },
          ],
        },
      },
    },
  },
  {
    // 회사 symbol 이 msft 인 것을 확인
    $match: {
      "_id.symbol": "msft",
    },
  },
  {
    // accounts 를 lookup
    $lookup: {
      from: "accounts",
      localField: "_id.account_id",
      foreignField: "account_id",
      as: "account_info",
      pipeline: [
        {
          // customers 를 lookup
          $lookup: {
            from: "customers",
            localField: "account_id",
            foreignField: "accounts",
            as: "customer_info",
            pipeline: [
              {
                $project: {
                  username: 1,
                  _id: 0,
                },
              },
            ],
          },
        },
        {
          $project: {
            _id: 0,
            account_id: 0,
          },
        },
        {
          $unwind: "$customer_info",
        },
      ],
    },
  },
  {
    $unwind: "$account_info",
  },
  {
    $project: {
      _id: 0,
      user: "$account_info.customer_info.username",
      account_id: "$_id.account_id",
      symbol: "$_id.symbol",
      currentHolding: 1,
      account_info: {
        limit: 1,
        products: 1,
      },
    },
  },
  {
    // currentHolding에 대하여 내림차순 정렬
    $sort: {
      currentHolding: -1,
    },
  },
  {
    // top 3 record만 출력
    $limit: 3,
  },
]);

//----------------------------------------------------------------
// 실행 계획 확인
db.transactions
  .aggregate([
    {
      // transactions 를 unwinding
      $unwind: "$transactions",
    },
    {
      // account 별로 symbol 에 따라서 그룹핑하고 그룹에 대하여 transaction_code 에 따라 합 연산 수행
      $group: {
        _id: {
          account_id: "$account_id",
          symbol: "$transactions.symbol",
        },
        currentHolding: {
          $sum: {
            $cond: [
              {
                $eq: ["$transactions.transaction_code", "buy"],
              },
              "$transactions.amount",
              {
                $multiply: ["$transactions.amount", -1],
              },
            ],
          },
        },
      },
    },
    {
      // 회사 symbol 이 msft 인 것을 확인
      $match: {
        "_id.symbol": "msft",
      },
    },
    {
      // accounts 를 lookup
      $lookup: {
        from: "accounts",
        localField: "_id.account_id",
        foreignField: "account_id",
        as: "account_info",
        pipeline: [
          {
            // customers 를 lookup
            $lookup: {
              from: "customers",
              localField: "account_id",
              foreignField: "accounts",
              as: "customer_info",
              pipeline: [
                {
                  $project: {
                    username: 1,
                    _id: 0,
                  },
                },
              ],
            },
          },
          {
            $project: {
              _id: 0,
              account_id: 0,
            },
          },
          {
            $unwind: "$customer_info",
          },
        ],
      },
    },
    {
      $unwind: "$account_info",
    },
    {
      $project: {
        _id: 0,
        user: "$account_info.customer_info.username",
        account_id: "$_id.account_id",
        symbol: "$_id.symbol",
        currentHolding: 1,
        account_info: {
          limit: 1,
          products: 1,
        },
      },
    },
    {
      // currentHolding에 대하여 내림차순 정렬
      $sort: {
        currentHolding: -1,
      },
    },
    {
      // top 3 record만 출력
      $limit: 3,
    },
  ])
  .explain("executionStats");

```
실행 시간은 1283ms의 시간이 소요
실행 계획을 확인 했을때 lookup 부분에서 문제가 있다는 것을 확인할 수 있다.

pipeline: [
  {
    '$lookup': {
      from: 'customers',
      localField: 'account_id',
      foreignField: 'accounts',
      as: 'customer_info',
      pipeline: [
        {
          '$project': {
            username: 1,
            _id: 0
          }
        }
      ]
    }
  },
  {
    '$project': {
      _id: 0,
      account_id: 0
    }
  },
  {
    '$unwind': '$customer_info'
  }
],
unwinding: {
  preserveNullAndEmptyArrays: false
}
},
totalDocsExamined: 1129738,
totalKeysExamined: 0,
collectionScans: 1006,
indexesUsed: [],
nReturned: 503,
executionTimeMillisEstimate: 1123
},

- 읽어들인 record는 1129738개 인데 반해
- 실제 리턴된 데이터는 503개에 불과하다.
```;

// sorting과 limit을 lookup stage 전으로 옮겨 보자
db.transactions
  .aggregate([
    {
      // transactions 를 unwinding
      $unwind: "$transactions",
    },
    {
      // account 별로 symbol 에 따라서 그룹핑하고 그룹에 대하여 transaction_code 에 따라 합 연산 수행
      $group: {
        _id: {
          account_id: "$account_id",
          symbol: "$transactions.symbol",
        },
        currentHolding: {
          $sum: {
            $cond: [
              {
                $eq: ["$transactions.transaction_code", "buy"],
              },
              "$transactions.amount",
              {
                $multiply: ["$transactions.amount", -1],
              },
            ],
          },
        },
      },
    },
    {
      // 회사 symbol 이 msft 인 것을 확인
      $match: {
        "_id.symbol": "msft",
      },
    },
    {
      // currentHolding에 대하여 내림차순 정렬
      $sort: {
        currentHolding: -1,
      },
    },
    {
      // top 3 record만 출력
      $limit: 3,
    },
    {
      // accounts 를 lookup
      $lookup: {
        from: "accounts",
        localField: "_id.account_id",
        foreignField: "account_id",
        as: "account_info",
        pipeline: [
          {
            // customers 를 lookup
            $lookup: {
              from: "customers",
              localField: "account_id",
              foreignField: "accounts",
              as: "customer_info",
              pipeline: [
                {
                  $project: {
                    username: 1,
                    _id: 0,
                  },
                },
              ],
            },
          },
          {
            $project: {
              _id: 0,
              account_id: 0,
            },
          },
          {
            $unwind: "$customer_info",
          },
        ],
      },
    },
    {
      $unwind: "$account_info",
    },
    {
      $project: {
        _id: 0,
        user: "$account_info.customer_info.username",
        account_id: "$_id.account_id",
        symbol: "$_id.symbol",
        currentHolding: 1,
        account_info: {
          limit: 1,
          products: 1,
        },
      },
    },
  ])
  .explain("executionStats");

```
실행 결과를 살펴보면
- 실행 시간은 기존 1283ms에서 134ms로 감소
- 스캔하는 데이터의 양도 감소한 것을 확인할 수 있다.
```;

// lookup 을 사용하기 전에 최대한 적은 데이터를 넘겨주는 것이 중요하다는 것을 확인할 수 있다.
