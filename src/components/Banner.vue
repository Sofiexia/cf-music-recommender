<template>
  <section class="banner-container">
    <div class="banner-card">
      <div class="banner-info">
        <span class="banner-tag">每日精选</span>
        <h1 class="banner-title">拾取城市里的最后一片声音</h1>
        <p class="banner-subtitle">
          探索由 AI 为你定制的私人旋律，发现那些藏在时光里的好音乐。
        </p>
        <div class="banner-btns">
          <button class="btn-primary" @click="emit('start-radio')">
            ▶ 开启私人漫游
          </button>
          <button class="btn-secondary" @click="emit('learn-more')">
            了解更多
          </button>
        </div>
      </div>
      <div class="banner-visual">
        <div class="visual-circle"></div>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
const emit = defineEmits<{
  (e: 'start-radio'): void
  (e: 'learn-more'): void
}>()
</script>

<style scoped>
.banner-container {
  margin-bottom: 40px;
}
.banner-card {
  position: relative;
  height: 280px;
  background: linear-gradient(135deg, #6e8efb 0%, #2d8cf0 100%);
  border-radius: 24px;
  padding: 40px 50px;
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  overflow: hidden;
  box-shadow: 0 20px 40px rgba(45, 140, 240, 0.2);
}
.banner-info {
  position: relative;
  z-index: 2;
  max-width: 500px;
}
.banner-tag {
  background: rgba(255, 255, 255, 0.2);
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 1px;
}
.banner-title {
  font-size: 36px;
  font-weight: 800;
  margin: 16px 0;
  line-height: 1.2;
}
.banner-subtitle {
  font-size: 16px;
  opacity: 0.9;
  margin-bottom: 30px;
  line-height: 1.6;
}
.banner-btns {
  display: flex;
  gap: 15px;
}
.btn-primary {
  background: white;
  color: #2d8cf0;
  border: none;
  padding: 12px 24px;
  border-radius: 12px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s;
}
.btn-primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(255, 255, 255, 0.3);
}
.btn-secondary {
  background: rgba(255, 255, 255, 0.1);
  color: white;
  border: 1px solid rgba(255, 255, 255, 0.3);
  padding: 12px 24px;
  border-radius: 12px;
  font-weight: 600;
  cursor: pointer;
  backdrop-filter: blur(5px);
}
.banner-visual {
  position: absolute;
  right: -50px;
  top: -50px;
  width: 300px;
  height: 300px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 50%;
  z-index: 1;
}
.visual-circle {
  position: absolute;
  right: 100px;
  bottom: 20px;
  width: 150px;
  height: 150px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 50%;
}
</style>