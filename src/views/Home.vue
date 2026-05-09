<template>
  <div class="home-shell">
    <nav class="top-navbar">
      <div class="nav-inner">
        <div class="nav-left">
          <h1 class="nav-title" @click="clearSearch">基于协同过滤的智能音乐推荐系统</h1>
          <div class="nav-links">
            <button class="nav-link active">首页</button>
            <button class="nav-link">排行榜</button>
            <button class="nav-link">歌单</button>
            <button class="nav-link">歌手</button>
          </div>
        </div>
        <div class="nav-right">
          <div class="nav-search" :class="{ focused: isSearchFocused }">
            <Search class="search-icon" />
            <input
              v-model="searchKeyword"
              placeholder="搜索你想听的..."
              @keyup.enter="handleSearch"
              @focus="isSearchFocused = true"
              @blur="isSearchFocused = false"
            />
            <button v-if="searchKeyword" class="clear-btn" @click="clearSearch">
              <X class="clear-icon" />
            </button>
          </div>
          <button class="notify-btn">
            <span class="notify-dot"></span>
            <Bell class="notify-icon" />
          </button>
          <button class="avatar-btn" @click="logout">
            <User class="avatar-icon" />
          </button>
        </div>
      </div>
    </nav>

    <div class="main-body-container">
      <main class="content-wrapper">
        <Banner @start-radio="startPersonalRadio" @learn-more="handleLearnMore" />

        <div class="page-header">
          <div>
            <h2 class="section-title">
              {{ isSearching ? `"${searchKeyword}" 的搜索结果` : '个性化推荐' }}
            </h2>
            <p class="section-subtitle">基于你的听歌风格实时生成</p>
          </div>
          <div class="header-actions">
            <button v-if="isSearching" class="text-link" @click="clearSearch">返回推荐</button>
            <button v-else class="more-link">
              更多推荐
              <ChevronRight class="more-icon" />
            </button>
          </div>
        </div>

        <div v-if="loading" class="loading-area">数据加载中...</div>

        <div v-else class="music-grid">
          <MusicCard
            v-for="item in recommendations"
            :key="item.musicId"
            :music="item"
            :defaultCover="defaultCover"
            @play="playMusic"
          />
        </div>

        <section class="quick-tags">
          <button
            v-for="tag in quickTags"
            :key="tag.label"
            class="quick-tag"
            :class="tag.color"
          >
            <span class="tag-icon">{{ tag.icon }}</span>
            {{ tag.label }}
          </button>
        </section>

        <section class="chart-grid">
          <div v-for="chart in charts" :key="chart.title" class="chart-card">
            <div class="chart-header">
              <h3>{{ chart.title }}</h3>
              <span class="chart-link">更多</span>
            </div>
            <div class="chart-list">
              <div v-for="(song, idx) in chart.items" :key="song" class="chart-item">
                <span class="chart-rank" :class="{ highlight: idx < 3 }">{{ idx + 1 }}</span>
                <div class="chart-cover"></div>
                <div class="chart-meta">
                  <p class="chart-title">{{ song }}</p>
                  <p class="chart-artist">周杰伦 / 陈奕迅</p>
                </div>
                <button class="chart-play">Play</button>
              </div>
            </div>
          </div>
        </section>
      </main>
    </div>

    <MusicPlayer
      v-model:playing="isPlaying"
      :currentMusic="currentMusic"
      @toggle-like="toggleLike"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import request from '../api/request'
import { usePlayerStore } from '../stores/player'
import { storeToRefs } from 'pinia' 

import Banner from '../components/Banner.vue'
import MusicPlayer from '../components/MusicPlayer.vue'
import MusicCard from '../components/MusicCard.vue'
import { Bell, ChevronRight, Search, User, X } from 'lucide-vue-next'

const router = useRouter()
const store = usePlayerStore()
const { isPlaying, currentMusic } = storeToRefs(store)

const recommendations = ref<any[]>([])
const loading = ref(false)
const searchKeyword = ref('')
const isSearching = ref(false)
const isSearchFocused = ref(false)
const defaultCover = 'https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/1d/1b/f9/1d1bf9b1-44c6-9a6c-6ffb-c158488c06ce/26UMGIM39303.rgb.jpg/600x600bb.jpg'

const quickTags = ref([
  { label: '排行榜', icon: 'Top', color: 'tag-primary' },
  { label: '歌单广场', icon: 'Mix', color: 'tag-secondary' },
  { label: '热门歌手', icon: 'Mic', color: 'tag-tertiary' },
  { label: '我喜欢的', icon: 'Fav', color: 'tag-error' }
])

const charts = ref([
  { title: '飙升榜', items: ['怪我过分美丽', '晴天', '搁浅', '夜曲'] },
  { title: '新歌榜', items: ['再见', '平凡之路', '消愁', '告白气球'] },
  { title: '原创榜', items: ['小夜曲', '田野', '风的方向', '海之歌'] }
])

// 获取推荐
const fetchRecommendations = async () => {
  loading.value = true
  try {
    const res = await request.get('/recommend/personal?page=1&size=12')
    if (res.data.code === 200) {
      recommendations.value = res.data.data.records || []
    }
  } catch (err) {
    console.error('推荐获取失败', err)
  } finally {
    loading.value = false
  }
}

//搜索逻辑
const handleSearch = async () => {
  if (!searchKeyword.value.trim()) return
  loading.value = true
  isSearching.value = true
  try {
    const res = await request.get(
      `/music/search?keyword=${searchKeyword.value}&page=1&size=20`
    )
    if (res.data.code === 200) {
      recommendations.value = res.data.data.records || []
    }
  } finally {
    loading.value = false
  }
}

//清除搜索
const clearSearch = () => {
  searchKeyword.value = ''
  isSearching.value = false
  fetchRecommendations()
}

const toggleLike = (music: any) => {
  store.toggleLike(music)
}

const logout = () => {
  localStorage.removeItem('token')
  router.push('/login')
}

const playMusic = (music: any) => {
  store.playMusic({ ...music, isLiked: music.isLiked || false })
}
const handleImgError = (e: Event) => {
  ;(e.target as HTMLImageElement).src = defaultCover
}

const startPersonalRadio = () => {
  if (recommendations.value.length > 0) {
    playMusic(recommendations.value[0])
  } else {
    alert('正在为您准备个性化电台...')
  }
}

const handleLearnMore = () => {
  // 可跳转到关于页面或执行其他逻辑
  console.log('了解更多')
  // router.push('/about')
}

onMounted(fetchRecommendations)
</script>

<style scoped>
@import './Home.css';
</style>