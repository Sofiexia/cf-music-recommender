import { defineStore } from 'pinia'
import request from '../api/request'; 

// 定义登录接口
export const loginApi = (data) => {
  return request({
    url: '/user/login', // 对应你后端的登录接口路径
    method: 'post',
    data: data
  });
};

export const useUserStore = defineStore('user', {
  state: () => ({ token: '' }),
  actions: {
    setToken(token) {
      this.token = token
      if (token) localStorage.setItem('token', token)
      else localStorage.removeItem('token')
    },
    loadToken() {
      const token = localStorage.getItem('token')
      if (token) this.token = token
    }
  }
})