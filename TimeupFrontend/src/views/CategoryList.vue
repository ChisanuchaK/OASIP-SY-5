<script setup>
import { ref, onBeforeMount } from 'vue'
import LoopCategoryList from '../components/LoopCategoryList.vue';
import { getEventCategory } from '../stores/book.js';
import PleaseLogInDialog from '../components/PleaseLogInDialog.vue';
import NavbarTop from '../components/NavbarTop.vue';
import NavbarBottom from '../components/NavbarBottom.vue';
import { useRouter } from 'vue-router';

const appRouter = useRouter();

const categoryLists = ref([]);
const responseGetAllCategory = ref({});
const getToken = ref(localStorage.getItem('refreshToken'));
const getUserRole = ref(localStorage.getItem('userRole'));
const pageName = ref('view CATEGORY-LIST');

onBeforeMount(async () => {
  if(getUserRole.value == 'student'){
    appRouter.go(-1);
  }
  responseGetAllCategory.value = await getEventCategory();
  if(responseGetAllCategory.value.status === 200){
    categoryLists.value = await responseGetAllCategory.value.data;
    console.log(categoryLists.value);
    categoryLists.value.map((category) => {
      category.statusConfirmDialog = ref(false)
      category.statusCancelDialog = ref(false)
      category.statusEditDialog = ref(false)
      category.statusEditSuccesDialog = ref(false)
    })
  }else{
    appRouter.go(-1);
  }
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