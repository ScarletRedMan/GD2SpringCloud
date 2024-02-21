package ru.scarletredman.accountbackups.controller.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.scarletredman.accountbackups.controller.response.serializer.GeometryDashResponseSerializer;

@JsonSerialize(using = GeometryDashResponseSerializer.class)
public class LoadBackupResponse implements GeometryDashResponseSerializer.Response {

    private final String data;

    private LoadBackupResponse(String data) {
        this.data = data;
    }

    public static LoadBackupResponse success(String data) {
        return new LoadBackupResponse(data);
    }

    public static LoadBackupResponse fail() {
        return new LoadBackupResponse(null);
    }

    @Override
    public String getResponse() {
        if (data == null) return "-1";
        return data + ";21;30;a;a";
    }
}
