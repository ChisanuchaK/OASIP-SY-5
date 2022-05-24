<script setup>
import { ref, reactive, onBeforeMount, computed, onBeforeUpdate } from "vue";
import Cancel from "./Cancel.vue";
import Confirm from "./Confirm.vue";
import { editCategory } from "../stores/book.js";
const emits = defineEmits(["EditCategoryId"]);
const props = defineProps({
    categorys: {
        type: Object,
        default: {}
    },
    categoryLists: {
        type: Object,
        default: {}
    }
});

//some category form for-loop 
const categoryList = computed(() => props.categorys);
const category = ref(categoryList);

//all categorys 
const categoryListAlls = computed(() => props.categoryLists);
const categoryAlls = ref(categoryListAlls);

const isInvalid = ref(false);
const isDuplicate = ref(false);

let editCategoryData = reactive({
    eventCategoryId: category.value.eventCategoryId,
    eventCategoryName: category.value.eventCategoryName,
    eventDuration: category.value.eventDuration,
    eventCategoryDescription: category.value.eventCategoryDescription
})

const isInvalidInputDescription = computed(() => {
    return isInvalid.value && editCategoryData.eventCategoryDescription.length > 500;
});

const isInvalidInputNameLength = computed(() => {
    return isInvalid.value && editCategoryData.eventCategoryName.length > 100;
});

const isInvalidInputName = computed(() => {
    return isInvalid.value && (editCategoryData.eventCategoryName == "" || editCategoryData.eventCategoryName == null);
});

const isInvalidInputDuration = computed(() => {
    return isInvalid.value && (editCategoryData.eventDuration == null || editCategoryData.eventDuration == "");
});

const isInvalidInputDurationisNaN = computed(() => {
    return isInvalid.value && (isNaN(editCategoryData.eventDuration));
});

const isInvalidInputDurationMaxMin = computed(() => {
    return isInvalid.value && (editCategoryData.eventDuration > 480 || editCategoryData.eventDuration < 1);
});

//check categoryname is duplicate?
const isInputCategoryNameDuplicate = computed(() => {
    for (let categorySome of categoryAlls.value) {
        // if (editCategoryData.eventCategoryName.trim() == category.value.eventCategoryName.trim()) {
        if (editCategoryData.eventCategoryName.toLowerCase().trim() == category.value.eventCategoryName.toLowerCase().trim()) {
            break;
        }
        if (new String(editCategoryData.eventCategoryName.toLowerCase()).valueOf() == new String(categorySome.eventCategoryName.toLowerCase()).valueOf()) {
            console.log("Duplicate name !");
            isDuplicate.value = true
            isInvalid.value = true
            // console.log("edit value " + editCategoryData.eventCategoryName);
            // console.log("database value " + categorySome.eventCategoryName);
            break;
        } else {
            isDuplicate.value = false
        }
    }
    return (isInvalid.value && isDuplicate.value)
});


const closeEdit = (event, category) => {
    if (event.target.id == "modalCategoryEdit") {
        category.statusCancelDialog = true;
    }
}
const changeCancelDialogFalse = (category) => {
    category.statusCancelDialog = false;
}
const changeCancelDialogTrue = (category) => {
    category.statusCancelDialog = true;
}
const changeEditDialogFalse = (category) => {
    category.statusCancelDialog = !category.statusCancelDialog;
    category.statusEditDialog = !category.statusEditDialog;
}
const changeCreateDialogFalse = (category) => {
    category.statusConfirmDialog = false
}
const changeCreateDialogTrue = (category) => {
    isInvalid.value = false
    if (editCategoryData.eventCategoryName == "" || editCategoryData.eventCategoryName == null
        || editCategoryData.eventCategoryName.length > 100 || editCategoryData.eventCategoryDescription.length > 500
        || editCategoryData.eventDuration == "" || editCategoryData.eventDuration == null || isNaN(editCategoryData.eventDuration)
        || (editCategoryData.eventDuration > 480 || editCategoryData.eventDuration < 1)) {
        isInvalid.value = true
    } else {
        isInvalid.value = false

    }
    if (isDuplicate.value == false && isInvalid.value == false) {
        category.statusConfirmDialog = true
        editCategoryData.eventCategoryName = editCategoryData.eventCategoryName.trim()
        editCategoryData.eventCategoryDescription = editCategoryData.eventCategoryDescription.trim()
    }
}

//put category is edited 
const editCategoryEvent = async (editCategoryitem, category) => {
    const editEvent = await editCategory(editCategoryitem);
    if (editEvent.status === 200) {
        alert('Edit Category Success!');
        category.statusConfirmDialog = !category.statusConfirmDialog;
        category.statusEditDialog = !category.statusEditDialog;
        category.statusEditSuccesDialog = !category.statusEditSuccesDialog;
        emits('EditCategoryId', editCategoryitem);
        // console.log('edited successfully');
    }
}

</script>

