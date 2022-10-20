package com.apress.messaging.listener;

import com.apress.messaging.annotation.Log;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

@Component
public class RestApiEventListener implements ApplicationListener<ApplicationEvent> {
    private static final String LATEST = "/currency/latest";
    // private final CounterService counterService;

   /* public RestApiEventListener(CounterService counterService) {
        this.counterService = counterService;
    }*/

    @Override
    @Log(printParamsValues = true)
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof ServletRequestHandledEvent) {
            if(((ServletRequestHandledEvent)event).getRequestUrl().equals(LATEST)){
                // counterService.increment("url.currency.latest.hits");
            }
        }
    }
}
