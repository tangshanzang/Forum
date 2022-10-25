<template>
  <div class="app__admin__container">
    <div class="app__admin__container__header">
        <p v-if="message">{{message}}</p>
        <h1 class="">Admin Page</h1>
    </div>
    <div class="app__admin__container__users">
        <div class="app__admin__container__users__input">
            <label for="name" class="app__admin__container__users__input__title">
                Username
            </label>
            <input class="app__admin__container__users__input_value" id="username" name="username" v-model="username"
            @keydown="checkKeyDownAlphaNumeric($event)"/>
        </div>
        <div class="app__admin__container__users__btn">
            <button @click="deleteOtherUser()">delete</button>
            <button @click="blockUser()">block</button>
            <button @click="unBlockUser()">unblock</button>
        </div>
    </div>

  </div>
</template>

<script>
export default {
  data() {
    return {
      message: '',
      username: '',
    }
  },

  methods:{
    async deleteOtherUser(){
        let res = await fetch('/api/admin/delete?username=' + this.username, {
        method: 'DELETE',
      })
      if(res.status == 200){
          this.message = await res.text();
      }else{
          this.getAccessTokenWithRefresh();
          if(sessionStorage.getItem("access_token")){
              let res = await fetch('/api/admin/delete?username=' + this.username, {
                    method: 'DELETE',
                })
                if(res.status == 200){
                    this.message = await res.text();
                }else{
                    this.message = "You are not logged in";
                }
          }else{
              this.message = "You are not logged in";
          }
      }

    },

    async blockUser(){
        let res = await fetch('/api/admin/block?username=' + this.username, {
        method: 'PUT',
        })
      if(res.status == 200){
          this.message = await res.text();
      }else{
          this.getAccessTokenWithRefresh();
          if(sessionStorage.getItem("access_token")){
              let res = await fetch('/api/admin/block?username=' + this.username, {
                    method: 'PUT',
                })
                if(res.status == 200){
                    this.message = await res.text();
                }else{
                    this.message = "You are not logged in";
                }
          }else{
              this.message = "You are not logged in";
          }
      }
    },

    async unBlockUser(){
        let res = await fetch('/api/admin/unBlock?username=' + this.username, {
        method: 'PUT',
        })
      if(res.status == 200){
          this.message = await res.text();
      }else{
          this.getAccessTokenWithRefresh();
          if(sessionStorage.getItem("access_token")){
              let res = await fetch('/api/admin/unBlock?username=' + this.username, {
                    method: 'PUT',
                })
                if(res.status == 200){
                    this.message = await res.text();
                }else{
                    this.message = "You are not logged in";
                }
          }else{
              this.message = "You are not logged in";
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
        }
    },
  }
}
</script>

<style>

</style>