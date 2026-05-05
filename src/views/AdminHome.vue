<template>
  <div class="admin-page">
    <div class="admin-card">
      <h1>管理员主页</h1>
      <p>欢迎进入管理端，你可以在这里进行后台管理。</p>
      <div class="actions">
        <button class="primary" @click="goUserHome">进入用户首页</button>
        <button class="danger" @click="logout">退出登录</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const goUserHome = () => {
  router.push('/');
};

const logout = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('userType');
  router.push('/login');
};
</script>

<style scoped>
.admin-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #f5f7f9 0%, #e4e9f0 100%);
}

.admin-card {
  width: min(92vw, 520px);
  background: #fff;
  border-radius: 18px;
  padding: 32px;
  box-shadow: 0 14px 36px rgba(0, 0, 0, 0.08);
}

h1 {
  margin: 0 0 12px;
  color: #1f2937;
}

p {
  margin: 0 0 24px;
  color: #64748b;
}

.actions {
  display: flex;
  gap: 12px;
}

button {
  border: none;
  border-radius: 10px;
  padding: 11px 16px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
}

.primary {
  background: #1db954;
  color: #fff;
}

.danger {
  background: #ef4444;
  color: #fff;
}
</style>
