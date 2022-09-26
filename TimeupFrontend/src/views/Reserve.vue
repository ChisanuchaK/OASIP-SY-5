<script setup>
import { ref, computed, onBeforeMount, reactive } from 'vue'
import moment from "moment";
import Cancel from "../components/Cancel.vue";
import Confirm from "../components/Confirm.vue";
import { getEventCategory, createBooking, getBookings } from "../stores/book.js";
import PleaseLogInDialog from '../components/PleaseLogInDialog.vue';
import NavbarTop from '../components/NavbarTop.vue';
import NavbarBottom from '../components/NavbarBottom.vue';

const getToken = localStorage.getItem('refreshToken');
const emailUserFromLogin = computed(() => localStorage.getItem('userEmail'));
const nameUserFromLogin = computed(() => localStorage.getItem('userName'));
const roleUserFromLogin = computed(() => localStorage.getItem('userRole'));
const pageName = ref('use RESERVE');

const statusCreateUser = ref();

const getEmail = () => {
    if (localStorage.getItem('userEmail') != undefined || localStorage.getItem('userEmail') != null) {
        localData.bookingEmail = localStorage.getItem('userEmail')
    } else {
        localData.bookingEmail = '';
    }
}

const getName = () => {
    if (localStorage.getItem('userName') != undefined || localStorage.getItem('userName') != null) {
        localData.bookingName = localStorage.getItem('userName')
    } else {
        localData.bookingName = '';
    }
}

const responseGetAllBooking = ref({});
const responseGetAllCategory = ref({});
const bookingLists = ref([]);
const categoryList = ref([]);
// const categoryList = ref([{ eventCategoryId: 6, eventCategoryName: 'Other', eventDuration: 30, eventColor: '#DFDADA', eventCategoryDescription: 'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย Clinic อิ่น' },
// { eventCategoryId: 5, eventCategoryName: 'Server-side Clinic', eventDuration: 30, eventColor: '#FFA0A0', eventCategoryDescription: '' },
// { eventCategoryId: 4, eventCategoryName: 'Client-side Clinic', eventDuration: 30, eventColor: '#B3F6C2', eventCategoryDescription: 'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย client-side clinic ในวิชา INT221 integrated project I' },
// { eventCategoryId: 3, eventCategoryName: 'Database Clinic', eventDuration: 15, eventColor: '#AFC8F9', eventCategoryDescription: 'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย database clinic ในวิชา INT221 integrated project I' },
// { eventCategoryId: 2, eventCategoryName: 'DevOps/Infra Clinic', eventDuration: 20, eventColor: '#FEE5A5', eventCategoryDescription: 'Use this event category for DevOps/Infra clinic.' },
// { eventCategoryId: 1, eventCategoryName: 'Project management', eventDuration: 30, eventColor: '#AAA4A4' }])

const categoryIndexSelect = ref();

const localPresentTime = moment.utc().local().format("YYYY-MM-DDTHH:mm");
const dateIndexSelect = ref(localPresentTime);
const regexEmail = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}";

let localData = reactive({
    bookingName: '',
    bookingEmail: '',
    eventCategory: { eventCategoryId: "" },
    eventStartTime: new Date(dateIndexSelect.value).toISOString(),
    eventDuration: "",
    eventNotes: "",
});

let date = new Date();
date.setMonth(date.getMonth() + 3);
let maxlocalPresentTime = moment(date).format("YYYY-MM-DDTHH:mm");
let maxdateIndexSelect = ref(maxlocalPresentTime);

const isInvalid = ref(false);
const isOverlap = ref(false);
const cancelDialog = ref(false);
const createDialog = ref(false);

const handleSelect = () => {
    localData.eventCategory.eventCategoryId = categoryList.value[categoryIndexSelect.value].eventCategoryId
    localData.eventDuration = categoryList.value[categoryIndexSelect.value].eventDuration
    // console.log(localData)
    console.log(localData.eventCategory.eventCategoryId);
}
const handleTime = () => {
    localData.eventStartTime = new Date(dateIndexSelect.value).toISOString()
    // console.log(dateIndexSelect.value);
    // console.log(localData.eventStartTime);
    // console.log(`${moment.utc(localData.eventStartTime).add(localData.eventDuration, 'm').format("YYYY-MM-DDTHH:mm:ss")}Z`);
}
const changeCancelDialogTrue = () => {
    cancelDialog.value = true
}
const changeCancelDialogFalse = () => {
    cancelDialog.value = false
}
const closeConfirmDialog = () => {
    createDialog.value = false
}

