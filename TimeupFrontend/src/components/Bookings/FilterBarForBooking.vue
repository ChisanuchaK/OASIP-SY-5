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
const time = ref();

const filterData = reactive({
    eventCategoryId: "",
    eventStartTime: ""
});

const getChangeCategory = () => {
    filterData.eventCategoryId = categoryList.value[categoryIndexSelect.value].eventCategoryId
    console.log(filterData.eventCategoryId);
    time.value = ''
    // console.log(time.value);
    emits('getChageCategory', filterData.eventCategoryId);
    
};

const getAllEvent = () => {
    categoryIndexSelect.value = ''
    time.value = ''
    emits('getAllEvent');
};

const getChangeToPast = () => {
    categoryIndexSelect.value = ''
    time.value = ''
    emits('getPastEvent');
};

const getChangeToUpcoming = () => {
    categoryIndexSelect.value = ''
    time.value = ''
    emits('getUpComingEvent');
};

const getChangeByDateTime = (times) => {
    categoryIndexSelect.value = ''
    emits('getDateTime',times);
};

const colorBg = (categorys) => {
    for (let category of categoryList.value) {
        if(categorys.eventCategoryId == category.eventCategoryId){
            return category.eventColor
        }
            
    }
};

</script>
<template>
    <div class="grid grid-flow-row grid-cols-10 w-[90%] mb-10 m-auto gap-2 ">
        <button class="row-start-1 col-start-1  col-span-1 w-full  p-2 bg-gray-200 border-4 border-[#50ABCB] hover:bg-[#50ABCB] rounded-xl uppercase font-bold drop-shadow-md"
            @click="getAllEvent">all-event</button>
        <button class="row-start-1 col-start-2  col-span-1 w-full  p-2 bg-gray-200 border-4 border-[#50ABCB] hover:bg-[#50ABCB] rounded-xl uppercase font-bold drop-shadow-md"
            @click="getChangeToPast">past-event</button>
        <button class="row-start-1 col-start-3  col-span-1 w-full  p-2 bg-gray-200 border-4 border-[#50ABCB] hover:bg-[#50ABCB] rounded-xl uppercase font-bold drop-shadow-md"
            @click="getChangeToUpcoming">upcoming-event</button>

        <input class="row-start-1 col-start-8  col-span-1 w-full  p-2 bg-gray-200 border-4 border-[#50ABCB] hover:bg-[#50ABCB] rounded-xl uppercase font-bold drop-shadow-md"
            type="date" name="" id="" v-model="time" @change="getChangeByDateTime(time)">

        <div class="row-start-1 col-start-9 col-span-2 w-full  p-2 bg-gray-200 border-4 border-[#50ABCB] hover:bg-[#50ABCB] rounded-xl uppercase drop-shadow-md">
               
            <select class="p-2 bg-gray-200 hover:bg-[#50ABCB] rounded w-full font-bold" v-model="categoryIndexSelect"
                @change="getChangeCategory()">
                <option value="" selected hidden >Select category</option>
                <option v-for="(category, indexs) in categoryLists" :value="indexs" :key="indexs">
                    {{ category.eventCategoryName }}
                </option>
            </select>
        </div>
    </div>
</template>
 
<style scoped>

</style>