<script setup>
</script>
 
<template>

<div class="overflow-hidden fixed top-0 w-full">
<nav class="bg-darkgreen px-2  py-2.5 ">
<div class="container flex flex-wrap justify-between items-center mx-auto">
<router-link to="/" class="flex items-center">
<img src="/images/logo.png" class="mr-3 h-9" alt="TimeUp-Logo">
<span class="self-center text-xl font-semibold whitespace-nowrap text-white uppercase">Time-Up</span>
</router-link>

<div class="md:block md:w-auto" >
<ul class="flex flex-row md:space-x-8 text-sm font-medium">

<li>
<router-link to="/bookingList" class="block py-2 pr-4 pl-3 text-white hover:bg-emerald-900 rounded uppercase ">Booking-List</router-link>
</li>
</ul>
</div>
</div>
</nav>
</div>
</template>
 
<style scoped>
.bg-darkgreen{
    background-color: #243326;
}

</style>