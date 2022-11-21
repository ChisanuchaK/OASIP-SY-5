<script setup>
import { ref, reactive, onBeforeMount, computed, onBeforeUpdate } from "vue";
import moment from "moment";
import Cancel from "../Cancel.vue";
import Confirm from "../Confirm.vue";
import { bookStore } from '../../stores/book.js';
import { categoryStore } from '../../stores/category.js';
// import { getEventCategory, editBooking, getBookings } from "../stores/book.js";
const emits = defineEmits(["EditbookingId"]);
const props = defineProps({
    bookingLists: {
        type: [Object],
        require: true
    },
    bookingsDetailsEdit: {
        type: Object,
        require: true,
    },
    loopEdit: {
        type: Object,
        require: true
    }
});

const bookRes = bookStore();
const categoryRes = categoryStore();

const bookingToEdit = computed(() => props.bookingsDetailsEdit);
const someBooking = ref(bookingToEdit);
const note = ref('');

//getbook make web refresh.
// bookRes.getBookings();
// categoryRes.getEventCategory();

const bookingPresentTime = ref(moment.utc(someBooking.value.eventStartTime).local().format("YYYY-MM-DDTHH:mm"));
//bookings for check overlap
const bookingLists = computed(() => props.bookingLists);
// const categoryList = computed(() => categoryRes.categorys);


let isInvalid = ref(false);
let isOverlap = ref(false);

const fileSelected = ref();
console.log(fileSelected.value);

let fileDetail = reactive({
    fileName: null,
    fileSize: 0,
    fileSizeUnit: "",
});

//get file from booking when booking have a file
// fileSelected.value = someBooking.value.file
fileDetail.fileName = someBooking.value.file;

const localPresentTime = moment.utc().local().format("YYYY-MM-DDTHH:mm");

let date = new Date();
date.setMonth(date.getMonth() + 3);
let maxlocalPresentTime = moment(date).format("YYYY-MM-DDTHH:mm");
let maxdateIndexSelect = ref(maxlocalPresentTime);

const deleteFile = () => {
    fileSelected.value = null;
    fileDetail.fileName = null;
    fileDetail.fileSize = 0;
    fileDetail.fileSizeUnit = "";
};

const fileOnInput = ($event) => {
    // console.log(fileSelected.value);
    // console.log($event.target.files.length);
    if ($event.target.files.length != 0  && $event.target.files[0].size <= 10485760) {
        fileSelected.value = $event.target.files[0];
        console.log(fileSelected.value);
        fileDetail.fileName = fileSelected.value.name;
        fileSize($event.target.files[0].size);
    }
    console.log(fileSelected.value);
};

const fileSize = (sizeOfFile) => {
    if (sizeOfFile != 0 && sizeOfFile != null) {
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
    }
};
//execute to check a booking have file? is not file and file size is not show 
fileSize(someBooking.value.fileSize);

const checkNote = () => {
    if (someBooking.value.eventNotes == null) {
        note.value = '';
    } else note.value = someBooking.value.eventNotes;
}

let editData = reactive({
    idBooking: someBooking.value.idBooking,
    eventCategory: { eventCategoryId: someBooking.value.eventCategoryId },
    eventStartTime: new Date(bookingPresentTime.value).toISOString(),
    eventDuration: someBooking.value.eventDuration,
    eventNotes: someBooking.value.eventNotes,
    fileName: null
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
    editData.fileName = fileDetail.fileName
    console.log(editData);
}

//------------------------------------------------check invalid form input --------------------------------------------------------------------

const IsFileSelected = computed(() => {
    return isInvalid.value && (fileSelected.value != undefined || fileSelected.value != null);
});

const IsFileHaved = computed(() => {
    return fileDetail.fileSize != 0 || fileDetail.fileName != null;
});

const isFileSizeNotOver = computed(() => {
    return isInvalid.value && (fileDetail.fileSize > 10 && fileDetail.fileSizeUnit == "MB");
});

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
    console.log("hello");
}
const changeCreateDialogFalse = (bookingEdit) => {
    bookingEdit.createDialog = false
}

