<template>
  <div class="app__group__container">
    <div class="app__group__container__header">
        <p v-if="message">{{message}}</p>
        <h1 class="">Thread</h1>
        <button @click="handleClick('create', '')">Create Thread</button>
    </div>
    <div v-if="isTryingToUpdate||isTryingToCreate" class="app__group__container__content">
        <div v-if="isTryingToCreate" class="app__group__container__content__elementContainer">
            <label for="name" class="app__group__container__content__elementContainer__elementTitle">
                Thread title
            </label>
            <input class="app__group__container__content__elementContainer__elementValue" id="name" name="name" v-model="newThread.title"/>
        </div>

        <div v-if="!isTryingToCreate" class="app__group__container__content__elementContainer">
            <label for="name" class="app__group__container__content__elementContainer__elementTitle">
                Thread title
            </label>
            <p class="app__group__container__content__elementContainer__elementValue" id="name" name="name">
                {{newThread.title}}
            </p>
        </div>

        <div class="app__group__container__content__elementContainer">
            <label for="description" class="app__group__container__content__elementContainer__elementTitle">
                Thread content
            </label>
            <input class="app__group__container__content__elementContainer__elementValue" id="description" name="description" v-model="newThread.content"/>
        </div>

        <button @click="handleSave()" class="app__group__container__content__button">
            {{isTryingToUpdate ? "Update" : "Create"}}
        </button>
    </div>
    <div class="app__group__container__content">
        <div v-for="thread in threads" :key="thread.id" class="app__group__container__content__groupContainer">
            <div class="app__group__container__content__groupContainer__group">
                <p class="">{{ thread.title }}</p>
                <p class="">{{ thread.content }}</p>
                <button @click="handleClick('update', thread.title)">Update Thread</button>
                <button @click="deleteThread(thread.title)">Delete Thread</button>
                <button @click="navigate(thread.id)">Thread's Posts</button>
            </div>
        </div>
    </div>
  </div>
</template>

