<template>
  <div>
    <h3>Weclome</h3>
  </div>
</template>

<script>
export default {
  async created() {
    this.test();
  },
  methods: {
    async test() {
      const { data: res } = await this.$http.get("user/online");
      console.log(res);
      if (res.code != 200) {
        this.$message.error("token已失效，请重新登录");
        this.$router.push("/login");
      }
    },
  },
};
</script>
