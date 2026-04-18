<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-header">
        <h2>欢迎回来</h2>
        <p class="subtitle">登录 拾音集 发现更多好音乐</p>
      </div>

      <div class="form-body">
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
import { useRouter } from 'vue-router';
import request from '../api/request';
import { loginApi } from '../stores/user';

const router = useRouter();
const loading = ref(false);
const loginError = ref(''); // 专门处理登录失败的提示

const loginForm = reactive({
  username: '',
  password: ''
});

const handleLogin = async () => {
  // 1. 前端基础校验
  if (!loginForm.username || !loginForm.password) {
    loginError.value = '用户名或密码不能为空';
    return;
  }

  loading.value = true;
  loginError.value = '';
  const res = await loginApi(loginForm)
  if (res.code === 200){
    localStorage.setItem('token', res.data.token)
    const redirectPath = route.query.redirect || '/'
    router.push(redirectPath)
  }

  try {
    // 2. 对接后端 AuthController: @PostMapping("/login")
    const res = await request.post('/auth/login', loginForm);
    
    // 后端封装的 Result 对象
    if (res.data && res.data.code === 200) {
      // 保存后端返回的 JWT Token
      localStorage.setItem('token', res.data.data);
      
      // 成功提示并跳转
      console.log("登录成功，正在跳转...");
      router.push('/'); 
    } else {
      // 这里的 res.data.message 可能是 "用户名或密码错误"
      loginError.value = res.data.message || '登录失败，请检查账号信息';
    }
  } catch (err) {
    console.error("登录请求异常:", err);
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