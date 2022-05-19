<script setup>
import NavbarTop from "../components/navbarTop.vue";
import NavbarBottom from "../components/navbarBottom.vue";
import {ref , onBeforeMount} from 'vue';
import moment from "moment"
let arrLists = ref()
const getEventCategory = async () =>{
 const res = await fetch(`${import.meta.env.VITE_BASE_URL}/admin/event-category`);
  if(res.status === 200 ){
    arrLists.value = await res.json()
  }
  else{
    alert("find not found !!")
  }
}
onBeforeMount(async ()=>{
  await getEventCategory()
  // var testDateUtc = moment.utc("2015-01-30 10:00:00");
  // var localDate = moment(testDateUtc).local('TH');
  // console.log(testDateUtc);
  // console.log(localDate);
var oldDateObj = moment.utc("2015-01-30 10:00:00");
var newDateObj = moment(oldDateObj).add(50, 'm').toDate();
console.log(oldDateObj);
console.log(newDateObj);
})
</script>
 
<template>
    <div>
      <NavbarTop/>
      <NavbarBottom/>
        <div class = "text-left text-[52px] text-white fixed top-1/3 left-1/4 ">
          T I M E - U P<br>
          <p class="text-[26px]">At your fingertips, <br>
          consulting is simple and convenient.</p>
         <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px; padding-top: 25px;">
            <button class="bg-white border-3 text-black rounded-full font-bold p-2 hover: transform transition ease-in-out delay-150 hover:-translate-y-3 text-[16px]">
            <a href="./Reserve">
             <p> New Booking</p>
            </a>
          </button>
           <button class="bg-white border-3 text-black rounded-full font-bold p-2 hover: transform transition ease-in-out delay-150 hover:-translate-y-3 text-[16px]" >
            <a href="./BookingList">
             <p> Booking List</p>
            </a>
          </button>
         </div>
        </div>
        <div class="animate-bounce w-700 h-20 md:shrink-0 object-cove  fixed right-20 top-1/4 w-1/4 ">
          <img src="../../public/images/icon.png">
        </div>
    </div>
</template>
 
<style scoped>
  
</style>