import { defineStore, acceptHMRUpdate } from "pinia";
import { reactive, ref, computed } from "vue";
import { useRouter } from "vue-router";

export const userStore = defineStore("Users", () => {
  const appRouter = useRouter();
  const users = ref([]);
  const userById = ref([]);
  const signInUserData = reactive({
    userId: "",
    userName: "",
    userEmail: "",
    userRole: "",
    userPassword: "",
  });
  // const signInUserData = computed(() => {
  //   return {
  //     userId: "",
  //     userName: "",
  //     userEmail: "",
  //     userRole: "",
  //     userPassword: "",
  //   };
  // });

  const currentUser = ref([]);

  const signInStatus = ref(false);

  const createResponse = (status, data) => {
    return { status: status, data: data };
  };

  //get User Alls
  const getAllUsers = async () => {
    const res = await fetch(`${import.meta.env.VITE_HTTPS_URL}/user`);
    // console.log(res.status + " dsadsa");
    if (signInUserData.userRole == "guest") {
      users.value = [];
      return;
    } else if (
      signInUserData.userRole == "student" ||
      signInUserData.userRole == "lecturer"
    ) {
      appRouter.go(-1);
    } else if (res.status === 200) {
      await getRefreshToken();
      const response = await res.json();
      users.value = response;
      return users.value;
      // return res;
    } else if (res.status === 401) {
      if (await getRefreshToken()) {
        console.log("can use refreshToken");
        return getAllUsers();
      } else {
        console.log("please SignIn");
        // alert("please sign-in");
        // setTimeout(router().push({ name: "SignIn" }),5000);
      }
    } else {
      console.log("error to getUserLists");
      // alert("please sign-in");
      // setTimeout(router().push({ name: "SignIn" }),5000);
      // const response = res.json();
      return res;
    }
  };

  //get User
  const getUser = async (idUser) => {
    const res = await fetch(`${import.meta.env.VITE_HTTPS_URL}/user/${idUser}`);
    if (res.status === 200) {
      console.log("that make a high loop");
      await getRefreshToken();
      const response = await res.json();
      userById.value = response;
    } else if (res.status === 401) {
      if (await getRefreshToken()) {
        console.log("can use refreshToken");
        return getUser(idUser);
      } else {
        console.log("please SignIn");
      }
    } else {
      console.log("error to getUser");
      const response = res.json();
      return res;
    }
  };

  // user Delete method
  const deletedUser = async (deletedUserId, user) => {
    const res = await fetch(
      `${import.meta.env.VITE_HTTPS_URL}/user/${deletedUserId}`,
      {
        method: "DELETE",
      }
    );
    if (res.status === 200) {
      console.log("deleted success");
      await getRefreshToken();
      await getAllUsers();
      alert("Delete User Success!!");
      user.deleteDialogStatus = false;
      // const response = await res.json();
      // return createResponse(res.status, response);
      return res;
    } else if (res.status === 401) {
      if (await getRefreshToken()) {
        console.log("can use refreshToken");
        return deletedUser(deletedUserId);
      } else {
        console.log("please SignIn");
      }
    } else {
      console.log("error , cannot delete user");
      return res;
    }
  };

  const createUser = async (localDataInput) => {
    const res = await fetch(`${import.meta.env.VITE_HTTPS_URL}/user`, {
      method: "POST",
      headers: {
        "content-type": "application/json",
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
      await getRefreshToken();
      await getAllUsers();
      // const response = await res.json();
      // return createResponse(res.status, response);
      return res;
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
    console.log("Hello");
  };

  //edit user
  const editUser = async (localDataInput, user) => {
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
      await getRefreshToken();
      await getAllUsers();
      user.confirmDialogStatus = false;
      // const response = await res.json();
      // return createResponse(res.status, response);
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
  const signInUser = async (SignInData) => {
    const res = await fetch(`${import.meta.env.VITE_HTTPS_URL}/login`, {
      method: "POST",
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify({
        emailUser: SignInData.userEmail,
        password: SignInData.userPassword,
      }),
    });
    if (res.status === 200) {
      await getRefreshToken();
      // signInStatus.value = true;
      const response = await res.json();
      console.log(response);
      // signInUserData.userId = response.userId;
      // const userLogIn = await getUser(signInUserData.userId);
      // signInUserData.userName = userLogIn.data.nameUser;
      // signInUserData.userEmail = userLogIn.data.emailUser;
      // signInUserData.userRole = userLogIn.data.roleUser;
      // console.log(signInUserData);

      console.log("log-in successfully");
      // return createResponse(res.status, response);
      return res;
    } else {
      // signInStatus.value = false;
      console.log("failed to log-in, email or password is invalid.");
      const response = res.json();
      return res;
    }
  };

  const signOutUser = async () => {
    const res = await fetch(`${import.meta.env.VITE_HTTPS_URL}/logout`);
    // const res = await fetch("https://intproj21.sit.kmutt.ac.th/sy5/api/logout");
    if (res.status === 200) {
      console.log("status 200");
      await getRefreshToken();

      // signInStatus.value = false;
      // signInUserData.userId = "";
      // signInUserData.userEmail = "";
      // signInUserData.userName = "";
      // signInUserData.userRole = "";
      // signInUserData.userPassword = "";

      // const response = res.json();
      return res;
    } else if (res.status === 401) {
      if (await getRefreshToken()) {
        console.log("can use refreshToken");
        return signOutUser();
      } else {
        console.log("please SignIn");
      }
    } else {
      console.log("error, cannot be signout.");
      // const response = res.json();
      return res;
    }
  };

  const getRefreshToken = async () => {
    const res = await fetch(`${import.meta.env.VITE_HTTPS_URL}/refreshtoken`);
    if (res.status === 200) {
      console.log("success to get refreshtoken");
      // const response = await res.json();
      signInStatus.value = true;
      const response = await res.json();

      currentUser.value = response;

      signInUserData.userId = response.userId;
      signInUserData.userName = response.userName;
      signInUserData.userEmail = response.userEmail;
      signInUserData.userRole = response.userRole;
      // console.log(signInUserData);
      return true;
    } else {
      console.log("error to get refreshtoken");
      signInStatus.value = false;
      signInUserData.userId = "";
      signInUserData.userEmail = "";
      signInUserData.userName = "";
      signInUserData.userRole = "guest";
      signInUserData.userPassword = "";
      // appRouter.push({ name: 'Home' });
      return false;
    }
  };

  return {
    users,
    userById,
    currentUser,
    getAllUsers,
    getUser,
    deletedUser,
    createUser,
    editUser,
    signOutUser,
    signInStatus,
    signInUserData,
    signInUser,
    getRefreshToken,
  };
});

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(userStore, import.meta.hot));
}

// const getRefreshToken = async () => {
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
