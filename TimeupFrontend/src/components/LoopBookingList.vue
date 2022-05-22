<script setup>
import { ref, onBeforeMount, computed, popScopeId } from 'vue'
import moment from 'moment';
import DialogDetails from './DialogDetails.vue';
import ConfirmDelete from './ConfirmDelete.vue';
import { getEventCategory,removeBooking } from "../stores/book.js";

const emits = defineEmits(["idDialogDetails", "EditIdFromDialog"])
const props = defineProps({
    bookings: {
        type: [Object],
        require: true
    },
    statusScheduled:{
        type: String,
        default: "No Scheduled Events"
    } 
})

const bookingList = computed(() => props.bookings);
console.log(bookingList);
const categorys = ref([]);
const indexSelect = ref();
// const categoryColors = ref([])
// const categoryNames = ref([])

// const bookId = ref([]);
// const bookingsDetails = ref([]);

onBeforeMount(async () => {
    const getAllCategory = await getEventCategory();
    categorys.value = await getAllCategory.json();
    console.log(categorys.value[0].eventCategoryName);

    // categorys.forEach(category => { 
    //     categoryColors.value.push(category.eventColor) 
    // });
    // console.log(categoryColors.value);

    // categorys.forEach(category => { 
    //     categoryNames.value.push(category.eventCategoryName) 
    // });
    // console.log(categoryNames.value);

    // const getColor = (()=>{})

    // bookingList.value.forEach(bookId => { bookingsDetails.value.push(getBookingId(bookId.idBooking))    
    // });

    //ใช้ไม่ได้
    // bookingsDetails.value = getBookingId(bookingList[0].idBooking)

    // console.log(bookingsDetails.value);
    // console.log(bookingsDetails.value);
    // bookingsDetails.value = await getBookingId(res[0].idBooking)


    // setTimeout(1,()=>console.log("aa"+superList))
    // console.log(" Show Bookings "+ props.bookings);
})

const colorBg = (booking) => {
    for (let category of categorys.value) {
        if (booking.eventCategoryId == category.eventCategoryId) {
            return category.eventColor
        }
    }
    // for(let i = 0;i<categorys.value.length;i++){
    //     if(booking == categorys.value[i].eventCategoryName){
    //         // console.log(categorys.value[i].eventColor);
    //       return categorys.value[i].eventColor
    //     }
    // }

    //    if(booking == categorys.value.eventCategoryName){
    // console.log(booking.eventCategoryName);
    // console.log(categorys.value.eventColor+"aaaaaa");

    // } 
    // console.log(booking);
    // console.log(categoryName);
}

const changeSeeDetailsDialog = (booking) => {
    booking.statusClickSeeDetails = !booking.statusClickSeeDetails
    console.log(booking.statusClickSeeDetails);
    // dialogs.value = !dialogs.value
    // console.log(dialogs.value);
}

const changeDeleteDialog = (booking) => {
    booking.statusClickDelete = !booking.statusClickDelete
    console.log(booking.statusClickDelete);
    // dialogs.value = !dialogs.value
    // console.log(dialogs.value);
}

const getIdFromDialog = (idDelete) => {
    emits('idDialogDetails', idDelete)
}

const getEditIdFromDialog = (EditId) => {
    emits('EditIdFromDialog', EditId)
}

const removeBookingEvent = async (deleteBookingId, booking, loopBooking) => {
    emits('idDialogDetails', deleteBookingId)
    await removeBooking(deleteBookingId, booking, loopBooking)
}

</script>
 
<template>

    <div>
        <div>
            <!-- <div class="text-center text-xl mt-24 mb-10">
                <h1 class="uppercase font-bold text-[#ffffff] text-4xl">Booking Lists</h1>
            </div> -->

            <div v-if="bookings == ''" class="flex flex-warp justify-center bg-gray-800 text-white text-xl">
                {{props.statusScheduled}}
            </div>

            <div v-if="!(bookings == '')" class="grid grid-flow-row grid-cols-1 mb-24">
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
                <!-- <div class="overflow-y-auto overflow-x-hidden 2xl:h-[500px] 2xl:mr-[3.25%] mr-[95px] h-[460px]"> -->
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

                            <div class="row-start-1 col-start-5 col-end-7 p-1 mb-1.5 rounded-lg "
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


                            <!-- <DialogDetails v-if="booking.statusClickSeeDetails" @onCloseDetails="changeSeeDetailsDialog(booking)" :bookings="booking"/> -->
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
