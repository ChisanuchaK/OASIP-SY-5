<script setup>
import { ref, reactive, onBeforeMount, computed, onBeforeUpdate } from "vue";
import moment from "moment";
import Cancel from "./Cancel.vue";
import Confirm from "./Confirm.vue";
import { getEventCategory, editBooking, getBookings } from "../stores/book.js";
const emits = defineEmits(["EditbookingId"]);
const props = defineProps({
    bookingsDetailsEdit: {
        type: Object,
        require: true,
    },
    loopEdit: {
        type: Object,
        require: true
    }
});

const bookingToEdit = computed(() => props.bookingsDetailsEdit);
const someBooking = ref(bookingToEdit);
const note = ref(someBooking.value.eventNotes);

const bookingPresentTime = ref(moment.utc(someBooking.value.eventStartTime).local().format("YYYY-MM-DDTHH:mm"));
const categoryList = ref([]);
const bookingLists = ref([]);

let isInvalid = ref(false);
let isOverlap = ref(false);

const localPresentTime = moment.utc().local().format("YYYY-MM-DDTHH:mm");

let date = new Date();
date.setMonth(date.getMonth() + 3);
let maxlocalPresentTime = moment(date).format("YYYY-MM-DDTHH:mm");
let maxdateIndexSelect = ref(maxlocalPresentTime);

let editData = reactive({
    idBooking: someBooking.value.idBooking,
    eventCategory: { eventCategoryId: someBooking.value.eventCategoryId },
    eventStartTime: new Date(bookingPresentTime.value).toISOString(),
    eventDuration: someBooking.value.eventDuration,
    eventNotes: someBooking.value.eventNotes,
})

//keep time to reactive valiable
const selectTime = () => {
    editData.eventStartTime = new Date(bookingPresentTime.value).toISOString()
    // console.log(editData.eventStartTime);
    // console.log(bookingPresentTime.value);
}

//keep time to reactive valiable (second check)
const sentEditData = () => {
    editData.eventStartTime = new Date(bookingPresentTime.value).toISOString()
    editData.eventNotes = note.value
    console.log(editData);
}

//------------------------------------------------check invalid form input --------------------------------------------------------------------
const isInputNotes = computed(() => {
    return isInvalid.value && note.value.length > 500;
});
const isInputTimeOlds = computed(() => {
    return (isInvalid.value && (new Date(bookingPresentTime.value).toISOString() < new Date().toISOString()));
});
const isInputTimes = computed(() => {
    const localEndTime = `${moment.utc(editData.eventStartTime).add(editData.eventDuration, 'm').format("YYYY-MM-DDTHH:mm:ss")}Z`;
    for (let booking of bookingLists.value) {
        if (editData.eventCategory.eventCategoryId === booking.eventCategoryId) {
            if ((editData.eventStartTime <= booking.eventEndTime) && (localEndTime >= booking.eventStartTime) && (editData.idBooking != booking.idBooking)) {
                isOverlap.value = true
                isInvalid.value = true
                // console.log("overlap")
                break;
            } else {
                isOverlap.value = false
                // console.log("not overlap")
            }
        }
    }
    return (isInvalid.value && isOverlap.value)
});

//------------------------------------------------check invalid form input --------------------------------------------------------------------



//------------------------------------------pop-up-dialog------------------------------------------------------------------
const closeEdit = (event, bookingEdit) => {
    if (event.target.id == "modalEdit") {
        bookingEdit.cancelDialog = true;
        // emits("onClickCancelEdit")
    }
}
const changeCancelDialogFalse = (bookingEdit) => {
    bookingEdit.cancelDialog = false;
}
const changeCancelDialogTrue = (bookingEdit) => {
    bookingEdit.cancelDialog = true;
}
const closeEditDialog = (bookingEdit) => {
    bookingEdit.cancelDialog = !bookingEdit.cancelDialog;
    bookingEdit.statusClickEdit = !bookingEdit.statusClickEdit;
}
const changeCreateDialogFalse = (bookingEdit) => {
    bookingEdit.createDialog = false
}

//check input is have invalid ?
const changeCreateDialogTrue = (bookingEdit) => {
    isInvalid.value = false
    if ((note.value.length > 500) || (new Date(bookingPresentTime.value).toISOString() < new Date().toISOString())) {
        // console.log("first if");
        isInvalid.value = true
    } else {
        isInvalid.value = false
        // console.log("first else");
    }
    if (isOverlap.value == false && isInvalid.value == false) {
        bookingEdit.createDialog = true
        isInvalid.value = false
        // console.log("second if");
    }
    // console.log("not overlap and isInvalid " + isInvalid.value);

}
//------------------------------------------pop-up-dialog------------------------------------------------------------------

