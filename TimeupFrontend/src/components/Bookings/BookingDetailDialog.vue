<script setup>
import { ref, onBeforeMount, computed, reactive } from "vue";
import { bookStore } from "../../stores/book.js";
import { userStore } from '../../stores/user.js';
// import { getBookingId, removeBooking } from "../stores/book.js";
import DeleteBookingDialog from './DeleteBookingDialog.vue';
import EditBookingDialog from "./EditBookingDialog.vue";
import moment from "moment";

const emits = defineEmits([
  "onCloseDetails",
  "idConfirmDelete",
  "EditIdFromEdit",
]);
const props = defineProps({
  bookingLists: {
    type: [Object],
    require: true
  },
  bookingDetailById: {
    type: Object,
    require: true,
  },
});

const userSignInRes = userStore();
const bookRes = bookStore();

userSignInRes.getRefreshToken().then(() => {
  //call getBookingId in this to make a size of fileDetail.fileSize is real
  // bookRes.getBookingId(props.bookingDetailById.idBooking).then(()=>fileSize(bookRes.bookingById.fileSize));
  bookRes.getBookingId(props.bookingDetailById.idBooking).then(()=>fileSize(bookingDetail.value.fileSize));
  console.log(fileDetail.fileSize);
})


const userSignInRole = computed(() => userSignInRes.signInUserData.userRole);

// booking form for-loop and fetch to get detail from id
// bookRes.getBookingId(props.bookingDetailById.idBooking).then(() => console.log(bookRes.bookingById));
const bookingDetail = computed(() => bookRes.bookingById);

// console.log(bookingDetail.value);
// responseGetBooking.value = await bookRes.getBookingId(props.bookings.idBooking);

// booking form for-loop not have a many detail of use
// const booking = computed(() => props.bookingDetailById);
// console.log(booking.value);

let fileDetail = reactive({
  fileSize: 0,
  fileSizeUnit: "",
});

const fileSize = (sizeOfFile) => {
  console.log(sizeOfFile);
  if (sizeOfFile < Math.pow(1024, 2)) {
    fileDetail.fileSize = parseFloat(sizeOfFile / Math.pow(1024, 1));
    console.log(fileDetail.fileSize);
    fileDetail.fileSizeUnit = "KB";
  } else if (sizeOfFile < Math.pow(1024, 3)) {
    fileDetail.fileSize = parseFloat(sizeOfFile / Math.pow(1024, 2));
    console.log(fileDetail.fileSize);
    fileDetail.fileSizeUnit = "MB";
  } else {
    return;
  }
};

//-----------------------------pop-up-dialog---------------------------------
const closeDetails = (event) => {
  if (event.target.id == "modalDetails") {
    emits("onCloseDetails");
  } else {
  }
};
const changeDeleteDialog = (booking) => {
  booking.statusClickDelete = !booking.statusClickDelete;
  // console.log(booking.statusClickDelete);
};
const showEditDialog = (booking) => {
  booking.statusClickEdit = true;
  console.log("go to edit " + booking.statusClickEdit);
  // console.log(booking.statusClickEdit);
};

const closeEditDialog = (booking) => {
  booking.statusClickEdit = false;
  // console.log(booking.statusClickEdit);
};
//-----------------------------pop-up-dialog---------------------------------

// get edit booking id to send back
const getEditIdFromEdit = (EditId) => {
  //   emits("EditIdFromEdit", EditId);
};

// remove booking
const removeBookingEvent = async (deleteBookingId, booking, loopBooking) => {
  alert("Delete Booking Success!");
  emits("idConfirmDelete", deleteBookingId);
  bookRes.removeBooking(deleteBookingId, booking, loopBooking);
};

//fetch data
onBeforeMount(async () => {
  // bookRes.getBookingId(props.bookings.idBooking)
  // responseGetBooking.value = await bookRes.getBookingId(props.bookings.idBooking);
  // if(responseGetBooking.value.status === 200){
  //     bookingDetail.value = responseGetBooking.value.data;
  //     console.log(bookingDetail.value);
  // }else{
  //     return ''
  // }
  // console.log(bookingDetail.value);
});
</script>

