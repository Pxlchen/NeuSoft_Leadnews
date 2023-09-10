package com.neusoft.common.baiduyun;


import com.baidu.aip.contentcensor.AipContentCensor;
import com.neusoft.common.baiduyun.util.CensorResult;
import com.neusoft.common.baiduyun.util.ContentWithCensorStateEnum;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "baidu.examine")
public class AipContentCensorClientConfig {
    /**
     * 百度云审核的AppID
     */
    private String AppID;
    /**
     * 百度云审核的Api Key
     */
    private String API_Key;
    /**
     * 百度云审核的Secret Key
     */
    private String Secret_Key;

    /**
     * 百度文本审核，识别审核结果的JSON KEY
     */
    final public static String CENSOR_CONCLUSION_TYPE_KEY = "conclusionType";

    AipContentCensor commonTextCensorClient() {
        /**
         * 可以选择在客户端中添加参数，参考 https://ai.baidu.com/ai-doc/ANTIPORN/ik3h6xdze
         * 如：
         *         // 可选：设置网络连接参数
         *         client.setConnectionTimeoutInMillis(2000);
         *         client.setSocketTimeoutInMillis(60000);
         *
         *         // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
         *         client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
         *         client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
         *
         *         // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
         *         // 也可以直接通过jvm启动参数设置此环境变量
         *         System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");
         */
        return new AipContentCensor(AppID, API_Key, Secret_Key);
    }

    /**
     * 获取常规文本审核结果
     * @param content
     * @return
     */
    public Map getCommonTextCensorResult(String content){
        AipContentCensor commonTextCensorClient = commonTextCensorClient();
//        System.out.println(AppID+"======"+API_Key+"======"+Secret_Key);
//如果内容为空，则直接返回
        if (content == null || content.isEmpty()) {
            return null;
        }

        try {
            Map<String, String> resultMap = new HashMap<>();
            JSONObject response = commonTextCensorClient.textCensorUserDefined(content);
            int conclusionType =response.getInt(CENSOR_CONCLUSION_TYPE_KEY);
            if (conclusionType == 2 || conclusionType ==4) {
                resultMap.put(CENSOR_CONCLUSION_TYPE_KEY, String.valueOf(2));//1.合规 2.不合规 3.不确定
                return resultMap;
            }
            else {
                resultMap.put(CENSOR_CONCLUSION_TYPE_KEY, String.valueOf(conclusionType));
                return resultMap;
            }
        } catch (Exception exception) {
            System.out.println(exception);
            return null;
        }
    }

    /**
     * 获取照片审核结果
     *
     * @param imageList 图片Url
     * @return 百度图片审核JSON
     */
    public Map getImageCensorResult(List<byte[]> imageList) {
        AipContentCensor commonTextCensorClient = commonTextCensorClient();
        Map<String, String> resultMap = new HashMap<>();
        //如果内容为空，则直接返回
        if (imageList == null || imageList.size() == 0) {
            return null;
        }
        List<JSONObject > responseList = new ArrayList<JSONObject >();
        int conclusionType = -1;
        for (byte[] bytes : imageList) {
            try {
                JSONObject response = commonTextCensorClient.imageCensorUserDefined(bytes,null);
                conclusionType =response.getInt(CENSOR_CONCLUSION_TYPE_KEY);
                if (conclusionType == 2 || conclusionType ==4) {
                    resultMap.put(CENSOR_CONCLUSION_TYPE_KEY, String.valueOf(2));//1.合规 2.不合规 3.不确定
                    return resultMap;
                }
                if (conclusionType == 3) {
                    resultMap.put(CENSOR_CONCLUSION_TYPE_KEY, String.valueOf(conclusionType));
                    return resultMap;
                }
                System.out.println(response);
            } catch (Exception exception) {
                System.out.println(exception);
                return null;
            }

        }
        resultMap.put(CENSOR_CONCLUSION_TYPE_KEY, String.valueOf(conclusionType));
        return resultMap;
    }




    /**
     * 获取审核结果
     *
     * @param clientJsonObject 百度审核的JSON字段
     * @return 审核结果
     */
    private CensorResult getCensorResult(JSONObject clientJsonObject) {

        //获取代表审核结果的字段
        //审核结果类型，可取值1.合规，2.不合规，3.疑似，4.审核失败
        int conclusionType;

        //如果是null就直接判定为失败
        if (clientJsonObject == null) {
            conclusionType = 4;
        } else {
            conclusionType = clientJsonObject.getInt(CENSOR_CONCLUSION_TYPE_KEY);
        }

        try {
            ContentWithCensorStateEnum result;

            switch (conclusionType) {
                case 1:
                    //合规情况
                    result = ContentWithCensorStateEnum.ADD;
                    break;
                case 2:
                    //不合规情况
                    result = ContentWithCensorStateEnum.CENSOR_FAIL;
                    break;
                case 3:
                    //疑似不合规
                    result = ContentWithCensorStateEnum.CENSOR_SUSPECT;
                    break;
                default:
                    //审核失败和其他情况，都归结到censor_error上去
                    result = ContentWithCensorStateEnum.CENSOR_ERROR;
                    break;
            }

            //过审要求：只能是合规情况
            //解释：因为百度云控制台是可以调节不合规和疑似不合规的参数值的，因此这里只写合规情况就可以了
            boolean isPass = result == ContentWithCensorStateEnum.ADD;

            return new CensorResult(isPass, result, clientJsonObject != null ? clientJsonObject.toString() : null, null);

        } catch (Exception exception) {
            System.out.println(exception);
            //如果出错，就直接返回true
            return new CensorResult(true, null, null, null);
        }

    }

}

