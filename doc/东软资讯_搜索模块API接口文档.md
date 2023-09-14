# 东软资讯-搜索模块API接口文档


**简介**:东软资讯-搜索模块API接口文档


**HOST**:localhost:51804


**联系人**:吴杰爽


**Version**:1.0


**接口路径**:/v2/api-docs?group=1.0


[TOC]






# app端搜索关键词联想


## 搜索联想词


**接口地址**:`/api/v1/associate/search`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
	"fromIndex": 0,
	"minBehotTime": "",
	"pageNum": 0,
	"pageSize": 0,
	"searchWords": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | in    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|dto|dto|body|true|UserSearchDto|UserSearchDto|
|&emsp;&emsp;fromIndex|||false|integer(int32)||
|&emsp;&emsp;minBehotTime|最小时间||false|string(date-time)||
|&emsp;&emsp;pageNum|当前页||true|integer(int32)||
|&emsp;&emsp;pageSize|分页条数||true|integer(int32)||
|&emsp;&emsp;searchWords|搜索关键字||true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResponseResult|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|errorMessage||string||
|host||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"errorMessage": "",
	"host": ""
}
```


# app端文章分页检索


## 文章分页检索


**接口地址**:`/api/v1/article/search/search`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
	"fromIndex": 0,
	"minBehotTime": "",
	"pageNum": 0,
	"pageSize": 0,
	"searchWords": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | in    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|dto|dto|body|true|UserSearchDto|UserSearchDto|
|&emsp;&emsp;fromIndex|||false|integer(int32)||
|&emsp;&emsp;minBehotTime|最小时间||false|string(date-time)||
|&emsp;&emsp;pageNum|当前页||true|integer(int32)||
|&emsp;&emsp;pageSize|分页条数||true|integer(int32)||
|&emsp;&emsp;searchWords|搜索关键字||true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResponseResult|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|errorMessage||string||
|host||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"errorMessage": "",
	"host": ""
}
```


# app端用户搜索记录


## 删除搜索记录


**接口地址**:`/api/v1/history/del`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
	"id": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | in    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|dto|dto|body|true|HistorySearchDto|HistorySearchDto|
|&emsp;&emsp;id|搜索历史记录id||true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResponseResult|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|errorMessage||string||
|host||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"errorMessage": "",
	"host": ""
}
```


## 查询搜索记录


**接口地址**:`/api/v1/history/load`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|ResponseResult|
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code||integer(int32)|integer(int32)|
|data||object||
|errorMessage||string||
|host||string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"errorMessage": "",
	"host": ""
}
```