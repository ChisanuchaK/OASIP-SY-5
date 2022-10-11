<script setup>
import { storeToRefs } from 'pinia';
import { ref, computed, onBeforeMount, onBeforeUpdate, reactive } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import Confirm from './Confirm.vue';
import { userStore } from '../stores/user.js';

const userSignInRes = userStore();
// const signInStatus = computed(()=> userSignInRes.signInStatus);
// const {signInStatus} = storeToRefs(userSignInRes);
// console.log(signInStatus.value);

// const signInUser = userSignInRes.signInUserData;
// console.log(signInUser);

const userSignInEmail = computed(() => userSignInRes.signInUserData.userEmail);
const userSignInName = computed(() => userSignInRes.signInUserData.userName);
const userSignInRole = computed(() => userSignInRes.signInUserData.userRole);

const route = useRoute();
const appRouter = useRouter();

const dialogSignOutStatus = ref(false);

const changeSignOutStatusTrue = () => dialogSignOutStatus.value = true;

const changeSignOutStatusFalse = () => dialogSignOutStatus.value = false;

const signOutOfUser = async () => {
  const res = userSignInRes.signOutUser();
  // console.log(res.status);
  // if (res.status === 200) {
    dialogSignOutStatus.value = false;
    alert("SignOut Success.");
    if (!(route.path == '/')) {
      appRouter.push({ name: 'Home' });
    } else {
      return;
    }
  // }
}

onBeforeMount(async () => {
})

</script>

