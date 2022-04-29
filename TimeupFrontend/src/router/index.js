import {createRouter, createWebHistory} from 'vue-router';
import Home from "../views/Home.vue";
import BookingList from "../views/BookingList.vue"
const history = createWebHistory();
const routes = [
    {
        path: "/",
        name: "Home",
        component: Home 
    },{
        path: "/bookingList",
        name: "BookingList",
        component:BookingList
    }
];

const router = createRouter({ history,routes });
export default router;