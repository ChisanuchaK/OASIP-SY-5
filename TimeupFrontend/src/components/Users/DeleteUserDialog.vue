<script setup>
import { computed, onMounted } from 'vue';
const emits = defineEmits(["cancelDeleteUser", "confirmDeleteUser"])
const props = defineProps({
    propsUserToDelete: {
        type: Object,
        default: {}
    }
})


const userToDelete = computed(() => props.propsUserToDelete);

const closeDelete = (event) => {
    if (event.target.id == "modalDeletedUser") {
        emits('cancelDeleteUser')
    } else { }
}

</script>
 
<template>
    <div id="modalDeletedUser" @click="closeDelete"
        class="font overflow-x-hidden overflow-y-auto fixed focus:outline-none justify-center items-center flex inset-0 z-20 outline-none  bg-black bg-opacity-20">
        <div class="">
            <div class="text-center bg-white p-10 rounded-lg">
                <div class="p-20 ">
                    <p class="text-4xl mb-5">Delete ?</p>
                    click confirm to delete user <br>
                    ID : {{ userToDelete.idUser }} <br>
                    {{ userToDelete.nameUser }} <br>
                    {{ userToDelete.emailUser }}

                </div>
                <div class="grid grid-flow-row grid-cols-4 ">
                    <button class="col-start-1 p-3 bg-[#F97473] text-white rounded-lg justify-start"
                        @click="$emit('cancelDeleteUser')">cancel</button>
                    <button class="col-start-4 p-3 bg-[#499D6B] text-white rounded-lg"
                        @click="$emit('confirmDeleteUser', userToDelete.idUser,userToDelete)">confirm</button>
                </div>
            </div>
        </div>
    </div>
</template>