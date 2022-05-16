<script setup>
import { ref, onBeforeMount, computed, onMounted } from "vue";
import { getBookingId } from "../stores/book.js";
import ConfirmDelete from "./ConfirmDelete.vue";
import { removeBooking } from "../stores/book.js";
import EditBooking from './EditBooking.vue';
import moment from "moment";

const emits = defineEmits(["onCloseDetails", "idConfirmDelete"]);
const props = defineProps({
    bookings: {
        type: [Object],
        require: true,
    },
    // dialog: false
});
console.log(props.bookings);

const bookingsDetails = ref([]);

const loopBooking = computed(() => props.bookings);

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

    // console.log(loopBooking);
    //     bookingsDetails.value.map((status)=>{
    //  status.statusClickDelete = ref(false)
    // })

    // Time.value = bookingsArray.value.eventStartTime
    // console.log(Time.value);
    // date.value = moment.utc(Time.value).format("DD MMMM YYYY")
    // startTime.value = moment.utc(Time.value).format("h:mm")
});

const closeDetails = (event) => {
    if (event.target.id == "modalDetails") {
        emits("onCloseDetails");
    } else {
    }
};

const changeDeleteDialog = (booking) => {
    booking.statusClickDelete = !booking.statusClickDelete;
    console.log(booking.statusClickDelete);
    // dialogs.value = !dialogs.value
    // console.log(dialogs.value);
};

const confirmDelete = (booking, loopBooking) => {
    booking.statusClickDelete = !booking.statusClickDelete;
    loopBooking.statusClickSeeDetails = !loopBooking.statusClickSeeDetails;
};

// const confirmDelete = (booking) => {
//     booking.statusClickDelete = !booking.statusClickDelete
// }

const removeBookingEvent = async (deleteBookingId, booking, loopBooking) => {
    emits("idConfirmDelete", deleteBookingId);
    await removeBooking(deleteBookingId, booking, loopBooking);
};

