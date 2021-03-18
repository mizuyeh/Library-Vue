<template>
  <div class="page">
    <div class="main">
      <el-form :model="loginForm" ref="loginForm" label-width="100px" class="loginContainer">
        <el-form-item
          prop="userName"
          label="登录名"
          :rules="[
            { required: true, message: '请输入登录名', trigger: 'blur' },
          ]"
        >
        <el-input type="text"  auto-complete="false" placeholder="请输入登录名" v-model="loginForm.userName"></el-input>
        
        </el-form-item>
        <el-form-item
          prop="password"
          label="密码"
          :rules="[
            { required: true, message: '请输入密码', trigger: 'blur' },
          ]"
        >
        <el-input type="password" auto-complete="false" placeholder="请输入密码" v-model="loginForm.password" show-password></el-input>
        </el-form-item>

        <el-form-item
          prop="code"
          label="验证码"
          :rules="[
            { required: true, message: '请输入验证码', trigger: 'blur' },
          ]"
        >
          <el-input type="text" auto-complete="false" v-model="loginForm.code" placeholder="点击图片更换验证码"
            style="width: 150px; margin-right: 50px"
          >
          </el-input>
          <img :src="captchaURL">
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('loginForm')">登录</el-button>
          <el-button @click="resetForm('loginForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
   </div>
</template>

<style>
  .main{
    text-align: center; /*让div内部文字居中*/
    border-radius: 20px;
    width: 300px;
    height: 350px;
    margin: auto;
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
}

 /*  img {
    background-size: cover;
  } */
</style>

<script>
  import {login} from '@/network/login'
  import {setToken} from '@/util/auth'
  export default {
   data() {
     return {
       loginForm: {},
       captchaURL: '',
     }
   },
   methods: {
     submitForm() {
       this.$refs.loginForm.validate(valid => {
          if (valid) {
            login(this.loginForm).then(res => {
              if(res) {
                const tokenStr = res.data.msg;
                setToken(tokenStr);
                this.$router.replace('/home');
              }
            })
          } else {
            this.$message.error("请输入所有字段!");
            return false;
          }
       });
     }
   }
  };
</script>