import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: null,
    menus: [],
    breadcrumbDatas: []
  },
  mutations: {
    setUser(state, user) {
      state.user = user;
    },
    setMenus(state, menus) {
      state.menus = menus;
    },
    setBreadcrumbDatas(state, datas) {
      state.breadcrumbDatas = datas
    }
  },
  actions: {

  }
})