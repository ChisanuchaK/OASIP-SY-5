<script setup>
import infoVue from '../components/info.vue';
import NavbarTop from '../components/NavbarTop.vue';
import NavbarBottom from '../components/NavbarBottom.vue';
</script>
 
<template>
  <div>
    <NavbarTop/>
  <NavbarBottom/>
    <div class="-z-10 relative">
    <infoVue />
    </div>
  </div>
  
  
</template>
 
<style>
</style>