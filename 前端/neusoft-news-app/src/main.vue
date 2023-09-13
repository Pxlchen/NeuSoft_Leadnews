<template>
  <div class="wrapper">
    <router-view></router-view>
  </div>
</template>

<script>
  import util from "@/utils/font"

export default {
  name: 'DONRRUAN-App',
  created () {
    util.initIconFont();
  }
}
</script>
