<script setup>
import { ref, computed, onBeforeMount, reactive } from 'vue'
import moment from "moment";
import NavbarTop from '../components/NavbarTop.vue';
import NavbarBottom from '../components/NavbarBottom.vue';
import { getEventCategory } from "../stores/book.js";
import { getBookings } from "../stores/book.js";
import Cancel from "../components/Cancel.vue";
import Confirm from "../components/Confirm.vue";

const localPresentTime = moment.utc().local().format("YYYY-MM-DDTHH:mm");
const bookingLists = ref([]);
// const books = computed(() => bookingLists.value);

const categoryList = ref([]);
const categoryIndexSelect = ref();
const dateIndexSelect = ref(localPresentTime);

let localData = reactive({
    bookingName: "",
    bookingEmail: "",
    eventCategory: { eventCategoryId: "" },
    eventStartTime: new Date(dateIndexSelect.value).toISOString(),
    eventDuration: "",
    eventNotes: "",
});

let date = new Date();
console.log(date);
date.setMonth(date.getMonth() + 3);
console.log(date);
let maxlocalPresentTime = moment(date).format("YYYY-MM-DDTHH:mm");
let maxdateIndexSelect = ref(maxlocalPresentTime);
const regexEmail = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}";

let isInvalid = ref(false);
let isOverlap = ref(false);

const cancelDialog = ref(false)
const createDialog = ref(false)

// console.log(bookingListSome.value);

// const localEndTime = moment.utc(localData.eventStartTime).add(localData.eventDuration, 'm').format("YYYY-MM-DDTHH:mm:ss")
// console.log(localEndTime);

// console.log(moment.utc().local().format("YYYY-MM-DDTHH:mm"));

const handleSelect = () => {
    localData.eventCategory.eventCategoryId = categoryList.value[categoryIndexSelect.value].eventCategoryId
    localData.eventDuration = categoryList.value[categoryIndexSelect.value].eventDuration
    // alert(CategorySelect)
    console.log(localData)
    console.log(categoryIndexSelect.value);
}

const handleTime = () => {
    // localData.eventStartTime = new Date().toISOString()
    // localData.eventStartTime = moment.utc(dateIndexSelect.value).format()
    localData.eventStartTime = new Date(dateIndexSelect.value).toISOString()
    console.log(dateIndexSelect.value);
    console.log(localData.eventStartTime);
    console.log(`${moment.utc(localData.eventStartTime).add(localData.eventDuration, 'm').format("YYYY-MM-DDTHH:mm:ss")}Z`);
}
onBeforeMount(async () => {
    const getAllBooks = await getBookings();
    bookingLists.value = await getAllBooks.json();
    const getAllCategory = await getEventCategory();
    categoryList.value = await getAllCategory.json();
})
const createBooking = async (localDataInput) => {
    // export const createBooking = async (localData) => {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/booking`, {
        method: "POST",
        headers: {
            "content-type": "application/json",
        },
        body: JSON.stringify({
            bookingName: localDataInput.bookingName,
            bookingEmail: localDataInput.bookingEmail,
            eventCategory: {
                eventCategoryId: localDataInput.eventCategory.eventCategoryId,
            },
            eventStartTime: localDataInput.eventStartTime,
            // eventDuration: localDataInput.eventDuration,
            eventNotes: localDataInput.eventNotes,
        }),
    });
    if (res.status === 201) {
        alert(`Create successfully \n Category ID :  ${localData.eventCategory.eventCategoryId} \n Date : ${localData.eventStartTime} \n Booking name :  ${localData.bookingName}`)
        console.log(`Create successfully \n Category ID :  ${localDataInput.eventCategory.eventCategoryId} \n Date : ${localDataInput.eventStartTime} \n Booking name :  ${localDataInput.bookingName}`)
        localDataInput.bookingName = ""
        localDataInput.bookingEmail = ""
        categoryIndexSelect.value = undefined
        localDataInput.eventDuration = ""
        dateIndexSelect.value = localPresentTime
        localDataInput.eventNotes = ""
    } else {
        console.log("error , failed to created");
    }
    createDialog.value = !createDialog.value
};

