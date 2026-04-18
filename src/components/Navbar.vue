<template>
  <header class="navbar">
    <div class="nav-container">
      <!-- 左侧 Logo 区域 -->
      <div class="logo-area" @click="emit('clear-search')">
        <div class="logo-icon-wrapper">
          <div class="logo-wave"></div>
          <div class="logo-wave"></div>
          <div class="logo-wave"></div>
          <span class="logo-symbol">♫</span>
        </div>
        <div class="logo-text-wrapper">
          <h1 class="logo-main-title">拾音集</h1>
          <span class="logo-tagline">COLLECT MUSICS</span>
        </div>
      </div>

      <!-- 中间搜索框 -->
      <div class="search-center">
        <div class="search-bar-modern" :class="{ focused: isSearchFocused }">
          <i class="icon-search">🔍</i>
          <input
            :value="searchKeyword"
            @input="emit('update:searchKeyword', ($event.target as HTMLInputElement).value)"
            placeholder="搜索音乐..."
            @keyup.enter="emit('search')"
            @focus="isSearchFocused = true"
            @blur="isSearchFocused = false"
          />
          <transition name="fade">
            <button
              v-if="searchKeyword"
              @click="emit('clear-search')"
              class="search-clear-btn"
            >
              ✕
            </button>
          </transition>
        </div>
      </div>

      <!-- 右侧用户操作区 -->
      <div class="user-action-area">
        <div class="user-menu-wrapper">
          <div class="user-profile-lite">
            <div class="user-avatar">
              <img :src="userAvatar" @error="handleImgError" />
            </div>
            <span class="user-name">{{ userInfo.username || '听歌人' }}</span>
            <i class="icon-arrow"></i> </div>

          <div class="user-dropdown">
            <div class="dropdown-header">
              <p class="account-id">ID: {{ userInfo.userId || '---' }}</p>
              <p class="account-email">{{ userInfo.email || '尚未绑定邮箱' }}</p>
            </div>
            <div class="dropdown-divider"></div>
            <div class="dropdown-menu">
              <div class="menu-item">
                <i class="icon">⚙️</i> 设置
              </div>
              <div class="menu-item logout" @click="emit('logout')">
                <i class="icon">🚪</i> 退出登录
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'

const userInfo = ref({
  username: '',
  userId: '',
  email: ''
})

onMounted(() => {
  // 从本地存储读取用户信息
  const savedInfo = localStorage.getItem('userInfo')
  if (savedInfo) {
    userInfo.value = JSON.parse(savedInfo)
  }
})

// 默认头像逻辑
const userAvatar = ref('https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/20/2c/e5/202ce578-3c96-12e7-7f55-72618aeffd60/850113.jpg/600x600bb.jpg')

// Props & Emits
defineProps<{
  searchKeyword: string;
}>();

const emit = defineEmits<{
  (e: 'update:searchKeyword', value: string): void;
  (e: 'search'): void;
  (e: 'clear-search'): void;
  (e: 'logout'): void;
}>();

const isSearchFocused = ref(false);
</script>

