<template>
  <div>
    <el-form inline>
      <el-form-item>
        <el-button @click="addRow">添加记录</el-button>
      </el-form-item>
    </el-form>

    <Pagination
      ref="husky-pagination"
      url="/exercise/getRunByPage"
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
        ref="run"
        :model="dialogModel.form" 
        label-width="100px">
        <el-form-item label="用户">
          <el-input v-model="dialogModel.form.userId"></el-input>
        </el-form-item>
        <el-form-item label="距离（公里）">
          <el-input v-model="dialogModel.form.distance"></el-input>
        </el-form-item>
        <el-form-item label="耗时（秒）">
          <el-input v-model="dialogModel.form.second"></el-input>
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
        { prop: "distance", label: "距离" },
        { prop: "formatSecond", label: "耗时" },
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
      this.$refs["husky-pagination"].search(function(records){
        if (records && records.length > 0) {
          records.forEach(item => {
            let second = item.second;
            if (second < 60) {
              item.formatSecond = second + "秒";
            } else if (second < 60 * 60) {
              let m = Math.floor(second / 60);
              item.formatSecond = m + "分" + second % 60 + "秒";
            } else if (second < 60 * 60 * 24) {
              let h = Math.floor(second / (60 * 60));
              let m = Math.floor((second - h * 60 * 60) / 60);
              item.formatSecond = h + "小时" + m + "分" + second % 60 + "秒";
            } else {
              // 超过“天”单位暂不考虑
            }
          })
        }
      });
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
        .get("/exercise/getRunById?id=" + row.id)
        .then(run => {
          // 展示对话框
          this.dialogModel = {
            title: "编辑跑步",
            visible: true,
            form: run
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
            .post("/exercise/deleteRunById", {
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
      this.$refs["run"].validate(valid => {
        if (valid) {
          this.$axios
            .post("/exercise/saveRun", this.dialogModel.form)
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
      this.$refs['run'].resetFields();
    }
  }
};
</script>

