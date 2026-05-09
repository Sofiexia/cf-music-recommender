import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import request from '../api/request'
import { usePlayerStore } from './player'
import { storeToRefs } from 'pinia'

export function useHome() {
  const router = useRouter()
  const store = usePlayerStore()
  const { isPlaying, currentMusic } = storeToRefs(store)

  const recommendations = ref([])
  const loading = ref(false)
  const searchKeyword = ref('')
  const isSearching = ref(false)
  const defaultCover =
    'https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/1d/1b/f9/1d1bf9b1-44c6-9a6c-6ffb-c158488c06ce/26UMGIM39303.rgb.jpg/600x600bb.jpg'

  const categories = [
    {
      label: '排行榜',
      color: '#2d8cf0',
      bg: 'rgba(45,140,240,0.08)',
      icon: `<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" width="20" height="20"><polyline points="22 7 13.5 15.5 8.5 10.5 2 17"/><polyline points="16 7 22 7 22 13"/></svg>`,
    },
    {
      label: '歌单广场',
      color: '#7c4dff',
      bg: 'rgba(124,77,255,0.08)',
      icon: `<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" width="20" height="20"><path d="M9 18V5l12-2v13"/><circle cx="6" cy="18" r="3"/><circle cx="18" cy="16" r="3"/></svg>`,
    },
    {
      label: '热门歌手',
      color: '#00bcd4',
      bg: 'rgba(0,188,212,0.08)',
      icon: `<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" width="20" height="20"><path d="M12 2a3 3 0 0 0-3 3v7a3 3 0 0 0 6 0V5a3 3 0 0 0-3-3z"/><path d="M19 10v2a7 7 0 0 1-14 0v-2"/><line x1="12" x2="12" y1="19" y2="22"/></svg>`,
    },
    {
      label: '我喜欢的',
      color: '#f44336',
      bg: 'rgba(244,67,54,0.08)',
      icon: `<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" width="20" height="20"><path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"/></svg>`,
    },
  ]

  const charts = [
    { title: '飙升榜', items: ['怪我过分美丽', '晴天', '搁浅', '夜曲'] },
    { title: '新歌榜', items: ['再见', '平凡之路', '消愁', '告白气球'] },
    { title: '原创榜', items: ['小夜曲', '田野', '风的方向', '海之歌'] },
  ]

  // ===== API =====
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

  const clearSearch = () => {
    searchKeyword.value = ''
    isSearching.value = false
    fetchRecommendations()
  }

  // ===== Player =====
  const playMusic = (music) => {
    store.playMusic({ ...music, isLiked: music.isLiked || false })
  }

  const toggleLike = (music) => store.toggleLike(music)

  const startPersonalRadio = () => {
    if (recommendations.value.length > 0) {
      playMusic(recommendations.value[0])
    } else {
      alert('正在为您准备个性化电台...')
    }
  }

  const handleLearnMore = () => {
    console.log('了解更多')
  }

  // ===== Auth =====
  const logout = () => {
    localStorage.removeItem('token')
    router.push('/login')
  }

  onMounted(fetchRecommendations)

  return {
    // state
    recommendations,
    loading,
    searchKeyword,
    isSearching,
    defaultCover,
    isPlaying,
    currentMusic,
    categories,
    charts,
    // methods
    handleSearch,
    clearSearch,
    playMusic,
    toggleLike,
    startPersonalRadio,
    handleLearnMore,
    logout,
  }
}