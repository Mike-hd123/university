<template>
  <el-menu
    router
    unique-opened
    :default-active="activePath"
    text-color="#fff"
    background-color="#333744"
  >
    <!-- 一级菜单 -->
    <el-submenu
      v-for="(item, index) in meunlist"
      :key="index"
      :index="index + ''"
    >
      <!-- 一级菜单标题 -->
      <template slot="title">
        <i :class="iconObj[index]"></i>
        <span>{{ item.name }}</span>
      </template>
      <!-- 二级菜单 -->
      <el-menu-item-group
        v-for="(item1, index1) in item.children"
        :key="index1"
      >
        <el-menu-item
          :index="'/' + item1.path"
          @click="saveNavstate('/' + item1.path)"
        >
          <template slot="title">
            <i class="el-icon-menu"></i>
            <span>{{ item1.name }}</span>
          </template>
        </el-menu-item>
      </el-menu-item-group>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  props: {
    meunlist: {
      type: Array,
      default: () => [],
    },
    iconObj: {
      type: Object,
      default: () => [],
    },
  },
  data() {
    return {
      activePath: "",
    };
  },
  created() {
    this.activePath = window.sessionStorage.getItem("activePath");
  },
  methods: {
    saveNavstate(path) {
      this.activePath = path;
      window.sessionStorage.setItem("activePath", path);
    },
  },
};
</script>

<style scoped>
.el-menu {
  border-right: none;
}
</style>

