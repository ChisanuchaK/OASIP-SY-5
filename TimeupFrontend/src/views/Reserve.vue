<script setup>
import { ref, computed, onBeforeMount, reactive } from 'vue'
import moment from "moment";
import NavbarTop from '../components/navbarTop.vue';
import NavbarBottom from '../components/navbarBottom.vue';
import { getEventCategory } from "../stores/book.js";
import { getBookings } from "../stores/book.js";
import Cancel from "../components/Cancel.vue";
import Confirm from "../components/Confirm.vue";

const localPresentTime = moment.utc().local().format("YYYY-MM-DDTHH:mm")
const bookingLists = ref([]);

const books = computed(() => bookingLists.value)

console.log(bookingLists.value);

let localData = reactive({
    bookingName: "",
    bookingEmail: "",
    eventCategory: { eventCategoryId: "" },
    eventStartTime: "",
    eventDuration: "",
    eventNotes: "",
})

const categoryList = ref([]);
const categoryIndexSelect = ref();
const dateIndexSelect = ref(localPresentTime);

let date = new Date();
console.log(date);
date.setMonth(date.getMonth() + 3);
let maxlocalPresentTime = moment(date).format("YYYY-MM-DDTHH:mm")
let maxdateIndexSelect = ref(maxlocalPresentTime)

let isInvalid = ref(false);

// console.log(moment.utc().local().format("YYYY-MM-DDTHH:mm"));
const cancelDialog = ref(false)
const createDialog = ref(false)

const handleSelect = () => {
    localData.eventCategory.eventCategoryId = categoryList.value[categoryIndexSelect.value].eventCategoryId
    localData.eventDuration = categoryList.value[categoryIndexSelect.value].eventDuration
    // alert(CategorySelect)
    console.log(localData)
    console.log(categoryIndexSelect.value);
}
const handleTime = () => {
    // localData.eventStartTime = new Date().toISOString()
    // localData.eventStartTime = new Date(dateIndexSelect.value).toISOString()
    localData.eventStartTime = moment.utc(dateIndexSelect.value).format()
    console.log(dateIndexSelect.value);
    console.log(localData.eventStartTime);
    console.log(moment.utc(localData.eventStartTime).add(localData.eventDuration, 'm').format("YYYY-MM-DDTHH:mm:ss"));
}
onBeforeMount(async () => {
    categoryList.value = await getEventCategory();
    bookingLists.value = await getBookings();
    // const utcTime = new Date(dateIndexSelect.value).toISOString()
    // const localEndTime = moment.utc(utcTime).add(localData.eventDuration, 'm').format()
    // console.log("form value " + (dateIndexSelect.value));
    // console.log("change isostring" + new Date(dateIndexSelect.value).toISOString());
    // console.log("utc time " + utcTime);
    // console.log("end time " + localEndTime);
    // console.log(localData.eventStartTime);
    // console.log(bookingLists.value);

    // for (let booking of bookingLists.value) {
    //     let bookingEndTime = moment.utc(booking.eventStartTime).add(booking.eventDuration, 'm').format("YYYY-MM-DDTHH:mm:ss")
    //     if ((localData.eventStartTime <= booking.eventStartTime && localEndTime <= bookingEndTime) || (localData.eventStartTime >= booking.eventStartTime && localEndTime >= bookingEndTime) || (localData.eventStartTime >= booking.eventStartTime && localEndTime <= bookingEndTime) || (localData.eventStartTime <= booking.eventStartTime && localEndTime >= bookingEndTime)) {
    //     }

    //     //  console.log("start : "+ booking.eventStartTime);
    //     //     console.log("end : "+ bookingEndTime);
    // }

    // {{
    //                             moment(bookingsDetails.eventStartTime)
    //                                 .local()
    //                                 .add(bookingsDetails.eventDuration, "m")
    //                                 .format("hh:mm A")
    //                     }}

})

