<script setup>
import NavbarTop from "../components/navbarTop.vue";
import NavbarBottom from "../components/navbarBottom.vue";
import CategoryListVue from "../components/CategoryList.vue";
import {ref , onBeforeMount} from 'vue';
let arrLists = ref()

const getEventCategory = async () =>{
 const res = await fetch(`${import.meta.env.VITE_BASE_URL}/sy5/admin/event-category`);
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
        <CategoryListVue :listEventCatgorys="arrLists"/>
    <NavbarBottom/>

    </div>

</template>
 
<style>

</style>