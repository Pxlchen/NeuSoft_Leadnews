<template>
  <div class="wapper" ref="bg">
    <image class="img" src="/static/images/neusoft.png"/>
    <p class="wapper-p">记录我的生活</p>
  </div>
</template>

<script>
export default {
  name: "load_screen",
  created() {
    let _this = this
    setTimeout(function(){
      _this.goLogin()
    },700)
  },
  methods : {
    goLogin : function () {
      let _this = this
      let animation = weex.requireModule('animation')
      animation.transition(this.$refs.bg, {
        styles: {
          opacity: '0',
        },
        duration: 800, //ms
        timingFunction: 'ease',
        needLayout:false,
        delay: 0 //ms
      },function(){
        _this.$router.push("/login")
      })
    }
  }
}
</script>

<style scoped>
.wapper{
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw; /* 当前屏幕的宽度 */
  height: 100vh; /* 当前屏幕的高度 */
  background: white;
  display: flex;
  flex-direction: column;
}
.img{
  margin: 420px auto;
  height: 150px;
  width: 510px;
}
.img2{
  margin: -450px auto;
  height: 80px;
  width: 380px;
}

.loaclPage {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw; /* 当前屏幕的宽度 */
  height: 100vh; /* 当前屏幕的高度 */
  background: #9DBFF3;
  display: flex;
  flex-direction: column;
}
.wapper-p{
  margin: -380px auto;
  letter-spacing: 19px;
  font-weight: bold;

}

</style>
