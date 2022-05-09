<script setup>
import {ref , onBeforeMount,computed} from 'vue'
import moment from 'moment';
import DialogDetails from '../views/DialogDetails.vue';


const emits = defineEmits(['idDialogDetails'])
const props = defineProps({
    bookings: {
        type: Object,
        require: true
    },
})

const bookingList = computed(()=> props.bookings)  ;

onBeforeMount(async () => {
console.log(bookingList);
console.log(props.bookings);
// setTimeout(1,()=>console.log("aa"+superList))
// console.log(" Show Bookings "+ props.bookings);
})

// const dialogs = ref(false);

const changeSeeDetailsDialog = (booking) => {
    booking.statusClickSeeDetails = !booking.statusClickSeeDetails
    console.log(booking.statusClickSeeDetails);
    // dialogs.value = !dialogs.value
    // console.log(dialogs.value);
      }

const getIdFromDialog = (idDelete)=>{
 emits('idDialogDetails',idDelete)
}      

const getBg = (name)=>{
    switch (name) {
        case "Client-side Clinic":
            return "background-color:#82D294;"
            break;
        case "Database Clinic":
            return "background-color:#AFC8F9;"
            break;
        case "DevOps/Infra Clinic":
            return "background-color:#FAC94E;"
            break;
        case "Other":
            return "background-color:#AAA4A4;"
            break;
        case "Project Management Clinic":
            return "background-color:#B5A0E2;"
            break;
        case "Server-side Clinic":
            return "background-color:#FF7777;"
    }
}

const getSeeMoreDetail = (name)=>{
    switch (name) {
        case "Client-side Clinic":
            return "background-color:#4CA861;"
            break;
        case "Database Clinic":
            return "background-color:#3365C6;"
            break;
        case "DevOps/Infra Clinic":
            return "background-color:#BB9636;"
            break;
        case "Other":
            return "background-color:#847F7F;"
            break;
        case "Project Management Clinic":
            return "background-color:#844FF9;"
            break;
        case "Server-side Clinic":
            return "background-color:#FE5050;"
    }
}

</script>
 
