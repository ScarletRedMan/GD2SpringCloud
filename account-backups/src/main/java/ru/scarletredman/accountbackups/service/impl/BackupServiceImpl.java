package ru.scarletredman.accountbackups.service.impl;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.scarletredman.accountbackups.exception.BackupNotFound;
import ru.scarletredman.accountbackups.model.Account;
import ru.scarletredman.accountbackups.service.BackupService;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class BackupServiceImpl implements BackupService {

    private final MinioClient minioClient;
    private final String bucketName;

    @SneakyThrows
    @Override
    public void save(Account account, String data) {
        var bytes = data.getBytes(StandardCharsets.UTF_8);

        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object("account-" + account.id())
                .stream(new ByteArrayInputStream(bytes), bytes.length, -1)
                .build());
    }

    @SneakyThrows
    @Override
    public String load(Account account) throws BackupNotFound {
        return new String(minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object("account-" + account.id())
                .build()).readAllBytes(), StandardCharsets.UTF_8);
    }
}
