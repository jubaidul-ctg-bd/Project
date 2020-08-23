var express = require("express");
var router = express.Router();
const Task = require("../model/Task");

//Get all Tasks
router.get("/tasks", (req, res) => {
  Task.findAll()
    .then((tasks) => {
      res.json(tasks);
    })
    .catch((err) => {
      res.send("error: " + err);
    });
});

//Add task
router.post("/task", (req, res) => {
  if (!req.body.task_name) {
    res.status(400);
    res.json({
      error: "Bad Data",
    });
  } else {
    Task.create(req.body)
      .then(() => {
        res.send("Task Add");
      })
      .catch((err) => {
        res.send("Error :" + err);
      });
  }
});

// Delete Task
router.delete("/task/:id", (req, res) => {
  Task.destroy({
    where: {
      id: req.params.id,
    },
  })
    .then(() => {
      req.send("Task Deleted!");
    })
    .catch((err) => {
      res.send("error: " + err);
    });
});

// Update Task
router.put("/task/:id", (req, res) => {
  if (!req.body.task_name) {
    res.status(400);
    res.json({
      error: "Bad Data",
    });
  } else {
    Task.update(
      { task_name: req.body.task_name },
      { where: { id: req.params.id } }
    )
      .then(() => {
        res.send("Task Updated");
      })
      .error((err) => res.send(err));
  }
});

module.exports = router;