//check all input is correct to create
const changeConfirmDialog = () => {
    isInvalid.value = false
    if ((localData.bookingName.trim() == "" || localData.bookingName.length > 100)
        || (localData.bookingEmail.trim() == "" || localData.bookingEmail.length > 100)
        || (!(localData.bookingEmail.match(regexEmail)))
        || categoryIndexSelect.value == undefined || (localData.eventNotes.length > 500)
        || (new Date(dateIndexSelect.value).toISOString() < new Date().toISOString())) {
        isInvalid.value = true
    } else {
        isInvalid.value = false
    }
    if (isOverlap.value == false && isInvalid.value == false) {
        createDialog.value = true
        isInvalid.value = false
    }
    console.log("not overlap and isInvalid " + isInvalid.value);
}

const isInputName = computed(() => {
    return (isInvalid.value && localData.bookingName.trim() == "");
});
const isInputNameOver = computed(() => {
    return (isInvalid.value && localData.bookingName.length > 100);
});
const isInputEmail = computed(() => {
    return isInvalid.value && localData.bookingEmail.trim() == ""
});
const isInputEmailVaild = computed(() => {
    if (localData.bookingEmail.trim() == "" || localData.bookingEmail.trim() == null) {
        return '';
    }
    if (!(localData.bookingEmail.match(regexEmail))) {
        return isInvalid.value && (!(localData.bookingEmail.match(regexEmail)));
    }
});
const isInputEmailOver = computed(() => {
    return isInvalid.value && localData.bookingEmail.length > 100;
});
const isInputCategory = computed(() => {
    return ((categoryIndexSelect.value == undefined));
});
const isInputNotes = computed(() => {
    return (isInvalid.value && localData.eventNotes.length > 500);
});
const isInputTimeOld = computed(() => {
    return (isInvalid.value && (new Date(dateIndexSelect.value).toISOString() < new Date().toISOString()));
});

//check overlap 
const isInputTime = computed(() => {
    if (roleUserFromLogin.value == 'admin') {
        console.log("i am admin");
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
    } else if (roleUserFromLogin.value == 'student') {
        console.log("i am student");
    } else if (roleUserFromLogin.value == 'lecturer') {
        console.log("i am lecturer");
    }
});

const reset = () => {
    // localData.bookingName = ""
    // localData.bookingEmail = ""
    getName();
    getEmail();
    categoryIndexSelect.value = undefined
    localData.eventDuration = ""
    dateIndexSelect.value = localPresentTime
    localData.eventNotes = ""
    cancelDialog.value = !cancelDialog.value
}

//create new booking event
const createBookingEvent = async (localDataInput) => {
    const res = await createBooking(localDataInput);
    if (res.status === 201) {
        alert(`Create successfully \n Category ID :  ${localData.eventCategory.eventCategoryId} \n CategoryName : ${categoryList.value[categoryIndexSelect.value].eventCategoryName} \n Date : ${localData.eventStartTime} \n Booking name :  ${localData.bookingName}`)
        console.log(`Create successfully \n Category ID :  ${localDataInput.eventCategory.eventCategoryId} \n Date : ${localDataInput.eventStartTime} \n Booking name :  ${localDataInput.bookingName}`)
        // localDataInput.bookingName = ""
        // localDataInput.bookingEmail = ""
        getName();
        getEmail();
        categoryIndexSelect.value = undefined
        localDataInput.eventDuration = ""
        dateIndexSelect.value = localPresentTime
        localDataInput.eventNotes = ""
    } else {
        statusCreateUser.value = res.status;
        console.log(statusCreateUser.value);
        console.log("error , failed to created");
    }
    // createDialog.value = !createDialog.value
    createDialog.value = false
};

//fetch data
onBeforeMount(async () => {
    getEmail();
    getName();
    responseGetAllBooking.value = await getBookings();
    bookingLists.value = await responseGetAllBooking.value.data;
    responseGetAllCategory.value = await getEventCategory();
    categoryList.value = await responseGetAllCategory.value.data;
})

</script>
 
