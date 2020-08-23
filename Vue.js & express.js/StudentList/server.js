var express = require("express")
var bodyParser = require("body-parser")
var cors = require("cors")
var app = express()
var port = 3000

var students = require("./routes/students")

app.use(cors())

app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: false }))

app.use("/api", students)
app.listen(port, function () {
	console.log("server started on port " + port)
})
