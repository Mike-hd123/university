<template>
  <view>
    <uni-list v-for="item in courselist" :key="item.id">
      <uni-list-item
       :title="item.name+':'+item.cteacher"
       :to="'info?item='+encodeURIComponent(JSON.stringify(item))"
       ></uni-list-item>
    </uni-list>
  </view>
</template>

<script>
export default {
  data() {
    return {
      courselist:[],
      total: 0
    };
  },
  onLoad() {
    this.getData();
  },
  onShow(){
    this.getData();
  },
  onPullDownRefresh() {
    this.getData();
  },
  methods: {
    getData() {
      uni.getStorage({
        key: "token",
        success: (res) => {
          uni.request({
            url: "/api/course",
            data: {
              query: "",
              pagenum: 1,
              pagesize: 5,
            },
            header: {
              Authorization: res.data,
            },
            success: (tmp) => {
              const { data: nres } = tmp;
              this.courselist = nres.data.list;
              this.total = nres.data.total;
              uni.stopPullDownRefresh();
              console.log(nres.data);
            },
          });
        },
      });
    },
  },
};
</script>

<style>
</style>
