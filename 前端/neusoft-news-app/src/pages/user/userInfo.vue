<template>
  <div class="user">

    <wxc-popup class="wxc-popup-c"
               popup-color="rgb(240, 240, 242)"
               :show="isBottomShow"
               @wxcPopupOverlayClicked="popupOverlayBottomClick"
               pos="left"
               width="620"
    >

      <div class="siderbarBox-top">
        <div class="saoMa">
          <image class="saoMa-image" src="/static/images/erweima2.png" mode=""></image>
        </div>
      </div>


      <!-- 头像区域 -->
      <div class="information">
        <div class="avatr">
          <image class="avatr-image" src="/static/images/load_screen.png" mode=""></image>
        </div>
        <div class="person-information">
          <div class="person-information-a" @click="">
            <h2>this.User.uname</h2>
          </div>
          <div class="person-information-b">
            <p>xx关注&nbsp&nbspxx粉丝&nbsp&nbspLv.7</p>
          </div>
        </div>
      </div>

      <div class="vip">
        <div class="vip-text-a">
          <h2>超级VIP</h2>
          <div class="dengjitiao">
            <div class="now"/>
          </div>
          <p>V2</p>
          <div class="vip-center">
            <h5>会员中心</h5>
          </div>
        </div>
        <div class="vip-text-b">
          <h4>礼品卡 | 吃糖啦小朋友🍭 </h4>
          <image class="vip-text-b-image" src="/static/images/shuiyu.jpg" mode=""></image>
        </div>
        <div class="vip-wave">
          <wave style="bottom: 50px;height: 90px"></wave>
        </div>
      </div>


      <div class="menu-i">
        <!-- ：style -->
        <div class="menu-item"  v-for="(item,index) in siderbarMenu" :key="index" :style="late(index)">
          <image class="menu-item-image" :src="item.image" mode=""></image>
          <p  class="menu-item-p" >{{item.text}}</p>
          <image class="menu-item-imagec"  :src="item.more" mode=""></image>
        </div>

      </div>

      <div class="again-login" @click="">
        <h2 class="again-login-h2">退出登录/关闭</h2>
      </div>

    </wxc-popup>


    <div class="top">
      <div class="top-left" @click="openBottomPopup">
        <image class="top-left-image" src="/static/images/morec.png"></image>
      </div>
      <div class="top-right">
        <image class="top-right-image" src="/static/images/search-user.png" mode=""></image>
      </div>
    </div>

    <!-- 头像区域 -->
    <div class="information">
      <div class="avatr">
        <image class="avatr-image" src="/static/images/load_screen.png" mode=""></image>
      </div>
      <div class="person-information">
        <div class="person-information-a" @click="">
          <h2 class="person-information-a-h2">this.User.uname</h2>
        </div>
        <div class="person-information-b">
          <p>xx关注&nbsp&nbspxx粉丝&nbsp&nbspLv.7</p>
        </div>
      </div>
    </div>

    <!-- 个人认证 -->
    <div class="love">
      <image class="love-image" src="/static/images/v.png"></image>
      <h2 class="love-h2">自媒体认证</h2>
    </div>

    <!-- 菜单 -->
    <div class="menu">
      <div class="menu-button" v-for="(item,index) in menu" :key="index">
        <image class="menu-button-image" :src="item.image" mode=""></image>
        <p class="menu-button-p">{{item.text}}</p>
      </div>
    </div>

    <!--列表-->
    <div>

    </div>
    <wave/>
  </div>
</template>

<script>
import wave from "../../compoents/user/wave.vue"
import { WxcPopup } from 'weex-ui';
export default {

  data() {
    return {
      isBottomShow: false,
      height: 400,
      menu:[
        {
          image:'/static/images/a.png',
          text:'最近观看'
        },
        {
          image:'/static/images/b.png',
          text:'本地'
        },
        {
          image:'/static/images/c.png',
          text:'个人中心'
        },
        {
          image:'/static/images/d.png',
          text:'搜索记录'
        },
        {
          image:'/static/images/e.png',
          text:'我的好友'
        },
        {
          image:'/static/images/f.png',
          text:'收藏和赞'
        },
        {
          image:'/static/images/g.png',
          text:'我的博客'
        },
        {
          image:'/static/images/h.png',
          text:'其他应用'
        },
      ],
      siderbarMenu:[
        {
          image:'/static/images/11.png',
          text:'我的信息',
          more:'/static/images/more.png'
        },
        {
          image:'/static/images/22.png',
          text:'创作者中心',
          more:'/static/images/more.png'
        },
        {
          image:'/static/images/33.png',
          text:'通知',
          more:'/static/images/more.png'
        },
        {
          image:'/static/images/44.png',
          text:'数据',
          more:'/static/images/more.png'
        },
        {
          image:'/static/images/55.png',
          text:'设置',
          more:'/static/images/more.png'
        },
        {
          image:'/static/images/66.png',
          text:'夜间模式',
          more:'/static/images/more.png'
        },
        {
          image:'/static/images/77.png',
          text:'定时关闭',
          more:'/static/images/more.png'
        },
        {
          image:'/static/images/88.png',
          text:'关于',
          more:'/static/images//more.png'
        },
      ]
    }
  },
  components:{wave,WxcPopup },








  name: "load_screen",
  created() {
    let _this = this
    setTimeout(function(){
      _this.goLogin()
    },100000)
  },
  methods : {

    openBottomPopup () {
      this.isBottomShow = true;
    },
    //非状态组件，需要在这里关闭
    popupOverlayBottomClick () {
      this.isBottomShow = false;
    },
    late(time){
      return `--tm:${time * 0.05}s`
    }

  }
}
</script>

