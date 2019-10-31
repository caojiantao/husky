<template>
  <div class="home">
    <el-container>
      <el-header class="home-header">
        <strong>Husky</strong>
        <div class="systemUser-info">
          <span class="nickname">{{userInfo.nickname}}</span>
          <span class="logout" @click="logout">退出</span>
        </div>
      </el-header>
      <el-container>
        <nav-systemMenu class="home-aside"/>
        <el-main>
          <Breadcrumb/>
          <router-view style="margin-top:20px;"/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style>
body {
  margin: 0;
}

.home-header {
  background-color: rgb(64, 158, 255);
  color: #fff;
  line-height: 60px;
}

.home-header .systemUser-info {
  float: right;
}

.home-header .systemUser-info span {
  margin-left: 20px;
}

.home-header .systemUser-info .logout {
  cursor: pointer;
}




.home-aside {
  width: 300px;
  background-color: #d3dce6;
  color: #333;
  line-height: 200px;
}
</style>


<script>
import NavMenu from "@/components/NavMenu.vue";
import Breadcrumb from "@/components/Breadcrumb.vue";
import { clearToken } from "@/utils/auth.js"

export default {
  name: "home",
  components: { NavMenu, Breadcrumb },
  data () {
    return {
      userInfo: {
        nickname: ''
      }
    };
  },
  created () {
    let systemUser = this.$store.state.systemUser;
    if (systemUser) {
      this.userInfo = systemUser;
    }
  },
  methods: {
    logout () {
      this.$confirm("确定退出吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
      .then(() => {
        clearToken();
        window.location.href = "/";
      })
      .catch(() => {});
    }
  }
};
</script>
