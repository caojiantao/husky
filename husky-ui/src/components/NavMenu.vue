<template>
  <el-aside>
    <el-systemMenu :default-active="activeIndex" router>
      <systemMenu-item v-for="systemMenu in treeData" :key="systemMenu.id" :systemMenu="systemMenu"/>
    </el-systemMenu>
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
      let nodes = getTreeData(this.$store.state.systemMenus);
      if (nodes.length === 0) {
        nodes.push({});
      }
      return nodes;
    }
  }
};
</script>