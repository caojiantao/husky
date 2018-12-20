<template>
  <el-breadcrumb separator-class="el-icon-arrow-right">
    <el-breadcrumb-item
      v-for="item in breadcrumbDatas"
      :key="item.path"
      :to="{path: item.path}"
    >{{item.title}}</el-breadcrumb-item>
  </el-breadcrumb>
</template>

<script>
export default {
  computed: {
    breadcrumbDatas() {
      return this.$store.state.breadcrumbDatas;
    }
  }
};
</script>

