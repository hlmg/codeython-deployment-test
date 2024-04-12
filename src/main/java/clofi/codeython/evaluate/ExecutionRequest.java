package clofi.codeython.evaluate;

import java.util.List;
import lombok.Getter;

@Getter
public class ExecutionRequest {
    private final String language;
    private final String version;
    private final List<FileContent> files;

    public ExecutionRequest(String language, String version, List<String> contents) {
        this.language = language;
        this.version = version;
        this.files = contents.stream().map(FileContent::new).toList();
    }

    @Getter
    private static class FileContent {
        private final String content;

        public FileContent(String content) {
            this.content = content;
        }
    }
}
