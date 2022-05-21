<script setup>
import { ref, reactive, onBeforeMount, computed, onBeforeUpdate } from "vue";
import moment from "moment";
import Cancel from "../components/Cancel.vue";
import Confirm from "../components/Confirm.vue";
import { getEventCategory } from "../stores/book.js";
import { getBookings } from "../stores/book.js";
const emits = defineEmits(["EditbookingId"]);
const props = defineProps({
    bookingsDetailsEdit: {
        type: [Object],
        require: true,
    },
    loopEdit: {
        type: [Object],
        require: true
    }
    // dialog: false
});
console.log(props.bookingsDetailsEdit);

const bookingToEdit = computed(() => props.bookingsDetailsEdit);
const someBooking = ref(bookingToEdit);
const note = ref(someBooking.value.eventNotes);
const bookingPresentTime = ref(moment.utc(someBooking.value.eventStartTime).local().format("YYYY-MM-DDTHH:mm"));
// console.log("some value "+someBooking.value.eventStartTime);
// console.log("some value moment"+ moment.utc(someBooking.value.eventStartTime).local().format("YYYY-MM-DDTHH:mm"));
console.log("book of some value" + bookingPresentTime.value);
const categoryList = ref([]);
const bookingLists = ref([]);

let isInvalid = ref(false);
let isOverlap = ref(false);
console.log(note.value);

const localPresentTime = moment.utc().local().format("YYYY-MM-DDTHH:mm");

let date = new Date();
console.log(date);
date.setMonth(date.getMonth() + 3);
let maxlocalPresentTime = moment(date).format("YYYY-MM-DDTHH:mm");
let maxdateIndexSelect = ref(maxlocalPresentTime);

let editData = reactive({
    idBooking: someBooking.value.idBooking,
    // bookingName: someBooking.value.bookingName,
    // bookingEmail: someBooking.value.bookingEmail,
    eventCategory: { eventCategoryId: someBooking.value.eventCategoryId },
    eventStartTime: new Date(bookingPresentTime.value).toISOString(),
    eventDuration: someBooking.value.eventDuration,
    eventNotes: someBooking.value.eventNotes,
})

console.log(editData);

const isInputNotes = computed(() => {
    return isInvalid.value && note.value.length > 500;
});

const isInputTimeOlds = computed(() => {
    return (isInvalid.value && (new Date(bookingPresentTime.value).toISOString() < new Date().toISOString()));
});

const isInputTimes = computed(() => {
    const localEndTime = `${moment.utc(editData.eventStartTime).add(editData.eventDuration, 'm').format("YYYY-MM-DDTHH:mm:ss")}Z`;
    for (let booking of bookingLists.value) {
        // if (bookingPresentTime.value === (moment.utc(someBooking.value.eventStartTime).local().format("YYYY-MM-DDTHH:mm"))) {
        //     break;
        // }
        if (editData.eventCategory.eventCategoryId === booking.eventCategoryId) {
            if ((editData.eventStartTime < booking.eventEndTime) && (localEndTime > booking.eventStartTime)  && (editData.idBooking != booking.idBooking)) {
                isOverlap.value = true
                isInvalid.value = true
                console.log("overlap")
                break;
            } else {
                isOverlap.value = false
                console.log("not overlap")
            }
        }
    }
    return (isInvalid.value && isOverlap.value)
});

// const categoryIndexSelect = ref([]);

// categoryList.value.forEach(categoryId => { categoryIndexSelect.value.push(categoryId.eventCategoryId) })
// console.log(categoryIndexSelect.value.eventCategoryId);
// let index = ref(0)
// let arr = ["Project Management Clinic" , "DevOps/Infra Clinic" , "Database Clinic" , "Client-side Clinic" , "Server-side Clinic" , "Other"]
// let check = ()=>{

