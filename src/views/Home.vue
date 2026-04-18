<template>
  <div class="music-app light-theme">
    <Navbar
      v-model:searchKeyword="searchKeyword"
      @search="handleSearch"
      @clear-search="clearSearch"
      @logout="logout"
    />

<div class="main-body-container">
      <Sidebar />

      <main class="content-wrapper">
        <Banner @start-radio="startPersonalRadio" @learn-more="handleLearnMore" />

        <div class="page-header">
          <h2 class="section-title">
            {{ isSearching ? `"${searchKeyword}" 的搜索结果` : '为你推荐' }}
          </h2>
          <button v-if="isSearching" class="text-link" @click="clearSearch">返回推荐</button>
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

import Navbar from '../components/Navbar.vue'
import Banner from '../components/Banner.vue'
import MusicPlayer from '../components/MusicPlayer.vue'
import Sidebar from '../components/Sidebar.vue'
import MusicCard from '../components/MusicCard.vue'

const router = useRouter()
const store = usePlayerStore()
const { isPlaying, currentMusic } = storeToRefs(store)

const recommendations = ref<any[]>([])
const loading = ref(false)
const searchKeyword = ref('')
const isSearching = ref(false)
const defaultCover = 'https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/1d/1b/f9/1d1bf9b1-44c6-9a6c-6ffb-c158488c06ce/26UMGIM39303.rgb.jpg/600x600bb.jpg'

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