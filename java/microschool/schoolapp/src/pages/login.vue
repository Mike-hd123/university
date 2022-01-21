<template>
  <view class="middle">
    <input class="name" style="" v-model="username" />
    <input class="pass" password="ture" v-model="password" />
    <button class="login" @click="login">登录</button>
    <uni-popup ref="msg" type="message">
      <uni-popup-message
        type="error"
        :message="error"
        :duration="2000"
      ></uni-popup-message>
    </uni-popup>
  </view>
</template>

<script>
export default {
  data() {
    return {
      username: "",
      password: "",
      error: "",
    };
  },
  onLoad() {},
  methods: {
    login() {
      console.log("登录...");
      uni.request({
        url: "/api/user/login",
        data: {
          username: this.username,
          password: this.password,
        },
        method: "POST",
        success: (a) => {
          const { data: res } = a;
          if (res.code == 200) {
            console.log("登录成功!");
            uni.setStorage({ key: "token", data: res.data });
            uni.navigateBack();
          } else {
            console.log("登录失败!");
            this.error = res.msg;
            this.$refs.msg.open("top");
          }
        },
      });
    },
  },
};
</script>

<style>
.middle {
  align-content: center;
}
.name {
  background-color: #e8f0fd;
  margin: 50px;
  height: 30px;
}
.pass {
  background-color: #e8f0fd;
  margin: 50px;
  height: 30px;
}
.login {
  margin: 50px;
}
</style>
