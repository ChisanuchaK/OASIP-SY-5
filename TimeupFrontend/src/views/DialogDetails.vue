<script setup>
import { ref, onBeforeMount, computed, onMounted } from 'vue'
import { getBookingId } from "../stores/book.js";
import ConfirmDelete from './ConfirmDelete.vue';
// import { getBookings } from '../stores/book.js';
import moment from 'moment';


const emits = defineEmits(["onCloseDetails", "idConfirmDelete"])
const props = defineProps({
    bookings: {
        type: [Object],
        require: true
    },
    // dialog: false
})
console.log(props.bookings);

const bookingsDetails = ref([]);

const loopBooking = computed(() => props.bookings)

// const getCatagoryName = async ()=> {
//     let s = "kuy";
//     setTimeout(()  => {
//   s = bookingsDetails.value.eventCategory.eventCategoryName
// }, "100")
//     console.log(s);
//     return s;

// }

onBeforeMount(async () => {
    bookingsDetails.value = await getBookingId(props.bookings.idBooking);
    console.log(bookingsDetails.value);

    //     bookingsDetails.value.map((status)=>{
    //  status.statusClickDelete = ref(false)
    // })

    // Time.value = bookingsArray.value.eventStartTime
    // console.log(Time.value);
    // date.value = moment.utc(Time.value).format("DD MMMM YYYY")
    // startTime.value = moment.utc(Time.value).format("h:mm")
})


const closeDetails = (event) => {
    if (event.target.id == "modalDetails") {
        emits('onCloseDetails')
    } else { }
}

const changeDeleteDialog = (booking) => {
    booking.statusClickDelete = !booking.statusClickDelete
    console.log(booking.statusClickDelete);
    // dialogs.value = !dialogs.value
    // console.log(dialogs.value);
}

const confirmDelete = (booking, loopBooking) => {
    booking.statusClickDelete = !booking.statusClickDelete
    loopBooking.statusClickSeeDetails = !loopBooking.statusClickSeeDetails
}

// const confirmDelete = (booking) => {
//     booking.statusClickDelete = !booking.statusClickDelete
// }