const changeCancelDialogTrue = () => {
    cancelDialog.value = true
}

const changeCancelDialogFalse = () => {
    cancelDialog.value = false
}


const checkOverLap = () => {
    const localEndTime = `${moment.utc(localData.eventStartTime).add(localData.eventDuration, 'm').format("YYYY-MM-DDTHH:mm:ss")}Z`;
    console.log("------------------------------------------------------------------");
    for (let booking of bookingLists.value) {
        if (localData.eventCategory.eventCategoryId === booking.eventCategoryId) {
            if (((localData.eventStartTime >= booking.eventStartTime) && (localData.eventStartTime <= booking.eventEndTime))
                || ((localEndTime >= booking.eventStartTime) && (localEndTime <= booking.eventEndTime))
                || (localData.eventStartTime <= booking.eventStartTime) && ((localEndTime) >= booking.eventEndTime)
            ) {
                // isOverlap.value
                // console.log(isOverlap.value);
                // console.log(booking);

                // console.log("local start " + localData.eventStartTime)
                // console.log(localEndTime);
                // console.log("book start " + booking.eventStartTime)
                // console.log("book end" + booking.eventEndTime)
                console.log("overlap")
            } else {

                // console.log("local start " + localData.eventStartTime)
                // console.log("local end " + localEndTime);
                // console.log("book start " + booking.eventStartTime)
                // console.log("book end" + booking.eventEndTime)
                // console.log(booking);
                console.log("not overlap")
            }
        }
    }
}

const reset = () => {
    localData.bookingName = ""
    localData.bookingEmail = ""
    categoryIndexSelect.value = undefined
    localData.eventDuration = ""
    dateIndexSelect.value = localPresentTime
    localData.eventNotes = ""
    cancelDialog.value = !cancelDialog.value
}

const closeConfirmDialog = () => {
    createDialog.value = false
}

