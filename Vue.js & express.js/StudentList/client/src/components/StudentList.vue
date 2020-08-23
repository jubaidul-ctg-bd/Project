<template>
  <div class="hello">
    <div id="todo-list-example" class="container">
      <div class="row">
        <div class="col-md-6 mx-auto">
          <h1 class="text-center">Students App</h1>
          <form v-on:submit.prevent="addNewTask">
            <label for="tasknameinput">Student Information</label>
            <input v-model="name" id="tasknameinput" class="form-control" placeholder="Name" />
            <input v-model="phone" id="tasknameinput" class="form-control" placeholder="Phone" />
            <input v-model="age" id="tasknameinput" class="form-control" placeholder="Age" />
            <button
              v-if="this.isEdit == false"
              type="submit"
              class="btn btn-success btn-block mt-3"
            >Submit</button>
            <button
              v-else
              type="button"
              v-on:click="updateTask()"
              class="btn btn-primary btn-block mt-3"
            >Update</button>
          </form>

          <table class="table">
            <thead>
              <th>Name</th>
              <th>Phone</th>
              <th>Age</th>
              <th>Action</th>
            </thead>
            <tr v-for="(student) in students" v-bind:key="student.id" v-bind:title="student.name">
              <td>{{student.name}}</td>
              <td>{{student.phone}}</td>
              <td>{{student.age}}</td>
              <td>
                <button
                  v-on:click="editTask(student.name, student.phone, student.age, student.id)"
                  class="btn btn-info"
                >Edit</button>
                <button v-on:click="deleteTask(student.id)" class="btn btn-danger">Delete</button>
              </td>
            </tr>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
import axios from "axios";
export default {
  data() {
    return {
      students: [],
      id: "",
      name: "",
      phone: "",
      age: "",
      isEdit: false
    };
  },
  mounted() {
    this.getTasks();
  },
  methods: {
    getTasks() {
      axios.get("/api/students").then(
        result => {
          console.log(result.data);
          this.students = result.data;
        },
        error => {
          console.error(error);
        }
      );
    },
    addNewTask() {
      axios
        .post("/api/student", {
          name: this.name,
          phone: this.phone,
          age: this.age
        })
        .then(res => {
          this.name = "";
          this.phone = "";
          this.age = "";
          this.getTasks();
          console.log(res);
        })
        .catch(err => {
          console.log(err);
        });
    },
    editTask(name, phone, age, id) {
      this.id = id;
      this.name = name;
      this.phone = phone;
      this.age = age;
      this.isEdit = true;
    },
    updateTask() {
      axios
        .put(`/api/student/${this.id}`, {
          name: this.name,
          phone: this.phone,
          age: this.age
        })
        .then(res => {
          this.name = "";
          this.phone = "";
          this.age = "";
          this.isEdit = false;
          this.getTasks();
          console.log(res);
        })
        .catch(err => {
          console.log(err);
        });
    },
    deleteTask(id) {
      axios
        .delete(`/api/student/${id}`)
        .then(res => {
          this.name = "";
          this.getTasks();
          console.log(res);
        })
        .catch(err => {
          console.log(err);
        });
    }
  }
};
</script>