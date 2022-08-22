<script setup></script>

<template>
  <!-- <div class="overflow-hidden fixed top-0 w-full"> -->
  <div class="fixed top-0 w-full">
    <nav class="px-2 py-2.5 bg-[#E5E5E5]">
      <!-- <div class="container flex flex-wrap justify-between items-center mx-auto"> -->
      <div class="flex items-center justify-between mx-auto">
        <div class="flex justify-start">
          <router-link to="/" class="flex">
            <img
              src="../../public/images/logo.png"
              class="mr-3 h-12"
              alt="TimeUp-logo"
            />
            <span
              class="self-center text-xl font-semibold whitespace-nowrap text-black uppercase"
              >Time-Up</span
            >
          </router-link>
        </div>

        <div class="md:block md:w-auto">
          <ul class="flex flex-row md:space-x-8 text-sm font-medium">
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
              <router-link
                to="/"
                class="block py-2 pr-4 pl-3 hover:bg-[#50ABCB] hover:rounded-lg hover:text-white hover:underline-offset-2 hover:underline uppercase menu"
              >
                Home</router-link
              >
            </li>
            <li>
              <router-link
                to="/aboutus"
                class="block py-2 pr-4 pl-3 hover:bg-[#50ABCB] hover:rounded-lg hover:text-white hover:text-white hover:underline-offset-2 hover:underline uppercase menu"
              >
                About</router-link
              >
            </li>
            <li>
              <router-link
                to="/categoryList"
                class="block py-2 pr-4 pl-3 hover:bg-[#50ABCB] hover:rounded-lg hover:text-white hover:text-white hover:underline-offset-2 hover:underline uppercase menu"
              >
                Category-List</router-link
              >
            </li>
            <li>
              <router-link
                to="/Reserve"
                class="block py-2 pr-4 pl-3 hover:bg-[#50ABCB] hover:rounded-lg hover:text-white hover:text-white hover:underline-offset-2 hover:underline uppercase menu"
              >
                New booking</router-link
              >
            </li>
            <li>
              <router-link
                to="/bookingList"
                class="block py-2 pr-4 pl-3 hover:bg-[#50ABCB] hover:rounded-lg hover:text-white hover:text-white hover:underline-offset-2 hover:underline uppercase menu"
              >
                Booking-List</router-link
              >
            </li>
            <li class="dropdown">
              <div
                class="Users flex py-2 pr-4 pl-3 hover:bg-[#50ABCB] hover:rounded-lg hover:text-white hover:text-white hover:underline-offset-2 hover:underline uppercase menu"
              >
                <p class="px-[20px]">User</p>
                <svg
                  class="dropdownButton"
                  width="20px"
                  height="20px"
                  viewBox="0 0 24 24"
                >
                  <path fill="#000000" d="m7 10l5 5l5-5z"></path>
                </svg>
              </div>
              <div class="dropdown-content uppercase">
                <!-- <a href="#">Link 1</a>
                <a href="#">Link 2</a>
                <a href="#">Link 3</a> -->
                <router-link
                  class="hover:bg-[#50ABCB] hover:rounded-sm hover:text-white hover:text-white hover:underline-offset-2 hover:underline uppercase"
                  :to="{ name: 'SignIn' }"
                  >Sign-In</router-link
                >
                <router-link
                  class="hover:bg-[#50ABCB] hover:rounded-sm hover:text-white hover:text-white hover:underline-offset-2 hover:underline uppercase"
                  :to="{ name: 'UserList' }"
                  >Userlist</router-link
                >
              </div>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </div>
</template>

<style>
.router-link-active.menu {
  color: rgb(255, 255, 255);
  text-decoration: underline;
  background-color: #6b6b6b;
  border-radius: 8.5px;
}

/* svg:hover {
  fill: #ffffff;
} */

.dropdown-content {
  display: none;
  position: absolute;
  /* right: 0; */
  background-color: #f9f9f9;
  min-width: 122px;
  max-width: 122px;
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
