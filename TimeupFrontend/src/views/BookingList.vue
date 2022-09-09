<script setup>
import { ref, onBeforeMount, onMounted, computed } from 'vue';
import NavbarTop from '../components/NavbarTop.vue';
import NavbarBottom from '../components/NavbarBottom.vue';
import LoopBookingList from '../components/LoopBookingList.vue';
import FilterBar from '../components/FilterBar.vue';
import { getBookings, getEventCategory } from '../stores/book.js';
import moment from 'moment';

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
    <NavbarTop />
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

    <LoopBookingList
      :statusScheduled="statusScheduledBL"
      :bookingLists="bookingLists"
      @idDialogDetails="filterList"
      @EditIdFromDialog="filterEditBooking"
    />
    <NavbarBottom />
  </div>
</template>
<style></style>
