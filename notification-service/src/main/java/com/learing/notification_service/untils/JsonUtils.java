package com.learing.notification_service.untils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learing.notification_service.event.UserRegisteredEvent;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static UserRegisteredEvent fromJson(String json){
        try {
            return objectMapper.readValue(json, UserRegisteredEvent.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
