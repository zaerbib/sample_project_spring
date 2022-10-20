package com.apress.messaging.listener;

import com.apress.messaging.annotation.Log;
import com.apress.messaging.event.CurrencyEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class RateEventListener {
    @TransactionalEventListener
    @Log(printParamsValues=true,callMethodWithNoParamsToString="getRate")
    public void processEvent(CurrencyEvent event){ }
}
