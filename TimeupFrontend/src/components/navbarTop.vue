<script setup>
</script>
 
<template>
 <!-- <div>
 <nav>
     <div class="container flex flex-wrap justify-between items-center mx-auto">
         <ul class="flex">
  <li class="">
    <a class="text-blue-500 hover:text-blue-800" href="#">TimeUp</a>
  </li>
  <div>
  <li class="">
    <a class="text-blue-500 hover:text-blue-800" href="#">BookingList</a>
  </li>
  </div>
</ul>
     </div>
 </nav>
 </div> -->

<nav class="bg-darkgreen px-2  py-2.5 ">
<div class="container flex flex-wrap justify-between items-center mx-auto">
<router-link to="/" class="flex items-center">
<img src="/images/logo.png" class="mr-3 h-9" alt="TimeUp-Logo">
<span class="self-center text-xl font-semibold whitespace-nowrap text-white uppercase">Time-Up</span>
</router-link>

<div class="md:block md:w-auto" >
<ul class="flex flex-row md:space-x-8 text-sm font-medium">
<!-- <li>
<a href="#" class="block py-2 pr-4 pl-3 text-white bg-blue-700 rounded md:bg-transparent md:text-blue-700 " aria-current="page">Home</a>
</li>
<li>
<a href="#" class="block py-2 pr-4 pl-3 text-gray-700 hover:bg-gray-50 ">About</a>
</li>
<li>
<a href="#" class="block py-2 pr-4 pl-3 text-gray-700 hover:bg-gray-50 ">Services</a>
</li>
<li>
<a href="#" class="block py-2 pr-4 pl-3 text-gray-700 hover:bg-gray-50 uppercase ">Booking-List</a>
</li> -->
<li>
<router-link to="/bookingList" class="block py-2 pr-4 pl-3 text-white hover:bg-emerald-900 rounded uppercase ">Booking-List</router-link>
</li>
</ul>
</div>
</div>
</nav>

</template>
 
<style>
.bg-darkgreen{
    background-color: #243326;
}

</style>