<script setup>
import { ref, computed, onBeforeMount, reactive } from 'vue'
import NavbarTop from '../components/navbarTop.vue';
import NavbarBottom from '../components/navbarBottom.vue';
import { getEventCategory } from "../stores/book.js";
import { createBooking } from "../stores/book.js";
import ConfirmCancel from "../components/ConfirmCancel.vue";
import ConfirmCreate from "../components/ConfirmCreate.vue";

const categoryList = ref([]);

const categoryIndexSelect = ref();
const dateIndexSelect = ref();

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

const handleSelect = () =>{
    localData.eventCategory.eventCategoryId = categoryList.value[categoryIndexSelect.value].eventCategoryId
    localData.eventDuration = categoryList.value[categoryIndexSelect.value].eventDuration
    // alert(CategorySelect)
    console.log( localData )
}

const handleTime = () =>{

    // localData.eventStartTime = new Date().toISOString()
    localData.eventStartTime = new Date(dateIndexSelect.value).toISOString()
    console.log(localData.eventStartTime);
}

onBeforeMount(async () => {
    categoryList.value = await getEventCategory();
})

const createBookingEvent = async(localDataInput) => {
 await createBooking(localDataInput)
  createDialog.value = !createDialog.value
}

const changeCancelDialog = () => {
   cancelDialog.value = !cancelDialog.value
}

const changeCreateDialog = ()=> {
     createDialog.value = !createDialog.value
}

</script>
 
<template>
    <div>
        <NavbarTop />
        <NavbarBottom />
        <div class="mt-16 mb-10 uppercase w-3/4 m-auto text-center text-[52px] text-white"> select scheduled</div>
        <div class="w-[80%] m-auto h-auto">
            <div class="bg-white text-2xl rounded-xl">
                <div class="m-auto w-1/2 text-center pt-5">Enter Your Details</div>
                <div class="grid grid-flow-row grid-cols-9 p-10 gap-3">

                    <div class="row-start-1 col-start-1 col-span-3 ">Scheduled Category :</div>
                    <div class="row-start-2 col-start-1 col-end-4 col-span-3">
                        <!-- <select class="bg-gray-200 rounded w-full" v-model="CategorySelect" @change="hanleSelcet()"> -->
                        <select class="bg-gray-200 rounded w-full" v-model="categoryIndexSelect" @change="handleSelect()">
                            <option v-for="(res, indexs) in categoryList" :value="indexs" v-bind:key="indexs" >{{
                                    res.eventCategoryName
                            }}</option>
                        </select>
                    </div>

                    <div class="row-start-1 col-start-7 col-span-1 ">
                        <!-- <div v-if="">Name is null</div>  -->
                        Name :
                    </div>
                    <div class="row-start-2 col-start-7 col-end-10 col-span-3 "><input
                            class="bg-gray-200 rounded w-full pb-0.5 pl-1" type="text" v-model="localData.bookingName"/></div>

                    <div class="row-start-3 col-start-1 col-span-1 ">Time :</div>
                    <div class="row-start-4 col-start-1 col-end-4 col-span-3 "><input class="bg-gray-200 rounded w-full" @change="handleTime()"
                            type="datetime-local" v-model="dateIndexSelect"/></div>

                    <div class="row-start-3 col-start-4 col-span-1 text-center">Duration</div>
                    <div class="row-start-4 col-start-4 col-span-1 "><input class="bg-gray-300 rounded w-full pb-0.5 pl-1 text-center" readonly
                            type="text"  v-model="localData.eventDuration" /></div>

                    <div class="row-start-3 col-start-7 col-span-1 ">Email :</div>
                    <div class="row-start-4 col-start-7 col-end-10 col-span-3 "><input
                            class="bg-gray-200 rounded w-full pb-0.5 pl-1" type="email" v-model="localData.bookingEmail" /></div>

                    <div class="row-start-5 col-start-1 col-span-1 ">Notes :</div>
                    <div class="row-start-6 col-start-1 col-end-10 span-9"> <textarea
                            class="bg-gray-200 w-full resize-none rounded" name="" id="" cols="100" rows="5"  v-model="localData.eventNotes"></textarea>
                    </div>

                    <div class="row-start-7 col-start-8"> <button @click="changeCreateDialog" class="2xl:px-5 px-10 py-3 bg-[#499D6B] text-white rounded-lg">
                            create </button> </div>
                    <div class="row-start-7 col-start-9"> <button @click="changeCancelDialog" class="2xl:px-5 px-10 py-3 bg-[#F97473] text-white rounded-lg">
                            cancel </button> </div>

                </div>
                <ConfirmCancel v-if="cancelDialog" @onClickCancelNo="changeCancelDialog" @onClickCancelYes="changeCancelDialog"/>

                <ConfirmCreate v-if="createDialog" @onClickCreateNo="changeCreateDialog" @onClickCreateYes="createBookingEvent(localData)"/>
            </div>
        </div>
    </div>
</template>
 
<style>
</style>