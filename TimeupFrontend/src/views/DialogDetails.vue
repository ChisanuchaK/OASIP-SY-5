<script setup>

defineEmits(["close"])
const props = defineProps({
     bookings: {
        type: [Object],
        require: true
    },
    dialog: false
})
console.log(props.bookings);

// @click.self="$emit('closeAll')"
</script>
 
<template>
   <div class="font overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none justify-center items-center flex bg-black bg-opacity-20"
    > 
    <!-- <div class=" ">
        {{bookings.bookings}}
    </div> -->
            <div class="">
                <div
                    class="relative bg-green-400 border-2 border-black rounded-lg text-xl p-20">
                    <button class="absolute top-0 right-0 h-16 w-16" @click.self="$emit('close')">x</button>
                    <div class="grid grid-flow-row grid-cols9 flex p-1">

                        <div class="row-start-1 col-start-1 col-end-2 p-1 mb-1.5 bg-white rounded-lg">Date</div>
                        <div class="row-start-1 col-start-3 col-end-9 p-1 mb-1.5 bg-white rounded-lg ">{{ bookings.date }}
                        </div>

                        <div class="row-start-2 col-start-1 col-end-2 p-1 mb-1.5 bg-white rounded-lg">StartTime</div>
                        <div class="row-start-2 col-start-3 col-end-9 p-1 mb-1.5 bg-white rounded-lg">{{ bookings.time }}
                        </div>

                        <div class="row-start-3 col-start-1 col-end-2 p-1 mb-1.5 bg-white rounded-lg">Duration</div>
                        <div class="row-start-3 col-start-3 col-end-9 p-1 mb-1.5 bg-white rounded-lg">{{
                                bookings.eventDuration
                        }}</div>

                        <div class="row-start-4 col-start-1 col-end-2 p-1 mb-1.5 bg-white rounded-lg">Category</div>
                        <div class="row-start-4 col-start-3 col-end-9 p-1 mb-1.5 bg-white rounded-lg ">{{ bookings.eventCategoryName.eventCategoryName
                        }}</div>

                        <div class="row-start-5 col-start-1 col-end-2 p-1 mb-1.5 bg-white rounded-lg">BookingName</div>
                        <div class="row-start-5 col-start-3 col-end-9 p-1 mb-1.5 bg-white rounded-lg">{{ bookings.bookingName
                        }}</div>
                        
                        <div class="row-start-6 col-start-1 col-end-2 p-1 mb-1.5 bg-white rounded-lg">Email</div>
                        <div class="row-start-6 col-start-3 col-end-9 p-1 mb-1.5 bg-white rounded-lg">{{ bookings.email
                        }}</div>

                        <div class="row-start-7 col-start-1 col-end-2 p-1 mb-1.5 bg-white rounded-lg">EventNotes</div>
                        <div class="row-start-7 col-start-3 col-end-9 p-1 mb-1.5 bg-white rounded-lg">{{ bookings.eventNote
                        }}</div>

                        <div class="row-start-8 col-start-1 col-end-2 p-1 mb-1.5 bg-white rounded-lg">Id</div>
                        <div class="row-start-8 col-start-3 col-end-9 p-1 mb-1.5 bg-white rounded-lg">{{ bookings.idBooking
                        }}</div>


                        <div
                            class="col-start-2 ml-4 mr-4 w-3/12">
                        </div>
                    </div>
                </div>
            </div>
        </div>

</template>
 
<style>

</style>