<template>
  <view class="form">
    <uni-forms :modelValue="formData">
      <uni-forms-item label="姓名" name="username">
        <uni-easyinput
          type="text"
          v-model="formData.username"
          placeholder="请输入姓名"
        />
      </uni-forms-item>
      <uni-forms-item label="性别" name="ssex">
        <uni-data-checkbox
          :multiple="false"
          v-model="formData.ssex"
          :localdata="sex"
        />
      </uni-forms-item>
      <uni-forms-item label="年龄" name="sage">
        <uni-easyinput
          type="number"
          v-model="formData.sage"
          placeholder="请输入年龄"
      /></uni-forms-item>
    </uni-forms>
    <button class="edit" @click="edit" size="mini">修改</button>
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
      formData: {},
      sex: [
        { value: "男", text: "男" },
        { value: "女", text: "女" },
      ],
      msg: "",
    };
  },
  onLoad(option) {
    this.formData = JSON.parse(decodeURIComponent(option.item));
    console.log(this.formData);
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
            url: "/api/teacher",
            method: "PUT",
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
