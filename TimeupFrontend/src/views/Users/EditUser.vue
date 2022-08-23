<script setup>
import { ref, onBeforeMount, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import NavbarTop from '../../components/NavbarTop.vue'
import NavbarBottom from '../../components/NavbarBottom.vue'
import Confirm from '../../components/Confirm.vue'
import Cancel from '../../components/Cancel.vue'
import { getUser } from '../../stores/book.js'
import moment from 'moment'

const appRouter = useRouter()
let { params } = useRoute()

// const userToEdit = ref()
// const user = computed(() => {
//   return userToEdit.value
// })

const user = ref({})

const roleIndexSelect = ref()

// const userLength = computed(() => {
//   try {
//     return {
//       userEmail: '',
//       userName: ''
//     }
//   } catch (error) {
//     return {
//       userEmail: '',
//       userName: ''
//     }
//   }
// })

// const goBackToUserList = () => appRouter.push({ name: 'UserList' }); //use to test back to userlist.

onBeforeMount(async () => {
  const userByIdEdit = await getUser(params.idUser)
  user.value = userByIdEdit
  //   userToEdit.value = userByIdEdit
})

const changeCancelDialogShow = (user) => {
  user.cancelDialogStatus = true
}

const changeCancelDialogClose = (user) => {
  user.cancelDialogStatus = false
}

const cancelEditUser = (user) => {
  user.cancelDialogStatus = false
  appRouter.push({ name: 'UserList' })
}

const changeConfirmDialogShow = (user) => {
  user.confirmDialogStatus = true
}

const changeConfirmDialogClose = (user) => {
  user.confirmDialogStatus = false
}
</script>

<template>
  <div>
    <NavbarTop />
    <NavbarBottom />

    <div
      @click="changeCancelDialogShow(user)"
      class="absolute top-[15%] bg-white rounded left-[2%] p-1 hover:bg-[#E9E9E9]"
    >
      <svg width="50px" height="50px" viewBox="0 0 12 24">
        <path
          fill="#000000"
          d="M9.125 21.1L.7 12.7q-.15-.15-.212-.325Q.425 12.2.425 12t.063-.375Q.55 11.45.7 11.3l8.425-8.425q.35-.35.875-.35t.9.375q.375.375.375.875t-.375.875L3.55 12l7.35 7.35q.35.35.35.862q0 .513-.375.888t-.875.375q-.5 0-.875-.375Z"
        ></path>
      </svg>
    </div>

    <div class="bg-white w-[1000px] h-[600px] mt-[200px] m-auto rounded-xl">
      <div class="bg-[#50ABCB] w-full h-[100px] rounded-t-xl">
        <div
          class="grid grid-rows-1 grid-flow-col h-full content-center justify-items-center"
        >
          <div
            class="col-start-1 col-span-1 uppercase m-auto text-[30px] text-white font-semibold"
          >
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
      <div class="w-full h-[450px] rounded-b-xl">
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
            class="grid row-start-1 col-start-6 col-end-8 content-center text-center text-[18px]"
          >
            Name user
          </div>
          <div
            class="grid row-start-1 col-start-8 col-end-12 content-center text-center w-[90%]"
          >
            <form>
              <div class="relative">
                <input
                  v-model="user.nameUser"
                  class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75"
                  type="text"
                />
                <label class="placeholder text-[#D9D9D9]">name</label>
                <label
                  v-if="false"
                  class="absolute text-red-500 left-0 top-[40px]"
                >
                  *name is invalid.
                </label>
              </div>
            </form>
          </div>

          <div
            class="grid row-start-1 col-start-12 content-center text-left text-[#D9D9D9] w-[90%]"
          >
            {{ user.nameUser?.length || 0 }}/100
          </div>

          <div
            class="grid row-start-2 col-start-6 col-end-8 content-center text-center text-[18px]"
          >
            Email
          </div>
          <div
            class="grid row-start-2 col-start-8 col-end-12 content-center text-center w-[90%]"
          >
            <form>
              <div class="relative">
                <input
                  v-model="user.emailUser"
                  class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75"
                  type="text"
                />
                <label class="placeholder text-[#D9D9D9]">Email</label>
                <label
                  v-if="false"
                  class="absolute text-red-500 left-0 top-[40px]"
                >
                  *something in form is invalid.
                </label>
              </div>
            </form>
          </div>
          <div
            class="grid row-start-2 col-start-12 content-center text-left text-[#D9D9D9] w-[90%]"
          >
            {{ user.emailUser?.length || 0 }}/50
          </div>

          <div
            class="grid row-start-3 col-start-6 col-end-8 content-center text-center text-[18px]"
          >
            role
          </div>
          <div
            class="grid row-start-3 col-start-8 col-end-12 content-center w-[90%]"
          >
            <div class="relative">
              <!-- <select :v-model="(user.roleUser == '' ? roleIndexSelect : user.roleUser )" -->
              <select
                :v-model="user.roleUser"
                class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75 text-center"
                name=""
                id=""
              >
                <!-- <option value=""></option> -->
                <option value="Admin">Admin</option>
                <option value="Lecturer">Lecturer</option>
                <option value="Student">Student</option>
              </select>
              <label v-if="false" class="text-red-500">
                *name is invalid
              </label>
            </div>
          </div>
          <div
            class="grid row-start-4 col-start-6 col-end-8 content-center text-center text-[18px]"
          >
            createOn
          </div>
          <div
            class="grid row-start-4 col-start-8 col-end-12 content-center text-center w-[90%]"
          >
            <form>
              <div class="relative">
                <input
                  disabled
                  class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75"
                  type="text"
                  :placeholder="`${moment(user.createOn)
                    .local()
                    .format('DD/MM/YYYY')} | ${moment(user.createOn)
                    .local()
                    .format('hh:mm A')}`"
                />
              </div>
              <!-- {{ `${moment(user.createOn).local().format('DD/MM/YYYY')} |${moment(user.createOn).local().format('hh:mm A')}`}} -->
            </form>
          </div>

          <div
            class="grid row-start-5 col-start-6 col-end-8 content-center text-center text-[18px]"
          >
            updateOn
          </div>

          <div
            class="grid row-start-5 col-start-8 col-end-12 content-center text-center w-[90%]"
          >
            <form>
              <div class="relative">
                <input
                  disabled
                  class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75"
                  type="text"
                  :placeholder="`${moment(user.updateOn)
                    .local()
                    .format('DD/MM/YYYY')} | ${moment(user.updateOn)
                    .local()
                    .format('hh:mm A')}`"
                />
              </div>
            </form>
          </div>

          <!-- <div class="grid row-start-5 col-start-8 col-end-12 content-center text-center  w-[90%]">
                        <form>
                            <div class="relative">
                                <input
                                    class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75"
                                    type="text" />
                                <label class="placeholder text-[#D9D9D9]">Email</label>
                                <label v-if="false" class="absolute text-red-500 left-0 top-[40px]">
                                    *something in form is invalid.
                                </label>
                            </div>
                        </form>
                    </div> -->

          <div
            class="relative grid row-start-6 col-start-6 col-end-9 content-center text-center w-full"
          >
            <button
              @click="changeCancelDialogShow(user)"
              class="rounded-md bg-[#F24052] text-[#ffffff] w-[80%] m-auto p-2 hover:bg-[#D92739] transition delay-75"
            >
              Cancel
            </button>
            <!-- <label v-if="false" class="absolute text-red-500 top-[70px]">
                            *something in form is invalid.
                        </label> -->
          </div>

          <div
            class="relative grid row-start-6 col-start-9 col-end-12 content-center text-center w-full"
          >
            <button
              @click="changeConfirmDialogShow(user)"
              class="rounded-md bg-[#00A28B] text-[#ffffff] w-[80%] m-auto p-2 hover:bg-[#017D6C] transition delay-75"
            >
              Confirm
            </button>

            <label v-if="false" class="absolute text-red-500 top-[70px]">
              *something in form is invalid.
            </label>
          </div>

          <Cancel
            v-if="user.cancelDialogStatus"
            @onClickCancelNo="changeCancelDialogClose(user)"
            @onClickCancelYes="cancelEditUser(user)"
          />
          <Confirm
            v-if="user.confirmDialogStatus"
            @onClickConfirmNo="changeConfirmDialogClose(user)"
            @onClickConfirmYes="changeConfirmDialogClose(user)"
          />
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

.placeholder {
  position: absolute;
  top: -10px;
  background-color: #fff;
  left: 8px;
  font-size: 14px;
  padding: 0px 1px;
  color: #666;
  transition: 0.3s;
  pointer-events: none;
}

input:focus + .placeholder {
  font-size: 12px;
  color: #105e99;
}

input:focus {
  border-color: #105e99;
  outline: none !important;
}
</style>
