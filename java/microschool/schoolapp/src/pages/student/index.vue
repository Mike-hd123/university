<template>
  <view>
    <uni-nav-bar fixed="ture"
      ><uni-search-bar
        class="search"
        radius="90"
        v-model="search.query"
        @confirm="getsearch"
        @clear="getsearch1"
        @cancelButton="getsearch1"
      ></uni-search-bar
    ></uni-nav-bar>
    <uni-list v-for="item in studentlist" :key="item.id">
      <uni-list-item
        :title="item.username + ':' + item.sdept + ':' + item.classname"
        :to="'edit?item=' + encodeURIComponent(JSON.stringify(item))"
      >
      </uni-list-item>
    </uni-list>
    <uni-fab @fabClick="add" horizontal="right"></uni-fab>
  </view>
</template>

<script>
export default {
  data() {
    return {
      studentlist: [],
      total: 0,
      search: {
        query: "",
        pagenum: 1,
        pagesize: 25,
      },
    };
  },
  onLoad() {
    this.search.pagenum = 1;
    this.getData();
  },
  onPullDownRefresh() {
    this.search.pagenum = 1;
    this.getData();
  },
  onReachBottom() {
    var size = Math.ceil(this.total / this.search.pagesize);
    if (this.search.pagenum < size) {
      this.search.pagenum += 1;
      this.getData();
    }
  },
  methods: {
    getsearch() {
      this.search.pagenum = 1;
      this.getData();
    },
    getsearch1() {
      this.search.query = "";
      this.search.pagenum = 1;
      this.getData();
    },
    getData() {
      uni.getStorage({
        key: "token",
        success: (res) => {
          uni.request({
            url: "/api/student",
            data: this.search,
            header: {
              Authorization: res.data,
            },
            success: (tmp) => {
              const { data: nres } = tmp;
              if (this.search.pagenum != 1)
                this.studentlist = this.studentlist.concat(nres.data.list);
              else this.studentlist = nres.data.list;
              this.total = nres.data.total;
              console.log(nres);
              uni.stopPullDownRefresh();
            },
          });
        },
      });
    },
    add() {
      uni.navigateTo({
        url: "add",
      });
    },
  },
};
</script>

<style>
.search {
  width: 325px;
}
</style>
