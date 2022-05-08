import {createRouter, createWebHistory} from 'vue-router';
import Home from "../views/Home.vue";
import BookingList from "../views/BookingList.vue"
import DialogDetails from "../views/DialogDetails.vue"
import Details from "../views/Details.vue";
import category from "../views/Category.vue"
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
    },{
        path:"/bookingList/d",
        name: 'DialogDetails',
        component: DialogDetails
    },{
        path:"/bookingList/details/:bookingId",
        name: 'Details',
        component: Details
    },
    {
        path:"/category",
        name: 'Category',
        component: category
    }
];

const router = createRouter({ history,routes });
export default router;