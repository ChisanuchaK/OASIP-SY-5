<script setup>
import { ref ,reactive} from 'vue'

let categoryList = reactive([
    {
        "eventCategoryName": "Client-side Clinic",
        "eventCategoryDescription": "ตารางนัดหมายนี้ใช้สําหรับนัดหมาย client-side clinic ในวิชา INT221 integrated project I",
        "eventDuration": 30
    },
    {
        "eventCategoryName": "Database Clinic",
        "eventCategoryDescription": "ตารางนัดหมายนี้ใช้สําหรับนัดหมาย database clinic ในวิชา INT221 integrated project I",
        "eventDuration": 15
    },
    {
        "eventCategoryName": "DevOps/Infra Clinic",
        "eventCategoryDescription": "Use this event category for DevOps/Infra clinic.",
        "eventDuration": 20
    },
    {
        "eventCategoryName": "Other",
        "eventCategoryDescription": "ตารางนัดหมายนี้ใช้สำหรับนัดหมาย Clinic อิ่น",
        "eventDuration": 30
    },
    {
        "eventCategoryName": "Project Management Clinic",
        "eventCategoryDescription": "ตารางนัดหมายนี้ใช้สําหรับนัดหมาย project management clinic ในวิชา INT221 integrated project I ให้นักศึกษาเตรียมเอกสารที่เกี่ยวข้องเพื่อแสดงระหว่างขอคําปรึกษา",
        "eventDuration": 30
    },
    {
        "eventCategoryName": "Server-side Clinic",
        "eventCategoryDescription": null,
        "eventDuration": 30
    }
])

let CategorySelect = ref()
let DateSelect = ref()

let holeData = reactive({
    bookingName: "",
    bookingEmail: "",
    eventCategory: {eventCategoryName:""} ,
    eventStartTime: "",
    eventDuration: "",
    eventNotes:  ""
})


const hanleSelcet = () =>{
    holeData.eventCategory.eventCategoryName = categoryList[CategorySelect.value].eventCategoryName
    holeData.eventDuration = categoryList[CategorySelect.value].eventDuration
    // alert(CategorySelect)
    console.log( holeData )
}

const handleTime = () =>{

    holeData.eventStartTime = DateSelect.value+':00Z'

}


</script>
 
<template>

<div style="background-color:rgb(189 189 189);">
    bookingName <input v-model="holeData.bookingName" type="text"> <br/><br/>
    bookingEmail <input v-model="holeData.bookingEmail" type="text"> <br/><br/>
    eventCategory 
    <select v-model="CategorySelect" @change="hanleSelcet()">
        <option v-for="(res,indexs) in categoryList" :value="indexs" v-bind:key="indexs" >{{ res.eventCategoryName }}</option>
    </select> <br/><br/>
    eventStartTime <input v-model="DateSelect" @change="handleTime()"  type="datetime-local"> <br/><br/>
    eventDuration <input v-model="holeData.eventDuration" readonly type="text"> <br/> <br/>
    eventNotes  <input v-model="holeData.eventNotes" type="text"> <br/><br/>
    

    <br/>

<pre>

{{ holeData }}



</pre>


</div>


</template>
 
<style>

</style>