<template>
  <!-- <div class="overflow-hidden fixed top-0 w-full"> -->
  <div class="relative z-10">
    <Confirm v-if="dialogSignOutStatus" @onClickConfirmNo="changeSignOutStatusFalse"
      @onClickConfirmYes="signOutOfUser" />
    <div class="fixed top-0 w-full">
      <nav class="px-2 py-2.5 bg-[#E5E5E5]">
        <!-- <div class="container flex flex-wrap justify-between items-center mx-auto"> -->
        <div class="flex items-center justify-between mx-auto">
          <div class="flex justify-start">
            <router-link to="/" class="flex">
              <img src="../../public/images/logo.png" class="mr-3 h-12" alt="TimeUp-logo" />
              <span class="self-center text-xl font-semibold whitespace-nowrap text-black uppercase">Time-Up</span>
            </router-link>
          </div>

          <div class="md:block md:w-auto">
            <ul class="flex flex-row md:space-x-4 text-sm font-medium justify-end">
              <!-- <li class="">
              <router-link
                :to="{name: 'EditUser'}"
                class="block py-2 pr-4 pl-3 hover:bg-[#50ABCB] hover:rounded-lg hover:text-white hover:underline-offset-2 hover:underline uppercase menu"
              >
                EditUser</router-link
              >
            </li>
            <li class="">
              <router-link
                :to="{name: 'DetailUser'}"
                class="block py-2 pr-4 pl-3 hover:bg-[#50ABCB] hover:rounded-lg hover:text-white hover:underline-offset-2 hover:underline uppercase menu"
              >
                DetailUser</router-link
              >
            </li> -->
              <li class="">
                <router-link :to="{ name: 'Home' }"
                  class="block py-2 pr-4 pl-3 hover:bg-[#50ABCB] hover:rounded-lg hover:text-white hover:underline-offset-2 hover:underline uppercase menu">
                  Home</router-link>
              </li>
              <li>
                <router-link :to="{ name: 'About' }"
                  class="block py-2 pr-4 pl-3 hover:bg-[#50ABCB] hover:rounded-lg hover:text-white hover:text-white hover:underline-offset-2 hover:underline uppercase menu">
                  About</router-link>
              </li>
              <li
                v-if="userSignInRes.signInUserData.userRole != 'student' && userSignInRes.signInUserData.userRole != 'guest'">
                <router-link :to="{ name: 'CategoryLists' }"
                  class="block py-2 pr-4 pl-3 hover:bg-[#50ABCB] hover:rounded-lg hover:text-white hover:text-white hover:underline-offset-2 hover:underline uppercase menu">
                  Category-List</router-link>
              </li>
              <li v-if="userSignInRes.signInUserData.userRole != 'lecturer'">
                <router-link :to="{ name: 'CreateBooking' }"
                  class="block py-2 pr-4 pl-3 hover:bg-[#50ABCB] hover:rounded-lg hover:text-white hover:text-white hover:underline-offset-2 hover:underline uppercase menu">
                  New booking</router-link>
              </li>
              <li>
                <router-link :to="{ name: 'BookingLists' }"
                  class="block py-2 pr-4 pl-3 hover:bg-[#50ABCB] hover:rounded-lg hover:text-white hover:text-white hover:underline-offset-2 hover:underline uppercase menu">
                  Booking-List</router-link>
              </li>
              <li class="dropdown" v-if="userSignInRes.signInUserData.userRole != 'student' && userSignInRes.signInUserData.userRole != 'lecturer'">
                <router-link :to="{ name: 'UserLists' }"
                  class="block py-2 pr-4 pl-3 hover:bg-[#50ABCB] hover:rounded-lg hover:text-white hover:text-white hover:underline-offset-2 hover:underline uppercase menu">
                  User-List</router-link>
              </li>
              <li v-if="userSignInRes.signInStatus" class="dropdown min-w-[160px] max-w-[160px]">
                <!-- v-if="localStorage.getItem('token') != undefined || localStorage.getItem('token') != ''" -->
                <!-- <button
                class="block py-2 pr-4 pl-3 hover:bg-[#50ABCB] hover:rounded-lg hover:text-white hover:text-white hover:underline-offset-2 hover:underline uppercase menu font-medium">
                Sign-Out</button> -->

                <div
                  class="Users flex py-2 pr-4 pl-3 hover:bg-[#50ABCB] hover:rounded-lg hover:text-white hover:text-white hover:underline-offset-2 hover:underline uppercase menu justify-end">
                  <p class="px-[20px] truncate">{{userSignInRes.signInUserData.userName}}</p>
                  <svg class="dropdownButton" width="20px" height="20px" viewBox="0 0 24 24">
                    <path fill="#000000" d="m7 10l5 5l5-5z"></path>
                  </svg>
                </div>
                <div class="dropdown-content uppercase">
                  <div class="lowercase">
                    <p class="py-1">{{userSignInEmail}}</p>
                    <p class="py-1">{{userSignInName}}</p>
                    <p class="py-1">{{userSignInRole}}</p>
                  </div>

                  <router-link v-if="userSignInRes.signInUserData.userRole == 'admin'"
                    class="hover:bg-[#50ABCB] hover:rounded-sm hover:text-white hover:text-white hover:underline-offset-2 hover:underline uppercase"
                    :to="{ name: 'CreateUser' }">Create-User</router-link>
                  <!-- <router-link
                  class="hover:bg-[#50ABCB] hover:rounded-sm hover:text-white hover:text-white hover:underline-offset-2 hover:underline uppercase"
                  :to="{ name: 'UserList' }"
                  >LogOut</router-link
                > -->
                  <div class="block py-2 pr-4 pl-3 hover:bg-[#50ABCB] hover:text-white hover:text-white">
                    <button @click="changeSignOutStatusTrue"
                      class="uppercase menu font-medium  hover:underline-offset-2 hover:underline">
                      Sign-Out</button>
                  </div>
                </div>
              </li>

              <li v-if="!(userSignInRes.signInStatus)" class="dropdown">
                <router-link :to="{ name: 'SignIn' }"
                  class="block py-2 pr-4 pl-3 hover:bg-[#50ABCB] hover:rounded-lg hover:text-white hover:text-white hover:underline-offset-2 hover:underline uppercase menu">
                  Sign-In</router-link>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    </div>
  </div>
</template>

<style>
.router-link-active.menu {
  color: rgb(255, 255, 255);
  text-decoration: underline;
  background-color: #50ABCB;
  border-radius: 8.5px;
}

/* svg:hover {
  fill: #ffffff;
} */

.dropdown-content {
  text-align: center;
  display: none;
  position: absolute;
  /* right: 0; */
  background-color: #f9f9f9;
  min-width: 158px;
  max-width: 158px;
  border-radius: 2%;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 1;
}

.dropdown-content a,
.router-link {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.dropdown:hover .dropdown-content {
  display: block;
}

/* .dropdown-content a:hover {
  background-color: #6b6b6b;
  border : rou
  
  hover:bg-[#6b6b6b] hover:rounded-lg hover:text-white hover:text-white hover:underline-offset-2 hover:underline uppercase
} */

/* #Users-active.menu {
  color: rgb(255, 255, 255);
  text-decoration: underline;
  background-color: #6b6b6b;
  border-radius: 8.5px;

} */
</style>
