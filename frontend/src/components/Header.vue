<template>
  <header>
      <div class="app__header__container">
          <p class="app__header__container__message" v-if="message">{{ message }}</p>
            <div v-if="!loggedIn" class="app__header__container__notLoggedIn">
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
              <div class="app__header__container__loggedIn__elementContainer">
                  <p class="app__header__container__loggedIn__elementContainer__title">Username</p>
                  <p class="app__header__container__loggedIn__elementContainer__value">{{username}}</p>
              </div>
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
    data() {
        return {
            message: '',
            username: '',
            password: '',
            name: '',
            loggedIn: false
        }
    },
    methods:{
        checkKeyDownAlphaNumeric(event) {
        if (!/[a-zA-Z0-9\s]/.test(event.key)) {
            this.ignoredValue = event.key ? event.key : "";
            event.preventDefault();
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
                    sessionStorage.setItem("Access_Token", token["access_token"])
                    sessionStorage.setItem("Refresh_Token", token["refresh_token"])
                    this.message = "";
                }else{
                    this.message = "Wrong username/password!";
                }
            }
        },

        async registerUser(){

        },

        async updateUser(){

        },

        async logoutUser(){

        },

        async deleteUser(){

        },
    }

}
</script>

<style>

</style>