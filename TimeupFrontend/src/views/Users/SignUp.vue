<script setup>
import { ref, computed, reactive, onBeforeMount } from 'vue';
import { useRouter } from 'vue-router'; //get params to script
import Confirm from '../../components/Confirm.vue';
import Cancel from '../../components/Cancel.vue';
import { createUser, getAllUsers } from '../../stores/user.js';
import PleaseLogInDialog from '../../components/PleaseLogInDialog.vue';

const appRouter = useRouter();
// const goBackToHome = () => appRouter.push({ name: 'SignIn' });
const localDataUser = reactive({
  nameUser: '',
  emailUser: '',
  roleUser: 'student',
  password: '',
  confirmPassword: ''
});

const userListAlls = ref([]);
const responseGetAllUser = ({});
const responseCreateUser = ({});
<<<<<<< HEAD
<<<<<<< HEAD
const getToken = localStorage.getItem('refreshToken');
=======
const getToken = localStorage.getItem('accessToken');
>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac
=======
const getToken = localStorage.getItem('accessToken');
>>>>>>> parent of 0b0be68 (update-fe-refreshToken-03-finish)
const pageName = ref('SIGN-UP');
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
const changeCancelDialogShow = () => {
  cancelDialogStatus.value = true;
};

const changeCancelDialogClose = () => {
  cancelDialogStatus.value = false;
};

const cancelCreateUser = () => {
  cancelDialogStatus.value = false;
  // appRouter.push({ name: 'SignIn' });
  appRouter.go(-1);
};

const changeConfirmDialogShow = () => {
  isInvalid.value = false;
  if (
    localDataUser.nameUser.trim() == '' ||
    localDataUser.nameUser.length > 100 ||
    localDataUser.emailUser.trim() == '' ||
    localDataUser.emailUser.trim() == null ||
    !localDataUser.emailUser.match(regexEmail) ||
    localDataUser.emailUser.length > 50 ||
    localDataUser.roleUser == null ||
    localDataUser.roleUser == undefined ||
    localDataUser.roleUser.trim() == '' ||
    localDataUser.roleUser.length == 0 ||
    localDataUser.password.length == 0 ||
    (localDataUser.password.length > 0 && localDataUser.password.length < 8) ||
    !(localDataUser.password == localDataUser.confirmPassword)
  ) {
    isInvalid.value = true;
  } else {
    isInvalid.value = false;
  }
  if (
    nameIsDuplicate.value == false &&
    emailIsDuplicate.value == false &&
    isInvalid.value == false
  ) {
    confirmDialogStatus.value = true;
    isInvalid.value = false;
  }
  console.log('Name and email is valid.');
};

const changeConfirmDialogClose = () => {
  confirmDialogStatus.value = false;
};

const createUserSuccess = async (dataOfUser) => {
  dataOfUser.nameUser = dataOfUser.nameUser.trim();
  dataOfUser.emailUser = dataOfUser.emailUser.trim();
  alert('create user success!!!');
  confirmDialogStatus.value = false;
  // appRouter.push({ name: 'SignIn' })
  appRouter.push({ name: 'Home' });
  await createUser(dataOfUser);
};

//check invalid from input
const inputNameIsInvalid = computed(() => {
  if (
    isInvalid.value &&
    (localDataUser.nameUser.trim() == '' || localDataUser.nameUser.length == 0)
  ) {
    return '';
  } else {
    return isInvalid.value && localDataUser.nameUser.trim() == '';
  }
});

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

const inputPasswordIsEmpty = computed(() => {
  return isInvalid.value && localDataUser.password.length == 0;
});

const inputPasswordIsInvalid = computed(() => {
  return (
    isInvalid.value &&
    localDataUser.password.length < 8 &&
    localDataUser.password.length > 0
  );
});

