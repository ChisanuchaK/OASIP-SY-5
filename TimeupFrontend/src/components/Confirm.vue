<script setup>
const emits = defineEmits(["onClickCreateNo", "onClickCreateYes"]);

const closeCancel = (event) => {
    if (event.target.id == "modalCreate") {
        emits('onClickCreateNo')
    } else { }
}



// const status = ref(false)

// const closeSuccess = (event) => {
//     if (event.target.id == "modalSuccess") {
//         emits('onClickCreateNo')
//     } else { }
// }

// const popupSuccess = ()=> {
//  status.value = !status.value
// }

</script>
 
<template>
<div>
    <div id="modalCreate" @click="closeCancel"
        class="font overflow-x-hidden overflow-y-auto fixed focus:outline-none justify-center items-center flex inset-0 z-20 outline-none  bg-black bg-opacity-20">
        <div class="">
            <div class="text-center bg-white p-10 rounded-lg">
                <div class="p-20 ">
                    <p class="text-4xl mb-5"> Confirm ? </p>
                      Are your sure to confirm this ?
                </div>
                <div class="grid grid-flow-row grid-cols-4 ">
                    <button class="col-start-1 p-3 bg-[#499D6B] text-white rounded-lg justify-start" @click="[$emit('onClickCreateYes')]"> Yes </button>
                    <button class="col-start-4 p-3 bg-[#F97473] text-white rounded-lg" @click="$emit('onClickCreateNo')">No</button>
                </div>
            </div>
        </div>
    </div>

    <!-- <div v-if="status" id="modalSuccess" @click="closeSuccess"
        class="font overflow-x-hidden overflow-y-auto fixed focus:outline-none justify-center items-center flex inset-0 z-20 outline-none  bg-black bg-opacity-20">
        <div class="">
            <div class="text-center bg-white p-10 rounded-lg">
                <div class="p-20 ">
                    <p class="text-4xl mb-5"> Confirm ? </p>
                      Are your sure to confirm this ?
                </div>
                <div class="grid grid-flow-row grid-cols-4 ">
                    <button class="col-start-1 p-3 bg-[#499D6B] text-white rounded-lg justify-start" @click="$emit('onClickCreateYes')"> Yes </button>
                    <button class="col-start-4 p-3 bg-[#F97473] text-white rounded-lg" @click="$emit('onClickCreateNo')">No</button>
                </div>
            </div>
        </div>
    </div> -->
</div>
</template>
 
<style>
</style> 