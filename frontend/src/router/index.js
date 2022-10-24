import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import GroupDetailView from '../views/GroupDetailView.vue'
import ThreadDetailView from '../views/ThreadDetailView.vue'
import AdminView from '../views/AdminView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/group/:id?',
      name: 'groupDetail',
      component: GroupDetailView
    },
    {
      path: '/thread/:id?',
      name: 'threadDetail',
      component: ThreadDetailView
    },
    {
      path: '/admin',
      name: 'adminPage',
      component: AdminView
    },
  ]
})

export default router
