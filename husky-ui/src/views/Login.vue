<template>
  <div class="login">
    <el-form ref="loginForm" :model="loginModel" :label-width="formLabelWidth">
      <el-form-item label="账号">
        <el-input v-model="loginModel.username"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input type="password" v-model="loginModel.password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { saveToken } from "@/utils/auth.js";

export default {
  name: "login",
  components: {},
  data: () => {
    return {
      loginModel: {},
      formLabelWidth: "80px"
    };
  },
  methods: {
    submitForm: function() {
      this.$api
        .post("/system/security/user/login", this.loginModel)
        .then(data => {
          let token = data.token;
          let user = data.user;
          // 保存用户登录凭据
          saveToken(token);
          this.$store.commit("setUser", user);
          // 地址跳转（重定向避免动态路由重复加载）
          window.location.href = "/";
        })
        .catch(() => {});
    }
  }
};
</script>
