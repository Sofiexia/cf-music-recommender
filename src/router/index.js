import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Home from '../views/Home.vue'

const routes = [
  { path: '/', component: Home },
  { path: '/login', component: Login },
  { path: '/register', component: Register }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');
  // 如果访问首页且没有 token，跳转到登录
  if (to.path === '/' && !token) {
    next('/login');
  } 
  if (to.path === '/login' && token) {
    return '/'
  }
  next();
});

export default router