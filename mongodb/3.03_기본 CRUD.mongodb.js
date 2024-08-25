// Select the database to use.
use("test");
// insert one document
db.employees.insertOne({
  name: "lake",
  age: 21,
  dept: "Database",
  joinDate: new ISODate("2024-08-24"),
  salary: 400000,
  bonus: null,
});

// insert multiple documents
db.employees.insertMany([
  {
    name: "ocean",
    age: 45,
    dept: "Network",
    joinDate: new ISODate("1999-11-15"),
    salary: 100000,
    resignationDate: new ISODate("2020-12-12"),
    bonus: null,
  },
  {
    name: "river",
    age: 34,
    dept: "DevOps",
    isNegotiating: true,
  },
]);

// test insert data one-by-one
for (i = 0; i < 300; i++) {
  db.insertTest.insertOne({ a: i });
}

// test insert data using insertMany
var docs = [];
for (i = 0; i < 300; i++) {
  docs.push({ a: i });
}
db.insertTest.insertMany(docs);

// update data
db.employees.updateOne(
  { name: "river" },
  {
    $set: {
      salary: 350000,
      dept: "Database",
      joinDate: new ISODate("2024-08-24"),
    },
    $unset: {
      isNegotiating: "",
    },
  }
);

// update increasing salary 10% than ordinal
db.employees.updateMany(
  {
    resignationDate: { $exists: false },
    joinDate: { $exists: true },
  },
  { $mul: { salary: Decimal128("1.1") } } // consider type and number of digits
);

// if the column is null mongodb think that is `null`
// the following operation may cause problem due to null column
db.employees.updateMany(
  {
    resignationDate: { $exists: false },
    bonus: null,
  },
  { $set: { bonus: 100000 } }
);
// after modifing
db.employees.updateMany(
  {
    resignationDate: { $exists: false },
    bonus: { $exists: true },
  },
  { $set: { bonus: 200000 } }
);

// delete one data
db.employees.deleteOne({ name: "river" });

// delete many data
db.employees.deleteMany({});

// delete collection
db.employees.drop();

// use sample_guides db
use("sample_guides");

// select plantet mars
db.planets.findOne({ name: "Mars" });

// $lte
db.planets.find({
  hasRings: true,
  orderFromSun: { $lte: 6 },
});

// $and
db.planets.find({
  $and: [{ hasRings: true }, { orderFromSun: { $lte: 6 } }],
});

// $or, $ne
db.planets.find({
  $or: [{ hasRings: { $ne: false } }, { orderFromSun: { $gt: 6 } }],
});

// find data in array
db.planets.find({ mainAtmosphere: { $in: ["O2"] } });
