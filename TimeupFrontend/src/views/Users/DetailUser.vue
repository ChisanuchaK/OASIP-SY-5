<script setup>
import { ref, onBeforeMount, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router' //get params to script
import NavbarTop from '../../components/NavbarTop.vue'
import NavbarBottom from '../../components/NavbarBottom.vue'
import { getUser } from '../../stores/book.js'
import moment from 'moment'

const appRouter = useRouter()
let { params } = useRoute()

const user = ref({})

const goBackToUserList = () => appRouter.push({ name: 'UserList' })

onBeforeMount(async () => {
  const userById = await getUser(params.idUser)
  user.value = userById
})
</script>

<template>
  <div>
    <NavbarTop />
    <NavbarBottom />

    <div
      @click="goBackToUserList()"
      class="absolute top-[15%] bg-white rounded left-[2%] p-1 hover:bg-[#E9E9E9]"
    >
      <svg width="50px" height="50px" viewBox="0 0 12 24">
        <path
          fill="#000000"
          d="M9.125 21.1L.7 12.7q-.15-.15-.212-.325Q.425 12.2.425 12t.063-.375Q.55 11.45.7 11.3l8.425-8.425q.35-.35.875-.35t.9.375q.375.375.375.875t-.375.875L3.55 12l7.35 7.35q.35.35.35.862q0 .513-.375.888t-.875.375q-.5 0-.875-.375Z"
        ></path>
      </svg>
    </div>

    <div class="bg-white w-[1000px] h-[500px] mt-[200px] m-auto rounded-xl">
      <div class="bg-[#50ABCB] w-full h-[100px] rounded-t-xl">
        <div
          class="grid grid-rows-1 grid-flow-col h-full content-center justify-items-center"
        >
          <div
            class="col-start-1 col-span-1 uppercase m-auto text-[30px] text-white font-semibold"
          >
            <!-- User Id {{this.$route.params.idUser}} -->
            User Id : {{ params.idUser }}
          </div>

          <!-- <span class="col-start-4 col-span-1 dot"></span>
                    <span class="col-start-5 col-span-1 dot"></span> -->
          <div class="col-start-6 col-span-1 w-[50%] m-auto">
            <div class="dot ml-[10px] mr-2"></div>
            <div class="dot mr-2"></div>
            <div class="dot mr-2"></div>
          </div>
        </div>
      </div>
      <div class="w-full h-[400px] rounded-b-xl">
        <div
          class="grid grid-rows-6 grid-cols-12 content-center justify-items-center"
        >
          <!-- <div class="grid grid-rows-6 grid-cols-12"> -->
          <div class="row-start-2 row-end-4 col-start-2 col-end-5">
            <img
              class="grid w-[150px] rounded-xl"
              src="../../../public/images/Spy.jpg"
              alt="spy"
            />
          </div>
          <div
            class="grid row-start-4 col-start-2 col-end-5 content-center text-center text-[18px]"
          >
            {{ user.nameUser }}
            <!-- {{user}} -->
          </div>
          <div
            class="grid row-start-5 col-start-2 col-end-5 text-center text-[30px] uppercase"
          >
            {{ user.roleUser }}
          </div>
          <div
            class="grid row-start-2 col-start-6 col-end-8 content-center text-center text-[18px]"
          >
            Email :
          </div>
          <div
            class="grid row-start-2 col-start-8 col-end-12 content-center text-center text-[18px]"
          >
            {{ user.emailUser }}
          </div>
          <div
            class="grid row-start-3 col-start-6 col-end-8 content-center text-center text-[18px]"
          >
            createOn :
          </div>
          <div
            class="grid row-start-3 col-start-8 col-end-12 content-center text-center text-[18px]"
          >
            {{ moment(user.createOn).local().format('DD/MM/YYYY') }} |
            {{ moment(user.createOn).local().format('hh:mm A') }}
          </div>
          <div
            class="grid row-start-4 col-start-6 col-end-8 content-center text-center text-[18px]"
          >
            updateOn :
          </div>
          <div
            class="grid row-start-4 col-start-8 col-end-12 content-center text-center text-[18px]"
          >
            {{ moment(user.createOn).local().format('DD/MM/YYYY') }} |
            {{ moment(user.updateOn).local().format('hh:mm A') }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.dot {
  height: 25px;
  width: 25px;
  background-color: #bbb;
  border-radius: 50%;
  display: inline-block;
}
</style>
