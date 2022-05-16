<script setup>
import { ref, reactive, onBeforeMount, computed , onBeforeUpdate  } from "vue";
import moment from "moment";
import Cancel from "../components/Cancel.vue";
import Confirm from "../components/Confirm.vue";
import { getEventCategory } from "../stores/book.js";
const emits = defineEmits(["onClickCancelEdit","EditbookingId"]);
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
const categoryList = ref([]);
console.log(note.value);
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

let editData = reactive({
    idBooking: someBooking.value.idBooking,
    // bookingName: someBooking.value.bookingName,
    // bookingEmail: someBooking.value.bookingEmail,
    // eventCategory: { eventCategoryId: index },
    eventStartTime: "",
    // eventDuration: someBooking.value.eventDuration,
    eventNotes: someBooking.value.eventNotes,
})

console.log(editData);
// console.log(someBooking.value.eventStartTime);
// console.log(bookingPresentTime.value);

const closeEdit = (event) => {
    if (event.target.id == "modalEdit") {
        emits("onClickCancelEdit")
    }
}

const changeCancelDialog = (bookingEdit) => {
    bookingEdit.cancelDialog = !bookingEdit.cancelDialog;
}

const closeEditDialog = (bookingEdit) => {
    bookingEdit.cancelDialog = !bookingEdit.cancelDialog;
    bookingEdit.statusClickEdit = !bookingEdit.statusClickEdit;
}

const changeCreateDialog = (bookingEdit) => {
    bookingEdit.createDialog = !bookingEdit.createDialog;
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
    categoryList.value = await getEventCategory();
 
    console.log(categoryList.value);
})

// onBeforeUpdate(async ()=>{
//    check()
//     console.log(index.value)
// })

// const categorySelect = () => {
//     editData.eventCategory.eventCategoryId = categoryList.value[categoryIndexSelect.value].eventCategoryId
//     // editData.eventDuration = categoryList.value[categoryIndexSelect.value].eventDuration
//     // alert(CategorySelect)
//     console.log(editData.eventCategory.eventCategoryId)
// }


// method PUT
// const editingNote = ref({})
// const toEditMode = (editNote) => {
//   console.log(editNote)
//   editingNote.value = editNote
// }
const editBooking = async (editNoteId,editData, bookingEdit, loopEdit) => {
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
        emits('EditbookingId',editData)
        // const editedNote = await res.json()
        // notes.value = notes.value.map((note) =>
        //   note.id === editedNote.id
        //     ? { ...note, noteDetail: editedNote.noteDetail }
        //     : note
        // )
        console.log('edited successfully')
    } else console.log('error, cannot be added')
}

</script>

<template>
    <div id="modalEdit" @click="closeEdit"
        class="font overflow-x-hidden overflow-y-auto fixed inset-0 z-10 outline-none focus:outline-none justify-center items-center flex bg-black bg-opacity-20">
        <!-- <div class=" ">
        {{bookings.bookings}}
    </div> -->
        <div class="">
            <div class="relative bg-white rounded-lg text-xl p-5 px-12">
                <div class="w-36 m-auto text-2xl text-center">
                    ID : {{ bookingToEdit.idBooking }}
                    <!-- {{bookingToEdit}} -->
                </div>
                <img class="absolute top-0 right-0 h-16 w-16 text-4xl text-lg font-normal h-[30px] w-[30px] mt-3 mr-7 cursor-pointer"
                    @click="changeCancelDialog(bookingToEdit)" src="/images/cancel.png" alt="cancel" />
                <div class="grid grid-flow-row grid-cols-2 p-1 gap-1 pt-5 text-center">
                    <div class="row-start-1 col-start-1 col-span-2 p-1 rounded-lg">
                        Category : {{ bookingToEdit.eventCategoryName }}
                    </div>
                    <div class="row-start-2 col-start-1 col-span-2 p-1 rounded-lg">
                        Name : {{ bookingToEdit.bookingName }}
                    </div>
                    <div class="row-start-3 col-start-1 col-span-2 p-1 rounded-lg">
                        Email : {{ bookingToEdit.bookingEmail }}
                    </div>
                    <div class="row-start-4 col-start-1 col-span-2 p-1 rounded-lg">
                        StartTime :
                    </div>
                    <!-- {{moment.utc(bookingsDetails.eventStartTime).format("DD MMMM YYYY")}} -->
                    <!-- {{ moment.utc(moment.utc(bookingsDetails.eventStartTime).format()).local().format("DD MMMM YYYY") }} -->
                    <!-- {{  moment(bookingsDetails.eventStartTime).local().format("DD MMMM YYYY HH:mm A") }} -->
                    <div class="row-start-5 col-start-1 col-span-2 p-1 rounded-lg">
                        <input type="datetime-local" v-model="bookingPresentTime" @change="selectTime()">
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
                        Duration : {{ bookingToEdit.eventDuration }} minute
                    </div>

                    <div class="row-start-7 col-start-1 col-span-2 p-1 rounded-lg">
                        EventNotes :
                    </div>
                    <!-- <div class="row-start-8 col-start-1 col-span-2 p-1 rounded-lg ">{{
                            bookingsDetails.eventNotes == null ? "-" : bookingsDetails.eventNotes
                    }}</div> -->

                    <div class="row-start-8 col-start-1 col-end-3 col-span-2">
                        <textarea class="bg-gray-200 w-full resize-none rounded text-center" v-model="note" name=""
                            id="" cols="90" rows="5">{{
                                    note == null
                                        ? "no message"
                                        : note
                            }}</textarea>
                    </div>

                    <div class="row-start-9 mt-5 col-start-1">
                        <button class="bg-[#499D6B] text-white rounded-lg w-6/12 h-full m-auto py-2"
                            @click="[changeCreateDialog(bookingToEdit), sentEditData()]">confirm</button>
                    </div>
                    <div class="row-start-9 mt-5 col-start-2">
                        <button class="bg-[#F97473] text-white rounded-lg w-6/12 h-full m-auto py-2"
                            @click="changeCancelDialog(bookingToEdit)">cancel</button>
                    </div>

                    <Cancel v-if="bookingToEdit.cancelDialog" @onClickCancelNo="changeCancelDialog(bookingToEdit)"
                        @onClickCancelYes="closeEditDialog(bookingToEdit)" />

                    <!-- <Confirm v-if="bookingToEdit.createDialog" @onClickCreateNo="changeCreateDialog(bookingToEdit)"
                        @onClickCreateYes="putEditDialog(editData.idBooking, bookingToEdit, loopEdit)" /> -->
                    <Confirm v-if="bookingToEdit.createDialog" @onClickCreateNo="changeCreateDialog(bookingToEdit)"
                        @onClickCreateYes="editBooking(editData.idBooking,editData, bookingToEdit, loopEdit )" />

                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
</style>
