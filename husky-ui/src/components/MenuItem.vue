<template>
  <el-submenu v-if="systemMenu.children && systemMenu.children.length > 0" :index="systemMenu.name">
    <template slot="title">
      <i :class="systemMenu.iconClass"/>
      {{systemMenu.name}}
    </template>
    <systemMenu-item v-for="subMenu in systemMenu.children" :key="subMenu.id" :systemMenu="subMenu"/>
  </el-submenu>

  <el-systemMenu-item v-else :index="systemMenu.href">
    <i :class="systemMenu.iconClass"/>
    {{systemMenu.name}}
  </el-systemMenu-item>
</template>

<script>
export default {
  name: "MenuItem",
  props: ["systemMenu"]
};
</script>