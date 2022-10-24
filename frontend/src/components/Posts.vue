<template>
  <div class="app__group__container">
    <div class="app__group__container__header">
        <p v-if="message">{{message}}</p>
        <h1 class="">Post</h1>
        <button @click="handleClick('create', '')">Create Post</button>
    </div>
    <div v-if="isTryingToUpdate||isTryingToCreate" class="app__group__container__content">
        <div v-if="!isTryingToCreate" class="app__group__container__content__elementContainer">
            <label for="name" class="app__group__container__content__elementContainer__elementTitle">
                Post ID
            </label>
            <p class="app__group__container__content__elementContainer__elementValue" id="name" name="name">
                {{newPost.id}}
            </p>
        </div>

        <div class="app__group__container__content__elementContainer">
            <label for="description" class="app__group__container__content__elementContainer__elementTitle">
                Post content
            </label>
            <input class="app__group__container__content__elementContainer__elementValue" id="description" name="description" v-model="newPost.content"
            @keydown="checkKeyDownAlphaNumeric($event)"/>
        </div>

        <button @click="handleSave()" class="app__group__container__content__button">
            {{isTryingToUpdate ? "Update" : "Create"}}
        </button>
    </div>
    <div class="app__group__container__content2">
        <div v-for="post in posts" :key="post.id" class="app__group__container__content2__groupContainer">
            <div class="app__group__container__content2__groupContainer__group">
                <p class="">{{ post.id }}</p>
                <p class="">{{ post.content }}</p>
                <button @click="handleClick('update', post)">Update Post</button>
                <button @click="deletePost(post)">Delete Post</button>
            </div>
        </div>
    </div>
  </div>
</template>

