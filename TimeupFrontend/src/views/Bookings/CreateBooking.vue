<script setup>
import { ref, computed, onBeforeMount, reactive, onBeforeUpdate } from "vue";
import moment from "moment";
import Cancel from "../../components/Cancel.vue";
import Confirm from "../../components/Confirm.vue";
import { useRouter } from "vue-router";
import { userStore } from "../../stores/user.js";
import { categoryStore } from "../../stores/category.js";
import { bookStore } from "../../stores/book.js";

const appRouter = useRouter();

const userSignInRes = userStore();
const categoryRes = categoryStore();
const bookRes = bookStore();

userSignInRes.getRefreshToken().then(() => {
  if (userSignInRole.value == "admin") {
    userSignInRes.getAllUsers();
    // console.log("OASIP");
  }

  if (userSignInRole.value == "lecturer") {
    appRouter.go(-1);
  }
  if (userSignInRole.value == "student" || userSignInRole.value == "lecturer") {
    localData.bookingName = userSignInName.value;
    localData.bookingEmail = userSignInEmail.value;
    localData.userIdInSignIn = userSignInUserId.value;
  } else {
    localData.bookingEmail = "";
  }
});

categoryRes.getEventCategory();
const categoryList = computed(() => categoryRes.categorys);

// userSignInRes.getAllUsers();
const userList = computed(() => userSignInRes.userStudent);

// await bookStore.getBookings();
// const bookingLists = computed(()=> bookStore.bookings);

// const currentUserData = computed(()=> {
//     return {
//         bookingName:userSignInRes.signInUserData.userName ,
//         bookingEmail:userSignInRes.signInUserData.userEmail
//     }
// });
// const name = ref(currentUserData.value.userName);

const userSignInName = computed(() => userSignInRes.signInUserData.userName);
const userSignInEmail = computed(() => userSignInRes.signInUserData.userEmail);
const userSignInRole = computed(() => userSignInRes.signInUserData.userRole);
const userSignInUserId = computed(() => userSignInRes.signInUserData.userId);
const pageName = ref("use RESERVE");

const statusCreateUser = ref();

const bookingEmailIndexSelect = ref();
const categoryIndexSelect = ref();

const localPresentTime = moment.utc().local().format("YYYY-MM-DDTHH:mm");
const dateIndexSelect = ref(localPresentTime);

const fileSelected = ref();

let fileDetail = reactive({
  fileName: null,
  fileSize: 0,
  fileSizeUnit: "",
});

let localData = reactive({
  bookingName: "",
  bookingEmail: "",
  eventCategory: { eventCategoryId: "" },
  eventStartTime: new Date(dateIndexSelect.value).toISOString(),
  eventDuration: "",
  eventNotes: "",
  userIdInSignIn: "",
});

// const getEmail = () => {
//     if (localStorage.getItem('userEmail') != undefined || localStorage.getItem('userEmail') != null) {
//         localData.bookingEmail = localStorage.getItem('userEmail')
//     } else {
//         localData.bookingEmail = '';
//     }
// }

// const getName = () => {
//     if (localStorage.getItem('userName') != undefined || localStorage.getItem('userName') != null) {
//         localData.bookingName = localStorage.getItem('userName')
//     } else {
//         localData.bookingName = '';
//     }
// }

// const responseGetAllBooking = ref({});
// const responseGetAllCategory = ref({});

// const categoryList = ref([{ eventCategoryId: 6, eventCategoryName: 'Other', eventDuration: 30, eventColor: '#DFDADA', eventCategoryDescription: 'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย Clinic อิ่น' },
// { eventCategoryId: 5, eventCategoryName: 'Server-side Clinic', eventDuration: 30, eventColor: '#FFA0A0', eventCategoryDescription: '' },
// { eventCategoryId: 4, eventCategoryName: 'Client-side Clinic', eventDuration: 30, eventColor: '#B3F6C2', eventCategoryDescription: 'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย client-side clinic ในวิชา INT221 integrated project I' },
// { eventCategoryId: 3, eventCategoryName: 'Database Clinic', eventDuration: 15, eventColor: '#AFC8F9', eventCategoryDescription: 'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย database clinic ในวิชา INT221 integrated project I' },
// { eventCategoryId: 2, eventCategoryName: 'DevOps/Infra Clinic', eventDuration: 20, eventColor: '#FEE5A5', eventCategoryDescription: 'Use this event category for DevOps/Infra clinic.' },
// { eventCategoryId: 1, eventCategoryName: 'Project management', eventDuration: 30, eventColor: '#AAA4A4' }])

