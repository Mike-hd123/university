<template>
  <div>
    <el-card>
      <el-card class="file">
        <!-- 用户头像修改 -->
        <el-upload
          :action="uploadUrl"
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
      <!-- 修改个人信息 -->
      <el-card class="edit">
        <!--内容主体  -->
        <el-form label-position="left" label-width="80px">
          <el-form-item label="姓名">
            <el-input v-model="editForm.username"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-input v-model="editForm.ssex"></el-input>
          </el-form-item>
          <el-form-item label="年龄">
            <el-input v-model="editForm.sage"></el-input>
          </el-form-item>
        </el-form>
        <!-- 底部区域 -->
        <span>
          <el-button @click="editdialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="edituser">修 改</el-button>
        </span>
      </el-card>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 上传url地址
      uploadUrl: "/api/upload",
      imageUrl: "",
      headerObj: {
        Authorization: window.localStorage.getItem("token"),
      },
      save: {
        filename: "",
      },
      // 修改表单
      editForm: {
        id: "",
        username: "",
        ssex: "",
        sage: "",
      },
    };
  },
  created() {
    this.getme();
  },
  methods: {
    async getme() {
      const { data: res } = await this.$http.get("me");
      console.log(res);
      this.editForm = res.data[0];
    },
    // 修改用户
    async edituser() {
      console.log(this.editForm);
      const { data: res } = await this.$http.put("teacher", this.editForm);
      console.log(res);

      if (res.code != 200) {
        return this.$message.error("修改失败！");
      }
      this.$message.success("修改成功");
      this.editdialogVisible = false;
      this.getteacherlist();
    },
    // 只有保存了文件才会生效
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
.edit {
  margin-top: 20px;
  text-align: center;
}
.file {
  text-align: center;
}
</style>
