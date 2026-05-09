<template>
  <section class="hero">
    <img
      class="hero-image"
      src="https://images.unsplash.com/photo-1511379938547-c1f69419868d?w=1600&q=80"
      alt="Hero"
    />
    <div class="hero-overlay">
      <div class="hero-content">
        <span class="hero-badge">Editor's Choice</span>
        <h1 class="hero-title">
          寻找你灵魂的
          <span>旋律</span>
        </h1>
        <p class="hero-subtitle">
          基于协同过滤的智能音乐推荐系统 AI 引擎已根据您的听歌偏好，为您整理出本周最懂您的 20 首动听曲目。
        </p>
        <div class="hero-actions">
          <button class="btn-primary" @click="emit('start-radio')">
            立即播放
          </button>
          <button class="btn-secondary" @click="emit('learn-more')">
            查看详情
          </button>
        </div>
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
.hero {
  position: relative;
  height: 380px;
  border-radius: 32px;
  overflow: hidden;
  margin-bottom: 32px;
  box-shadow: 0 24px 50px rgba(0, 0, 0, 0.15);
}

.hero-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 1s ease;
}

.hero:hover .hero-image {
  transform: scale(1.05);
}

.hero-overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(90deg, rgba(0, 0, 0, 0.65), rgba(0, 0, 0, 0.1));
  display: flex;
  align-items: center;
}

.hero-content {
  padding: 40px 56px;
  color: #ffffff;
  max-width: 560px;
}

.hero-badge {
  display: inline-block;
  padding: 6px 14px;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.2);
  font-size: 11px;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.2em;
  margin-bottom: 16px;
}

.hero-title {
  font-size: 42px;
  font-weight: 800;
  margin: 0 0 16px;
  line-height: 1.1;
}

.hero-title span {
  color: var(--color-primary-container);
}

.hero-subtitle {
  font-size: 16px;
  opacity: 0.85;
  margin-bottom: 24px;
  line-height: 1.6;
}

.hero-actions {
  display: flex;
  gap: 14px;
  flex-wrap: wrap;
}

.btn-primary {
  background: var(--color-primary);
  color: var(--color-on-primary);
  border: none;
  padding: 12px 26px;
  border-radius: 999px;
  font-weight: 700;
  cursor: pointer;
  box-shadow: 0 12px 24px rgba(0, 109, 54, 0.25);
}

.btn-secondary {
  background: rgba(255, 255, 255, 0.12);
  color: #ffffff;
  border: 1px solid rgba(255, 255, 255, 0.4);
  padding: 12px 26px;
  border-radius: 999px;
  font-weight: 700;
  cursor: pointer;
  backdrop-filter: blur(6px);
}

@media (max-width: 960px) {
  .hero {
    height: 320px;
  }

  .hero-content {
    padding: 28px 32px;
  }

  .hero-title {
    font-size: 32px;
  }
}
</style>