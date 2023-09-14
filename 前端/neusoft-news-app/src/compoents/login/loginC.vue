<template>
  <div class="page">
    <div class="loginPage">

      <div class="returnBox">
        <!-- 返回首页按钮盒子 -->
        <div class="returnIndex"@click="bearkIndex()">
          <image class="returnIndex-image" src="/static/images/login/leftReturn.png" mode=""></image>
          <p class="returnIndex-p">游客登录</p>
        </div>
      </div>

      <div class="loginTop">
        <!-- 插画盒子 -->
        <div class="imageBox">
          <div>
            <image src="/static/images/login/topImage1.png" mode="" class="image1"  key="1" v-show="true"></image>

          </div>
          <div>
            <image src="/static/images/login/topImage2.png" mode="" class="image2" key="2" v-show="true"></image>

          </div>
        </div>

      </div>

      <!-- 外层：下层盒子 -->
      <div class="loginBottom">

        <!-- 耳机logo盒子 -->
        <div class="logoView">
          <div class="logViewScro">
            <image class="image4" src="/static/images/login/logoc.png" mode="widthFix"></image>
          </div>
        </div>


        <!-- 登录logo 盒子-->
        <div class="loginBottom-a">
          <image class="loginBottom-a-image" src="/static/images/login/login.png" mode=""></image>
        </div>

        <!-- 用户输入盒子 -->
        <div class="loginBottom-b">
          <image class="loginBottom-b-image" src="/static/images/login/id.png"></image>
          <input class="loginBottom-b-input" type="text" placeholder="用户名"  v-model="phone" mode="" @blur="checkPhone">
        </div>


        <!-- 密码输入盒子 -->
        <div class="loginBottom-b password-div">
          <image class="loginBottom-b-image" src="/static/images/login/password.png" ></image>
          <input class="loginBottom-b-input" type="password"  v-model="password"  placeholder="密码">
        </div>




        <!-- 忘记密码盒子 -->
        <div class="loginBottom-d">
          <text class="loginBottom-d-text">忘记密码？</text>
        </div>

        <div class="loginBottom-e" @click="loginC()">
          <text class="loginBottom-e-text">登录</text>
        </div>


        <!-- 第三方登录盒子 -->
        <div class="loginBottom-f">
          <div class="thirdParty">
            <image class="thirdParty-image" src="/static/images/login/phone.png" mode=""></image>
          </div>
          <div class="thirdParty">
            <image  class="thirdParty-image"  src="/static/images/login/qqc.png" mode=""></image>
          </div>
          <div class="thirdParty">
            <image  class="thirdParty-image"  src="/static/images/login/wechatc.png" mode=""></image>
          </div>
        </div>

      </div>
    </div>
    <wxc-dialog title="错误"
                :content=errText
                :top="500"
                :show="show"
                :single="true"
                :is-checked="isChecked"
                :show-no-prompt="false"
                @wxcDialogCancelBtnClicked="wxcDialogCancelBtnClicked"
                @wxcDialogConfirmBtnClicked="wxcDialogConfirmBtnClicked"
                @wxcDialogNoPromptClicked="wxcDialogNoPromptClicked"
    >
    </wxc-dialog>
  </div>
</template>

