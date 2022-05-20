<script setup>
import { ref, computed, reactive } from 'vue';
import moment from 'moment';
const emits = defineEmits(["getChageCategory", "getReset", "getPastEvent", "getUpComingEvent", "getDateTime"])
const props = defineProps({
    categorys: {
        type: [Object],
        default: {}
    }
})

const categoryLists = computed(() => props.categorys);
const categoryList = ref(categoryLists);

const categoryIndexSelect = ref();

// const time = ref(moment().format("YYYY-MM-DD"))
const time = ref()

const filterData = reactive({
    eventCategoryId: "",
    eventStartTime: ""
})

const getChangeCategory = () => {
    filterData.eventCategoryId = categoryList.value[categoryIndexSelect.value].eventCategoryId
    console.log(filterData.eventCategoryId);
    // console.log(time.value);
    emits('getChageCategory', filterData.eventCategoryId);
    
}

const getReset = () => {
    categoryIndexSelect.value = undefined
    emits('getReset');
}

const getChangeToPast = () => {
    emits('getPastEvent');
}
const getChangeToUpcoming = () => {
    emits('getUpComingEvent');
}

const getChangeByDateTime = (times) => {
    emits('getDateTime',times);
}

</script>
 
<template>
    <div class="grid grid-flow-row grid-cols-10 w-[90%] mb-10 m-auto gap-2 ">
        <button class="row-start-1 col-start-1  col-span-1 w-full  p-2 bg-[#E2DDDD] rounded-xl uppercase font-bold"
            @click="getReset">reset</button>
        <button class="row-start-1 col-start-2  col-span-1 w-full  p-2 bg-[#E2DDDD] rounded-xl uppercase font-bold"
            @click="getChangeToPast">past-event</button>
        <button class="row-start-1 col-start-3  col-span-1 w-full  p-2 bg-[#E2DDDD] rounded-xl uppercase font-bold"
            @click="getChangeToUpcoming">upcoming-event</button>

        <input class="row-start-1 col-start-8  col-span-1 w-full  p-2 bg-[#E2DDDD] rounded-xl uppercase font-bold"
            type="date" name="" id="" v-model="time" @change="getChangeByDateTime(time)">

        <div class="row-start-1 col-start-9 col-span-2 w-full  p-2 bg-[#E2DDDD] rounded-xl uppercase">
            <!-- <select class="bg-gray-200 rounded w-full" v-model="CategorySelect" @change="hanleSelcet()"> -->
            <select class="p-2 bg-[#E2DDDD] rounded w-full font-bold" v-model="categoryIndexSelect"
                @change="getChangeCategory()">
                <!-- <option value="">select category</option> -->
                <!-- <option value="" selected hidden>Choose a drink</option> -->
                <option v-for="(category, indexs) in categoryLists" :value="indexs" :key="indexs">
                    <!-- <option value=""></option> -->
                    {{ category.eventCategoryName }}
                </option>
            </select>
        </div>
    </div>
</template>
 
<style>
</style>