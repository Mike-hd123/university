<template>
  <div>
    <el-card
      ><el-card class="file">
        <!-- 用户头像修改 -->
        <el-upload
          :action="this.$uploadUrl"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :headers="headerObj"
        >
          <img v-if="imageUrl" :src="imageUrl" class="avatar" />
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
        <el-button @click="update">修改</el-button>
      </el-card>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      imageUrl: "",
      headerObj: {
        Authorization: window.localStorage.getItem("token"),
      },
      save: {
        filename: "",
      },
    };
  },
  methods: {
    async update() {
      console.log(this.save.filename);
      const { data: res } = await this.$http.post("savefile", this.save);
      console.log(res);
      if (res.code != 200) {
        return this.$message.error("修改失败！");
      }
      this.$message.success("修改成功,请重新加载页面!");
    },
    // 添加文件成功
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      console.log(res);
      this.save.filename = res.data;
    },
    // 上传前进行校验
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 10;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 10MB!");
      }
      return isJPG && isLt2M;
    },
  },
};
</script>

<style scoped>
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.file {
  text-align: center;
}
</style>
