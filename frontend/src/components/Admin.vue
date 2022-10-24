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
}
</script>

<style>

</style>