<script setup>
import { ref, onBeforeMount, onMounted, computed } from 'vue';
import FilterBarForBooking from '../../components/Bookings/FilterBarForBooking.vue';
import BookingList from '../../components/Bookings/BookingList.vue'
import { bookStore } from '../../stores/book.js';
import { categoryStore } from '../../stores/category.js';
import { userStore } from '../../stores/user.js';
import moment from 'moment';
import PleaseLogInDialog from '../../components/PleaseLogInDialog.vue';

const bookRes = bookStore();
const categoryRes = categoryStore();
const userSignInRes = userStore();
const pageName = ref('view BOOKING-LIST');

bookRes.getBookings().then(()=>{
  console.log(bookRes.bookings);
});
console.log(bookRes.getBookings());
categoryRes.getEventCategory();

// bookRes.bookings.sort(
//   (a, b) => new Date(b.eventStartTime) - new Date(a.eventStartTime)
// );

const bookingLists = computed(() => bookRes.bookings);
const categoryLists = computed(() => categoryRes.categorys);

const statusScheduledBL = ref();
const dates = moment().local().format('YYYY-MM-DD HH:mm');
const userSignInRole = computed(() => userSignInRes.signInUserData.userRole);

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
  bookRes.getBookings().then(() => {
    bookRes.bookings = descOrder();
    bookRes.bookings = bookRes.bookings.filter((booking) => {
      return booking.eventCategoryId == filterData;
    });
  });
  console.log('fildata' + filterData);
};
const filterAllEvent = async () => {
  statusScheduledBL.value = 'No Scheduled Events';
  bookRes.getBookings().then(() => {
    bookRes.bookings = descOrder();
  });
  // bookingLists.value = bookingLists.value;
};
const filterPastEvent = async () => {
  statusScheduledBL.value = 'No Past Events';
  bookRes.getBookings().then(() => {
    bookRes.bookings = ascOrder();
    bookRes.bookings = bookRes.bookings.filter((booking) => {
      return (
        moment(booking.eventStartTime).local().format('YYYY-MM-DD HH:mm') < dates
      );
    });
  })
};

const filterUpComingEvent = async () => {
  statusScheduledBL.value = 'No On-Going';
  bookRes.getBookings().then(() => {
    bookRes.bookings = ascOrder();
    bookRes.bookings = bookRes.bookings.filter((booking) => {
      return (
        moment(booking.eventStartTime).local().format('YYYY-MM-DD HH:mm') >= dates
      );
    });
  })
};
const filterByDateTime = async (time) => {
  statusScheduledBL.value = 'No Scheduled Events';
  bookRes.getBookings().then(() => {
    bookRes.bookings = ascOrder();
    bookRes.bookings = bookRes.bookings.filter((booking) => {
      return moment(booking.eventStartTime).local().format('YYYY-MM-DD') == time;
    });
  });
};
// ---------------------------- filter-bar ----------------------------------------

//order data
const ascOrder = () =>
  bookRes.bookings.sort(
    (a, b) => new Date(a.eventStartTime) - new Date(b.eventStartTime)
  );
const descOrder = () =>
  bookRes.bookings.sort(
    (a, b) => new Date(b.eventStartTime) - new Date(a.eventStartTime)
  );

//fetch data
onBeforeMount(async () => {
  // await userSignInRes.getRefreshToken().then(()=> bookingLists.value = descOrder());
  bookingLists.value.map((booking) => {
    booking.statusClickSeeDetails = ref(false);
    booking.statusClickSeeDetails = ref(false);
    booking.statusClickDelete = ref(false);
  });
});
</script>
<template>
  <div>
    <div class="text-center text-xl mt-24 mb-10">
      <h1 class="uppercase font-bold text-black underline decoration-[#50ABCB] text-4xl">
        Booking Lists
      </h1>
    </div>

    <FilterBarForBooking :categorys="categoryLists" @getChageCategory="filterBookFromCategory"
      @getAllEvent="filterAllEvent" @getPastEvent="filterPastEvent" @getUpComingEvent="filterUpComingEvent"
      @getDateTime="filterByDateTime" />

    <BookingList :statusScheduled="statusScheduledBL" :bookingLists="bookingLists" @idDialogDetails="filterList"
      @EditIdFromDialog="filterEditBooking" />
    <PleaseLogInDialog v-if="userSignInRole == 'guest'" :pageName="pageName" />
  </div>
