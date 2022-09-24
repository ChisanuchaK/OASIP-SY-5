<script setup>
import { onBeforeMount, onBeforeUpdate } from 'vue';
import NavbarTop from '../components/NavbarTop.vue';
import NavbarBottom from '../components/NavbarBottom.vue';

onBeforeMount(async()=>{
})
</script>

<template>
  <div>
    <NavbarTop/>
    <NavbarBottom/>
    <div class="text-left text-[52px] text-black fixed top-1/3 left-1/4">
      T I M E - U P<br>
      <p class="text-[26px]">At your fingertips, <br>
        consulting is simple and convenient.</p>
      <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px; padding-top: 25px;">
        <button
          class="bg-[#105E99]  border-3 border-sky-500 text-white rounded-full font-bold p-2 hover: transform transition ease-in-out delay-10 hover:-translate-y-3 text-[16px] hover:bg-[#008EDE]">
          <a href="./Reserve">
            <p> New Booking</p>
          </a>
        </button>
        <button
          class="bg-[#105E99]  border-3 border-sky-500 text-white rounded-full font-bold p-2 hover: transform transition ease-in-out delay-10 hover:-translate-y-3 text-[16px] hover:bg-[#008EDE]">
          <a href="./BookingList">
            <p> Booking List</p>
          </a>
        </button>
      </div>
    </div>
    <div class="animate-bounce h-24 md:shrink-0 object-cove  fixed right-52 top-36 w-3/12">
      <img src="../../public/images/icon.png">
    </div>
  </div>
</template>
 
<style scoped>
</style>