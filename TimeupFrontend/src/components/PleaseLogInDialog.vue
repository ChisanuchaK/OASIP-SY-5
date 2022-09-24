<script setup>
    import { computed, onMounted } from 'vue';
    import { useRouter } from 'vue-router'; 
    // const emits = defineEmits(["cancelDeleteUser", "confirmDeleteUser"])
    const props = defineProps({
        pageName: {
            type: String,
            default: ''
        }
    })
    
    const pageName = computed(() => props.pageName);
    const appRouter = useRouter();
    const goToSignIn = () => appRouter.push({name: 'SignIn'})

    const pushToSignIn = (event) => {
        if (event.target.id == "PleaseLogInDialog") {
            goToSignIn()
        } else return;
    }
    
    </script>
     
    <template>
        <div id="PleaseLogInDialog" @click="pushToSignIn"
            class="font overflow-x-hidden overflow-y-auto fixed focus:outline-none justify-center items-center flex inset-0 z-20 outline-none  bg-black bg-opacity-20">
            <div class="">
                <div class="text-center bg-white p-10 rounded-lg">
                    <div class="p-20 ">

                        <p class="text-4xl mb-5"> plese SIGN-IN </p>
                        <p class="text-4xl mb-5"> to {{pageName}} </p>
                    </div>
                    <div class="grid grid-flow-row grid-cols-3 ">
                        <button class="col-start-2 p-3 bg-[#00A28B] text-white rounded-lg"
                            @click="goToSignIn()">Sign-In</button>
                    </div>
                </div>
            </div>
        </div>
    </template>