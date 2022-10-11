import { defineStore, acceptHMRUpdate } from "pinia";
import { reactive, ref, computed } from "vue";
import { userStore } from "./user.js";
import { useRouter } from "vue-router";

export const categoryStore = defineStore("category", () => {
  const store = userStore();
  const appRouter = useRouter();
  const categorys = ref([]);
  const createResponse = (status, data) => {
    return { status: status, data: data };
  };

  //get All Category
  const getEventCategory = async () => {
    const res = await fetch(
      `${import.meta.env.VITE_HTTPS_URL}/admin/event-category`
    );
    // if (store.signInUserData.userRole == "student") {
    //   appRouter.go(-1);
    // } else if (
    //   store.signInUserData.userRole == "lecturer" ||
    //   store.signInUserData.userRole == "admin" ||
    //   store.signInUserData.userRole == "guest"
    // ) {
      if (res.status === 403) {
        return res;
      }
      if (res.status === 200) {
        await store.getRefreshToken();
        console.log("get successfully");
        const response = await res.json();
        // console.log(response);
        // console.log(createResponse(res.status, response));
        // allCategorys.value = createResponse(res.status, response);
        categorys.value = response;
        // console.log(categorys.value);
        // return allCategorys.value
      } else if (res.status === 401) {
        if (await store.getRefreshToken()) {
          console.log("can use refreshToken");
          return getEventCategory();
        } else {
          console.log("please SignIn");
        }
      } else {
        console.log("error to getCategory");
        return res;
      }
    // }
  };

  //   edit category
  const editCategory = async (editCategoryData, category) => {
    const res = await fetch(
      `${import.meta.env.VITE_HTTPS_URL}/admin/event-category/${
        editCategoryData.eventCategoryId
      }`,
      {
        method: "PUT",
        headers: {
          "content-type": "application/json",
        },
        body: JSON.stringify({
          eventCategoryName: editCategoryData.eventCategoryName.trim(),
          eventDuration: editCategoryData.eventDuration,
          eventCategoryDescription: editCategoryData.eventCategoryDescription,
        }),
      }
    );
    if (res.status === 200) {
      await store.getRefreshToken();
      alert("Edit Category Success!");
      await getEventCategory();
      category.statusConfirmDialog = false;
      category.statusEditDialog = false;
      //   category.statusEditSuccesDialog = !category.statusEditSuccesDialog;
      console.log("edited successfully");
      return res;
    } else if (res.status === 401) {
      if (await store.getRefreshToken()) {
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

  return {
    categorys,
    getEventCategory,
    editCategory,
  };
});

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(categoryStore, import.meta.hot));
}
