package com.kuznetsovka.trueshop.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.channel.DirectChannel;

@Configuration
@ImportResource("classpath:/integration/http-sells-integration.xml")
public class SellsIntegrationConfig {
    private DirectChannel sellsChannel;

    public SellsIntegrationConfig(@Qualifier("sellsChannel") DirectChannel sellsChannel) {
        this.sellsChannel = sellsChannel;
    }

    public DirectChannel getSellsChannel(){
        return sellsChannel;
    }
}
