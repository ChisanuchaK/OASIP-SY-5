<script setup>
import { ref, onBeforeMount } from 'vue'
import NavbarTop from "../components/NavbarTop.vue";
import NavbarBottom from "../components/NavbarBottom.vue";
import LoopCategoryList from '../components/LoopCategoryList.vue';
import { getEventCategory } from '../stores/book.js';
import PleaseLogInDialog from '../components/PleaseLogInDialog.vue';

const categoryLists = ref([]);
const getToken = localStorage.getItem('token');
const pageName = ref('view CATEGORY-LIST');

onBeforeMount(async () => {
  const getAllCategory = await getEventCategory();
  categoryLists.value = await getAllCategory.json();
  console.log(categoryLists.value);
  categoryLists.value.map((category) => {
    category.statusConfirmDialog = ref(false)
    category.statusCancelDialog = ref(false)
    category.statusEditDialog = ref(false)
    category.statusEditSuccesDialog = ref(false)
  })
})

const filterEditCategory = (editCategory) => {
  categoryLists.value = categoryLists.value.map((category) =>
    category.eventCategoryId === editCategory.eventCategoryId
      ? { ...category, eventCategoryName: editCategory.eventCategoryName, eventDuration: editCategory.eventDuration, eventCategoryDescription: editCategory.eventCategoryDescription }
      : category
  )
  console.log(editCategory.eventCategoryId);
}

</script>
 
<template>
  <div>
    <NavbarTop />

    <LoopCategoryList :categorysLists="categoryLists.sort((a, b) => b.eventCategoryId - a.eventCategoryId)"
      @EditCategoryIdFromEdit="filterEditCategory" />

    <PleaseLogInDialog v-if="!getToken" :pageName="pageName"/>
    <NavbarBottom />
  </div>
</template>
 
<style>
</style>