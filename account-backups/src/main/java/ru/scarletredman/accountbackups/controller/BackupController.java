package ru.scarletredman.accountbackups.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.scarletredman.accountbackups.controller.request.LoadBackupRequest;
import ru.scarletredman.accountbackups.controller.request.SaveBackupRequest;
import ru.scarletredman.accountbackups.controller.response.LoadBackupResponse;
import ru.scarletredman.accountbackups.controller.response.SaveBackupResponse;

@Log4j2
@RestController
@RequestMapping("/database/accounts")
public class BackupController {

    @PostMapping("/backupGJAccountNew.php")
    SaveBackupResponse save(SaveBackupRequest request) {
        return SaveBackupResponse.fail();
    }

    @PostMapping("/syncGJAccountNew.php")
    LoadBackupResponse load(LoadBackupRequest request) {
        return LoadBackupResponse.fail();
    }
}
