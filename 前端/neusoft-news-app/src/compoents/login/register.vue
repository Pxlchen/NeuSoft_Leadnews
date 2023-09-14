<template>
  <div class="page">
    <div class="loginPage">

      <div class="go-login">
      </div>
      <!-- logo -->
      <div class="registerPagetop">
        <image class="registerPagetop-image registerPagetop-image-animation" src="/static/images/login/registerLogo.png" mode=""></image>
      </div>

      <!-- 注册logo -->
      <div class="logoText">
        <image class="logoText-image" src="/static/images/login/register.png" mode=""></image>
      </div>

      <!-- 注册表单实现 -->
      <div class="form-a">
        <image class="form-a-image" src="/static/images/login/phoner.png" mode=""></image>
        <input class="form-a-input" type="text" placeholder="手机号"  v-model="phone" @blur="checkPhone()">
      </div>
      <div class="form-b">
        <image  class="form-a-image"  src="/static/images/login/id.png" mode=""></image>
        <input class="form-a-input"  type="text" placeholder="姓名" v-model="name" @blur="checkName()">
      </div>

      <div class="form-d">
        <image class="form-a-image"  src="/static/images/login/password.png" mode=""></image>
        <input class="form-a-input"  type="password" placeholder="密码" v-model="password" @blur="checkPassword" >
      </div>
      <div class="form-d">
        <image class="form-a-image"  src="/static/images/login/password2.png" mode=""></image>
        <input class="form-a-input"  type="password" placeholder="确认密码"  v-model="passwordT" @blur="checkPasswordT()">
      </div>

      <div  class="form-which">
        <div class="whichId">
          <div class="select" :class="isXue?'select-left':'select-right'"></div>
          <div class="whichId-x"  @click="selectId(1)" >
            <p class="whichId-p" :class="isXue?'whichId-p-is':'whichId-p-no'" >学号</p>
          </div>
          <div class="whichId-i"  @click="selectId(0)">
            <p class="whichId-p"  :class="isId?'whichId-p-is':'whichId-p-no'">身份证号</p>
          </div>
        </div>

        <div class="form-c">
          <image v-show="isXue" class="form-a-image"  src="/static/images/login/idCard.png" mode=""></image>
          <image v-show="isId" class="form-a-image-a"  src="/static/images/login/idcardb.png" mode=""></image>
          <input  v-show="isXue" class="form-c-input"  type="text" placeholder="学号" v-model="xuehao"  @blur="checkXuehao()">
          <input  v-show="isId" class="form-c-input"  type="text" placeholder="身份证" v-model="idCard" @blur="checkIdCard()">
        </div>
      </div>





      <div class="form-f" @click="registerC()">
        <text class="form-f-text">注册</text>
      </div>

    </div>


    <wxc-dialog :title=title
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
import {WxcDialog} from "weex-ui";
import {doPostJson} from "../../../configs/httpRequest";
import md5 from "md5";

