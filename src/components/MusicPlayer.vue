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
          <Heart
            class="heart-icon"
            :fill="currentMusic.isLiked ? 'currentColor' : 'none'"
          />
        </button>
      </div>

      <!-- 中间播放控制 -->
      <div class="player-controls">
        <div class="btn-group">
          <button class="ctrl-icon" aria-label="上一首">
            <SkipBack class="ctrl-icon-svg" />
          </button>
          <button class="main-play" @click="togglePlay">
            <Pause v-if="isPlaying" class="main-icon" />
            <Play v-else class="main-icon play-icon" />
          </button>
          <button class="ctrl-icon" aria-label="下一首">
            <SkipForward class="ctrl-icon-svg" />
          </button>
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
        <Volume2 class="vol-icon" />
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
import { Heart, Pause, Play, SkipBack, SkipForward, Volume2 } from 'lucide-vue-next'

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
  padding: 16px 20px 24px;
  display: flex;
  align-items: flex-end;
  z-index: 2000;
  pointer-events: none;
}
.player-container {
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px 20px;
  height: 96px;
  background: rgba(255, 255, 255, 0.7);
  border-radius: 24px;
  border: 1px solid rgba(255, 255, 255, 0.5);
  box-shadow: 0 -8px 30px rgba(0, 0, 0, 0.08);
  backdrop-filter: blur(24px);
  -webkit-backdrop-filter: blur(24px);
  pointer-events: auto;
}
.player-info {
  display: flex;
  align-items: center;
  width: 30%;
}
.mini-cover {
  width: 54px;
  height: 54px;
  border-radius: 14px;
  margin-right: 15px;
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.1);
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
  font-size: 14px;
  color: var(--color-on-surface);
}
.p-song-artist {
  font-size: 11px;
  color: rgba(25, 28, 29, 0.55);
}
.like-btn {
  background: transparent;
  border: none;
  cursor: pointer;
  margin-left: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 32px;
  height: 32px;
  border-radius: 50%;
  color: rgba(186, 26, 26, 0.55);
  transition: transform 0.2s ease, color 0.2s ease, background 0.2s ease;
  outline: none;
}
.heart-icon {
  width: 18px;
  height: 18px;
  transition: all 0.3s ease;
}
.like-btn.active .heart-icon {
  color: var(--color-error);
  animation: heart-pop 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}
.like-btn:hover {
  transform: scale(1.05);
  background: rgba(186, 26, 26, 0.12);
  color: var(--color-error);
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
  color: rgba(25, 28, 29, 0.7);
  padding: 6px;
  border-radius: 50%;
  transition: color 0.2s ease, transform 0.2s ease;
}
.ctrl-icon:hover {
  color: var(--color-primary);
  transform: translateY(-1px);
}
.ctrl-icon-svg {
  width: 22px;
  height: 22px;
}
.main-play {
  background: var(--color-primary);
  color: var(--color-on-primary);
  border: none;
  width: 48px;
  height: 48px;
  border-radius: 50%;
  font-size: 20px;
  cursor: pointer;
  margin: 0 20px;
  transition: transform 0.2s;
  box-shadow: 0 4px 12px rgba(74, 222, 128, 0.3);
}
.main-icon {
  width: 22px;
  height: 22px;
}
.play-icon {
  transform: translateX(1px);
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
  background: var(--color-surface-container-high);
  border-radius: 2px;
}
.progress-fill {
  height: 100%;
  background: linear-gradient(90deg, var(--color-primary-container), var(--color-secondary-container));
  border-radius: 2px;
  transition: width 0.1s linear;
}
.player-extra {
  display: flex;
  align-items: center;
  gap: 10px;
  color: rgba(25, 28, 29, 0.5);
}

.vol-icon {
  width: 18px;
  height: 18px;
}
.vol-bar-simple {
  width: 80px;
  height: 4px;
  background: var(--color-surface-container-high);
  border-radius: 2px;
}
</style>