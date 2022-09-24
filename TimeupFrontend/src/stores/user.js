import { useRouter } from "vue-router";

const createResponse = (status, data) => {
  return { status: status, data: data };
};

// const router = () => {
//   return useRouter();
// };

//get User Alls
export const getAllUsers = async () => {
  const res = await fetch(`${import.meta.env.VITE_HTTPS_URL}/user`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
    },
  });
  //   console.log(res);
  // const router = useRouter();
  // const res = { status: 401 };

  if (res.status === 200) {
    const response = await res.json();
    return createResponse(res.status, response);
    // return res;
  } else if (res.status === 401) {
    if (await getRefreshToken()) {
      console.log("can use refreshToken");
      return getAllUsers();
    } else {
      console.log("please SignIn");
    }
  } else {
    console.log("error to getUserLists");
    // alert("please sign-in");
    // setTimeout(router().push({ name: "SignIn" }),5000);
    const response = res.json();
    return createResponse(res.status, response);
  }
};

//get User
export const getUser = async (idUser) => {
  const res = await fetch(`${import.meta.env.VITE_HTTPS_URL}/user/${idUser}`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
    },
  });
  if (res.status === 200) {
    const response = await res.json();
    return createResponse(res.status, response);
    // return res;
  } else if (res.status === 401) {
    if (await getRefreshToken()) {
      console.log("can use refreshToken");
<<<<<<< HEAD
      return getUser(idUser);
=======
      return getUser();
>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac
    } else {
      console.log("please SignIn");
    }
  } else {
<<<<<<< HEAD
    console.log("error to getUser");
=======
    console.log("error to getUserLists");
>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac
    const response = res.json();
    return createResponse(res.status, response);
  }
};

// user Delete method
export const deletedUser = async (deletedUserId) => {
  const res = await fetch(
    `${import.meta.env.VITE_HTTPS_URL}/user/${deletedUserId}`,
    {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
      },
    }
  );
  if (res.status === 200) {
    console.log("deleted success");
    // const response = await res.json();
    // return createResponse(res.status, response);
    return res;
  } else if (res.status === 401) {
    if (await getRefreshToken()) {
      console.log("can use refreshToken");
<<<<<<< HEAD
      return deletedUser(deletedUserId);
=======
      return deletedUser();
>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac
    } else {
      console.log("please SignIn");
    }
  } else {
    console.log("error , cannot delete user");
    return res;
  }
};

// create user
export const createUser = async (localDataInput) => {
  const res = await fetch(`${import.meta.env.VITE_HTTPS_URL}/user`, {
    method: "POST",
    headers: {
      "content-type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
    },
    body: JSON.stringify({
      nameUser: localDataInput.nameUser,
      emailUser: localDataInput.emailUser,
      roleUser: localDataInput.roleUser,
      password: localDataInput.password,
    }),
  });
  if (res.status === 201) {
    console.log("create successfully");
<<<<<<< HEAD
    // const response = await res.json();
    // return createResponse(res.status, response);
    return res;
=======
    const response = await res.json();
    return createResponse(res.status, response);
    // return res;
>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac
  } else if (res.status === 401) {
    if (await getRefreshToken()) {
      console.log("can use refreshToken");
      return createUser(localDataInput);
    } else {
      console.log("please SignIn");
    }
  } else {
    console.log("error , failed to created");
    // const response = res.json();
    // return createResponse(res.status, response);
    return res;
  }
};

//edit user
export const editUser = async (localDataInput) => {
  const res = await fetch(
    `${import.meta.env.VITE_HTTPS_URL}/user/${localDataInput.idUser}`,
    {
      method: "PUT",
      headers: {
        "content-type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
      },
      body: JSON.stringify({
        nameUser: localDataInput.nameUser,
        emailUser: localDataInput.emailUser,
        roleUser: localDataInput.roleUser,
      }),
    }
  );
  if (res.status === 200) {
    console.log("edited successfully.");
<<<<<<< HEAD
    // const response = await res.json();
    // return createResponse(res.status, response);
=======
>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac
    return res;
  } else if (res.status === 401) {
    if (await getRefreshToken()) {
      console.log("can use refreshToken");
      return editUser(localDataInput);
    } else {
      console.log("please SignIn");
    }
  } else {
    console.log("error, cannot be edit.");
    return res;
  }
};

//login
export const loginToUse = async (loginData) => {
  // const res = await fetch('https://intproj21.sit.kmutt.ac.th/sy5/api/login', {
  const res = await fetch(`${import.meta.env.VITE_HTTPS_URL}/login`, {
    method: "POST",
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify({
      emailUser: loginData.email,
      password: loginData.password,
    }),
  });
  console.log("status : " + res.status);
  if (res.status === 200) {
    const response = await res.json();

    console.log("log-in successfully");
    localStorage.setItem("accessToken", response.accessToken);
    localStorage.setItem("refreshToken", response.refreshToken);
    return createResponse(res.status, response);
  } else {
    console.log("failed to log-in, email or password is invalid.");
    const response = res.json();
    return createResponse(res.status, response);
    // return res;
    // alert("failed to log-in, email or password is invalid.");
    // return createResponse(res.status, res);
  }
};

const getRefreshToken = async () => {
  const res = await fetch(`${import.meta.env.VITE_HTTPS_URL}/refreshtoken`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("refreshToken")}`,
    },
  });
  // const response = res.json();
  // console.log(response.accessToken);
  // console.log(response.refreshToken);
  // console.log(res);
  if (res.status === 200) {
    console.log("success to get refreshtoken");
    const response = await res.json();
    localStorage.setItem("accessToken", response.accessToken);
    localStorage.setItem("refreshToken", response.refreshToken);
    return true;
  } else {
    console.log("error to get refreshtoken");
    return false;
  }
};