<script>
import { WxcDialog } from 'weex-ui';
import Api from "@/apis/login/api";
export default {
  components: { WxcDialog },
  methods : {
    bearkIndex(){
      this.$router.push('/home');
    },

    wxcDialogCancelBtnClicked () {
      //此处必须设置，组件为无状态组件，自己管理
      this.show = false;
    },
    wxcDialogConfirmBtnClicked () {
      //此处必须设置，组件为无状态组件，自己管理
      this.show = false;
    },
    wxcDialogNoPromptClicked (e) {
      //此处必须设置，组件为无状态组件，自己管理
      this.isChecked = e.isChecked;
    },
    // 验证手机号格式
    checkPhone() {
      if (this.phone == '' || this.phone == undefined) {
        this.phoneErr = '请输入手机号';
        this.isPhoneSure = false;
      } else if (!/^1[1-9]\d{9}$/.test(this.phone)) {
        this.phoneErr = '手机号格式不正确'
        this.isPhoneSure = false;
      } else if (this.phone.length != 11) {
        this.phoneErr = '手机号长度有误'
        this.isPhoneSure = false;
      } else {
        this.isPhoneSure = true; // 格式正确，隐藏 tooltip
        this.phoneErr = ''
      }
    },
    loginC () {
      if (!this.isPhoneSure){
        this.show = true;
        this.checkPhone()
        this.errText=this.phoneErr
        //alert(this.phone)
      }else {
        this.params.phone=this.phone
        this.params.password=this.password
        //alert(JSON.stringify(this.params))
        Api.login(this.params).then(d=>{
          //alert(d.code)
          if(d.code==200){
            this.$store.setToken(d.data.token)
            //this.$store.setUserInfo(JSON.stringify(d.data.user))
            //console.log(JSON.stringify(d.data.user))
            //this.$store.setToken(d.data.token)
            this.$router.push("/home")
          }else{
            this.show = true;
            this.errText=d.errorMessage
          }
        }).catch(e=>{
          console.log(e)
        })
      }
    },
    login:function(){

      if(this.params.phone==''||this.params.password==''){
        modal.toast({
          message:'请输入用户名或密码',
          duration:3
        })
      }else{
        this.params.phone=this.phone
        this.params.password=this.password
        Api.login(this.params).then(d=>{
          if(d.code==200){
            this.$store.setToken(d.data.token)
            //this.$store.setUserInfo(JSON.stringify(d.data.user))
            this.$router.push("/home")
            this.$store.setToken(d.data.token)
            this.$router.push("/home")
          }else{
            modal.toast({ message:'用户或密码错误',duration:3})
          }
        }).catch(e=>{
          console.log(e)
        })
      }
    },
  },
  watch:{
    phone:{
      deep:true,
      handler(){
        this. checkPhone()
      }
    }
  },
  data(){
    return{
      show: false,
      isChecked: false,
      logo:[
        {
          url:'/static/images/login/logoc.png'
        },
        {
          url:'/static/images/login/logoc.png'
        },
      ],
      isLogin:true,
      isLoginc:true,
      isRegister:false,
      isRegisterc:false,
      phone: '13511223456',
      phoneErr: '请输入手机号',
      isPhoneSure: false,
      password: 'admin',
      passwordErr: '',
      isPwordSure:false,
      errText:'',

      //注册默认值
      userPoster:'https://chen-1317386995.cos.ap-guangzhou.myqcloud.com/C2c-Music/user.webp',


      params:{
        phone:'',
        password:''
      },
    }
  },

}
</script>

<style>
.page{
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw; /* 当前屏幕的宽度 */
  height: 100vh; /* 当前屏幕的高度 */
  overflow: hidden;
}
.loginPage {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw; /* 当前屏幕的宽度 */
  height: 100vh; /* 当前屏幕的高度 */
  background: #9DBFF3;
  display: flex;
  flex-direction: column;
}
.registerPage{
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw; /* 当前屏幕的宽度 */
  height: 1000px; /* 当前屏幕的高度 */
  background: white url('https://chen-1317386995.cos.ap-guangzhou.myqcloud.com/C2c-Music%2Fimage%2FSignUP.png') no-repeat;
  background-size: cover;
}
.logo{
  position: relative;
  width: 100%;
  height: 490px;
  display: flex;
  align-items: center;
  z-index: 1;
}
.logoImage{
  width: 150px;
  height: 140px;
  margin: 0 auto;

}
.loginTop{
  position: relative;
  height: 33%;
  width: 100%;
  display: flex;
  flex-direction: column;
  z-index: 2;
  overflow: visible;
//background-color: #00c9b7;


}
.returnBox{
  position: relative;
  width: 100%;
  height: 7%;
  margin-top: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  z-index: 6;

}
.returnIndex{
  position: relative;
  width: 200px;
  height: 80px;
  background-color:white;
  margin-left: 40px;
  border-radius: 20px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;

}
.returnIndex-p{
  font-weight: bold;
  font-size: 15px;
}
.returnIndex-image{
  position: relative;
  height: 40px;
  width: 45px;
}