const checkOverLap = () => {
    // console.log(bookingLists.value);
    // console.log(dateIndexSelect.value);
    // console.log(dateIndexSelect);

    // console.log("form value " + (dateIndexSelect.value));
    // console.log("change isostring" + new Date(dateIndexSelect.value).toISOString());
    // console.log("utc time " + utcTime);
    // console.log("end time " + localEndTime);
    // console.log(localData.eventStartTime);
    // console.log(bookingLists.value);

    // const utcTime = ref(new Date(dateIndexSelect.value).toISOString())
    const localEndTime = `${moment.utc(localData.eventStartTime).add(localData.eventDuration, 'm').format("YYYY-MM-DDTHH:mm:ss")}Z`
    // let localEndTimeFormat = new Date(localEndTime).toISOString()
    // console.log("local start time" + localData.eventStartTime);
    // console.log("local end time " + localEndTime);
    //  console.log("local end time Z " + localEndTimeFormat);
    console.log("------------------------------------------------------------------");
    for (let booking of bookingLists.value) {

        // console.log("local start time" + localData.eventStartTime);
        // console.log("local end time " + localEndTime);
        // console.log("booking start time " + booking.eventStartTime);
        // console.log("booking end time " + booking.eventEndTime);
        // console.log("booking duration " + booking.eventDuration);

 
        if((localData.eventStartTime >= booking.eventStartTime) && (localData.eventStartTime <= booking.eventEndTime)
          || ((localEndTime) >= booking.eventStartTime) && ((localEndTime) <= booking.eventEndTime)
          || (localData.eventStartTime <= booking.eventStartTime) && ((localEndTime) >= booking.eventEndTime))
    {
                
console.log(localData.eventStartTime)
console.log(localEndTime);
console.log(booking.eventStartTime)
console.log(booking.eventEndTime)
            return console.log("overlap")
    }
   console.log(localData.eventStartTime)
console.log(localEndTime);
console.log(booking.eventStartTime)
console.log(booking.eventEndTime)
    return  console.log("not overlap")
}



// const isInputTime = computed(() => { 

//     for (let booking of bookingLists.value) {
//         let bookingEndTime = moment.utc(booking.eventStartTime).add(booking.eventDuration, 'm').format("YYYY-MM-DDTHH:mm:ss")
//             console.log("local start time"+localData.eventStartTime);
//             console.log("booking start time "+booking.eventStartTime);
//             console.log("local end time " + localEndTime);
//             console.log("booking end time "+bookingEndTime);

//             return ( !((localData.eventStartTime <= booking.eventStartTime && localEndTime <= bookingEndTime) 
//             || (localData.eventStartTime >= booking.eventStartTime && localEndTime >= bookingEndTime)
//             || (localData.eventStartTime >= booking.eventStartTime && localEndTime <= bookingEndTime) 
//             || (localData.eventStartTime <= booking.eventStartTime && localEndTime >= bookingEndTime)) && isInvalid.value );
//     }
// });

// }


// const createBookingEvent = async (localDataInput) => {
//     await createBooking(localDataInput)

//     // localData.bookingName = ""
//     // localData.bookingEmail = ""
//     // categoryIndexSelect.value = ""
//     // localData.eventDuration = ""
//     // dateIndexSelect.value = localPresentTime
//     // localData.eventNotes = ""
//     createDialog.value = !createDialog.value
// }

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
            eventDuration: localDataInput.eventDuration,
            eventNotes: localDataInput.eventNotes,
        }),
    });
    if (res.status === 201) {
        alert(`Create successfully \n Category ID :  ${localData.eventCategory.eventCategoryId} \n Date : ${localData.eventStartTime} \n Booking name :  ${localData.bookingName}`)
        console.log(`Create successfully \n Category ID :  ${localDataInput.eventCategory.eventCategoryId} \n Date : ${localDataInput.eventStartTime} \n Booking name :  ${localDataInput.bookingName}`)
        localDataInput.bookingName = ""
        localDataInput.bookingEmail = ""
        categoryIndexSelect.value = ""
        localDataInput.eventDuration = ""
        dateIndexSelect.value = localPresentTime
        localDataInput.eventNotes = ""

    } else {
        console.log("error , failed to created");
    }
    createDialog.value = !createDialog.value
};

