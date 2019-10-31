<template>
  <div class="husky-systemMenu">
    <el-form inline>
      <el-form-item>
        <el-button @click="dialogModel.visible=true">添加顶级菜单</el-button>
      </el-form-item>
    </el-form>

    <el-tree
      :data="treeData"
      :props="defaultProps"
      node-key="id"
      default-expand-all
      highlight-current
      :expand-on-click-node="false"
      :render-content="renderContent"
    ></el-tree>

    <el-dialog :title="dialogModel.title" :visible.sync="dialogModel.visible">
      <el-form :model="dialogModel.form" ref="systemMenu" label-width="80px">
        <el-form-item label="路径">
          <el-input v-model="dialogModel.form.parentId" style="display:none;"></el-input>
          <el-input v-model="dialogModel.form.nodePath" disabled></el-input>
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="dialogModel.form.name"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="dialogModel.form.href"></el-input>
        </el-form-item>
        <el-form-item label="iconClass">
          <el-input v-model="dialogModel.form.iconClass"></el-input>
        </el-form-item>
        <el-form-item label="组件路径">
          <el-input v-model="dialogModel.form.componentPath"></el-input>
        </el-form-item>
        <el-form-item label="排序">
          <el-input v-model="dialogModel.form.order"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogModel.visible=false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style>
.el-tree-node__content {
  height: 30px;
}
.icon-operate {
  margin-left: 10px;
}
</style>

<script>
import { getTreeData } from "@/utils/common.js";

export default {
  created() {
    this.getMenus();
  },
  data() {
    return {
      systemMenus: [],
      defaultProps: {
        id: "id",
        label: "name",
        children: "children"
      },
      dialogModel: this.getInitDialogModel()
    };
  },
  methods: {
    getInitDialogModel() {
      return {
        title: "",
        visible: false,
        form: {
          id: 0,
          parentId: 0,
          name: "",
          href: "",
          iconClass: "",
          componentPath: "",
          order: 0,
          nodePath: "/"
        }
      };
    },
    getMenus() {
      this.$api
        .get("/system/security/systemMenu/getAllMenus")
        .then(systemMenus => {
          this.systemMenus = systemMenus;
        })
        .catch(() => {});
    },
    // 提交表单请求
    submitForm() {
      this.$refs["systemMenu"].validate(valid => {
        if (valid) {
          this.$api
            .post("/system/security/systemMenu/saveMenu", this.dialogModel.form)
            .then(() => {
              this.dialogModel.visible = false;
              this.getMenus();
            })
            .catch(() => {});
        } else {
          return false;
        }
      });
    },
    // 渲染节点，加上操作
    renderContent(h, { node, data }) {
      return h("span", [
        h("span", node.label),
        h("span", [
          h("i", {
            attrs: {
              class: "el-icon-circle-plus-outline icon-operate"
            },
            on: {
              click: () => {
                Object.assign(this.dialogModel, {
                  title: "新增菜单",
                  visible: true,
                  form: {
                    parentId: data.id,
                    nodePath: this.getCurrentPath(node)
                  }
                });
              }
            }
          }),
          h("i", {
            attrs: {
              class: "el-icon-edit icon-operate"
            },
            on: {
              click: () => {
                this.$api
                  .get("/system/security/systemMenu/getMenuById", {
                    params: {
                      id: data.id
                    }
                  })
                  .then(systemMenu => {
                    Object.assign(this.dialogModel, {
                      title: "编辑菜单",
                      visible: true,
                      form: systemMenu
                    });
                    this.$set(
                      this.dialogModel.form,
                      "nodePath",
                      this.getCurrentPath(node.parent)
                    );
                  });
              }
            }
          }),
          h("i", {
            attrs: {
              class: "el-icon-delete icon-operate"
            },
            on: {
              click: () => {
                this.$confirm("此操作将永久删除该菜单, 是否继续?", "提示", {
                  confirmButtonText: "确定",
                  cancelButtonText: "取消",
                  type: "warning"
                })
                  .then(() => {
                    this.node = node;
                    this.removeMenu(data.id);
                  })
                  .catch(() => {});
              }
            }
          })
        ])
      ]);
    },
    getCurrentPath(node) {
      let nodePath = "";
      while (node && node.level) {
        nodePath = "/" + node.label + nodePath;
        node = node.parent;
      }
      return nodePath + "/";
    },
    // 删除菜单
    removeMenu(id) {
      this.$api
        .post("/system/security/systemMenu/deleteMenuById", {
          id: id
        })
        .then(() => {
          this.getMenus();
        })
        .catch(() => {});
    }
  },
  watch: {
    // 监听弹出框状态
    "dialogModel.visible"(val) {
      // 弹出框关闭初始化对话框数据
      if (!val) {
        this.dialogModel = this.getInitDialogModel();
      }
    }
  },
  computed: {
    // 计算菜单树数据，坑爹，el-tree必须有元素
    treeData: function() {
      let nodes = getTreeData(this.systemMenus);
      if (nodes.length === 0) {
        nodes.push({});
      }
      return nodes;
    }
  }
};
</script>

