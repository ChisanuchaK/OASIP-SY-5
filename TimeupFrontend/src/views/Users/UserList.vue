<script setup>
import { ref, onBeforeMount, onMounted, computed } from 'vue'
import NavbarTop from '../../components/NavbarTop.vue';
import NavbarBottom from '../../components/NavbarBottom.vue';
import UserLists from '../../components/Users/LoopUserLists.vue';
import {getAllUsers} from '../../stores/book.js';

const userListAlls = ref([]);
// const userListAllsToUse = ref();

const filterUserList = (idUserDelete) => {
  userListAlls.value = userListAlls.value.filter((user) => (user.idUser != idUserDelete))
}

onBeforeMount(async () => {
  const getUsers = await getAllUsers();
 userListAlls.value = await getUsers.json();
  userListAlls.value.map((user) => {
    user.deleteDialogStatus = ref(false)
    user.cancelDialogStatus = ref(false)
    user.confirmDialogStatus = ref(false)
  })

})

</script>

<template>
  <div>
    <NavbarTop />
    <!-- <div class="text-white">{{userListAlls}}</div> -->
      <UserLists :userLists="userListAlls" @idUserForDelete="filterUserList"/>
    <NavbarBottom />
  </div>
</template>

<style></style>
