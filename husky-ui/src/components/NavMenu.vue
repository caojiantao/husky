<template>
  <el-aside>
    <el-menu :default-active="activeIndex" router>
      <menu-item v-for="menu in treeData" :key="menu.id" :menu="menu"/>
    </el-menu>
  </el-aside>
</template>

<script>
import MenuItem from "@/components/MenuItem.vue";
import { getTreeData } from "@/utils/common.js";

export default {
  components: { MenuItem },
  data() {
    return {
      activeIndex: this.$route.path
    };
  },
  computed: {
    // 计算菜单树数据，坑爹，el-tree必须有元素
    treeData: function() {
      let nodes = getTreeData(this.$store.state.menus);
      if (nodes.length === 0) {
        nodes.push({});
      }
      return nodes;
    }
  }
};
</script>