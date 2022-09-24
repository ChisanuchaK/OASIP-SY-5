<script setup>
import { ref, onBeforeMount, onMounted, computed, reactive } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import Confirm from '../../components/Confirm.vue';
import Cancel from '../../components/Cancel.vue';
import { getUser, getAllUsers, editUser } from '../../stores/user.js';
import moment from 'moment';
import NavbarTop from '../../components/NavbarTop.vue';
import NavbarBottom from '../../components/NavbarBottom.vue';

const appRouter = useRouter();
let { params } = useRoute();

// const getUserToEdit = ref({});
// const user = computed(() => { return getUserToEdit.value });
const user = ref([]);
const responseGetAllUser = ref({});
const responseGetUser = ref({});

const roleIndexSelect = ref();

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

const localDataUser = reactive({
  idUser: params.idUser,
  nameUser: '',
  emailUser: '',
  roleUser: ''
});

// const roles = ref(['admin', 'lecturer', 'student']);

const userListAlls = ref([]);
// const UserName = ref([]);
// const roleLists = ref();

const isInvalid = ref(false);
const nameIsDuplicate = ref(false);
const emailIsDuplicate = ref(false);

const cancelDialogStatus = ref(false);
const confirmDialogStatus = ref(false);
const regexEmail = '[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}';
// const roleIndexSelect = ref();
// const localPresentTime = moment.utc().local().format("YYYY-MM-DDTHH:mm");

//status pop-up
const changeCancelDialogShow = (user) => {
  user.cancelDialogStatus = true;
};

const changeCancelDialogClose = (user) => {
  user.cancelDialogStatus = false;
};

const cancelEditUser = (user) => {
  user.cancelDialogStatus = false;
  appRouter.push({ name: 'UserList' });
};

const changeConfirmDialogClose = (user) => {
  user.confirmDialogStatus = false;
};

const changeConfirmDialogShow = (user) => {
  isInvalid.value = false;

  if (
    userListAlls.value.some((someUser) => {
      if (params.idUser != someUser.idUser) {
        if (
          localDataUser.nameUser.trim() == '' ||
          localDataUser.nameUser.length > 100 ||
          localDataUser.emailUser.trim() == '' ||
          localDataUser.emailUser.trim() == null ||
          !localDataUser.emailUser.match(regexEmail) ||
          localDataUser.emailUser.length > 50 ||
          localDataUser.nameUser === someUser.nameUser ||
          localDataUser.emailUser === someUser.emailUser
        ) {
          // isInvalid.value = true
          return true;
        } else {
          // isInvalid.value = false
          return false;
        }
      }
    })
  ) {
    isInvalid.value = true;
    console.log('Name and email is valid.');
  } else {
    isInvalid.value = false;
  }
  if (
    nameIsDuplicate.value == false &&
    emailIsDuplicate.value == false &&
    isInvalid.value == false
  ) {
    user.confirmDialogStatus = true;
    isInvalid.value = false;
  }
  // }
};

const editUserSuccess = async (dataOfUser) => {
  dataOfUser.nameUser = dataOfUser.nameUser.trim();
  dataOfUser.emailUser = dataOfUser.emailUser.trim();
  await editUser(dataOfUser);
  alert('edit user success!!!');
  confirmDialogStatus.value = false;
  appRouter.push({ name: 'UserList' });
};

const checkValue = (event) => {
  let selectedValue = event.target.value;
  localDataUser.roleUser = selectedValue;
  console.log(localDataUser.roleUser);
  console.log(localDataUser.idUser);
};

//check invalid from input
// const inputNameIsInvalid = computed(() => {
//   return (
//     isInvalid.value &&
//     (localDataUser.nameUser.trim() == '' || localDataUser.nameUser.length == 0)
//   );
// });

const inputNameIsEmpty = computed(() => {
  return (
    isInvalid.value &&
    (localDataUser.nameUser.trim() == '' || localDataUser.nameUser.length == 0)
  );
});
const inputNameIsOver = computed(() => {
  return isInvalid.value && localDataUser.nameUser.length > 100;
});

