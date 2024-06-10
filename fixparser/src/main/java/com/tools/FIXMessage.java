package com.tools;

import java.util.Map;
import java.util.HashMap;

public class FIXMessage {

    private Map<String, String> tags;

    public FIXMessage() {
        tags = new HashMap<String, String>();
    }

    public void addTag(String tag, String value) {
        tags.put(tag, value);
    }

    public String getTagValue(String tag) {
        return tags.get(tag);
    }

    @Override
    public String toString() {
        return "FIXMessage{" +
                "tags=" + tags +
                "}";
    }
}