const changeEditDialog = (booking)=>{
    booking.statusClickEdit = !booking.statusClickEdit;
    console.log(booking.statusClickEdit);
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
            <div class="relative bg-white rounded-lg text-xl p-5 px-12">
                <div class="w-36 m-auto text-1xl text-center">
                    ID : {{ bookingsDetails.idBooking }}
                </div>
                <img class="absolute top-0 right-0 h-16 w-16 text-4xl text-lg font-normal h-[30px] w-[30px] mt-3 mr-7 cursor-pointer"
                    @click="$emit('onCloseDetails')" src="/images/cancel.png" alt="cancel" />
                <div class="grid grid-flow-row grid-cols-2 p-1 gap-1 pt-5 text-center">
                    <div class="row-start-1 col-start-1 col-span-2 p-1 rounded-lg">
                        Category : {{ bookingsDetails.eventCategoryName }}
                    </div>
                    <div class="row-start-2 col-start-1 col-span-2 p-1 rounded-lg">
                        Name : {{ bookingsDetails.bookingName }}
                    </div>
                    <div class="row-start-3 col-start-1 col-span-2 p-1 rounded-lg">
                        Email : {{ bookingsDetails.bookingEmail }}
                    </div>
                    <div class="row-start-4 col-start-1 col-span-2 p-1 rounded-lg">
                        StartTime :
                    </div>
                    <!-- {{moment.utc(bookingsDetails.eventStartTime).format("DD MMMM YYYY")}} -->
                    <!-- {{ moment.utc(moment.utc(bookingsDetails.eventStartTime).format()).local().format("DD MMMM YYYY") }} -->
                    <!-- {{  moment(bookingsDetails.eventStartTime).local().format("DD MMMM YYYY HH:mm A") }} -->
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
                                    .format("hh:mm A")
                        }}
                    </div>
                    <!-- <div class="row-start-5 col-start-1 p-1  bg-gray rounded-lg "> -->
                    <!-- {{  moment.utc(moment.utc(bookingsDetails.eventStartTime).format()).local().format("h:mm A") }} -->
                    <!-- {{ moment(bookingsDetails.eventStartTime).local().format("HH:mm A") }} - {{ moment(bookingsDetails.eventStartTime).local().add(bookingsDetails.eventDuration, 'm').format('HH:mm A')}} -->
                    <!-- {{moment.utc(bookingsDetails.eventStartTime).format("h:mm A")}} -->
                    <!-- </div> -->
                    <div class="row-start-6 col-start-1 col-span-2 p-1 rounded-lg">
                        Duration : {{ bookingsDetails.eventDuration }} minute
                    </div>

                    <div class="row-start-7 col-start-1 col-span-2 p-1 rounded-lg">
                        EventNotes :
                    </div>
                    <!-- <div class="row-start-8 col-start-1 col-span-2 p-1 rounded-lg ">{{
                            bookingsDetails.eventNotes == null ? "-" : bookingsDetails.eventNotes
                    }}</div> -->

                    <div class="row-start-8 col-start-1 col-end-3 col-span-2">
                        <textarea class=" w-full resize-none rounded text-center" readonly
                            v-model="bookingsDetails.eventNotes" name="" id="" cols="90" rows="5">{{
                                    bookingsDetails.eventNotes == null
                                        ? "-"
                                        : bookingsDetails.eventNotes
                            }}</textarea>
                    </div>

                    <!-- template -->
                    <!-- div class="row-start-6 col-start-1 col-end-10 span-9"> <textarea
                            class="bg-gray-200 w-full resize-none rounded" name="" id="" cols="100" rows="5"  v-model="localData.eventNotes"></textarea>
                    </div> -->

                    <!-- <button class="row-start-1 col-start-11 bg-[#74ABFF] text-white p-1 rounded-lg uppercase w-11/12 h-full m-auto"  @click="changeSeeDetailsDialog(booking)">Detail</button>
                    <button class="row-start-1 col-start-12 bg-[red] text-white rounded-lg uppercase w-11/12 h-full m-auto" @click="changeDeleteDialog(booking)">delete</button> -->
                    <!-- template -->

                    <div class="row-start-9 mt-5 col-start-1">
                        <button class="bg-[#499D6B] text-white rounded-lg w-6/12 h-full m-auto py-2" @click="changeEditDialog(bookingsDetails)">edit</button>
                    </div>
                    <div class="row-start-9 mt-5 col-start-2">
                        <button class="bg-[#F97473] text-white rounded-lg w-6/12 h-full m-auto py-2" @click="changeDeleteDialog(bookingsDetails)" >delete</button>
                    </div>

                    <!-- <ConfirmDelete v-if="bookingsDetails.statusClickDelete" :bookingsFromDetails="bookingsDetails" @onCancelDelete="changeDeleteDialog(bookingsDetails)" @onConfirmDelete="confirmDelete(bookingsDetails,loopBooking)"/> -->

                    <ConfirmDelete v-if="bookingsDetails.statusClickDelete" :bookingsFromDetails="bookingsDetails"
                        :bookingsFromLoopBookings="loopBooking" @onCancelDelete="changeDeleteDialog(bookingsDetails)"
                        @onConfirmDelete="removeBookingEvent" />

                    <EditBooking v-if="bookingsDetails.statusClickEdit" :loopEdit="loopBooking" :bookingsDetailsEdit="bookingsDetails" @onClickCancelEdit="changeEditDialog(bookingsDetails)" />
                    <!-- <EditBooking v-if="bookingsDetails.statusClickEdit"  :bookingsEdit="bookingsDetails" /> -->
                    
                    <!-- <DialogDetails v-if="booking.statusClickSeeDetails" @onCloseDetails="changeSeeDetailsDialog(booking)" :bookings="booking"/> -->

                    <!-- <div class="row-start-8 col-start-1 col-end-2 p-1 mb-1.5 bg-gray rounded-lg">Id</div>
                        <div class="row-start-8 col-start-3 col-end-9 p-1 mb-1.5 bg-gray rounded-lg">{{ bookingsDetails.idBooking
                        }}</div> -->

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