const inputNameIsDuplicate = computed(() => {
  for (let someUser of userListAlls.value) {
    if (params.idUser != someUser.idUser) {
      if (
        new String(localDataUser.nameUser).valueOf().trim() ==
        new String(someUser.nameUser).valueOf().trim()
      ) {
        console.log('Duplicate name !');
        nameIsDuplicate.value = true;
        isInvalid.value = true;
        break;
      } else {
        nameIsDuplicate.value = false;
      }
    }
  }
  return isInvalid.value && nameIsDuplicate.value;
});

const inputEmailIsEmpty = computed(() => {
  return (
    isInvalid.value &&
    (localDataUser.emailUser.trim() == '' ||
      localDataUser.emailUser.length == 0)
  );
});
const inputEmailIsInvalid = computed(() => {
  if (inputEmailIsEmpty.value) {
    return '';
  }
  if (!localDataUser.emailUser.match(regexEmail)) {
    return isInvalid.value && !localDataUser.emailUser.match(regexEmail);
  }
});
const inputEmailIsOver = computed(() => {
  return isInvalid.value && localDataUser.emailUser.length > 50;
});

const inputEmailIsDuplicate = computed(() => {
  for (let someUser of userListAlls.value) {
    if (params.idUser != someUser.idUser) {
      if (
        new String(localDataUser.emailUser).valueOf().trim() ==
        new String(someUser.emailUser).valueOf().trim()
      ) {
        console.log('Duplicate email !');
        emailIsDuplicate.value = true;
        isInvalid.value = true;
        break;
      } else {
        emailIsDuplicate.value = false;
      }
    }
  }
  return isInvalid.value && emailIsDuplicate.value;
});

const inputRoleIsEmpty = computed(() => {
  return (
    isInvalid.value &&
    (localDataUser.roleUser == null ||
      localDataUser.roleUser == undefined ||
      localDataUser.roleUser.trim() == '' ||
      localDataUser.roleUser.length == 0)
  );
});

const checkInputNameInvalid = () => {
  // console.log(localDataUser);
  // if (localDataUser.nameUser.length != 0) {
  let errorInputName = '';
  // if (inputNameIsInvalid.value) {
  //   console.log(isInvalid.value + '' + inputNameIsInvalid.value);
  //   console.log(localDataUser);
  //   errorInputName += ' name is invalid.';
  // }
  if (inputNameIsEmpty.value) {
    errorInputName += ' please enter your name.';
  }
  if (inputNameIsOver.value) {
    errorInputName += ' over limit of input name.';
  }
  if (inputNameIsDuplicate.value) {
    errorInputName += 'name is already to use.';
  }
  if (
    inputNameIsOver.value ||
    inputNameIsDuplicate.value ||
    inputNameIsEmpty.value
  )
    return '*' + errorInputName;
  // }
};

const checkInputEmailInvalid = () => {
  // console.log(localDataUser);
  // if (localDataUser.emailUser.length != 0) {
  let errorInputEmail = '';
  if (inputEmailIsInvalid.value) {
    // console.log(isInvalid.value + '' + inputNameIsInvalid.value);
    // console.log(localDataUser);
    errorInputEmail += ' email is invalid.';
  }
  if (inputEmailIsEmpty.value) {
    errorInputEmail += ' please enter your email.';
  }
  if (inputEmailIsOver.value) {
    errorInputEmail += ' over limit of input email.';
  }
  if (inputEmailIsDuplicate.value) {
    errorInputEmail += 'email is already to use.';
  }
  if (
    inputEmailIsInvalid.value ||
    inputEmailIsOver.value ||
    inputEmailIsDuplicate.value ||
    inputEmailIsEmpty.value
  )
    return '*' + errorInputEmail;
  // }
};

