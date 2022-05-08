<script setup>
</script>
 
<template>

<div class="overflow-hidden fixed top-0 w-full h-f">
<nav class="px-2  py-2.5 bg-[#E5E5E5] ">
<div class="container flex flex-wrap justify-between items-center mx-auto">
<router-link to="/" class="flex items-center">
<img src="/images/logo.png" class="mr-3 h-9" alt="TimeUp-Logo">
<span class="self-center text-xl font-semibold whitespace-nowrap text-black uppercase">Time-Up</span>
</router-link>

<div class="md:block md:w-auto" >
<ul class="flex flex-row md:space-x-8 text-sm font-medium">

<li class="">
<router-link to="/" class="block py-2 pr-4 pl-3 hover:bg-gray-300 uppercase hover:underline-offset-4 hover:underline ">Home</router-link>
</li>
<li>
<router-link to="/About" class="block py-2 pr-4 pl-3 hover:bg-gray-300 uppercase hover:underline-offset-4 hover:underline  ">About</router-link>
</li>
<li>
<router-link to="/Reserve" class="block py-2 pr-4 pl-3 hover:bg-gray-300 uppercase hover:underline-offset-4 hover:underline ">Reserve</router-link>
</li>
<li>
<router-link to="/bookingList" class="block py-2 pr-4 pl-3 hover:bg-gray-300 uppercase hover:underline-offset-4 hover:underline ">Booking-List</router-link>
</li>
</ul>
</div>
</div>
</nav>
</div>
</template>
 
<style>


</style>