<template>
    <div id="modalCategoryEdit" @click="closeEdit($event, category)"
        class="font overflow-x-hidden overflow-y-auto fixed inset-0 z-10 outline-none focus:outline-none justify-center items-center flex bg-black bg-opacity-20">
        <div class="">
            <div class="relative bg-white rounded-lg text-xl p-6 px-12">
                <div class="w-36 m-auto text-1xl text-center">
                    Category ID : {{ category.eventCategoryId }}

                </div>
                <img class="absolute top-0 right-0 text-4xl text-lg font-normal h-[30px] w-[30px] mt-3 mr-7 cursor-pointer"
                    @click="changeCancelDialogTrue(category)" src="/images/cancel.png" alt="cancel" />
                <div class="grid grid-flow-row grid-cols-2 p-1 gap-1 pt-5 text-center">
                    <div class="row-start-1 col-start-1 col-span-2 p-1 rounded-lg w-[50%] m-auto">
                        <div class="grid grid-flow-row grid-cols-3">
                            <p class="row-start-1 col-start-1 col-span-2 text-right md:-mr-4">
                                Category Name :
                            </p>
                            <p class="row-start-1 col-start-3 text-right">
                                {{ editCategoryData.eventCategoryName.length }}/100
                            </p>
                        </div>

                    </div>
                    <div class="row-start-2 col-start-1 col-span-2 p-1 rounded-lg w-[50%] m-auto">
                        <input class="rounded text-center border bg-gray-200 w-full" type="text"
                            :style="{ 'border-color': isInvalidInputNameLength || isInvalidInputName || isInputCategoryNameDuplicate ? 'red' : 'white' }"
                            v-model="editCategoryData.eventCategoryName">
                        <div class="text-red-500 text-center m-auto" v-if="isInvalidInputName">
                            please enter category name !
                        </div>
                        <div class="text-red-500 text-center m-auto" v-if="isInvalidInputNameLength">
                            over limit of category name !
                        </div>
                        <div class="text-red-500 text-center m-auto" v-if="isInputCategoryNameDuplicate">
                            Duplicate category name !
                        </div>
                    </div>
                    <div class="row-start-3 col-start-1 col-span-2 p-1 rounded-lg">
                        Duration : <span class="text-gray-400">(minutes)</span>
                    </div>
                    <div class="row-start-4 col-start-1 col-span-2 p-1 rounded-lg w-[50%] m-auto">
                        <input class="rounded text-center border bg-gray-200 w-full" type="text"
                            :style="{ 'border-color': isInvalidInputDuration || isInvalidInputDurationisNaN || isInvalidInputDurationMaxMin ? 'red' : 'white' }"
                            v-model="editCategoryData.eventDuration">
                        <div class="text-red-500 text-center m-auto" v-if="isInvalidInputDuration">
                            please enter duration !
                        </div>
                        <div class="text-red-500 text-center m-auto" v-if="isInvalidInputDurationisNaN">
                            input is not a number !
                        </div>
                        <div class="text-red-500 text-center m-auto" v-if="isInvalidInputDurationMaxMin">
                            input is isInvalid ! please choose between 1 and 480 minute
                        </div>
                    </div>



                    <div class="row-start-5 col-start-1 col-span-2 p-1 rounded-lg">
                        Description :
                    </div>
                    <div class="row-start-5 col-start-2 col-span-1 p-1 rounded-lg text-right text-gray-400">
                        {{ editCategoryData.eventCategoryDescription.length }}/500
                    </div>

                    <div class="row-start-8 col-start-1 col-end-3 col-span-2">
                        <textarea class="bg-gray-200 w-full resize-none rounded text-center border"
                            v-model="editCategoryData.eventCategoryDescription" name=""
                            :style="{ 'border-color': isInvalidInputDescription ? 'red' : 'white' }" id="" cols="90"
                            rows="5">{{
                                    category.eventCategoryDescription == null
                                        ? category.eventCategoryDescription = ""
                                        : category.eventCategoryDescription
                            }}</textarea>
                        <div class="text-red-500 text-center m-auto" v-if="isInvalidInputDescription">
                            over limit of Description!
                        </div>
                    </div>
                    <div class="row-start-9 mt-5 col-start-1">
                        <button class="bg-[#499D6B] text-white rounded-lg w-6/12 h-9/12 m-auto py-2"
                            @click="[changeCreateDialogTrue(category)]">confirm</button>
                            <div class="text-red-500 text-center m-auto" v-if="isInputCategoryNameDuplicate || isInvalidInputDurationMaxMin || isInvalidInputDurationisNaN || isInvalidInputDuration || isInvalidInputName || isInvalidInputNameLength || isInvalidInputDescription">
                            *some input is invalid
                        </div>
                    </div>


                    <div class="row-start-9 mt-5 col-start-2">
                        <button class="bg-[#F97473] text-white rounded-lg w-6/12 h-9/12 m-auto py-2"
                            @click="changeCancelDialogTrue(category)">cancel</button>
                    </div>


                    <Cancel v-if="category.statusCancelDialog" @onClickCancelNo="changeCancelDialogFalse(category)"
                        @onClickCancelYes="changeEditDialogFalse(category)" />

                    <Confirm v-if="category.statusConfirmDialog" @onClickConfirmNo="changeCreateDialogFalse(category)"
                        @onClickConfirmYes="editCategoryEvent(editCategoryData, category)" />

                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
</style>
