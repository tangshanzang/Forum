<template>
  <div class="app__group__container">
    <div class="app__group__container__header">
        <p v-if="message">{{message}}</p>
        <h1 class="">Group</h1>
        <button @click="handleClick('create', '')">Create Group</button>
    </div>
    <div v-if="isTryingToUpdate||isTryingToCreate" class="app__group__container__content">
        <div v-if="isTryingToCreate" class="app__group__container__content__elementContainer">
            <label for="name" class="app__group__container__content__elementContainer__elementTitle">
                Group name
            </label>
            <input class="app__group__container__content__elementContainer__elementValue" id="name" name="name" v-model="newGroup.name"/>
        </div>

        <div v-if="!isTryingToCreate" class="app__group__container__content__elementContainer">
            <label for="name" class="app__group__container__content__elementContainer__elementTitle">
                Group name
            </label>
            <p class="app__group__container__content__elementContainer__elementValue" id="name" name="name">
                {{newGroup.name}}
            </p>
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
    <div class="app__group__container__content">
        <div v-for="group in groups" :key="group.id" class="app__group__container__content__groupContainer">
            <div class="app__group__container__content__groupContainer__group">
                <p class="">{{ group.name }}</p>
                <p class="">{{ group.description }}</p>
                <button @click="handleClick('update', group.name)">Update Group</button>
                <button @click="deleteGroup(group.name)">Delete Group</button>
                <button @click="navigate(group.id)">Group's Threads</button>
            </div>
        </div>
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
        navigate(id) {
            this.$router.push('/group/' + id)
        },
         
        handleClick(e, name){
            if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
                if(e === "update"){
                    if(this.newGroup.name == "[Deleted Group]"){
                        this.message = "This group was already deleted in DB, can not be updated"
                    }
                    else{
                        this.isTryingToUpdate = true;
                        this.isTryingToCreate = false;
                        this.newGroup.name = name;

                    }
            
                }else{
                    this.isTryingToUpdate = false;
                    this.isTryingToCreate = true;
                    this.newGroup.name = name;
            }}else{
                this.message = "You need to log in first"
            }
        },

        handleSave(){
            if(this.isTryingToCreate){
                this.createGroup();
            }else{
                this.updateGroup();
            }
        },

        async createGroup() {
            if(!this.newGroup.name || !this.newGroup.description){
                this.message = "Please fill all group info"
            }else if(this.newGroup.name == "[Deleted Group]"){
                this.message = "Invalid Group Name"
            }
            else{

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
            }
        },

        async updateGroup() {
            if(!this.newGroup.name || !this.newGroup.description){
                this.message = "Please fill all group info"
            }else if(this.newGroup.name == "[Deleted Group]"){
                this.message = "This group was already deleted in DB, can not be updated"
            }else{

                let res = await fetch('/api/group/update?name=' + this.newGroup.name + '&description=' + this.description, {
                    method: 'PUT',
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": "Bearer " + sessionStorage.getItem("access_token")
                    },
                    body: JSON.stringify(this.newGroup)
                })
    
                var msg = await res.text();
                if(msg == "Group Has Been Updated"){
                    this.message = msg;
                    this.groups = await this.fetchGroups();
                }else if(msg == "Group Name Is Taken" || msg == "User Is Blocked"
                 || msg == "Group Is Blocked" || msg == "Group Does Not Exist"
                 || msg == "You Can't Update Other's Groups"){
                    this.message = msg;
                }else if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
                    this.getAccessTokenWithRefresh();
                    // double if cause the above method might force logout
                    if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
                        let res = await fetch('/api/group/update?name=' + this.newGroup.name + '&description=' + this.description, {
                            method: 'PUT',
                            headers: {
                                "Content-Type": "application/json",
                                "Authorization": "Bearer " + sessionStorage.getItem("access_token")
                            },
                            body: JSON.stringify(this.newGroup)
                        })
            
                        var msg = await res.text();
                        if(msg == "Group Has Been Updated"){
                            this.message = msg;
                            this.groups = await this.fetchGroups();
                        }else{
                            this.message = msg;
                        }
                    }

                }
            }
        },

        async deleteGroup(groupName){
            if(groupName == "[Deleted Group]"){
                this.message = "This group was already deleted in DB, can not be deleted again"
            }
            else{

                let res = await fetch('/api/group/delete?name=' + groupName, {
                        method: 'DELETE',
                        headers: {
                            "Authorization": "Bearer " + sessionStorage.getItem("access_token")
                        },
                    })
        
                    var msg = await res.text();
                    if(msg == "Group Has Been Deleted"){
                        this.message = msg;
                        this.groups = await this.fetchGroups();
                    }else if(msg == "User Is Blocked"
                     || msg == "Group Is Blocked" || msg == "Group Does Not Exist"
                     || msg == "You Can't Delete Other's Groups"){
                        this.message = msg;
                    }else if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
                        this.getAccessTokenWithRefresh();
                        // double if cause the above method might force logout
                        if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
                            let res = await fetch('/api/group/delete?name=' + groupName, {
                                method: 'DELETE',
                                headers: {
                                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
                                },
                            })
                
                            var msg = await res.text();
                            if(msg == "Group Has Been Deleted"){
                                this.message = msg;
                                this.groups = await this.fetchGroups();
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

        async fetchGroups() {
            let res = await fetch('/api/group/groups',{
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