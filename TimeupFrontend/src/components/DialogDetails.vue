<script setup>
import { ref, onBeforeMount, computed} from "vue";
import { getBookingId, removeBooking } from "../stores/book.js";
import ConfirmDelete from "./ConfirmDelete.vue";
import EditBooking from './EditBooking.vue';
import moment from "moment";

const emits = defineEmits(["onCloseDetails", "idConfirmDelete", "EditIdFromEdit"]);
const props = defineProps({
    bookings: {
        type: Object,
        require: true,
    },
});

// booking form for-loop and fetch to get detail from id 
const bookingsDetails = ref([]);

// booking form for-loop
const loopBooking = computed(() => props.bookings);

//-----------------------------pop-up-dialog---------------------------------
const closeDetails = (event) => {
    if (event.target.id == "modalDetails") {
        emits("onCloseDetails");
    } else {
    }
};
const changeDeleteDialog = (booking) => {
    booking.statusClickDelete = !booking.statusClickDelete;
    console.log(booking.statusClickDelete);
};
const changeEditDialog = (booking) => {
    booking.statusClickEdit = !booking.statusClickEdit;
    console.log(booking.statusClickEdit);
}
//-----------------------------pop-up-dialog---------------------------------

// get edit booking id to send back
const getEditIdFromEdit = (EditId) => {
    emits('EditIdFromEdit', EditId)
}

// remove booking
const removeBookingEvent = async (deleteBookingId, booking, loopBooking) => {
    alert('Delete Booking Success!');
    emits("idConfirmDelete", deleteBookingId);
    await removeBooking(deleteBookingId, booking, loopBooking);
};

//fetch data
onBeforeMount(async () => {
    bookingsDetails.value = await getBookingId(props.bookings.idBooking);
    console.log(bookingsDetails.value);
});

</script>

<template>
    <div id="modalDetails" @click="closeDetails"
        class="font overflow-x-hidden overflow-y-auto fixed inset-0 z-10 outline-none focus:outline-none justify-center items-center flex bg-black bg-opacity-20">

        <div class="">
            <div class="relative bg-white rounded-lg text-xl p-5 px-12">
                <div class="w-36 m-auto text-1xl text-center">
                    ID : {{ bookingsDetails.idBooking }}
                </div>
                <img class="absolute top-0 right-0 text-4xl text-lg font-normal h-[30px] w-[30px] mt-3 mr-7 cursor-pointer"
                    @click="$emit('onCloseDetails')" src="/images/cancel.png" alt="cancel" />
                <div class="grid grid-flow-row grid-cols-2 p-1 gap-1 pt-5 text-center">
                    <div class="row-start-1 col-start-1 col-span-2 p-1 rounded-lg">
                        Category : {{ bookingsDetails.eventCategoryName }}
                    </div>
                    <div class="row-start-2 col-start-1 col-span-2 p-1 rounded-lg  ">
                        Name : {{ bookingsDetails.bookingName }}
                    </div>
                    <div class="row-start-3 col-start-1 col-span-2 p-1 rounded-lg">
                        Email : {{ bookingsDetails.bookingEmail }}
                    </div>
                    <div class="row-start-4 col-start-1 col-span-2 p-1 rounded-lg">
                        StartTime :
                    </div>
                    <div class="row-start-5 col-start-1 col-span-2 p-1 rounded-lg">
                        {{
                                moment(bookingsDetails.eventStartTime)
                                    .local()
                                    .format("DD MMMM YYYY hh:mm A")
                        }}
                        -
                        {{
                                moment(bookingsDetails.eventStartTime)
                                    .local()
                                    .add(bookingsDetails.eventDuration, "m")
                                    .format("DD MMMM YYYY hh:mm A")
                        }}
                    </div>
                    <div class="row-start-6 col-start-1 col-span-2 p-1 rounded-lg">
                        Duration : {{ bookingsDetails.eventDuration }} minute
                    </div>

                    <div class="row-start-7 col-start-1 col-span-2 p-1 rounded-lg">
                        EventNotes :
                    </div>

                    <div class="row-start-8 col-start-1 col-end-3 col-span-2">
                        <textarea class="bg-gray-200 w-full resize-none rounded text-center bg-"
                            placeholder="No Message" disabled v-model="bookingsDetails.eventNotes" name="" id=""
                            cols="90" rows="5">{{
                                    bookingsDetails.eventNotes == null || bookingsDetails.eventNotes.trim() === ''
                                        ? ""
                                        : bookingsDetails.eventNotes
                            }}</textarea>
                    </div>
                    <div class="row-start-9 mt-5 col-start-1">
                        <button class="bg-[#499D6B] text-white rounded-lg w-6/12 h-full m-auto py-2"
                            @click="changeEditDialog(bookingsDetails)">edit</button>
                    </div>
                    <div class="row-start-9 mt-5 col-start-2">
                        <button class="bg-[#F97473] text-white rounded-lg w-6/12 h-full m-auto py-2"
                            @click="changeDeleteDialog(bookingsDetails)">delete</button>
                    </div>

                    <ConfirmDelete v-if="bookingsDetails.statusClickDelete" :bookingsFromDetails="bookingsDetails"
                        :bookingsFromLoopBookings="loopBooking" @onCancelDelete="changeDeleteDialog(bookingsDetails)"
                        @onConfirmDelete="removeBookingEvent" />

                    <EditBooking v-if="bookingsDetails.statusClickEdit" :loopEdit="loopBooking"
                        :bookingsDetailsEdit="bookingsDetails" @EditbookingId="getEditIdFromEdit" />
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.bg-gray {
    background-color: #e2dddd;
}

.border-gray {
    border-color: #6d6d6d;
}
</style>
