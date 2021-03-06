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

const api = axios.create(config);

api.interceptors.request.use(
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

api.interceptors.response.use(
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
    $api: {
      get() {
        return api;
      }
    },
  });
};

Vue.use(Plugin)

export default Plugin;

export {
  api
}