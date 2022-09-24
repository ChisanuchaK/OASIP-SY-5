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
<<<<<<< HEAD
<<<<<<< HEAD
    const response = res.json();
    return createResponse(res.status, response);
=======
    return res;
>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac
=======
    return res;
>>>>>>> parent of 0b0be68 (update-fe-refreshToken-03-finish)
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
<<<<<<< HEAD
<<<<<<< HEAD
    console.log("error to getBooking");
    const response = res.json();
    return createResponse(res.status, response);
=======
    console.log("not found");
>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac
=======
    console.log("not found");
>>>>>>> parent of 0b0be68 (update-fe-refreshToken-03-finish)
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
<<<<<<< HEAD
<<<<<<< HEAD
    // const response = await res.json();
    // return createResponse(res.status, response);
=======
>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac
=======
>>>>>>> parent of 0b0be68 (update-fe-refreshToken-03-finish)
    return res;
  } else {
<<<<<<< HEAD
<<<<<<< HEAD
    console.log("error to getBooking");
=======
    console.log("error , failed to created");
>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac
=======
    console.log("error , failed to created");
>>>>>>> parent of 0b0be68 (update-fe-refreshToken-03-finish)
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
<<<<<<< HEAD
<<<<<<< HEAD
    console.log("create successfully");
    // const response = await res.json();
    // return createResponse(res.status, response);
=======
    console.log("edited successfully");
>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac
=======
    console.log("edited successfully");
>>>>>>> parent of 0b0be68 (update-fe-refreshToken-03-finish)
    return res;
  } else {
<<<<<<< HEAD
<<<<<<< HEAD
    console.log("error to editBooking");
=======
    console.log("error, cannot be added");
>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac
=======
    console.log("error, cannot be added");
>>>>>>> parent of 0b0be68 (update-fe-refreshToken-03-finish)
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
<<<<<<< HEAD
<<<<<<< HEAD
    return res;
  } else if (res.status === 401) {
    if (await getRefreshToken()) {
      console.log("can use refreshToken");
      return removeBooking(deleteBookingId, booking, loopBooking);
    } else {
      console.log("please SignIn");
    }
  } else {
    console.log("error , cannot delete");
    return res;
=======
    console.log("deleted success");
  } else {
    console.log("error , cannot delete");
>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac
=======
    console.log("deleted success");
  } else {
    console.log("error , cannot delete");
>>>>>>> parent of 0b0be68 (update-fe-refreshToken-03-finish)
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
<<<<<<< HEAD
<<<<<<< HEAD
    console.log("error to getCategory");
=======
    // alert('find not found !! form getEventCategory');
    console.log("find not found !! form getEventCategory");
>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac
=======
    // alert('find not found !! form getEventCategory');
    console.log("find not found !! form getEventCategory");
>>>>>>> parent of 0b0be68 (update-fe-refreshToken-03-finish)
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
<<<<<<< HEAD
<<<<<<< HEAD
        eventCategoryName: editCategoryData.eventCategoryName.trim(),
        eventDuration: editCategoryData.eventDuration,
        eventCategoryDescription: editCategoryData.eventCategoryDescription,
=======
        eventCategoryName: editCategory.eventCategoryName.trim(),
        eventDuration: editCategory.eventDuration,
        eventCategoryDescription: editCategory.eventCategoryDescription,
>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac
=======
        eventCategoryName: editCategory.eventCategoryName.trim(),
        eventDuration: editCategory.eventDuration,
        eventCategoryDescription: editCategory.eventCategoryDescription,
>>>>>>> parent of 0b0be68 (update-fe-refreshToken-03-finish)
      }),
    }
  );

  if (res.status === 200) {
    console.log("edited successfully");
    return res;
  } else {
<<<<<<< HEAD
<<<<<<< HEAD
    console.log("error to editCategory");
=======
    console.log("error, cannot be added");
>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac
=======
    console.log("error, cannot be added");
>>>>>>> parent of 0b0be68 (update-fe-refreshToken-03-finish)
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
