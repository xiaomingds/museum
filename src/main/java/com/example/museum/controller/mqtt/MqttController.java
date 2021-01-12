//package com.example.museum.controller.mqtt;
//
//import com.example.museum.entity.ApiResult;
//import com.example.museum.mqtt.MqttPushClient;
//import com.example.museum.mqtt.PushCallback;
//import com.example.museum.util.ApiResultHandler;
//import io.swagger.annotations.Api;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
///**
// * @ClassName MqttController
// * @Author xiaomingds
// * @Date 2021/1/1 21:05
// **/
//@CrossOrigin
//@RestController
//@Api(tags = "接口路径以及名称")
//public class MqttController {
//    @Autowired
//    private MqttPushClient mqttPushClient;
//    @Autowired
//    private PushCallback pushCallback;
//
//    @GetMapping(value = "/publishTopic")
//    public ApiResult publishTopic() {
//        String topicString = "test";
//        mqttPushClient.publish(0, false, topicString, "测试一下发布消息");
//        return ApiResultHandler.buildApiResult(200, "测试发布成功", "");
//    }
//
//    // 发送自定义消息内容（使用默认主题）
//    @PostMapping("/publishTopic/{data}")
//    public String test1(@PathVariable("data") String data) {
//        String topicString = "test";
//        mqttPushClient.publish(0,false,topicString, data);
//        return "ok";
//    }
//
//    // 发送自定义消息内容，且指定主题
//    @RequestMapping("/publishTopic/{topic}/{data}")
//    public String test2(@PathVariable("topic") String topic, @PathVariable("data") String data) {
//        mqttPushClient.publish(0,false,topic, data);
//        return "ok";
//    }
//
//}
