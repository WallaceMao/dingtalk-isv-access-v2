package com.rishiqing.dingtalk.web.dingcallback.event;

import com.google.common.eventbus.EventBus;
import com.rishiqing.dingtalk.api.event.EventListener;

import java.util.Map;
import java.util.Set;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 20:40
 */
public class CallbackListenerInitService {
    private Map<EventBus,EventListener> eventListenerMap;

    public Map<EventBus, EventListener> getEventListenerMap() {
        return eventListenerMap;
    }

    public void setEventListenerMap(Map<EventBus, EventListener> eventListenerMap) {
        this.eventListenerMap = eventListenerMap;
    }

    public void register(){
        Set<EventBus> eventBusSet = eventListenerMap.keySet();
        for(EventBus eventBus : eventBusSet){
            eventBus.register(eventListenerMap.get(eventBus));
        }
    }
}
