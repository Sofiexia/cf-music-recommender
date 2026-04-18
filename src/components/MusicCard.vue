<template>
  <div class="music-item" @click="emit('play', music)">
    <div class="music-cover-wrapper">
      <img :src="coverUrl" class="cover-img" @error="handleImgError" />
      <div class="play-hint">▶ 立即播放</div>
    </div>
    <div class="music-details">
      <div class="song-title">{{ music.title }}</div>
      <div class="song-artist">{{ music.artist }}</div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
const defaultCover = new URL('../assets/default-cover.jpg', import.meta.url).href
interface MusicItem {
  musicId: number | string
  title: string
  artist: string
  coverUrl?: string
  [key: string]: any
}


const props = defineProps<{
  music: MusicItem
  defaultCover: string
}>()

const emit = defineEmits<{
  (e: 'play', music: MusicItem): void
}>()

const coverUrl = computed(() => props.music.coverUrl || props.defaultCover)

const handleImgError = (e: Event) => {
  (e.target as HTMLImageElement).src = props.defaultCover
}
</script>

<style scoped>
.music-item {
  background: #fff;
  border-radius: 16px;
  overflow: hidden;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  cursor: pointer;
  box-shadow: 0 4px 12px rgba(0,0,0,0.03);
  border: 1px solid rgba(0,0,0,0.02);
}
.music-item:hover {
  transform: translateY(-6px);
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
  border-color: rgba(45, 140, 240, 0.1);
}
.music-cover-wrapper {
  position: relative;
  aspect-ratio: 1;
  overflow: hidden;
  border-radius: 12px;
}
.cover-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
}
.music-item:hover .cover-img {
  transform: scale(1.05);
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
}
.play-hint {
  position: absolute;
  bottom: 8px;
  right: 8px;
  background: rgba(0,0,0,0.6);
  color: white;
  padding: 4px 10px;
  border-radius: 20px;
  font-size: 12px;
  opacity: 0;
  transition: opacity 0.3s;
  backdrop-filter: blur(4px);
}
.music-item:hover .play-hint {
  opacity: 1;
}
.music-details {
  padding: 18px;
}
.song-title {
  font-weight: 600;
  color: #1c2438;
  margin-bottom: 6px;
}
.song-title::after {
  content: 'HQ';
  font-size: 10px;
  color: #2d8cf0;
  border: 1px solid #2d8cf0;
  border-radius: 3px;
  padding: 0 2px;
  margin-left: 8px;
  vertical-align: middle;
}
.song-artist {
  color: #888;
  font-size: 13px;
}
</style>