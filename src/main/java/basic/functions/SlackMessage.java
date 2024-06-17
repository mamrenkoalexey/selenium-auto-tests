package basic.functions;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

import java.io.IOException;

import static basic.config.Config.SLACK_TOKEN;

public class SlackMessage {

    public void sendMessage() {
        Slack slack = Slack.getInstance();

        MethodsClient methods = slack.methods(SLACK_TOKEN);

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel("#random")
                .text(":wave: Hi from a bot written in Java!")
                .build();

        try {
            ChatPostMessageResponse response = methods.chatPostMessage(request);
            System.out.println(response);
        } catch (IOException | SlackApiException e) {
            throw new RuntimeException(e);
        }
    }
}
