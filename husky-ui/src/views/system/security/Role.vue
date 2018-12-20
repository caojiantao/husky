<template>
  <div>
    <el-form inline>
      <el-form-item>
        <el-input v-model="query.name" placeholder="请输入角色名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button @click="addRole">添加角色</el-button>
      </el-form-item>
    </el-form>

    <Pagination
      ref="husky-pagination"
      url="/system/role/getRoleByPage"
      :query="query"
      :columns="columns"
    >
      <template slot="operate" slot-scope="scope">
        <el-button size="medium" icon="el-icon-edit" @click="editRole(scope.row)">编辑</el-button>
        <el-button size="medium" icon="el-icon-delete" @click="removeRole(scope.row)">删除</el-button>
      </template>
    </Pagination>

    <el-dialog
      :title="dialogModel.title"
      :visible.sync="dialogModel.visible"
      :close-on-click-modal="false"
    >
      <el-row>
        <el-col :span="12">
          <el-form :model="dialogModel.form" ref="role" label-width="80px">
            <el-form-item label="名称">
              <el-input v-model="dialogModel.form.name"></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="12">
          <el-tree
            :data="treeData"
            ref="tree"
            :props="defaultProps"
            show-checkbox
            node-key="id"
            default-expand-all
            highlight-current
            :expand-on-click-node="false"
          >
            <!-- keys 为什么不能动态修改 -->
          </el-tree>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogModel.visible=false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>


<script>
import Pagination from "@/components/Pagination.vue";
import { getTreeData } from "@/utils/common.js";

export default {
  components: { Pagination },
  data() {
    return {
      query: {
        name: ""
      },
      columns: [
        { prop: "id", label: "ID" },
        { prop: "name", label: "角色名" },
        { label: "操作", slotName: "operate" }
      ],
      defaultProps: {
        id: "id",
        label: "name",
        children: "children"
      },
      dialogModel: this.getInitDialogModel(),
      menus: []
    };
  },
  created: function() {
    // 加载所有菜单树
    this.$axios
      .get("/system/menu/getAllMenus")
      .then(menus => {
        this.menus = menus;
      })
      .catch(() => {});
  },
  mounted: function() {
    this.search();
  },
  methods: {
    search: function() {
      // 调用子组件查询方法
      this.$refs["husky-pagination"].search();
    },
    getInitDialogModel() {
      return {
        title: "",
        visible: false,
        form: {}
      };
    },
    addRole() {
      this.dialogModel = {
        title: "新增角色",
        visible: true,
        form: {}
      };
    },
    editRole(item) {
      // 首先获取角色基本信息
      this.$axios
        .get("/system/role/getRoleWithMenusById?id=" + item.id)
        .then(data => {
          // 展示对话框
          this.dialogModel = {
            title: "编辑角色",
            visible: true,
            form: data
          };
          // 注意放在对话框展示的后面，并且采用下属写法避免undefined
          this.$nextTick(() => {
            let checkedKeys = [];
            if (data.menus) {
              data.menus.forEach(menu => {
                checkedKeys.push(menu.id);
              });
            }
            // 注意只能选中叶子节点
            let leafNodeIds = [];
            checkedKeys.forEach(id => {
              if (!this.menuMap[id]) {
                leafNodeIds.push(id);
              }
            });
            this.$refs.tree.setCheckedKeys(leafNodeIds);
          });
        });
    },
    removeRole(item) {
      this.$confirm("此操作将永久删除该角色, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$axios
            .post("/system/role/deleteRoleById", {
              id: item.id
            })
            .then(() => {
              this.search();
            })
            .catch(() => {});
        })
        .catch(() => {});
    },
    submitForm() {
      this.$refs["role"].validate(valid => {
        if (valid) {
          let $tree = this.$refs.tree;
          let menuIds = $tree
            .getCheckedKeys()
            .concat($tree.getHalfCheckedKeys());
          let menus = [];
          menuIds.forEach(id =>
            menus.push({
              id: id
            })
          );
          this.dialogModel.form["menus"] = menus;
          this.$axios
            .post("/system/role/saveRole", this.dialogModel.form)
            .then(() => {
              this.dialogModel.visible = false;
              this.search();
            })
            .catch(() => {});
        } else {
          return false;
        }
      });
    }
  },
  watch: {
    // 监听弹出框状态
    "dialogModel.visible"(v) {
      if (!v) {
        // 重置表单元素
        this.dialogModel = this.getInitDialogModel();
        // 清除菜单树选中节点
        this.$refs.tree.setCheckedKeys([]);
      }
    }
  },
  computed: {
    treeData: function() {
      let treeData = getTreeData(this.menus);
      if (treeData.length === 0) {
        treeData.push({});
      }
      return treeData;
    },
    // parentId -> menu 映射
    menuMap: function() {
      let obj = {};
      this.menus.forEach(node => {
        // 注意地址引用
        if (!obj[node.parentId]) {
          obj[node.parentId] = [];
        }
        obj[node.parentId].push(node);
      });
      return obj;
    }
  }
};
</script>