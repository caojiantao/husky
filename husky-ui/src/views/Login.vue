<template>
  <div class="login">
    <el-form ref="loginForm" :model="loginForm.model" :label-width="loginForm.labelWidth">
        <el-form-item label="账号">
          <el-input v-model="loginForm.model.username"></el-input>
        </el-form-item>
          <el-form-item label="密码">
          <el-input type="password" v-model="loginForm.model.password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
    </el-form>
  </div>
</template>

<script>

export default {
  name: 'login',
  components: {
  },
  data: () => {
    return {
      loginForm: {
        model: {},
        labelWidth: '80px'
      }
    }
  },
  methods: {
    submitForm: function() {
      console.log(this.loginForm.model)
    },
    resetForm: function() {

    }
  }
}
</script>
