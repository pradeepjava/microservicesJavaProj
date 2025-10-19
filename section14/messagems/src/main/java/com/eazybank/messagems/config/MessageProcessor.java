package com.eazybank.messagems.config;

import com.eazybank.messagems.dto.AccountMessageTransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Function;

@Configuration
public class MessageProcessor {
    Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    @Bean
    public Function<AccountMessageTransfer, AccountMessageTransfer> processEmail() {
        return accountMessageTransfer -> {
            logger.debug("Email Sent to customer with account number {} successfully", accountMessageTransfer.getAccountNumber());
            return accountMessageTransfer;
        };

    }


    @Bean
    public Function<AccountMessageTransfer, Long> processSMS() {
        return accountMessageTransfer -> {
            logger.debug("SMS Sent to customer with account number {} successfully", accountMessageTransfer.getAccountNumber());
            return accountMessageTransfer.getAccountNumber();
        };
    }

    @Bean
    public Function<Message<AccountMessageTransfer>, Message<AccountMessageTransfer>> processEmailWithHeader() {
        return accountMessageTransfer -> {
            logger.debug("received header ABC", accountMessageTransfer.getHeaders().get("ABC"));
            logger.debug("Email Sent to customer with account number {} successfully", accountMessageTransfer.getPayload().getAccountNumber());
            return MessageBuilder.withPayload(accountMessageTransfer.getPayload())
                    .setHeader("abc", "abc")
                    .setHeader("xyz", "xyz")
                    //issue with status code, we need to return ResponseEntity instead of Message.
                    .build();
        };
    }
}
