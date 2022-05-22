<script setup>
import { computed, onMounted } from 'vue';  
const emits = defineEmits(["onCancelDelete","onConfirmDelete"])
const props = defineProps({
    bookingsFromDetails: {
        type: Object,
        require: true
    },
    bookingsFromLoopBookings: {
        type: Object,
        default : {}
    }
})

const bookingDelete = computed(() => props.bookingsFromDetails);

const bookingLoop = computed(() => props.bookingsFromLoopBookings);

const closeDelete = (event) => {
    if (event.target.id == "modalDelete") {
        emits('onCancelDelete')
    } else { }
}
    
</script>
 
<template>
    <div id="modalDelete" @click="closeDelete"
        class="font overflow-x-hidden overflow-y-auto fixed focus:outline-none justify-center items-center flex inset-0 z-20 outline-none  bg-black bg-opacity-20">
        <div class="">
            <div class="text-center bg-white p-10 rounded-lg">
                <div class="p-20 ">
                  <p class="text-4xl mb-5">Delete ?</p> 
            click confirm to delete scheduled <br>
            ID : {{bookingDelete.idBooking}} <br>
            {{bookingDelete.eventCategoryName}} <br>
            {{bookingDelete.bookingName}}
                </div>
                <div class="grid grid-flow-row grid-cols-4 ">
                    <button class="col-start-1 p-3 bg-[#499D6B] text-white rounded-lg" @click="$emit('onConfirmDelete',bookingDelete.idBooking, bookingDelete,bookingLoop)">confirm</button>
                    <button class="col-start-4 p-3 bg-[#F97473] text-white rounded-lg justify-start" @click="$emit('onCancelDelete')">cancel</button>
                    <!-- <button class="col-start-4 p-3 bg-[#499D6B] text-white rounded-lg" @click="$emit('onConfirmDelete')">confirm</button> -->

                </div>
            </div>
        </div>
    </div>
</template>
 
<style>
</style> 