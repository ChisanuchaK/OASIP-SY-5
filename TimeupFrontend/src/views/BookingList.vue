<script setup>
import { ref, onBeforeMount, onMounted } from 'vue'
import moment from 'moment';
import NavbarTop from "../components/navbarTop.vue";
import NavbarBottom from "../components/navbarBottom.vue";
import LoopBookingList from "../components/LoopBookingList.vue";
import { getBookings } from '../stores/book.js';
const bookings = ref([]);

const dates = ref([]);


// const eventStartTime = "2022-06-27 02:30";
// const dates = moment.utc(eventStartTime).format("DD MMMM YYYY");
// const startTimes = moment.utc(eventStartTime).format("h:mm");
// console.log(dates);
// console.log(startTimes);

onBeforeMount(async () => {
 bookings.value = await getBookings();
 bookings.value.map((s)=>{
 s.status = ref(false)
})
  console.log(bookings.value);
  // dates.value = await bookings.value.idBooking
  // console.log(dates.value);
})


</script>
 
<template>

<div>
<NavbarTop/>
       <!-- <LoopBookingList :bookings="bookings" @deleteBooking="removeBooking"/> -->
       <LoopBookingList :bookings="bookings" />
<NavbarBottom/>
</div>

</template>
 
<style>

</style>