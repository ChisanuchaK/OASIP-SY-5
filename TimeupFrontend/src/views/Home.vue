<script setup>
import NavbarTop from "../components/navbarTop.vue";
import NavbarBottom from "../components/navbarBottom.vue";
import {ref , onBeforeMount} from 'vue';
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
})
</script>
 
<template>
    <div>
      <NavbarTop/>
      <NavbarBottom/>
        <div class = "text-left text-[52px] text-white fixed top-1/4 left-1/4 ">
          T I M E - U P<br><br>
          <p class="text-[30px]">At your fingertips, <br>
          consulting is simple and convenient.</p>
          <button class="bg-white border-4 text-black rounded-full font-bold p-2 hover: transform transition ease-in-out delay-150 hover:-translate-y-3 text-[22px]">
            <a href="./Reserve">
              reserve
            </a>
          </button>
        </div>
        <div class="animate-bounce w-700 h-20 md:shrink-0 object-cove  fixed right-20 top-1/4 w-1/4 ">
          <img src="../../public/images/icon.png">
        </div>
    </div>

</template>
 
<style>

</style>