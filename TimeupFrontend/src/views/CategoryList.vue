<script setup>
import { ref, onBeforeMount } from 'vue'
import LoopCategoryList from '../components/LoopCategoryList.vue';
import { getEventCategory } from '../stores/book.js';
import PleaseLogInDialog from '../components/PleaseLogInDialog.vue';
import NavbarTop from '../components/NavbarTop.vue';
import NavbarBottom from '../components/NavbarBottom.vue';

const categoryLists = ref([]);
<<<<<<< HEAD
const responseGetAllCategory = ref({});
const getToken = localStorage.getItem('refreshToken');
=======
const getToken = localStorage.getItem('accessToken');
>>>>>>> e68bf99f029f839b3128d4974e130d7b8b5218ac
const pageName = ref('view CATEGORY-LIST');

onBeforeMount(async () => {
  responseGetAllCategory.value = await getEventCategory();
  categoryLists.value = await responseGetAllCategory.value.data;
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
    <NavbarTop/>
    <NavbarBottom/>
    <LoopCategoryList :categorysLists="categoryLists.sort((a, b) => b.eventCategoryId - a.eventCategoryId)"
      @EditCategoryIdFromEdit="filterEditCategory" />
    <PleaseLogInDialog v-if="!getToken" :pageName="pageName"/>
  </div>
</template>
 
<style>
</style>