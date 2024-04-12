package clofi.codeython.evaluate;

import lombok.Getter;

@Getter
public enum Language {
    JAVASCRIPT("javascript", "18.15.0"), JAVA("java", "15.0.2"), PYTHON("python", "3.10.0");

    private final String language;
    private final String version;

    Language(String language, String version) {
        this.language = language;
        this.version = version;
    }
}
