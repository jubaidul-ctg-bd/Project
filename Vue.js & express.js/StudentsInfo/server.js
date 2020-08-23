var express = require("express");
var bodyParser = require("body-parser");

var students = require("./routes/students");
var cors = require("cors");

var port = 3000;

var app = express();
app.use(cors());

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

app.use("/api", students);
app.listen(port, function () {
  console.log("Server start on port " + port);
});
