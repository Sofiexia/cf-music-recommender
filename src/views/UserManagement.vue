<template>
  <div class="users-page">
    <header class="users-header">
      <div>
        <h2>用户管理</h2>
        <p>管理平台用户，配置偏好标签及账户状态。</p>
      </div>
      <div class="header-actions">
        <button class="ghost-btn">导出数据</button>
        <button class="primary-btn">添加新用户</button>
      </div>
    </header>

    <section class="filter-bar">
      <div class="filter-left">
        <div class="search-box">
          <input type="text" placeholder="搜索用户名或 ID..." />
        </div>
        <select>
          <option value="all">所有状态</option>
          <option value="active">活跃 (Active)</option>
          <option value="banned">封禁 (Banned)</option>
          <option value="new">新增 (New)</option>
        </select>
      </div>
      <div class="filter-actions">
        <button class="text-btn">重置</button>
        <button class="solid-btn">应用过滤</button>
      </div>
    </section>

    <section class="table-card">
      <div class="table-scroll">
        <table>
          <thead>
            <tr>
              <th>头像</th>
              <th>用户名 / 邮箱</th>
              <th>用户 ID</th>
              <th>注册日期</th>
              <th>音乐偏好标签</th>
              <th>状态</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in users" :key="user.id" :class="{ banned: user.status === '已封禁' }">
              <td>
                <div class="avatar">
                  <img v-if="user.avatar" :src="user.avatar" :alt="user.name" />
                  <span v-else>{{ user.name.slice(0, 1) }}</span>
                </div>
              </td>
              <td>
                <div class="user-info">
                  <span class="user-name">{{ user.name }}</span>
                  <span class="user-email">{{ user.email }}</span>
                </div>
              </td>
              <td class="mono">{{ user.id }}</td>
              <td>{{ user.date }}</td>
              <td>
                <div class="tag-list">
                  <span v-for="tag in user.tags" :key="tag" class="tag">{{ tag }}</span>
                  <span v-if="!user.tags.length" class="tag-empty">-</span>
                </div>
              </td>
              <td>
                <span class="status" :class="user.statusClass">{{ user.status }}</span>
              </td>
              <td>
                <div class="row-actions">
                  <button>编辑</button>
                  <button>密码</button>
                  <button>封禁</button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="table-footer">
        <span>显示 1 到 4 条，共 248 条记录</span>
        <div class="pager">
          <button class="active">1</button>
          <button>2</button>
          <button>3</button>
          <span>...</span>
          <button>25</button>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
const users = [
  {
    id: '#SYJ-8492',
    name: 'Echo_Dreamer',
    email: 'echo@example.com',
    date: '2023-10-12',
    tags: ['流行 Pop', '电子 Electronic'],
    status: '活跃',
    statusClass: 'active',
    avatar:
      'https://lh3.googleusercontent.com/aida-public/AB6AXuCJU7TbuV5BU2gXdidRz4N3kizRyPIYGAL3fQW2c3bjbw1BePzTCbEj2H0fIdAZUCo8h1D3FQrzgJZlhyKw4mFLK2DRx-VzU7fY598osq-pQjw-kc3-lLPAIZuSTWe4hTVt72ESUtuiubrlr_Zfavj_8Tqrtztbyv98B_tksKJoJT7geRzu984bje4GJenKaCB5NMLoOeWNQLcn8qLnQhGe9Lu_yQAinNpCTVuqt-JkHGii5sCcJqE4Q2Ul32JUPjNJN-cpc72BBao'
  },
  {
    id: '#SYJ-9104',
    name: 'LoFi_Chill',
    email: 'chill.beats@example.com',
    date: '2023-11-05',
    tags: ['爵士 Jazz', '环境音 Ambient'],
    status: '新增',
    statusClass: 'new',
    avatar: null
  },
  {
    id: '#SYJ-1022',
    name: 'SpamBot_99',
    email: 'spam@unknown.com',
    date: '2023-11-20',
    tags: [],
    status: '已封禁',
    statusClass: 'banned',
    avatar:
      'https://lh3.googleusercontent.com/aida-public/AB6AXuCpRASCa-VBWHKHOwlVEPFT9-zM1lRYPj3hupW47XO4GuIZU_l7CCbCQlqvTla4U8s-S3gJ40ePDNiGdejQOEXV5wU-oBCpdTmhLeSM94fd2B5tN6d4thgwoByXUbrDqAqqAPupDlGvPPpnEUDyN-nntB5dTHcSvWR3Z_meeLy6uAe35ZzXmay1uc6liT8XplvbODDxsKQdYZyWBDLgEvyhe7b7Vv_7TlsKWjr9Hq6mWY2cu_zfW8DY7hGrSwIC62QkpjqGm8tKcps'
  },
  {
    id: '#SYJ-4521',
    name: 'Beat_Junkie',
    email: 'dj.sarah@example.com',
    date: '2022-05-18',
    tags: ['嘻哈 Hip-Hop', 'R&B', 'Funk'],
    status: '活跃',
    statusClass: 'active',
    avatar:
      'https://lh3.googleusercontent.com/aida-public/AB6AXuBC6n5EB3VgtCfEsU01BoUUh0RvEGLaTwUg_7e9V1-71af0kUBuE3nBJFIxjn7y1ECyjL_MgkphsXPVqDkBNVc2n51RejcF7KmqS5vItPTu5NvRSJFIYNz4XLfUxjduMPsD56AciOABZiKdDOh0HNkq1kKgD70RCysOlRkeqQCol6rAhOgGGAZMts8Z-7Zo7Hv91MrlQnk7Bke36OE8RhN3tbuWqlm7E58sn0UTzMIj4MjxrHHEJDDFnk7yhasMMeC1Drr9WZCu1qA'
  }
]
</script>

