<template>
  <aside class="sidebar">
    <div class="sidebar-wrapper">
      <div class="menu-group">
        <h3 class="group-title">在线音乐</h3>
        <nav class="nav-list">
          <div class="nav-item active">
            <span class="icon">🏠</span>
            <span class="label">发现音乐</span>
          </div>
          <div class="nav-item">
            <span class="icon">📻</span>
            <span class="label">私人漫游</span>
          </div>
          <div class="nav-item">
            <span class="icon">📊</span>
            <span class="label">排行榜</span>
          </div>
        </nav>
      </div>

      <div class="menu-group">
        <h3 class="group-title">我的音乐</h3>
        <nav class="nav-list">
          <div class="nav-item">
            <span class="icon">❤️</span>
            <span class="label">我喜欢的</span>
          </div>
          <div class="nav-item">
            <span class="icon">🕒</span>
            <span class="label">最近播放</span>
          </div>
        </nav>
      </div>

      <div class="menu-group">
        <h3 class="group-title">创建的歌单</h3>
        <div class="playlist-item">歌单一</div>
        <div class="playlist-item">歌单二</div>
        <div class="playlist-item">歌单三</div>
      </div>
    </div>
  </aside>
</template>

<style scoped>
.sidebar {
  width: 220px;
  height: 100%;
  border-right: 1px solid rgba(0, 0, 0, 0.04);
  background: transparent;
  flex-shrink: 0; /* 防止被右侧内容挤压变形 */
  overflow-y: auto;
}

.sidebar-wrapper {
  padding: 24px 16px;
  display: flex;
  flex-direction: column;
  gap: 32px;
}

.group-title {
  font-size: 12px;
  color: #999;
  padding-left: 12px;
  margin-bottom: 12px;
  letter-spacing: 1px;
}

.nav-list {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.nav-item {
  display: flex;
  align-items: center;
  padding: 10px 12px;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.2s ease;
  color: #515a6e;
}

.nav-item:hover {
  background: rgba(45, 140, 240, 0.05);
  color: #2d8cf0;
}

.nav-item.active {
  background: #eef6ff;
  color: #2d8cf0;
  font-weight: 600;
}

.icon {
  margin-right: 12px;
  font-size: 16px;
}

.label {
  font-size: 14px;
}

.playlist-item {
  padding: 8px 12px;
  font-size: 13px;
  color: #666;
  cursor: pointer;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  border-radius: 8px;
  transition: all 0.2s ease;
}

.playlist-item:hover {
  background: rgba(45, 140, 240, 0.03);
  color: #2d8cf0;
}

/* 隐藏滚动条但保留滚动功能 */
.sidebar::-webkit-scrollbar {
  width: 0px;
}
</style>