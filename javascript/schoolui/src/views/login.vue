<template>
  <div class="login_style">
    <div class="login_box">
      <!-- 头像 -->
      <div class="avatr_box">
        <img src="../assets/tx.png" />
      </div>
      <!-- 表单 -->
      <!-- :rules="LoginRules" -->
      <el-form
        class="login_form"
        ref="LoginFormRef"
        :model="LoginForm"
        label-width="0px"
      >
        <!-- 账号 -->
        <el-form-item prop="username">
          <el-input
            v-model="LoginForm.username"
            prefix-icon="el-icon-user-solid"
          ></el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item prop="password">
          <el-input
            v-model="LoginForm.password"
            type="password"
            prefix-icon="el-icon-lock"
          ></el-input>
        </el-form-item>
        <!--  -->
        <el-form-item class="btns">
          <el-button type="primary" @click="post">登录</el-button>
          <el-button type="info" @click="resetform">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      LoginForm: {
        username: "admin",
        password: "admin",
      },
    };
  },
  methods: {
    resetform() {
      this.$refs.LoginFormRef.resetFields();
    },

    async post() {
      const { data: res } = await this.$http.post("login", this.LoginForm);
      console.log(res);
      if (res.code !== 200) return this.$message.error(res.msg);
      window.localStorage.setItem("token", res.data);
      this.$router.push("/");
    },
  },
};
</script>

<style scoped>
.login_form {
  position: absolute;
  bottom: 0px;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}

.btns {
  display: flex;
  justify-content: center;
}

.login_style {
  /* 背景链接 */
  background-image: url("../assets/bg.jpg");
  /* 拉伸 */
  background-size: cover;
  /* 加载时的背景颜色 */
  background-color: #cccccc;
  /* 全屏 */
  height: 100%;
  /* 外边距为0 */
  margin: 0px;
}

.login_box {
  width: 450px;
  height: 300px;
  background-color: #fff;
  /* 绝对定位 */
  position: absolute;
  /* 边距 */
  left: 50%;
  top: 50%;
  /* 这个属性允许你将元素旋转，缩放，移动，倾斜 translate 定义 2D 转换 */
  transform: translate(-50%, -50%);
}

.avatr_box {
  height: 130px;
  width: 130px;
  border: 1px solid #eee;
  border-radius: 50%;
  box-shadow: 0 0 10px #ddd;
  position: absolute;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #fff;
}

img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background-color: #eee;
}
</style>