const removeBooking = async (deleteBookingId, booking, loopBooking) => {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/booking/${deleteBookingId}`, {
        method: 'DELETE'
    })
    if (res.status === 200) {
        // bookingAll.value = bookingAll.value.filter((BookingInBookings) => (BookingInBookings.idBooking != deleteBookingId))
        booking.statusClickDelete = !booking.statusClickDelete
        loopBooking.statusClickSeeDetails = !loopBooking.statusClickSeeDetails
        emits('idConfirmDelete', deleteBookingId)
        // location.reload();
        console.log('deleted success');
    } else {
        console.log('error , cannot delete');
    }
}

// @click.self="$emit('closeAll')"
//  v-if="booking.idBooking == bookings.idBooking"
</script>
 
<template>
    <div id="modalDetails" @click="closeDetails"
        class="font overflow-x-hidden overflow-y-auto fixed inset-0 z-10 outline-none focus:outline-none justify-center items-center flex bg-black bg-opacity-20">
        <!-- <div class=" ">
        {{bookings.bookings}}
    </div> -->
        <div class="">
            <div class="relative bg-white border-8 border-gray rounded-lg text-xl p-5">
                <div class="w-36 m-auto text-2xl text-center">ID : {{ bookingsDetails.idBooking }}</div>
                <img class="absolute top-0 right-0 h-16 w-16 text-4xl text-lg font-normal h-[30px] w-[30px] mt-3 mr-7 cursor-pointer"
                    @click="$emit('onCloseDetails')" src="/images/cancel.png" alt="cancel">
                <div class="grid grid-flow-row grid-cols-5 flex p-1 gap-3 pt-10 ">

                    <div class="row-start-1 col-start-1 col-end-2 p-1  bg-gray rounded-lg">BookingName</div>
                    <div class="row-start-1 col-start-2 col-end-6 p-1  bg-gray rounded-lg ml-10">{{
                            bookingsDetails.bookingName
                    }}</div>

                    <div class="row-start-2 col-start-1 col-end-2 p-1  bg-gray rounded-lg">Category</div>
                    <div class="row-start-2 col-start-2 col-end-6 p-1  bg-gray rounded-lg ml-10">{{
                            bookingsDetails.eventCategoryName
                    }}</div>

                    <div class="row-start-3 col-start-1 col-end-2 p-1  bg-gray rounded-lg">Email</div>
                    <div class="row-start-3 col-start-2 col-end-6 p-1  bg-gray rounded-lg ml-10">{{
                            bookingsDetails.bookingEmail
                    }}</div>

                    <div class="row-start-4 col-start-1 col-end-2 p-1  bg-gray rounded-lg">Date</div>
                    <div class="row-start-4 col-start-2 col-end-6 p-1  bg-gray rounded-lg ml-10 ">
                        <!-- {{moment.utc(bookingsDetails.eventStartTime).format("DD MMMM YYYY")}} -->
                        {{ moment.utc(moment.utc(bookingsDetails.eventStartTime).format()).local().format("DD MMMM YYYY") }}

                    </div>

                    <div class="row-start-5 col-start-1 col-end-2 p-1  bg-gray rounded-lg">StartTime</div>
                    <div class="row-start-5 col-start-2 col-end-6 p-1  bg-gray rounded-lg ml-10">
                        {{  moment.utc(moment.utc(bookingsDetails.eventStartTime).format()).local().format("h:mm A") }}
                        <!-- {{moment.utc(bookingsDetails.eventStartTime).format("h:mm A")}} -->
                    </div>

                    <div class="row-start-6 col-start-1 col-end-2 p-1  bg-gray rounded-lg">Duration</div>
                    <div class="row-start-6 col-start-2 col-end-6 p-1  bg-gray rounded-lg ml-10">{{
                            bookingsDetails.eventDuration
                    }} minute</div>

                    <div class="row-start-7 col-start-1 col-end-2 p-1  bg-gray rounded-lg">EventNotes</div>
                    <div class="row-start-7 col-start-2 col-end-6 p-1  bg-gray rounded-lg ml-10">{{
                            bookingsDetails.eventNotes == null ? "-" : bookingsDetails.eventNotes
                    }}</div>

                    <button class="row-start-8 mt-5 col-start-1 p-3 bg-[#499D6B] text-white rounded-lg">edit</button>
                    <button class="row-start-8 mt-5 col-start-5 p-3 bg-[#F97473] text-white rounded-lg"
                        @click="changeDeleteDialog(bookingsDetails)">delete</button>

                    <!-- <ConfirmDelete v-if="bookingsDetails.statusClickDelete" :bookingsFromDetails="bookingsDetails" @onCancelDelete="changeDeleteDialog(bookingsDetails)" @onConfirmDelete="confirmDelete(bookingsDetails,loopBooking)"/> -->

                    <ConfirmDelete v-if="bookingsDetails.statusClickDelete" :bookingsFromDetails="bookingsDetails"
                        :bookingsFromLoopBookings="loopBooking" @onCancelDelete="changeDeleteDialog(bookingsDetails)"
                        @onConfirmDelete="removeBooking" />
                    <!-- <DialogDetails v-if="booking.statusClickSeeDetails" @onCloseDetails="changeSeeDetailsDialog(booking)" :bookings="booking"/> -->

                    <!-- <div class="row-start-8 col-start-1 col-end-2 p-1 mb-1.5 bg-gray rounded-lg">Id</div>
                        <div class="row-start-8 col-start-3 col-end-9 p-1 mb-1.5 bg-gray rounded-lg">{{ bookingsDetails.idBooking
                        }}</div> -->


                    <!-- <div
                            class="col-start-2 ml-4 mr-4 w-3/12">
                        </div> -->
                </div>
            </div>
        </div>
    </div>

</template>
 
<style scoped>
.bg-gray {
    background-color: #E2DDDD;
}

.border-gray {
    border-color: #6D6D6D;
}
</style>