<style scoped>

.again-login{
  position: relative;
  width: 80%;
  margin-left: 10%;
  height: 85px;
  background-color: white;
  margin-top: 30px;
  border-radius: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.again-login h2{
  font-size: 25px;
  color: #6bb8fe;
  font-weight: bold;
}
.menu-i{
  position: relative;
  width: 98%;
  height: 682px;
  background-color: white;
  margin-top: 22px;
  margin-left: 1%;
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: hidden;
}
.menu-item{
  position: relative;
  width: 90%;
  height: 70px;
  margin-top: 10px;
// background-color: #f1d8da;
  border-bottom: 1px solid #c8c6c4 ;
  display: flex;
  align-items: center;
  flex-direction: row;

  transform: translate(100%);
  animation: move 1.2s forwards;
  animation-delay: var(--tm);
}
.menu-item-image{
  width: 42px;
  height: 42px;
  margin-left: 10px;
}
.menu-item-imagec{
  width: 52px;
  height: 52px;
  margin-left: 205px;
}
.menu-item-p{
  font-size: 10px;
  margin-left: 10px;
  margin-right:50px;
  width: 150px;
}
@keyframes move {
  to{
    transform: translate(0);
  }

}

.vip{
  position: relative;
  width: 98%;
  margin-left: 1%;
  height: 240px;
  background-color: #3e3d3f;
  margin-top: 20px;
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}
.vip-text-a{
  position: relative;
  width: 100%;
  height: 52px;
  margin-top: 15px;
  display: flex;
  flex-direction: row;
  align-items: center;
  border-radius: 20px 20px 0 0 ;
}
.vip-text-a h2{
  font-size: 19px;
  font-weight: bold;
  margin-left: 12px;
  color: #f1d8da;
}
.vip-text-a p{
  font-size: 10px;
  font-weight: bold;
  margin-left: 5px;
  color: #f1d8da;
}
.vip-center{
  position: relative;
  width: 122px;
  height: 52px;
  margin-left: 180px;
  border-radius: 20px;
  border:solid 1px #f1d8da;
  display: flex;
  align-items: center;
  justify-content: center;
}
.vip-center h5{
  color: #f1d8da;
  font-size: 10px;
  transform: scale(0.8); /* 缩放为原来的 80% */
}
.dengjitiao{
  position: relative;
  width: 100px;
  height: 8px;
  margin-left: 10px;
  background-color: black;
  border-radius: 20px;
}
.now{
  position: relative;
  border-radius: 20px;
  width: 40%;
  height: 100%;
  background-color: white;
}
.vip-text-b{
  position: relative;
  width: 100%;
  height: 80px;
  margin-top: 10px;
  display: flex;
  flex-direction: row;

}
.vip-text-b h4{
  color: #f1d8da;
  font-size: 12px;
  transform: scale(0.9); /* 缩放为原来的 80% */
}
.vip-text-b-image{
  width: 30px;
  height: 30px;
  margin-top: 20px;
}
.vip-wave{
  position: absolute;
  bottom: -50px;
  width: 100%;
  height: 550px;
  border-radius: 0 0 20px 20px;
}


.siderbarBox-top{
  position: relative;
  width: 98%;
  height: 80px;
  margin-top: 10px;
  align-items: center;
}
.saoMa{
  position: relative;
  width: 98%;
  height: 80px;
  border-radius: 15px;
  float: right;
  display: flex;
  align-items: center;
  justify-content: center;
}
.saoMa-image{
  position: relative;
  margin-left: 80%;
  width: 70px;
  height: 70px;
}
.user{
  position: relative;
  top: 0;
  left: 0;
  width: 100vw; /* 当前屏幕的宽度 */
  height: 100vh; /* 当前屏幕的高度 */
  overflow-y: hidden; /* 禁止垂直方向上的滚动 */
  background: linear-gradient(40deg,#f7f7f7,#f7f7f7,#eaf6fe,#e7f3ff,#e7f3fe);
}
.wxc-popup-c{
  position: absolute;
  z-index: 100;
}
.top{
  position: relative;
  width: 100%;
  height: 100px;
  display: flex;
  flex-direction: row;
}
.top-left{
  float: left;
  width: 100px;
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;

}
.top-left-image{
  width: 65px;
  height: 65px;
}
.top-right{
  position: relative;
  float: right;
  width: 100px;
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 520px;
}
.top-right-image{
  width: 62px;
  height: 62px;
}
.top-right image{
  width: 30px;
  height: 30px;
}

.information{
  position: relative;
  width: 100%;
  height: 380px;
}
.informationb{
  position: relative;
  width: 100%;
  height: 280px;
}
.avatr{
  position: absolute;
  height: 90px;
  width: 90px;
  z-index: 2;
  top: 21%;
  left: 45%;
  transform: translate(-50%, -50%);
  border-radius: 50px;
}
.avatr-image{
  width: 170px;
  height: 170px;
  border-radius: 100px;
}
.person-information{
  position: relative;
  margin-left: 3%;
  width: 94%;
  height: 250px;
  background-color: #e3ecf4  ;
  border-radius: 20px;
  top: 120px;
  z-index: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}
.person-information-a{
  position: relative;
  width: 230px;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 30px;

  font-size: 32px;
}
.person-information-b{
  position: relative;
  width: 300px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.person-information-b p{
  font-size: 10px;
  color:#a19f9d  ;
}

.menu{
  position: relative;
  width: 98%;
  margin-left: 1%;
  margin-top: 30px;
  height: 300px;
  display: flex;
  flex-direction: column;
  align-items: center;
  flex-wrap: wrap;
  border-radius: 15px;
  background-color: #ffffff;

}
.menu-button{
  position: relative;
  width: 25%;
  height: 150px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.menu-button-image{
  width: 50px;
  height: 50px;
}
.menu-button-p{
  margin-top: 16px;
  font-size: 12px;
  color: #8a8886   ;
}

.love{
  position: relative;
  width: 70%;
  margin-left: 15%;
  height: 90px;
  background-color: white;
  display: flex;
  border-radius: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: row;
  border-radius: 100px;
  border: none;
  animation: chen01 2s infinite;
}
@keyframes chen01 {
  0% {
    box-shadow: 0 0 0 0 #00c9b7;
  }

  70% {
    box-shadow: 0 0 0 12px rgb(218 103 68 / 0%);
  }

  100% {
    box-shadow: 0 0 0 0 rgba(14, 4, 1, 0);
  }
}
.love-image{
  height: 50px;
  width: 50px;
}
.love-h2{
  position: relative;
  margin-top: 28px;
  margin-left: 15px;
  font-size: 30px;
  font-weight: bold;
  color: #6deaed;
}
.love-a{
  position: relative;
  width: 70px;
  height: 70px;
  margin-left: 20px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.love-a image{
  width: 100%;
  height: 100%;
  border-radius: 10px;
}
.love-aaa{
  position: absolute;
  height: 33px;
  width: 33px;
  z-index: 4;
}
.love-aa{
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: black;
  opacity: 0.3;
  border-radius: 10px;
  z-index: 3;
  display: flex;
  align-items: center;
  justify-content: center;
}
.love-b{
  position: relative;
  width: 195px;
  height: 70px;
  margin-left: 5px;
  display: flex;
  justify-content: center;
  flex-direction: column;
}
.love-b h3{
  margin-left: 9px;
  font-size: 13px;
}
.love-b p{
  margin-left: 9px;
  margin-top: 5px;
  font-size: 9px;
  color: #8a8886;
}
.love-c{
  position: relative;
  width: 90px;
  height: 30px;
  border: #8a8886 1px solid;
  border-radius: 20px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}
.love-c image{
  width: 18px;
  height: 18px;
}
.love-c p{
  margin-left: 2px;
  font-size: 9px;
}

.collect{
  position: relative;
  width: 385px;
  height: 200px;
  background-color: white;
  margin: auto;
  display: flex;
  margin-top: 15px;
  border-radius: 15px;
  display: flex;
  align-items: center;
  flex-direction: row;
  flex-direction: column;
}
.collect-top{
  position: relative;
  width: 380px;
  height: 40px;
  display: flex;
  align-items: center;
}
.collect-top p{
  margin-left: 10px;
  font-size: 10px;
}
.collect-scroll{
  position: relative;
  width: 385px;
  height: 150px;
  white-space: nowrap;
  display: flex;
  lign-items: center;
  justify-content:center;
}
.item{
  position: relative;
  width:100%;
  height: 70px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}
.item-image{
  position: relative;
  height: 55px;
  width: 55px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.item-image image{
  height: 55px;
  width: 55px;
  border-radius: 10px;
}
.item-text{
  position: relative;
  height: 55px;
  width: 280px;
  margin-left: 15px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.item-text h2{
  font-size: 14px;
}
.item-text p{
  font-size: 12px;
  margin-top: 8px;
  color: #8a8886;
}
.item-more{
  position: relative;
  height: 55px;
  width: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.item-more image{
  width: 15px;
  height: 22px;
}




</style>
