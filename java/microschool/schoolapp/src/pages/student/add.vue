<template>
  <view class="form">
    <studentinfo :formData="formData"></studentinfo>
    <button class="edit" @click="edit" size="mini">添加</button>
    <button class="back" @click="back" size="mini" type="primary">
      返回上一层
    </button>
    <uni-popup ref="msg" type="message">
      <uni-popup-message
        type="error"
        :message="msg"
        :duration="2000"
      ></uni-popup-message>
    </uni-popup>
  </view>
</template>

<script>
export default {
  data() {
    return {
      formData: {
        username: "",
        ssex: "",
        sage: 0,
        classname: "",
        sdept: "",
        file: "/Usersfile/admin_头像.jpeg",
      },
      msg: "",
    };
  },
  methods: {
    back() {
      uni.navigateBack();
    },
    edit() {
      uni.getStorage({
        key: "token",
        success: (a) => {
          uni.request({
            url: "/api/student",
            method: "POST",
            data: this.formData,
            header: {
              Authorization: a.data,
            },
            success: (b) => {
              const { data: res } = b;
              this.msg = res.msg;
              this.$refs.msg.open("top");
              if (res.code == 200) uni.navigateBack();
            },
          });
        },
      });
    },
  },
};
</script>

<style>
.form {
  margin: 5px;
}
.edit {
  margin-top: 30px;
  margin-left: 41%;
  border: 1px;
}
.back {
  margin-top: 10px;
  margin-left: 35%;
}
</style>