<style scoped>
.navbar {
  position: sticky;
  top: 0;
  height: 80px;
  background: rgba(255, 255, 255, 0.7);
  backdrop-filter: blur(20px) saturate(180%);
  -webkit-backdrop-filter: blur(20px) saturate(180%);
  border-bottom: 1px solid rgba(0, 0, 0, 0.04);
  z-index: 1000;
  display: flex;
  align-items: center;
}
.nav-container {
  width: 100%;
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 30px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.logo-area {
  display: flex;
  align-items: center;
  gap: 14px;
  cursor: pointer;
  user-select: none;
}
.logo-icon-wrapper {
  position: relative;
  width: 42px;
  height: 42px;
  background: linear-gradient(135deg, #2d8cf0, #5cadff);
  border-radius: 12px;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}
.logo-symbol {
  color: white;
  font-size: 20px;
  font-weight: bold;
  z-index: 2;
}
.logo-wave {
  position: absolute;
  width: 100%;
  height: 100%;
  background: rgba(255, 255, 255, 0.15);
  border-radius: 40%;
  animation: wave 5s infinite linear;
}
.logo-wave:nth-child(2) {
  top: 10%;
  left: 10%;
  animation-duration: 7s;
}
.logo-wave:nth-child(3) {
  top: -5%;
  left: -5%;
  animation-duration: 9s;
}
@keyframes wave {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}
.logo-main-title {
  font-size: 20px;
  font-weight: 800;
  color: #1c2438;
  letter-spacing: 1px;
  margin: 0;
}
.logo-tagline {
  font-size: 9px;
  color: #808695;
  letter-spacing: 2px;
  font-weight: 600;
}
.search-center {
  flex: 0 1 500px;
}
.search-bar-modern {
  display: flex;
  align-items: center;
  background: #f5f7f9;
  padding: 10px 20px;
  border-radius: 30px;
  transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
  border: 1.5px solid transparent;
}
.search-bar-modern.focused {
  background: #fff;
  border-color: #2d8cf0;
  box-shadow: 0 8px 20px rgba(45, 140, 240, 0.1);
  transform: translateY(-1px);
}
.search-bar-modern input {
  flex: 1;
  background: transparent;
  border: none;
  outline: none;
  padding: 0 12px;
  font-size: 14px;
  color: #495060;
}
.search-clear-btn {
  background: #bbbec4;
  color: white;
  border: none;
  width: 16px;
  height: 16px;
  border-radius: 50%;
  font-size: 8px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}
.user-action-area {
  display: flex;
  align-items: center;
  gap: 20px;
}
.user-profile-lite {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 4px 12px 4px 4px;
  background: rgba(0, 0, 0, 0.02);
  border-radius: 24px;
}
.user-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  border: 2px solid white;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
}
.user-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.user-meta {
  display: flex;
  flex-direction: column;
}
.welcome-text {
  font-size: 10px;
  color: #999;
}
.display-name {
  font-size: 13px;
  font-weight: 600;
  color: #464c5b;
}
.vertical-divider {
  width: 1px;
  height: 18px;
  background: #e8eaec;
}
.btn-logout-icon {
  background: transparent;
  border: 1px solid #dcdee2;
  color: #808695;
  padding: 6px 14px;
  border-radius: 16px;
  font-size: 12px;
  cursor: pointer;
  transition: all 0.2s;
}
.btn-logout-icon:hover {
  background: #fff1f0;
  color: #ed4014;
  border-color: #ffa39e;
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
/* 容器设置为相对定位 */
.user-menu-wrapper {
  position: relative;
  cursor: pointer;
  padding: 5px 0;
}

/* 默认隐藏浮窗 */
.user-dropdown {
  position: absolute;
  top: 110%; /* 出现在头像下方 */
  right: 0;
  width: 200px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
  border: 1px solid rgba(0, 0, 0, 0.05);
  padding: 12px 0;
  z-index: 1000;
  backdrop-filter: blur(10px);
  
  /* 初始不可见状态 */
  opacity: 0;
  transform: translateY(10px);
  pointer-events: none;
  transition: all 0.3s ease;
}

/* 鼠标悬停时显示 */
.user-menu-wrapper:hover .user-dropdown {
  opacity: 1;
  transform: translateY(0);
  pointer-events: auto;
}

/* 内部细节样式 */
.dropdown-header {
  padding: 8px 16px;
}
.account-id {
  font-size: 12px;
  color: #999;
  margin: 0;
}
.account-email {
  font-size: 13px;
  font-weight: 500;
  margin: 4px 0 0 0;
}

.dropdown-divider {
  height: 1px;
  background: #f0f0f0;
  margin: 10px 0;
}

.menu-item {
  padding: 10px 16px;
  font-size: 14px;
  color: #333;
  display: flex;
  align-items: center;
  gap: 10px;
  transition: background 0.2s;
}

.menu-item:hover {
  background: #f5f5f5;
}

.menu-item.logout {
  color: #ed4014; /* 退出按钮用红色 */
}
</style>