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

    <div class="app__admin__container__groups">
        <div class="app__admin__container__groups__input">
            <label for="name" class="app__admin__container__groups__input__title">
                Group Name
            </label>
            <input class="app__admin__container__groups__input_value" id="name" name="name" v-model="groupName"
            @keydown="checkKeyDownAlphaNumeric($event)"/>
        </div>
        <div class="app__admin__container__groups__btn">
            <button @click="deleteOtherGroup()">delete</button>
            <button @click="blockGroup()">block</button>
            <button @click="unBlockGroup()">unblock</button>
        </div>
    </div>

    <div class="app__admin__container__threads">
        <div class="app__admin__container__threads__input">
            <label for="title" class="app__admin__container__threads__input__title">
                Thread Title
            </label>
            <input class="app__admin__container__threads__input_value" id="title" name="title" v-model="threadTitle"
            @keydown="checkKeyDownAlphaNumeric($event)"/>
        </div>
        <div class="app__admin__container__threads__btn">
            <button @click="deleteOtherThread()">delete</button>
            <button @click="blockThread()">block</button>
            <button @click="unBlockThread()">unblock</button>
        </div>
    </div>

        <div class="app__admin__container__posts">
        <div class="app__admin__container__posts__input">
            <label for="postId" class="app__admin__container__posts__input__title">
                Post ID
            </label>
            <input class="app__admin__container__posts__input_value" id="postId" name="postId" v-model="postId"
            type="number" @input="postId = Math.round(postId)"
            @keydown="checkKeyDownAlphaNumeric($event)"/>
        </div>
        <div class="app__admin__container__posts__btn">
            <button @click="deleteOtherPost()">delete</button>
            <button @click="blockPost()">block</button>
            <button @click="unBlockPost()">unblock</button>
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
      groupName: '',
      threadTitle: '',
      postId: null
    }
  },

  methods:{
    checkKeyDownAlphaNumeric(event) {
        if (!/[a-zA-Z0-9\s]/.test(event.key)) {
            this.ignoredValue = event.key ? event.key : "";
            event.preventDefault();
        }
    },

    async deleteOtherUser(){
        let res = await fetch('/api/admin/deleteUser?username=' + this.username, {
        method: 'DELETE',
        headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
      })
      if(res.status == 200){
          this.message = await res.text();
      }else{
          this.getAccessTokenWithRefresh();
          if(sessionStorage.getItem("access_token")){
              let res = await fetch('/api/admin/deleteUser?username=' + this.username, {
                    method: 'DELETE',
                    headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
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
        let res = await fetch('/api/admin/blockUser?username=' + this.username, {
        method: 'PUT',
        headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
        })
      if(res.status == 200){
          this.message = await res.text();
      }else{
          this.getAccessTokenWithRefresh();
          if(sessionStorage.getItem("access_token")){
              let res = await fetch('/api/admin/blockUser?username=' + this.username, {
                    method: 'PUT',
                    headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
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
        let res = await fetch('/api/admin/unBlockUser?username=' + this.username, {
        method: 'PUT',
        headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
        })
      if(res.status == 200){
          this.message = await res.text();
      }else{
          this.getAccessTokenWithRefresh();
          if(sessionStorage.getItem("access_token")){
              let res = await fetch('/api/admin/unBlockUser?username=' + this.username, {
                    method: 'PUT',
                    headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
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

    async deleteOtherGroup(){
        let res = await fetch('/api/admin/deleteGroup?name=' + this.groupName, {
        method: 'DELETE',
        headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
      })
      if(res.status == 200){
          this.message = await res.text();
      }else{
          this.getAccessTokenWithRefresh();
          if(sessionStorage.getItem("access_token")){
              let res = await fetch('/api/admin/deleteGroup?name=' + this.groupName, {
                    method: 'DELETE',
                    headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
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

    async blockGroup(){
        let res = await fetch('/api/admin/blockGroup?name=' + this.groupName, {
        method: 'PUT',
        headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
      })
      if(res.status == 200){
          this.message = await res.text();
      }else{
          this.getAccessTokenWithRefresh();
          if(sessionStorage.getItem("access_token")){
              let res = await fetch('/api/admin/blockGroup?name=' + this.groupName, {
                    method: 'PUT',
                    headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
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

    async unBlockGroup(){
        let res = await fetch('/api/admin/unBlockGroup?name=' + this.groupName, {
        method: 'PUT',
        headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
      })
      if(res.status == 200){
          this.message = await res.text();
      }else{
          this.getAccessTokenWithRefresh();
          if(sessionStorage.getItem("access_token")){
              let res = await fetch('/api/admin/unBlockGroup?name=' + this.groupName, {
                    method: 'PUT',
                    headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
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

    async deleteOtherThread(){
        let res = await fetch('/api/admin/deleteThread?title=' + this.threadTitle, {
        method: 'DELETE',
        headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
      })
      if(res.status == 200){
          this.message = await res.text();
      }else{
          this.getAccessTokenWithRefresh();
          if(sessionStorage.getItem("access_token")){
              let res = await fetch('/api/admin/deleteThread?title=' + this.threadTitle, {
                    method: 'DELETE',
                    headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
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

    async blockThread(){
        let res = await fetch('/api/admin/blockThread?title=' + this.threadTitle, {
        method: 'PUT',
        headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
      })
      if(res.status == 200){
          this.message = await res.text();
      }else{
          this.getAccessTokenWithRefresh();
          if(sessionStorage.getItem("access_token")){
              let res = await fetch('/api/admin/blockThread?title=' + this.threadTitle, {
                    method: 'PUT',
                    headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
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

    async unBlockThread(){
        let res = await fetch('/api/admin/unBlockThread?title=' + this.threadTitle, {
        method: 'PUT',
        headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
      })
      if(res.status == 200){
          this.message = await res.text();
      }else{
          this.getAccessTokenWithRefresh();
          if(sessionStorage.getItem("access_token")){
              let res = await fetch('/api/admin/unBlockThread?title=' + this.threadTitle, {
                    method: 'PUT',
                    headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
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

    async deleteOtherPost(){
        let res = await fetch('/api/admin/deletePost?id=' + this.postId, {
        method: 'DELETE',
        headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
      })
      if(res.status == 200){
          this.message = await res.text();
      }else{
          this.getAccessTokenWithRefresh();
          if(sessionStorage.getItem("access_token")){
              let res = await fetch('/api/admin/deletePost?id=' + this.postId, {
                    method: 'DELETE',
                    headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
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

    async blockThread(){
        let res = await fetch('/api/admin/blockPost?id=' + this.postId, {
        method: 'PUT',
        headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
      })
      if(res.status == 200){
          this.message = await res.text();
      }else{
          this.getAccessTokenWithRefresh();
          if(sessionStorage.getItem("access_token")){
              let res = await fetch('/api/admin/blockPost?id=' + this.postId, {
                    method: 'PUT',
                    headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
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

    async unBlockThread(){
        let res = await fetch('/api/admin/unBlockPost?id=' + this.postId, {
        method: 'PUT',
        headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
      })
      if(res.status == 200){
          this.message = await res.text();
      }else{
          this.getAccessTokenWithRefresh();
          if(sessionStorage.getItem("access_token")){
              let res = await fetch('/api/admin/unBlockPost?id=' + this.postId, {
                    method: 'PUT',
                    headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
            }
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


  }
}
</script>

<style>

</style>