package com.example.Buoi_1.exception;

public enum ErrorCode {
    SUCSSES(1000, "Thành công!"),
    USER_EXISTED(1001, "Người dùng đã tồn tại"),
    USER_NOT_EXISTED(1002, "Người dùng không tồn tại"),
    USER_NOT_FOUND(1003,"Không tìm thấy người dùng"),
    INVALID_KEY(1004, "Dữ liệu không hợp lệ"),
    UNAUTHENTICATED(1005, "Chưa xác thực"),
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi server nội bộ")
    ;

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
