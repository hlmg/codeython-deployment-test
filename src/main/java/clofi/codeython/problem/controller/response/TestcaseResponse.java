package clofi.codeython.problem.controller.response;

import lombok.Getter;

import java.util.List;


public record TestcaseResponse(
        List<String> inputCase, String outputCase, String description
) {
}
