<script setup>
import { storeToRefs } from "pinia";
import { ref, reactive, onBeforeMount, computed } from "vue";
import { useRouter } from "vue-router"; //get params to script
import { userStore } from "../../stores/user.js";
// let {params} = useRoute()
// console.log(params)
const appRouter = useRouter();

const userSignInRes = userStore();
// const {signInUser} = storeToRefs(userResponse);

userSignInRes.getRefreshToken().then(() => {
   if (userSignInRole.value == 'student' || userSignInRole.value == 'lecturer' || userSignInRole.value == 'admin') {
    appRouter.go(-1);
  }
});

const loginData = reactive({
  userEmail: "",
  userPassword: "",
});

const userMatch = ref(false);
const isInvalid = ref(false);
const regexEmail = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}";

const userSignInRole = computed(() => userSignInRes.signInUserData.userRole);

const goBackToHome = () => {
  appRouter.push({ name: "Home" });
};

const inputEmailIsEmpty = computed(() => {
  return (
    isInvalid.value &&
    (loginData.userEmail.trim() == "" || loginData.userEmail.length == 0)
  );
});

const inputEmailIsInvalid = computed(() => {
  if (inputEmailIsEmpty.value) {
    return "";
  }
  if (!loginData.userEmail.match(regexEmail)) {
    return isInvalid.value && !loginData.userEmail.match(regexEmail);
  }
});
const inputEmailIsOver = computed(() => {
  return isInvalid.value && loginData.userEmail.length > 50;
});

const inputPasswordIsEmpty = computed(() => {
  return isInvalid.value && loginData.userPassword.length == 0;
});

const inputPasswordIsInvalid = computed(() => {
  return (
    isInvalid.value &&
    loginData.userPassword.length < 8 &&
    loginData.userPassword.length > 0
  );
});

const confirmToLogin = async (dataForLogin) => {
  isInvalid.value = false;
  loginData.userEmail = loginData.userEmail.trim();
  if (
    loginData.userEmail.trim() == "" ||
    loginData.userEmail.length == 0 ||
    !loginData.userEmail.match(regexEmail) ||
    loginData.userEmail.length > 50 ||
    loginData.userPassword.length == 0 ||
    (loginData.userPassword.length < 8 && loginData.userPassword.length > 0)
  ) {
    isInvalid.value = true;
    console.log("something is invalid");
    // alert("email or password is not match")
  } else {
    isInvalid.value = false;
    console.log("valid");
  }

  const res = await userSignInRes.signInUser(dataForLogin);
  if (res.status === 200) {
    isInvalid.value = false;
    alert("Log in success !!");
    // console.log('Log in success !!');
    // setTimeout(goBackToHome(), 2000);
    goBackToHome();
  } else if (res.status === 400) {
    isInvalid.value = true;
    alert("please enter your email and password.");
    console.log("please enter your email and password.");
  } else if (res.status === 401) {
    isInvalid.value = true;
    alert("email or password is not match.");

    console.log("email or password is not match.");
  } else if (res.status === 404) {
    isInvalid.value = true;
    alert("error , can not found");
    console.log("error , can not found");
  }

  return "";
  // for(let someUser of getUserListAlls.value){
  //   console.log(someUser);
  //   if(loginData.email != someUser.emailUser || loginData.password != someUser.password){

  //   }
  // }
};

onBeforeMount(async () => {
  // const allUsers = await getAllUsers();
  // getUserListAlls.value = await allUsers.json();
  // console.log(getUserListAlls.value);
});
</script>

