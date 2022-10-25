<template>
  <header>
      <div class="app__header__container">
            <div class="app__nav__container">
                <a @click="navigate('/')">Home</a>
                <a v-if="isAdmin" @click="navigate('/admin')">Admin</a>
            </div>
          <p class="app__header__container__message" v-if="message">{{ message }}</p>
            <div v-if="!loggedIn" class="app__header__container__notLoggedIn">
                <h2>Welcome, Visitor</h2>
                <div class="app__header__container__notLoggedIn__elementContainer">
                    <label for="username" class="app__header__container__notLoggedIn__elementContainer__title">
                        Username
                    </label>
                    <input class="app__header__container__notLoggedIn__elementContainer__value" id="username" name="username" v-model="username" @keydown="checkKeyDownAlphaNumeric($event)"/>
                </div>
                <div class="app__header__container__notLoggedIn__elementContainer">
                    <label for="password" class="app__header__container__notLoggedIn__elementContainer__title">
                        Password
                    </label>
                    <input class="app__header__container__notLoggedIn__elementContainer__value" id="password" name="password" v-model="password" @keydown="checkKeyDownAlphaNumeric($event)"/>
                </div>
                <div class="app__header__container__notLoggedIn__elementContainer">
                    <label for="name" class="app__header__container__notLoggedIn__elementContainer__title">
                        Name
                    </label>
                    <input class="app__header__container__notLoggedIn__elementContainer__value" id="name" name="name" v-model="name" @keydown="checkKeyDownAlphaNumeric($event)"/>
                </div>
                <div class="app__header__container__notLoggedIn__elementContainer">
                    <button @click="loginUser()">login</button>
                    <button @click="registerUser()">register</button>
                </div>

            </div>

          <div v-if="loggedIn" class="app__header__container__loggedIn">
              <h2>You Are Now Logged In</h2>
                <div class="app__header__container__loggedIn__elementContainer">
                    <label for="password" class="app__header__container__loggedIn__elementContainer__title">
                        Password
                    </label>
                    <input class="app__header__container__loggedIn__elementContainer__value" id="password" name="password" v-model="password" @keydown="checkKeyDownAlphaNumeric($event)"/>
                </div>
                <div class="app__header__container__loggedIn__elementContainer">
                    <label for="name" class="app__header__container__loggedIn__elementContainer__title">
                        Name
                    </label>
                    <input class="app__header__container__loggedIn__elementContainer__value" id="name" name="name" v-model="name" @keydown="checkKeyDownAlphaNumeric($event)"/>
                </div>
                <div class="app__header__container__loggedIn__elementContainer">
                    <button @click="updateUser()">update</button>
                    <button @click="logoutUser()">logout</button>
                    <button @click="deleteUser()">delete</button>
                </div>
          </div>

      </div>
  </header>
</template>

<script>
export default {
    async beforeCreate() {

        if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
            
            let res = await fetch('/api/user/current',{
                    method: 'GET',
                    headers: {
                        "Authorization": "Bearer " + sessionStorage.getItem("access_token")
                        }
                    }
                )
                if(res.status === 200){
                    var user = await res.json();
                    this.name = user.name;
                    this.loggedIn = true;
                    if(user.role == "ROLE_ADMIN"){
                        this.isAdmin = true;
                    }else{
                        this.isAdmin = false;
                    }
                    }
                else{
                    this.getAccessTokenWithRefresh();
                    if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){  
                        this.getCurrent();
                    }
                }
        }
    },

    data() {
        return {
            message: '',
            username: '',
            password: '',
            name: '',
            loggedIn: false,
            isAdmin: false
        }
    },
    methods:{
        checkKeyDownAlphaNumeric(event) {
        if (!/[a-zA-Z0-9\s]/.test(event.key)) {
            this.ignoredValue = event.key ? event.key : "";
            event.preventDefault();
            }
        },

        async getCurrent(){
            let res = await fetch('/api/user/current',{
                    method: 'GET',
                    headers: {
                        "Authorization": "Bearer " + sessionStorage.getItem("access_token")
                        }
                    }
                )
                if(res.status === 200){
                    var user = await res.json();
                    this.name = user.name;
                    if(user.role == "ROLE_ADMIN"){
                        this.isAdmin = true;
                    }
                    if(user.status != "active"){
                        this.logoutUser();
                    }
                    }
                else{
                    if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){  
                        this.getAccessTokenWithRefresh();
                        if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){  
                            let res = await fetch('/api/user/current',{
                                method: 'GET',
                                headers: {
                                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
                                    }
                                }
                            )
                            if(res.status === 200){
                                var user = await res.json();
                                this.name = user.name;
                                }
                            // else{
                            //     this.message = "Please login again, token has expired"
                            //     this.logoutUser();
                            //     sessionStorage.clear();
                            // }

                    }
                    }
                    
                }
        },

        async loginUser(){
            if(!this.username || !this.password){
                this.message = "Fill username and password"
            }else{
                let res = await fetch('/api/login?username=' + this.username + '&password=' + this.password, {
                method: 'POST',
                })
                    var token = await res.json();
                if (!token["msg"]) {
                    sessionStorage.setItem("access_token", token["access_token"])
                    sessionStorage.setItem("refresh_token", token["refresh_token"])
                    this.message = "";
                    this.loggedIn = true;
                    this.getCurrent();
                }else{
                    this.message = "Wrong username/password!";
                }
            }
        },

        navigate(e) {
            this.$router.push(e);
        },

        async registerUser(){
            if(!this.username || !this.password || !this.name){
                this.message = "Fill username, password and name"
            }else{
                var userDTO = {
                    "username": this.username,
                    "password": this.password,
                    "name": this.name
                }

                let res = await fetch('/api/user/create', {
                    method: 'POST',
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(userDTO),
                })
                var msg = await res.text();
                if(msg === "false"){
                    this.message = "Username is taken"
                }else{
                    this.loginUser();
                }
            }
        },

        async updateUser(){
            if(!this.password || !this.name){
                this.message = "Fill name and password";
            }else{
                let res = await fetch('/api/user/update?name=' + this.name + '&password=' + this.password, {
                    method: 'PUT',
                    headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
                    }
                })
                var msg = await res.text();
                if(msg === "true"){
                    this.message = "Your info have updated, try log in again with new info";
                    this.logoutUser();
                }else{
                    this.getAccessTokenWithRefresh();
                    if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
                        this.updateUser();
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
            }
        },

        logoutUser(){
            sessionStorage.clear();
            this.loggedIn = false;
            this.username = "";
            this.password = "";
            this.name = "";
            this.isAdmin = false;
        },

        async deleteUser(){
            let res = await fetch('/api/user/delete', {
                    method: 'DELETE',
                    headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
                    }
                })
                var msg = await res.text();
                if(msg === "true"){
                    this.message = "Your user have been deleted, hope we will see you again soon";
                    this.logoutUser();
                }else{
                    this.getAccessTokenWithRefresh();
                    if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
                        this.deleteUser();
                    }
                }
        },
    }

}
</script>

<style>

</style>