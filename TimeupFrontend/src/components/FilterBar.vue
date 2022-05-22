<script setup>
import { ref, computed, reactive } from 'vue';
import moment from 'moment';
const emits = defineEmits(["getChageCategory", "getAllEvent", "getPastEvent", "getUpComingEvent", "getDateTime"])
const props = defineProps({
    categorys: {
        type: [Object],
        default: {}
    }
})

const categoryLists = computed(() => props.categorys);
const categoryList = ref(categoryLists);

const categoryIndexSelect = ref('');

// const time = ref(moment().format("YYYY-MM-DD"))
const time = ref()

const filterData = reactive({
    eventCategoryId: "",
    eventStartTime: ""
})

const getChangeCategory = () => {
    filterData.eventCategoryId = categoryList.value[categoryIndexSelect.value].eventCategoryId
    console.log(filterData.eventCategoryId);
    time.value = ''
    // console.log(time.value);
    emits('getChageCategory', filterData.eventCategoryId);
    
}

const getAllEvent = () => {
    categoryIndexSelect.value = ''
    time.value = ''
    emits('getAllEvent');
}

const getChangeToPast = () => {
    categoryIndexSelect.value = ''
    time.value = ''
    emits('getPastEvent');
}
const getChangeToUpcoming = () => {
    categoryIndexSelect.value = ''
    time.value = ''
    emits('getUpComingEvent');
}

const getChangeByDateTime = (times) => {
    categoryIndexSelect.value = ''
    emits('getDateTime',times);
}

const colorBg = (categorys) => {
    for (let category of categoryList.value) {
        if(categorys.eventCategoryId == category.eventCategoryId){
            return category.eventColor
        }
            
    }
}

</script>
 
<template>
    <div class="grid grid-flow-row grid-cols-10 w-[90%] mb-10 m-auto gap-2 ">
        <button class="row-start-1 col-start-1  col-span-1 w-full  p-2 bg-[#E2DDDD] rounded-xl uppercase font-bold"
            @click="getAllEvent">all-event</button>
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
                <option value="" selected hidden >Select category</option>
                <option v-for="(category, indexs) in categoryLists" :value="indexs" :key="indexs">
                    <!-- <option value=""></option> -->
                    {{ category.eventCategoryName }}
                </option>
            </select>
        </div>
    </div>
</template>
 
<style scoped>

</style>