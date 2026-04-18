<template>
  <footer class="player-fixed" v-if="currentMusic">
    <div class="player-container">
      <!-- 左侧歌曲信息 + 喜欢按钮 -->
      <div class="player-info">
        <img
          :src="currentMusic.coverUrl"
          class="mini-cover"
          :class="{ rotate: playing }"
          @error="handleCoverError"
        />
        <div class="title-meta">
          <span class="p-song-name">{{ currentMusic.title }}</span>
          <span class="p-song-artist">{{ currentMusic.artist }}</span>
        </div>
        <button
          class="like-btn"
          :class="{ active: currentMusic.isLiked }"
          @click.stop="emit('toggle-like', currentMusic)"
        >
          <span class="heart-icon">{{ currentMusic.isLiked ? '❤️' : '🤍' }}</span>
        </button>
      </div>

      <!-- 中间播放控制 -->
      <div class="player-controls">
        <div class="btn-group">
          <button class="ctrl-icon">⏮</button>
          <button class="main-play" @click="togglePlay">
            {{ isPlaying ? '⏸' : '▶' }}
          </button>
          <button class="ctrl-icon">⏭</button>
        </div>
        <div class="progress-wrapper">
          <div class="progress-bar">
            <div
              class="progress-fill"
              :style="{ width: progressPercent + '%' }"
            ></div>
          </div>
        </div>
      </div>

      <!-- 右侧音量（示意） -->
      <div class="player-extra">
        <span class="vol-icon">🔊</span>
        <div class="vol-bar-simple"></div>
      </div>
    </div>

    <!-- 隐藏的音频元素 -->
    <audio
      ref="audioRef"
      :src="currentMusic?.musicUrl"
      @timeupdate="updateProgress"
      @ended="store.next()"
    ></audio>
  </footer>
</template>

<script setup lang="ts">
import { storeToRefs } from 'pinia'
import { usePlayerStore } from '../stores/player';
import { ref, watch, computed,onMounted } from 'vue';

const store = usePlayerStore()
const defaultCover = new URL('../assets/default-cover.jpg', import.meta.url).href

const { currentMusic, isPlaying } = storeToRefs(store)

onMounted(() => {
  if (audioRef.value) {
    store.setAudioElement(audioRef.value)
  }
})
interface Music {
  musicId: number | string;
  title: string;
  artist: string;
  coverUrl: string;
  musicUrl: string;
  isLiked?: boolean;
}

const props = defineProps<{
  currentMusic: Music | null;
  playing: boolean;
}>();

const emit = defineEmits<{
  (e: 'update:playing', value: boolean): void;
  (e: 'toggle-like', music: Music): void;
}>();

const audioRef = ref<HTMLAudioElement | null>(null);
const currentProgress = ref(0); // 0-100

// 播放/暂停切换（由父组件 v-model:playing 驱动）
const togglePlay = () => {
  if (isPlaying.value) {
    audioRef.value?.pause()
    isPlaying.value = false
  } else {
    audioRef.value?.play()
    isPlaying.value = true
  } // 调用 store 里的方法
}

// 监听播放状态变化，控制 audio 元素
watch(
  () => props.playing,
  async (shouldPlay) => {
    if (!audioRef.value) return;
    if (shouldPlay) {
      try {
        await audioRef.value.play();
      } catch (e) {
        // 播放失败时回退状态
        emit('update:playing', false);
      }
    } else {
      audioRef.value.pause();
    }
  }
);

// 监听歌曲变化，自动加载并播放（如果处于播放状态）
watch(
  () => props.currentMusic?.musicUrl,
  (newUrl, oldUrl) => {
    if (newUrl && newUrl !== oldUrl) {
      currentProgress.value = 0;
      if (audioRef.value) {
        audioRef.value.load();
        if (props.playing) {
          audioRef.value.play().catch(() => emit('update:playing', false));
        }
      }
    }
  },
  { immediate: true }
);

// 更新进度条
const updateProgress = () => {
  if (!audioRef.value) return;
  const { currentTime, duration } = audioRef.value;
  currentProgress.value = duration ? (currentTime / duration) * 100 : 0;
};

const progressPercent = computed(() => currentProgress.value);

// 播放结束自动下一首（这里简单置为暂停，后续可扩展）
const onEnded = () => {
  emit('update:playing', false);
  // 可在此触发下一曲事件，由父组件实现
};

const handleCoverError = (e: Event) => {
  (e.target as HTMLImageElement).src = defaultCover;
};
</script>

<style scoped>
.player-fixed {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background: rgba(255, 255, 255, 0.85);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border-top: 1px solid rgba(0, 0, 0, 0.05);
  height: 88px;
  display: flex;
  align-items: center;
  z-index: 2000;
}
.player-container {
  max-width: 1400px;
  margin: 0 auto;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 40px;
}
.player-info {
  display: flex;
  align-items: center;
  width: 30%;
}
.mini-cover {
  width: 56px;
  height: 56px;
  border-radius: 12px;
  margin-right: 15px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  object-fit: cover;
}
.mini-cover.rotate {
  animation: rotate 20s linear infinite;
}
@keyframes rotate {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}
.title-meta {
  flex: 1;
}
.p-song-name {
  display: block;
  font-weight: 600;
  font-size: 15px;
  color: #1c2438;
}
.p-song-artist {
  font-size: 12px;
  color: #888;
}
.like-btn {
  background: transparent;
  border: none;
  cursor: pointer;
  margin-left: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: transform 0.2s ease;
  outline: none;
}
.heart-icon {
  font-size: 20px;
  filter: grayscale(100%);
  opacity: 0.6;
  transition: all 0.3s ease;
}
.like-btn.active .heart-icon {
  filter: grayscale(0%);
  opacity: 1;
  animation: heart-pop 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}
.like-btn:hover {
  transform: scale(1.2);
}
.like-btn:active {
  transform: scale(0.9);
}
@keyframes heart-pop {
  0% { transform: scale(1); }
  50% { transform: scale(1.5); }
  100% { transform: scale(1.2); }
}
.player-controls {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.btn-group {
  display: flex;
  align-items: center;
}
.ctrl-icon {
  background: transparent;
  border: none;
  font-size: 18px;
  cursor: pointer;
  color: #515a6e;
  padding: 8px;
}
.main-play {
  background: #2d8cf0;
  color: white;
  border: none;
  width: 44px;
  height: 44px;
  border-radius: 50%;
  font-size: 20px;
  cursor: pointer;
  margin: 0 20px;
  transition: transform 0.2s;
}
.main-play:hover {
  transform: scale(1.1);
}
.progress-wrapper {
  width: 100%;
  max-width: 450px;
  margin-top: 8px;
}
.progress-bar {
  width: 100%;
  height: 4px;
  background: #eee;
  border-radius: 2px;
}
.progress-fill {
  height: 100%;
  background: #2d8cf0;
  border-radius: 2px;
  transition: width 0.1s linear;
}
.player-extra {
  display: flex;
  align-items: center;
  gap: 10px;
  color: #808695;
}
.vol-bar-simple {
  width: 80px;
  height: 4px;
  background: #e8eaec;
  border-radius: 2px;
}
</style>