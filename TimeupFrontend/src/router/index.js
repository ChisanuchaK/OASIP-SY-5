import {createRouter, createWebHistory} from 'vue-router';
import Home from "../views/Home.vue";
import BookingList from "../views/BookingList.vue";
import About from "../views/About.vue";
import Reserve from "../views/Reserve.vue";
import CategoryList from "../views/CategoryList.vue";

const history = createWebHistory(`${import.meta.env.VITE_ROUTER_URL}`);
// const history = createWebHistory();
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
    ,{
        path:"/About",
        name: 'About',
        component: About
    },{
        path:"/Reserve",
        name: 'Reserve',
        component: Reserve
    },{
        path:"/CategoryList",
        name: 'CategoryList',
        component: CategoryList
    }
];

const router = createRouter({ history,routes });
export default router;