<script setup>
import { onBeforeMount } from 'vue';
import NavbarTop from './components/NavbarTop.vue';
import NavbarBottom from './components/NavbarBottom.vue';
import { userStore } from './stores/user.js'
const userSignInRes = userStore();
userSignInRes.getRefreshToken();

onBeforeMount(async () => {
})
</script>

<template>
  <!-- <div v-if="this.$route.name != 'CreateUser'"> -->
  <div>
    <NavbarTop/>
    <NavbarBottom />
  </div>
  <router-view />
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Libre+Bodoni&family=Newsreader:opsz@6..72&family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');

* {
  font-family: 'Poppins', sans-serif;
}

Body {
  background-color: white;
}
</style>
