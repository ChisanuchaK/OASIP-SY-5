// ---------------------------------------- ALL FETCH API ----------------------------------------
const createResponse = (status, data) => {
  return { status: status, data: data };
};
//Get All Booking
export const getBookings = async () => {
  const res = await fetch(`${import.meta.env.VITE_HTTPS_URL}/event`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
    },
  });
  if (res.status === 200) {
    console.log(res);
    // console.log(await res.json());
    return res;
  } else {
    console.log("error to getBookings");
    return res;
  }
};

//GetBy BookingId
export const getBookingId = async (bookingId) => {
  const res = await fetch(
    `${import.meta.env.VITE_HTTPS_URL}/event/${bookingId}`
  );
  if (res.status === 200) {
    console.log(res);
    return await res.json();
  } else {
    console.log("not found");
  }
};

// create Booking
export const createBooking = async (localDataInput) => {
  const res = await fetch(`${import.meta.env.VITE_HTTPS_URL}/event`, {
    method: "POST",
    headers: {
      "content-type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
    },
    body: JSON.stringify({
      bookingName: localDataInput.bookingName,
      bookingEmail: localDataInput.bookingEmail,
      eventCategory: {
        eventCategoryId: localDataInput.eventCategory.eventCategoryId,
      },
      eventStartTime: localDataInput.eventStartTime,
      eventNotes: localDataInput.eventNotes,
    }),
  });
  if (res.status === 201) {
    console.log("create successfully");
    return res;
  } else {
    console.log("error , failed to created");
    return res;
  }
};

// edit booking
export const editBooking = async (editData) => {
  const res = await fetch(
    `${import.meta.env.VITE_HTTPS_URL}/event/${editData.idBooking}`,
    {
      method: "PUT",
      headers: {
        "content-type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
      },
      body: JSON.stringify({
        eventStartTime: editData.eventStartTime,
        eventNotes: editData.eventNotes,
      }),
    }
  );
  if (res.status === 200) {
    console.log("edited successfully");
    return res;
  } else {
    console.log("error, cannot be added");
    return res;
  }
};

// Delete method
export const removeBooking = async (deleteBookingId, booking, loopBooking) => {
  const res = await fetch(
    `${import.meta.env.VITE_HTTPS_URL}/event/${deleteBookingId}`,
    {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
      },
    }
  );
  if (res.status === 200) {
    booking.statusClickDelete = !booking.statusClickDelete;
    loopBooking.statusClickSeeDetails = !loopBooking.statusClickSeeDetails;
    console.log("deleted success");
  } else {
    console.log("error , cannot delete");
  }
};

//get All Category
export const getEventCategory = async () => {
  const res = await fetch(
    `${import.meta.env.VITE_HTTPS_URL}/admin/event-category`,
    {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
      },
    }
  );
  if (res.status === 200) {
    return res;
  } else {
    // alert('find not found !! form getEventCategory');
    console.log("find not found !! form getEventCategory");
    return res;
  }
};

// edit category
export const editCategory = async (editCategory) => {
  const res = await fetch(
    `${import.meta.env.VITE_HTTPS_URL}/admin/event-category/${
      editCategory.eventCategoryId
    }`,
    {
      method: "PUT",
      headers: {
        "content-type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
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
    return res;
  }
};

// //get User Alls
// export const getAllUsers = async () => {
//   const res = await fetch(`${import.meta.env.VITE_BASE_URL}/user`);
//   if (res.status === 200) {
//     // console.log(res);
//     return res;
//   } else console.log('error to getUserLists');
// };

// //get User
// export const getUser = async (idUser) => {
//   const res = await fetch(`${import.meta.env.VITE_BASE_URL}/user/${idUser}`);
//   if (res.status === 200) {
//     // console.log(res);
//     return await res.json();
//   } else console.log('error to getUserLists');
// };

// // user Delete method
// export const deletedUser = async (deletedUserId) => {
//   const res = await fetch(
//     `${import.meta.env.VITE_BASE_URL}/user/${deletedUserId}`,
//     {
//       method: 'DELETE'
//     }
//   );
//   if (res.status === 200) {
//     console.log('deleted success');
//   } else {
//     console.log('error , cannot delete user');
//   }
// };

// // create user
// export const createUser = async (localDataInput) => {
//   const res = await fetch(`${import.meta.env.VITE_BASE_URL}/user`, {
//     method: 'POST',
//     headers: {
//       'content-type': 'application/json'
//     },
//     body: JSON.stringify({
//       nameUser: localDataInput.nameUser,
//       emailUser: localDataInput.emailUser,
//       roleUser: localDataInput.roleUser,
//       password: localDataInput.password
//     })
//   });
//   if (res.status === 201) {
//     console.log('create successfully');
//     return res;
//   } else {
//     console.log('error , failed to created');
//     return res;
//   }
// };

// //edit user
// export const editUser = async (localDataInput) => {
//   const res = await fetch(
//     `${import.meta.env.VITE_BASE_URL}/user/${localDataInput.idUser}`,
//     {
//       method: 'PUT',
//       headers: {
//         'content-type': 'application/json'
//       },
//       body: JSON.stringify({
//         nameUser: localDataInput.nameUser,
//         emailUser: localDataInput.emailUser,
//         roleUser: localDataInput.roleUser
//       })
//     }
//   );
//   if (res.status === 200) {
//     console.log('edited successfully.');
//     return res;
//   } else {
//     console.log('error, cannot be edit.');
//     return res;
//   }
// };

// //login
// export const loginToUse = async (loginData) => {
//   // const res = await fetch('https://intproj21.sit.kmutt.ac.th/sy5/api/login', {
//   const res = await fetch(
//     `${import.meta.env.VITE_HTTPS_URL}${import.meta.env.VITE_ROUTER_URL}${
//       import.meta.env.VITE_BASE_URL
//     }/login`,
//     {
//       method: 'POST',
//       headers: {
//         'content-type': 'application/json'
//       },
//       body: JSON.stringify({
//         emailUser: loginData.email,
//         password: loginData.password
//       })
//     }
//   );
//   // console.log(res.status);
//   console.log('restatus ' + res.status);
//   if (res.status === 200) {
//     console.log('log-in successfully');
//     // console.log(res.json())
//     return { status: res.status, data: res.json };
//   } else {
//     console.log('failed to log-in, email or password is invalid.');
//     // alert("failed to log-in, email or password is invalid.");
//     return res;
//   }
// };
