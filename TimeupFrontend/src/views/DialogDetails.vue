<script setup>
import {ref,onBeforeMount} from 'vue'
import { getBookingId } from "../stores/book.js";
import moment from 'moment';

defineEmits(["onCloseDetails"])
const props = defineProps({
     bookings: {
        type: [Object],
        require: true
    },
    // dialog: false
})
console.log(props.bookings);

const bookingsDetails = ref([]);


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
    // Time.value = bookingsArray.value.eventStartTime
    // console.log(Time.value);
    // date.value = moment.utc(Time.value).format("DD MMMM YYYY")
    // startTime.value = moment.utc(Time.value).format("h:mm")
})


// const closeDetails = () => {
//         $emits('close')
// }

// @click.self="$emit('closeAll')"
//  v-if="booking.idBooking == bookings.idBooking"
</script>
 
<template>
   <div @click="closeDetails" class="font overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none justify-center items-center flex bg-black bg-opacity-20"
    > 
    <!-- <div class=" ">
        {{bookings.bookings}}
    </div> -->
            <div class="">
                <div
                    class="relative bg-white border-8 border-gray rounded-lg text-xl p-5">
                     <img class="absolute top-0 right-0 h-16 w-16 text-4xl text-lg font-normal h-[30px] w-[30px] mt-3 mr-7 cursor-pointer" @click="$emit('onCloseDetails')" src="images/cancel.png" alt="cancel"> 
                    <div class="grid grid-flow-row grid-cols-6 flex p-1 gap-3 py-10">
                        
                        <div class="row-start-1 col-start-1 col-end-2 p-1  bg-gray rounded-lg">BookingName</div>
                        <div class="row-start-1 col-start-2 col-end-7 p-1  bg-gray rounded-lg ml-10">{{ bookingsDetails.bookingName
                        }}</div>

                        <div class="row-start-2 col-start-1 col-end-2 p-1  bg-gray rounded-lg">Category</div>
                        <div class="row-start-2 col-start-2 col-end-7 p-1  bg-gray rounded-lg ml-10">{{ bookingsDetails.bookingEmail
                        }}</div>
                        
                        <div class="row-start-3 col-start-1 col-end-2 p-1  bg-gray rounded-lg">Email</div>
                        <div class="row-start-3 col-start-2 col-end-7 p-1  bg-gray rounded-lg ml-10">{{ bookingsDetails.eventCategoryName
                        }}</div>

                        <div class="row-start-4 col-start-1 col-end-2 p-1  bg-gray rounded-lg">Date</div>
                        <div class="row-start-4 col-start-2 col-end-7 p-1  bg-gray rounded-lg ml-10 ">{{ moment.utc(bookingsDetails.eventStartTime).format("DD MMMM YYYY") }}
                        </div>

<<<<<<< HEAD
                        <div class="row-start-5 col-start-1 col-end-2 p-1  bg-gray rounded-lg">StartTime</div>
                        <div class="row-start-5 col-start-2 col-end-7 p-1  bg-gray rounded-lg ml-10">{{moment.utc(bookingsDetails.eventStartTime).format("h:mm A") }}
=======
                        <div class="row-start-2 col-start-1 col-end-2 p-1 mb-1.5 bg-white rounded-lg">StartTime</div>
                        <div class="row-start-2 col-start-3 col-end-9 p-1 mb-1.5 bg-white rounded-lg">{{moment.utc(bookingsDetails.eventStartTime).format("h:mm a") }}
>>>>>>> e32423ffdcf8fafe6ae12da701580c3b944e4cfe
                        </div>

                        <div class="row-start-6 col-start-1 col-end-2 p-1  bg-gray rounded-lg">Duration</div>
                        <div class="row-start-6 col-start-2 col-end-7 p-1  bg-gray rounded-lg ml-10">{{
                                bookingsDetails.eventDuration
                        }}</div>

                        <div class="row-start-7 col-start-1 col-end-2 p-1  bg-gray rounded-lg">EventNotes</div>
                        <div class="row-start-7 col-start-2 col-end-7 p-1  bg-gray rounded-lg ml-10">{{ bookingsDetails.eventNotes == null ? "-" : bookingsDetails.eventNotes
                        }}</div>

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

.border-gray{
    border-color: #6D6D6D;
}

</style>