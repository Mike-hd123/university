import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  server: {
    proxy: {
      // 字符串简写写法
      '/api': 'http://localhost:8081'
    }
  },
  plugins: [vue()]
})
