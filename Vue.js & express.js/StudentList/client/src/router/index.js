/* eslint-disable */
import Vue from "vue";
import Router from "vue-router";
import StudentList from "@/components/StudentList";
import HelloWorld from "@/components/HelloWorld";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/student",
      name: "StudentList",
      component: StudentList
    },
    {
      path: "/",
      name: "HelloWorld",
      component: HelloWorld
    }
  ]
});
