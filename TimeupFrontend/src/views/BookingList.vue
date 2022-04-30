<script setup>
import { ref, onBeforeMount } from 'vue'
import NavbarTop from "../components/navbarTop.vue";
import NavbarBottom from "../components/navbarBottom.vue";
import LoopBookingList from "../components/LoopBookingList.vue";
const bookings = ref([])


// Delete method
const removeBooking = async (deleteBookingId)=>{
  const res = await fetch(`http://localhost:3001/BookingLists/${deleteBookingId}`,{
    method: 'DELETE'
  })
  if(res.status === 200){
    bookings.value = bookings.value.filter((BookingInBookings)=> (BookingInBookings.id != deleteBookingId) )
    console.log('deleted success');
  }else{
    console.log('error , cannot delete');
  }
}

//Get
const getBookings = async () => {
  const res = await fetch(`${ import.meta.env.VITE_BASE_URL}/booking`)
  // const res = await fetch('http://10.4.84.23:8080/api/booking')
  if (res.status === 200) {
    bookings.value = await res.json()
    console.log(bookings.value)
    return bookings.value
  } else  console.log('error to getBookings');
}

onBeforeMount(async () => {
  await getBookings();
  console.log(bookings.value);
})

</script>
 
<template>

<div>
<NavbarTop/>
       <LoopBookingList :bookings="bookings" @deleteBooking="removeBooking"/>
<NavbarBottom/>
</div>

</template>
 
<style>

</style>