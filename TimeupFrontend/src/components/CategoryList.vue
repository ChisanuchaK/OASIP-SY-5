<script setup>
 
    defineProps({
        listEventCatgorys:{
            type: Array
        }
    })

</script>
 
<template>
    <div class="container">
        <div class="tilte">
            <h1>SELECT SCHEDULED</h1>
        </div>
        <div class="list-event-category" v-for="(listEventCatgory , index) in listEventCatgorys" :key="index">
                {{listEventCatgory}}
        </div>
    </div>
</template>
 
<style>

</style>