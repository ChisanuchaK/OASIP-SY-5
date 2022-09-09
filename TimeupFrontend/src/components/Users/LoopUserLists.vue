<script setup>
import { ref, computed } from 'vue';
import DeleteUserDialog from './DeleteUserDialog.vue';
import { deletedUser } from '../../stores/user.js';
const emit = defineEmits(['idUserForDelete']);
const props = defineProps({
  userLists: {
    type: Array,
    required: true
  },
  statusUser: {
    type: String,
    default: 'Not Have a User'
  }
});

const userAlls = computed(() => props.userLists);

const changeDeleteDialogShow = (user) => {
  user.deleteDialogStatus = true;
};

const changeDeleteDialogClose = (user) => {
  user.deleteDialogStatus = false;
};

const deleteUserEvent = async (idUserToDelete, user) => {
  alert('Delete User Success!!');
  user.deleteDialogStatus = false;
  emit('idUserForDelete', idUserToDelete);
  await deletedUser(idUserToDelete);
};
</script>

<template>
  <!-- <div class=" p-[200px] bg-white mt-20 text-red-100">
        <div v-for="(userList,index) in userAlls" :key="index">
        {{userList}}
        </div>
    </div> -->
  <div class="bg-white rounded-xl mt-[120px] m-auto w-[1200px] h-[850px]">
    <div
      class="grid grid-flow-row grid-flow-col bg-white rounded-xl border m-auto w-full"
    >
      <div class="grid row-start-1 col-span-4 h-[100px]"></div>

      <div class="grid row-start-2 col-span-3 h-[60px] w-full">
        <!-- <div class="relative w-[90%] mx-auto">
          <input
            class="w-full border rounded-md border-solid border-[#D9D9D9] w-full hover:bg-[#F2F2F2] transition delay-75"
            type="text"
          />
        </div> -->
        <div class="relative w-[90%] m-auto">
          <form>
            <input
              class="m-auto border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75 text-center"
              type="text"
            />
            <svg
              class="placeholder"
              width="2em"
              height="2em"
              viewBox="0 0 28 34"
            >
              <path
                fill="currentColor"
                d="m18.031 16.617l4.283 4.282l-1.415 1.415l-4.282-4.283A8.96 8.96 0 0 1 11 20c-4.968 0-9-4.032-9-9s4.032-9 9-9s9 4.032 9 9a8.96 8.96 0 0 1-1.969 5.617zm-2.006-.742A6.977 6.977 0 0 0 18 11c0-3.868-3.133-7-7-7c-3.868 0-7 3.132-7 7c0 3.867 3.132 7 7 7a6.977 6.977 0 0 0 4.875-1.975l.15-.15z"
              ></path>
            </svg>
          </form>
        </div>
      </div>

      <div
        class="grid row-start-2 col-start-4 justify-items-center content-center"
      >
        <button
          class="rounded-md bg-[#105E99] text-[#ffffff] w-[full] p-3 hover:bg-[#50ABCB] transition delay-75"
        >
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Find your account.
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </button>

        <!-- <label v-if="false" class="absolute text-red-500 top-[70px]">
          something in form is invalid.
        </label> -->
      </div>
    </div>

    <div class="grid grid-flow-row grid-cols-1">
      <div
        class="grid grid-flow-row grid-cols-12 content-center justify-items-center p-1 bg-[#50ABCB] text-lg text-white font-bold uppercase"
      >
        <div class="row-start-1 col-start-1 col-span-1">No.</div>
        <div class="row-start-1 col-start-2 col-end-4 col-span-2">Name</div>
        <div class="row-start-1 col-start-4 col-end-7 col-span-3">Email</div>
        <div class="row-start-1 col-start-8 col-span-1">Role</div>
        <div class="row-start-1 col-start-10 col-span-1">Info</div>
        <div class="row-start-1 col-start-11 col-span-1">edit</div>
        <div class="row-start-1 col-start-12 col-span-1">delete</div>
      </div>
      <!-- <div v-if="!(userAlls == '')">
      {{props.statusUser}}
      </div> -->
      <div
        v-if="userAlls == ''"
        class="grid grid-flow-row grid-cols-1 w-full h-[300px] bg-[#E9E9E9] content-center justify-items-center"
      >
        <div class="col-span-1 text-[128px] uppercase text-[#50ABCB]">
          {{ props.statusUser }}
        </div>
      </div>

      <div v-else class="scroller w-full h-[300px] bg-[#E9E9E9]">
        <div
          class="grid grid-flow-row grid-cols-12 content-center justify-items-center p-1 my-[2px] bg-white text-[16px] text-center hover:bg-[#F2F2F2]"
          v-for="(userList, index) in userAlls"
          :key="index"
        >
          <div class="grid row-start-1 col-start-1 col-span-1">
            {{ userList.idUser }}
          </div>
          <div class="row-start-1 col-start-2 col-end-4 col-span-2 w-[80%]">
            <p class="truncate">
              {{ userList.nameUser }}
            </p>
          </div>
          <div class="row-start-1 col-start-4 col-end-7 col-span-3 w-[90%]">
            <p class="truncate">
              {{ userList.emailUser }}
            </p>
          </div>
          <div class="row-start-1 col-start-8 col-span-1">
            {{ userList.roleUser }}
          </div>
          <div class="row-start-1 col-start-10 col-span-1">
            <!-- <router-link :to="{name: 'DetailUser' ,params:{idUser:userList.idUser}}"> -->
            <router-link
              :to="{ name: 'DetailUser', params: { idUser: userList.idUser } }"
            >
              <button class="hover:bg-[#D9D9D9] rounded-sm">
                <svg
                  class="hover:text-[#008EDE]"
                  width="2em"
                  height="2em"
                  viewBox="0 0 24 24"
                >
                  <path
                    fill="currentColor"
                    d="M15 11h7v2h-7zm1 4h6v2h-6zm-2-8h8v2h-8zM4 19h10v-1c0-2.757-2.243-5-5-5H7c-2.757 0-5 2.243-5 5v1h2zm4-7c1.995 0 3.5-1.505 3.5-3.5S9.995 5 8 5S4.5 6.505 4.5 8.5S6.005 12 8 12z"
                  ></path>
                </svg>
              </button>
            </router-link>
          </div>

          <div class="row-start-1 col-start-11 col-span-1">
            <router-link
              :to="{ name: 'EditUser', params: { idUser: userList.idUser } }"
            >
              <button class="hover:bg-[#D9D9D9] rounded-sm">
                <svg
                  class="hover:text-[#007850]"
                  width="2em"
                  height="2em"
                  viewBox="0 0 24 24"
                >
                  <path
                    fill="currentColor"
                    d="M3 17.46v3.04c0 .28.22.5.5.5h3.04c.13 0 .26-.05.35-.15L17.81 9.94l-3.75-3.75L3.15 17.1c-.1.1-.15.22-.15.36zM20.71 5.63l-2.34-2.34a.996.996 0 0 0-1.41 0l-1.83 1.83l3.75 3.75l1.83-1.83a.996.996 0 0 0 0-1.41z"
                  ></path>
                </svg>
              </button>
            </router-link>
          </div>

          <div class="row-start-1 col-start-12 col-span-1">
            <button
              @click="changeDeleteDialogShow(userList)"
              class="hover:bg-[#D9D9D9] rounded-sm"
            >
              <svg
                class="hover:text-[#F24052]"
                width="2em"
                height="2em"
                viewBox="0 0 24 24"
              >
                <path
                  fill="currentColor"
                  d="M6 19a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V7H6v12m2.46-7.12l1.41-1.41L12 12.59l2.12-2.12l1.41 1.41L13.41 14l2.12 2.12l-1.41 1.41L12 15.41l-2.12 2.12l-1.41-1.41L10.59 14l-2.13-2.12M15.5 4l-1-1h-5l-1 1H5v2h14V4h-3.5Z"
                ></path>
              </svg>
            </button>
          </div>
          <DeleteUserDialog
            v-if="userList.deleteDialogStatus"
            :propsUserToDelete="userList"
            @cancelDeleteUser="changeDeleteDialogClose(userList)"
            @confirmDeleteUser="deleteUserEvent"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.input {
  padding: 10px;
  width: 100%;
  max-width: 300px;
  box-sizing: border-box;
  outline: none;
  border: 1px solid #04aa6d;
}

.placeholder {
  position: absolute;
  top: 10px;
  left: 8px;
  font-size: 14px;
  padding: 0px 5px;
  color: #666;
  transition: 0.3s;
  pointer-events: none;
}

input:focus + .placeholder {
  font-size: 16px;
  color: #105e99;
}

input:focus {
  border-color: #105e99;
  outline: none !important;
}

.scroller {
  overflow-y: scroll;
  scrollbar-color: gray;
  scrollbar-track-color: gray;
  scrollbar-width: thin;
}
</style>
