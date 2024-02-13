package ru.scarletredman.gateway.controller.response.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.RawSerializer;

import java.io.IOException;

public class GeometryDashResponseSerializer extends RawSerializer<GeometryDashResponseSerializer.Response> {

    public GeometryDashResponseSerializer() {
        super(Response.class);
    }

    @Override
    public void serialize(Response value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeRawValue(value.getResponse());
    }

    public interface Response {

        String getResponse();
    }
}