<template>
  <div id="modalDetails" @click="closeDetails"
    class="font overflow-x-hidden overflow-y-auto fixed inset-0 z-10 outline-none focus:outline-none justify-center items-center flex bg-black bg-opacity-20">
    <div class="">
      <div class="relative bg-white rounded-lg text-xl p-5 px-12">
        <div class="w-36 m-auto text-1xl text-center">
          ID : {{ bookingDetail.idBooking }}
          <!-- ID : {{ booking.idBooking }} -->
        </div>
        <img class="absolute top-0 right-0 text-4xl text-lg font-normal h-[30px] w-[30px] mt-3 mr-7 cursor-pointer"
          @click="$emit('onCloseDetails')" src="/images/cancel.png" alt="cancel" />
        <div class="grid grid-flow-row grid-cols-2 p-1 gap-1 pt-5 text-center">
          <div class="row-start-1 col-start-1 col-span-2 p-1 rounded-lg">
            Category : {{ bookingDetail.eventCategoryName }}
            <!-- Category : {{ booking.eventCategoryName }} -->
          </div>
          <div class="row-start-2 col-start-1 col-span-2 p-1 rounded-lg">
            Name : {{ bookingDetail.bookingName }}
            <!-- Name : {{ booking.bookingName }} -->
          </div>
          <div class="row-start-3 col-start-1 col-span-2 p-1 rounded-lg">
            Email : {{ bookingDetail.bookingEmail }}
            <!-- Email : {{ booking.bookingEmail }} -->
          </div>
          <div class="row-start-4 col-start-1 col-span-2 p-1 rounded-lg">
            StartTime :
          </div>
          <div class="row-start-5 col-start-1 col-span-2 p-1 rounded-lg">
            {{
                moment(bookingDetail.eventStartTime)
                  .local()
                  .format("DD MMMM YYYY hh:mm A")
            }}
            <!-- {{
              moment(booking.eventStartTime)
                .local()
                .format("DD MMMM YYYY hh:mm A")
            }} -->
            -
            {{
                moment(bookingDetail.eventStartTime)
                  .local()
                  .add(bookingDetail.eventDuration, "m")
                  .format("DD MMMM YYYY hh:mm A")
            }}
            <!-- {{
              moment(booking.eventStartTime)
                .local()
                .add(booking.eventDuration, "m")
                .format("DD MMMM YYYY hh:mm A")
            }} -->
          </div>
          <div class="row-start-6 col-start-1 col-span-2 p-1 rounded-lg">
            Duration : {{ bookingDetail.eventDuration }} minute
            <!-- Duration : {{ booking.eventDuration }} minute -->
          </div>

          <div class="row-start-7 col-start-1 col-span-2 p-1 rounded-lg">
            file :
          </div>

          <div class="row-start-8 col-start-1 col-span-2 p-1 rounded-lg">
            <!-- <a class="text-blue-900 hover:underline" href="" :download="`https://intproj21.sit.kmutt.ac.th/sy5/api/event/file/${bookingDetail.idBooking}`">{{ bookingDetail.file }}</a> -->
            <a v-if="bookingDetail.file != null" class="text-blue-900 hover:underline"
              :href="`https://intproj21.sit.kmutt.ac.th/sy5/api/event/file/${bookingDetail.idBooking}`">
              {{ bookingDetail.file }}
            </a>
            <a v-else class="text-[#D9D9D9]">
              empty
            </a>
            {{ fileDetail.fileSize == 0 ? "" : fileDetail.fileSize.toFixed(2)+ " " + fileDetail.fileSizeUnit }}
            <!-- {{ fileDetail.fileSize == 0 ? "" :fileDetail.fileSizeUnit }} -->
          </div>

          <div class="row-start-9 col-start-1 col-span-2 p-1 rounded-lg">
            EventNotes :
          </div>

          <div class="row-start-10 col-start-1 col-end-3 col-span-2">
            <textarea class="bg-gray-200 w-full resize-none rounded text-center bg-" placeholder="No Message" disabled
              v-model="bookingDetail.eventNotes" name="" id="" cols="90" rows="5">{{
                  bookingDetail.eventNotes == null ||
                    bookingDetail.eventNotes.trim() === ""
                    ? ""
                    : bookingDetail.eventNotes
              }}</textarea>
          </div>

          <div class="row-start-11 mt-5 col-start-1">
            <button v-if="userSignInRole != 'lecturer'"
              class="bg-[#F24052] text-white rounded-lg w-6/12 h-full m-auto py-2"
              @click="changeDeleteDialog(bookingDetail)">
              delete
            </button>
          </div>
          <div class="row-start-11 mt-5 col-start-2">
            <button v-if="userSignInRole != 'lecturer'"
              class="bg-[#00A28B] text-white rounded-lg w-6/12 h-full m-auto py-2"
              @click="showEditDialog(bookingDetail)">
              edit
            </button>
          </div>

          <DeleteBookingDialog v-if="bookingDetail.statusClickDelete" :bookingsFromDetails="bookingDetail"
            :bookingsFromLoopBookings="bookingDetail" @onCancelDelete="changeDeleteDialog(bookingDetail)"
            @onConfirmDelete="removeBookingEvent" />

          <EditBookingDialog v-if="bookingDetail.statusClickEdit" :loopEdit="bookingDetail" :bookingLists="bookingLists"
            :bookingsDetailsEdit="bookingDetail" @EditbookingId="getEditIdFromEdit" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.row-start-8 {
  grid-row-start: 8;
}

.row-start-9 {
  grid-row-start: 9;
}

.row-start-10 {
  grid-row-start: 10;
}

.row-start-11 {
  grid-row-start: 11;
}


.bg-gray {
  background-color: #e2dddd;
}

.border-gray {
  border-color: #6d6d6d;
}
</style>
