package com.lgbi.springbootinit.openai;


import com.unfbx.chatgpt.OpenAiClient;
import com.unfbx.chatgpt.entity.chat.BaseMessage;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import com.unfbx.chatgpt.entity.chat.ChatCompletionResponse;
import com.unfbx.chatgpt.entity.chat.Message;
import com.unfbx.chatgpt.interceptor.OpenAILogger;
import com.unfbx.chatgpt.interceptor.OpenAiResponseInterceptor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class ChatGptService {


    public String doChat(String userMessage) {
        OpenAiClient openAiClient = getOpenAiClient();
        final String systemPrompt = "你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容：\n" +
                "分析需求：\n" + "{数据分析的需求或者目标}\n" +
                "原始数据：\n" + "{csv格式的原始数据，用,作为分隔符}\n" +
                "请根据这两部分内容，按照以下指定格式生成内容,其中包括生成分隔符\"【【【【【\"，同时分析结论请直接给出（此外不要输出任何多余的开头、结尾、注释）\n" +
                "【【【【【\n" +
                "{前端 Echarts V5 的 option 配置对象js代码(json格式)，代码需要包括title.text（需要该图的名称）部分、图例部分（即legend元素，文字部分应为黑色，图例线颜色与图例颜色相同），合理地将数据进行可视化，图表要求：1、若图表有轴线请将轴线画出，如y轴线，颜色为黑色2、坐标字体为黑色，不要生成任何多余的内容，比如注释}\n" +
                "【【【【【\n" +
                "{请直接明确的数据分析结论、越详细越好（字数越多越好），不要生成多余的注释}";
        //聊天模型：gpt-3.5
        Message systemMessage = Message.builder().role(BaseMessage.Role.USER).content(systemPrompt).build();
        Message message = Message.builder().role(Message.Role.USER).content(userMessage).build();
        List<Message> lists = new ArrayList<>();
        lists.add(systemMessage);
        lists.add(message);
        ChatCompletion chatCompletion = ChatCompletion
                .builder()
                .messages(lists)
                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
                .build();
        ChatCompletionResponse chatCompletionResponse = openAiClient.chatCompletion(chatCompletion);
        StringBuilder builder = new StringBuilder();
        chatCompletionResponse.getChoices().forEach(e -> {
            String result = e.getMessage().getContent();
            builder.append(result);
        });
        return builder.toString();
    }


    private OpenAiClient getOpenAiClient() {
        //可以为null
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new OpenAILogger());
        //！！！！千万别再生产或者测试环境打开BODY级别日志！！！！
        //！！！生产或者测试环境建议设置为这三种级别：NONE,BASIC,HEADERS,！！！
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        String openaiApiKey = "sk-rXTosY84NnqPrlgH1a7d5954CeAd4b0e9aC7C05a293fCb17";
        System.setProperty("openai.api_key", openaiApiKey);
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
//                .proxy(proxy)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new OpenAiResponseInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        return OpenAiClient.builder()
                //支持多key传入，请求时候随机选择
                //ChatGpt开发密钥
                .apiKey(Arrays.asList("sk-rXTosY84NnqPrlgH1a7d5954CeAd4b0e9aC7C05a293fCb17"))
                //自定义key的获取策略：默认KeyRandomStrategy
                .keyStrategy(new KeyRandomStrategy())
                .okHttpClient(okHttpClient)
                //自己做了代理就传代理地址，没有可不不传
                //测试代理地址https://dgr.life/
                //.apiHost("https://dgr.life/")
                .build();
    }

}