<template>
    <div>
        <NavbarTop />
        <NavbarBottom />
        <div>
            <div
                class="mt-24 mb-8 uppercase w-3/4 m-auto text-center text-4xl font-bold text-black underline decoration-[#50ABCB]">
                select scheduled</div>
            <div class="w-[80%] m-auto h-auto mb-24">
                <div class="bg-white text-xl rounded-xl font-bold">
                    <div class="m-auto w-1/2 text-center pt-5">Enter Your Details
                    </div>
                    <div class="grid grid-flow-row grid-cols-9 p-5 gap-3">
                        <div class="row-start-1 col-start-1 col-span-3  ">Scheduled Category :</div>
                        <div class="row-start-2 col-start-1 col-end-4 col-span-3">
                            <select class="bg-gray-200 rounded w-full border" v-model="categoryIndexSelect"
                                :style="{ 'border-color': isInputCategory ? 'red' : 'white' }" @change="handleSelect()">
                                <option v-for="(res, indexs) in categoryList" :value="indexs" v-bind:key="indexs">{{
                                res.eventCategoryName
                                }}</option>
                            </select>
                            <label class="text-red-500" v-if="isInputCategory">
                                *Please choose category
                            </label>
                        </div>
                        <div class="row-start-1 col-start-7 col-span-1 ">
                            Name :
                        </div>
                        <div class="row-start-1 col-start-9 col-span-1 text-right text-gray-400">
                            {{ localData.bookingName.length }}/100
                            <!-- {{ nameUserFromLogin.length }}/100 -->
                        </div>
                        <div class="row-start-2 col-start-7 col-end-10 col-span-3 "><input :disabled="getToken"
                                class="bg-gray-200 rounded w-full pb-0.5 pl-1 border disabled:text-gray-400" type="text"
                                v-model="localData.bookingName"
                                :style="{ 'border-color': (isInputName || isInputNameOver) ? 'red' : 'white' }" />
                            <label class="text-red-500" v-if="isInputName">
                                *Please enter name
                            </label>
                            <label class="text-red-500" v-if="isInputNameOver">
                                *over limit of input name
                            </label>
                        </div>
                        <div class="row-start-3 col-start-1 col-span-1 ">Time :</div>
                        <div class="row-start-4 col-start-1 col-end-4 col-span-3 ">
                            <form action=""> <input class="bg-gray-200 rounded w-full border" :max="maxdateIndexSelect"
                                    :min="localPresentTime" @change="handleTime()" type="datetime-local"
                                    :style="{ 'border-color': isInputTime || isInputTimeOld || (statusCreateUser == 400) ? 'red' : 'white' }"
                                    v-model="dateIndexSelect" />
                            </form>
                            <label class="text-red-500" v-if="isInputTime || (statusCreateUser == 400)">
                                *overlap
                            </label>
                            <label class="text-red-500" v-if="isInputTimeOld">
                                *Please select time is future than present
                            </label>
                        </div>

                        <div class="row-start-3 col-start-4 col-span-1 text-center">Duration</div>
                        <div class="row-start-4 col-start-4 col-span-1 "><input
                                class="bg-gray-300 rounded w-full pb-0.5 pl-1 text-center" readonly type="text"
                                v-model="localData.eventDuration" /></div>
                        <div class="row-start-3 col-start-7 col-span-1 ">Email :</div>
                        <div class="row-start-3 col-start-9 col-span-1 text-right text-gray-400">
                            {{ localData.bookingEmail.length }}/100
                            <!-- {{ emailUserFromLogin.length }}/100 -->
                        </div>

                        <div class="row-start-4 col-start-7 col-end-10 col-span-3 "><input :disabled="getToken"
                                class="bg-gray-200 rounded w-full pb-0.5 pl-1 border disabled:text-gray-400"
                                type="email" v-model="localData.bookingEmail"
                                :style="{ 'border-color': (isInputEmail || isInputEmailOver || isInputEmailVaild) ? 'red' : 'white' }" />
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
                        <div class="row-start-5 col-start-1 col-span-1 ">Notes :</div>
                        <div class="row-start-5 col-start-9 col-span-1 text-right text-gray-400">
                            {{ localData.eventNotes.length }}/500
                        </div>
                        <div class="row-start-6 col-start-1 col-end-10 span-9"> <textarea
                                class="bg-gray-200 w-full resize-none rounded border" name="" id="" cols="100" rows="5"
                                placeholder="enter your message(limit 500 text)" v-model="localData.eventNotes"
                                :style="{ 'border-color': isInputNotes ? 'red' : 'white' }"></textarea>
                            <label class="text-red-500" v-if="isInputNotes">
                                *over limit of input message
                            </label>
                        </div>

                        <div class="row-start-7 col-start-8 col-span-1"> <button @click="changeCancelDialogTrue"
                                class="w-full h-full m-auto py-2  bg-[#F24052] text-white rounded-lg">
                                cancel </button> </div>


                        <div class="row-start-7 col-start-9 col-span-1"> <button @click="changeConfirmDialog"
                                class="w-full h-full m-auto  bg-[#00A28B] text-white rounded-lg">
                                create </button>
                        </div>
                        <div class="row-start-8 col-start-8 py-2 col-span-2 text-red-500 text-center" v-show="isInputName || isInputNameOver || isInputEmail || isInputEmailVaild || isInputEmailOver
                        || isInputCategory || isInputNotes || isInputTime || isInputTimeOld">
                            *some input is invalid
                        </div>

                    </div>
                    <Cancel v-if="cancelDialog" @onClickCancelNo="changeCancelDialogFalse" @onClickCancelYes="reset" />

                    <Confirm v-if="createDialog" @onClickConfirmNo="closeConfirmDialog"
                        @onClickConfirmYes="createBookingEvent(localData)" />
                </div>
                <!-- <PleaseLogInDialog v-if="!getToken" :pageName="pageName" /> -->
            </div>
        </div>
    </div>
</template>
 
<style>

</style>
