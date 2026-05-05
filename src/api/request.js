import axios from 'axios';
import router from '../router';

const request = axios.create({
  baseURL: 'http://localhost:8080/api', // 确保指向你后端的端口
  timeout: 5000
});

// 请求拦截器：自动注入 Token
request.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers['Authorization'] = `Bearer ${token}`; // 适配后端 Jwt 校验
  }
  return config;
}, error => {
  return Promise.reject(error);
});

request.interceptors.response.use(
  response => {
    // 如果返回 200，直接返回数据
    return response;
  },
  error => {
    // 如果请求出错（比如 401, 403, 500）
    if (error.response) {
      switch (error.response.status) {
        case 401:
          // Token 过期或无效
          console.error('身份验证失败，请重新登录');
          
          // 清理本地存储的废弃数据
          localStorage.removeItem('token');
          localStorage.removeItem('userType');
          localStorage.removeItem('userInfo');
          
          // 强制跳转回登录页
          router.push({
            path:'/login',
            query: { redirect: router.currentRoute.value.fullPath}
          });
          break;
        
        case 403:
          console.error('权限不足，禁止访问');
          break;
          
        case 500:
          console.error('服务器内部错误');
          break;
      }
    } else {
      console.error('网络连接异常，请检查后端服务');
    }
    
    return Promise.reject(error);
  }
);
export default request;
