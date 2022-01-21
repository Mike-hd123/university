<template>
  <view class="form">
    <studentinfo :formData="formData"></studentinfo>
    <button class="edit" @click="edit" size="mini">修改</button>
    <button class="dle" @click="del" size="mini" type="warn">删除</button>
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
    <uni-popup ref="popup" type="dialog">
      <uni-popup-dialog
        type="warn"
        title="删除"
        content="是否删除数据?"
        @confirm="confirm"
      ></uni-popup-dialog>
    </uni-popup>
  </view>
</template>

<script>
export default {
  data() {
    return {
      formData: {},
      msg: "",
    };
  },
  onLoad(option) {
    this.formData = JSON.parse(decodeURIComponent(option.item));
  },
  methods: {
    back() {
      uni.navigateBack();
    },
    del() {
      this.$refs.popup.open();
    },
    confirm() {
      uni.getStorage({
        key: "token",
        success: (a) => {
          uni.request({
            url: "/api/student?id=" + this.formData.id,
            method: "DELETE",
            header: {
              Authorization: a.data,
            },
            success: (b) => {
              const { data: res } = b;
              this.msg = res.msg;
              this.$refs.msg.open("top");
              console.log(res);
              if (res.code == 200)
                uni.navigateBack({
                  delta: 1,
                  success: function() {
                    let page = getCurrentPages()[0];
                    if (!page) return;
                    page.getsearch();
                  },
                });
            },
          });
        },
      });
    },
    edit() {
      uni.getStorage({
        key: "token",
        success: (a) => {
          uni.request({
            url: "/api/student",
            method: "PUT",
            data: this.formData,
            header: {
              Authorization: a.data,
            },
            success: (b) => {
              const { data: res } = b;
              this.msg = res.msg;
              this.$refs.msg.open("top");
              if (res.code == 200)
                uni.navigateBack({
                  delta: 1,
                  success: function() {
                    let page = getCurrentPages()[0];
                    if (!page) return;
                    page.getsearch();
                  },
                });
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
  margin-left: 30%;
  border: 1px;
}
.dle {
  margin-left: 22px;
}
.back {
  margin-top: 10px;
  margin-left: 35%;
}
</style>
