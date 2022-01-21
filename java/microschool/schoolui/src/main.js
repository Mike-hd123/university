import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'

import './assets/css/global.css'
import './plugins/element.js'


Vue.config.productionTip = false

// 后端接口地址
axios.defaults.baseURL = '/api'
// 文件上传路径
Vue.prototype.$uploadUrl = axios.defaults.baseURL + "/upload"

axios.interceptors.request.use(config => {
    config.headers.Authorization = window.localStorage.getItem('token')
    return config
})
Vue.prototype.$http = axios

new Vue({
    router,
    render: h => h(App)
}).$mount('#app')



