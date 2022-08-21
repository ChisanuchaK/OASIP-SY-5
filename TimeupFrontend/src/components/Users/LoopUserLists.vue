<script setup>
import { ref, computed } from 'vue'
const emit = defineEmits([])
const props = defineProps({
  userLists: {
    type: Array,
    required: true
  }
})

const userAlls = computed(() => props.userLists)
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
          class="rounded-md bg-[#105E99] text-[#ffffff] w-[full] p-3 hover:bg-[#004980] transition delay-75"
        >
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Create your account.
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </button>

        <!-- <label v-if="false" class="absolute text-red-500 top-[70px]">
          something in form is invalid.
        </label> -->
      </div>
    </div>

    <div class="grid grid-flow-row grid-cols-1">
      <div
        class="grid grid-flow-row grid-cols-12 content-center justify-items-center p-1 bg-[#50ABCB] text-lg font-bold uppercase"
      >
        <div class="row-start-1 col-start-1 col-span-1">No.</div>
        <div class="row-start-1 col-start-2 col-end-4 col-span-2">Name</div>
        <div class="row-start-1 col-start-4 col-end-7 col-span-3">Email</div>
        <div class="row-start-1 col-start-8 col-span-1">Role</div>
        <div class="row-start-1 col-start-10 col-span-1">Info</div>
        <div class="row-start-1 col-start-11 col-span-1">edit</div>
        <div class="row-start-1 col-start-12 col-span-1">delete</div>
      </div>
      <!-- <div v-if=""></div> -->
      <div class="scroller w-full h-[300px] bg-[#D9D9D9]">
        <div
          class="grid grid-flow-row grid-cols-12 content-center justify-items-center p-1 my-[2px] bg-white text-[16px] text-center"
          v-for="(userList, index) in userAlls"
          :key="index"
        >
          <div class="grid ow-start-1 col-start-1 col-span-1">
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
          <div class="row-start-1 col-start-10 col-span-1">Info</div>
          <div class="row-start-1 col-start-11 col-span-1">edit</div>
          <div class="row-start-1 col-start-12 col-span-1">delete</div>
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
