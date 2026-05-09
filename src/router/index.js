import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Home from '../views/Home.vue'
import AdminHome from '../views/AdminHome.vue'
import Dashboard from '../views/Dashboard.vue'
import UserManagement from '../views/UserManagement.vue'
import EngineMonitor from '../views/EngineMonitor.vue'
import MusicLibrary from '../views/MusicLibrary.vue'

const routes = [
  { path: '/', component: Home },
  {
    path: '/admin',
    component: AdminHome,
    children: [
      { path: '', redirect: '/admin/dashboard' },
      { path: 'dashboard', component: Dashboard },
      { path: 'users', component: UserManagement },
      { path: 'library', component: MusicLibrary },
      { path: 'monitor', component: EngineMonitor }
    ]
  },
  { path: '/login', component: Login },
  { path: '/register', component: Register }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const userType = localStorage.getItem('userType') || '0'
  const isAdmin = userType === '1' || userType.toLowerCase() === 'admin'
  const isPublicPath = to.path === '/login' || to.path === '/register'

  if (!token && !isPublicPath) {
    next({
      path: '/login',
      query: { redirect: to.fullPath }
    })
    return
  }

  if (token && to.path === '/login') {
    next(isAdmin ? '/admin/dashboard' : '/')
    return
  }

  if (to.path.startsWith('/admin') && !isAdmin) {
    next('/')
    return
  }

  next()
})

export default router
