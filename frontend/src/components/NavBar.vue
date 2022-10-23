<template>
    <div class="app__nav__container">
        <div class="app__nav__bar">
            <router-link :to="'/'">Home</router-link>
            <router-link v-if="isAdmin" :to="'/admin'">admin</router-link>
            <p v-if="!isLoggedIn" @click="isRegister=false">Login</p>
            <p v-else @click="isRegister=false">Logout</p>
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
                        Roles
                    </p>
                    <p class="app__nav__profile__elementValue">
                        {{ roleFormat(user.roles) }}
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
                <button class="app__nav__profile__submit" @click="update()">
                    Update
                </button>
                <button class="app__nav__profile__submit" @click="deleted()">
                    Delete
                </button>
            </div>
        </div>


    </div>
</template>

<script>
export default {
    async mounted() {
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
        message: "",
        isAdmin: false,
        isSuperAdmin: false,
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
            roles: ""
        },
      }
  },
  methods: {
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
            this.getCurrent();
            }
            else{
                this.message = "Wrong username or password";
            }
        }
    },

    async update(){
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
        for (let index = 0; index < this.user.roles.length; index++) {
            if(this.user.roles[index] === "ROLES_ADMIN"){
                this.isAdmin = true;
            }
            else if(this.user.roles[index] === "ROLES_SUPERADMIN"){
                this.isSuperAdmin = true;
            }
        }
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
}
</script>

<style scoped>
</style>