<script setup>
import { ref, onBeforeMount, onMounted, computed } from 'vue'
import NavbarTop from '../../components/NavbarTop.vue';
import NavbarBottom from '../../components/NavbarBottom.vue';
import UserLists from '../../components/Users/LoopUserLists.vue';
import {getAllUsers} from '../../stores/book.js';

const userListAlls = ref([]);

onBeforeMount(async () => {
  const getUsers = await getAllUsers();
 userListAlls.value = await getUsers.json();
  // const getAllBooks = await getBookings();
  // bookingLists.value = await getAllBooks.json();
  // bookingLists.value = descOrder();
  // const getAllCategory = await getEventCategory();
  // categoryLists.value = await getAllCategory.json();

  // bookingLists.value.map((booking) => {
  //   booking.statusShow = ref(false)
  // })
})

</script>

<template>
  <div>
    <NavbarTop />
    <!-- <div class="text-white">{{userListAlls}}</div> -->
      <UserLists :userLists="userListAlls"/>
    <NavbarBottom />
  </div>
</template>

<style></style>
