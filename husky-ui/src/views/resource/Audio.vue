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
        <el-button @click="addRow">添加音频</el-button>
      </el-form-item>
    </el-form>

    <Pagination
      ref="husky-pagination"
      url="/resource/audio/getAudioByPage"
      :query="query"
      :columns="columns"
    >
      <template slot="play" slot-scope="scope">
        <audio :src="scope.row.playUrl" controls/>
      </template>

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
        ref="audio"
        :model="dialogModel.form" 
        label-width="80px">
        <el-form-item>
          <el-upload
            action=""
            :limit="1"
            :show-file-list="false"
            :http-request="uploadFile">
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
          <el-progress :percentage="filePercent"></el-progress>
        </el-form-item>
        <el-form-item>
          <audio v-if="dialogModel.form.playUrl" :src="dialogModel.form.playUrl" controls/>
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="dialogModel.form.name"></el-input>
        </el-form-item>
        <el-form-item label="时长">
          <el-input v-model="dialogModel.form.duration"></el-input>
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
        { prop: "name", label: "名称" },
        { prop: "duration", label: "时长" },
        { label: "播放", slotName: "play" },
        { label: "操作", slotName: "operate" }
      ],
      dialogModel: this.getInitDialogModel(),
      filePercent: 0
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
        title: "新增音频",
        visible: true,
        form: {}
      };
    },
    editRow: function(row) {
      this.$axios
        .get("/resource/audio/getAudioById?id=" + row.id)
        .then(audio => {
          // 展示对话框
          this.dialogModel = {
            title: "编辑音频",
            visible: true,
            form: audio
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
          .post("/resource/audio/deleteAudioById", {
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
      this.$refs["audio"].validate(valid => {
        if (valid) {
          // 深拷贝，避免改变表单页面数据
          let audio = JSON.parse(JSON.stringify(this.dialogModel.form));
          this.$axios
            .post("/resource/audio/saveAudio", audio)
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
      this.$refs['audio'].resetFields();
      this.filePercent = 0;
    },
    validFile(file) {
      let isAudio = (file.type === 'audio/mp3');
      if (!isAudio) {
        this.$message.error('上传音频只能是 MP3 格式!');
      }
      return isAudio;
    },
    uploadFile(task) {
      let file = task.file;
      if (this.validFile(file)) {
        let data = new FormData();
        data.append('file', task.file);
        this.$axios.post('/file/upload', data,
            {
              headers: {'Content-Type': 'multipart/form-data'},
              onUploadProgress: progressEvent => {
                // 对原生进度事件的处理
                if(progressEvent.lengthComputable) {
                  this.filePercent = (progressEvent.loaded / progressEvent.total * 100 | 0)
                }
              },
            }
          )
          .then(url => {
            this.$set(this.dialogModel.form, 'playUrl', url);
          })
          .catch(() => {});
      }
    }
  }
};
</script>