</template>
<style>

</style>

<!-- <script setup>
import { ref, onBeforeMount, onMounted, computed } from 'vue';
import LoopBookingList from '../components/LoopBookingList.vue';
import FilterBar from '../components/FilterBar.vue';
import { getBookings, getEventCategory } from '../stores/book.js';
import moment from 'moment';
import PleaseLogInDialog from '../../components/PleaseLogInDialog.vue';
import NavbarTop from '../components/NavbarTop.vue';
import NavbarBottom from '../components/NavbarBottom.vue';

const getToken = localStorage.getItem('refreshToken');
const pageName = ref('view BOOKING-LIST');


const statusGetAllBookings = ref();
const responseGetAllBookings = ref({});
const responseGetAllCategory = ref({});
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
  responseGetAllBookings.value = await getBookings();
  bookingLists.value = await responseGetAllBookings.value.data;
  bookingLists.value = descOrder();
  bookingLists.value = bookingLists.value.filter((booking) => {
    return booking.eventCategoryId == filterData;
  });
  console.log('fildata' + filterData);
};
const filterAllEvent = async () => {
  statusScheduledBL.value = 'No Scheduled Events';
  responseGetAllBookings.value = await getBookings();
  bookingLists.value = await responseGetAllBookings.value.data;
  bookingLists.value = descOrder();
  bookingLists.value = bookingLists.value;
};
const filterPastEvent = async () => {
  statusScheduledBL.value = 'No Past Events';
  // const getAllBooks = await getBookings();
  // bookingLists.value = await getAllBooks.json();
  responseGetAllBookings.value = await getBookings();
  bookingLists.value = await responseGetAllBookings.value.data;
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
  // const getAllBooks = await getBookings();
  // bookingLists.value = await getAllBooks.json();
  responseGetAllBookings.value = await getBookings();
  bookingLists.value = await responseGetAllBookings.value.data;
  bookingLists.value = ascOrder();
  bookingLists.value = bookingLists.value.filter((booking) => {
    return (
      moment(booking.eventStartTime).local().format('YYYY-MM-DD HH:mm') >= dates
    );
  });
};
const filterByDateTime = async (time) => {
  statusScheduledBL.value = 'No Scheduled Events';
  // const getAllBooks = await getBookings();
  // bookingLists.value = await getAllBooks.json();
  responseGetAllBookings.value = await getBookings();
  bookingLists.value = await responseGetAllBookings.value.data;
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
  responseGetAllBookings.value = await getBookings();
  bookingLists.value = responseGetAllBookings.value.data;
  console.log(bookingLists.value);
  bookingLists.value = descOrder();
  responseGetAllCategory.value = await getEventCategory();
  categoryLists.value = responseGetAllCategory.value.data;

  bookingLists.value.map((booking) => {
    booking.statusClickSeeDetails = ref(false);
    booking.statusClickSeeDetails = ref(false);
    booking.statusClickDelete = ref(false);
  });
});
</script>
<template>
  <div>
    <div class="relative z-10">
      <NavbarTop />
      <NavbarBottom />
    </div>
      <div class="text-center text-xl mt-24 mb-10">
        <h1 class="uppercase font-bold text-black underline decoration-[#50ABCB] text-4xl">
          Booking Lists
        </h1>
      </div>

      <FilterBar :categorys="categoryLists" @getChageCategory="filterBookFromCategory" @getAllEvent="filterAllEvent"
        @getPastEvent="filterPastEvent" @getUpComingEvent="filterUpComingEvent" @getDateTime="filterByDateTime" />

      <LoopBookingList :statusScheduled="statusScheduledBL" :bookingLists="bookingLists" @idDialogDetails="filterList"
        @EditIdFromDialog="filterEditBooking" />
      <PleaseLogInDialog v-if="!getToken" :pageName="pageName" />
  </div>
</template>
<style>

</style> -->