const changeConfirmDialog = () => {
    // const localEndTime = `${moment.utc(localData.eventStartTime).add(localData.eventDuration, 'm').format("YYYY-MM-DDTHH:mm:ss")}Z`;
    // console.log("------------------------------------------------------------------");
    isInvalid.value = false
    if ((localData.bookingName.trim() == "" || localData.bookingName.length > 100)
        || (localData.bookingEmail.trim() == "" || localData.bookingEmail.length > 100)
        || (!(localData.bookingEmail.match(regexEmail)))
        || categoryIndexSelect.value == undefined || (localData.eventNotes.length > 500)
        || (new Date(dateIndexSelect.value).toISOString() < new Date().toISOString())) {
        console.log("first if");
        isInvalid.value = true
    } else {
        isInvalid.value = false
        console.log("first else");
    }
    if (isOverlap.value == false && isInvalid.value == false) {
        createDialog.value = true
        isInvalid.value = false
        console.log("second if");
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
    // var validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
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

const isInputTime = computed(() => {
    const localEndTime = `${moment.utc(localData.eventStartTime).add(localData.eventDuration, 'm').format("YYYY-MM-DDTHH:mm:ss")}Z`;
    // console.log("------------------------------------------------------------------");
    for (let booking of bookingLists.value) {
        if (localData.eventCategory.eventCategoryId === booking.eventCategoryId) {
            // if (((localData.eventStartTime >= booking.eventStartTime) && (localData.eventStartTime <= booking.eventEndTime))
            //     || ((localEndTime >= booking.eventStartTime) && (localEndTime <= booking.eventEndTime))
            //     || (localData.eventStartTime <= booking.eventStartTime) && ((localEndTime) >= booking.eventEndTime)
            //     // || (new Date(dateIndexSelect.value).toISOString() < new Date().toISOString())
            // ) {
            if ((localData.eventStartTime < booking.eventEndTime) && (localEndTime > booking.eventStartTime)) {
                isOverlap.value = true
                isInvalid.value = true
                // console.log("local start " + localData.eventStartTime)
                // console.log("local end " + localEndTime);
                // console.log("book start " + booking.eventStartTime)
                // console.log("book end " + booking.eventEndTime)
                console.log("overlap")
                break;
            } else {
                isOverlap.value = false
                // console.log("local start " + localData.eventStartTime)
                // console.log("local end " + localEndTime);
                // console.log("book start " + booking.eventStartTime)
                // console.log("book end " + booking.eventEndTime)
                console.log("not overlap")
            }
        }

    }
    console.log("overlap value " + isOverlap.value);
    return (isInvalid.value && isOverlap.value)
});

</script>
 
<template>
    <div>
        <NavbarTop />

        <div class="mt-24 mb-8 uppercase w-3/4 m-auto text-center text-4xl font-bold text-white"> select scheduled</div>
        <div class="w-[80%] m-auto h-auto mb-24">
            <div class="bg-white text-xl rounded-xl font-bold">
                <div class="m-auto w-1/2 text-center pt-5">Enter Your Details
                    <!-- <button @click="checkOverLap"> aaa </button>
                    <button @click="clickCheck"> sssssssssssssssssss </button> -->
                </div>
                <div class="grid grid-flow-row grid-cols-9 p-5 gap-3">
                    <div class="row-start-1 col-start-1 col-span-3 ">Scheduled Category :</div>
                    <div class="row-start-2 col-start-1 col-end-4 col-span-3">
                        <!-- <select class="bg-gray-200 rounded w-full" v-model="CategorySelect" @change="hanleSelcet()"> -->
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
                        <!-- <div v-if="">Name is null</div>  -->
                        Name :
                    </div>
                    <div class="row-start-1 col-start-9 col-span-1 text-right text-gray-400">
                        {{ localData.bookingName.length }}/100
                    </div>
                    <div class="row-start-2 col-start-7 col-end-10 col-span-3 "><input
                            class="bg-gray-200 rounded w-full pb-0.5 pl-1 border" type="text"
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
                                :style="{ 'border-color': isInputTime || isInputTimeOld ? 'red' : 'white' }"
                                v-model="dateIndexSelect" />
                        </form>
                        <label class="text-red-500" v-if="isInputTime">
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
                    </div>

                    <div class="row-start-4 col-start-7 col-end-10 col-span-3 "><input
                            class="bg-gray-200 rounded w-full pb-0.5 pl-1 border" type="email"
                            v-model="localData.bookingEmail"
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
                    <div class="row-start-7 col-start-8 col-span-1"> <button @click="changeConfirmDialog"
                            class="w-full h-full m-auto  bg-[#499D6B] text-white rounded-lg">
                            create </button>
                    </div>
                    <div class="row-start-8 col-start-8 py-2 col-span-2 text-red-500" v-show="isInputName || isInputNameOver || isInputEmail || isInputEmailVaild || isInputEmailOver
                    || isInputCategory || isInputNotes || isInputTime || isInputTimeOld">
                        *some input is invalid
                    </div>

                    <div class="row-start-7 col-start-9 col-span-1"> <button @click="changeCancelDialogTrue"
                            class="w-full h-full m-auto py-2  bg-[#F97473] text-white rounded-lg">
                            cancel </button> </div>
                </div>
                <Cancel v-if="cancelDialog" @onClickCancelNo="changeCancelDialogFalse" @onClickCancelYes="reset" />

                <Confirm v-if="createDialog" @onClickCreateNo="closeConfirmDialog"
                    @onClickCreateYes="createBooking(localData)" />
            </div>
        </div>
        <NavbarBottom />
    </div>
</template>
 
<style>
</style>