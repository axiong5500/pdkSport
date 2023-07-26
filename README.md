# pdkSport
微信小程序搜索【跑得快运动】修改微信步数，无广告，一步到位

# 接口说明
|请求参数|	必选|	参数类型|	说明|
|---|---|---|---|
|usr|	必选|	string |	账号|
|psw|	必选|	string |	密码|
|bs|	必选|	string |	步数|

|响应参数|	必选|	参数类型|	说明|
|---|---|---|---|
|result|	必选|	string |	状态标识|
|message|	必选|	string |	状态message|



# 接口调用

```javascript
Api:

localhost:8090/pdksport/wxsports/refushStep

请求body:

{
    "usr":"6666666",
    "psw":"666666666",
    "bs":"3300"
}

响应Response：

{
    "result": 200,
    "message": "数据提交成功"
}
```
