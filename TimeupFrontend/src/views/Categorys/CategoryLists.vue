<script setup>
import { ref, onBeforeMount,computed } from 'vue'
import CategoryList from '../../components/Categorys/CategoryList.vue';
// import LoopCategoryList from '../../components/Categorys/CategoryList.vue';
import PleaseLogInDialog from '../../components/PleaseLogInDialog.vue';
import { categoryStore } from '../../stores/category.js';
import { userStore } from '../../stores/user.js';
import { useRouter } from 'vue-router';

const appRouter = useRouter();

const categoryRes = categoryStore();
const userSignInRes = userStore();

userSignInRes.getRefreshToken().then(()=>{
  if (userSignInRole.value == 'student' || userSignInRole.value == 'guest') {
    appRouter.go(-1);
  }
})

// const  checkLoginUser = ()=> userSignInRes.signInUserData.userRole == 'guest' ? '' : categoryRes.getEventCategory() ;
categoryRes.getEventCategory();
const categoryLists = computed(() => categoryRes.categorys);

const userSignInRole = computed(()=>userSignInRes.signInUserData.userRole);
const pageName = ref('view CATEGORY-LIST');
// const categoryLists = computed(() => eventRes.allCategorys);
// const categoryLists = computed(() => eventsStore().allCategorys.data);

// ----- use when not use pinia
// const filterEditCategory = (editCategory) => {
//   categoryLists.value = categoryLists.value.map((category) =>
//     category.eventCategoryId === editCategory.eventCategoryId
//       ? { ...category, eventCategoryName: editCategory.eventCategoryName, eventDuration: editCategory.eventDuration, eventCategoryDescription: editCategory.eventCategoryDescription }
//       : category
//   )
//   console.log(editCategory.eventCategoryId);
// }


onBeforeMount(async () => {
  // checkLoginUser();
  
  // responseGetAllCategory.value = await getEventCategory();
  // if(responseGetAllCategory.value.status === 200){
  //   categoryLists.value = await responseGetAllCategory.value.data;
  //   console.log(categoryLists.value);
  categoryLists.value.map((category) => {
    category.statusConfirmDialog = ref(false)
    category.statusCancelDialog = ref(false)
    category.statusEditDialog = ref(false)
    category.statusEditSuccesDialog = ref(false)
  })
  // }else{
  //   appRouter.go(-1);
  // }
})
</script>
 
<template>
  <div>
    <CategoryList :categoryLists="categoryLists.sort((a, b) => b.eventCategoryId - a.eventCategoryId)" />
    <!--  // ----- use when not use pinia  @EditCategoryIdFromEdit="filterEditCategory" -->
    <PleaseLogInDialog v-if="userSignInRole == 'guest'" :pageName="pageName"/>
  </div>
</template>
 
<style>

</style>