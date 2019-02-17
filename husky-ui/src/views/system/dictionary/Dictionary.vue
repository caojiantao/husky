<template>
  <div>
    <el-form :model="query" inline>
      <el-form-item>
        <el-input v-model="query.name" placeholder="请输入字典名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button @click="addRow">添加数据字典</el-button>
      </el-form-item>
    </el-form>

    <Pagination
      ref="husky-pagination"
      url="/system/dictionary/getDictionaryByPage"
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
        ref="dictionary"
        :model="dialogModel.form" 
        label-width="80px">
        <el-form-item label="父级" prop="parentId">
          <el-select 
            v-model="dialogModel.form.parentId" 
            placeholder="请选择"
            clearable
            filterable
            remote
            :remote-method="remoteMethod"
            :loading="optionsLoading">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="编码">
          <el-input v-model="dialogModel.form.code"></el-input>
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="dialogModel.form.name"></el-input>
        </el-form-item>
        <el-form-item label="值">
          <el-input v-model="dialogModel.form.value"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="dialogModel.form.description"></el-input>
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
        { prop: "code", label: "编码" },
        { prop: "name", label: "名称" },
        { prop: "value", label: "值" },
        { prop: "description", label: "描述" },
        { label: "操作", slotName: "operate" }
      ],
      dialogModel: this.getInitDialogModel(),
      // 下拉框远程字典
      options: [],
      optionsLoading: false
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
        title: "新增字典",
        visible: true,
        form: {}
      };
    },
    editRow: function(row) {
      this.$axios
        .get("/system/dictionary/getDictionaryById?id=" + row.id)
        .then(dictionary => {
          if(dictionary.parentId) {
            // 存在父级节点增加对应OPTION
            this.options.push({
              value: dictionary.parentId,
              label: dictionary.parentName
            })
          } else {
            // 否则赋值null，重置下拉框
            dictionary.parentId = null;
          }
          // 展示对话框
          this.dialogModel = {
            title: "编辑字典",
            visible: true,
            form: dictionary
          };
        });
    },
    removeRow(row) {
      this.$confirm("此操作将永久删除该数据字典, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$axios
            .post("/system/dictionary/deleteDictionaryById", {
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
      this.$refs["dictionary"].validate(valid => {
        if (valid) {
          let dictionary = this.dialogModel.form;
          if (!dictionary.parentId) {
            // 父级节点未选中默认值0
            dictionary.parentId = 0;
          }
          this.$axios
            .post("/system/dictionary/saveDictionary", dictionary)
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
    // 下拉框远程加载数据字典列表
    remoteMethod(keyword) {
      this.options = [];
      if(keyword) {
        this.optionsLoading = true;
        this.$axios
          .get("/system/dictionary/getDictionariesByKeyword?keyword=" + keyword)
          .then(dictionaries => {
            this.optionsLoading = false;
            if(dictionaries && dictionaries.length > 0) {
              dictionaries.forEach(dictionary => {
                this.options.push({
                  value: dictionary.id,
                  label: dictionary.name
                });
              })
            }
          });
      }
    },
    // 对话框完全关闭事件
    dialogClosed() {
      this.options = [];
      this.dialogModel.form = {};
      this.$refs['dictionary'].resetFields();
    }
  }
};
</script>

