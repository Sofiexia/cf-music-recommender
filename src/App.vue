<template>
  <div id="app-main">
    <router-view v-if="!globalLoading" />
    
    <div v-if="globalLoading" class="global-loading">
      <div class="spinner"></div>
      <span>正在连接音乐世界...</span>
    </div>
    
    <div v-if="toastMessage" class="toast">{{ toastMessage }}</div>
  </div>
</template>


<script setup>

import { computed, onMounted } from 'vue'; 
import { useAppStore } from './stores/app';

const appStore = useAppStore();

// 建议加上 try-catch 或默认值防止 Store 未初始化报错
const globalLoading = computed(() => appStore?.globalLoading || false);
const toastMessage = computed(() => appStore?.toastMessage || '');

onMounted(() => {
  console.log("App 组件已挂载，当前加载状态:", globalLoading.value);
});
</script>

<style>
html,
body,
#app {
  margin: 0;
  padding: 0;
  width: 100%;
  min-height: 100%;
  background-color: var(--color-background);
}

#app-main {
  width: 100%;
  min-height: 100%;
}

.global-loading {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: rgba(255, 255, 255, 0.9);
  color: var(--color-on-background);
  z-index: 9999;
}

.spinner {
  width: 40px;
  height: 40px;
  border: 4px solid rgba(0, 0, 0, 0.08);
  border-top: 4px solid var(--color-primary);
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 10px;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

.toast {
  position: fixed;
  bottom: 40px;
  left: 50%;
  transform: translateX(-50%);
  background: rgba(15, 15, 15, 0.8);
  color: #ffffff;
  padding: 10px 20px;
  border-radius: 25px;
  z-index: 10000;
  font-size: 14px;
}
</style>