// put bookings is edit
const editBookingEvent = async (editData, bookingEdit, loopEdit) => {
    const res = await editBooking(editData);
    if (res.status === 200) {
        alert('Edit Booking Success!');
        bookingEdit.createDialog = !bookingEdit.createDialog;
        bookingEdit.statusClickEdit = !bookingEdit.statusClickEdit;
        loopEdit.statusClickSeeDetails = !loopEdit.statusClickSeeDetails;
        emits('EditbookingId', editData)
        console.log('edited successfully')
    } else {
        // bookingEdit.createDialog = !bookingEdit.createDialog;
        console.log('error, cannot be added')
    }
}

//fetch data
onBeforeMount(async () => {
    const getAllBooks = await getBookings();
    bookingLists.value = await getAllBooks.json();
    const getAllCategory = await getEventCategory();
    categoryList.value = await getAllCategory.json();
    console.log(bookingLists.value);
    console.log(categoryList.value);
    console.log(someBooking.value);
})


</script>

<template>
    <div id="modalEdit" @click="closeEdit($event, someBooking)"
        class="font overflow-x-hidden overflow-y-auto fixed inset-0 z-10 outline-none focus:outline-none justify-center items-center flex bg-black bg-opacity-20">
        <div class="">
            <div class="relative bg-white rounded-lg text-xl p-5 px-12">
                <div class="w-36 m-auto text-1xl text-center">
                    ID : {{ someBooking.idBooking }}
                </div>
                
                <img class="absolute top-0 right-0 text-4xl text-lg font-normal h-[30px] w-[30px] mt-3 mr-7 cursor-pointer"
                    @click="changeCancelDialogTrue(someBooking)" src="/images/cancel.png" alt="cancel" />

                <div class="grid grid-flow-row grid-cols-2 p-1 gap-1 pt-5 text-center">
                    <div class="row-start-1 col-start-1 col-span-2 p-1 rounded-lg">
                        Category : {{ someBooking.eventCategoryName }}
                    </div>

                    <div class="row-start-2 col-start-1 col-span-2 p-1 rounded-lg">
                        Name : {{ someBooking.bookingName }}
                    </div>

                    <div class="row-start-3 col-start-1 col-span-2 p-1 rounded-lg">
                        Email : {{ someBooking.bookingEmail }}
                    </div>

                    <div class="row-start-4 col-start-1 col-span-2 p-1 rounded-lg">
                        StartTime :
                    </div>

                    <div class="row-start-5 col-start-1 col-span-2 p-1 ">
                        <input class="bg-gray-200 rounded-lg border " type="datetime-local" :max="maxdateIndexSelect"
                            :min="localPresentTime" v-model="bookingPresentTime"
                            :style="{ 'border-color': (isInputTimes || isInputTimeOlds) ? 'red' : 'white' }"
                            @change="selectTime()">
                        <div class="text-red-500" v-if="isInputTimes">
                            *overlap
                        </div>

                        <div class="text-red-500" v-if="isInputTimeOlds">
                            *Please select time is future than present
                        </div>
                    </div>

                    <div class="row-start-6 col-start-1 col-span-2 p-1 rounded-lg">
                        Duration : {{ someBooking.eventDuration }} minute
                    </div>

                    <div class="row-start-7 col-start-1 col-span-2 p-1 rounded-lg">
                        EventNotes :
                    </div>
                    <div class="row-start-7 col-start-2 col-span-1 p-1 rounded-lg text-right text-gray-400">
                        {{ note.length }}/500
                    </div>

                    <div class="row-start-8 col-start-1 col-end-3 col-span-2">
                        <textarea class="bg-gray-200 w-full resize-none rounded text-center border" v-model="note"
                            name="" :style="{ 'border-color': isInputNotes ? 'red' : 'white' }" id="" cols="90"
                            rows="5">{{
                                    note == null
                                        ? "no message"
                                        : note
                            }}</textarea>
                        <div class="text-red-500 text-center m-auto" v-if="isInputNotes">
                            *over limit of Notes
                        </div>

                    </div>
                    <div class="row-start-9 mt-5 col-start-1">
                        <button class="bg-[#499D6B] text-white rounded-lg w-6/12 h-full m-auto py-2"
                            @click="[changeCreateDialogTrue(someBooking), sentEditData()]">confirm</button>
                    </div>

                    <div class="row-start-9 mt-5 col-start-2">
                        <button class="bg-[#F97473] text-white rounded-lg w-6/12 h-full m-auto py-2"
                            @click="changeCancelDialogTrue(someBooking)">cancel</button>
                    </div>

                    <div class="text-red-500" v-show="isInputNotes || isInputTimeOlds || isInputTimes">
                        *some input is invalid
                    </div>

                    <Cancel v-if="someBooking.cancelDialog" @onClickCancelNo="changeCancelDialogFalse(someBooking)"
                        @onClickCancelYes="closeEditDialog(someBooking)" />

                    <Confirm v-if="someBooking.createDialog" @onClickConfirmNo="changeCreateDialogFalse(someBooking)"
                        @onClickConfirmYes="editBookingEvent(editData, someBooking, loopEdit)" />
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
</style>
