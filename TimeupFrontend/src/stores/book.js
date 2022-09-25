import { getRefreshToken } from "./user.js";

const createResponse = (status, data) => {
  return { status: status, data: data };
};
// ---------------------------------------- ALL FETCH API ----------------------------------------

//Get All Booking
export const getBookings = async () => {
  const res = await fetch(`${import.meta.env.VITE_HTTPS_URL}/event`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
    },
  });
  if (res.status === 200) {
    const response = await res.json();
    return createResponse(res.status, response);
  } else if (res.status === 401) {
    if (await getRefreshToken()) {
      console.log("can use refreshToken");
      return getBookings();
    } else {
      console.log("please SignIn");
    }
  } else {
    console.log("error to getBookings");
    const response = res.json();
    return createResponse(res.status, response);
  }
};

//GetBy BookingId
export const getBookingId = async (bookingId) => {
  const res = await fetch(
    `${import.meta.env.VITE_HTTPS_URL}/event/${bookingId}`,
    {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
      },
    }
  );
  // if (res.status === 200) {
  //   console.log(res);
  //   return await res.json();
  // } else {
  //   console.log("not found");
  // }
  if (res.status === 200) {
    const response = await res.json();
    return createResponse(res.status, response);
  } else if (res.status === 401) {
    if (await getRefreshToken()) {
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
  // if (res.status === 201) {
  //   console.log("create successfully");
  //   return res;
  // } else {
  //   console.log("error , failed to created");
  //   return res;
  // }
  if (res.status === 201) {
    console.log("create successfully");
    // const response = await res.json();
    // return createResponse(res.status, response);
    return res;
  } else if (res.status === 401) {
    if (await getRefreshToken()) {
      console.log("can use refreshToken");
      return createBooking(localDataInput);
    } else {
      console.log("please SignIn");
    }
  } else {
    console.log("error to getBooking");
    return res;
    // const response = res.json();
    // return createResponse(res.status, response);
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
  // if (res.status === 200) {
  //   console.log("edited successfully");
  //   return res;
  // } else {
  //   console.log("error, cannot be added");
  //   return res;
  // }
  if (res.status === 200) {
    console.log("create successfully");
    // const response = await res.json();
    // return createResponse(res.status, response);
    return res;
  } else if (res.status === 401) {
    if (await getRefreshToken()) {
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
  // if (res.status === 200) {
  //   booking.statusClickDelete = !booking.statusClickDelete;
  //   loopBooking.statusClickSeeDetails = !loopBooking.statusClickSeeDetails;
  //   console.log("deleted success");
  // } else {
  //   console.log("error , cannot delete");
  // }
  if (res.status === 200) {
    console.log("deleted success");
    booking.statusClickDelete = !booking.statusClickDelete;
    loopBooking.statusClickSeeDetails = !loopBooking.statusClickSeeDetails;
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
  // if (res.status === 200) {
  //   return res;
  // } else {
  //   // alert('find not found !! form getEventCategory');
  //   console.log("find not found !! form getEventCategory");
  //   return res;
  // }
  if(res.status === 403){
    return res;
  }
  if (res.status === 200) {
    console.log("create successfully");
    const response = await res.json();
    return createResponse(res.status, response);
  } else if (res.status === 401) {
    if (await getRefreshToken()) {
      console.log("can use refreshToken");
      return getEventCategory();
    } else {
      console.log("please SignIn");
    }
  } else {
    console.log("error to getCategory");
    return res;
  }
};

// edit category
export const editCategory = async (editCategoryData) => {
  const res = await fetch(
    `${import.meta.env.VITE_HTTPS_URL}/admin/event-category/${
      editCategoryData.eventCategoryId
    }`,
    {
      method: "PUT",
      headers: {
        "content-type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
      },
      body: JSON.stringify({
        eventCategoryName: editCategoryData.eventCategoryName.trim(),
        eventDuration: editCategoryData.eventDuration,
        eventCategoryDescription: editCategoryData.eventCategoryDescription,
      }),
    }
  );
  // if (res.status === 200) {
  //   console.log("edited successfully");
  //   return res;
  // } else {
  //   console.log("error, cannot be added");
  //   return res;
  // }
  if (res.status === 200) {
    console.log("edited successfully");
    return res;
  } else if (res.status === 401) {
    if (await getRefreshToken()) {
      console.log("can use refreshToken");
      return editCategory(editCategoryData);
    } else {
      console.log("please SignIn");
    }
  } else {
    console.log("error to editCategory");
    return res;
  }
};

// export const getRefreshToken = async () => {
//   const res = await fetch(`${import.meta.env.VITE_HTTPS_URL}/refreshtoken`, {
//     headers: {
//       Authorization: `Bearer ${localStorage.getItem("refreshToken")}`,
//     },
//   });
//   if (res.status === 200) {
//     console.log("success to get refreshtoken");
//     const response = await res.json();
//     localStorage.setItem("accessToken", response.accessToken);
//     localStorage.setItem("refreshToken", response.refreshToken);
//     return true;
//   } else {
//     console.log("error to get refreshtoken");
//     return false;
//   }
// };

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
