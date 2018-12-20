<template>
  <div class="home">
    <el-container>
      <el-header class="home-header">
        <strong>Husky</strong>
      </el-header>
      <el-container>
        <nav-menu class="home-aside"/>
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

export default {
  name: "home",
  components: { NavMenu, Breadcrumb }
};
</script>
