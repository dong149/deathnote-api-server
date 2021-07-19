package com.rest.api.exception.report;

public class ReportNotFoundException extends RuntimeException {
    public ReportNotFoundException() {
    }

    public ReportNotFoundException(Long id) {
        super("Report id:" + id + "를 찾을 수 없습니다.");
    }

    public ReportNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ReportNotFoundException(String msg) {
        super(msg);
    }
}