//check input is have invalid ?
const changeCreateDialogTrue = (bookingEdit) => {
    isInvalid.value = false
    if ((note.value.length > 500) || (new Date(bookingPresentTime.value).toISOString() < new Date().toISOString()) || fileDetail.fileSize > 10 && fileDetail.fileSizeUnit == "MB") {
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
const editBookingEvent = async (editData, bookingEdit, loopEdit, fileSelected) => {
    bookRes.editBooking(editData, bookingEdit, loopEdit, fileSelected);
    // if (res.status === 200) {
    //     alert('Edit Booking Success!');
    //     bookingEdit.createDialog = !bookingEdit.createDialog;
    //     bookingEdit.statusClickEdit = !bookingEdit.statusClickEdit;
    //     loopEdit.statusClickSeeDetails = !loopEdit.statusClickSeeDetails;
    //     emits('EditbookingId', editData)
    //     console.log('edited successfully')
    // } else {
    //     // bookingEdit.createDialog = !bookingEdit.createDialog;
    //     console.log('error, cannot be added')
    // }
}

//fetch data
onBeforeMount(async () => {
    // const getAllBooks = await getBookings();
    // bookingLists.value = await getAllBooks.json();
    // const getAllCategory = await getEventCategory();
    // categoryList.value = await getAllCategory.json();

    // responseGetAllBookings.value = await getBookings();
    // bookingLists.value = responseGetAllBookings.value.data;
    // responseGetAllCategory.value = await getEventCategory();
    // categoryList.value = await responseGetAllCategory.value.data;
    checkNote();
    // console.log(bookingLists.value);
    // console.log(categoryList.value);
    // console.log(someBooking.value);
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

                    <div class="row-start-7 col-start-1 col-span-2 p-1 rounded-lg text-center">
                        file :
                    </div>

                    <div class="row-start-8 col-start-1 col-span-2 p-1 rounded-lg text-center">
                        <div class="grid grid-flow-rows grid-cols-6 content-center justify-items-center text-center">

                            <div v-if="IsFileHaved"
                                class="grid row-start-1 col-start-2 col-end-3 col-span-1 content-center justify-items-center">
                                <input id="file1" name="file1"
                                    class="hidden bg-gray-200 rounded w-[100%] border disabled:text-gray-400"
                                    type="file" @change="fileOnInput" />
                                <label for="file1" class="p-1 bg-gray-200 rounded border text-center">file
                                    choose</label>
                            </div>

                            <div v-else class="grid relative z-10 row-start-1 col-start-3 col-end-5 col-span-2 content-center justify-items-center">
                                <input id="file1" name="file1"
                                    class="hidden bg-gray-200 rounded w-[100%] border disabled:text-gray-400"
                                    type="file" @change="fileOnInput" />
                                <label for="file1" class="p-1 bg-gray-200 rounded border text-center">file
                                    choose</label>
                            </div>

                            <div class="grid row-start-1 col-start-3 col-end-5 col-span-2 w-[95%] content-center">
                                <p class="truncate text-center">{{ fileDetail.fileName }}</p>
                            </div>

                            <!-- <div v-if="isFileSizeNotOver" class="grid row-start-2 col-start-2 col-end-6 col-span-4">
                                <label class="text-red-500 text-center">
                                    * Please choose file maximum 10 MB
                                </label>
                            </div> -->

                            <div class="grid row-start-2 col-start-2 col-end-6 col-span-4">
                                <label class="text-red-500 text-center">
                                    * Please choose file maximum 10 MB
                                </label>
                            </div>

                            <div class="grid row-start-1 col-start-5 col-end-6 col-span-1 content-center">
                                <p class="text-left h-full">
                                    {{
                                            fileDetail.fileSize == 0 ? "" : fileDetail.fileSize.toFixed(2)
                                    }}
                                    {{ fileDetail.fileSizeUnit }}
                                </p>
                            </div>

                            <div v-if="IsFileHaved"
                                class="grid row-start-1 col-start-6 col-end-7 col-span-1 justify-items-center">
                                <button @click="deleteFile" class="w-[100%] py-1 bg-[#F24052] text-white rounded-lg">
                                    <svg class="hover:text-[#F24052] m-auto" width="1.5em" height="1.5em"
                                        viewBox="0 0 24 24">
                                        <path fill="currentColor"
                                            d="M6 19a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V7H6v12m2.46-7.12l1.41-1.41L12 12.59l2.12-2.12l1.41 1.41L13.41 14l2.12 2.12l-1.41 1.41L12 15.41l-2.12 2.12l-1.41-1.41L10.59 14l-2.13-2.12M15.5 4l-1-1h-5l-1 1H5v2h14V4h-3.5Z">
                                        </path>
                                    </svg>
                                </button>
                            </div>
                        </div>
                    </div>

                    <div class="row-start-9 col-start-1 col-span-2 p-1 rounded-lg text-center">
                        EventNotes :
                    </div>
                    <div class="row-start-9 col-start-2 col-span-1 p-1 rounded-lg text-right text-gray-400">
                        {{ note.length }}/500
                    </div>

                    <div class="row-start-10 col-start-1 col-end-3 col-span-2">
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

                    <div class="row-start-11 mt-5 col-start-1">
                        <button class="bg-[#F24052] text-white rounded-lg w-6/12 h-full m-auto py-2"
                            @click="changeCancelDialogTrue(someBooking)">cancel</button>
                    </div>

                    <div class="row-start-11 mt-5 col-start-2">
                        <button class="bg-[#00A28B] text-white rounded-lg w-6/12 h-full m-auto py-2"
                            @click="[changeCreateDialogTrue(someBooking), sentEditData()]">confirm</button>
                    </div>

                    <div class="text-red-500" v-show="isInputNotes || isInputTimeOlds || isInputTimes">
                        *some input is invalid
                    </div>

                    <Cancel v-if="someBooking.cancelDialog" @onClickCancelNo="changeCancelDialogFalse(someBooking)"
                        @onClickCancelYes="closeEditDialog(someBooking)" />

                    <Confirm v-if="someBooking.createDialog" @onClickConfirmNo="changeCreateDialogFalse(someBooking)"
                        @onClickConfirmYes="editBookingEvent(editData, someBooking, loopEdit, fileSelected)" />
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.row-start-8 {
    grid-row-start: 8;
}

.row-start-9 {
    grid-row-start: 9;
}

.row-start-10 {
    grid-row-start: 10;
}

.row-start-11 {
    grid-row-start: 11;
}
</style>
