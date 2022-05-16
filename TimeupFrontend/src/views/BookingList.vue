<script setup>
import { ref, onBeforeMount, onMounted } from 'vue'
import NavbarTop from "../components/navbarTop.vue";
import NavbarBottom from "../components/navbarBottom.vue";
import LoopBookingList from "../components/LoopBookingList.vue";
import { getBookings } from '../stores/book.js';

const bookings = ref([]);

// const eventStartTime = "2022-06-27 02:30";
// const dates = moment.utc(eventStartTime).format("DD MMMM YYYY");
// const startTimes = moment.utc(eventStartTime).format("h:mm");
// console.log(dates);
// console.log(startTimes);


onBeforeMount(async () => {
 bookings.value = await getBookings();
 bookings.value.map((s)=>{
 s.statusClickSeeDetails = ref(false)
//  s.statusClickDelete = ref(false)
 
//  s.statusClickEdit = ref(false)
//  s.statusCancelEdit = ref(false)
//  s.statusConfirmEdit = ref(false)

//  s.statusClickDelete = ref(false)
//  s.statusCancelEdit = ref(false)
//  s.statusConfirmEdit = ref(false)
})

  console.log(bookings.value);
  console.log(bookings.value[0]);
  // dates.value = await bookings.value.idBooking
  // console.log(dates.value);
})

const filterList = (idDeleteFromLoop)=>{
  bookings.value = bookings.value.filter((BookingInBookings) => (BookingInBookings.idBooking != idDeleteFromLoop))
}

const filterEdit = (editId)=>{
  // bookings.value = bookings.value.map((booking)=>{
  //   booking.idBooking === editId 
  //   ? {...booking, }
    
  // })
  console.log(editId);
  // notes.value = notes.value.map((note) =>
        //   note.id === editedNote.id
        //     ? { ...note, noteDetail: editedNote.noteDetail }
        //     : note
        // )
}

</script>
 
<template>

<div>
<NavbarTop/>
       <!-- <LoopBookingList :bookings="bookings" @deleteBooking="removeBooking"/> -->
       <!-- <LoopBookingList :bookings="bookings" /> -->
       <LoopBookingList :bookings="bookings.sort(
         (a,b)=> new Date(b.eventStartTime) - new Date(a.eventStartTime) 
       )" @idDialogDetails="filterList" @EditIdFromDialog="filterEdit"/>
<NavbarBottom/>
</div>

</template>
 
<style>

</style>