package com.adelean.xmlReader.models;

import java.util.HashMap;
import java.util.Map;

public class SmartphoneSpec {
    private final Map<String, String> specsMap = new HashMap<>();

    public void add(String elementName, String value) {

        /*
            it_flash_type -> itFlashType
         */
        String elementSanitized = elementName
                .replace("it_", "");

        specsMap.put(elementSanitized, value);
    }


    public Map<String, String> getSpecsMap() {
        return specsMap;
    }
}
