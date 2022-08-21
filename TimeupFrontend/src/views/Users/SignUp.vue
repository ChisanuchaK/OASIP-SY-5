<script setup>
import { useRouter } from 'vue-router' //get params to script
// let {params} = useRoute()
// console.log(params)

const appRouter = useRouter();

const goBackToHome = () => appRouter.push({ name: 'SignIn' });
</script>

<template>
  <div>
    <div @click="goBackToHome()" class="absolute bg-white rounded left-[2%] p-1 hover:bg-[#E9E9E9]">
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
        <div class="row-start-1 row-end-9 col-start-2 col-end-7 bg-[#8BBDDB] flex">
          <img class="m-auto" src="../../../public/images/peoples01.jpg" alt="peoples" />
        </div>

        <div class="row-start-1 col-start-8 col-end-10 m-auto flex">
          <img class="w-[56px] items-right mr-4" src="../../../public/images/logo.png" alt="logo" />
          <div class="font-bold uppercase text-center text-[30px]">time-up</div>
        </div>

        <div class="row-start-2 col-start-8 col-end-12">
          <p class="text-[18px]">Create Your Account with the form below .</p>
        </div>

        <div class="row-start-3 col-start-7 col-end-10 mx-auto w-[90%]">
          <form>
            <div class="relative">
              <input
                class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75"
                type="text" />
              <label class="placeholder text-[#D9D9D9]">Name</label>
              <label v-if="false" class="text-red-500">
                *name is invalid
              </label>
            </div>
          </form>
        </div>

        <div class="row-start-3 col-start-10 col-end-12 mx-auto w-[80%]">
          <div class="relative">
            <select
              class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75"
              name="" id="">
              <option value=""></option>
              <option value="Admin">Admin</option>
              <option value="Lecturer">Lecturer</option>
              <option value="Student">Student</option>
            </select>
            <label v-if="false" class="text-red-500"> *name is invalid </label>
          </div>
        </div>

        <div class="row-start-4 col-start-7 col-end-10 mx-auto w-[90%]">
          <form>
            <div class="relative">
              <input
                class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75"
                type="text" />
              <label class="placeholder text-[#D9D9D9]">Email</label>
              <label v-if="false" class="text-red-500">
                *email is invalid
              </label>
            </div>
          </form>
        </div>

        <!-- <div class="row-start-5 col-start-7 col-end-10  mx-auto w-[90%] ">
                    <form>
                        <div class="relative">
                            <input
                                class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75"
                                type="text" />
                            <label class="placeholder text-[#D9D9D9]">Password</label>
                            <label v-if="false"  class="text-red-500">
                                *password is invalid
                            </label>
                        </div>
                    </form>
                </div>

                <div class="row-start-6 col-start-7 col-end-10  mx-auto w-[90%] ">
                    <form>
                        <div class="relative">
                            <input
                                class="border rounded-md border-solid border-[#D9D9D9] w-full p-2 hover:bg-[#F2F2F2] transition delay-75"
                                type="text" />
                            <label class="placeholder text-[#D9D9D9]">Confirm Password</label>
                            <label v-if="false"  class="text-red-500">
                                *Confirm password is invalid
                            </label>
                        </div>
                    </form>
                </div> -->

        <div class="relative row-start-7 col-start-7 col-end-12 justify-center flex w-full">
          <button
            class="rounded-md bg-[#105E99] text-[#ffffff] w-[70%] m-auto p-2 hover:bg-[#004980] transition delay-75">
            Create your account
          </button>

          <label v-if="false" class="absolute text-red-500 top-[70px]">
            *something in form is invalid.
          </label>
        </div>

        <div class="row-start-1 row-end-9 col-start-12 col-end-13 bg-[#105E99] rounded-r-lg"></div>
      </div>
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
  top: 10px;
  left: 8px;
  font-size: 14px;
  padding: 0px 5px;
  color: #666;
  transition: 0.3s;
  pointer-events: none;
}

input:focus+.placeholder {
  top: -10px;
  font-size: 12px;
  color: #105e99;
  background-color: #fff;
}

input:focus {
  border-color: #105e99;
  outline: none !important;
}
</style>
