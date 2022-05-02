import {createRouter, createWebHistory} from 'vue-router';
import Home from "../views/Home.vue";
import BookingList from "../views/BookingList.vue"
import DialogDetails from "../views/DialogDetails.vue"
import Details from "../views/Details.vue";
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
<<<<<<< HEAD
    },{
=======
    },,{
>>>>>>> 8ab1cd4033f6bafe8429f3ed98b3dda711ee5860
        path:"/bookingList/details/:bookingId",
        name: 'Details',
        component: Details
    }
];

const router = createRouter({ history,routes });
export default router;