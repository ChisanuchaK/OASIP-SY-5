<script setup>
import { ref, onBeforeMount, onMounted, computed } from 'vue';
import NavbarBottom from '../../components/NavbarBottom.vue';
import NavbarTop from '../../components/NavbarTop.vue';
import PleaseLogInDialog from '../../components/PleaseLogInDialog.vue';
import UserLists from '../../components/Users/LoopUserLists.vue';
import { getAllUsers } from '../../stores/user.js';


const userListAlls = ref([]);
const statusGetAllUser = ref();
const responseGetAllUser = ref({});
// const statusPleseSignIn = ref(false);
const getToken = localStorage.getItem('token');
const pageName = ref('view USER-LIST');

const filterUserList = (idUserDelete) => {
  userListAlls.value = userListAlls.value.filter(
    (user) => user.idUser != idUserDelete
  );
};

onBeforeMount(async () => {
  responseGetAllUser.value = await getAllUsers();
  if(responseGetAllUser.value.status == 200){
  
  statusGetAllUser.value = responseGetAllUser.value.status;
  userListAlls.value = responseGetAllUser.value.data;
  // statusPleseSignIn.value = false
    // const userlistAll = await getAllUsers();
    // userListAlls.value = await userlistAll.json();
    userListAlls.value.map((user) => {
      user.deleteDialogStatus = ref(false);
      user.cancelDialogStatus = ref(false);
      user.confirmDialogStatus = ref(false);
    });
}else{
  // alert("please sign-n to view USER-LIST");
  // statusPleseSignIn.value = true
}
});
</script>

<template>
  <div>
    <NavbarTop />
    <!-- <div class="text-white">{{userListAlls}}</div> -->
    <UserLists :userLists="userListAlls" @idUserForDelete="filterUserList" />
    <PleaseLogInDialog v-if="!getToken"  :pageName="pageName"/>
    <NavbarBottom />
  </div>
</template>

<style>

</style>
