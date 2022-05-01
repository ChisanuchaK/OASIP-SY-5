<script setup>
import { ref, onBeforeMount } from "vue";
import { useRoute, useRouter } from 'vue-router';
import NavbarTop from "../components/navbarTop.vue";
import NavbarBottom from "../components/navbarBottom.vue";
import { getBookingId } from "../stores/book.js";
import moment from "moment";

const bookingsArray = ref({});

const { params } = useRoute();
const appRouter = useRouter();

const Time = ref([]);
const date = ref("");
const startTime = ref("");

const goBack = () => appRouter.go(-1);

//   const getBookingId = async (bookingId)=>{
//     const res = await fetch(`${import.meta.env.VITE_BASE_URL}/booking/${bookingId}`);
//     if(res.status === 200){
//     bookingsArray.value = await res.json();
//     }else{
//     console.log('not found');
//     }
//    }

onBeforeMount(async () => {
    const res = await getBookingId(params.bookingId);
    bookingsArray.value = res
    console.log(bookingsArray.value);
    Time.value = bookingsArray.value.eventStartTime
    console.log(Time.value);
    date.value = moment.utc(Time.value).format("DD MMMM YYYY")
    startTime.value = moment.utc(Time.value).format("h:mm")
})
</script>
 
<template>
    <div>
        <NavbarTop />
        <NavbarBottom />
        <div class="mt-24">
            <button class="top-0 left-0 h-16 w-16" @click="goBack">BACK</button>
            <div class="text-center">Details Of Scheduled</div>
            <div class="">
                <div class="mt-20 bg-white p-20 mx-auto w-3/4 border rounded-xl">
                    <div class="grid grid-flow-row grid-cols-3">
                        <div class="row-start-1 col-start-1">ID</div>
                        <div class="row-start-1 col-start-3">{{ bookingsArray.idBooking }}</div>

                        <div class="row-start-2 col-start-1">Date</div>
                        <div class="row-start-2 col-start-3">{{ date }}</div>

                        <div class="row-start-3 col-start-1">EventStartTime</div>
                        <div class="row-start-3 col-start-3">{{ startTime }} AM</div>

                        <div class="row-start-4 col-start-1">Duration</div>
                        <div class="row-start-4 col-start-3">{{ bookingsArray.eventDuration }}</div>

                        <div class="row-start-5 col-start-1">CategoryName</div>
                        <div class="row-start-5 col-start-3">{{ bookingsArray.eventCategoryName }}
                        </div>

                        <div class="row-start-6 col-start-1">BookingName</div>
                        <div class="row-start-6 col-start-3">{{ bookingsArray.bookingName }}</div>

                        <div class="row-start-7 col-start-1">Email</div>
                        <div class="row-start-7 col-start-3">{{ bookingsArray.bookingEmail }}</div>

                        <div class="row-start-8 col-start-1">EventNote</div>
                        <div class="row-start-8 col-start-3">{{ bookingsArray.eventNote }}</div>
                        <!-- <div v-else class="row-start-8 col-start-3"> - </div> -->


                        <div></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
 
<style>
</style>