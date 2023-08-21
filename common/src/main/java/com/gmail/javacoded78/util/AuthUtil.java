package com.gmail.javacoded78.util;

import com.gmail.javacoded78.exception.ApiRequestException;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;

@UtilityClass
public class AuthUtil {

    public Long getAuthenticatedUserId() {
        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        if (attribs == null) {
            throw new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        HttpServletRequest request = ((ServletRequestAttributes) attribs).getRequest();
        String userId = request.getHeader(AUTH_USER_ID_HEADER);
        if (userId == null) {
            throw new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return Long.parseLong(userId);
    }
}
