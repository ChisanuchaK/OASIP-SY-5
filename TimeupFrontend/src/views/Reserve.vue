<script setup>
import { ref, computed, onBeforeMount, reactive } from 'vue'
import moment from "moment";
import NavbarTop from '../components/navbarTop.vue';
import NavbarBottom from '../components/navbarBottom.vue';
import { getEventCategory } from "../stores/book.js";
import { createBooking } from "../stores/book.js";
import Cancel from "../components/Cancel.vue";
import Confirm from "../components/Confirm.vue";

const localPresentTime = moment.utc().local().format("YYYY-MM-DDTHH:mm")
const categoryList = ref([]);
const categoryIndexSelect = ref();
const dateIndexSelect = ref(localPresentTime);

let date = new Date();
date.setMonth(date.getMonth() + 3);
let maxlocalPresentTime = moment(date).format("YYYY-MM-DDTHH:mm")
let maxdateIndexSelect = ref(maxlocalPresentTime)

let isInvalid = ref(false);

// console.log(moment.utc().local().format("YYYY-MM-DDTHH:mm"));

const cancelDialog = ref(false)
const createDialog = ref(false)

let localData = reactive({
    bookingName: "",
    bookingEmail: "",
    eventCategory: { eventCategoryId: "" },
    eventStartTime: "",
    eventDuration: "",
    eventNotes: "",
})

const handleSelect = () => {
    localData.eventCategory.eventCategoryId = categoryList.value[categoryIndexSelect.value].eventCategoryId
    localData.eventDuration = categoryList.value[categoryIndexSelect.value].eventDuration
    // alert(CategorySelect)
    console.log(localData)
    console.log(categoryIndexSelect.value);
}

const handleTime = () => {
    // localData.eventStartTime = new Date().toISOString()
    localData.eventStartTime = new Date(dateIndexSelect.value).toISOString()
    console.log(localData.eventStartTime);
}

onBeforeMount(async () => {
    categoryList.value = await getEventCategory();
    console.log(categoryIndexSelect.value);
})

const createBookingEvent = async (localDataInput) => {
    await createBooking(localDataInput)
    localData.bookingName = ""
    localData.bookingEmail = ""
    categoryIndexSelect.value = ""
    localData.eventDuration = ""
    dateIndexSelect.value = localPresentTime
    localData.eventNotes = ""
    createDialog.value = !createDialog.value
}

const changeCancelDialog = () => {
    cancelDialog.value = !cancelDialog.value
}

const reset = () => {
    localData.bookingName = ""
    localData.bookingEmail = ""
    categoryIndexSelect.value = ""
    localData.eventDuration = ""
    dateIndexSelect.value = ""
    localData.eventNotes = ""
    cancelDialog.value = !cancelDialog.value
}

const changeConfirmDialog = () => {
    if (localData.bookingName.trim() === "" || localData.bookingEmail.trim() === "" || dateIndexSelect.value === undefined) {
        isInvalid.value = true
    }
    else {
        createDialog.value = !createDialog.value
         isInvalid.value = false
    }
    console.log(isInvalid.value);
}

const isInputName = computed(() => {
    return isInvalid.value && localData.bookingName.trim() === "";
});

const isInputEmail = computed(() => {
    return isInvalid.value && localData.bookingEmail.trim() === "";
});

const isInputCategory = computed(() => {
    return isInvalid.value && categoryIndexSelect.value === undefined ;
});

const isInputNotes = computed(() => {
    return isInvalid.value && localData.eventNotes.length > 500 ;
});



</script>
 
<template>
    <div>
        <NavbarTop />
        <NavbarBottom />
        <div class=" pt-4 mt-16 mb-3 uppercase w-3/4 m-auto text-center text-[40px] text-white"> select scheduled</div>
        <div class="w-[80%] m-auto h-auto">
            <div class="bg-white text-xl rounded-xl">
                <div class="m-auto w-1/2 text-center pt-4">Enter Your Details</div>
                <div class="grid grid-flow-row grid-cols-9 p-10 gap-3">

                    <div class="row-start-1 col-start-1 col-span-3 ">Scheduled Category :</div>
                    <div class="row-start-2 col-start-1 col-end-4 col-span-3">
                        <!-- <select class="bg-gray-200 rounded w-full" v-model="CategorySelect" @change="hanleSelcet()"> -->
                        <select class="bg-gray-200 rounded w-full border" v-model="categoryIndexSelect" :style="{ 'border-color': categoryIndexSelect == undefined && isInvalid ? 'red' : 'white' }"
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
                    <div class="row-start-2 col-start-7 col-end-10 col-span-3 "><input
                            class="bg-gray-200 rounded w-full pb-0.5 pl-1 border" type="text"
                            v-model="localData.bookingName"
                            :style="{ 'border-color': localData.bookingName.trim() === '' && isInvalid ? 'red' : 'white' }" />
                        <label for="name-with-label" class="text-red-500" v-if="isInputName">
                            Please enter Name
                        </label>
                    </div>

                    <div class="row-start-3 col-start-1 col-span-1 ">Time :</div>
                    <div class="row-start-4 col-start-1 col-end-4 col-span-3 "><input class="bg-gray-200 rounded w-full"
                            :max="maxdateIndexSelect" :min="localPresentTime" @change="handleTime()"
                            type="datetime-local" v-model="dateIndexSelect" /></div>

                    <div class="row-start-3 col-start-4 col-span-1 text-center">Duration</div>
                    <div class="row-start-4 col-start-4 col-span-1 "><input
                            class="bg-gray-300 rounded w-full pb-0.5 pl-1 text-center" readonly type="text"
                            v-model="localData.eventDuration" /></div>

                    <div class="row-start-3 col-start-7 col-span-1 ">Email :</div>
                    <div class="row-start-4 col-start-7 col-end-10 col-span-3 "><input
                            class="bg-gray-200 rounded w-full pb-0.5 pl-1 border" type="email"
                            v-model="localData.bookingEmail" :style="{ 'border-color': localData.bookingEmail.trim() === '' && isInvalid ? 'red' : 'white' }"/>
                            <label for="name-with-label" class="text-red-500" v-if="isInputEmail">
                            Please enter Email
                        </label>
                            </div>

                    <div class="row-start-5 col-start-1 col-span-1 ">Notes :</div>
                    <div class="row-start-6 col-start-1 col-end-10 span-9"> <textarea
                            class="bg-gray-200 w-full resize-none rounded border" name="" id="" cols="100" rows="5" placeholder="enter your message(limit 500 text)"
                            v-model="localData.eventNotes" :style="{ 'border-color': localData.eventNotes.length > 500 && isInvalid ? 'red' : 'white' }"></textarea>
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
                    @onClickCreateYes="createBookingEvent(localData)" />
            </div>
        </div>
    </div>
</template>
 
<style>
</style>