export default {
  components: {WxcDialog},
  methods : {
    selectId(which){
      if (which==0){
        this.isXue=false
        this.isId=true
        this.whichc=which
      }else{
        this.isXue=true
        this.isId=false
        this.whichc=which
      }

    },

    registerC () {
      this.checkPhone()
      this.checkPassword()
      this.checkPasswordT();
      this.checkXuehao();
      this.checkName();
      this.checkIdCard()
      let isAllSure = true
      if (!this.isPhoneSure){this.errText=this.errText+this.phoneErr+"\n";isAllSure=false}
      if (!this.isNameSure){this.errText=this.errText+this.nameErr+"\n";isAllSure=false}
      if (!this.isPwordSure){this.errText=this.errText+this.passwordErr+"\n";isAllSure=false}
      if (!this.isNameSure){this.errText=this.errText+this.nameErr+"\n";isAllSure=false}
      if (!this.isPwordTSure){
        if (this.isPwordSure){
          this.errText=this.errText+this.passwordTErr+"\n";
          isAllSure=false
        }
      }
      switch (this.whichc){
        case 1:if (!this.isXueHao){this.errText=this.errText+this.xuehaoErr+"\n";isAllSure=false};break;
        case 0:if (!this.isCardSure){this.errText=this.errText+this.idCardErr+"\n";isAllSure=false};break;

        }
      if (isAllSure) {
        let params={
          phone:this.phone,
          name:this.name,
          number:this.xuehao,
          idNumber:this.idCard,
          password:md5(this.password),
          type:this.whichc
        }
        //发起请求
        doPostJson("/api/v1/signin/signin",params).then(resp=>{
          if (resp.data.code==200 && resp.data.data=="SUCCESS"){
            this.show=true
            this.errText='注册成功'
            this.title='注册提醒'
          }else{
            this.show=true
            this.errText=resp.data.errorMessage
            this.title='注册提醒'
          }
        })
      }
      else
      {
        this.show = true;
      }

    },
    wxcDialogCancelBtnClicked () {
      this.show = false;
      this.errText=''
    },
    wxcDialogConfirmBtnClicked () {
      this.show = false;
      this.errText=''
    },
    wxcDialogNoPromptClicked (e) {
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
        this.isPhoneSure = true;
        this.phoneErr = ''
      }
    },
    checkName(){
      this.name = this.name.replace(/\s/g, ""); // 去除所有空格
      if (this.name == '' || this.name == null){
        this.nameErr = '必须输入名字'
        this.isNameSure=false
      }
      else if (this.name.length < 2){
        this.nameErr = '姓名格式不对'
        this.isNameSure=false
      }
      else if (!/^[\u4e00-\u9fa5]{0,}$/.test(this.name)){
        this.nameErr = '姓名必须是中文'
        this.isNameSure=false
      }
      else {
        this.nameErr=''
        this.isNameSure=true
      }
    },
    checkPassword(){
      if (this.password == '' || this.password == undefined){
        this.passwordErr = '请输入密码'
        this.isPwordSure = false;
      }else if (this.password.length < 6 || this.password.length > 20){
        this.passwordErr='密码长度为6-20位';
        this.isPwordSure = false;
      } else if (!/^[0-9a-zA-Z]+$/.test(this.password)) {
        this.passwordErr='密码只能使用数字和字母';
        this.isPwordSure = false;
      }else if (!/^(([a-zA-Z]+[0-9]+)|([0-9]+[a-zA-Z]+))[a-zA-Z0-9]*/.test(this.password)){
        this.passwordErr='密码应是数字和字母的混合';
        this.isPwordSure = false;
      }else {
        this.passwordErr='';
        this.isPwordSure = true;
      }
    },
    checkPasswordT(){
      if (this.passwordT == '' || this.passwordT == undefined){
        this.passwordTErr = '请再次输入密码'
        this.isPwordSure = false;
      }
      else if (this.passwordT!=this.password){
        this.passwordTErr='两次输入密码不相同'
        this.isPwordTSure=false
      }else {
        this.passwordTErr=''
        this.isPwordTSure=true
      }
    },
    checkIdCard(){
      this.idCard = this.idCard.replace(/\s/g, ""); // 去除所有空格
      if (this.isId){
        if (this.idCard == '' || this.idCard == null){
          this.idCardErr = '必须输入身份证号码'
          this.isCardSure=false
        }
        else if (!this.idCard.length == 15 || !this.idCard.length == 18){
          this.idCardErr = '身份证号码长度格式不对'
          this.isCardSure=false
        }
        else if (!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(this.idCard)){
          this.idCardErr = '身份证号码格式不对'
          this.isCardSure=false
        }
        else {
          this.idCardErr=''
          this.isCardSure=true
        }
      }
    },
    checkXuehao(){
      if (this.isXue){
        if (this.xuehao == '' || this.xuehao == null){
          this.xuehaoErr='必须输入学号'
          this.isXueHao=false
        }
        // else if (!/^1[1-9]\d{9}$/.test(this.xuehao)) {
        //   this.xuehaoErr = '学号格式不正确'
        //   this.isXueHao = false;
        // }
        else if (this.xuehao.length != 11) {
          this.xuehaoErr = '学号格式错误'
          this.isXueHao = false;
        } else {
          this.isXueHao = true;
          this.xuehaoErr = ''
        }
      }
    }
  },
  watch:{
    phone:{
      deep:true,
      handler(){
        this. checkPhone()
      }
    },
    password:{
      deep:true,
      handler(){
        this. checkPassword()
      }
    },
    passwordT:{
      deep:true,
      handler(){
        this. checkPasswordT()
      }
    },
    xuehao:{
      deep:true,
      handler(){
        this. checkXuehao()
      }
    },
    name:{
      deep:true,
      handler(){
        this. checkName()
      }
    },
    idCard:{
      deep:true,
      handler(){
        this. checkIdCard()
      }
    }
  },
  data(){
    return{
      show: false,
      isChecked: false,
      whichc:1,
      title:'提醒',


      isXue:true,
      isId:false,

      phone: '18814216566',
      phoneErr: '',
      isPhoneSure: false,

      name: '阿松大',
      nameErr: '',
      isNameSure: false,

      password: 'qq123123',
      passwordErr: '',
      isPwordSure:false,

      passwordT: 'qq123123',
      passwordTErr: '',
      isPwordTSure:false,

      xuehao:'20215120222',
      xuehaoErr:'',
      isXueHao:false,

      idCard:'',
      idCardErr:'',
      isCardSure:'',

      errText:'',
    }
  }
}
</script>

