<template>
  <div>
    <Pagination
      ref="husky-pagination"
      url="/system/systemQuartz/getQuartzLogByPage"
      :query="query"
      :columns="columns"
    >
    </Pagination>
  </div>
</template>

<script>
import Pagination from "@/components/Pagination.vue";

export default {
  components: { Pagination },
  data() {
    return {
      query: {},
      columns: [
        { prop: "quartzId", label: "任务ID" },
        { prop: "startTime", label: "开始时间" },
        { prop: "endTime", label: "结束时间" },
        { prop: "status", label: "状态" },
      ]
    };
  },
  mounted: function() {
    this.search();
  },
  methods: {
    search: function() {
      // 调用子组件查询方法
      this.$refs["husky-pagination"].search();
    }
  }
};
</script>