const regexEmail = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}";

let date = new Date();
date.setMonth(date.getMonth() + 3);
let maxlocalPresentTime = moment(date).format("YYYY-MM-DDTHH:mm");
let maxdateIndexSelect = ref(maxlocalPresentTime);

const isInvalid = ref(false);
const isOverlap = ref(false);
const cancelDialog = ref(false);
const createDialog = ref(false);

const handleCategorySelect = () => {
  localData.eventCategory.eventCategoryId =
    categoryList.value[categoryIndexSelect.value].eventCategoryId;
  localData.eventDuration =
    categoryList.value[categoryIndexSelect.value].eventDuration;
  // console.log(localData)
  // console.log(localData.eventCategory.eventCategoryId);
};

const handleBookingEmailSelect = () => {
  localData.bookingEmail =
    userList.value[bookingEmailIndexSelect.value].emailUser;
  localData.userIdInSignIn =
    userList.value[bookingEmailIndexSelect.value].idUser;
  // console.log(localData.bookingEmail);
  // console.log(localData.userIdInSignIn);
};

const handleTime = () => {
  localData.eventStartTime = new Date(dateIndexSelect.value).toISOString();
  statusCreateUser.value = "";
  // console.log(dateIndexSelect.value);
  // console.log(localData.eventStartTime);
  // console.log(`${moment.utc(localData.eventStartTime).add(localData.eventDuration, 'm').format("YYYY-MM-DDTHH:mm:ss")}Z`);
};
const changeCancelDialogTrue = () => {
  cancelDialog.value = true;
};
const changeCancelDialogFalse = () => {
  cancelDialog.value = false;
};
const closeConfirmDialog = () => {
  createDialog.value = false;
};

//check all input is correct to create
const changeConfirmDialog = () => {
  isInvalid.value = false;
  if (
    localData.bookingName.trim() == "" ||
    localData.bookingName.length > 100 ||
    localData.bookingEmail.trim() == "" ||
    localData.bookingEmail.length > 100 ||
    !localData.bookingEmail.match(regexEmail) ||
    categoryIndexSelect.value == undefined ||
    localData.eventNotes.length > 500 ||
    new Date(dateIndexSelect.value).toISOString() < new Date().toISOString() || 
    fileDetail.fileSize > 10 && fileDetail.fileSizeUnit == "MB"
  ) {
    isInvalid.value = true;
  } else {
    isInvalid.value = false;
  }
  if (isOverlap.value == false && isInvalid.value == false) {
    createDialog.value = true;
    isInvalid.value = false;
  }
  console.log("not overlap and isInvalid " + isInvalid.value);
};

const deleteFile = () => {
  fileSelected.value = null;
  fileDetail.fileName = null;
  fileDetail.fileSize = 0;
  fileDetail.fileSizeUnit = "";
};

const fileOnInput = ($event) => {
  console.log("1");
  console.log(fileSelected.value);
  console.log($event.target.files.length);
  console.log("before in func");
  if ($event.target.files.length != 0) {
    fileSelected.value = $event.target.files[0];
    // console.log(fileSelected.value);
    fileDetail.fileName = fileSelected.value.name;
    fileSize($event.target.files[0].size);
  }
  console.log("2");
  console.log(fileSelected.value);
  console.log($event.target.files.length);
  console.log("after in func");
  // console.log(fileSelected.value);
};

const fileSize = (sizeOfFile) => {
  // console.log(sizeOfFile);
  // console.log(Math.pow(1024, 2));
  // console.log(sizeOfFile / Math.pow(1024, 2));

  //  localData.fileSize = (sizeOfFile / Math.pow(1024, 2)).toFixed(2);
  // localData.fileSizeUnit = "KB";
  // console.log(localData.fileSize);

  // if (sizeOfFile < Math.pow(1024, 1)) {
  //   localData.fileSize = (sizeOfFile / Math.pow(1024, 1)).toFixed(2);
  //   localData.fileSizeUnit = "Bytes";

  if (sizeOfFile < Math.pow(1024, 2)) {
    fileDetail.fileSize = parseFloat(sizeOfFile / Math.pow(1024, 1));
    console.log(fileDetail.fileSize);
    fileDetail.fileSizeUnit = "KB";
  } else if (sizeOfFile < Math.pow(1024, 3)) {
    fileDetail.fileSize = parseFloat(sizeOfFile / Math.pow(1024, 2));
    console.log(fileDetail.fileSize);
    fileDetail.fileSizeUnit = "MB";
  } else {
    return;
  }
};

