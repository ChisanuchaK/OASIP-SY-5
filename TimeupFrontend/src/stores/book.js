//   //Get All Booking
export const getBookings = async () => {
  const res = await fetch(`${import.meta.env.VITE_BASE_URL}/booking`);
  if (res.status === 200) {
    console.log(res);
    return res;
  } else console.log("error to getBookings");
};

//GetBy BookingId
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

// create Booking
// export const createBooking = async (localData,categoryIndexSelect,dateIndexSelect,localPresentTime) => {
export const createBooking = async (localDataInput) => {
  // export const createBooking = async (localData) => {
  const res = await fetch(`${import.meta.env.VITE_BASE_URL}/booking`, {
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
      // eventDuration: localDataInput.eventDuration,
      eventNotes: localDataInput.eventNotes,
    }),
  });
  if (res.status === 201) {
    console.log("create successfully");
    return res;
  } else {
    console.log("error , failed to created");
  }
};

// edit booking
export const editBooking = async (editData) => {
  const res = await fetch(
    `${import.meta.env.VITE_BASE_URL}/booking/${editData.idBooking}`,
    {
      method: "PUT",
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify({
        // idBooking: editData.idBooking,
        // bookingName: editData.bookingName,
        // bookingEmail: editData.bookingEmail,
        // eventCategory: { eventCategoryId: editData.eventCategory.eventCategoryId },
        eventStartTime: editData.eventStartTime,
        // eventDuration: editData.eventDuration,
        eventNotes: editData.eventNotes,
      }),
    }
  );
  // console.log(editData.eventStartTime);
  if (res.status === 200) {
    console.log("edited successfully");
    return res;
  } else {
    // bookingEdit.createDialog = !bookingEdit.createDialog;
    console.log("error, cannot be added");
  }
};

// Delete method
export const removeBooking = async (deleteBookingId, booking, loopBooking) => {
  const res = await fetch(
    `${import.meta.env.VITE_BASE_URL}/booking/${deleteBookingId}`,
    {
      method: "DELETE",
    }
  );
  if (res.status === 200) {
    // bookingAll.value = bookingAll.value.filter((BookingInBookings) => (BookingInBookings.idBooking != deleteBookingId))
    booking.statusClickDelete = !booking.statusClickDelete;
    loopBooking.statusClickSeeDetails = !loopBooking.statusClickSeeDetails;
    // emits('idConfirmDelete', deleteBookingId)
    // location.reload();
    console.log("deleted success");
  } else {
    console.log("error , cannot delete");
  }
};

//get All Category
export const getEventCategory = async () => {
  const res = await fetch(
    `${import.meta.env.VITE_BASE_URL}/admin/event-category`
  );
  if (res.status === 200) {
    return res;
  } else {
    alert("find not found !!");
  }
};

// edit category
export const editCategory = async (editCategory) => {
  const res = await fetch(
    `${import.meta.env.VITE_BASE_URL}/admin/event-category/${
      editCategory.eventCategoryId
    }`,
    {
      method: "PUT",
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify({
        eventCategoryName: editCategory.eventCategoryName.trim(),
        eventDuration: editCategory.eventDuration,
        eventCategoryDescription: editCategory.eventCategoryDescription,
      }),
    }
  );

  if (res.status === 200) {
    console.log("edited successfully");
    return res;
  } else {
    console.log("error, cannot be added");
  }
};
