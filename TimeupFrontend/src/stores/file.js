import { defineStore, acceptHMRUpdate } from "pinia";
import { reactive, ref, computed } from "vue";
import { userStore } from "./user.js";
import { useRouter } from "vue-router";

export const fileStore = defineStore("file", () => {
  const store = userStore();
  const appRouter = useRouter();
  const fileById = ref();

    //GetBy BookingId
  const getFileByBookingId = async (bookingId) => {
    const res = await fetch(
      `${import.meta.env.VITE_HTTPS_URL}/event/file/${bookingId}`
    );
    if (res.status === 200) {
      await store.getRefreshToken();
      const response = await res.json();
      fileById.value = response;
      console.log(fileById.value);
      return response;
    } else if (res.status === 401) {
      if (await store.getRefreshToken()) {
        console.log("can use refreshToken");
        return getFileByBookingId(bookingId);
      } else {
        console.log("please SignIn");
      }
    } else {
      console.log("error to getBooking");
    //   const response = res.json();
    //   return createResponse(res.status, response);
    return ;
    }
  };

  return {
    fileById,
    getFileByBookingId,
  };
});

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(fileStore, import.meta.hot));
}
