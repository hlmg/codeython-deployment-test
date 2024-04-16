package clofi.codeython.problem.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.converter.HttpMessageNotReadableException;


public enum LanguageType {
    JAVA, JAVASCRIPT, PYTHON,
    /*
     * Not use. just for test or future implement.
     */
    CSharp;


    @JsonCreator
    public static LanguageType forValue(String value) {
        for (LanguageType languageType : LanguageType.values()) {
            if (languageType.name().equalsIgnoreCase(value)) {
                return languageType;
            }
        }
        throw new IllegalArgumentException(value + "(은)는 지원하지 않는 언어 종류입니다.");
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