const IsFileSelected = computed(() => {
  return fileSelected.value != undefined ||  fileSelected.value != null;
});

const isFileSizeNotOver = computed(() => {
  return isInvalid.value && (fileDetail.fileSize > 10 && fileDetail.fileSizeUnit == "MB");
});

const isInputName = computed(() => {
  return isInvalid.value && localData.bookingName.trim() == "MB";
});
const isInputNameOver = computed(() => {
  return isInvalid.value && localData.bookingName.length > 100;
});
const isInputEmail = computed(() => {
  return isInvalid.value && localData.bookingEmail.trim() == "";
});
const isInputEmailVaild = computed(() => {
  if (
    localData.bookingEmail.trim() == "" ||
    localData.bookingEmail.trim() == null
  ) {
    return "";
  }
  if (!localData.bookingEmail.match(regexEmail)) {
    return isInvalid.value && !localData.bookingEmail.match(regexEmail);
  }
});
const isInputEmailOver = computed(() => {
  return isInvalid.value && localData.bookingEmail.length > 100;
});
const isInputCategory = computed(() => {
  return categoryIndexSelect.value == undefined;
});
const isInputNotes = computed(() => {
  return isInvalid.value && localData.eventNotes.length > 500;
});
const isInputTimeOld = computed(() => {
  return (
    isInvalid.value &&
    new Date(dateIndexSelect.value).toISOString() < new Date().toISOString()
  );
});

//check overlap
const isInputTime = computed(() => { 
  // if (userSignInRole.value == 'admin') {
  //     console.log("i am admin");
  // const localEndTime = `${moment.utc(localData.eventStartTime).add(localData.eventDuration, 'm').format("YYYY-MM-DDTHH:mm:ss")}Z`;
  // for (let booking of bookingLists.value) {
  //     if (localData.eventCategory.eventCategoryId === booking.eventCategoryId) {
  //         if ((localData.eventStartTime <= booking.eventEndTime) && (localEndTime >= booking.eventStartTime)) {
  //             isOverlap.value = true
  //             isInvalid.value = true
  //             break;
  //         } else {
  //             isOverlap.value = false
  //         }
  //     }
  // }
  // console.log("overlap value " + isOverlap.value);
  // return (isInvalid.value && isOverlap.value)
  // } else if (userSignInRole.value == 'student') {
  //     console.log("i am student");
  // } else if (userSignInRole.value == 'lecturer') {
  //     console.log("i am lecturer");
  // }
});

const reset = () => {
  // localData.bookingName = ""
  // localData.bookingEmail = ""
  // getName();
  // getEmail();
  if (
    userSignInRes.signInUserData.userRole == "student" ||
    userSignInRes.signInUserData.userRole == "lecturer"
  ) {
    localData.bookingName = userSignInName.value;
    localData.bookingEmail = userSignInEmail.value;
    categoryIndexSelect.value = undefined;
    localData.eventDuration = "";
    dateIndexSelect.value = localPresentTime;
    localData.eventNotes = "";
    cancelDialog.value = !cancelDialog.value;
    deleteFile();
  } else if (userSignInRes.signInUserData.userRole == "admin") {
    localData.bookingName = "";
    bookingEmailIndexSelect.value = undefined;
    categoryIndexSelect.value = undefined;
    localData.eventDuration = "";
    dateIndexSelect.value = localPresentTime;
    localData.eventNotes = "";
    cancelDialog.value = !cancelDialog.value;
    deleteFile();
  } else {
    localData.bookingName = "";
    localData.bookingEmail = "";
    categoryIndexSelect.value = undefined;
    localData.eventDuration = "";
    dateIndexSelect.value = localPresentTime;
    localData.eventNotes = "";
    cancelDialog.value = !cancelDialog.value;
    deleteFile();
  }
};

