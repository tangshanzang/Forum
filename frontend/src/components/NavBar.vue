

<template>
    <div class="app__nav__container">
        <div class="app__nav__bar">
            <p @click="showForum = true">Forum</p>
            <router-link v-if="isAdmin" :to="'/admin'">admin</router-link>
            <p v-if="!isLoggedIn" @click="isRegister=false">Login</p>
            <p v-else @click="logout()">Logout</p>
            <p v-if="!isLoggedIn" @click="isRegister=true">Register</p>
        </div>
        <div class="app__nav__message" v-if="message">
            <p class="app__nav__message_text">
                {{message}}
            </p>
        </div>

        <div class="app__nav__loginBar" v-if="!isRegister && !isLoggedIn">
            <h1>Login</h1>
            <div class="app__nav__loginBar__elementContainer">
                <p class="app__nav__loginBar__elementTitle">Username</p>
                <input class="app__nav__loginBar__elementValue" type="text" v-model="user.username" @keydown="checkKeyDownAlphaNumeric($event)"/>
            </div>
            <div class="app__nav__loginBar__elementContainer">
                <p class="app__nav__loginBar__elementTitle">Password</p>
                <input class="app__nav__loginBar__elementValue" type="text" v-model="user.password" @keydown="checkKeyDownAlphaNumeric($event)"/>
            </div>
            <button class="app__nav__loginBar__submit" @click="login()">
                Login
            </button>
        </div>

        <div class="app__nav__loginBar" v-if="isRegister && !isLoggedIn">
            <h1>Register</h1>
            <div class="app__nav__loginBar__elementContainer">
                <p class="app__nav__loginBar__elementTitle">Username</p>
                <input class="app__nav__loginBar__elementValue" type="text" v-model="user.username" @keydown="checkKeyDownAlphaNumeric($event)"/>
            </div>
            <div class="app__nav__loginBar__elementContainer">
                <p class="app__nav__loginBar__elementTitle">Password</p>
                <input class="app__nav__loginBar__elementValue" type="text" v-model="user.password" @keydown="checkKeyDownAlphaNumeric($event)"/>
            </div>
            <div class="app__nav__loginBar__elementContainer">
                <p class="app__nav__loginBar__elementTitle">Name</p>
                <input class="app__nav__loginBar__elementValue" type="text" v-model="user.name" @keydown="checkKeyDownAlphaNumeric($event)"/>
            </div>
            <button class="app__nav__loginBar__submit" @click="register()">
                Register
            </button>
        </div>

        <div class="app__nav__display">
            <div class="app__nav__profile" v-if="isLoggedIn">
                <h1>Profile</h1>
                <div class="app__nav__profile__elementContainer">
                    <label for="name" class="app__nav__profile__elementTitle">
                        Name
                    </label>
                    <input class="app__nav__profile__elementValue" id="name" name="name" v-model="user.name" @keydown="checkKeyDownAlphaNumeric($event)"/>
                </div>

                <div class="app__nav__profile__elementContainer">
                    <p class="app__nav__profile__elementTitle">
                        Member Since
                    </p>
                    <p class="app__nav__profile__elementValue">
                        {{ user.createdTime }}
                    </p>
                </div>

                <div class="app__nav__profile__elementContainer">
                    <p class="app__nav__profile__elementTitle">
                        Role
                    </p>
                    <p class="app__nav__profile__elementValue">
                        {{ user.role }}
                    </p>
                </div>

                <div class="app__nav__profile__elementContainer">
                    <p class="app__nav__profile__elementTitle">
                        Status
                    </p>
                    <p class="app__nav__profile__elementValue">
                        {{ user.status }}
                    </p>
                </div>

                <div class="app__nav__profile__elementContainer">
                    <label for="password" class="app__nav__profile__elementTitle">
                        Password
                    </label>
                    <input class="app__nav__profile__elementValue" id="password" name="password" v-model="user.password" @keydown="checkKeyDownAlphaNumeric($event)"/>
                </div>
                <button class="app__nav__profile__submit" @click="updated()">
                    Update
                </button>
                <button class="app__nav__profile__submit" @click="deleted()">
                    Delete
                </button>
            </div>

            <div v-if="showForum" class="app__nav__myForum__container">
                <h1>Forum</h1>
                <button v-if="isLoggedIn" class="app__nav__myForum__newGroupBtn" @click="isCreatingGroup=true">Create New Group</button>
                <div v-if="isLoggedIn && isCreatingGroup" class="app__nav__myForum__newGroup__form">
                    <div class="app__nav__myForum__elementContainer">
                        <label for="groupName" class="app__nav__myForum__elementTitle">
                            Group name
                        </label>
                        <input class="app__nav__myForum__elementValue" id="groupName" name="groupName" v-model="newGroup.groupName" @keydown="checkKeyDownAlphaNumeric($event)"/>
                        
                    </div>
                        <div class="app__nav__myForum__elementContainer">
                        <label for="description" class="app__nav__myForum__elementTitle">
                            Description
                        </label>
                        <input class="app__nav__myForum__elementValue" id="description" name="description" v-model="newGroup.description" />
                        
                    </div>
                    <button class="app__nav__myForum__submitBtn" @click="createNewGroup()">
                            Save Group
                    </button>
                </div>

                <div v-if="groups.length != 0" class="app__nav__myForum__GroupsContainer">
                    <h1>Groups</h1>
                    <div v-for="group in groups" :key="group.id" class="app__nav__myForum__Group">
                        <p>{{group.name}}</p>
                        <p>{{group.description}}</p>
                        <!-- Threads -->
                        <button v-if="group.owner.username===user.username">
                            Delete Group
                        </button>
                    </div>
                </div>
            </div>

            
        </div>


    </div>
