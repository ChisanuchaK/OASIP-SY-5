<script setup>
import {ref} from 'vue'
import DialogDetails from './DialogDetails.vue';

defineEmits(['deleteBooking'])
defineProps({
    bookings: {
        type: Array,
        require: true
    }
    
})

const dialogs = ref(false);

const changeDialog = () => { 
    dialogs.value = !dialogs.value
    console.log(dialogs.value);  }

</script>
 
<template>

    <div>
        <div>
            <div class="text-center text-xl mt-20 mb-10">
                <h1 class="uppercase font-bold">Booking Lists</h1>
            </div>
            <DialogDetails v-if="dialogs" @close="dialogs = false"/>

            <div v-if="bookings == '' " class="flex flex-warp justify-center bg-emerald-800 text-white text-xl">
                    No schedule Evets
            </div>

            <div v-else class="grid grid-flow-row grid-cols-2">
                <div v-for="(booking, index) in bookings" :key="index"
                    class="mx-24 my-10 bg-greentea border-2 border-black rounded-lg text-xl p-5">
                    <div class="grid grid-flow-row grid-cols9 flex p-1">

                        <div class="row-start-1 col-start-1 col-end-2 p-1 mb-1.5 bg-white rounded-lg">Date</div>
                        <div class="row-start-1 col-start-3 col-end-9 p-1 mb-1.5 bg-white rounded-lg">{{ booking.date }}
                        </div>

                        <div class="row-start-2 col-start-1 col-end-2 p-1 mb-1.5 bg-white rounded-lg">StartTime</div>
                        <div class="row-start-2 col-start-3 col-end-9 p-1 mb-1.5 bg-white rounded-lg">{{ booking.time }}
                        </div>

                        <div class="row-start-3 col-start-1 col-end-2 p-1 mb-1.5 bg-white rounded-lg">Duration</div>
                        <div class="row-start-3 col-start-3 col-end-9 p-1 mb-1.5 bg-white rounded-lg">{{
                                booking.duration
                        }}</div>

                        <div class="row-start-4 col-start-1 col-end-2 p-1 mb-1.5 bg-white rounded-lg">Category</div>
                        <div class="row-start-4 col-start-3 col-end-9 p-1 mb-1.5 bg-white rounded-lg ">{{ booking.categoryName
                        }}</div>

                        <div class="row-start-5 col-start-1 col-end-2 p-1 mb-1.5 bg-white rounded-lg">BookingName</div>
                        <div class="row-start-5 col-start-3 col-end-9 p-1 mb-1.5 bg-white rounded-lg">{{ booking.bookingName
                        }}</div>

                        <div
                            class="col-start-2 row-start-1 row-end-6 border rounded-full bg-white  2xl:ml-4 xl:ml-1 -mr-6 w-3/12 mb-1">
                        </div>

                        <button class="mt-5" @click="changeDialog">See Details</button>
                    <!-- <button class="mt-5 col-start-5">edit</button> -->
                    <button class="mt-5 col-start-7" @click="$emit('deleteBooking', booking.id)">delete</button>


                        <!-- <div class="grid grid-flow-row grid-cols-5 flex p-1 bg-white rounded-lg ">
                            <p class="col-start-1">Date </p>
                            <div class="col-start-2"> </div>
                            <p class="col-start-3"> {{ booking.day }}</p>
                        </div>

                        <div class="p-1 bg-white rounded-lg my-2"> StartTime : {{ booking.time }} </div>
                        <div class="p-1 bg-white rounded-lg my-2"> Duration : {{ booking.duration }}</div>
                        <div class="p-1 bg-white rounded-lg my-2"> CategoryName : {{ booking.cName }} </div>
                        <div class="p-1 bg-white rounded-lg my-2"> BookingName : {{ booking.bName }} </div> -->
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- <div class="text-center text-xl my-10">
        <h1>Booking Skills:</h1>

        <ul>
            <div v-for="(booking, index) in bookings" :key="index"
                class="flex flex-warp justify-center bg-emerald-800 text-xl">
                Day:{{ booking.day }}, Start Time:{{ booking.time }}, Duration:{{ booking.duration }}, Category Name:{{
                        booking.cName
                }}, Booking Name:{{ booking.bName }}
            </div>
        </ul>
    </div> -->
</template>
 
<style scoped>
.bg-greentea {
    background-color: #D3E4CD;
}
</style>
