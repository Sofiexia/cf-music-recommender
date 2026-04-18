import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import request from '../api/request'

export const usePlayerStore = defineStore('player', () => {
  // ---------- 状态 ----------
  const playlist = ref([])               // 当前播放列表
  const currentIndex = ref(-1)           // 当前播放索引
  const isPlaying = ref(false)           // 播放状态
  const audioElement = ref(null)         // audio 元素引用（由组件设置）

  // 当前播放的音乐（计算属性）
  const currentMusic = computed(() => {
    return playlist.value[currentIndex.value] || null
  })

  // ---------- 辅助方法 ----------
  const setAudioElement = (audio) => {
    audioElement.value = audio
  }

  // 播放指定音乐（如果不在列表中则替换整个列表）
  const playMusic = (music) => {
    if (!music) return

    const musicToPlay = {
      ...music,
      url: music.url || music.musicUrl // 兼容不同接口的字段名
    }
    const existingIndex = playlist.value.findIndex(item => item.musicId === music.musicId)
    if (existingIndex !== -1) {
      currentIndex.value = existingIndex
    } else {
      // 新歌：添加到列表并播放
      playlist.value.push(music)
      currentIndex.value = playlist.value.length - 1
    }

    // 确保喜欢的字段存在
    if (currentMusic.value && currentMusic.value.isLiked === undefined) {
      currentMusic.value.isLiked = false
    }

    isPlaying.value = true
    // 记录播放行为（可选）
    request.post('/user/actions', { musicId: music.musicId, actionType: 'play' }).catch(() => {})

    // 实际播放由组件监听 currentMusic 变化后执行
    setTimeout(() => {
      audioElement.value?.play()
    }, 50)
  }

  // 播放/暂停切换
  const togglePlay = () => {
    if (!currentMusic.value) return
    if (isPlaying.value) {
      audioElement.value?.pause()
    } else {
      audioElement.value?.play()
    }
    isPlaying.value = !isPlaying.value
  }

  // 上一首
  const prev = () => {
    if (playlist.value.length === 0) return
    const newIndex = (currentIndex.value - 1 + playlist.value.length) % playlist.value.length
    currentIndex.value = newIndex
    isPlaying.value = true
    setTimeout(() => audioElement.value?.play(), 50)
  }

  // 下一首
  const next = () => {
    if (playlist.value.length === 0) return
    const newIndex = (currentIndex.value + 1) % playlist.value.length
    currentIndex.value = newIndex
    isPlaying.value = true
    setTimeout(() => audioElement.value?.play(), 50)
  }

  // 喜欢/取消喜欢（带乐观更新）
  const toggleLike = async (music) => {
    if (!music) return
    const originalStatus = music.isLiked
    music.isLiked = !music.isLiked

    try {
      await request.post('/user/actions', {
        musicId: music.musicId,
        actionType: music.isLiked ? 'like' : 'unlike',
        duration: 0
      })
    } catch (err) {
      // 失败回滚
      music.isLiked = originalStatus
      console.error('喜欢操作失败', err)
    }
  }

  // 设置播放列表（例如从推荐列表批量添加）
  const setPlaylist = (newList, startIndex = 0) => {
    playlist.value = newList.map(item => ({
      ...item,
      isLiked: item.isLiked ?? false
    }))
    currentIndex.value = startIndex
    isPlaying.value = true
    setTimeout(() => audioElement.value?.play(), 50)
  }

  // 清空播放器（退出登录时）
  const reset = () => {
    playlist.value = []
    currentIndex.value = -1
    isPlaying.value = false
    audioElement.value?.pause()
  }

  return {
    // state
    playlist,
    currentIndex,
    isPlaying,
    audioElement,
    // getters
    currentMusic,
    // actions
    setAudioElement,
    playMusic,
    togglePlay,
    prev,
    next,
    toggleLike,
    setPlaylist,
    reset
  }
})