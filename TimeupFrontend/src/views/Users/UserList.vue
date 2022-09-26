<script setup>
import { ref, onBeforeMount, onMounted, computed } from 'vue';
import PleaseLogInDialog from '../../components/PleaseLogInDialog.vue';
import UserLists from '../../components/Users/LoopUserLists.vue';
import { getAllUsers } from '../../stores/user.js';
import NavbarTop from '../../components/NavbarTop.vue';
import NavbarBottom from '../../components/NavbarBottom.vue';
import { useRouter } from 'vue-router';

const appRouter = useRouter();

const userListAlls = ref([]);
const statusGetAllUser = ref();
const responseGetAllUser = ref({});
// const statusPleseSignIn = ref(false);
const getAccessToken = localStorage.getItem('accessToken');
const getRefreshToken = localStorage.getItem('refreshToken');
const getUserRole = ref(localStorage.getItem('userRole'));

const pageName = ref('view USER-LIST');

const filterUserList = (idUserDelete) => {
  userListAlls.value = userListAlls.value.filter(
    (user) => user.idUser != idUserDelete
  );
};

onBeforeMount(async () => {
  if(getUserRole.value == 'student'){
    appRouter.go(-1);
  }
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
  appRouter.go(-1)
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
