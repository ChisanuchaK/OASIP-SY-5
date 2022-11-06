import { defineStore, acceptHMRUpdate } from "pinia";
import { reactive, ref, computed } from "vue";
import { userStore } from "./user.js";

export const bookStore = defineStore("book", () => {
  const store = userStore();
  const bookings = ref([]);
  const bookingById = ref([]);
  const createResponse = (status, data) => {
    return { status: status, data: data };
  };
  //Get All Booking
  const getBookings = async () => {
    const res = await fetch(`${import.meta.env.VITE_HTTPS_URL}/event`);
    if (store.signInUserData.userRole == "guest") {
      bookings.value = [];
      return;
    } else if (res.status === 200) {
      await store.getRefreshToken();
      const response = await res.json();
      bookings.value = response;
      bookings.value.sort(
        (a, b) => new Date(b.eventStartTime) - new Date(a.eventStartTime)
      );
      console.log(bookings.value);
      // return res;
    } else if (res.status === 401) {
      if (await store.getRefreshToken()) {
        console.log("can use refreshToken");
        return getBookings();
      } else {
        console.log("please SignIn");
      }
    } else {
      console.log("error to getBookings");
      const response = res.json();
      return res;
    }
  };

  //GetBy BookingId
  const getBookingId = async (bookingId) => {
    const res = await fetch(
      `${import.meta.env.VITE_HTTPS_URL}/event/${bookingId}`
    );
    if (res.status === 200) {
      await store.getRefreshToken();
      const response = await res.json();
      bookingById.value = response;
      console.log(bookingById.value);
      return response;
    } else if (res.status === 401) {
      if (await store.getRefreshToken()) {
        console.log("can use refreshToken");
        return getBookingId(bookingId);
      } else {
        console.log("please SignIn");
      }
    } else {
      console.log("error to getBooking");
      const response = res.json();
      return createResponse(res.status, response);
    }
  };

  // create Booking
  const createBooking = async (localDataInput) => {
    console.log(localDataInput);
    const res = await fetch(`${import.meta.env.VITE_HTTPS_URL}/event`, {
      method: "POST",
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify({
        bookingName: localDataInput.bookingName,
        bookingEmail: localDataInput.bookingEmail,
        eventCategory: {
          eventCategoryId: localDataInput.eventCategory.eventCategoryId,
        },
        eventStartTime: localDataInput.eventStartTime,
        eventNotes: localDataInput.eventNotes,
        user: {
          idUser: localDataInput.userIdInSignIn,
        },
        fileName: localDataInput.fileData
      }),
    });
    if (res.status === 201) {
      await store.getRefreshToken();
      console.log("create successfully");
      // const response = await res.json();
      // return createResponse(res.status, response);
      return res;
    } else if (res.status === 401) {
      if (await store.getRefreshToken()) {
        console.log("can use refreshToken");
        return createBooking(localDataInput);
      } else {
        console.log("please SignIn");
      }
    } else {
      console.log("error to getBooking");
      return res;
    }
  };

  // edit booking
  const editBooking = async (editData, bookingEdit, loopEdit) => {
    const res = await fetch(
      `${import.meta.env.VITE_HTTPS_URL}/event/${editData.idBooking}`,
      {
        method: "PUT",
        headers: {
          "content-type": "application/json",
        },
        body: JSON.stringify({
          eventStartTime: editData.eventStartTime,
          eventNotes: editData.eventNotes,
        }),
      }
    );
    if (res.status === 200) {
      await store.getRefreshToken();
      await getBookings();
      alert("Edit Booking Success!");
      bookingEdit.createDialog = false;
      bookingEdit.statusClickEdit = false;
      loopEdit.statusClickSeeDetails = false;
      console.log("create successfully");
      return res;
    } else if (res.status === 401) {
      if (await store.getRefreshToken()) {
        console.log("can use refreshToken");
        return editBooking(editData);
      } else {
        console.log("please SignIn");
      }
    } else {
      console.log("error to editBooking");
      return res;
    }
  };

  // Delete method
  const removeBooking = async (deleteBookingId, booking, loopBooking) => {
    const res = await fetch(
      `${import.meta.env.VITE_HTTPS_URL}/event/${deleteBookingId}`,
      {
        method: "DELETE",
      }
    );
    if (res.status === 200) {
      await store.getRefreshToken();
      await getBookings();
      console.log("deleted success");
      booking.statusClickDelete = false;
      loopBooking.statusClickSeeDetails = false;
      return res;
    } else if (res.status === 401) {
      if (await store.getRefreshToken()) {
        console.log("can use refreshToken");
        return removeBooking(deleteBookingId, booking, loopBooking);
      } else {
        console.log("please SignIn");
      }
    } else {
      console.log("error , cannot delete");
      return res;
    }
  };

  // const getRefreshToken = async () => {
  //   const res = await fetch(`${import.meta.env.VITE_HTTPS_URL}/refreshtoken`);
  //   if (res.status === 200) {
  //     console.log("success to get refreshtoken");
  //     // const response = await res.json();
  //     signInStatus.value = true;
  //     const response = await res.json();
  //     signInUserData.userId = response.userId;
  //     signInUserData.userName = response.userName;
  //     signInUserData.userEmail = response.userEmail;
  //     signInUserData.userRole = response.userRole;
  //     console.log(signInUserData);
  //     return true;
  //   } else {
  //     console.log("error to get refreshtoken");
  //     signInStatus.value = false;
  //     signInUserData.userId = "";
  //     signInUserData.userEmail = "";
  //     signInUserData.userName = "";
  //     signInUserData.userRole = "";
  //     signInUserData.userPassword = "";
  //     return false;
  //   }
  // };

  return {
    bookings,
    bookingById,
    getBookings,
    getBookingId,
    createBooking,
    editBooking,
    removeBooking,
  };
});

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(bookStore, import.meta.hot));
}
