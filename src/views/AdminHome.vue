<template>
  <div class="admin-shell">
    <AdminSidebar @logout="logout" @go-home="goUserHome" />
    <main class="admin-main">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import AdminSidebar from '../components/AdminSidebar.vue'

const router = useRouter()

const goUserHome = () => {
  router.push('/')
}

const logout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('userType')
  router.push('/login')
}
</script>

<style scoped>
.admin-shell {
  min-height: 100vh;
  background: var(--color-background);
  display: flex;
}

.admin-main {
  flex: 1;
  padding: 32px 40px 32px 300px;
}

@media (max-width: 960px) {
  .admin-shell {
    flex-direction: column;
  }

  .admin-main {
    padding: 24px;
  }
}
</style>
