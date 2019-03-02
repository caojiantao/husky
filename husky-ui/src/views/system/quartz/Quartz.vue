<template>
  <div>
    <el-form inline>
      <el-form-item>
        <el-button @click="addRow">添加任务</el-button>
      </el-form-item>
    </el-form>

    <Pagination
      ref="husky-pagination"
      url="/system/quartz/getQuartzByPage"
      :query="query"
      :columns="columns"
    >
      <template slot="operate" slot-scope="scope">
        <el-button v-if="scope.row.status" size="medium" icon="el-icon-edit" @click="changeStatus(scope.row.id, false)">禁用</el-button>
        <el-button v-else size="medium" icon="el-icon-edit" @click="changeStatus(scope.row.id, true)">启用</el-button>
    
        <el-button size="medium" icon="el-icon-edit" @click="editRow(scope.row)">编辑</el-button>
        <el-button size="medium" icon="el-icon-delete" @click="removeRow(scope.row)">删除</el-button>
      </template>
    </Pagination>

    <el-dialog
      :title="dialogModel.title"
      :visible.sync="dialogModel.visible"
      :close-on-click-modal="false"
      width="950px"
      @closed="dialogClosed"
    >
      <el-form
        ref="quartz"
        :model="dialogModel.form" 
        label-width="80px">
        <el-form-item label="分组">
          <el-input v-model="dialogModel.form.group"></el-input>
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="dialogModel.form.name"></el-input>
        </el-form-item>
        <el-form-item label="表达式">
          <el-input v-model="dialogModel.form.cronExpression"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="dialogModel.form.description"></el-input>
        </el-form-item>
        <el-form-item label="class">
          <el-input v-model="dialogModel.form.jobClass"></el-input>
        </el-form-item>
      </el-form>
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
        name: ""
      },
      columns: [
        { prop: "group", label: "分组" },
        { prop: "name", label: "名称" },
        { prop: "cronExpression", label: "表达式" },
        { prop: "status", label: "状态" },
        { label: "操作", slotName: "operate" }
      ],
      dialogModel: this.getInitDialogModel(),
    };
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
        form: {}
      };
    },
    addRow: function() {
      this.dialogModel = {
        title: "新增任务",
        visible: true,
        form: {}
      };
    },
    editRow: function(row) {
      this.$axios
        .get("/system/quartz/getQuartzById?id=" + row.id)
        .then(quartz => {
          this.dialogModel = {
            title: "编辑字典",
            visible: true,
            form: quartz
          };
        });
    },
    removeRow(row) {
      this.$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$axios
            .post("/system/quartz/deleteQuartzById", {
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
      this.$refs["quartz"].validate(valid => {
        if (valid) {
          this.$axios
            .post("/system/quartz/saveQuartz", this.dialogModel.form)
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
    // 对话框完全关闭事件
    dialogClosed() {
      this.$refs['quartz'].resetFields();
    },
    changeStatus(id, status) {
      this.$axios
        .post("/system/quartz/changeStatus", {
            id: id,
            status: status
        })
        .then(() => {
            this.search();
        })
        .catch(() => {});
    }
  }
};
</script>

