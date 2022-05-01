import {ref , onBeforeMount} from 'vue'
// const bookings = ref([]);

// // Delete method
const removeBooking = async (deleteBookingId)=>{
    const res = await fetch(`http://localhost:3001/BookingLists/${deleteBookingId}`,{
      method: 'DELETE'
    })
    if(res.status === 200){
      bookings.value = bookings.value.filter((BookingInBookings)=> (BookingInBookings.id != deleteBookingId) )
      console.log('deleted success');
    }else{
      console.log('error , cannot delete');
    }
  }
  
//   //Get All
export const getBookings = async () => {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/booking`)
    if (res.status === 200) {
      console.log(res)
      return await res.json()
    } else  console.log('error to getBookings');
  }

  //Getby Id
export const getBookingId = async (bookingId)=>{
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/booking/${bookingId}`);
    if(res.status === 200){
    console.log(res)
    return await res.json();
    }else{
    console.log('not found');
    }
   }

// export {getBookings, getBookingId};