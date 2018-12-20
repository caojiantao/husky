<template>
  <div class="about">
    <Pagination :query="query" url="http://94.191.46.132:8081/kugou/songs" :columns="columns"/>
  </div>
</template>

<script>
import Pagination from "@/components/Pagination.vue";

export default {
  components: { Pagination },
  data() {
    return {
      columns: [
        {prop: "FileName", label: "歌曲名"}
      ],
      query: {
        keyword: "光年之外"
      }
    };
  }
};
</script>

