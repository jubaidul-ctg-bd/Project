/* var express = require("express");
var router = express.Router();
const Student = require("../model/Student");

// Get All Students info
router.get("/students", (req, res) => {
  Student.findAll()
    .then((students) => {
      res.json(students);
    })
    .catch((err) => {
      res.send("Error " + err);
    });
});

// Add Student
router.post("/student", (req, res) => {
  if (!req.body.fname || !req.body.lname || !req.body.age) {
    res.status(400);
    res.json({
      error: "Bad Data",
    });
  } else {
    Student.create(req.body)
      .then(() => {
        res.send("Task Add");
      })
      .catch((err) => {
        res.send("Error " + err);
      });
  }
});

// Delete Student
router.delete("/student/:id", (req, res) => {
  Student.destroy({
    where: {
      id: req.param.id,
    },
  })
    .then(() => {
      req.send("Student Info Deleted!");
    })
    .catch((err) => {
      res.send("Error " + err);
    });
});

// Update Student
router.put("/student/:id", (req, res) => {
  if (!req.body.fname || !req.body.lname || !req.body.age) {
    res.status(400);
    res.json({
      error: "Bad Data",
    });
  } else {
    Student.update(
      { fname: req.body.fname, lname: req.body.lname, age: req.body.age },
      { where: { id: req.param.id } }
    )
      .then(() => {
        res.send("Task Updated");
      })
      .catch((err) => {
        res.send("Error " + err);
      });
  }
});
 */
