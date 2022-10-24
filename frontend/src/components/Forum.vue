<script setup>
import Group from '../components/Group.vue'
</script>

<template>
    <div class="app__forum__cointainer">
        <h1 class="app__forum__title">Java Forum</h1>
        <Group />
    </div>
</template>

<script>
export default {
  components: {
    Group,
  }
};
</script>
