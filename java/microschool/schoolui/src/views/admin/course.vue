<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>课程</el-breadcrumb-item>
      <el-breadcrumb-item>课程管理</el-breadcrumb-item>
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
          <el-button type="primary" @click="addcoursedialog"
            >添加课程</el-button
          >
        </el-col>
      </el-row>
      <!-- 课程列表 -->
      <el-table :data="courselist" border>
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column
          prop="name"
          label="课程名"
          width="180"
        ></el-table-column>
        <el-table-column
          prop="cteacher"
          label="教师"
          width="180"
        ></el-table-column>
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
    <el-dialog title="添加课程" :visible.sync="adddialogVisible" width="50%">
      <!--内容主体  -->
      <el-form label-position="left" label-width="80px">
        <el-form-item label="课程名称">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="教师">
          <el-input v-model="addForm.cteacher"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span>
        <el-button @click="adddialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addcourse">添 加</el-button>
      </span>
    </el-dialog>
    <!-- 编辑对话框 -->
    <el-dialog title="修改课程" :visible.sync="editdialogVisible" width="50%">
      <!--内容主体  -->
      <el-form label-position="left" label-width="80px">
        <el-form-item label="课程名称">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="教师">
          <el-input v-model="editForm.cteacher"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span>
        <el-button @click="editdialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editcourse">修 改</el-button>
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
      courselist: [],
      total: 0,
      // 搜索表单
      queryInfo: {
        query: "",
        pagenum: 1,
        pagesize: 5,
      },
      // 添加表单
      addForm: {
        name: "",
        cteacher: "",
      },
      // 修改表单
      editForm: {
        id: 0,
        name: "",
        cteacher: "",
      },
      delForm: {
        id: -1,
      },
    };
  },
  created() {
    this.getcourselist();
  },
  methods: {
    async getcourselist() {
      const { data: res } = await this.$http.get("course", {
        params: this.queryInfo,
      });
      console.log(res);
      this.courselist = res.data.list;
      this.total = res.data.total;
    },
    search() {
      this.getcourselist();
    },
    // 添加用户
    async addcourse() {
      console.log(this.addForm);
      const { data: res } = await this.$http.post("course", this.addForm);
      console.log(res);
      if (res.code != 200) {
        return this.$message.error("添加失败！");
      }
      this.$message.success("添加成功");
      this.getcourselist();
      this.adddialogVisible = false;
    },
    // 修改用户
    async editcourse() {
      console.log(this.editForm);
      const { data: res } = await this.$http.put("course", this.editForm);
      console.log(res);

      if (res.code != 200) {
        return this.$message.error("修改失败！");
      }
      this.$message.success("修改成功");
      this.editdialogVisible = false;
      this.getcourselist();
    },
    // 编辑用户
    edit(id) {
      this.editdialogVisible = true;
      this.courselist.forEach((temp) => {
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

      const { data: res } = await this.$http.delete("course", {
        params: this.delForm,
      });
      console.log(res);

      if (res.code != 200) {
        return this.$message.error("删除失败！");
      }
      this.$message.success("删除成功");
      this.getcourselist();
    },
    // 显示添加表单
    addcoursedialog() {
      this.adddialogVisible = true;
    },
    // 分页数据修改
    handleSizeChange(pagesize) {
      console.log(pagesize);
      this.queryInfo.pagesize = pagesize;
      this.getcourselist();
    },
    handleCurrentChange(pagenum) {
      console.log(pagenum);
      this.queryInfo.pagenum = pagenum;
      this.getcourselist();
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
