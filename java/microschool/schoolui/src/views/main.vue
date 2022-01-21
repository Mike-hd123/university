<template>
  <el-container>
    <!-- 头部 -->
    <el-header>
      <admin-top :data="data"></admin-top>
    </el-header>
    <el-container>
      <!-- 侧边栏 -->
      <el-aside>
        <admin-left :meunlist="meunlist" :iconObj="iconObj"></admin-left>
      </el-aside>
      <!-- 主体 -->
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import admintop from "../components/top";
import adminleft from "../components/left";

export default {
  data() {
    return {
      data: {
        title: "",
      },
      meunlist: [],
      meunlist_admin: [
        {
          name: "教师",
          children: [{ name: "教师管理", path: "teacher" }],
        },
        {
          name: "学生",
          children: [{ name: "学生管理", path: "student" }],
        },
        {
          name: "课程",
          children: [{ name: "课程管理", path: "course" }],
        },
        {
          name: "更多",
          children: [{ name: "我的信息", path: "me" }],
        },
      ],
      meunlist_teacher: [
        {
          name: "更多",
          children: [{ name: "我的信息", path: "teacherme" }],
        },
      ],
      meunlist_student: [
        {
          name: "更多",
          children: [{ name: "我的信息", path: "studentme" }],
        },
      ],
      iconObj: {},
      iconObj_admin: {
        0: "el-icon-s-custom",
        1: "el-icon-s-custom",
        2: "el-icon-data-analysis",
        3: "el-icon-more",
      },
      iconObj_teacher: {
        0: "el-icon-more",
      },
      iconObj_student: {
        0: "el-icon-more",
      },
    };
  },
  components: {
    "admin-top": admintop,
    "admin-left": adminleft,
  },
  created() {
    this.getdate();
  },
  methods: {
    async getdate() {
      const { data: res } = await this.$http.get("user/utype");
      if (res.code != 200) return this.$message.error("获取用户类型失败！");
      console.log(res);
      if (res.data == 1) {
        this.iconObj = this.iconObj_admin;
        this.meunlist = this.meunlist_admin;
        this.data.title = "教务系统管理员端";
      } else if (res.data == 2) {
        this.iconObj = this.iconObj_teacher;
        this.meunlist = this.meunlist_teacher;
        this.data.title = "教师端";
      } else {
        this.iconObj = this.iconObj_student;
        this.meunlist = this.meunlist_student;
        this.data.title = "学生端";
      }
    },
  },
};
</script>

<style scoped>
.el-container {
  height: 100%;
}
.el-header {
  padding: 0px;
  background-color: #373d41;
}
.el-aside {
  background-color: #333744;
  width: 200px !important;
}
.el-main {
  background-color: #eaedf1;
}
</style>
