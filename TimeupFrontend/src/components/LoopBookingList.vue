<script setup>
import { ref, onBeforeMount, computed } from 'vue'
import moment from 'moment';
import DialogDetails from './DialogDetails.vue';
import ConfirmDelete from './ConfirmDelete.vue';
import { getEventCategory, removeBooking } from "../stores/book.js";

const emits = defineEmits(["idDialogDetails", "EditIdFromDialog"])
const props = defineProps({
    bookingLists: {
        type: [Object],
        require: true
    },
    statusScheduled: {
        type: String,
        default: "No Scheduled Events"
    }
})

const bookingList = computed(() => props.bookingLists);
const categorys = ref([]);

//---------------------------pop-up-dialog-------------------------------------
const changeSeeDetailsDialog = (booking) => {
    booking.statusClickSeeDetails = !booking.statusClickSeeDetails
    // console.log(booking.statusClickSeeDetails);
}
const changeDeleteDialog = (booking) => {
    booking.statusClickDelete = !booking.statusClickDelete
    // console.log(booking.statusClickDelete);
}
//---------------------------pop-up-dialog-------------------------------------

//set color
const colorBg = (booking) => {
    for (let category of categorys.value) {
        if (booking.eventCategoryId == category.eventCategoryId) {
            return category.eventColor
        }
    }
}
// get delete booking id to send back
const getIdFromDialog = (idDelete) => {
    emits('idDialogDetails', idDelete)
}

// get edit booking id to send back
const getEditIdFromDialog = (EditId) => {
    emits('EditIdFromDialog', EditId)
}

// remove booking 
const removeBookingEvent = async (deleteBookingId, booking, loopBooking) => {
    alert('Delete Booking Success!');
    emits('idDialogDetails', deleteBookingId)
    await removeBooking(deleteBookingId, booking, loopBooking)
}

//fetch data
onBeforeMount(async () => {
    const getAllCategory = await getEventCategory();
    categorys.value = await getAllCategory.json();
})

</script>
 
<template>

    <div>
        <div>
            <div v-if="bookingLists == ''" class="flex flex-warp justify-center bg-gray-800 text-white text-xl">
                {{ props.statusScheduled }}
            </div>

            <div v-if="!(bookingLists == '')" class="grid grid-flow-row grid-cols-1 mb-24">
                <div class="w-[90%] m-auto mb-1 bg-[#E2DDDD] rounded-lg  p-2 text-center px-2">
                    <div class="grid grid-flow-row grid-cols-12 flex p-1 ">
                        <div class="row-start-1 col-start-1 col-end-3 col-span-2  uppercase text-lg font-bold">date
                        </div>
                        <div class="row-start-1 col-start-3 col-end-5 col-span-2  uppercase text-lg font-bold">time
                        </div>
                        <div class="row-start-1 col-start-5 col-end-7 col-span-2  uppercase text-lg font-bold">category
                        </div>
                        <div class="row-start-1 col-start-7 col-end-9 col-span-2  uppercase text-lg font-bold">duration
                        </div>
                        <div class="row-start-1 col-start-9 col-end-11 col-span-2 uppercase text-lg font-bold ">name
                        </div>
                    </div>
                </div>
                <div class="overflow-y-auto overflow-x-hidden h-[460px] mr-[95px]">
                    <div v-for="(booking, index) in bookingList" :key="index"
                        class="mx-24 my-1 bg-white rounded-lg text-xl w-[95%]">
                        <div class="grid grid-flow-row grid-cols-12 flex py-3 text-center px-2">

                            <div class="row-start-1 col-start-1 col-end-3 p-1 mb-1.5 rounded-lg ">{{
                                    moment(booking.eventStartTime).local().format("DD MMMM YYYY")
                            }}</div>

                            <div class="row-start-1 col-start-3 col-end-5 p-1 mb-1.5 rounded-lg ">{{
                                    moment(booking.eventStartTime).local().format('hh:mm A')
                            }}
                                -
                                {{
                                        moment(booking.eventStartTime).local().add(booking.eventDuration, 'm')
                                            .format('hh:mm A')
                                }}
                            </div>

                            <div class="row-start-1 col-start-5 col-end-7 p-1 mb-1.5 rounded-lg truncate"
                                :style="[`background-color:${colorBg(booking)};`]">{{ booking.eventCategoryName
                                }} </div>

                            <div class="row-start-1 col-start-7 col-end-9 p-1 mb-1.5 rounded-lg ">{{
                                    booking.eventDuration
                            }} minute </div>

                            <div class="row-start-1 col-start-9 col-end-11 p-1 mb-1.5 rounded-lg ">
                                <p class="truncate">
                                    {{ booking.bookingName
                                    }}
                                </p>
                            </div>

                            <button
                                class="row-start-1 col-start-11 bg-[#74ABFF] text-white p-1 rounded-lg uppercase w-11/12 h-full m-auto"
                                @click="changeSeeDetailsDialog(booking)">Detail</button>
                            <button
                                class="row-start-1 col-start-12 bg-[red] text-white rounded-lg uppercase w-11/12 h-full m-auto"
                                @click="changeDeleteDialog(booking)">delete</button>

                            <DialogDetails v-if="booking.statusClickSeeDetails"
                                @onCloseDetails="changeSeeDetailsDialog(booking)" :bookings="booking"
                                @idConfirmDelete="getIdFromDialog" @EditIdFromEdit="getEditIdFromDialog" />

                            <ConfirmDelete v-if="booking.statusClickDelete" :bookingsFromDetails="booking"
                                :bookingsFromLoopBookings="booking" @onCancelDelete="changeDeleteDialog(booking)"
                                @onConfirmDelete="removeBookingEvent" />
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

</template>
 
<style scoped>
/* width */
::-webkit-scrollbar {
    width: 10px;
}

/* Track */
::-webkit-scrollbar-track {
    background: #f1f1f1;
}

/* Handle */
::-webkit-scrollbar-thumb {
    background: #888;
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
    background: #555;
}

::placeholder {
    font-style: italic;
}
</style>
