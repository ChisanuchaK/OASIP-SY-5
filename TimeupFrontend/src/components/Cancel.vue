<script setup>
const emits = defineEmits(["onClickCancelNo", "onClickCancelYes"])

const closeCancel = (event) => {
    if (event.target.id == "modalCancel") {
        emits('onClickCancelNo')
    } else { }
}

</script>
 
<template>
    <div id="modalCancel" @click="closeCancel"
        class="font overflow-x-hidden overflow-y-auto fixed focus:outline-none justify-center items-center flex inset-0 z-20 outline-none  bg-black bg-opacity-20">
        <div class="">
            <div class="text-center bg-white p-10 rounded-lg">
                <div class="p-20 ">
                    <p class="text-4xl mb-5">Cancel ?</p>
                    You want to cancel this ?
                </div>
                <div class="grid grid-flow-row grid-cols-4 ">
                    <button class="col-start-1 p-3 bg-[#499D6B] text-white rounded-lg justify-start"
                        @click="$emit('onClickCancelYes')"> Yes
                    </button>
                    <button class="col-start-4 p-3 bg-[#F97473] text-white rounded-lg"
                        @click="$emit('onClickCancelNo')">No</button>

                </div>
            </div>
        </div>
    </div>
</template>
 
<style>
</style> 