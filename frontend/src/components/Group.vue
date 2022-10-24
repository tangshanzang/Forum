<template>
  <div class="app__group__container">
    <div class="app__group__container__header">
        <p v-if="message">{{message}}</p>
        <h1 class="">Group</h1>
        <button @click="handleClick('update')">Update Group</button>
        <button @click="handleClick('create')">Create Group</button>
    </div>
    <div v-if="isTryingToUpdate||isTryingToCreate" class="app__group__container__content">
        <div class="app__group__container__content__elementContainer">
            <label for="name" class="app__group__container__content__elementContainer__elementTitle">
                Group name
            </label>
            <input class="app__group__container__content__elementContainer__elementValue" id="name" name="name" v-model="newGroup.name"/>
        </div>

        <div class="app__group__container__content__elementContainer">
            <label for="description" class="app__group__container__content__elementContainer__elementTitle">
                Group description
            </label>
            <input class="app__group__container__content__elementContainer__elementValue" id="description" name="description" v-model="newGroup.description"/>
        </div>

        <button @click="handleSave()" class="app__group__container__content__button">
            {{isTryingToUpdate ? "Update" : "Create"}}
        </button>
    </div>
  </div>
</template>

<script>
export default {
    async beforeCreate() {

        let res = await fetch('/api/group/groups',{
                method: 'GET'
            })
        this.groups = await res.json();
    },
    data() {
        return {
            message: '',
            isLoggedIn: sessionStorage.getItem("access_token") ? true:false,
            isTryingToUpdate: false,
            isTryingToCreate: false,
            name: '',
            newName: '',
            groups: [],
            newGroup: {
                name: "",
                description: ""
            },
        }
    },
    methods:{
        handleClick(e){
            if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
                if(e === "update"){
                    this.isTryingToUpdate = true;
                    this.isTryingToCreate = false;
                }else{
                    this.isTryingToUpdate = false;
                    this.isTryingToCreate = true;
            }}else{
                this.message = "You need to log in first"
            }
        },

        handleSave(){
            if(this.isTryingToCreate){
                this.createGroup();
            }else{

            }
        },

        async createGroup() {
            let res = await fetch('/api/group/create', {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
                },
                body: JSON.stringify(this.newGroup)
            })
            var msg = await res.text();

            if(msg == "Group Has Been Created"){
                this.message = msg;
                this.groups = await this.fetchGroups();
            }else if(msg == "Group Name Is Taken" || msg == "User Is Blocked"){
                this.message = msg;
            }else if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
                this.getAccessTokenWithRefresh();
                // double if cause the above method might force logout
                if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
                    this.createGroup();
                }
            }
        },

        async updateGroup() {
            let res = await fetch('/api/group/update?name=' + this.newGroup.name + '&description=' + this.description, {
                method: 'POST',
                headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
                },
                body: JSON.stringify(this.newGroup)
            })

            var msg = await res.text();
            console.log(msg);

            // if(msg){
            //     this.message = await res.text()
            //     this.groups = await this.fetchGroups()
            // }else if(res.status === 403 && (sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token"))){
            //     this.getAccessTokenWithRefresh();
            //     // double if cause the above method might force logout
            //     if(res.status === 403 && (sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token"))){
            //         this.createGroup();
            //     }
            // }
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
                this.message = "Please try to logout and re login!";
            }
        },

        async fetchGroups() {
            let res = await fetch('/api/group/groups',{
                method: 'GET'
            })
            return await res.json()
        },
    }
}
</script>

<style>

</style>