</template>

<script>
export default {
    // async beforeMount(){
    //     await this.getGroups();
    // },

    async mounted() {
        this.getGroups();
        if (this.accessToken != null) {
            this.isLoggedIn = true;
            let res = await fetch('/api/user/current',{
                method: 'GET',
                headers: {
                    "Authorization": "Bearer " + this.accessToken
                }
            })
            this.user = await res.json();
        }
    },

  data() {
      return {
        showForum: false,
        isCreatingGroup: false,
        message: "",
        isAdmin: false,
        isLoggedIn: false,
        isRegister: false,
        accessToken: sessionStorage.getItem("Access_Token"),
        refreshToken: sessionStorage.getItem("Refresh_Token"),
        user: {
            name: "",
            username: "",
            password: "",
            createdTime: "",
            status: "",
            roles: "",
            groups: [],
            threads: [],
            posts: []
        },
        groups: {},
        newGroup: {}
      }
  },
  methods: {

    createNewGroup(){
        if(!this.newGroup.name){
            this.message = "Please name your group"
        }
        if(!this.newGroup.description){
            this.newGroup.description = "No description"
        }
        this.postGroup();
    },      

    async postGroup(){
        var groupDTO = {
                "name": this.newGroup.groupName,
                "description": this.newGroup.description,
            }
            let res = await fetch('/api/group/create', {
            method: 'POST',
            headers: { "Content-Type": "application/json",
            "Authorization": "Bearer " + this.accessToken },
            body: JSON.stringify(groupDTO),
            })
            var msg = await res.text();
            if(msg === "False"){
                this.message = "Name is taken, please try an other"
            }
            else{
                await this.getGroups();
                this.message = "Group created";
                this.isCreatingGroup = false;
            }
    },

    async getGroups(){
        let res = await fetch('/api/group/groups',{
                method: 'GET',
            })
            this.groups = await res.json();
            console.log(this.groups)
    },

    checkKeyDownAlphaNumeric(event) {
      if (!/[a-zA-Z0-9\s]/.test(event.key)) {
        this.ignoredValue = event.key ? event.key : "";
        event.preventDefault();
      }
    },

    async login() {
        if(this.user.username === "[Deleted]"){
            this.message = "Invalid username"
        }
        else{
            let res = await fetch('/api/login?username=' + this.user.username + '&password=' + this.user.password, {
            method: 'POST',
            })
            var msg = await res.json();
            if (!msg["msg"]) {
            sessionStorage.setItem("Access_Token", msg["access_token"])
            this.accessToken = msg["access_token"];
            sessionStorage.setItem("Refresh_Token", msg["refresh_token"])
            this.refreshToken = msg["refresh_token"];
            this.isLoggedIn = true;
            this.message = "";
            // getCurrent
                let res = await fetch('/api/user/current',{
                    method: 'GET',
                    headers: {
                        "Authorization": "Bearer " + this.accessToken
                    }
                }
                    )
                this.user = await res.json();
                if(this.user.role === "ROLE_ADMIN")
                this.isAdmin = true;
            }
            else{
                this.message = "Wrong username or password";
            }
        }
    },

    logout(){
        sessionStorage.clear();
        this.message = "user logged out";
        this.isLoggedIn = false;
        this.isAdmin = false;
        this.isRegister = false;
        this.user =  
        {
            name: "",
            username: "",
            password: "",
            createdTime: "",
            status: "",
            roles: ""
        };

    },

    async register(){
        if(!this.user.password || !this.user.username || !this.user.name){
            this.message = "Information can not be blank"
        }
        else{
            var userDTO = {
                "username": this.user.username,
                "password": this.user.password,
                "name": this.user.name
            }
            let res = await fetch('/api/user/create', {
            method: 'POST',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(userDTO),
            })
            var msg = await res.text();
            if(msg === "False"){
                this.message = "Username is taken, please try an other"
            }
            else{
                await this.login();
            }
            }
        }
    },

    async updated(){
        if(!this.user.name){
            this.message = "Please enter name"
        }
        else if(!this.user.password){
            this.message = "Please enter password"
        }
        else if(!this.user.password && !this.user.name){
            this.message = "Please enter name and password"
        }
        else{
            let res = await fetch('/api/user/update?name=' + this.user.name + '&password=' + this.user.password, {
                    method: 'PUT',
                    headers: {
                    "Authorization": "Bearer " + this.accessToken
                }
            })
            this.message = await res.text();
        }
    },

    async deleted(){
        let res = await fetch('/api/user/delete', {
                    method: 'DELETE',
                    headers: {
                    "Authorization": "Bearer " + this.accessToken
                }
            })
            if(res.status === 204){
                this.message = "user deleted";
                this.isLoggedIn = false;
                this.isAdmin = false;
                this.isSuperAdmin = false;
                sessionStorage.clear();
            }
    },

    async getCurrent(){
        let res = await fetch('/api/user/current',{
                method: 'GET',
                headers: {
                    "Authorization": "Bearer " + this.accessToken
                    }
            }
                )
        this.user = await res.json();
        if(this.user.role === "ROLE_ADMIN")
        this.isAdmin = true;
    },

    roleFormat(list){
        var newList = "";
        for (let index = 0; index < list.length; index++) {
            var formatedRoleName = list[index].split("_")[1];
            newList += (formatedRoleName + " ");
        }
        return newList;
    }
  
}
</script>

<style scoped>
</style>