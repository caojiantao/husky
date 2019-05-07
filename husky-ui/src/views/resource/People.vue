<template>
  <div>
    <el-form :model="query" inline>
      <el-form-item>
        <el-input v-model="query.name" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button @click="addRow">添加人员</el-button>
      </el-form-item>
    </el-form>

    <Pagination
      ref="husky-pagination"
      url="/resource/people/getPeopleByPage"
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
        ref="people"
        :model="dialogModel.form" 
        label-width="80px">
        <el-form-item>
          <el-upload
            class="people-uploader"
            action=""
            :show-file-list="false"
            :http-request="uploadFile">
            <img v-if="dialogModel.form.avatar" :src="dialogModel.form.avatar" class="people-avatar">
            <i v-else class="el-icon-plus people-avatar-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="dialogModel.form.name"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="dialogModel.form.sex" placeholder="请选择">
            <el-option
              v-for="item in sexOpts"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="生日">
          <el-date-picker
            v-model="dialogModel.form.birthday"
            type="date"
            placeholder="选择日期"
            value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="头像地址">
          <el-input v-model="dialogModel.form.avatar"></el-input>
        </el-form-item>
        <el-form-item label="个人简介">
          <el-input v-model="dialogModel.form.profile"></el-input>
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
  .people-avatar {
    width: 200px;
    height: 200px;
  }
  
  .people-avatar-icon {
    font-size: 28px;
    color: #8c939d;
    width: 200px;
    height: 200px;
    line-height: 200px;
    text-align: center;
    border: 1px dashed #d9d9d9;
  }

  .people-avatar-icon:hover {
    border-color: #409EFF;
  }
</style>


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
        { prop: "name", label: "姓名" },
        { label: "操作", slotName: "operate" }
      ],
      dialogModel: this.getInitDialogModel(),
      sexOpts: [
        {label: "男", value: 0},
        {label: "女", value: 1},
        {label: "未知", value: 2},
      ]
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
        title: "新增人员",
        visible: true,
        form: {}
      };
    },
    editRow: function(row) {
      this.$api
        .get("/resource/people/getPeopleById?id=" + row.id)
        .then(people => {
          // 展示对话框
          this.dialogModel = {
            title: "编辑人员",
            visible: true,
            form: people
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
        this.$api
          .post("/resource/people/deletePeopleById", {
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
      this.$refs["people"].validate(valid => {
        if (valid) {
          // 深拷贝，避免改变表单页面数据
          let people = JSON.parse(JSON.stringify(this.dialogModel.form));
          this.$api
            .post("/resource/people/savePeople", people)
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
      this.options = [];
      this.$refs['people'].resetFields();
    },
    validFile(file) {
      const isPic = (file.type === 'image/jpeg') || (file.type === 'image/png');
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isPic) {
        this.$message.error('上传头像图片只能是 JPG 或者 PNG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isPic && isLt2M;
    },
    uploadFile(task) {
      let file = task.file;
      if (this.validFile(file)) {
        let data = new FormData();
        data.append('file', task.file);
        this.$api.post('/file/upload', data,
            {
              headers: {'Content-Type': 'multipart/form-data'}
            }
          )
          .then(url => {
            this.$set(this.dialogModel.form, 'avatar', url);
          })
          .catch(() => {});
      }
    }
  }
};
</script>

