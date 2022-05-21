<script setup>
import { ref, onBeforeMount, onMounted, computed } from 'vue'
import NavbarTop from "../components/NavbarTop.vue";
import NavbarBottom from "../components/NavbarBottom.vue";
import LoopBookingList from "../components/LoopBookingList.vue";
import FilterBar from "../components/FilterBar.vue"
import { getBookings, getEventCategory } from '../stores/book.js';
import moment from 'moment';

const bookings = ref([]);
const reBooks = computed(() => bookings.value)
const allBooking = ref();
const categoryLists = ref([]);
// const eventStartTime = "2022-06-27 02:30";
const dates = moment().local().format("YYYY-MM-DD");
// const startTimes = moment.utc(eventStartTime).format("h:mm");
console.log(dates);
// console.log(startTimes);


onBeforeMount(async () => {
  const getAllBooks = await getBookings();
  bookings.value = await getAllBooks.json();
  allBooking.value = bookings.value
  // allBooking.value = await getAllBooks.json();
  const getAllCategory = await getEventCategory();
  categoryLists.value = await getAllCategory.json();

  bookings.value.map((s) => {
    s.statusClickSeeDetails = ref(false)
    //  s.statusClickDelete = ref(false)

    //  s.statusClickEdit = ref(false)
    //  s.statusCancelEdit = ref(false)
    //  s.statusConfirmEdit = ref(false)

    //  s.statusClickDelete = ref(false)
    //  s.statusCancelEdit = ref(false)
    //  s.statusConfirmEdit = ref(false)
  })
})

const filterList = (idDeleteFromLoop) => {
  bookings.value = bookings.value.filter((BookingInBookings) => (BookingInBookings.idBooking != idDeleteFromLoop))
}

const filterEditBooking = (editId) => {
  bookings.value = bookings.value.map((booking) =>
    booking.idBooking === editId.idBooking
      ? { ...booking, eventStartTime: editId.eventStartTime, eventNotes: editId.eventNotes }
      : booking
  )
  console.log(editId);
}

const filterBookFromCategory = async (filterData) => {
  bookings.value = allBooking.value
  bookings.value = descOrder()
  bookings.value = bookings.value.filter((booking) => {
    return booking.eventCategoryId == filterData
  })
  console.log("fildata" + filterData);
}

const filterReset = ()=>{
  bookings.value = allBooking.value
}

const filterPastEvent = ()=>{
  bookings.value = allBooking.value
   bookings.value = descOrder()
  bookings.value = bookings.value.filter((booking)=>{
   return (moment(booking.eventStartTime).local().format("YYYY-MM-DD") < dates)
  })
  // for(let booking of bookings.value){
  //   console.log(moment.utc(booking.eventStartTime).format("DD"));
  // }
}

const filterUpComingEvent = ()=>{
  bookings.value = allBooking.value
  bookings.value = ascOrder()
  bookings.value = bookings.value.filter((booking)=>{
    return (moment(booking.eventStartTime).local().format("YYYY-MM-DD") > dates)
  })
  // ascOrder()
  // return bookings.value
}

const filterByDateTime = (time)=>{
  bookings.value = allBooking.value
  bookings.value =  ascOrder();
  bookings.value = bookings.value.filter((booking)=>{
   return (moment(booking.eventStartTime).local().format("YYYY-MM-DD") == time)
  })
}

const ascOrder = ()=>
  bookings.value.sort(
      (a, b) => new Date(a.eventStartTime) - new Date(b.eventStartTime)
    )

const descOrder = ()=>
  bookings.value.sort(
      (a, b) => new Date(b.eventStartTime) - new Date(a.eventStartTime)
    )








</script>
<template>
  <div>
    <NavbarTop />
    <div class="text-center text-xl mt-24 mb-10">
      <h1 class="uppercase font-bold text-[#ffffff] text-4xl">Booking Lists</h1>
    </div>

    <!-- <div v-if="bookings == ''" class="flex flex-warp justify-center bg-gray-800 text-white text-xl">
      No scheduled Events
    </div> -->
    <FilterBar :categorys="categoryLists" @getChageCategory="filterBookFromCategory" @getReset="filterReset" @getPastEvent="filterPastEvent"
     @getUpComingEvent="filterUpComingEvent" @getDateTime="filterByDateTime"/>

    <LoopBookingList :bookings="bookings" @idDialogDetails="filterList" @EditIdFromDialog="filterEditBooking" />
    <NavbarBottom />
  </div>
</template>
<style>
</style>