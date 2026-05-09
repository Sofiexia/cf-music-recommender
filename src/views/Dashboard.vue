<template>
  <div class="dashboard">
    <header class="dashboard-header">
      <div>
        <h2>今日概览</h2>
        <p>数据最后更新于 10 分钟前</p>
      </div>
      <button class="range-btn">近 7 天</button>
    </header>

    <section class="stat-grid">
      <div v-for="card in statCards" :key="card.label" class="stat-card">
        <div class="stat-top">
          <p>{{ card.label }}</p>
          <span class="stat-icon">{{ card.icon }}</span>
        </div>
        <h3>{{ card.value }}</h3>
        <div class="stat-trend">
          <span class="trend-dot"></span>
          <span>{{ card.trend }}</span>
          <span class="trend-label">较上期</span>
        </div>
        <div class="stat-wave"></div>
      </div>

      <div class="stat-card glass">
        <div class="stat-top">
          <p>算法命中率</p>
          <span class="stat-icon">AI</span>
        </div>
        <h3 class="stat-highlight">94.2%</h3>
        <div class="stat-trend">
          <span class="trend-dot pulse"></span>
          <span class="trend-label">模型状态健康</span>
        </div>
      </div>
    </section>

    <section class="panel-grid">
      <div class="panel chart">
        <h3>用户活跃度趋势</h3>
        <svg viewBox="0 0 600 240" preserveAspectRatio="none">
          <defs>
            <linearGradient id="chartFill" x1="0" y1="0" x2="0" y2="1">
              <stop offset="0%" stop-color="#4ade80" stop-opacity="0.35" />
              <stop offset="100%" stop-color="#4ade80" stop-opacity="0" />
            </linearGradient>
          </defs>
          <path
            d="M0,200 C60,140 120,160 180,120 C240,70 300,120 360,90 C420,60 480,40 600,80 L600,240 L0,240 Z"
            fill="url(#chartFill)"
          />
          <path
            d="M0,200 C60,140 120,160 180,120 C240,70 300,120 360,90 C420,60 480,40 600,80"
            fill="none"
            stroke="#006d36"
            stroke-width="4"
          />
        </svg>
      </div>

      <div class="panel logs">
        <div class="panel-header">
          <h3>引擎实时监控</h3>
          <span class="live-dot"></span>
        </div>
        <div class="log-list">
          <div v-for="log in logs" :key="log.id" class="log-item">
            <div class="log-icon">Play</div>
            <div>
              <p class="log-title">命中 · {{ log.id }}</p>
              <p class="log-msg">{{ log.msg }}</p>
              <span class="log-tag">{{ log.tag }}</span>
            </div>
          </div>
        </div>
        <button class="link-btn">查看完整日志</button>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
const statCards = [
  { label: '总用户数', value: '84,209', trend: '+12.5%', icon: 'U' },
  { label: '活跃听众', value: '12,450', trend: '+5.2%', icon: 'H' },
  { label: '24h 播放量', value: '342.8k', trend: '+18.1%', icon: 'P' }
]

const logs = [
  { id: '#A982', msg: '推送歌曲《夜曲》 -> 完整播放', tag: '华语流行' },
  { id: 'SYS', msg: '聚类分析完成: 新增细分流派 nodes', tag: 'Chillwave' },
  { id: '#B210', msg: '漫游模式切歌率极低 (0.5%)', tag: '纯音乐' }
]
</script>

<style scoped>
.dashboard {
  display: flex;
  flex-direction: column;
  gap: 28px;
  animation: fade-in 0.5s ease;
}

.dashboard-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
}

.dashboard-header h2 {
  margin: 0;
  font-size: 28px;
}

.dashboard-header p {
  margin: 6px 0 0;
  color: rgba(25, 28, 29, 0.5);
  font-size: 13px;
}

.range-btn {
  padding: 10px 16px;
  border-radius: 12px;
  border: 1px solid rgba(109, 123, 109, 0.2);
  background: transparent;
  font-weight: 700;
  cursor: pointer;
}

.stat-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 18px;
}

.stat-card {
  background: var(--color-surface-container-lowest);
  border-radius: 18px;
  padding: 20px;
  position: relative;
  overflow: hidden;
  border: 1px solid rgba(109, 123, 109, 0.12);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.04);
}

.stat-card.glass {
  background: rgba(255, 255, 255, 0.7);
  backdrop-filter: blur(20px);
}

.stat-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: rgba(25, 28, 29, 0.5);
  font-weight: 600;
  font-size: 12px;
}

.stat-icon {
  font-size: 12px;
  padding: 4px 8px;
  border-radius: 8px;
  background: rgba(0, 109, 54, 0.12);
  color: var(--color-primary);
}

.stat-card h3 {
  margin: 12px 0 8px;
  font-size: 26px;
}

.stat-highlight {
  color: var(--color-primary);
}

.stat-trend {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 12px;
  color: var(--color-primary);
  font-weight: 700;
}

.trend-label {
  color: rgba(25, 28, 29, 0.5);
  font-weight: 600;
}

.trend-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: var(--color-primary);
}

.trend-dot.pulse {
  animation: pulse 1.2s infinite ease;
}

.stat-wave {
  position: absolute;
  bottom: -40px;
  right: -20px;
  width: 140px;
  height: 120px;
  background: rgba(0, 109, 54, 0.08);
  border-radius: 50%;
}

.panel-grid {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 20px;
}

.panel {
  background: var(--color-surface-container-lowest);
  border-radius: 20px;
  padding: 20px;
  border: 1px solid rgba(109, 123, 109, 0.12);
  box-shadow: 0 10px 24px rgba(0, 0, 0, 0.05);
}

.panel.chart svg {
  width: 100%;
  height: 220px;
  margin-top: 10px;
}

.panel.logs {
  display: flex;
  flex-direction: column;
}

.panel-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.live-dot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background: var(--color-primary);
  box-shadow: 0 0 0 6px rgba(0, 109, 54, 0.12);
}

.log-list {
  display: flex;
  flex-direction: column;
  gap: 14px;
  margin-top: 16px;
  flex: 1;
}

.log-item {
  display: flex;
  gap: 12px;
  border-bottom: 1px solid rgba(109, 123, 109, 0.12);
  padding-bottom: 12px;
}

.log-item:last-child {
  border-bottom: none;
}

.log-icon {
  background: rgba(0, 109, 54, 0.12);
  color: var(--color-primary);
  font-size: 10px;
  font-weight: 700;
  padding: 8px;
  border-radius: 10px;
}

.log-title {
  margin: 0;
  font-weight: 700;
  font-size: 13px;
}

.log-msg {
  margin: 4px 0;
  font-size: 12px;
  color: rgba(25, 28, 29, 0.55);
}

.log-tag {
  font-size: 10px;
  font-weight: 700;
  color: var(--color-primary);
  background: rgba(0, 109, 54, 0.12);
  padding: 2px 8px;
  border-radius: 999px;
}

.link-btn {
  margin-top: 18px;
  background: none;
  border: none;
  color: var(--color-primary);
  font-weight: 700;
  cursor: pointer;
  padding: 0;
}

@keyframes pulse {
  0% {
    transform: scale(1);
    opacity: 0.9;
  }
  70% {
    transform: scale(1.6);
    opacity: 0.2;
  }
  100% {
    transform: scale(1.6);
    opacity: 0;
  }
}

@keyframes fade-in {
  from {
    opacity: 0;
    transform: translateY(8px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@media (max-width: 1024px) {
  .panel-grid {
    grid-template-columns: 1fr;
  }
}
</style>
