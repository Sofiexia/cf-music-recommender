<template>
  <div class="register-container">
    <div class="register-card">
      <h2>加入 基于协同过滤的智能音乐推荐系统</h2>
      <p class="subtitle">创建账号，开启你的音乐之旅</p>

      <div class="form-body">
        <div class="form-group">
          <label>用户名 <span class="required">*</span></label>
          <input 
            v-model.trim="form.username" 
            placeholder="3-20个字符" 
            :class="{ 'error-border': errors.username }"
          />
          <span class="error-msg" v-if="errors.username">{{ errors.username }}</span>
        </div>

        <div class="form-group">
          <label>密码 <span class="required">*</span></label>
          <input 
            v-model="form.password" 
            type="password" 
            placeholder="至少6位字符" 
            :class="{ 'error-border': errors.password }"
          />
          <span class="error-msg" v-if="errors.password">{{ errors.password }}</span>
        </div>

        <div class="form-group">
          <label>邮箱 <span class="required">*</span></label>
          <input 
            v-model.trim="form.email" 
            type="email" 
            placeholder="example@mail.com" 
            :class="{ 'error-border': errors.email }"
          />
          <span class="error-msg" v-if="errors.email">{{ errors.email }}</span>
        </div>

        <div class="form-row">
          <div class="form-group half">
            <label>性别</label>
            <select v-model="form.gender">
              <option value="female">女</option>
              <option value="male">男</option>
              <option value="unknown">保密</option>
            </select>
          </div>
          <div class="form-group half">
            <label>年龄</label>
            <input v-model.number="form.age" type="number" min="1" max="120" />
          </div>
        </div>

        <button 
          class="reg-btn" 
          @click="handleRegister" 
          :disabled="loading"
        >
          <span v-if="loading" class="loader"></span>
          {{ loading ? '正在提交...' : '立即注册' }}
        </button>

        <div class="footer-links">
          <span>已有账号？</span>
          <router-link to="/login">返回登录</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import request from '../api/request';

const router = useRouter();
const loading = ref(false);
const errors = reactive({
  username: '',
  password: '',
  email: ''
});

const form = reactive({
  username: '',
  password: '',
  email: '',
  gender: 'male',
  age: 18
});

// 简单的表单校验逻辑
const validateForm = () => {
  let isValid = true;
  // 重置错误
  errors.username = '';
  errors.password = '';
  errors.email = '';

  if (form.username.length < 3) {
    errors.username = '用户名至少需要3个字符';
    isValid = false;
  }
  if (form.password.length < 6) {
    errors.password = '密码不能少于6位';
    isValid = false;
  }
  const emailReg = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailReg.test(form.email)) {
    errors.email = '请输入有效的邮箱地址';
    isValid = false;
  }
  return isValid;
};

const handleRegister = async () => {
  if (!validateForm()) return;

  loading.value = true;
  try {
    const res = await request.post('/auth/register', form);
    
    if (res.data.code === 200) {
      // 成功后的反馈
      alert("注册成功！欢迎加入 基于协同过滤的智能音乐推荐系统");
      router.push('/login');
    } else {
      // 处理后端返回的业务错误（如：邮箱已存在、用户名已存在）
      // 这里对应的就是你后端 Result.error() 的信息
      alert(res.data.message || "注册失败，请检查输入内容");
    }
  } catch (err) {
    // 处理网络错误或服务器崩溃(500)
    console.error("注册请求异常:", err);
    alert("服务器繁忙，请稍后再试或更换邮箱注册");
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f5f7f9;
}
.register-card {
  background: white;
  padding: 30px 40px;
  border-radius: 16px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.05);
  width: 100%;
  max-width: 420px;
}
h2 { text-align: center; color: #333; margin-bottom: 8px; font-weight: 600; }
.subtitle { text-align: center; color: #999; font-size: 14px; margin-bottom: 30px; }

.form-group { margin-bottom: 20px; position: relative; }
.form-group label { display: block; margin-bottom: 8px; font-size: 13px; color: #666; font-weight: 500; }
.required { color: #ff4d4f; }

.form-row { display: flex; gap: 15px; }
.half { flex: 1; }

input, select {
  width: 100%;
  padding: 12px 15px;
  border: 1px solid #e8e8e8;
  border-radius: 8px;
  box-sizing: border-box;
  background: #fcfcfc;
  transition: all 0.2s;
}

input:focus { outline: none; border-color: #2d8cf0; background: #fff; box-shadow: 0 0 0 2px rgba(45,140,240,0.1); }
.error-border { border-color: #ff4d4f !important; }
.error-msg { color: #ff4d4f; font-size: 12px; position: absolute; bottom: -18px; left: 0; }

.reg-btn {
  width: 100%;
  padding: 14px;
  background: #1db954; 
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
  font-weight: 600;
  margin-top: 15px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
}

.reg-btn:hover { background: #1aa34a; transform: translateY(-1px); }
.reg-btn:disabled { background: #a5d6a7; cursor: not-allowed; transform: none; }

/* 简单的加载小圆圈 */
.loader {
  width: 16px; height: 16px;
  border: 2px solid #ffffff;
  border-bottom-color: transparent;
  border-radius: 50%;
  animation: rotation 1s linear infinite;
}
@keyframes rotation { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }

.footer-links { text-align: center; margin-top: 25px; font-size: 14px; color: #888; }
.footer-links a { color: #1db954; text-decoration: none; font-weight: 500; margin-left: 5px; }
</style>