<template>

    <div>
        <div>
            <div class="text-center text-xl mt-20 mb-10">
                <h1 class="uppercase font-bold text-[#ffffff]">Booking Lists</h1>
            </div>

            <div v-if="bookings == '' " class="flex flex-warp justify-center bg-emerald-800 text-white text-xl">
                    No scheduled Events
            </div>

            <div v-else class="grid grid-flow-row grid-cols-3">
                <div v-for="(booking, index) in bookingList" :key="index" :style="getBg(booking.eventCategoryName)"
                    class="mx-24 my-10 bg-white border-8 border-[#6D6D6D] rounded-lg text-xl p-5">
                    <div class="grid grid-flow-row grid-cols-8 flex p-1 ">

                        <div class="row-start-1 col-start-1 col-end-3 p-1 mb-1.5 bg-[#E2DDDD] rounded-lg -mr-12">Date</div>
                        <!-- <div class="row-start-1 col-start-3 col-end-9 p-1 mb-1.5 bg-[#E2DDDD] rounded-lg ml-16">{{ moment.utc(booking.eventStartTime).format("DD MMMM YYYY") }} -->
                        <div class="row-start-1 col-start-3 col-end-9 p-1 mb-1.5 bg-[#E2DDDD] rounded-lg ml-16">{{ moment.utc(moment.utc(booking.eventStartTime).format()).local().format("DD MMMM YYYY") }}
                        <!-- <div class="row-start-1 col-start-3 col-end-9 p-1 mb-1.5 bg-[#E2DDDD] rounded-lg ml-16">{{ new Date(booking.eventStartTime).toLocaleDateString('th-TH', {   year: 'numeric',   month: 'long',   day: 'numeric', }) }} -->
                        <!-- <div class="row-start-1 col-start-3 col-end-9 p-1 mb-1.5 bg-white rounded-lg">{{ booking.eventStartTime.split(" ")[0] }} -->
                        </div>

                        <div class="row-start-2 col-start-1 col-end-3 p-1 mb-1.5 bg-[#E2DDDD] rounded-lg -mr-12">StartTime</div>
                        <!-- <div class="row-start-2 col-start-3 col-end-9 p-1 mb-1.5 bg-[#E2DDDD] rounded-lg ml-16">{{  moment.utc(booking.eventStartTime).format("h:mm A")  }} -->
                        <div class="row-start-2 col-start-3 col-end-9 p-1 mb-1.5 bg-[#E2DDDD] rounded-lg ml-16">{{  moment.utc(moment.utc(booking.eventStartTime).format()).local().format("hh:mm A") }}
                        <!-- <div class="row-start-2 col-start-3 col-end-9 p-1 mb-1.5 bg-[#E2DDDD] rounded-lg ml-16">{{   new Date(booking.eventStartTime).toLocaleString('th-TH', {   hour: 'numeric',   minute: 'numeric' }) }} น. -->
                        <!-- <div class="row-start-2 col-start-3 col-end-9 p-1 mb-1.5 bg-white rounded-lg">{{  booking.eventStartTime.split(" ")[1] }} -->
                        </div>

                        <div class="row-start-3 col-start-1 col-end-3 p-1 mb-1.5 bg-[#E2DDDD] rounded-lg -mr-12">Duration</div>
                        <div class="row-start-3 col-start-3 col-end-9 p-1 mb-1.5 bg-[#E2DDDD] rounded-lg ml-16">{{
                                booking.eventDuration
                        }} minute </div>

                        <div class="row-start-4 col-start-1 col-end-3 p-1 mb-1.5 bg-[#E2DDDD] rounded-lg -mr-12">CategoryName</div>
                        <div class="row-start-4 col-start-3 col-end-9 p-1 mb-1.5 bg-[#E2DDDD] rounded-lg ml-16">{{ booking.eventCategoryName
                        }}</div>

                        <div class="row-start-5 col-start-1 col-end-3 p-1 mb-1.5 bg-[#E2DDDD] rounded-lg -mr-12">BookingName</div>
                        <div class="row-start-5 col-start-3 col-end-9 p-1 mb-1.5 bg-[#E2DDDD] rounded-lg ml-16">{{ booking.bookingName
                        }}</div>
        
                       <!-- <div
                            class="col-start-2 row-start-1 row-end-6   -mr-6 w-3/12 mb-1 mx-10"> a
                        </div> -->
                                
                     <button :style="getSeeMoreDetail(booking.eventCategoryName)" class="row-start-6 col-start-1 col-end-9  mt-5 bg-[#74ABFF] text-white p-3 rounded-lg" @click="changeSeeDetailsDialog(booking)">See Details</button>
                       <!-- <router-link :to="{ name: 'Details', params: { bookingId: booking.idBooking } }"> <button class="ml-5 mt-5">See Details</button> </router-link> -->
                    <!-- <router-link :to="{name: 'DialogDetails',param: { BookingId: booking.idBooking }}"> </router-link> -->
                    <!-- <button class="row-start-6 mt-5 col-start-8 btn-colorRed text-white rounded-lg" @click="$emit('deleteBooking', booking.id)">delete</button> -->
                    
                    <!-- <button class="row-start-6 mt-5 col-start-6 bg-[#499D6B] text-white rounded-lg">edit</button>
                    <button class="row-start-6 mt-5 col-start-8 bg-[#F97473] text-white rounded-lg" >delete</button> -->
                    
                    <!-- <DialogDetails v-if="booking.statusClickSeeDetails" @onCloseDetails="changeSeeDetailsDialog(booking)" :bookings="booking"/> -->
                    <DialogDetails v-if="booking.statusClickSeeDetails" @onCloseDetails="changeSeeDetailsDialog(booking)" :bookings="booking" @idConfirmDelete="getIdFromDialog"/>
                     
                     

                        <!-- <div class="grid grid-flow-row grid-cols-5 flex p-1 bg-white rounded-lg ">
                            <p class="col-start-1">Date </p>
                            <div class="col-start-2"> </div>
                            <p class="col-start-3"> {{ booking.day }}</p>
                        </div>

                        <div class="p-1 bg-white rounded-lg my-2"> StartTime : {{ booking.time }} </div>
                        <div class="p-1 bg-white rounded-lg my-2"> Duration : {{ booking.duration }}</div>
                        <div class="p-1 bg-white rounded-lg my-2"> CategoryName : {{ booking.cName }} </div>
                        <div class="p-1 bg-white rounded-lg my-2"> BookingName : {{ booking.bName }} </div> -->
                    </div>
                    
                </div>
            </div>
        </div>
    </div>

    <!-- <div class="text-center text-xl my-10">
        <h1>Booking Skills:</h1>

        <ul>
            <div v-for="(booking, index) in bookings" :key="index"
                class="flex flex-warp justify-center bg-emerald-800 text-xl">
                Day:{{ booking.day }}, Start Time:{{ booking.time }}, Duration:{{ booking.duration }}, Category Name:{{
                        booking.cName
                }}, Booking Name:{{ booking.bName }}
            </div>
        </ul>
    </div> -->
</template>
 
<style scoped>




</style>
