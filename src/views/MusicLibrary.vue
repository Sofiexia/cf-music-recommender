<template>
  <div class="library-page">
    <header class="library-header">
      <div>
        <h2>音乐库</h2>
        <p>管理平台内所有音频资源与元数据。</p>
      </div>
      <button class="primary-btn">添加新歌曲</button>
    </header>

    <section class="library-filters">
      <div class="search-box">
        <input placeholder="搜索歌曲名、歌手或专辑..." />
      </div>
      <div class="filter-selects">
        <select>
          <option>所有流派</option>
          <option>流行 Pop</option>
          <option>电子 Electronic</option>
          <option>古典 Classical</option>
        </select>
        <select>
          <option>按热度排序</option>
          <option>最新添加</option>
          <option>时长最长</option>
        </select>
      </div>
      <div class="bulk-actions">
        <button>批量操作</button>
        <button class="danger">批量删除</button>
      </div>
    </section>

    <section class="library-table">
      <div class="table-head">
        <span></span>
        <span>封面</span>
        <span>标题与艺术家</span>
        <span>专辑</span>
        <span class="right">时长</span>
        <span class="right">播放量</span>
        <span class="center">操作</span>
      </div>
      <div class="table-body">
        <div v-for="song in songs" :key="song.title" class="table-row">
          <span><input type="checkbox" /></span>
          <span class="cover"><img :src="song.cover" :alt="song.title" /></span>
          <span class="title">
            <strong>{{ song.title }}</strong>
            <small>{{ song.artist }}</small>
          </span>
          <span>
            <span class="album">{{ song.album }}</span>
            <span class="genre">{{ song.genre }}</span>
          </span>
          <span class="right mono">{{ song.duration }}</span>
          <span class="right bold">{{ song.plays }}</span>
          <span class="center actions">
            <button>试听</button>
            <button>编辑</button>
            <button>删除</button>
          </span>
        </div>
      </div>
      <div class="table-footer">
        <span>显示 1 - 3 条记录，共 1,204 条系统收录</span>
        <div class="pager">
          <button class="active">1</button>
          <button>2</button>
          <button>3</button>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
const songs = [
  {
    title: 'Midnight Breeze',
    artist: 'Luna Echoes',
    album: 'Silent Horizons (EP)',
    genre: 'Electronic',
    duration: '3:45',
    plays: '12.4k',
    cover:
      'https://lh3.googleusercontent.com/aida-public/AB6AXuAaZ8hIyQzll2Tl6YQivMkIEtvYkLZVsuXxctbudfCT2zwcsNuG9d3P4xy6sI1fZUcI9yodhvfisTwrwvfbH_EMWYliv9r1OncGj9yEy2uSnwZOz5iBfbClZPJwKkyT1hSsYHzdzfsDxe8Es7oSoElupVxr8qrTUvYiBxaA_UBJ6IbBYOt9Ho-F7Vlfz4yU6RDmoXOdH8h1yNa3nBYNb-qKGdsK2p4WDeSAOFgxG-doPiwYNPKXntyPfcRqWQ2Oiv69y3U7HOOOM8A'
  },
  {
    title: 'Urban Symphony',
    artist: 'The Architects',
    album: 'Concrete & Glass',
    genre: 'Indie Pop',
    duration: '4:12',
    plays: '8.1k',
    cover:
      'https://lh3.googleusercontent.com/aida-public/AB6AXuDRNs3ega-1tCxyUzkYJRNVVZDTTJBR-4894zVbpYSb4gqRcIWJ_jANKOgbb_CELp-JSSg_bbUG7w7bQfAzGJQKxDEybz48oksJwxKVAMv7Zo9BGwUZqXfwS2d1uRuegd2376RO2K6qwaCXp5a1pPGcLIuXpwA_MLwMdjhPbXqicbwVd3ye04EPEUZ2w2-kKCHFt60KL5A6AolQSWzy5qnexv8qDXE-spkzXNH5CHwJ4xOcyca_ELyH1pPrkKAtjKF3gE8h_kC-6E8'
  },
  {
    title: 'Desert Rain',
    artist: 'Mirage Collective',
    album: 'Oasis',
    genre: 'Ambient',
    duration: '5:30',
    plays: '3.2k',
    cover:
      'https://lh3.googleusercontent.com/aida-public/AB6AXuC-pW5WPzi23D0-HQDT7pNnPpdQmJ55hqFCS3ju3pjK3flusk4C7NYDAO3_LT4Z2wN0Y9X8t5EAAmH6yhCEkvVGI2Zrld3kOFtzssWm76RLy59te21l54Te-I__bN8aBlwFeXm1jjExnUTkdjiViXkrsrS_E7CB9cPfAQV5OnkGU_hpbTXdoLxaDL9Z4gN3-vag3BHgNnPeufJ824snIiRqkwIPzoupMTJ_FadBXSGnqOvt4eZm0Y3gNQk9GQecn-MpUk9JXRc__HM'
  }
]
</script>

