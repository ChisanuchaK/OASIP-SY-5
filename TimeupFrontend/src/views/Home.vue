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
    </div>

</template>
 
<style>

</style>