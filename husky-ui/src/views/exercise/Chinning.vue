<template>
  <div>
    <el-form inline>
      <el-form-item>
        <el-button @click="addRow">添加记录</el-button>
      </el-form-item>
    </el-form>

    <Pagination
      ref="husky-pagination"
      url="/exercise/getChinningByPage"
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
      @closed="dialogClosed"
    >
      <el-form
        ref="chinning"
        :model="dialogModel.form" 
        label-width="80px">
        <el-form-item label="用户">
          <el-input v-model="dialogModel.form.userId"></el-input>
        </el-form-item>
        <el-form-item label="个数">
          <el-input v-model="dialogModel.form.number"></el-input>
        </el-form-item>
        <el-form-item label="时间">
          <el-date-picker
            v-model="dialogModel.form.time"
            type="datetime"
            placeholder="选择日期时间"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
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
      query: {},
      columns: [
        { prop: "userId", label: "用户" },
        { prop: "number", label: "个数" },
        { prop: "time", label: "时间" },
        { label: "操作", slotName: "operate" }
      ],
      dialogModel: {
        title: "",
        visible: false,
        form: this.getInitForm()
      },
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
    getInitForm: function() {
      return {
        time: new Date()
      };
    },
    addRow: function() {
      this.dialogModel = {
        title: "新增任务",
        visible: true,
        form: this.getInitForm()
      };
    },
    editRow: function(row) {
      this.$axios
        .get("/exercise/getChinningById?id=" + row.id)
        .then(chinning => {
          // 展示对话框
          this.dialogModel = {
            title: "编辑引体向上",
            visible: true,
            form: chinning
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
            .post("/exercise/deleteChinningById", {
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
      this.$refs["chinning"].validate(valid => {
        if (valid) {
          this.$axios
            .post("/exercise/saveChinning", this.dialogModel.form)
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
      this.$refs['chinning'].resetFields();
    }
  }
};
</script>

