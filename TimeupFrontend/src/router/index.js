import {createRouter, createWebHistory} from 'vue-router';
import Home from "../views/Home.vue";
import BookingList from "../views/BookingList.vue"
import DialogDetails from "../views/DialogDetails.vue"
import Details from "../views/Details.vue";
import category from "../views/Category.vue"
const history = createWebHistory();
const routes = [
    {
        path: "/sy5/",
        name: "Home",
        component: Home 
    },{
        path: "/sy5/bookingList",
        name: "BookingList",
        component:BookingList
    },{
        path:"/sy5/bookingList/d",
        name: 'DialogDetails',
        component: DialogDetails
    },{
        path:"/sy5/bookingList/details/:bookingId",
        name: 'Details',
        component: Details
    },
    {
        path:"/sy5/category",
        name: 'Category',
        component: category
    }
];

const router = createRouter({ history,routes });
export default router;