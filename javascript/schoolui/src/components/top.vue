<template>
  <el-row type="flex" justify="space-between">
    <!-- 用户名 -->
    <el-col :span="6">
      <div class="avatr_box">
        <img :src="imageUrl" />
      </div>
    </el-col>
    <!-- title -->
    <el-col :span="6">
      <span>{{ data.title }}</span>
    </el-col>
    <!-- 退出按钮 -->
    <el-col :span="1">
      <el-link type="primary" @click="loginout">退出登录</el-link>
    </el-col>
  </el-row>
</template>

<script>
export default {
  props: {
    data: {
      type: Object,
      default: () => {},
    },
  },
  data() {
    return {
      imageUrl: "",
    };
  },
  created() {
    this.getfile();
  },
  methods: {
    async getfile() {
      const { data: res } = await this.$http.get("file");
      console.log(res.data);
      this.imageUrl = res.data;
    },
    async loginout() {
      const { data: res } = await this.$http.get("loginout");
      console.log(res);
      if (res.code != 200) {
        return this.$message.error("退出失败，请检查网络！");
      }
      this.$message.success("退出成功，请重新登录！");
      window.localStorage.clear();
      this.$router.push("/login");
    },
  },
};
</script>

<style scoped>
.el-row {
  width: 100%;
  height: 60px;
  font-size: 18px;
  color: #fff;
  padding-top: 15px;
}
.el-link {
  font-size: 15px;
  color: aliceblue;
}

.avatr_box {
  height: 60px;
  width: 60px;
  border: 1px solid #eee;
  border-radius: 50%;
  box-shadow: 0 0 10px #ddd;
  left: 50%;
  transform: translate(0%, -30%);
  background-color: #fff;
}

img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background-color: #eee;
}
</style>
