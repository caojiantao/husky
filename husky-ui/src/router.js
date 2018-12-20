import Vue from 'vue'
import Router from 'vue-router'
import {
  getToken,
  getBreadcrumbDatas
} from '@/utils/auth.js'
import {
  getItemByKey
} from '@/utils/common.js'
import store from './store'

Vue.use(Router)


const router = new Router({
  routes: [{
    path: '/login',
    name: 'Login',
    meta: {
      open: true
    },
    component: () => import('@/views/Login.vue')
  }]
})

// 路由登录拦截
router.beforeEach((to, from, next) => {
  if (to.meta.open) {
    next();
  } else {
    if (getToken()) {
      // 初始化面包屑
      let menus = store.state.menus
      let curMenu = getItemByKey(to.path, menus, 'href');
      if (curMenu) {
        let breadcrumbDatas = getBreadcrumbDatas(curMenu.id, menus);
        store.commit('setBreadcrumbDatas', breadcrumbDatas);
      }
      next();
    } else {
      next({
        path: '/login',
        query: {
          redirect: to.fullPath
        }
      })
    }
  }
})

export default router