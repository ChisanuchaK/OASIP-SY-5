import {createRouter, createWebHistory} from 'vue-router';
import Home from "../views/Home.vue";
import BookingList from "../views/BookingList.vue";
import DialogDetails from "../views/DialogDetails.vue";
import About from "../views/About.vue";
import Reserve from "../views/Reserve.vue";
import CategoryList from "../views/CategoryList.vue";

const history = createWebHistory("/sy5");
const routes = [
    {
        path: "/",
        name: "Home",
        component: Home 
    },{
        path: "/bookingList",
        name: "BookingList",
        component:BookingList
    },{
        path:"/bookingList/d",
        name: 'DialogDetails',
        component: DialogDetails
    },{
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