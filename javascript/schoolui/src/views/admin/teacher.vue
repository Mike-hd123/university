<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>教师</el-breadcrumb-item>
      <el-breadcrumb-item>教师管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <!-- 搜索与添加 -->
      <el-row :gutter="20">
        <el-col :span="8">
          <!-- 搜索栏 -->
          <el-input placeholder="请输入内容" class="input-with-select" v-model="queryInfo.query">
            <template #append>
              <el-button icon="Search" @click="search"></el-button>
            </template>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addteacherdialog">添加教师</el-button>
        </el-col>
      </el-row>
      <!-- 教师列表 -->
      <el-table :data="teacherlist" border>
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column prop="username" label="姓名"></el-table-column>
        <el-table-column prop="ssex" label="性别"></el-table-column>
        <el-table-column prop="sage" label="年龄"></el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button type="primary" icon="Edit" @click="edit(scope.row.id)" circle></el-button>
            <el-button type="danger" icon="Delete" @click="dele(scope.row.id)" circle></el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分页功能  -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pagenum"
        :page-sizes="[5, 10, 50, 100]"
        :page-size="queryInfo.pagesize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        :hide-on-single-page="true"
      ></el-pagination>
    </el-card>
    <!-- 添加对话框 -->
    <el-dialog title="添加用户" v-model="adddialogVisible" width="50%">
      <!--内容主体  -->
      <el-form label-position="left" label-width="80px">
        <el-form-item label="姓名">
          <el-input v-model="addForm.username"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="addForm.ssex"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="addForm.sage"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span>
        <el-button @click="adddialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="adduser">添 加</el-button>
      </span>
    </el-dialog>
    <!-- 编辑对话框 -->
    <el-dialog title="修改用户" v-model="editdialogVisible" width="50%">
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
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 控制添加对话框
      adddialogVisible: false,
      editdialogVisible: false,
      total: 0,
      teacherlist: [],
      // 搜索表单
      queryInfo: {
        query: "",
        pagenum: 1,
        pagesize: 5,
      },
      // 添加表单
      addForm: {
        username: "",
        ssex: "",
        sage: "",
        file: "/Usersfile/admin_头像.jpeg",
      },
      // 修改表单
      editForm: {
        id: "",
        username: "",
        ssex: "",
        sage: "",
      },
      delForm: {
        id: -1,
      },
    };
  },
  created() {
    this.getteacherlist();
  },
  methods: {
    // 获取列表
    async getteacherlist() {
      const { data: res } = await this.$http.get("teacher", {
        params: this.queryInfo,
      });
      console.log(res);
      this.teacherlist = res.data.list;
      this.total = res.data.total;
    },
    // 搜索用户
    search() {
      this.getteacherlist();
    },
    // 添加用户
    async adduser() {
      console.log(this.addForm);
      const { data: res } = await this.$http.post("teacher", this.addForm);
      console.log(res);
      if (res.code != 200) {
        return this.$message.error("添加失败！");
      }
      this.$message.success("添加成功");
      this.getteacherlist();
      this.adddialogVisible = false;
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
    // 编辑用户
    edit(id) {
      this.editdialogVisible = true;
      this.teacherlist.forEach((temp) => {
        if (temp.id == id) return (this.editForm = temp);
      });
      console.log(id);
    },
    // 删除用户
    async dele(id) {
      this.delForm.id = id;
      const confirmrsult = await this.$confirm(
        "此操作将永久删除该文件, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).catch((err) => err);

      if (confirmrsult != "confirm") {
        return this.$message.info("已取消删除");
      }

      const { data: res } = await this.$http.delete("teacher", {
        params: this.delForm,
      });
      console.log(res);

      if (res.code != 200) {
        return this.$message.error("删除失败！");
      }
      this.$message.success("删除成功");
      this.getteacherlist();
    },
    // 显示添加表单
    addteacherdialog() {
      this.adddialogVisible = true;
    },
    // 分页数据修改
    handleSizeChange(pagesize) {
      console.log(pagesize);
      this.queryInfo.pagesize = pagesize;
      this.getteacherlist();
    },
    handleCurrentChange(pagenum) {
      console.log(pagenum);
      this.queryInfo.pagenum = pagenum;
      this.getteacherlist();
    },
  },
};
</script>

<style scoped>
.el-table {
  margin-top: 15px;
  font-size: 12px;
}
</style>
