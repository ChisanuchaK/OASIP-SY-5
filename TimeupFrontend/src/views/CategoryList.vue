<script setup>
import { ref, onBeforeMount } from 'vue'
import NavbarTop from "../components/NavbarTop.vue";
import NavbarBottom from "../components/NavbarBottom.vue";
import LoopCategoryList from '../components/LoopCategoryList.vue';
import { getEventCategory } from '../stores/book.js';

const categoryList = ref([]);

onBeforeMount(async () => {
  const getAllCategory = await getEventCategory();
 categoryList.value = await getAllCategory.json();
 console.log(categoryList.value);
 categoryList.value.map((category)=>{
     category.statusConfirmDialog = ref(false)
     category.statusCancelDialog = ref(false)
     category.statusEditDialog = ref(false)
 })
})

const filterEditCategory = (editCategory)=>{
  categoryList.value = categoryList.value.map((category)=>
    category.eventCategoryId === editCategory.eventCategoryId
    ? {...category, eventCategoryName: editCategory.eventCategoryName , eventDuration: editCategory.eventDuration, eventCategoryDescription : editCategory.eventCategoryDescription}
    : category
  )
        console.log(editCategory.eventCategoryId);
}

</script>
 
<template>
    <NavbarTop />
    
    <LoopCategoryList :categorysList="categoryList" @EditCategoryIdFromEdit="filterEditCategory"/>
    <NavbarBottom />
</template>
 
<style>
</style>