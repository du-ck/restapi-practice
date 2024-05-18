package com.gritstandard.project.common.util;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * data 파싱 유틸 클래스
 */
public final class ParsingUtil {

    /**
     * String,String ... 로 이루어진 GroupNumber  를 파싱하기 위한 메서드
     * @param groupNumbers
     * @return
     */
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
