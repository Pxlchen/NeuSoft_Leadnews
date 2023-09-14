<template>
<!--    <div class="login-wapper">-->
<!--        <div class="log-top"><TopBar/></div>-->
<!--        <div class="bg-wapper">-->
<!--            <div class="input-wapper">-->
<!--                <text class="icon">{{userIcon}}</text>-->
<!--                <input v-model="params.phone" return-key-type="defalut"-->
<!--                       autocomplete="off"-->
<!--                       placeholder="请输入手机号"-->
<!--                       class="input"-->
<!--                />-->
<!--            </div>-->
<!--            <div class="input-wapper" key="1">-->
<!--                <text class="icon">{{passIcon}}</text>-->
<!--                <input v-model="params.password" return-key-type="go"-->
<!--                       autocomplete="off"-->
<!--                       type="password"-->
<!--                       placeholder="请输密码"-->
<!--                       class="input"-->
<!--                />-->
<!--            </div>-->
<!--            <text class="button" @click="login"> 登 录 </text>-->
<!--            <div class="more">-->
<!--                <text class="go-register" @click="tip">没有账号，去注册</text>-->
<!--                <router-link to="/home">-->
<!--                    <text class="go-home">先看看，稍后登录</text>-->
<!--                </router-link>-->
<!--            </div>-->
<!--        </div>-->
<!--        <div class="empty"> </div>-->
<!--    </div>-->
    <div class="page">

      <!-- 背景滚动图 -->"
      <image
          src="/static/images/login/2.png"
             class="imagec" mode=""
             :class="isLoginc?'bottom':'topc'"
      >
      </image>

      <router-view></router-view>
<!--  :class="isLogin?'loginBottom-g-top':'loginBottom-g-bottom'"    -->

      <div class="loginBottom-g" @click="goRou()" :class="nowClass">

        <a class="loginBottom-g-text" @click="goRou()">{{nowText}}</a>

      </div>

    </div>
</template>

<script>


import Api from '@/apis/login/api'
    import TopBar from '@/compoents/bars/login_top_bar'
    const modal = weex.requireModule('modal')
    export default {
        name: "login",
        components:{TopBar},
        data(){
            return{
                userIcon : '\uf007',
                passIcon : '\uf023',
                params:{
                    phone:'',
                    password:''
                },

              isLogin:true,
              isLoginc:true,
              isRegister:false,
              isRegisterc:false,
              uid:'',
              upassword:'',
              umail:'',
              show: false,
              showb:false,
              content:'',//模态框消息
              contentb:'',//模态框消息

              //注册默认值
              userPoster:'https://chen-1317386995.cos.ap-guangzhou.myqcloud.com/C2c-Music/user.webp',

              nowClass:'loginBottom-g-bottom',
              nowText:'前往注册'
            }
        },
        created(){
            Api.setVue(this);
        },
        methods:{
          goRou(){
            let r = '/login/register'
            let u = '/login/user'
            let rc='loginBottom-g-top'
            let uc='loginBottom-g-bottom'
            let router
            if (this.isLoginc){router=r}
            else if (this.isRegisterc){router=u}
            this.isLoginc=!this.isLoginc,
            this.isRegisterc=!this.isRegisterc
              setTimeout(() => {
                if (this.isLoginc){this.nowClass=uc,this.nowText='前往注册'}
                else if (this.isRegisterc){this.nowClass=rc,this.nowText='前往登录'}
                this.$router.push(router);
              }, 700);

          },
            tip : function(){
                modal.toast({ message:'该功能暂未实现！',duration:3})
            },
            login:function(){

                if(this.params.phone==''||this.params.password==''){
                    modal.toast({
                        message:'请输入用户名或密码',
                        duration:3
                    })
                }else{
                    // alert(JSON.stringify(this.params))
                    Api.login(this.params).then(d=>{
                        if(d.code==200){
                          this.$store.setToken(d.data.token)
                          this.$store.setUserInfo(JSON.stringify(d.data.user))
                          console.log(JSON.stringify(d.data.user))
                          this.$router.push("/home")
                            this.$store.setToken(d.data.token)
							// alert(d.data.token)
                            this.$router.push("/home")
                        }else{
                            modal.toast({ message:'用户或密码错误',duration:3})
                        }
                    }).catch(e=>{
                        console.log(e)
                    })
                }
            },
        }
    }
</script>

<style lang="less" scoped>
    @import '../../styles/common';
    .login-wapper {
        flex: 1;
        width: 750px;
        flex-direction: column;
        background-color: #ffffff;
    }
    .bg-wapper{
        margin-top: 35px;
        margin-bottom: 35px;
        width: 750px;
        justify-content: center;
        align-items: center;
    }
    .log-top{
        width: 750px;
        height: 90px;
    }
    .empty{
        flex: 1;
        background-color: #f5f7f9;
    }
    .title{
        font-size: 52px;
        color: @title-color;
        margin: 55px 0px;
    }
    .icon{
        color: @icon-color;
        font-size: 32px;
    }
    .input-wapper {
        flex-direction: row;
        width: 700px;
        border-bottom-width: 1px;
        border-bottom-color: #eeeeee;
        padding: 15px 0px;
        align-items: center;
        margin: 15px 0px;
    }
    .more{
        margin-top: 35px;
        flex-direction: row;
    }
    .go-register{
        font-size: 24px;
        color: @placeholder-color;
        text-decoration: underline;
        margin-right: 35px;
    }
    .go-home{
        font-size: 24px;
        color: @placeholder-color;
        text-decoration: underline;
    }
    .input{
        border: none;
        flex: 1;
        line-height: 30px;
        font-size: 28px;
        color: @title-color;
        background-color: transparent;
        margin-left: 20px;
        placeholder-color:@placeholder-color;
    }
    .input :active,.input :hover{
        background-color: transparent;
    }
    .button{
        margin-top:60px;
        background-color: #6db4fb;
        width: 690px;
        height: 70px;
        border-radius: 10px;
        color: @bg-white;
        font-size: 32px;
        text-align: center;
        line-height: 70px;
    }




    .loginBottom-g-text{
      font-weight: bold;
      font-size: 25px;
      letter-spacing: 2px;
      color: white;
    }
    .loginBottom-g{
      position: absolute;
      width: 280px;
      height:50px;
      background-color: #77eaed;
      margin-left: 220px;
      display: flex;
      align-items: center;
      justify-content: center;
      border-radius: 40px;
      z-index: 2;
    }
    .loginBottom-g-top{
      margin-top: 35px;
    }
    .loginBottom-g-bottom{
      bottom: 80px;
      background-color: #77eaed;
    }
    .imagec{
      position: absolute;
      width:1990px ;
      height: 1950px;
      z-index: 9;
      left: -600px;
      transition: all 2s;
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