//create new booking event
const createBookingEvent = async (localDataInput, fileSelected) => {
  // if(userSignInRes.signInUserData.userRole == 'admin'){
  //     localData.bookingEmail.trim();
  //     for(let user of userList.value){
  //         if(user.emailUser == localData.bookingEmail){
  //             localData.userIdInSignIn = user.idUser
  //         }
  //         console.log(user.emailUser);
  //     }
  // }

  if(fileSelected == undefined || fileSelected == ""){
    fileSelected = null;
  } 
  const res = await bookRes.createBooking(localDataInput, fileSelected);
  if (res.status === 201) {
    alert(
      `Create successfully \n Category ID :  ${localData.eventCategory.eventCategoryId
      } \n CategoryName : ${categoryList.value[categoryIndexSelect.value].eventCategoryName
      } \n Date : ${localData.eventStartTime} \n Booking name :  ${localData.bookingName
      }`
    );
    console.log(
      `Create successfully \n Category ID :  ${localDataInput.eventCategory.eventCategoryId} \n Date : ${localDataInput.eventStartTime} \n Booking name :  ${localDataInput.bookingName}`
    );
    // localDataInput.bookingName = ""
    // localDataInput.bookingEmail = ""
    // getName();
    // getEmail();
    if (
      userSignInRes.signInUserData.userRole == "student" ||
      userSignInRes.signInUserData.userRole == "lecturer"
    ) {
      localData.bookingName = userSignInName.value;
      localData.bookingEmail = userSignInEmail.value;
      categoryIndexSelect.value = undefined;
      localData.eventDuration = "";
      dateIndexSelect.value = localPresentTime;
      localData.eventNotes = "";
      deleteFile();
    } else if (userSignInRes.signInUserData.userRole == "admin") {
      localData.bookingName = "";
      bookingEmailIndexSelect.value = undefined;
      categoryIndexSelect.value = undefined;
      localData.eventDuration = "";
      dateIndexSelect.value = localPresentTime;
      localData.eventNotes = "";
      deleteFile();
    } else {
      localData.bookingName = "";
      localData.bookingEmail = "";
      categoryIndexSelect.value = undefined;
      localData.eventDuration = "";
      dateIndexSelect.value = localPresentTime;
      localData.eventNotes = "";
      deleteFile();
    }
  } else {
    statusCreateUser.value = res.status;
    console.log(statusCreateUser.value);
    console.log("error , failed to created");
  }
  // createDialog.value = !createDialog.value
  createDialog.value = false;
};

//fetch data
onBeforeMount(async () => {
  //     getEmail();
  //     getName();
  //     responseGetAllBooking.value = await bookStgetBookings();
  //     bookingLists.value = await responseGetAllBooking.value.data;
  //     responseGetAllCategory.value = await getEventCategory();
  //     categoryList.value = await responseGetAllCategory.value.data;
});
</script>