onBeforeMount(async () => {
  responseGetAllUser.value = await getAllUsers();
  userListAlls.value = await responseGetAllUser.value.data;
  responseGetUser.value = await getUser(params.idUser);
<<<<<<< HEAD
  user.value = responseGetUser.value.data;
  console.log(user.value);
=======
>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac
  // console.log(responseGetUser.value.data);
  localDataUser.nameUser = responseGetUser.value.data.nameUser;
  localDataUser.emailUser = responseGetUser.value.data.emailUser;
  localDataUser.roleUser = responseGetUser.value.data.roleUser;
<<<<<<< HEAD
  console.log(localDataUser);
=======

>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac
  // const response = await responseGetUser.value.data.then((userFromId) => {
  //   user.value = userFromId;
  //   // console.log(user.value);
  //   localDataUser.nameUser = user.value.nameUser;
  //   localDataUser.emailUser = user.value.emailUser;
  //   localDataUser.roleUser = user.value.roleUser;
  //   // console.log(localDataUser);
  // });
  // // console.log(response);
});
</script>

<template>
  <div>
    <NavbarTop/>
    <NavbarBottom/>
    <div @click="changeCancelDialogShow(user)"
      class="absolute top-[15%] bg-white rounded left-[2%] p-1 hover:bg-[#E9E9E9]">
      <svg width="50px" height="50px" viewBox="0 0 12 24">
        <path fill="#000000"
          d="M9.125 21.1L.7 12.7q-.15-.15-.212-.325Q.425 12.2.425 12t.063-.375Q.55 11.45.7 11.3l8.425-8.425q.35-.35.875-.35t.9.375q.375.375.375.875t-.375.875L3.55 12l7.35 7.35q.35.35.35.862q0 .513-.375.888t-.875.375q-.5 0-.875-.375Z">
        </path>
      </svg>
    </div>

    <div class="bg-white w-[1000px] h-[600px] mt-[200px] m-auto rounded-xl">
      <div class="bg-[#50ABCB] w-full h-[100px] rounded-t-xl">
        <div class="grid grid-rows-1 grid-flow-col h-full content-center justify-items-center">
          <div class="col-start-1 col-span-1 uppercase m-auto text-[30px] text-white font-semibold">
            User Id : {{ localDataUser.idUser }}
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
        <div class="grid grid-rows-6 grid-cols-12 content-center justify-items-center">
          <!-- <div class="grid grid-rows-6 grid-cols-12"> -->
          <div class="row-start-2 row-end-4 col-start-2 col-end-5">
            <img class="grid w-[150px] rounded-xl" src="../../../public/images/Spy.jpg" alt="spy" />
          </div>
          <div class="grid row-start-1 col-start-6 col-end-8 content-center text-center text-[18px]">
            Name user
          </div>
          <div class="grid row-start-1 col-start-8 col-end-12 content-center text-center w-[90%]">
            <form>
              <div class="relative">
                <input v-model="localDataUser.nameUser"
                  class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75"
                  type="text" />
                <label class="placeholder text-[#D9D9D9]">name</label>
                <label class="absolute text-red-500 left-0 top-[40px]">
                  {{ checkInputNameInvalid() }}
                </label>
              </div>
            </form>
          </div>

          <div class="grid row-start-1 col-start-12 content-center text-left text-[#D9D9D9] w-[90%]">
            {{ localDataUser.nameUser?.length || 0 }}/100
          </div>

          <div class="grid row-start-2 col-start-6 col-end-8 content-center text-center text-[18px]">
            Email
          </div>
          <div class="grid row-start-2 col-start-8 col-end-12 content-center text-center w-[90%]">
            <form>
              <div class="relative">
                <input v-model="localDataUser.emailUser"
                  class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75"
                  type="text" />
                <label class="placeholder text-[#D9D9D9]">Email</label>
                <label class="absolute text-red-500 left-0 top-[40px]">
                  {{ checkInputEmailInvalid() }}
                </label>
              </div>
            </form>
          </div>
          <div class="grid row-start-2 col-start-12 content-center text-left text-[#D9D9D9] w-[90%]">
            {{ localDataUser.emailUser?.length || 0 }}/50
          </div>

          <div class="grid row-start-3 col-start-6 col-end-8 content-center text-center text-[18px]">
            role
          </div>
          <div class="grid row-start-3 col-start-8 col-end-12 content-center w-[90%]">
            <div class="relative">
              <!-- <select :v-model="(user.roleUser == '' ? roleIndexSelect : user.roleUser )" -->