const changeCancelDialog = () => {
    cancelDialog.value = !cancelDialog.value
}
const reset = () => {
    localData.bookingName = ""
    localData.bookingEmail = ""
    categoryIndexSelect.value = ""
    localData.eventDuration = ""
    dateIndexSelect.value = localPresentTime
    localData.eventNotes = ""
    cancelDialog.value = !cancelDialog.value
}

const changeConfirmDialog = () => {
    // const utcTime = new Date(dateIndexSelect.value).toISOString()
    // const localEndTime = moment.utc(utcTime).add(localData.eventDuration, 'm').format()

    for (let booking of bookingLists.value) {
        let bookingEndTime = moment.utc(booking.eventStartTime).add(booking.eventDuration, 'm').format("YYYY-MM-DDTHH:mm:ss")
        // if ((localData.eventStartTime <= booking.eventStartTime && localEndTime <= bookingEndTime) 
        // || (localData.eventStartTime >= booking.eventStartTime && localEndTime >= bookingEndTime) 
        // || (localData.eventStartTime >= booking.eventStartTime && localEndTime <= bookingEndTime) 
        // || (localData.eventStartTime <= booking.eventStartTime && localEndTime >= bookingEndTime)) {
        //     return a
        // }

        //  console.log("start : "+ booking.eventStartTime);
        //     console.log("end : "+ bookingEndTime);

        if ((localData.bookingName.trim() === "" || localData.bookingName.length > 100) || (localData.bookingEmail.trim() === "")
            || (categoryIndexSelect.value === undefined || categoryIndexSelect.value === "") || localData.eventNotes.length > 500
            || ((localData.eventStartTime <= booking.eventStartTime && localEndTime <= bookingEndTime) || (localData.eventStartTime >= booking.eventStartTime && localEndTime >= bookingEndTime)
                || (localData.eventStartTime >= booking.eventStartTime && localEndTime <= bookingEndTime) || (localData.eventStartTime <= booking.eventStartTime && localEndTime >= bookingEndTime))
        ) {
            isInvalid.value = true
        }
        else {
            createDialog.value = !createDialog.value
            isInvalid.value = false
        }

    } console.log(isInvalid.value);
}
const isInputName = computed(() => {
    if (localData.bookingName.trim() === "") {
        return (isInvalid.value && localData.bookingName.trim() === "");
    }
});

const isInputNameOver = computed(() => {
    return (isInvalid.value && localData.bookingName.length > 100);
});
const isInputEmail = computed(() => {
    return isInvalid.value && localData.bookingEmail.trim() === "";
});
const isInputCategory = computed(() => {
    return (isInvalid.value && (categoryIndexSelect.value === undefined || categoryIndexSelect.value === ""));
});
const isInputNotes = computed(() => {
    return (isInvalid.value && localData.eventNotes.length > 500);
});

const isInputTime = computed(() => {

    // for (let booking of bookingLists.value) {
    //     let bookingEndTime = moment.utc(booking.eventStartTime).add(booking.eventDuration, 'm').format("YYYY-MM-DDTHH:mm:ss")
    //         return ( ((localData.eventStartTime <= booking.eventStartTime && localEndTime <= bookingEndTime) 
    //         || (localData.eventStartTime >= booking.eventStartTime && localEndTime >= bookingEndTime)
    //         || (localData.eventStartTime >= booking.eventStartTime && localEndTime <= bookingEndTime) 
    //         || (localData.eventStartTime <= booking.eventStartTime && localEndTime >= bookingEndTime))&& isInvalid.value );
    // }
});
}
</script>
 
