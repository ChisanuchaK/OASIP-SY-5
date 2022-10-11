<script setup>
import { ref, onBeforeMount, onMounted, computed } from 'vue';
import PleaseLogInDialog from '../../components/PleaseLogInDialog.vue';
import UserList from '../../components/Users/UserList.vue'
import { userStore } from '../../stores/user.js'
import { useRouter } from 'vue-router';

const appRouter = useRouter();

const userSignInRes = userStore();

userSignInRes.getRefreshToken().then(()=>{
  if (userSignInRole.value == 'student' || userSignInRole.value == 'lecturer') {
    appRouter.go(-1);
  }
})

userSignInRes.getAllUsers();
const userLists = computed(() => userSignInRes.users);
// const statusPleseSignIn = ref(false);
const userSignInRole = computed(() => userSignInRes.signInUserData.userRole);
const pageName = ref('view USER-LIST');

const filterUserList = (idUserDelete) => {
  userLists.value = userLists.value.filter(
    (user) => user.idUser != idUserDelete
  );
};

onBeforeMount(async () => {
  // userSignInRes.getAllUsers();
  // statusPleseSignIn.value = false
  // const userlistAll = await getAllUsers();
  // userListAlls.value = await userlistAll.json();
  userLists.value.map((user) => {
    user.deleteDialogStatus = ref(false);
    user.cancelDialogStatus = ref(false);
    user.confirmDialogStatus = ref(false);
  })
});
</script>

<template>
  <div>
    <!-- <div class="text-white">{{userListAlls}}</div> -->
    <UserList :userLists="userLists"/>
    <!-- @idUserForDelete="filterUserList" -->
    <PleaseLogInDialog v-if="userSignInRole == 'guest'" :pageName="pageName" />
  </div>
</template>

<style>

</style>