<script>
export default {
    async beforeCreate() {

        let res = await fetch('/api/thread/threadsOfGroup?groupId=' + this.$route.params.id,{
                method: 'GET'
            })
        this.threads = await res.json();
    },
    data() {
        return {
            message: '',
            isLoggedIn: sessionStorage.getItem("access_token") ? true:false,
            isTryingToUpdate: false,
            isTryingToCreate: false,
            name: '',
            threads: [],
            newThread: {
                title: "",
                content: ""
            },
        }
    },
    methods:{
        navigate(id) {
            this.$router.push('/thread/' + id)
        },
         
        handleClick(e, name){
            if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
                if(e === "update"){
                    if(name == "[Deleted Thread]"){
                        this.message = "This thread was already deleted in DB, can not be updated"
                        this.isTryingToUpdate = false;
                        this.isTryingToCreate = false;
                    }
                    else{
                        this.isTryingToUpdate = true;
                        this.isTryingToCreate = false;
                        this.newThread.title = name;
                        this.message = "";
                    }
            
                }else{
                    this.isTryingToUpdate = false;
                    this.isTryingToCreate = true;
                    this.newThread.title = name;
            }}else{
                this.message = "You need to log in first"
            }
        },

        handleSave(){
            if(this.isTryingToCreate){
                this.createThread();
            }else{
                this.updateThread();
            }
        },

        async createThread() {
            if(!this.newThread.title || !this.newThread.content){
                this.message = "Please fill all thread info"
            }else if(this.newThread.title == "[Deleted Thread]"){
                this.message = "Invalid Thread Title"
            }
            else{

                let res = await fetch('/api/thread/create?groupId=' + this.$route.params.id + '&title=' + this.newThread.title +
                '&content=' + this.newThread.content, {
                    method: 'POST',
                    headers: {
                        "Authorization": "Bearer " + sessionStorage.getItem("access_token")
                    },
                })
                var msg = await res.text();
    
                if(msg == "Thread Has Been Created"){
                    this.message = msg;
                    this.threads = await this.fetchThreads();
                }else if(msg == "Thread Title Is Taken" || msg == "User Is Blocked" || msg == "Group Is Blocked"){
                    this.message = msg;
                }else if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
                    this.getAccessTokenWithRefresh();
                    // double if cause the above method might force logout
                    if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
                        this.createThread();
                    }
                }
            }
        },

        async updateThread() {
            if(!this.newThread.title || !this.newThread.content){
                this.message = "Please fill all thread info"
            }else if(this.newThread.title == "[Deleted Thread]"){
                this.message = "This thread was already deleted in DB, can not be updated"
            }else{
                // not needed to send as rest since i sent JSON but i don't want to waste time here anymore
                let res = await fetch('/api/thread/update?title=' + this.newThread.title + '&content=' + this.newThread.content, {
                    method: 'PUT',
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": "Bearer " + sessionStorage.getItem("access_token")
                    },
                    body: JSON.stringify(this.newThread)
                })
    
                var msg = await res.text();
                if(msg == "Thread Has Been Updated"){
                    this.message = msg;
                    this.threads = await this.fetchThreads();
                }else if(msg == "Thread Title Is Taken" || msg == "User Is Blocked"
                 || msg == "Thread Is Blocked" || msg == "Thread Does Not Exist"
                 || msg == "You Can't Update Other's Threads"){
                    this.message = msg;
                }else if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
                    this.getAccessTokenWithRefresh();
                    // double if cause the above method might force logout
                    if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
                        let res = await fetch('/api/thread/update?title=' + this.newThread.title + '&content=' + this.newThread.content, {
                            method: 'PUT',
                            headers: {
                                "Content-Type": "application/json",
                                "Authorization": "Bearer " + sessionStorage.getItem("access_token")
                            },
                            body: JSON.stringify(this.newThread)
                        })
            
                        var msg = await res.text();
                        if(msg == "Thread Has Been Updated"){
                            this.message = msg;
                            this.threads = await this.fetchThreads();
                        }else{
                            this.message = msg;
                        }
                    }

                }
            }
        },

        async deleteThread(threadName){
            if(threadName == "[Deleted Thread]"){
                this.message = "This thread was already deleted in DB, can not be deleted again"
            }
            else{

                let res = await fetch('/api/thread/delete?title=' + threadName, {
                        method: 'DELETE',
                        headers: {
                            "Authorization": "Bearer " + sessionStorage.getItem("access_token")
                        },
                    })
        
                    var msg = await res.text();
                    if(msg == "Thread Has Been Deleted"){
                        this.message = msg;
                        this.threads = await this.fetchThreads();
                    }else if(msg == "User Is Blocked"
                     || msg == "Thread Is Blocked" || msg == "Thread Does Not Exist"
                     || msg == "You Can't Delete Other's Threads"){
                        this.message = msg;
                    }else if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
                        this.getAccessTokenWithRefresh();
                        // double if cause the above method might force logout
                        if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
                            let res = await fetch('/api/thread/delete?title=' + threadName, {
                                method: 'DELETE',
                                headers: {
                                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
                                },
                            })
                
                            var msg = await res.text();
                            if(msg == "Thread Has Been Deleted"){
                                this.message = msg;
                                console.log(this.threads)
                                this.threads = await this.fetchThreads();
                                console.log(this.threads)
                            }else{
                                this.message = msg;
                            }
                        }
    
                    }
            }
        },

        async getAccessTokenWithRefresh(){
            let res = await fetch('/api/user/token/refresh',{
                method: 'GET',
                headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("refresh_token")
                }
            })
            var token = await res.json();
            if (!token["msg"]) {
                sessionStorage.setItem("access_token", token["access_token"])
                sessionStorage.setItem("refresh_token", token["refresh_token"])
            }else{
                this.message = "Please try to login!";
                sessionStorage.clear();
            }
        },

        async fetchThreads() {
            let res = await fetch('/api/thread/threadsOfGroup?groupId=' + this.$route.params.id,{
                method: 'GET'
            })
            return await res.json()
        },
    }
}
</script>

<style scoped>
.app__group__container__content__groupContainer{
    border: 2px solid;
    margin: 0.5rem;
}
</style>