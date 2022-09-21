<script setup>
import { ref, onBeforeMount, onMounted, computed } from 'vue';
import PleaseLogInDialog from '../../components/PleaseLogInDialog.vue';
import UserLists from '../../components/Users/LoopUserLists.vue';
import { getAllUsers } from '../../stores/user.js';
import NavbarTop from '../../components/NavbarTop.vue';
import NavbarBottom from '../../components/NavbarBottom.vue';


const userListAlls = ref([]);
const statusGetAllUser = ref();
const responseGetAllUser = ref({});
// const statusPleseSignIn = ref(false);
const getAccessToken = localStorage.getItem('accessToken');
const getRefreshToken = localStorage.getItem('refreshToken');

const pageName = ref('view USER-LIST');

const filterUserList = (idUserDelete) => {
  userListAlls.value = userListAlls.value.filter(
    (user) => user.idUser != idUserDelete
  );
};

onBeforeMount(async () => {
  responseGetAllUser.value = await getAllUsers();
  // console.log(responseGetAllUser.value.data);
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
}
else{
  // alert("please sign-n to view USER-LIST");
  // statusPleseSignIn.value = true
}
});
</script>

<template>
  <div>
    <NavbarTop/>
    <NavbarBottom/>
    <!-- <div class="text-white">{{userListAlls}}</div> -->
    <UserLists :userLists="userListAlls" @idUserForDelete="filterUserList" />
    <PleaseLogInDialog v-if="!getAccessToken && !getRefreshToken"  :pageName="pageName"/>
  </div>
</template>

<style>

</style>
