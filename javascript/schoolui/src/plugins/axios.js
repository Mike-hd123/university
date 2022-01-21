import axios from 'axios'

// 后端接口地址
axios.defaults.baseURL = '/api'

axios.interceptors.request.use(config => {
    config.headers.Authorization = window.localStorage.getItem('token')
    return config
})
export default (app) => {
    // 全局请求对象
    app.config.globalProperties.$http = axios
    // 文件上传路径
    app.config.globalProperties.$uploadUrl = "/api/upload"
}