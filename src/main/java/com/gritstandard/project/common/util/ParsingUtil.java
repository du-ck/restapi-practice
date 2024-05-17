package com.gritstandard.project.common.util;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public final class ParsingUtil {

    public static List<String> ParsingGroupNumber(String groupNumbers) {
        List<String> resultList = new ArrayList<>();

        if (StringUtils.hasText(groupNumbers)) {
            String[] ParsingGroupNumber = groupNumbers.split(",");
            for (String groupNumber : ParsingGroupNumber) {
                resultList.add(groupNumber.trim());
            }
        }

        return resultList;
    }
}