//            for(let i = 0 ; i < arr.length ; i ++){
//              if(arr.includes(someBooking.value.eventCategoryName)){
//                  if(arr[i] === someBooking.value.eventCategoryName){
//                      index.value = i + 1
//                  }
//              }
//            }    
// }

// console.log(arr.includes(someBooking.value.eventCategoryName ))
// console.log(someBooking.value.eventCategoryName)
// console.log(index.value)

// console.log(someBooking.value.eventStartTime);
// console.log(bookingPresentTime.value);

const closeEdit = (event,bookingEdit) => {
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

const changeCreateDialogTrue = (bookingEdit) => {
    // const localEndTime = `${moment.utc(editData.eventStartTime).add(editData.eventDuration, 'm').format("YYYY-MM-DDTHH:mm:ss")}Z`;

    // for (let booking of bookingLists.value) {
    //     // if (bookingPresentTime.value === (moment.utc(someBooking.value.eventStartTime).local().format("YYYY-MM-DDTHH:mm"))) {
    //     //     break;
    //     // }
    //     if (editData.eventCategory.eventCategoryId === booking.eventCategoryId) {
    //         if (((editData.eventStartTime >= booking.eventStartTime) && (editData.eventStartTime <= booking.eventEndTime))
    //             || ((localEndTime >= booking.eventStartTime) && (localEndTime <= booking.eventEndTime))
    //             || (editData.eventStartTime <= booking.eventStartTime) && ((localEndTime) >= booking.eventEndTime)
    //             || (new Date(bookingPresentTime.value).toISOString() < new Date().toISOString())
    //         ) {
    //             isInvalid.value = true
    //             console.log("local start " + editData.eventStartTime)
    //             console.log("local end " + localEndTime);
    //             console.log("book start " + booking.eventStartTime)
    //             console.log("book end " + booking.eventEndTime)
    //             console.log("overlap")
    //             break;
    //         } else {
    //             isInvalid.value = false
    //             console.log("local start " + editData.eventStartTime)
    //             console.log("local end " + localEndTime);
    //             console.log("book start " + booking.eventStartTime)
    //             console.log("book end " + booking.eventEndTime)
    //             console.log("not overlap")
    //         }
    //     }
    // }

    isInvalid.value = false
    if ((note.value.length > 500) || (new Date(bookingPresentTime.value).toISOString() < new Date().toISOString())) {
        console.log("first if");
        isInvalid.value = true
    } else {
        isInvalid.value = false
        console.log("first else");
    }
    if (isOverlap.value == false && isInvalid.value == false) {
        bookingEdit.createDialog = true
        isInvalid.value = false
        console.log("second if");
    }
    console.log("not overlap and isInvalid " + isInvalid.value);

    // if ((note.value.length > 500) || (new Date(bookingPresentTime.value).toISOString() < new Date().toISOString())) {
    //     console.log("aaaaaaa");
    //     isInvalid.value = true
    // }else {
    //      console.log("bbbbbb");
    //     bookingEdit.createDialog = !bookingEdit.createDialog;
    //     isInvalid.value = false
    // }
    // console.log(isInvalid.value);
    // console.log(isOverlap.value);

}

const putEditDialog = (idBooking, bookingEdit, loopEdit) => {
    bookingEdit.createDialog = !bookingEdit.createDialog;
    bookingEdit.statusClickEdit = !bookingEdit.statusClickEdit;
    loopEdit.statusClickSeeDetails = !loopEdit.statusClickSeeDetails;
    console.log(idBooking);
    console.log(editData);
}

const selectTime = () => {
    editData.eventStartTime = new Date(bookingPresentTime.value).toISOString()
    console.log(editData.eventStartTime);
    console.log(bookingPresentTime.value);
    // categorySelect();
    // console.log(editData.eventCategory.eventCategoryId);
}

const sentEditData = () => {
    editData.eventStartTime = new Date(bookingPresentTime.value).toISOString()
    editData.eventNotes = note.value
    console.log(editData);
}

onBeforeMount(async () => {
    const getAllBooks = await getBookings();
    bookingLists.value = await getAllBooks.json();
    const getAllCategory = await getEventCategory();
    categoryList.value = await getAllCategory.json();
    console.log(bookingLists.value);
    console.log(categoryList.value);
    console.log(someBooking.value);
})

// onBeforeUpdate(async ()=>{
//    check()
//     console.log(index.value)
// })



// method PUT
// const editingNote = ref({})
// const toEditMode = (editNote) => {
//   console.log(editNote)
//   editingNote.value = editNote
// }
const editBooking = async (editNoteId, editData, bookingEdit, loopEdit) => {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/booking/${editNoteId}`, {
        method: 'PUT',
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify({
            // idBooking: editData.idBooking,
            // bookingName: editData.bookingName,
            // bookingEmail: editData.bookingEmail,
            // eventCategory: { eventCategoryId: editData.eventCategory.eventCategoryId },
            eventStartTime: editData.eventStartTime,
            // eventDuration: editData.eventDuration,
            eventNotes: editData.eventNotes,
        })
    })
    // console.log(editData.eventStartTime);
    if (res.status === 200) {
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

</script>

<template>
    <div id="modalEdit" @click="closeEdit($event,someBooking)"
        class="font overflow-x-hidden overflow-y-auto fixed inset-0 z-10 outline-none focus:outline-none justify-center items-center flex bg-black bg-opacity-20">
        <!-- <div class=" ">
        {{bookings.bookings}}
    </div> -->
        <div class="">
            <div class="relative bg-white rounded-lg text-xl p-5 px-12">
                <div class="w-36 m-auto text-1xl text-center">
                    <!-- ID : {{ bookingToEdit.idBooking }}  -->
                    ID : {{ someBooking.idBooking }}
                    <!-- {{bookingToEdit}} -->
                </div>
                <img class="absolute top-0 right-0 h-16 w-16 text-4xl text-lg font-normal h-[30px] w-[30px] mt-3 mr-7 cursor-pointer"
                    @click="changeCancelDialogTrue(someBooking)" src="/images/cancel.png" alt="cancel" />
                <div class="grid grid-flow-row grid-cols-2 p-1 gap-1 pt-5 text-center">
                    <div class="row-start-1 col-start-1 col-span-2 p-1 rounded-lg">
                        <!-- Category : {{ bookingToEdit.eventCategoryName }} -->
                        Category : {{ someBooking.eventCategoryName }}
                    </div>
                    <div class="row-start-2 col-start-1 col-span-2 p-1 rounded-lg">
                        <!-- Name : {{ bookingToEdit.bookingName }} -->
                        Name : {{ someBooking.bookingName }}
                    </div>
                    <div class="row-start-3 col-start-1 col-span-2 p-1 rounded-lg">
                        <!-- Email : {{ bookingToEdit.bookingEmail }} -->
                        Email : {{ someBooking.bookingEmail }}
                    </div>
                    <div class="row-start-4 col-start-1 col-span-2 p-1 rounded-lg">
                        StartTime :
                    </div>
                    <!-- {{moment.utc(bookingsDetails.eventStartTime).format("DD MMMM YYYY")}} -->
                    <!-- {{ moment.utc(moment.utc(bookingsDetails.eventStartTime).format()).local().format("DD MMMM YYYY") }} -->
                    <!-- {{  moment(bookingsDetails.eventStartTime).local().format("DD MMMM YYYY HH:mm A") }} -->
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
                        <!-- {{
                                moment(bookingToEdit.eventStartTime)
                                    .local()
                                    .format("DD MMMM YYYY HH:mm A")
                        }}
                        -
                        {{
                                moment(bookingToEdit.eventStartTime)
                                    .local()
                                    .add(bookingToEdit.eventDuration, "m")
                                    .format("HH:mm A")
                        }} -->
                    </div>
                    <!-- <div class="row-start-5 col-start-1 p-1  bg-gray rounded-lg "> -->
                    <!-- {{  moment.utc(moment.utc(bookingsDetails.eventStartTime).format()).local().format("h:mm A") }} -->
                    <!-- {{ moment(bookingsDetails.eventStartTime).local().format("HH:mm A") }} - {{ moment(bookingsDetails.eventStartTime).local().add(bookingsDetails.eventDuration, 'm').format('HH:mm A')}} -->
                    <!-- {{moment.utc(bookingsDetails.eventStartTime).format("h:mm A")}} -->
                    <!-- </div> -->
                    <div class="row-start-6 col-start-1 col-span-2 p-1 rounded-lg">
                        <!-- Duration : {{ bookingToEdit.eventDuration }} minute -->
                        Duration : {{ someBooking.eventDuration }} minute
                    </div>

                    <div class="row-start-7 col-start-1 col-span-2 p-1 rounded-lg">
                        EventNotes :
                    </div>
                    <div class="row-start-7 col-start-2 col-span-1 p-1 rounded-lg text-right text-gray-400">
                        {{ note.length }}/500
                    </div>
                    <!-- <div class="row-start-8 col-start-1 col-span-2 p-1 rounded-lg ">{{
                            bookingsDetails.eventNotes == null ? "-" : bookingsDetails.eventNotes
                    }}</div> -->

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

                    <!-- <div class="row-start-9 mt-5 col-start-1">
                        <button class="bg-[#499D6B] text-white rounded-lg w-6/12 h-full m-auto py-2"
                            @click="[changeCreateDialog(bookingToEdit), sentEditData()]">confirm</button>
                    </div> -->
                    <div class="row-start-9 mt-5 col-start-1">
                        <button class="bg-[#499D6B] text-white rounded-lg w-6/12 h-full m-auto py-2"
                            @click="[changeCreateDialogTrue(someBooking), sentEditData()]">confirm</button>
                    </div>


                    <!-- <div class="row-start-9 mt-5 col-start-2">
                        <button class="bg-[#F97473] text-white rounded-lg w-6/12 h-full m-auto py-2"
                            @click="changeCancelDialog(bookingToEdit)">cancel</button>
                    </div> -->
                    <div class="row-start-9 mt-5 col-start-2">
                        <button class="bg-[#F97473] text-white rounded-lg w-6/12 h-full m-auto py-2"
                            @click="changeCancelDialogTrue(someBooking)">cancel</button>
                    </div>

                    <div class="text-red-500" v-show="isInputNotes || isInputTimeOlds || isInputTimes">
                        *some input is invalid
                    </div>

                    <!-- <Cancel v-if="bookingToEdit.cancelDialog" @onClickCancelNo="changeCancelDialog(bookingToEdit)"
                        @onClickCancelYes="closeEditDialog(bookingToEdit)" /> -->

                    <Cancel v-if="someBooking.cancelDialog" @onClickCancelNo="changeCancelDialogFalse(someBooking)"
                        @onClickCancelYes="closeEditDialog(someBooking)" />



                    <!-- <Confirm v-if="bookingToEdit.createDialog" @onClickCreateNo="changeCreateDialog(bookingToEdit)"
                        @onClickCreateYes="putEditDialog(editData.idBooking, bookingToEdit, loopEdit)" /> -->

                    <!-- <Confirm v-if="bookingToEdit.createDialog" @onClickCreateNo="changeCreateDialog(bookingToEdit)"
                        @onClickCreateYes="editBooking(editData.idBooking,editData, bookingToEdit, loopEdit )" /> -->

                    <Confirm v-if="someBooking.createDialog" @onClickCreateNo="changeCreateDialogFalse(someBooking)"
                        @onClickCreateYes="editBooking(editData.idBooking, editData, someBooking, loopEdit)" />
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
</style>