.imageBox{
  position: relative;
  width: 100%;
  flex-grow: 1; /* 使用剩余空间 */
  /* 		background-color: skyblue; */
}
.image1{
  position: absolute;
  width: 135px;
  height: 135px;
  left: 35px;
  top: 15px;
}
.image2{
  position: absolute;
  width: 250px;
  height: 250px;
  right: -90px;
  top: -15px;
}
.image3{
  position: absolute;
  width: 340px;
  height: 340px;
  left: -70px;
  bottom:-10px;
}
.image4{
  width: 520px;
  height: 420px;
  z-index: 5;
  left: 50px;
  filter: drop-shadow(0 0 10px #585ce5);
}
.image4-2{
  position: absolute;
  width: 500px;
  height: 500px;
  left: 50px;
  transform: rotateY(-180deg) translateZ(1px);
}
.logoView{
  position: absolute;
  width: 600px;
  height: 380px;
  left: 0;
  right: 0;
  top: -380px;
  margin: auto;
  z-index: 5;
  display: flex;
  align-content: center;
  justify-content: center;
  filter: drop-shadow(0 0 10px #585ce5);
//background-color: #8a8886;
}
.logViewScro{
  width: 100%;
  height: 100%;
  z-index: 5;
  display: flex;
  align-content: center;
  justify-content: center;
  //animation: play 6s infinite;
  //position: relative;
  //transform-style: preserve-3d;
}

.pro-box {
  width: 32%;
  animation: play 6s infinite;
  position: relative;
  transform-style: preserve-3d;
}
.pro-box-image {
  width: 100%;
  background-color: #fff;
  border-radius: 20px;
}
.pro-box image:nth-child(2) {
  position: absolute;
  left: 0;
  top: 0;
  height: 300px;
  transform: rotateY(-180deg) translateZ(1px);
}

@keyframes play {
  0% {
    transform: rotateY(0deg);
  }
  25% {
    transform: rotateY(0deg);
  }
  50% {
    transform: rotateY(180deg);
  }
  75% {
    transform: rotateY(180deg);
  }
  100% {
    transform: rotateY(360deg);
  }
}

.loginTop button{
  position: relative;
  bottom: 0;
}
.loginBottom{
  position: relative;
  margin: 0 auto;
  height: 60%;
  width: 100%;
  display: flex;
  align-items: center;
  flex-direction: column;
  background: white url('https://chen-1317386995.cos.ap-guangzhou.myqcloud.com/C2c-Music%2Fimage%2Fbase.png') no-repeat;
  background-size: cover;
  z-index: 2;
  border-radius: 45px 45px 0 0;
}
.loginBottom-a{
  position: relative;
  width: 90%;
  height: 130px;
  margin-top: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.loginBottom-a-image{
  position: relative;
  width: 178px;
  height: 89px;
}
.loginBottom-b{
  position: relative;
  width: 620px;
  height: 100px;
  background-color: white;
  margin-top: 40px;
  display: flex;
  flex-direction: row;
  align-items: center;
  border-radius: 90px;
  margin-top: 10px;
}
.loginBottom-b-image{
  position: relative;
  width: 80px;
  height: 80px;
  left: 20px;
}
.loginBottom-b-input{
  width: 400px;
  height: 80px;
  margin-left: 30px;
  font-weight: bold;
  font-size: 25px;
  border: #ffffff solid 1px;
  background-color: white;
  outline: none;
}
.password-div{
  margin-top: 40px;
}
.loginBottom-c-password{
  position: relative;
  width: 290px;
  height: 63px;
  display: flex;
  flex-direction: row;
  align-items: center;
  background-color: white;
  border-radius: 40px;
}
.loginBottom-c-password image{
  position: relative;
  width: 45px;
  height: 45px;
  margin-left: 25px;
}
.loginBottom-c-password-nput{
  width: 180px;
  height: 40px;
  margin-left: 15px;
  font-weight: bold;
  font-size: 17px;
}
.loginBottom-c-zhiwen{
  position: relative;
  width: 60px;
  height: 60px;
  display: flex;
  margin-left: 10px;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  border-radius: 40px;
}
.loginBottom-c-zhiwen image{
  position: relative;
  width: 100%;
  height: 100%;
}
.loginBottom-d{
  position: relative;
  width: 150px;
  height: 40px;
  margin-top: 17px;
  margin-left: 480px;


}
.loginBottom-d-text{
  position: relative;
  float: right;
  font-size: 12px;
  line-height: 30px;
  color: #a19f9d;
}
.loginBottom-e{
  position: relative;
  width: 550px;
  height:80px;
  background-color: #585ce5;
  margin-top: 35px;
  margin-left: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 40px;
}
.loginBottom-e-text{
  font-weight: bold;
  font-size: 25px;
  letter-spacing: 2px;
  color: white;
}


.loginBottom-f{
  position: relative;
  width: 380px;
  height: 100px;
  margin-top: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: row;
}
.thirdParty{
  position: relative;
  width: 100px;
  height: 100px;
  border-radius: 100%;
  background-color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0px 20px 0 20px;
}
.thirdParty-image{
  width: 50px;
  height: 50px;
}



.imagec{
  position: absolute;
  width:1990px ;
  height: 1950px;
  z-index: 9;
  left: -600px;
  transition: all 5s;
  /* 自定义的贝塞尔曲线 */
  transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
}

.bottom {
  top: 1600px;
}
.topc{
  top:-2000px;
}


</style>
