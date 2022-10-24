<script setup>
import Header from '../components/Header.vue'
import Forum from '../components/Forum.vue'
</script>

<template>
  <main>
    <Header />
    <Forum />
  </main>
</template>

<script>
export default {
  components: {
    Header,
    Forum
  }
};
</script>
