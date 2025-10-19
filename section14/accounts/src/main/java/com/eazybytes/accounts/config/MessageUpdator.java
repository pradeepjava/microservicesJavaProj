package com.eazybytes.accounts.config;

import com.eazybytes.accounts.dto.AccountMessageTransfer;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.function.Consumer;

@Configuration
public class MessageUpdator {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    AccountsRepository accountsRepository;
    Logger log = LoggerFactory.getLogger(MessageUpdator.class);
    @Bean
    public Consumer<Long>updateDbCommunicationSent(){

        Consumer<Long>consumerAccount = (accountNumber) -> {
            Optional<Accounts> accountInDb=accountsRepository.findById(accountNumber);
            if(accountInDb.isPresent()){
                Optional<Customer> customerOptional= customerRepository.findById(accountInDb.get().getCustomerId());
                if(customerOptional.isPresent()){
                    Customer customer=  customerOptional.get();
                    customer.setNotified(true);
                    customerRepository.save(customer);
                    log.debug("customer notified udpated to db");
                }
            };
            };

        return consumerAccount;
    }
}