const inputConfirmPasswordIsInvalid = computed(() => {
  return (
    isInvalid.value &&
    (localDataUser.confirmPassword.length == 0 ||
      localDataUser.confirmPassword.length < 8 ||
      !(localDataUser.confirmPassword == localDataUser.password))
    // !localDataUser.confirmPassword.matchAll(localDataUser.password)
  );
});

const checkRole = () => {
  console.log(localDataUser.roleUser);
  // console.log('password : ' + localDataUser.password);
  // console.log('confirmpassword : ' + localDataUser.confirmPassword);
  // console.log(localDataUser.confirmPassword == localDataUser.password);
  // console.log(localDataUser.confirmPassword.matchAll(localDataUser.password));
};

//

onBeforeMount(async () => {
  responseGetAllUser.value = await getAllUsers();
  userListAlls.value = responseGetAllUser.value.data;
  // console.log(localDataUser.roleUser.length)
});
</script>

<template>
  <div>
    <div @click="changeCancelDialogShow" class="absolute bg-white rounded left-[2%] p-1 hover:bg-[#E9E9E9]">
      <!-- <router-link :to="{ name: 'UserList' }"> </router-link> -->
      <svg width="50px" height="50px" viewBox="0 0 12 24">
        <path fill="#000000"
          d="M9.125 21.1L.7 12.7q-.15-.15-.212-.325Q.425 12.2.425 12t.063-.375Q.55 11.45.7 11.3l8.425-8.425q.35-.35.875-.35t.9.375q.375.375.375.875t-.375.875L3.55 12l7.35 7.35q.35.35.35.862q0 .513-.375.888t-.875.375q-.5 0-.875-.375Z">
        </path>
      </svg>

      <!-- <svg
        class="dropdownButton"
        width="20px"
        height="20px"
        viewBox="0 0 24 24"
      >
        <path fill="#000000" d="m7 10l5 5l5-5z"></path>
      </svg> -->
    </div>

    <div class="bg-white rounded-xl mt-[100px] m-auto w-[1200px] h-[800px]">
      <div class="grid grid-rows-8 grid-cols-12">
        <div class="row-start-1 col-start-7 h-[100px] w-[100px]"></div>
        <div class="row-start-1 row-end-9 col-start-1 col-end-2 bg-[#F24052] rounded-l-lg"></div>
        <div class="grid row-start-1 row-end-9 col-start-2 col-end-6 bg-[#8BBDDB] content-center">
          <img class="m-auto" src="../../../public/images/peoples01.jpg" alt="peoples" />
        </div>

        <div class="row-start-1 col-start-8 col-end-10 m-auto flex">
          <img class="w-[56px] items-right mr-4" src="../../../public/images/logo.png" alt="logo" />
          <div class="font-bold uppercase text-center text-[30px]">time-up</div>
        </div>

        <div class="row-start-2 col-start-8 col-end-12">
          <p class="text-[18px]">Create Your Account with the form below .</p>
        </div>

        <div class="row-start-3 col-start-6 col-end-9 mx-auto w-[90%]">
          <form>
            <div class="relative">
              <input :style="{
                'border-color':
                  inputNameIsEmpty || inputNameIsOver || inputNameIsDuplicate
                    ? 'red'
                    : ''
              }" v-model="localDataUser.nameUser"
                class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75"
                type="text" />
              <label class="placeholder text-[#D9D9D9]">Name</label>
              <label v-if="
                inputNameIsEmpty || inputNameIsOver || inputNameIsDuplicate
              " class="text-red-500 text-[14px]">
                *
              </label>
              <!-- <label v-if="inputNameIsInvalid" class="text-red-500 text-[14px]">
                name is invalid.
              </label> -->
              <label v-if="inputNameIsEmpty" class="text-red-500 text-[14px]">
                please enter your name.
              </label>
              <label v-if="inputNameIsOver" class="text-red-500 text-[14px]">
                over limit of input name.
              </label>
              <label v-if="inputNameIsDuplicate" class="text-red-500 text-[14px]">
                *name is already to use.
              </label>
            </div>
          </form>
        </div>

        <div class="row-start-3 col-start-9 w-[90%] text-[#D9D9D9]">
          {{ localDataUser.nameUser.length }}/100
        </div>

        <div class="row-start-3 col-start-10 col-end-12 mx-auto w-[80%]">
          <div class="relative">
            <select :style="{
              'border-color': inputRoleIsEmpty ? 'red' : ''
            }" v-model="localDataUser.roleUser" @change="checkRole()"
              class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75 text-center"
              name="" id="">
              <!-- <option value="" selected hidden>role</option> -->
              <option value="admin">Admin</option>
              <option value="lecturer">Lecturer</option>
              <option value="student">Student</option>
            </select>

            <label v-if="inputRoleIsEmpty" class="text-red-500 text-[14px]">
              *&nbsp;please choose <br />
              &nbsp;&nbsp; your role</label>
          </div>
        </div>

        <div class="row-start-4 col-start-6 col-end-9 mx-auto w-[90%]">
          <form>
            <div class="relative">
              <input :style="{
                'border-color':
                  inputEmailIsDuplicate ||
                  inputEmailIsEmpty ||
                  inputEmailIsInvalid ||
                  inputEmailIsOver
                    ? 'red'
                    : ''
              }" v-model="localDataUser.emailUser"
                class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75"
                type="text" />
              <label class="placeholder text-[#D9D9D9]">Email</label>
              <label v-if="inputEmailIsEmpty" class="text-red-500 text-[14px]">
                please enter your email.
              </label>
              <label v-if="inputEmailIsInvalid" class="text-red-500 text-[14px]">
                email is invalid form.
              </label>
              <label v-if="inputEmailIsOver" class="text-red-500 text-[14px]">
                over limit of input email.
              </label>
              <label v-if="inputEmailIsDuplicate" class="text-red-500 text-[14px]">
                email is already to use.
              </label>
            </div>
          </form>
        </div>

        <div class="row-start-4 col-start-9 w-[90%] text-[#D9D9D9]">
          {{ localDataUser.emailUser.length }}/50
        </div>

        <div class="row-start-5 col-start-6 col-end-9 mx-auto w-[90%]">
          <form>
            <div class="relative">
              <input :style="{
                'border-color':
                  inputPasswordIsEmpty || inputPasswordIsInvalid ? 'red' : ''
              }" v-model="localDataUser.password"
                class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75"
                type="password" minlength="8" maxlength="14" />
              <label :style="{
                color:
                  inputPasswordIsEmpty || inputPasswordIsInvalid ? 'red' : ''
              }" class="placeholder text-[#D9D9D9]">Password</label>
              <label v-if="inputPasswordIsEmpty" class="text-red-500 text-[14px]">
                *please enter your password.
              </label>
              <label v-if="inputPasswordIsInvalid" class="text-red-500 text-[14px]">
                *password length minimum is 8.
              </label>
            </div>
          </form>
        </div>

        <div class="row-start-5 col-start-9 w-[90%] text-[#D9D9D9]">
          {{ localDataUser.password.length }}/14
        </div>

        <div class="row-start-6 col-start-6 col-end-9 mx-auto w-[90%]">
          <form>
            <div class="relative">
              <input :style="{
                'border-color': inputConfirmPasswordIsInvalid ? 'red' : ''
              }" v-model="localDataUser.confirmPassword"
                class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75"
                type="password" minlength="8" maxlength="14" />
              <label :style="{
                color: inputConfirmPasswordIsInvalid ? 'red' : ''
              }" class="placeholder text-[#D9D9D9]">Confirm Password</label>
              <label v-if="inputConfirmPasswordIsInvalid" class="text-red-500 text-[14px]">
                *Confirm password is invalid.
              </label>
            </div>
          </form>
        </div>

        <div class="row-start-6 col-start-9 w-[90%] text-[#D9D9D9]">
          {{ localDataUser.confirmPassword.length }}/14
        </div>

        <div class="relative row-start-7 col-start-7 col-end-12 justify-center flex w-full">
          <button :disabled="
            inputNameIsEmpty ||
            inputNameIsOver ||
            inputNameIsDuplicate ||
            inputRoleIsEmpty ||
            inputEmailIsDuplicate ||
            inputEmailIsEmpty ||
            inputEmailIsInvalid ||
            inputEmailIsOver ||
            inputPasswordIsEmpty ||
            inputPasswordIsInvalid ||
            inputConfirmPasswordIsInvalid
          " :style="{
              'border-color':
                inputNameIsEmpty ||
                inputNameIsOver ||
                inputNameIsDuplicate ||
                inputRoleIsEmpty ||
                inputEmailIsDuplicate ||
                inputEmailIsEmpty ||
                inputEmailIsInvalid ||
                inputEmailIsOver ||
                inputPasswordIsEmpty ||
                inputPasswordIsInvalid ||
                inputConfirmPasswordIsInvalid
                  ? 'red'
                  : ''
            }" @click="changeConfirmDialogShow"
            class="rounded-md bg-[#105E99] text-[#ffffff] w-[70%] m-auto p-2 hover:bg-[#004980] transition delay-75 disabled:opacity-50">
            Create your account
          </button>

          <label v-if="
            inputNameIsEmpty ||
            inputNameIsOver ||
            inputNameIsDuplicate ||
            inputRoleIsEmpty ||
            inputEmailIsDuplicate ||
            inputEmailIsEmpty ||
            inputEmailIsInvalid ||
            inputEmailIsOver ||
            inputPasswordIsEmpty ||
            inputPasswordIsInvalid ||
            inputConfirmPasswordIsInvalid
          " class="absolute text-red-500 top-[70px] text-[14px]">
            *something in form is invalid.
          </label>
        </div>

        <div class="row-start-1 row-end-9 col-start-12 col-end-13 bg-[#105E99] rounded-r-lg"></div>

        <Cancel v-if="cancelDialogStatus" @onClickCancelNo="changeCancelDialogClose"
          @onClickCancelYes="cancelCreateUser" />
        <Confirm v-if="confirmDialogStatus" @onClickConfirmNo="changeConfirmDialogClose"
          @onClickConfirmYes="createUserSuccess(localDataUser)" />
      </div>

      <PleaseLogInDialog v-if="!getToken" :pageName="pageName"/>

    </div>
  </div>
</template>

<style scoped>
.grid-rows-8 {
  grid-template-rows: repeat(8, minmax(0, 1fr));
}

.grid-cols-13 {
  grid-template-columns: repeat(13, minmax(0, 1fr));
}

.grid-cols-14 {
  grid-template-columns: repeat(14, minmax(0, 1fr));
}

.row-start-8 {
  grid-row-start: 8;
}

.row-end-8 {
  grid-row-end: 8;
}

.row-end-9 {
  grid-row-end: 9;
}

.row-end-10 {
  grid-row-end: 10;
}

.col-start-14 {
  grid-column-start: 14;
}

.col-end-14 {
  grid-column-end: 14;
}

.col-end-15 {
  grid-column-end: 15;
}

/* .form
{
    background-color: #fff;
    padding: 40px 20px;
    width: 100%;
    max-width: 300px;
    box-sizing: border-box;
    border-radius: 5px;
    box-shadow: 0px 0px 10px 0px #666;
} */

/* .input {
  padding: 10px;
  width: 100%;
  max-width: 300px;
  box-sizing: border-box;
  outline: none;
  border: 1px solid #04aa6d;
} */

.placeholder {
  position: absolute;
  top: -10px;
  left: 8px;
  font-size: 14px;
  background-color: #fff;
  padding: 0px 5px;
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
