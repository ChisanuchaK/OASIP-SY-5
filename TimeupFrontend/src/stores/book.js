import { ref, onBeforeMount } from "vue";
// const bookings = ref([]);

// // Delete method
const removeBooking = async (deleteBookingId) => {
  const res = await fetch(
    `${import.meta.env.VITE_BASE_URL}/booking/${deleteBookingId}`,
    {
      method: "DELETE",
    }
  );
  if (res.status === 200) {
    bookings.value = bookings.value.filter(
      (BookingInBookings) => BookingInBookings.id != deleteBookingId
    );
    console.log("deleted success");
  } else {
    console.log("error , cannot delete");
  }
};

//   //Get All
export const getBookings = async () => {
  const res = await fetch(`${import.meta.env.VITE_BASE_URL}/booking`);
  if (res.status === 200) {
    console.log(res);
    return await res.json();
  } else console.log("error to getBookings");
};

//Getby Id
export const getBookingId = async (bookingId) => {
  const res = await fetch(
    `${import.meta.env.VITE_BASE_URL}/booking/${bookingId}`
  );
  if (res.status === 200) {
    console.log(res);
    return await res.json();
  } else {
    console.log("not found");
  }
};

//get All Category
export const getEventCategory = async () => {
  const res = await fetch(
    `${import.meta.env.VITE_BASE_URL}/admin/event-category`
  );
  if (res.status === 200) {
    return await res.json();
  } else {
    alert("find not found !!");
  }
};

// create Booking
export const createBooking = async (localData) => {
  const res = await fetch(`${import.meta.env.VITE_BASE_URL}/booking`, {
    method: "POST",
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify({
      bookingName: localData.bookingName,
      bookingEmail: localData.bookingEmail,
      eventCategory: {
        eventCategoryName: localData.eventCategory.eventCategoryName,
      },
      eventStartTime: localData.eventStartTime,
      eventDuration: localData.eventDuration,
      eventNotes: localData.eventNotes,
    }),
  });
  if (res.status === 201) {
    // const addBooking = await res.json()
    // bookingsList.value.push(addBooking)
    alert(`Create successfully \n Category :  ${localData.eventCategory.eventCategoryName} \n Date : ${localData.eventStartTime} \n Booking name :  ${localData.bookingName}`)
  } else {
    console.log("error , failed to created");
  }
};

// export {getBookings, getBookingId};
