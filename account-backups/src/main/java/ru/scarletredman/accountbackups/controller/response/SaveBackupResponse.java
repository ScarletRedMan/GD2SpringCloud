package ru.scarletredman.accountbackups.controller.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.scarletredman.accountbackups.controller.response.serializer.GeometryDashResponseSerializer;

@JsonSerialize(using = GeometryDashResponseSerializer.class)
public class SaveBackupResponse implements GeometryDashResponseSerializer.Response {

    private final int code;

    private SaveBackupResponse(int code) {
        this.code = code;
    }

    public static SaveBackupResponse success() {
        return new SaveBackupResponse(1);
    }

    public static SaveBackupResponse fail() {
        return new SaveBackupResponse(-1);
    }

    @Override
    public String getResponse() {
        return Integer.toString(code);
    }
}
