<script setup>

defineEmits(["close"])
const props = defineProps({
     bookings: {
        type: Array,
        require: true
    },
    dialog: false
})

</script>
 
<template>
   <div class="font overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none justify-center items-center flex bg-black bg-opacity-80"
   @click.self="emit('close')" > 
   <div class="bg-white p-20">
       TEST MODAL
   </div> </div>
</template>
 
<style>

</style>