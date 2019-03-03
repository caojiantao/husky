"use strict";

import Vue from 'vue';
import axios from "axios";
import {
  Message
} from 'element-ui'
import {
  getToken
} from '@/utils/auth.js'

// husky项目接口
let config = {
  baseURL: process.env.VUE_APP_baseURL
  // timeout: 60 * 1000, // Timeout
  // withCredentials: true, // Check cross-site Access-Control
};

const _axios = axios.create(config);

_axios.interceptors.request.use(
  function (config) {
    // token赋值
    let token = getToken();
    if (token) {
      config.headers['X-Token'] = token;
    }
    return config;
  },
  function (error) {
    return Promise.reject(error);
  }
);

_axios.interceptors.response.use(
  function (response) {
    let responseData = response.data
    if (responseData.code === 0) {
      return responseData.data
    } else {
      Message({
        message: responseData.msg,
        type: 'error'
      })
      return Promise.reject();
    }
  },
  function (error) {
    // Do something with response error
    return Promise.reject(error);
  }
);

Plugin.install = function (Vue) {
  Object.defineProperties(Vue.prototype, {
    $axios: {
      get() {
        return _axios;
      }
    },
  });
};

Vue.use(Plugin)

export default Plugin;

export {
  _axios
}