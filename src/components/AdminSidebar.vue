<template>
  <aside class="admin-sidebar">
    <div class="admin-brand">
      <div class="brand-avatar">
        <img
          alt="管理员头像"
          src="https://lh3.googleusercontent.com/aida-public/AB6AXuAbg5MgR4EdYH8re-OJSvxXcD6bh-8--s1utYXDnfjlDbthC2sxF6BIJyeGQM-y0JTvZOwdzPpLnmu3XxggD5ntsarjty6D3KXT3zL_d5MsIBLdCYlJAEpa3TUsQVgmqYWSGjGJw5aoESJYzKZzThHU1eEh-eRECLllb3vZHw6Mnhc6J3lWBtEO4I_0YewG4Lw77CQ3t8YoeOu-knNei4BHqyPjMuYAWy_i2vhE-Vdcco5clC9XyrA7Pbfcg751KEADhlI5WA2Jrjo"
        />
      </div>
      <div>
        <h1 class="brand-title">基于协同过滤的智能音乐推荐系统·管理台</h1>
        <p class="brand-subtitle">System Admin</p>
      </div>
    </div>

    <nav class="admin-nav">
      <router-link class="admin-link" to="/admin/dashboard">
        <LayoutDashboard class="nav-icon" />
        控制台
      </router-link>
      <router-link class="admin-link" to="/admin/users">
        <Users class="nav-icon" />
        用户管理
      </router-link>
      <router-link class="admin-link" to="/admin/library">
        <Music class="nav-icon" />
        音乐库
      </router-link>
      <router-link class="admin-link" to="/admin/monitor">
        <Monitor class="nav-icon" />
        引擎监控
      </router-link>

    </nav>

    <div class="admin-footer">
      <button class="logout-btn" @click="emit('logout')">
        <LogOut class="nav-icon" />
        退出登录
      </button>
    </div>
  </aside>
</template>

<script setup lang="ts">
import { LayoutDashboard, LogOut, Monitor, Music, Users } from 'lucide-vue-next'

const emit = defineEmits<{
  (e: 'logout'): void
  (e: 'go-home'): void
}>()
</script>

<style scoped>
.admin-sidebar {
  position: fixed;
  left: 0;
  top: 0;
  height: 100vh;
  width: 250px;
  display: flex;
  flex-direction: column;
  padding: 20px;
  background: rgba(255, 255, 255, 0.85);
  border-right: 1px solid rgba(109, 123, 109, 0.2);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  z-index: 50;
  box-shadow: 6px 0 24px rgba(0, 109, 54, 0.05);
}

.admin-brand {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 8px 6px 18px;
}

.brand-avatar {
  width: 44px;
  height: 44px;
  border-radius: 50%;
  overflow: hidden;
  border: 1px solid rgba(109, 123, 109, 0.2);
  background: var(--color-surface-container-highest);
}

.brand-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.brand-title {
  font-size: 16px;
  font-weight: 700;
  margin: 0;
  color: var(--color-primary);
}

.brand-subtitle {
  font-size: 10px;
  text-transform: uppercase;
  letter-spacing: 0.2em;
  color: rgba(25, 28, 29, 0.5);
  margin: 4px 0 0;
}

.admin-nav {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-top: 14px;
  flex: 1;
}

.admin-link {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px 16px;
  border-radius: 14px;
  font-weight: 600;
  color: rgba(25, 28, 29, 0.7);
  transition: all 0.2s ease;
}

.admin-link.router-link-active {
  background: rgba(0, 109, 54, 0.15);
  color: var(--color-primary);
  box-shadow: 0 6px 16px rgba(0, 109, 54, 0.08);
}

.admin-link:hover {
  background: rgba(0, 109, 54, 0.08);
  color: var(--color-primary);
}


.admin-footer {
  border-top: 1px solid rgba(109, 123, 109, 0.2);
  padding-top: 16px;
}

.logout-btn {
  width: 100%;
  padding: 12px 16px;
  border-radius: 14px;
  border: none;
  background: var(--color-surface-container-highest);
  color: rgba(25, 28, 29, 0.7);
  font-weight: 700;
  cursor: pointer;
  transition: all 0.2s ease;
  display: flex;
  align-items: center;
  gap: 10px;
  justify-content: center;
}

.logout-btn:hover {
  background: rgba(186, 26, 26, 0.12);
  color: var(--color-error);
}

.nav-icon {
  width: 18px;
  height: 18px;
}

@media (max-width: 960px) {
  .admin-sidebar {
    position: static;
    width: 100%;
    height: auto;
    box-shadow: none;
  }
}
</style>