<style scoped>
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
  overflow: hidden;
  background: white url('https://chen-1317386995.cos.ap-guangzhou.myqcloud.com/C2c-Music%2Fimage%2FSignUP.png') no-repeat;
  background-size: cover;
}
.go-login{
  position: relative;
  width: 100%;
  height: 106px;
  display: flex;
  justify-content: center;
}
.go-login-to{
  position: relative;
  width: 180px;
  height: 80px;
  margin-left: 20px;
}
.registerPagetop{
  position: relative;
  width: 100%;
  height: 450px;
  display: flex;
  justify-content: center;
  overflow: hidden;
  z-index: 5;
}
.registerPagetop-image{
  position: relative;
  margin: auto;
  width: 350px;
  height: 380px;
  z-index: 8;
}
.registerPagetop-image-animation{
  animation-name: chen02;
  animation-duration: 2.5s;
  animation-timing-function: linear;
  animation-iteration-count: infinite;
// 交替
  animation-direction: alternate;
}
@keyframes chen02 {
  0% {
    filter: drop-shadow(0 0 0 rgb(7, 132, 240));
  }
  30%{
    filter: drop-shadow(0 0 40px rgb(19, 135, 235));
  }
  60%{
    filter: drop-shadow(0 0 60px rgb(37, 142, 232));
  }
  100%{
    filter: drop-shadow(0 0 80px rgb(37, 142, 232));
  }
}
.logoText{
  position: relative;
  width: 90%;
  margin-left: 5%;
  height: 80px;
  display: flex;
  align-items: center;
  z-index: -1;
}
.logoText-image{
  position: relative;
  width: 142px;
  height: 80px;
  z-index: 5;
}
.form-a,.form-b,.form-d{
  position: relative;
  width: 580px;
  height: 82px;
  background-color: white;
  margin: 15px auto;
  display: flex;
  flex-direction: row;
  align-items: center;
  border-radius: 40px;
}
.form-which{
  position: relative;
  width: 580px;
  height: 200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.form-c{
  position: relative;
  width: 580px;
  height: 82px;
  background-color: white;
  margin: 10px auto;
  display: flex;
  flex-direction: row;
  align-items: center;
  border-radius: 40px;
}

.whichId{
  position: relative;
  width: 240px;
  height: 62px;
  border-radius: 40px;
  display: flex;
  flex-direction: row;
  align-content: center;
  justify-content: center;
  transition: all 2.5s ease-in-out;
  background-color: #dbddff;
}
.select{
  position: absolute;
  z-index: 1;
  width: 50%;
  height: 100%;
  border-radius: 40px;
  background-color: #9DBFF3;
  transition: all 0.2s ease-in-out;
}
.select-left{
  left: 0;
}
.select-right{
  left: 122px;
}
.whichId-p{
  z-index: 5;
}
.whichId-p-is{
  font-size: 13px;
  font-weight: bold;
  color: #ec3a4e;
}
.whichId-p-no{
  font-size: 10px;
  color: #9ccce7;
}
.whichId-x{
  position: relative;
  width: 50%;
  height: 100%;
  border-radius:40px 0 0 40px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}
.whichId-i{
  position: relative;
  width: 50%;
  height: 100%;
  border-radius:0 40px 40px 0 ;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}
.form-a{
  margin-top: 40px;
}
.form-f{
  position: relative;
  width: 450px;
  height: 80px;
  background-color: #ffebe4;
  margin: 50px auto;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  border-radius: 40px;
}
.form-f-text{
  font-weight: bold;
  color: #00c8b8;
}
.form-a-image{
  position: relative;
  width: 65px;
  height: 65px;
  margin-left: 25px;
}
.form-a-image-a{
  position: relative;
  width: 60px;
  height: 60px;
  margin-left: 25px;
}
.form-a-input{
  width: 350px;
  height: 50px;
  margin-left: 15px;
  font-weight: bold;
  font-size: 28px;
  outline: none;
}
.form-c-input{
  width: 300px;
  height: 50px;
  margin-left: 15px;
  font-weight: bold;
  font-size: 28px;
  outline: none;
}
</style>
