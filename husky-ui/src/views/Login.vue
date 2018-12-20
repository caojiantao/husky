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
import { saveToken, initMenusAndRoutes } from "@/utils/auth.js";

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
      this.$axios
        .post("/system/user/login", this.loginModel)
        .then(data => {
          let token = data.token;
          let user = data.user;
          // 保存用户登录凭据
          saveToken(token);
          this.$store.commit("setUser", user);
          // 初始化当前用户菜单及路由
          initMenusAndRoutes(user.id, this.$store, this.$axios, this.$router);
          // 地址跳转
          let redirect = this.$route.query.redirect;
          this.$router.push({
            path: redirect ? redirect : "/"
          });
        })
        .catch(() => {});
    }
  }
};
</script>
