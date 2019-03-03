<template>
  <div>
    <el-table v-loading="loading" :data="records">
      <el-table-column
        v-for="item in columns"
        :key="item.prop"
        :prop="item.prop"
        :label="item.label"
        :width="item.width"
      >
        <template slot-scope="scope">
          <span v-if="item.slotName">
            <slot :name="item.slotName" :row="scope.row"/>
          </span>
          <span v-else>{{scope.row[item.prop]}}</span>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="changeSize"
      @current-change="changePage"
      :current-page="curPage"
      :page-size="pagesize"
      :page-sizes="pagesizes"
      :total="total"
      layout="total, sizes, prev, pager, next, jumper"
    ></el-pagination>
  </div>
</template>

<script>
export default {
  name: "pagination",
  props: ["url", "query", "columns"],
  data() {
    return {
      loading: false,
      records: [],
      pagesize: 10,
      pagesizes: [10, 20, 50],
      curPage: 1,
      total: 0
    };
  },
  methods: {
    search: function() {
      this.loading = true;
      let queryWrapper = {
        current: this.curPage,
        size: this.pagesize
      };
      if (this.query) {
        Object.assign(queryWrapper, this.query);
      }
      this.$axios
        .get(this.url, {
          params: queryWrapper
        })
        .then(result => {
          this.loading = false;
          this.records = result.records;
          this.total = result.total;
        });
    },
    // 分页大小改变
    changeSize: function(pagesize) {
      this.pagesize = pagesize;
      this.search();
    },
    // 分页跳转
    changePage: function(curPage) {
      this.curPage = curPage;
      this.search();
    }
  }
};
</script>