<style scoped>
.users-page {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.users-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
}

.users-header h2 {
  margin: 0;
  font-size: 28px;
}

.users-header p {
  margin: 6px 0 0;
  color: rgba(25, 28, 29, 0.5);
  font-size: 13px;
}

.header-actions {
  display: flex;
  gap: 12px;
}

.ghost-btn {
  padding: 10px 18px;
  border-radius: 999px;
  border: 1px solid rgba(0, 109, 54, 0.2);
  background: transparent;
  color: var(--color-primary);
  font-weight: 700;
}

.primary-btn {
  padding: 10px 18px;
  border-radius: 999px;
  border: none;
  background: linear-gradient(135deg, var(--color-primary-container), var(--color-tertiary-container));
  color: var(--color-on-primary-container);
  font-weight: 700;
}

.filter-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 14px 18px;
  border-radius: 18px;
  background: var(--color-surface-container-lowest);
  border: 1px solid rgba(109, 123, 109, 0.15);
  flex-wrap: wrap;
  gap: 16px;
}

.filter-left {
  display: flex;
  gap: 12px;
  align-items: center;
  flex-wrap: wrap;
}

.search-box input,
.filter-bar select {
  padding: 10px 14px;
  border-radius: 12px;
  border: none;
  background: var(--color-surface-container-low);
  font-size: 13px;
  min-width: 200px;
}

.filter-actions {
  display: flex;
  gap: 10px;
}

.text-btn {
  background: none;
  border: none;
  font-weight: 700;
  color: rgba(25, 28, 29, 0.6);
  cursor: pointer;
}

.solid-btn {
  padding: 8px 16px;
  border-radius: 12px;
  border: none;
  background: var(--color-surface-container-high);
  font-weight: 700;
  cursor: pointer;
}

.table-card {
  background: var(--color-surface-container-lowest);
  border-radius: 22px;
  border: 1px solid rgba(109, 123, 109, 0.15);
  overflow: hidden;
}

.table-scroll {
  overflow-x: auto;
}

.table-scroll table {
  width: 100%;
  border-collapse: collapse;
  min-width: 900px;
}

th,
 td {
  padding: 14px 16px;
  text-align: left;
  font-size: 12px;
}

thead {
  background: rgba(243, 244, 245, 0.6);
}

th {
  text-transform: uppercase;
  letter-spacing: 0.1em;
  color: rgba(25, 28, 29, 0.5);
}

tbody tr {
  border-top: 1px solid rgba(109, 123, 109, 0.12);
}

tbody tr:hover {
  background: rgba(243, 244, 245, 0.6);
}

tbody tr.banned {
  background: rgba(186, 26, 26, 0.05);
}

.avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background: var(--color-surface-container);
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
  color: var(--color-primary);
  overflow: hidden;
}

.avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.user-info {
  display: flex;
  flex-direction: column;
}

.user-name {
  font-weight: 700;
  font-size: 13px;
}

.user-email {
  font-size: 11px;
  color: rgba(25, 28, 29, 0.5);
}

.mono {
  font-family: ui-monospace, SFMono-Regular, Menlo, monospace;
}

.tag-list {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
}

.tag {
  background: var(--color-surface-container-high);
  padding: 2px 8px;
  border-radius: 999px;
  font-size: 10px;
  font-weight: 700;
}

.tag-empty {
  font-size: 11px;
  color: rgba(25, 28, 29, 0.4);
}

.status {
  display: inline-flex;
  align-items: center;
  padding: 4px 10px;
  border-radius: 999px;
  font-size: 10px;
  font-weight: 700;
}

.status.active {
  background: rgba(0, 109, 54, 0.12);
  color: var(--color-primary);
}

.status.new {
  background: rgba(0, 96, 172, 0.12);
  color: var(--color-secondary);
}

.status.banned {
  background: rgba(186, 26, 26, 0.12);
  color: var(--color-error);
}

.row-actions {
  display: flex;
  gap: 8px;
}

.row-actions button {
  border: none;
  background: transparent;
  color: rgba(25, 28, 29, 0.6);
  font-weight: 700;
  cursor: pointer;
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
  align-items: center;
  gap: 6px;
}

.pager button {
  width: 28px;
  height: 28px;
  border-radius: 8px;
  border: 1px solid rgba(109, 123, 109, 0.2);
  background: transparent;
  font-size: 12px;
  font-weight: 700;
  cursor: pointer;
}

.pager button.active {
  background: var(--color-primary);
  color: var(--color-on-primary);
}
</style>
