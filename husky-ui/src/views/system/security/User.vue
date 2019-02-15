<template>
  <div>
    <el-form :model="query" inline>
      <el-form-item>
        <el-input v-model="query.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button @click="addRow">添加用户</el-button>
      </el-form-item>
    </el-form>

    <Pagination
      ref="husky-pagination"
      url="/system/security/user/getUserByPage"
      :query="query"
      :columns="columns"
    >
      <template slot="operate" slot-scope="scope">
        <el-button size="medium" icon="el-icon-edit" @click="editRow(scope.row)">编辑</el-button>
        <el-button size="medium" icon="el-icon-delete" @click="removeRow(scope.row)">删除</el-button>
      </template>
    </Pagination>

    <el-dialog
      :title="dialogModel.title"
      :visible.sync="dialogModel.visible"
      :close-on-click-modal="false"
      width="950px"
    >
      <el-row :gutter="20">
        <el-col :span="10">
          <el-form :model="dialogModel.form" ref="user" label-width="80px">
            <el-form-item label="用户名">
              <el-input v-model="dialogModel.form.username"></el-input>
            </el-form-item>
            <el-form-item label="昵称">
              <el-input v-model="dialogModel.form.nickname"></el-input>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="dialogModel.form.password" placeholder="请输入密码" type="password"></el-input>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="14">
          <el-transfer
            v-model="dialogModel.roleIds"
            :data="roles"
            :props="{
            key: 'id',
            label: 'name'
          }"
            :titles="['角色列表','当前角色']"
          ></el-transfer>
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

export default {
  components: { Pagination },
  data() {
    return {
      query: {
        username: ""
      },
      columns: [
        { prop: "id", label: "ID", width: "80" },
        { prop: "username", label: "角色名" },
        { prop: "nickname", label: "昵称" },
        { label: "操作", slotName: "operate" }
      ],
      dialogModel: this.getInitDialogModel(),
      roles: []
    };
  },
  created: function() {
    this.getAllRoles();
  },
  mounted: function() {
    this.search();
  },
  methods: {
    search: function() {
      // 调用子组件查询方法
      this.$refs["husky-pagination"].search();
    },
    getInitDialogModel: function() {
      return {
        title: "",
        visible: false,
        form: {},
        roleIds: []
      };
    },
    addRow: function() {
      this.dialogModel = {
        title: "新增用户",
        visible: true,
        form: {}
      };
    },
    editRow: function(row) {
      // 首先获取角色基本信息
      this.$axios
        .get("/system/security/user/getUserWithRolesById?id=" + row.id)
        .then(user => {
          let roleIds = [];
          // 初始化选中的角色
          if (user.roleDTOS) {
            user.roleDTOS.forEach(roleDTO => {
              roleIds.push(roleDTO.id);
            });
          }
          // 展示对话框
          this.dialogModel = {
            title: "编辑角色",
            visible: true,
            form: user,
            roleIds: roleIds
          };
        });
    },
    removeRow(row) {
      this.$confirm("此操作将永久删除该用户, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$axios
            .post("/system/security/user/deleteUserById", {
              id: row.id
            })
            .then(() => {
              this.search();
            })
            .catch(() => {});
        })
        .catch(() => {});
    },
    submitForm() {
      this.$refs["user"].validate(valid => {
        if (valid) {
          let user = this.dialogModel.form;
          user.roleDTOS = [];
          this.dialogModel.roleIds.forEach(roleId =>
            user.roleDTOS.push({ id: roleId })
          );
          this.$axios
            .post("/system/security/user/saveUser", user)
            .then(() => {
              this.dialogModel.visible = false;
              this.search();
            })
            .catch(() => {});
        } else {
          return false;
        }
      });
    },
    getAllRoles() {
      this.$axios
        .get("/system/security/role/getAllRoles")
        .then(roles => {
          this.roles = roles;
        })
        .catch(() => {});
    }
  }
};
</script>

