package com.lms.project.dto;
public class UserContextHolder {
    private static final ThreadLocal<UserContextDto> USER_CONTEXT = new ThreadLocal<>();
    public static void setUserDto(UserContextDto userContextDTO) {
        USER_CONTEXT.set(userContextDTO);
    }
    public static UserContextDto getUserDto() {
        return USER_CONTEXT.get();
    }
    public static void clear() {
        USER_CONTEXT.remove();
    }
}
