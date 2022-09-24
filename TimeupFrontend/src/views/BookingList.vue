<script setup>
import { ref, onBeforeMount, onMounted, computed } from 'vue';
import LoopBookingList from '../components/LoopBookingList.vue';
import FilterBar from '../components/FilterBar.vue';
import { getBookings, getEventCategory } from '../stores/book.js';
import moment from 'moment';
import PleaseLogInDialog from '../components/PleaseLogInDialog.vue';
import NavbarTop from '../components/NavbarTop.vue';
import NavbarBottom from '../components/NavbarBottom.vue';
<<<<<<< HEAD
=======

const getToken = localStorage.getItem('accessToken');
const pageName = ref('view BOOKING-LIST');
>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac

const getToken = localStorage.getItem('accessToken');
const pageName = ref('view BOOKING-LIST');

const bookingLists = ref([]);
const categoryLists = ref([]);
const statusScheduledBL = ref();
const dates = moment().local().format('YYYY-MM-DD HH:mm');

// ---------------------------- filter data is change real-time ----------------------------------------
//delete data
const filterList = (idDeleteFromLoop) => {
  bookingLists.value = bookingLists.value.filter(
    (BookingInBookings) => BookingInBookings.idBooking != idDeleteFromLoop
  );
};
//edit data
const filterEditBooking = (editId) => {
  bookingLists.value = bookingLists.value.map((booking) =>
    booking.idBooking === editId.idBooking
      ? {
          ...booking,
          eventStartTime: editId.eventStartTime,
          eventNotes: editId.eventNotes
        }
      : booking
  );
  console.log(editId);
};
// ---------------------------- filter data is change real-time ----------------------------------------

// ---------------------------- filter-bar ----------------------------------------
const filterBookFromCategory = async (filterData) => {
  statusScheduledBL.value = 'No Scheduled Events';
  const getAllBooks = await getBookings();
  bookingLists.value = await getAllBooks.json();
  bookingLists.value = descOrder();
  bookingLists.value = bookingLists.value.filter((booking) => {
    return booking.eventCategoryId == filterData;
  });
  console.log('fildata' + filterData);
};
const filterAllEvent = async () => {
  statusScheduledBL.value = 'No Scheduled Events';
  const getAllBooks = await getBookings();
  bookingLists.value = await getAllBooks.json();
  bookingLists.value = descOrder();
  bookingLists.value = bookingLists.value;
};
const filterPastEvent = async () => {
  statusScheduledBL.value = 'No Past Events';
  const getAllBooks = await getBookings();
  bookingLists.value = await getAllBooks.json();
  bookingLists.value = descOrder();
  bookingLists.value = bookingLists.value.filter((booking) => {
    return (
      moment(booking.eventStartTime).local().format('YYYY-MM-DD HH:mm') < dates
    );
  });
};
const filterUpComingEvent = async () => {
  statusScheduledBL.value = 'No On-Going';
  bookingLists.value = ascOrder();
  const getAllBooks = await getBookings();
  bookingLists.value = await getAllBooks.json();
  bookingLists.value = ascOrder();
  bookingLists.value = bookingLists.value.filter((booking) => {
    return (
      moment(booking.eventStartTime).local().format('YYYY-MM-DD HH:mm') >= dates
    );
  });
};
const filterByDateTime = async (time) => {
  statusScheduledBL.value = 'No Scheduled Events';
  const getAllBooks = await getBookings();
  bookingLists.value = await getAllBooks.json();
  bookingLists.value = ascOrder();
  bookingLists.value = bookingLists.value.filter((booking) => {
    return moment(booking.eventStartTime).local().format('YYYY-MM-DD') == time;
  });
};
// ---------------------------- filter-bar ----------------------------------------

//order data
const ascOrder = () =>
  bookingLists.value.sort(
    (a, b) => new Date(a.eventStartTime) - new Date(b.eventStartTime)
  );
const descOrder = () =>
  bookingLists.value.sort(
    (a, b) => new Date(b.eventStartTime) - new Date(a.eventStartTime)
  );

//fetch data
onBeforeMount(async () => {
  const getAllBooks = await getBookings();
  bookingLists.value = await getAllBooks.json();
  bookingLists.value = descOrder();
  const getAllCategory = await getEventCategory();
  categoryLists.value = await getAllCategory.json();

  bookingLists.value.map((booking) => {
    booking.statusClickSeeDetails = ref(false);
    booking.statusClickSeeDetails = ref(false);
    booking.statusClickDelete = ref(false);
  });
});
</script>
<template>
  <div>
<<<<<<< HEAD
<<<<<<< HEAD
    <NavbarTop />
    <NavbarBottom />
=======
    <NavbarTop/>
    <NavbarBottom/>
>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac
=======
    <NavbarTop/>
    <NavbarBottom/>
>>>>>>> parent of 0b0be68 (update-fe-refreshToken-03-finish)
    <div class="text-center text-xl mt-24 mb-10">
      <h1
        class="uppercase font-bold text-black underline decoration-[#50ABCB] text-4xl"
      >
        Booking Lists
      </h1>
    </div>

    <FilterBar
      :categorys="categoryLists"
      @getChageCategory="filterBookFromCategory"
      @getAllEvent="filterAllEvent"
      @getPastEvent="filterPastEvent"
      @getUpComingEvent="filterUpComingEvent"
      @getDateTime="filterByDateTime"
    />

<<<<<<< HEAD
<<<<<<< HEAD
    <LoopBookingList :statusScheduled="statusScheduledBL" :bookingLists="bookingLists" @idDialogDetails="filterList"
      @EditIdFromDialog="filterEditBooking" />
    <PleaseLogInDialog v-if="!getToken" :pageName="pageName" />
=======
=======
>>>>>>> parent of 0b0be68 (update-fe-refreshToken-03-finish)
    <LoopBookingList
      :statusScheduled="statusScheduledBL"
      :bookingLists="bookingLists"
      @idDialogDetails="filterList"
      @EditIdFromDialog="filterEditBooking"
    />
    <PleaseLogInDialog v-if="!getToken" :pageName="pageName"/>
<<<<<<< HEAD
>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac
=======
>>>>>>> parent of 0b0be68 (update-fe-refreshToken-03-finish)
  </div>
</template>
<style></style>