<style scoped>
.library-page {
  display: flex;
  flex-direction: column;
  gap: 22px;
}

.library-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
}

.library-header h2 {
  margin: 0;
  font-size: 30px;
}

.library-header p {
  margin: 6px 0 0;
  color: rgba(25, 28, 29, 0.5);
}

.primary-btn {
  padding: 12px 22px;
  border-radius: 999px;
  border: none;
  background: linear-gradient(135deg, var(--color-primary-container), var(--color-tertiary-container));
  color: var(--color-on-primary-container);
  font-weight: 700;
}

.library-filters {
  display: flex;
  flex-wrap: wrap;
  gap: 14px;
  align-items: center;
  background: var(--color-surface-container-lowest);
  border-radius: 20px;
  border: 1px solid rgba(109, 123, 109, 0.15);
  padding: 14px 16px;
}

.search-box input,
.filter-selects select {
  padding: 10px 14px;
  border-radius: 12px;
  border: none;
  background: var(--color-surface-container-low);
  font-size: 13px;
}

.filter-selects {
  display: flex;
  gap: 10px;
}

.bulk-actions {
  margin-left: auto;
  display: flex;
  gap: 8px;
}

.bulk-actions button {
  border: none;
  padding: 8px 12px;
  border-radius: 12px;
  background: var(--color-surface-container-high);
  font-weight: 700;
  cursor: pointer;
}

.bulk-actions button.danger {
  color: var(--color-error);
  background: rgba(186, 26, 26, 0.12);
}

.library-table {
  border-radius: 22px;
  border: 1px solid rgba(109, 123, 109, 0.15);
  overflow: hidden;
  background: var(--color-surface-container-lowest);
}

.table-head,
.table-row {
  display: grid;
  grid-template-columns: 40px 60px 1.2fr 1fr 100px 100px 140px;
  gap: 12px;
  align-items: center;
  padding: 12px 16px;
}

.table-head {
  background: rgba(243, 244, 245, 0.6);
  text-transform: uppercase;
  font-size: 11px;
  letter-spacing: 0.1em;
  color: rgba(25, 28, 29, 0.5);
  font-weight: 700;
}

.table-row {
  border-top: 1px solid rgba(109, 123, 109, 0.12);
}

.table-row:hover {
  background: rgba(243, 244, 245, 0.6);
}

.cover img {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  object-fit: cover;
}

.title strong {
  display: block;
  font-size: 13px;
}

.title small {
  color: rgba(25, 28, 29, 0.5);
  font-size: 11px;
}

.album {
  display: block;
  font-size: 12px;
  margin-bottom: 4px;
}

.genre {
  background: rgba(0, 109, 54, 0.12);
  color: var(--color-primary);
  font-size: 10px;
  padding: 2px 8px;
  border-radius: 999px;
  font-weight: 700;
}

.right {
  text-align: right;
}

.center {
  text-align: center;
}

.bold {
  font-weight: 700;
}

.mono {
  font-family: ui-monospace, SFMono-Regular, Menlo, monospace;
}

.actions button {
  border: none;
  background: transparent;
  font-weight: 700;
  color: rgba(25, 28, 29, 0.6);
  margin: 0 4px;
}

.table-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 16px;
  border-top: 1px solid rgba(109, 123, 109, 0.12);
  font-size: 12px;
  color: rgba(25, 28, 29, 0.5);
}

.pager {
  display: flex;
  gap: 6px;
}

.pager button {
  width: 28px;
  height: 28px;
  border-radius: 8px;
  border: 1px solid rgba(109, 123, 109, 0.2);
  background: transparent;
  font-weight: 700;
}

.pager button.active {
  background: var(--color-primary);
  color: var(--color-on-primary);
}

@media (max-width: 1200px) {
  .table-head,
  .table-row {
    grid-template-columns: 40px 60px 1fr 1fr 80px 80px 120px;
  }
}
</style>
