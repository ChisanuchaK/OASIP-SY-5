import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import About from "../views/About.vue";
import CategoryLists from "../views/Categorys/CategoryLists.vue";
import SignIn from "../views/Users/SignIn.vue";
import CreateUser from "../views/Users/CreateUser.vue";
import CreateBooking from "../views/Bookings/CreateBooking.vue";
import BookingLists from "../views/Bookings/BookingLists.vue";
import UserLists from "../views/Users/UserLists.vue";
import UserDetail from "../views/Users/UserDetail.vue";
import EditUser from "../views/Users/EditUser.vue"

const history = createWebHistory(`${import.meta.env.VITE_ROUTER_URL}`);
// const history = createWebHistory();
const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/about",
    name: "About",
    component: About,
  },
  {
    path: '/categorylists',
    name: 'CategoryLists',
    component: CategoryLists
  },
  {
    path: "/sign-in",
    name: "SignIn",
    component: SignIn,
  },
  {
    path: '/createuser',
    name: 'CreateUser',
    component: CreateUser
  },
    {
    path: '/createbooking',
    name: 'CreateBooking',
    component: CreateBooking
  },
  {
    path: '/bookinglists',
    name: 'BookingLists',
    component: BookingLists
  },
  {
    path: '/userlists',
    name: 'UserLists',
    component: UserLists
  },
    {
    path: '/userlist/userdetail/:iduser',
    name: 'UserDetail',
    component: UserDetail
  },
  {
    path: '/userlist/edituser/:iduser',
    name: 'EditUser',
    component: EditUser
  }
  // {
  //   path: '/sign-up',
  //   name: 'SignUp',
  //   component: SignUp
  // }
];

const router = createRouter({ history, routes });
export default router;
