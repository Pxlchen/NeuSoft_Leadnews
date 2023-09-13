<template>
    <div class="user">

      <div class="top">
        <div class="top-left">

          <image src="../../../static/user/morec.png"></image>
        </div>
        <div class="top-right">
          <image src=".../../../static/user/search-user.png" mode=""></image>
        </div>
      </div>

    </div>

</template>

<script>
export default {
  name: 'User-Home',
  mounted() {
    let a;
    this.$store.getUserInfo().then(resp =>{
      this.user=JSON.parse(resp)
      console.log(this.user)
    }
    )
  },

  data(){
    return{
      user:{
        certification:null,
        createdTime:0,
        flag:0,   //用户类型
        id:"",
        identityAuthentication:null,  //身份认证
        image:null,   //头像
        name:"",
        phone:"",
        sex:0,
      }
    }
  },
  methods:{
  }
}

</script>

<style scoped>
.user{
  position: relative;
  top: 0;
  left: 0;
  width: 1000vw; /* 当前屏幕的宽度 */
  height: 100vh; /* 当前屏幕的高度 */
  overflow-y: hidden; /* 禁止垂直方向上的滚动 */
  background-color: #00B4FF;
}


.top{
  position: relative;
  width: 390px;
  height: 50px;
  margin: auto;
}
.top-left{
  float: left;
  width: 50px;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.top-left image{
  width: 30px;
  height: 30px;
}
.top-right{
  float: right;
  width: 50px;
  height: 50px;

  display: flex;
  align-items: center;
  justify-content: center;
}
.top-right image{
  width: 30px;
  height: 30px;
}

.information{
  position: relative;
  width: 385px;
  height: 180px;
  margin: auto;
  background-color: #00B4FF;
}
  .avatr{
    position: absolute;
    height: 90px;
    width: 90px;
    z-index: 2;
    top: 21%;
    left: 50%;
    transform: translate(-50%, -50%);
    border-radius: 50px;
  }
  .avatr image{
    width: 90px;
    height: 90px;
    border-radius: 50px;
  }
  .person-information{
    position: relative;
    width: 100%;
    height: 118px;
    background-color: #e3ecf4  ;
    border-radius: 20px;
    top: 50px;
    z-index: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;

  }
  .person-information-b p{
    font-size: 10px;
    color:#a19f9d  ;
  }
  .person-information-a{
    position: relative;
    width: 140px;
    height: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 20px;
  }
  .person-information-b{
    position: relative;
    width: 150px;
    height: 20px;
    display: flex;
    align-items: center;
    justify-content: center;
  }


.menu {
  position: relative;
  width: 385px;
  height: 180px;
  background-color: white;
  margin: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  flex-wrap: wrap;
  border-radius: 15px;
}
  .menu-button{
    position: relative;
    width: 90px;
    height: 86px;
    margin-top: 2px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }
.menu-button image{
  width: 30px;
  height: 30px;
}
.menu-button p{
  margin-top: 6px;
  font-size: 12px;
  color: #8a8886   ;
}

.love{
  position: relative;
  width: 385px;
  height: 90px;
  background-color: white;
  margin: auto;
  display: flex;
  margin-top: 15px;
  border-radius: 15px;
  display: flex;
  align-items: center;
  flex-direction: row;}
  .love-a{
    position: relative;
    width: 70px;
    height: 70px;
    margin-left: 20px;
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    image{
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
  }
  .love-b{
    position: relative;
    width: 195px;
    height: 70px;
    margin-left: 5px;
    display: flex;
    justify-content: center;
    flex-direction: column;
    h3{
      margin-left: 9px;
      font-size: 13px;
    }
    p{
      margin-left: 9px;
      margin-top: 5px;
      font-size: 9px;
      color: #8a8886;
    }
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
    image{
      width: 18px;
      height: 18px;
    }
    p{
      margin-left: 2px;
      font-size: 9px;
    }
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
  .collect-top{
    position: relative;
    width: 380px;
    height: 40px;
    display: flex;
    align-items: center;
    p{
      margin-left: 10px;
      font-size: 10px;
    }
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
      .item image{
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

}


</style>
