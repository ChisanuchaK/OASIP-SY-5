import {createRouter, createWebHistory} from 'vue-router';
import Home from "../views/Home.vue";
import BookingList from "../views/BookingList.vue"
import DialogDetails from "../views/DialogDetails.vue"
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
        path:"/bookingList/:BookingId",
        name: 'DialogDetails',
        component: DialogDetails
    }
];

const router = createRouter({ history,routes });
export default router;