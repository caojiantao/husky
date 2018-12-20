<template>
  <el-submenu v-if="menu.children && menu.children.length > 0" :index="menu.name">
    <template slot="title">
      <i :class="menu.iconClass"/>
      {{menu.name}}
    </template>
    <menu-item v-for="subMenu in menu.children" :key="subMenu.id" :menu="subMenu"/>
  </el-submenu>

  <el-menu-item v-else :index="menu.href">
    <i :class="menu.iconClass"/>
    {{menu.name}}
  </el-menu-item>
</template>

<script>
export default {
  name: "MenuItem",
  props: ["menu"]
};
</script>