<template>
    <div>
        <NavbarTop />

        <div class="mt-20 mb-8 uppercase w-3/4 m-auto text-center text-[46px] text-white"> select scheduled</div>
        <div class="w-[80%] m-auto h-auto">
            <div class="bg-white text-[100%] rounded-xl font-bold">
                <div class="m-auto w-1/2 text-center pt-5">Enter Your Details
                    <button @click="checkOverLap"> aaa </button>
                </div>
                <div class="grid grid-flow-row grid-cols-9 p-10 gap-3">

                    <div class="row-start-1 col-start-1 col-span-3 ">Scheduled Category :</div>
                    <div class="row-start-2 col-start-1 col-end-4 col-span-3">
                        <!-- <select class="bg-gray-200 rounded w-full" v-model="CategorySelect" @change="hanleSelcet()"> -->
                        <select class="bg-gray-200 rounded w-full border" v-model="categoryIndexSelect"
                            :style="{ 'border-color': (categoryIndexSelect == undefined || categoryIndexSelect == '') && isInvalid ? 'red' : 'white' }"
                            @change="handleSelect()">
                            <option v-for="(res, indexs) in categoryList" :value="indexs" v-bind:key="indexs">{{
                                    res.eventCategoryName
                            }}</option>
                        </select>
                        <label for="name-with-label" class="text-red-500" v-if="isInputCategory">
                            Please choose category
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
                            :style="{ 'border-color': (localData.bookingName.trim() === '' || localData.bookingName.length > 100) && isInvalid ? 'red' : 'white' }" />
                        <label for="name-with-label" class="text-red-500" v-if="isInputName">
                            Please enter Name!
                        </label>
                        <label for="name-with-label" class="text-red-500" v-if="isInputNameOver">
                            over limit of input Name!
                        </label>
                    </div>

                    <div class="row-start-3 col-start-1 col-span-1 ">Time :</div>
                    <div class="row-start-4 col-start-1 col-end-4 col-span-3 ">
                        <form action=""> <input class="bg-gray-200 rounded w-full border" :max="maxdateIndexSelect"
                                :min="localPresentTime" @change="handleTime()" type="datetime-local"
                                :style="{ 'border-color': (dateIndexSelect < localPresentTime || isInputTime) && isInvalid ? 'red' : 'white' }"
                                v-model="dateIndexSelect" />
                        </form>
                        <label for="name-with-label" class="text-red-500" v-if="isInputTime">
                            over limit of input message!!
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
                            :style="{ 'border-color': localData.bookingEmail.trim() === '' && isInvalid ? 'red' : 'white' }" />
                        <label for="name-with-label" class="text-red-500" v-if="isInputEmail">
                            Please enter Email!
                        </label>
                    </div>

                    <div class="row-start-5 col-start-1 col-span-1 ">Notes :</div>
                    <div class="row-start-5 col-start-9 col-span-1 text-right text-gray-400">
                        {{ localData.eventNotes.length }}/500
                    </div>

                    <div class="row-start-6 col-start-1 col-end-10 span-9"> <textarea
                            class="bg-gray-200 w-full resize-none rounded border" name="" id="" cols="100" rows="5"
                            placeholder="enter your message(limit 500 text)" v-model="localData.eventNotes"
                            :style="{ 'border-color': localData.eventNotes.length > 500 && isInvalid ? 'red' : 'white' }"></textarea>
                        <label for="name-with-label" class="text-red-500" v-if="isInputNotes">
                            over limit of input message!!
                        </label>
                    </div>

                    <div class="row-start-7 col-start-8 col-span-1"> <button @click="changeConfirmDialog"
                            class="w-full h-full m-auto py-2 bg-[#499D6B] text-white rounded-lg">
                            create </button> </div>
                    <div class="row-start-7 col-start-9 col-span-1"> <button @click="changeCancelDialog"
                            class="w-full h-full m-auto py-2 bg-[#F97473] text-white rounded-lg">
                            cancel </button> </div>

                </div>
                <Cancel v-if="cancelDialog" @onClickCancelNo="changeCancelDialog" @onClickCancelYes="reset" />

                <Confirm v-if="createDialog" @onClickCreateNo="changeConfirmDialog"
                    @onClickCreateYes="createBooking(localData)" />
            </div>
        </div>
        <NavbarBottom />
    </div>

</template>
 
<style>

</style>