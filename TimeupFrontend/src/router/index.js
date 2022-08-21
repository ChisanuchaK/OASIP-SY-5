import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import BookingList from '../views/BookingList.vue'
import About from '../views/About.vue'
import Reserve from '../views/Reserve.vue'
import CategoryList from '../views/CategoryList.vue'
import UserList from '../views/Users/UserList.vue'
import SignIn from '../views/Users/SignIn.vue'
import SignUp from '../views/Users/SignUp.vue'
// const history = createWebHistory(`${import.meta.env.VITE_ROUTER_URL}`);
const history = createWebHistory()
const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/bookingList',
    name: 'BookingList',
    component: BookingList
  },
  {
    path: '/aboutus',
    name: 'AboutUs',
    component: About
  },
  {
    path: '/Reserve',
    name: 'Reserve',
    component: Reserve
  },
  {
    path: '/CategoryList',
    name: 'CategoryList',
    component: CategoryList
  },
  {
    path: '/userList',
    name: 'UserList',
    component: UserList
  },{
    path: '/sign-in',
    name: 'SignIn',
    component: SignIn
  },{
    path: '/sign-up',
    name: 'SignUp',
    component: SignUp
  }
]

const router = createRouter({ history, routes })
export default router
