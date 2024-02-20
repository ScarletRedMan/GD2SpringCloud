package ru.scarletredman.gateway.controller.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.scarletredman.gateway.controller.response.serializer.GeometryDashResponseSerializer;

@JsonSerialize(using = GeometryDashResponseSerializer.class)
public class LoginResponse implements GeometryDashResponseSerializer.Response {

    private final long accountId;
    private final int errorId;

    private LoginResponse(long accountId, int errorId) {
        this.accountId = accountId;
        this.errorId = errorId;
    }

    public static LoginResponse success(long accountId) {
        return new LoginResponse(accountId, 0);
    }

    public static LoginResponse error(Reason reason) {
        return new LoginResponse(-1, reason.code);
    }

    @Override
    public String getResponse() {
        if (accountId == -1) return Integer.toString(errorId);

        return accountId + "," + accountId;
    }

    public enum Reason {
        LOGIN_FAILED(-1),
        BANNED(-12);

        private final int code;

        Reason(int code) {
            this.code = code;
        }
    }
}
