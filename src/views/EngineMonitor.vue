<template>
  <div class="monitor-page">
    <header class="monitor-header">
      <div>
        <h2>推荐引擎监控</h2>
        <p>Real-time monitoring and control panel for the core discovery algorithms.</p>
      </div>
      <div class="monitor-actions">
        <button class="ghost-btn">清理缓存</button>
        <button class="primary-btn">重校准模型</button>
      </div>
    </header>

    <section class="status-card">
      <div class="status-left">
        <div class="status-icon">OK</div>
        <div>
          <h3>系统运行状态: 正常</h3>
          <p>All neural pathways and inference nodes are responding within expected parameters.</p>
        </div>
      </div>
      <div class="status-right">
        <div>
          <span>Uptime Rate</span>
          <strong>99.99%</strong>
        </div>
        <div>
          <span>Active Nodes</span>
          <strong>1,024</strong>
        </div>
      </div>
    </section>

    <section class="monitor-grid">
      <div class="panel">
        <h4>资源占用负载</h4>
        <div class="meter">
          <div class="meter-title">
            <span>CPU Allocation</span>
            <span class="value primary">45%</span>
          </div>
          <div class="meter-bar"><span style="width:45%"></span></div>
        </div>
        <div class="meter">
          <div class="meter-title">
            <span>Memory usage</span>
            <span class="value secondary">62%</span>
          </div>
          <div class="meter-bar secondary"><span style="width:62%"></span></div>
        </div>
        <div class="meter">
          <div class="meter-title">
            <span>GPU Inference Load</span>
            <span class="value tertiary">88%</span>
          </div>
          <div class="meter-bar tertiary"><span style="width:88%"></span></div>
          <p class="meter-note">Warning threshold: 90%</p>
        </div>
      </div>

      <div class="terminal">
        <div class="terminal-header">
          <span>Live Inference Logs</span>
          <div class="terminal-dots">
            <span></span>
            <span></span>
            <span></span>
          </div>
        </div>
        <div class="terminal-body">
          <p>[INFO] 10:42:01.001 - Node 04: Connected to user session stream.</p>
          <p>[SUCCESS] 10:42:01.045 - Recommendation Generated for User #84729 -> 12 tracks.</p>
          <p>[INFO] 10:42:02.112 - Node 01: Cache hit for cluster query Q-992.</p>
          <p>[SUCCESS] 10:42:02.880 - Model weights synchronized successfully across region AP.</p>
          <p>[INFO] 10:42:03.015 - Batch processing: 500 vector embeddings compiled.</p>
          <p>[WARN] 10:42:03.402 - Slight spike in inference time (85ms) detected.</p>
          <p>[SUCCESS] 10:42:04.100 - Node 12: Distributed discovery task completed.</p>
          <p class="cursor">admin@shiyinji-cluster:~$</p>
        </div>
      </div>
    </section>
  </div>
</template>

<style scoped>
.monitor-page {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.monitor-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  gap: 20px;
  border-bottom: 1px solid rgba(109, 123, 109, 0.2);
  padding-bottom: 16px;
}

.monitor-header h2 {
  margin: 0;
  font-size: 28px;
}

.monitor-header p {
  margin: 6px 0 0;
  color: rgba(25, 28, 29, 0.5);
  font-size: 13px;
}

.monitor-actions {
  display: flex;
  gap: 12px;
}

.ghost-btn {
  padding: 10px 18px;
  border-radius: 14px;
  border: 1px solid rgba(0, 109, 54, 0.25);
  background: transparent;
  font-weight: 700;
  color: var(--color-primary);
}

.primary-btn {
  padding: 10px 18px;
  border-radius: 14px;
  border: none;
  background: linear-gradient(135deg, var(--color-primary-container), var(--color-tertiary-container));
  font-weight: 700;
  color: var(--color-on-primary-container);
}

.status-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 24px;
  border-radius: 20px;
  border: 1px solid rgba(109, 123, 109, 0.18);
  background: var(--color-surface-container-lowest);
  position: relative;
  overflow: hidden;
}

.status-card::after {
  content: '';
  position: absolute;
  right: -40px;
  top: -40px;
  width: 160px;
  height: 160px;
  background: rgba(0, 109, 54, 0.08);
  border-radius: 50%;
}

.status-left {
  display: flex;
  align-items: center;
  gap: 16px;
  z-index: 1;
}

.status-icon {
  width: 52px;
  height: 52px;
  border-radius: 50%;
  background: rgba(0, 109, 54, 0.15);
  color: var(--color-primary);
  font-weight: 800;
  display: flex;
  align-items: center;
  justify-content: center;
}

.status-left h3 {
  margin: 0 0 6px;
}

.status-left p {
  margin: 0;
  color: rgba(25, 28, 29, 0.55);
  font-size: 12px;
}

.status-right {
  display: flex;
  gap: 32px;
  z-index: 1;
}

.status-right span {
  display: block;
  font-size: 10px;
  text-transform: uppercase;
  letter-spacing: 0.2em;
  color: rgba(25, 28, 29, 0.45);
}

.status-right strong {
  font-size: 20px;
}

.monitor-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}

.panel {
  background: var(--color-surface-container-lowest);
  border-radius: 20px;
  padding: 20px;
  border: 1px solid rgba(109, 123, 109, 0.18);
}

.panel h4 {
  margin: 0 0 18px;
}

.meter {
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-bottom: 16px;
}

.meter-title {
  display: flex;
  justify-content: space-between;
  font-size: 11px;
  text-transform: uppercase;
  letter-spacing: 0.12em;
  color: rgba(25, 28, 29, 0.5);
  font-weight: 700;
}

.meter-title .value {
  color: var(--color-primary);
}

.meter-title .value.secondary {
  color: var(--color-secondary);
}

.meter-title .value.tertiary {
  color: var(--color-tertiary);
}

.meter-bar {
  height: 8px;
  background: var(--color-surface-container-high);
  border-radius: 999px;
  overflow: hidden;
}

.meter-bar span {
  display: block;
  height: 100%;
  background: var(--color-primary);
}

.meter-bar.secondary span {
  background: var(--color-secondary);
}

.meter-bar.tertiary span {
  background: var(--color-tertiary);
}

.meter-note {
  margin: 0;
  font-size: 10px;
  text-align: right;
  color: rgba(25, 28, 29, 0.5);
}

.terminal {
  background: #1e1e1e;
  border-radius: 20px;
  border: 1px solid rgba(109, 123, 109, 0.2);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.terminal-header {
  background: #2d2d2d;
  padding: 10px 14px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #d7d7d7;
  font-size: 10px;
  text-transform: uppercase;
  letter-spacing: 0.2em;
}

.terminal-dots span {
  display: inline-block;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  margin-left: 4px;
}

.terminal-dots span:nth-child(1) {
  background: #ef4444;
}

.terminal-dots span:nth-child(2) {
  background: #38dac5;
}

.terminal-dots span:nth-child(3) {
  background: #006d36;
}

.terminal-body {
  padding: 16px;
  color: #d6d6d6;
  font-size: 11px;
  line-height: 1.6;
  height: 280px;
  overflow-y: auto;
}

.cursor {
  margin-top: 12px;
  color: #4ade80;
}

@media (max-width: 1024px) {
  .monitor-grid {
    grid-template-columns: 1fr;
  }

  .status-card {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }
}
</style>