<template>
  <div>
    <div
      @click="goBackToHome()"
      class="absolute bg-white rounded left-[2%] p-1 hover:bg-[#E9E9E9]"
    >
      <svg width="50px" height="50px" viewBox="0 0 12 24">
        <path
          fill="#000000"
          d="M9.125 21.1L.7 12.7q-.15-.15-.212-.325Q.425 12.2.425 12t.063-.375Q.55 11.45.7 11.3l8.425-8.425q.35-.35.875-.35t.9.375q.375.375.375.875t-.375.875L3.55 12l7.35 7.35q.35.35.35.862q0 .513-.375.888t-.875.375q-.5 0-.875-.375Z"
        ></path>
      </svg>
    </div>
    <div
      class="bg-white rounded-xl mt-[100px] m-auto w-[1200px] h-[800px] border border-[#CFD8DC] drop-shadow-lg"
    >
      <div class="grid grid-rows-8 grid-cols-12">
        <div class="row-start-1 h-[100px] w-[100px]"></div>

        <div class="row-start-2 col-start-6 col-end-8 m-auto flex">
          <img
            class="w-[56px] items-right mr-4"
            src="../../../public/images/logo.png"
            alt="logo"
          />
          <div class="font-bold uppercase text-center text-[30px]">time-up</div>
        </div>

        <div class="row-start-3 col-start-6 col-end-8 m-auto">
          <p class="text-[18px]">Sign-in</p>
        </div>

        <div class="row-start-4 col-start-4 col-end-10">
          <div class="relative m-auto w-[90%]">
            <input
              :style="{
                'border-color':
                  inputEmailIsEmpty || inputEmailIsInvalid || inputEmailIsOver
                    ? 'red'
                    : '',
              }"
              v-model="loginData.userEmail"
              class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75"
              type="text"
            />
            <label
              :style="{
                color:
                  inputEmailIsEmpty || inputEmailIsInvalid || inputEmailIsOver
                    ? 'red'
                    : '',
              }"
              class="placeholder text-[#D9D9D9]"
              >Email</label
            >
            <label v-if="inputEmailIsEmpty" class="text-red-500 text-[14px]">
              please enter your email.
            </label>
            <label v-if="inputEmailIsInvalid" class="text-red-500 text-[14px]">
              email is invalid form.
            </label>
            <label v-if="inputEmailIsOver" class="text-red-500 text-[14px]">
              over limit of input email.
            </label>
          </div>
        </div>

        <div class="row-start-4 col-start-10 text-[#D9D9D9]">
          {{ loginData.userEmail.length }}/50
        </div>

        <div class="row-start-5 col-start-4 col-end-10">
          <div class="relative mx-auto w-[90%]">
            <input
              @keyup.enter="confirmToLogin(loginData)"
              :style="{
                'border-color':
                  inputPasswordIsEmpty || inputPasswordIsInvalid ? 'red' : '',
              }"
              v-model="loginData.userPassword"
              class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75"
              type="password"
              minlength="8"
              maxlength="14"
            />
            <label
              :style="{
                color:
                  inputPasswordIsEmpty || inputPasswordIsInvalid ? 'red' : '',
              }"
              class="placeholder text-[#D9D9D9]"
              >Password</label
            >
            <label v-if="inputPasswordIsEmpty" class="text-red-500 text-[14px]">
              *please enter your password.
            </label>
            <label
              v-if="inputPasswordIsInvalid"
              class="text-red-500 text-[14px]"
            >
              *password length minimum is 8.
            </label>
          </div>
        </div>

        <div class="row-start-5 col-start-10 text-[#D9D9D9]">
          {{ loginData.userPassword.length }}/14
        </div>

        <!-- <div class="row-start-7 col-start-4 col-end-7 justify-center flex">
          <router-link class="w-[70%] m-auto" :to="{ name: 'CreateUser' }">
          <button
            class="rounded-md bg-[#105E99] text-[#ffffff] w-full m-auto p-2 hover:bg-[#004980] transition delay-75"
          >
            Sign-Up
          </button>
          </router-link>
        </div> -->

        <div
          class="relative grid row-start-7 col-start-5 col-end-9 w-full content-center"
        >
          <!-- <router-link class="w-[70%] m-auto" :to="{ name: '' }"> -->
          <button
            @click="confirmToLogin(loginData)"
            class="w-[70%] m-auto rounded-md bg-[#105E99] text-[#ffffff] w-full m-auto p-2 hover:bg-[#004980] transition delay-75"
          >
            Sign-In
          </button>
          <!-- </router-link> -->
          <label
            v-if="
              isInvalid ||
              inputEmailIsEmpty ||
              inputEmailIsInvalid ||
              inputEmailIsOver ||
              inputPasswordIsEmpty ||
              inputPasswordIsInvalid
            "
            class="left-[40px] absolute text-red-500 top-[70px] text-[14px]"
          >
            *something in form is invalid.
          </label>
        </div>

        <div
          class="row-start-1 row-end-7 col-start-1 bg-[#F24052] rounded-tl-lg"
        ></div>
        <div
          class="row-start-1 row-end-7 col-start-12 bg-[#105E99] rounded-tr-lg"
        ></div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.grid-rows-8 {
  grid-template-rows: repeat(8, minmax(0, 1fr));
}

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

input:focus + .placeholder {
  font-size: 12px;
  color: #105e99;
}

input:focus {
  border-color: #105e99;
  outline: none !important;
}
</style>
