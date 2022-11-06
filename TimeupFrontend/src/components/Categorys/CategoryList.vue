<script setup>
import { ref, onBeforeMount, computed } from 'vue';
import EditCategory from './EditCategory.vue';
import { userStore } from '../../stores/user.js';
// ----- use when not use pinia
// const emits = defineEmits(["EditCategoryIdFromEdit"]);
const props = defineProps({
    categoryLists: {
        type: [Object],
        default: {}
    }
})

const userSignInRes = userStore();

const userSignInRole = computed(() => userSignInRes.signInUserData.userRole);

const categorys = computed(() => props.categoryLists)

const changeEditDialogTrue = (category) => {
    category.statusEditDialog = true
}

// ----- use when not use pinia
// const getEditCategoryValue = (editCategory) => {
//     emits('EditCategoryIdFromEdit', editCategory)
// }

</script>
 
<template>
    <div class="mt-24 ">
        <div>
            <div class="mb-10 uppercase text-4xl text-center font-bold text-black underline decoration-[#50ABCB]">
                category List
            </div>

            <div class="grid grid-cols-3 w-full mb-10">
                <div v-for="(category, index) in categorys" :key="index"
                    class="grid grid-flow-row grid-cols-2 mb-10 m-auto w-[90%] gap-2 text-center bg-white font-bold rounded-xl border border-[#E9E9E9]">

                    <div class="row-start-1 col-start-1 col-span-2 w-full rounded-t-xl text-2xl p-1 font-semibold "
                        :style="{ 'background-color': category.eventColor }">
                        {{ category.eventCategoryName }}
                    </div>

                    <div class="row-start-3 col-start-1 col-span-2">
                        Duration : {{ category.eventDuration }} minute
                    </div>
                    <div class="row-start-4 col-start-1 col-span-2">
                        Description :
                    </div>

                    <textarea
                        class="row-start-5 col-start-1 col-span-2 bg-gray-200 w-[95%] m-auto resize-none rounded text-center border "
                        disabled placeholder="No massage" v-model="category.eventCategoryDescription" id="" cols="90"
                        rows="5">{{
                                category.eventCategoryDescription == null || category.eventCategoryDescription == ""
                                    ? category.eventCategoryDescription = ""
                                    : category.eventCategoryDescription
                        }}</textarea>

                    <button v-if="userSignInRole != 'lecturer'"
                        class="row-start-6 col-start-1 col-span-2 bg-[#00A28B] rounded-b-xl text-white uppercase p-1"
                        @click="changeEditDialogTrue(category)"> Edit </button>

                    <div v-else
                        class="row-start-6 col-start-1 col-span-2 bg-[#D9D9D9] rounded-b-xl text-white uppercase p-1"></div>

                    <EditCategory v-if="category.statusEditDialog" :categorys="category" :categoryLists="categorys" />
                    <!-- // ----- use when not use pinia @EditCategoryId="getEditCategoryValue"  -->

                </div>
            </div>
        </div>
    </div>

</template>
 
<style>

</style>