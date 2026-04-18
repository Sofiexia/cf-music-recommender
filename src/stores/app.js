import { defineStore } from 'pinia'
export const useAppStore = defineStore('app', {
  state: () => ({ globalLoading: false, toastMessage: '' }),
  actions: {
    showLoading() { this.globalLoading = true },
    hideLoading() { this.globalLoading = false },
    showToast(msg, duration = 2000) {
      this.toastMessage = msg
      setTimeout(() => { this.toastMessage = '' }, duration)
    }
  }
})