import Vue from 'vue'
import './plugins/axios'
import './plugins/element.js'
import App from './App.vue'
import store from './store'
import router from './router'

Vue.config.productionTip = false

import {
  getToken,
  initMenusAndRoutes
} from './utils/auth';

new Vue({
  store,
  router,
  render: h => h(App),
  created: function () {
    let token = getToken();
    if (token) {
      // 保存user至vuex
      this.$axios.get('/system/user/getCurrentUser').then(user => {
        store.commit('setUser', user);
        // 动态创建导航菜单以及对应路由
        initMenusAndRoutes(user.id, store, this.$axios, router);
      })
    }
  }
}).$mount('#app')