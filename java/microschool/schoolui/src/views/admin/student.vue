<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>学生</el-breadcrumb-item>
      <el-breadcrumb-item>学生管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <!-- 搜索与添加 -->
      <el-row :gutter="20">
        <el-col :span="8">
          <!-- 搜索栏 -->
          <el-input
            placeholder="请输入内容"
            class="input-with-select"
            v-model="queryInfo.query"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="search"
            ></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addstudentdialog"
            >添加学生</el-button
          >
        </el-col>
      </el-row>
      <!-- 学生列表 -->
      <el-table :data="studentlist" border>
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column prop="username" label="姓名"></el-table-column>
        <el-table-column prop="sage" label="年龄"></el-table-column>
        <el-table-column prop="ssex" label="性别"></el-table-column>
        <el-table-column prop="classname" label="班级"></el-table-column>
        <el-table-column prop="sdept" label="学部"></el-table-column>
        <el-table-column label="操作" width="210">
          <template slot-scope="scope">
            <el-button
              type="primary"
              icon="el-icon-edit"
              @click="edit(scope.row.id)"
              >编辑</el-button
            >
            <el-button
              type="danger"
              icon="el-icon-delete"
              @click="dele(scope.row.id)"
              >删除</el-button
            >
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
      ></el-pagination>
    </el-card>
    <!-- 添加对话框 -->
    <el-dialog title="添加用户" :visible.sync="adddialogVisible" width="50%">
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
        <el-form-item label="班级">
          <el-input v-model="addForm.classname"></el-input>
        </el-form-item>
        <el-form-item label="学部">
          <el-input v-model="addForm.sdept"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span>
        <el-button @click="adddialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="adduser">添 加</el-button>
      </span>
    </el-dialog>
    <!-- 编辑对话框 -->
    <el-dialog title="修改用户" :visible.sync="editdialogVisible" width="50%">
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
        <el-form-item label="班级">
          <el-input v-model="editForm.classname"></el-input>
        </el-form-item>
        <el-form-item label="学部">
          <el-input v-model="editForm.sdept"></el-input>
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
      adddialogVisible: false,
      editdialogVisible: false,
      studentlist: [],
      total: 0,
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
        classname: "",
        sdept: "",
        file: "/Usersfile/admin_头像.jpeg",
      },
      // 修改表单
      editForm: {
        id: "",
        username: "",
        ssex: "",
        sage: "",
        classname: "",
        sdept: "",
      },
      delForm: {
        id: -1,
      },
    };
  },
  created() {
    this.getstudentlist();
  },
  methods: {
    async getstudentlist() {
      const { data: res } = await this.$http.get("student", {
        params: this.queryInfo,
      });
      console.log(res);
      this.studentlist = res.data.list;
      this.total = res.data.total;
    },
    search() {
      this.getstudentlist();
    },
    // 添加用户
    async adduser() {
      console.log(this.addForm);
      const { data: res } = await this.$http.post("student", this.addForm);
      console.log(res);
      if (res.code != 200) {
        return this.$message.error("添加失败！");
      }
      this.$message.success("添加成功");
      this.getstudentlist();
      this.adddialogVisible = false;
    },
    // 修改用户
    async edituser() {
      console.log(this.editForm);
      const { data: res } = await this.$http.put("student", this.editForm);
      console.log(res);

      if (res.code != 200) {
        return this.$message.error("修改失败！");
      }
      this.$message.success("修改成功");
      this.editdialogVisible = false;
      this.getstudentlist();
    },
    // 编辑用户
    edit(id) {
      this.editdialogVisible = true;
      this.studentlist.forEach((temp) => {
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

      const { data: res } = await this.$http.delete("student", {
        params: this.delForm,
      });
      console.log(res);

      if (res.code != 200) {
        return this.$message.error("删除失败！");
      }
      this.$message.success("删除成功");
      this.getstudentlist();
    },
    // 显示添加表单
    addstudentdialog() {
      this.adddialogVisible = true;
    },
    // 分页数据修改
    handleSizeChange(pagesize) {
      console.log(pagesize);
      this.queryInfo.pagesize = pagesize;
      this.getstudentlist();
    },
    handleCurrentChange(pagenum) {
      console.log(pagenum);
      this.queryInfo.pagenum = pagenum;
      this.getstudentlist();
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
