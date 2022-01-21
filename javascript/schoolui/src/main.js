import { createApp } from 'vue'
import App from './App.vue'

import router from './router'
import axios from './plugins/axios'
import element from './plugins/element'
import './assets/css/global.css'

const app = createApp(App)
axios(app)
element(app)
app.use(router)
app.mount('#app')
