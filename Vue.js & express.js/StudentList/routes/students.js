var express = require("express")
var router = express.Router()
const Student = require("../model/Student")

//Get all students
router.get("/students", (req, res) => {
	Student.findAll()
		.then((students) => {
			res.json(students)
		})
		.catch((err) => {
			res.send("error: " + err)
		})
})

//Add task
router.post("/student", (req, res) => {
	if (!req.body.name) {
		res.status(400)
		res.json({
			error: "Bad Data",
		})
	} else {
		Student.create(req.body)
			.then(() => {
				res.send("Student Add")
			})
			.catch((err) => {
				res.send("Error :" + err)
			})
	}
})

// Delete Student
router.delete("/student/:id", (req, res) => {
	Student.destroy({
		where: {
			id: req.params.id,
		},
	})
		.then(() => {
			req.send("Student Deleted!")
		})
		.catch((err) => {
			res.send("error: " + err)
		})
})

// Update Task
router.put("/student/:id", (req, res) => {
	if (!req.body.name) {
		res.status(400)
		res.json({
			error: "Bad Data",
		})
	} else {
		Student.update(
			{ name: req.body.name, phone: req.body.phone, age: req.body.age },
			{ where: { id: req.params.id } }
		)
			.then(() => {
				res.send("Student Updated")
			})
			.error((err) => res.send(err))
	}
})

module.exports = router