<template>
  <div>
    <div
      class="mt-24 mb-8 uppercase w-3/4 m-auto text-center text-4xl font-bold text-black underline decoration-[#50ABCB]">
      select scheduled
    </div>
    <div class="w-[80%] m-auto h-auto mb-24">
      <div class="bg-white text-xl rounded-xl font-bold">
        <div class="m-auto w-1/2 text-center pt-5">Enter Your Details</div>
        <div class="grid grid-flow-row">
          <div class="grid grid-flow-row grid-cols-9 px-5 pb-3 gap-3">
            <div class="row-start-1 col-start-1 col-span-3">
              Scheduled Category :
            </div>
            <div class="row-start-2 col-start-1 col-end-4 col-span-3">
              <select class="bg-gray-200 rounded w-full border" v-model="categoryIndexSelect"
                :style="{ 'border-color': isInputCategory ? 'red' : 'white' }" @change="handleCategorySelect()">
                <option v-for="(res, indexs) in categoryList" :value="indexs" v-bind:key="indexs">
                  {{ res.eventCategoryName }}
                </option>
              </select>
              <label class="text-red-500" v-if="isInputCategory">
                *Please choose category
              </label>
            </div>
            <div class="row-start-1 col-start-7 col-span-1">Name :</div>
            <div class="row-start-1 col-start-9 col-span-1 text-right text-gray-400">
              {{ localData.bookingName.length }}/100
            </div>
            <div class="row-start-2 col-start-7 col-end-10 col-span-3">
              <input class="bg-gray-200 rounded w-full pb-0.5 pl-1 border disabled:text-gray-400" type="text"
                v-model="localData.bookingName" :style="{
                  'border-color':
                    isInputName || isInputNameOver ? 'red' : 'white',
                }" />
              <label class="text-red-500" v-if="isInputName">
                *Please enter name
              </label>
              <label class="text-red-500" v-if="isInputNameOver">
                *over limit of input name
              </label>
            </div>
            <div class="row-start-3 col-start-1 col-span-1">Time :</div>
            <div class="row-start-4 col-start-1 col-end-4 col-span-3">
              <form action="">
                <input class="bg-gray-200 rounded w-full border" :max="maxdateIndexSelect" :min="localPresentTime"
                  @change="handleTime()" type="datetime-local" :style="{
                    'border-color':
                      isInputTime || isInputTimeOld || statusCreateUser == 400
                        ? 'red'
                        : 'white',
                  }" v-model="dateIndexSelect" />
              </form>
              <label class="text-red-500" v-if="isInputTime || statusCreateUser == 400">
                *overlap
              </label>
              <label class="text-red-500" v-if="isInputTimeOld">
                *Please select time is future than present
              </label>
            </div>

            <div class="row-start-3 col-start-4 col-span-1 text-center">
              Duration
            </div>
            <div class="row-start-4 col-start-4 col-span-1">
              <input class="bg-gray-300 rounded w-full pb-0.5 pl-1 text-center" readonly type="text"
                v-model="localData.eventDuration" />
            </div>
            <div class="row-start-3 col-start-7 col-span-1">Email :</div>
            <div class="row-start-3 col-start-9 col-span-1 text-right text-gray-400">
              {{ localData.bookingEmail.length }}/100
            </div>

            <div class="row-start-4 col-start-7 col-end-10 col-span-3">
              <!-- <input class="bg-gray-200 rounded w-full pb-0.5 pl-1 border disabled:text-gray-400"
                                type="email" v-model="localData.bookingEmail" /> -->

              <select v-if="userSignInRole == 'admin'" v-model="bookingEmailIndexSelect"
                @change="handleBookingEmailSelect()"
                class="bg-gray-200 rounded-md w-full pb-0.5 pl-1 border border-solid disabled:text-gray-400 border-[#D9D9D9] hover:bg-[#F2F2F2] transition delay-75 text-center"
                name="" id="">
                <!-- <option value="" selected hidden>role</option> -->
                <option v-for="(user, index) in userList" :key="index" :value="index">
                  {{ user.emailUser }}
                </option>
              </select>

              <!--                     <select class="bg-gray-200 rounded w-full border" v-model="categoryIndexSelect"
                                :style="{ 'border-color': isInputCategory ? 'red' : 'white' }"
                                @change="handleCategorySelect()">
                                <option v-for="(res, index) in categoryList" :value="index" v-bind:key="index">{{
                                res.eventCategoryName
                                }}</option>
                            </select> -->

              <input v-else :disabled="
                userSignInRole == 'student' || userSignInRole == 'lecturer'
              " class="bg-gray-200 rounded w-full pb-0.5 pl-1 border disabled:text-gray-400" type="email"
                v-model="localData.bookingEmail" :style="{
                  'border-color':
                    isInputEmail || isInputEmailOver || isInputEmailVaild
                      ? 'red'
                      : 'white',
                }" />

              <label class="text-red-500" v-if="isInputEmail">
                *Please enter email
              </label>
              <label class="text-red-500" v-if="isInputEmailOver">
                *over limit of input email
              </label>
              <label class="text-red-500" v-if="isInputEmailVaild">
                *input email is invalid
              </label>
            </div>
            <div class="row-start-5 col-start-1 col-span-1">Notes :</div>
            <div class="row-start-5 col-start-9 col-span-1 text-right text-gray-400">
              {{ localData.eventNotes.length }}/500
            </div>
            <div class="row-start-6 col-start-1 col-end-10 span-9">
              <textarea class="bg-gray-200 w-full resize-none rounded border" name="" id="" cols="100" rows="5"
                placeholder="enter your message(limit 500 text)" v-model="localData.eventNotes"
                :style="{ 'border-color': isInputNotes ? 'red' : 'white' }"></textarea>
              <label class="text-red-500" v-if="isInputNotes">
                *over limit of input message
              </label>
            </div>

            <div class="row-start-7 col-start-1 col-span-1">Upload File :</div>
            <div class="grid row-start-8 col-start-1 col-end-2 col-span-1 content-center">
              <input id="file1" name="file1" class="hidden bg-gray-200 rounded w-[100%] border disabled:text-gray-400"
                type="file" @change="fileOnInput" />
              <label for="file1" class="p-1 bg-gray-200 rounded border text-center">file choose</label>
            </div>

            <div class="grid row-start-8 col-start-2 col-end-4 col-span-2 w-[95%] content-center">
              <p class="truncate text-center">{{ fileDetail.fileName }}</p>
            </div>

            <div v-if="isFileSizeNotOver" class="grid row-start-9 col-start-1 col-span-5">
              <label class="text-red-500">
                *The file size cannot be be larger than 10 MB
              </label>
            </div>

            <div class="grid row-start-8 col-start-4 col-end-5 col-span-1 content-center">
              <p class="text-left h-full">
                {{
                    fileDetail.fileSize == 0 ? "" : fileDetail.fileSize.toFixed(2)
                }}
                {{ fileDetail.fileSizeUnit }}
              </p>
            </div>

            <div v-if="IsFileSelected" class="grid row-start-8 col-start-5 col-end-6 col-span-1 justify-items-start">
              <button @click="deleteFile" class="w-[50%] py-1 bg-[#F24052] text-white rounded-lg">
                <svg class="hover:text-[#F24052] m-auto" width="1.5em" height="1.5em" viewBox="0 0 24 24">
                  <path fill="currentColor"
                    d="M6 19a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V7H6v12m2.46-7.12l1.41-1.41L12 12.59l2.12-2.12l1.41 1.41L13.41 14l2.12 2.12l-1.41 1.41L12 15.41l-2.12 2.12l-1.41-1.41L10.59 14l-2.13-2.12M15.5 4l-1-1h-5l-1 1H5v2h14V4h-3.5Z">
                  </path>
                </svg>
              </button>
            </div>
            <!-- <div class="row-start-7 col-start-8 col-span-1">
              <button @click="changeCancelDialogTrue"
                class="w-full h-full m-auto py-2 bg-[#F24052] text-white rounded-lg">
                cancel
              </button>
            </div>

            <div class="row-start-7 col-start-9 col-span-1">
              <button @click="changeConfirmDialog" class="w-full h-full m-auto bg-[#00A28B] text-white rounded-lg">
                create
              </button>
            </div>
            <div class="row-start-8 col-start-8 py-2 col-span-2 text-red-500 text-center" v-show="
              isInputName ||
              isInputNameOver ||
              isInputEmail ||
              isInputEmailVaild ||
              isInputEmailOver ||
              isInputCategory ||
              isInputNotes ||
              isInputTime ||
              isInputTimeOld
            ">
              *some input is invalid
            </div> -->
          </div>
          <div class="grid grid-flow-row grid-cols-9 px-5 pb-3 gap-3">
            <div class="row-start-1 col-start-8 col-span-1">
              <button @click="changeCancelDialogTrue"
                class="w-full h-full m-auto py-2 bg-[#F24052] text-white rounded-lg">
                cancel
              </button>
            </div>

            <div class="row-start-1 col-start-9 col-span-1">
              <button @click="changeConfirmDialog" class="w-full h-full m-auto bg-[#00A28B] text-white rounded-lg">
                create
              </button>
            </div>
            <div class="row-start-2 col-start-8 py-2 col-span-2 text-red-500 text-center" v-show="
              isInputName ||
              isInputNameOver ||
              isInputEmail ||
              isInputEmailVaild ||
              isInputEmailOver ||
              isInputCategory ||
              isInputNotes ||
              isInputTime ||
              isInputTimeOld ||
              isFileSizeNotOver
            ">
              *some input is invalid
            </div>
          </div>
        </div>
        <Cancel v-if="cancelDialog" @onClickCancelNo="changeCancelDialogFalse" @onClickCancelYes="reset" />

        <Confirm v-if="createDialog" @onClickConfirmNo="closeConfirmDialog"
          @onClickConfirmYes="createBookingEvent(localData, fileSelected)" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.row-start-8 {
  grid-row-start: 8;
}
</style>