<<<<<<< HEAD
              <select v-model="localDataUser.roleUser" @change="checkValue($event)"
=======
              <select :v-model="localDataUser.roleUser" @change="checkValue($event)"
>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac
                class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75 text-center"
                name="" id="">
                <!-- <option value=""></option> -->
                <!-- <option v-for="(role,index) in roles" :value="role" v-bind:key="indexs">
                {{role}}</option> -->
                <!-- <option value="admin"> {{localDataUser.roleUser}}</option> -->
                <option value="admin">Admin</option>
                <option value="lecturer">Lecturer</option>
                <option value="student">Student</option>
              </select>
              <!-- {{localDataUser.roleUser}} -->
            </div>
          </div>
          <div class="grid row-start-4 col-start-6 col-end-8 content-center text-center text-[18px]">
            createOn
          </div>
          <div class="grid row-start-4 col-start-8 col-end-12 content-center text-center w-[90%]">
            <form>
              <div class="relative">
                <input disabled
                  class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75"
                  type="text" :placeholder="`${moment(user.createOn)
                  .local()
                  .format('DD/MM/YYYY')} | ${moment(user.createOn)
                  .local()
                  .format('hh:mm A')}`" />
              </div>
              <!-- {{ `${moment(user.createOn).local().format('DD/MM/YYYY')} |${moment(user.createOn).local().format('hh:mm A')}`}} -->
            </form>
          </div>

          <div class="grid row-start-5 col-start-6 col-end-8 content-center text-center text-[18px]">
            updateOn
          </div>

          <div class="grid row-start-5 col-start-8 col-end-12 content-center text-center w-[90%]">
            <form>
              <div class="relative">
                <input disabled
                  class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75"
                  type="text" :placeholder="`${moment(user.updateOn)
                  .local()
                  .format('DD/MM/YYYY')} | ${moment(user.updateOn)
                  .local()
                  .format('hh:mm A')}`" />
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

          <div class="relative grid row-start-6 col-start-6 col-end-9 content-center text-center w-full">
            <button @click="changeCancelDialogShow(user)"
              class="rounded-md bg-[#F24052] text-[#ffffff] w-[80%] m-auto p-2 hover:bg-[#D92739] transition delay-75">
              Cancel
            </button>
            <!-- <label v-if="false" class="absolute text-red-500 top-[70px]">
                            *something in form is invalid.
                        </label> -->
          </div>

          <div class="relative grid row-start-6 col-start-9 col-end-12 content-center text-center w-full">
            <button :disabled="
              inputNameIsEmpty ||
              inputNameIsOver ||
              inputNameIsDuplicate ||
              inputRoleIsEmpty ||
              inputEmailIsDuplicate ||
              inputEmailIsEmpty ||
              inputEmailIsInvalid ||
              inputEmailIsOver
            " @click="changeConfirmDialogShow(user)"
              class="rounded-md bg-[#00A28B] text-[#ffffff] w-[80%] m-auto p-2 hover:bg-[#017D6C] transition delay-75 disabled:bg-[#679790]">
              Confirm
            </button>

            <label v-if="
              inputNameIsEmpty ||
              inputNameIsOver ||
              inputNameIsDuplicate ||
              inputRoleIsEmpty ||
              inputEmailIsDuplicate ||
              inputEmailIsEmpty ||
              inputEmailIsInvalid ||
              inputEmailIsOver
            " class="absolute text-red-500 left-[22px] top-[70px] text-[14px]">
              *something in form is invalid.
            </label>
          </div>

          <Cancel v-if="user.cancelDialogStatus" @onClickCancelNo="changeCancelDialogClose(user)"
            @onClickCancelYes="cancelEditUser(user)" />
          <Confirm v-if="user.confirmDialogStatus" @onClickConfirmNo="changeConfirmDialogClose(user)"
            @onClickConfirmYes="editUserSuccess(localDataUser)" />
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

input:focus+.placeholder {
  font-size: 12px;
  color: #105e99;
}

input:focus {
  border-color: #105e99;
  outline: none !important;
}
</style>
