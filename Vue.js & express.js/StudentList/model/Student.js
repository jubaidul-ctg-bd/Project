const Sequelize = require("sequelize");
const db = require("../database/db.js");

module.exports = db.sequelize.define(
  "students",
  {
    id: {
      type: Sequelize.INTEGER,
      primaryKey: true,
      autoInrement: true,
    },
    name: {
      type: Sequelize.STRING,
    },
    phone: {
      type: Sequelize.STRING,
    },
    age: {
      type: Sequelize.STRING,
    },
  },
  {
    timestamps: false,
  }
);
