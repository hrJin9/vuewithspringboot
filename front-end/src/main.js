import { createApp } from 'vue'
import store from "@/scripts/store"
import App from './App.vue'
import router from '@/scripts/router'

createApp(App)
    .use(router)
    .use(store)
    .mount('#app')