<script>
export default {
    async beforeCreate() {

        let res = await fetch('/api/post/postsOfThread?threadId=' + this.$route.params.id,{
                method: 'GET'
            })
        this.posts = await res.json();
    },
    data() {
        return {
            message: '',
            isLoggedIn: sessionStorage.getItem("access_token") ? true:false,
            isTryingToUpdate: false,
            isTryingToCreate: false,
            name: '',
            posts: [],
            newPost: {
                id: "",
                content: ""
            },
        }
    },
    methods:{
        checkKeyDownAlphaNumeric(event) {
            if (!/[a-zA-Z0-9\s]/.test(event.key)) {
                this.ignoredValue = event.key ? event.key : "";
                event.preventDefault();
            }
        },

        handleClick(e, post){
            if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
                if(e === "update"){
                    if(post.content == "[Deleted Post]"){
                        this.message = "This post was already deleted in DB, can not be updated"
                        this.isTryingToUpdate = false;
                        this.isTryingToCreate = false;
                    }
                    else{
                        this.isTryingToUpdate = true;
                        this.isTryingToCreate = false;
                        this.newPost.content = post.content;
                        this.newPost.id = post.id;
                        this.message = "";
                    }
            
                }else{
                    this.isTryingToUpdate = false;
                    this.isTryingToCreate = true;
                    this.newPost.content = post.content;
                    this.newPost.id = post.id;
            }}else{
                this.message = "You need to log in first"
            }
        },

        handleSave(){
            if(this.isTryingToCreate){
                this.createPost();
            }else{
                this.updatePost();
            }
        },

        async createPost() {
            if(!this.newPost.content){
                this.message = "Please fill post content"
            }else if(this.newPost.content == "[Deleted Post]"){
                this.message = "Invalid post content"
            }
            else{

                let res = await fetch('/api/post/create?threadId=' + this.$route.params.id + '&content=' + this.newPost.content, {
                    method: 'POST',
                    headers: {
                        "Authorization": "Bearer " + sessionStorage.getItem("access_token")
                    },
                })
                var msg = await res.text();
    
                // if(msg == "Post Has Been Created"){
                //     this.message = msg;
                //     this.isTryingToUpdate = false;
                //     this.isTryingToCreate = false;
                //     this.posts = await this.fetchPosts();
                // }else if(msg == "Post Title Is Taken" || msg == "User Is Blocked" || msg == "Thread Is Blocked"){
                //     this.message = msg;
                // }
                if(res.status == 200){
                    this.message = msg;
                    this.isTryingToUpdate = false;
                    this.isTryingToCreate = false;
                    this.posts = await this.fetchPosts();
                }
                else if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
                    this.getAccessTokenWithRefresh();
                    // double if cause the above method might force logout
                    if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
                        this.createPost();
                    }
                }
            }
        },

        async updatePost() {
            if(!this.newPost.content){
                this.message = "Please fill all post info"
            }else if(this.newPost.content == "[Deleted Post]"){
                this.message = "Invalid post content"
            }else{
                var postDTO = {
                    "id": this.newPost.id,
                    "content": this.newPost.content
                }
                let res = await fetch('/api/post/update', {
                    method: 'PUT',
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": "Bearer " + sessionStorage.getItem("access_token")
                    },
                    body: JSON.stringify(postDTO)
                })
    
                var msg = await res.text();
                if(msg == "Post Has Been Updated"){
                    this.message = msg;
                    this.posts = await this.fetchPosts();
                }else if(msg == "Post Title Is Taken" || msg == "User Is Blocked"
                 || msg == "Post Is Blocked" || msg == "Post Does Not Exist"
                 || msg == "You Can't Update Other's Post"){
                    this.message = msg;
                }else if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
                    this.getAccessTokenWithRefresh();
                    // double if cause the above method might force logout
                    if(sessionStorage.getItem("access_token") || sessionStorage.getItem("refresh_token")){
                        let res = await fetch('/api/post/update', {
                            method: 'PUT',
                            headers: {
                                "Content-Type": "application/json",
                                "Authorization": "Bearer " + sessionStorage.getItem("access_token")
                            },
                            body: JSON.stringify(postDTO)
                        })
            
                        var msg = await res.text();
                        if(msg == "Post Has Been Updated"){
                            this.message = msg;
                            this.posts = await this.fetchPosts();
                        }else{
                            this.message = msg;
                        }
                    }

                }
            }
        },

        async deletePost(post){
                                    this.isTryingToUpdate = false;
                        this.isTryingToCreate = false;
            if(post.content == "[Deleted Post]"){
                this.message = "This post was already deleted in DB, can not be deleted again"
            }
            else{
                var postDTO = {
                    "id": post.id,
                    "content": post.content
                }

                let res = await fetch('/api/post/delete', {
                        method: 'DELETE',
                        headers: {
                            "Content-Type": "application/json",
                            "Authorization": "Bearer " + sessionStorage.getItem("access_token")
                        },
                        body: JSON.stringify(postDTO),
                    })
        
                    var msg = await res.text();
                    // if(msg == "Post Has Been Deleted"){
                    //     this.message = msg;
                    //     this.posts = await this.fetchPosts();
                    // }else if(msg == "User Is Blocked"
                    //  || msg == "Thread Is Blocked" || msg == "Post Does Not Exist"
                    //  || msg == "You Can't Delete Other's Posts"){
                    //     this.message = msg;
                    // }
                    if(res.status == 200){
                        this.message = msg;
                        this.posts = await this.fetchPosts();
                    }
                    else if(sessionStorage.getItem("refresh_token")){
                        this.getAccessTokenWithRefresh();
                        // double if cause the above method might force logout
                        if(sessionStorage.getItem("access_token")){
                            let res = await fetch('/api/post/delete', {
                                method: 'DELETE',
                                headers: {
                                    "Content-Type": "application/json",
                                    "Authorization": "Bearer " + sessionStorage.getItem("access_token")
                                },
                                body: JSON.stringify(postDTO),
                            })
                
                            var msg = await res.text();
                            if(res.status == 200){
                                this.message = msg;
                                this.posts = await this.fetchPosts();
                            }
                        }else{
                        this.message = "You must log in first"}
    
                    }else{
                        this.message = "You must log in first"
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

        async fetchPosts() {
            let res = await fetch('/api/post/postsOfThread?threadId=' + this.$route.params.id,{
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