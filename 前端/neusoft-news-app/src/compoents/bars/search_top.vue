<template>
    <div class="bar_bg">
        <Search type="search"
                autofocus="autofocus"
                @onSubmit="onSubmit"
                @onInput="onInput"
                @onBlur="onBlur"
                @onChange="onChange"
                :radius="62"
                :icon="icon"
                placeholder="请输入搜索关键字..."
                rightWidth="0" />
        <text class="icon" @click="back">取消</text>
    </div>
</template>

<script>
    import Search from '@/compoents/inputs/search';
    export default {
        name: "search_top_bar",
        components: {Search},
        data(){
            return {
                icon:'\uF002',
                autofocus:true
            }
        },
        methods:{
            back : function(){
                this.$router.back();
            },
            onSubmit : function(val){
                this.$router.push({name:'search_result',params:{'keyword':val}})
            },
            onInput:function(val){
                this.$emit("onInput",val)
            },
            onChange:function(val){
                this.$emit("onChange",val)
            },
            onBlur:function(val){
                this.$emit("onBlur",val)
            }
        }
    }
</script>

<style lang="less" scoped>
    @import '../../styles/common';
    .bar_bg{
        width: @screen-width;
        flex-direction: row;
        background-color: #d7eafc;

        height: @top-height;
        align-items: center;
        padding: 5px 30px 8px;
    }
    .icon{
        color: #ffffff;
        font-size: 32px;
        padding:0px 0px 0px 20px ;
    }
</style>
