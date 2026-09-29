package com.detrox.advisiors_app.advisors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import reactor.core.publisher.Flux;


public class TokenPrintAdvisor implements CallAdvisor, StreamAdvisor {


    private Logger logger = LoggerFactory.getLogger(TokenPrintAdvisor.class);

    @Override
    public ChatClientResponse adviseCall(
            ChatClientRequest chatClientRequest,
            CallAdvisorChain callAdvisorChain) {

        logger.info("My Token Print Advisor is called");

        // Request
        logger.info("Request: {}",
                chatClientRequest.prompt().getContents());

        // Call next advisor / model
        ChatClientResponse chatClientResponse =
                callAdvisorChain.nextCall(chatClientRequest);

        logger.info("Token advisor: Response received from the model");

        // Response
        logger.info("Response: {}",
                chatClientResponse
                        .chatResponse()
                        .getResult()
                        .getOutput()
                        .getText());

        // Token usage
        if (chatClientResponse.chatResponse().getMetadata().getUsage() != null) {
            logger.info("Total Token consumed: {}",
                    chatClientResponse
                            .chatResponse()
                            .getMetadata()
                            .getUsage()
                            .getTotalTokens());
        }

        return chatClientResponse;
    }

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest,
                                                 StreamAdvisorChain streamAdvisorChain) {
        return null;
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
