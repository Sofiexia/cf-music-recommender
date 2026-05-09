<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-header">
        <h2>欢迎回来</h2>
        <p class="subtitle">登录 基于协同过滤的智能音乐推荐系统 发现更多好音乐</p>
      </div>

      <div class="form-body">
        <div class="form-group">
          <label>用户类型</label>
          <div class="user-type-selector">
            <button
              v-for="option in userTypeOptions"
              :key="option.value"
              type="button"
              class="type-btn"
              :class="{ active: loginForm.userType === option.value }"
              @click="loginForm.userType = option.value"
            >
              {{ option.label }}
            </button>
          </div>
        </div>

        <div class="form-group">
          <label>用户名</label>
          <div class="input-wrapper">
            <input 
              v-model.trim="loginForm.username" 
              type="text" 
              placeholder="请输入用户名" 
              @keyup.enter="handleLogin"
              :class="{ 'error-border': loginError }"
            />
          </div>
        </div>

        <div class="form-group">
          <label>密码</label>
          <div class="input-wrapper">
            <input 
              v-model="loginForm.password" 
              type="password" 
              placeholder="请输入密码" 
              @keyup.enter="handleLogin"
              :class="{ 'error-border': loginError }"
            />
          </div>
          <span class="error-msg" v-if="loginError">{{ loginError }}</span>
        </div>

        <button 
          class="login-btn" 
          @click="handleLogin" 
          :disabled="loading"
        >
          <span v-if="loading" class="loader"></span>
          {{ loading ? '验证中...' : '登录' }}
        </button>

        <div class="footer-links">
          <span>还没有账号？</span>
          <router-link to="/register">立即注册</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { login } from '../api/auth';

const router = useRouter();
const route = useRoute();
const loading = ref(false);
const loginError = ref('');
const userTypeOptions = [
  { label: '普通用户', value: 0 },
  { label: '管理员', value: 1 }
];

const loginForm = reactive({
  username: '',
  password: '',
  userType: 0
});

const handleLogin = async () => {
  if (!loginForm.username || !loginForm.password) {
    loginError.value = '用户名或密码不能为空';
    return;
  }

  loading.value = true;
  loginError.value = '';
  try {
    const res = await login(loginForm);
    const result = res?.data || {};
    if (result.code === 200) {
      const responseData = result.data;
      const token =
        typeof responseData === 'string'
          ? responseData
          : responseData?.token || responseData?.accessToken || '';

      if (!token) {
        loginError.value = '登录响应异常，请稍后重试';
        return;
      }

      const serverUserType = responseData?.userType ?? responseData?.role ?? responseData?.userRole;
      const normalizedUserType = serverUserType ?? loginForm.userType;
      const isAdmin =
        normalizedUserType === 1 ||
        normalizedUserType === '1' ||
        String(normalizedUserType).toLowerCase() === 'admin';
      const userType = isAdmin ? '1' : '0';
      localStorage.setItem('token', token);
      localStorage.setItem('userType', userType);

      const redirectPath = typeof route.query.redirect === 'string' ? route.query.redirect : '';
      if (redirectPath && redirectPath !== '/login') {
        router.push(redirectPath);
      } else {
        router.push(userType === '1' ? '/admin' : '/');
      }
    } else {
      loginError.value = result.message || '登录失败，请检查账号信息';
    }
  } catch (err) {
    console.error('登录请求异常:', err);
    loginError.value = '无法连接服务器，请稍后再试';
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7f9 0%, #e4e9f0 100%);
}

.login-card {
  background: white;
  padding: 40px;
  border-radius: 20px;
  box-shadow: 0 15px 35px rgba(0,0,0,0.05);
  width: 100%;
  max-width: 400px;
}

.login-header {
  text-align: center;
  margin-bottom: 32px;
}

h2 { color: #2c3e50; font-size: 24px; margin-bottom: 8px; }
.subtitle { color: #94a3b8; font-size: 14px; }

.form-group { margin-bottom: 24px; position: relative; }
.form-group label { display: block; margin-bottom: 8px; font-size: 13px; color: #64748b; font-weight: 600; }

.user-type-selector {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 10px;
}

.type-btn {
  padding: 10px 0;
  border: 1px solid #dbe3ef;
  border-radius: 10px;
  background: #f8fafc;
  color: #64748b;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
}

.type-btn.active {
  border-color: #1db954;
  color: #1db954;
  background: rgba(29, 185, 84, 0.08);
}

input {
  width: 100%;
  padding: 12px 16px;
  border: 1px solid #e2e8f0;
  border-radius: 10px;
  box-sizing: border-box;
  font-size: 15px;
  transition: all 0.2s ease;
  background-color: #f8fafc;
}

input:focus {
  outline: none;
  border-color: #1db954;
  background-color: #fff;
  box-shadow: 0 0 0 3px rgba(29, 185, 84, 0.1);
}

.error-border { border-color: #ef4444 !important; }
.error-msg { color: #ef4444; font-size: 12px; margin-top: 6px; position: absolute; }

.login-btn {
  width: 100%;
  padding: 14px;
  background: #1db954;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-size: 16px;
  font-weight: 600;
  margin-top: 8px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
  transition: background 0.2s;
}

.login-btn:hover { background: #1aa34a; }
.login-btn:disabled { background: #a5d6a7; cursor: not-allowed; }

.loader {
  width: 18px; height: 18px;
  border: 2px solid #ffffff;
  border-bottom-color: transparent;
  border-radius: 50%;
  animation: rotation 0.8s linear infinite;
}
@keyframes rotation { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }

.footer-links { text-align: center; margin-top: 24px; font-size: 14px; color: #64748b; }
.footer-links a { color: #1db954; text-decoration: none; font-weight: 600; margin-left: 4px; }
.footer-links a:hover { text-decoration: underline; }
</style>
