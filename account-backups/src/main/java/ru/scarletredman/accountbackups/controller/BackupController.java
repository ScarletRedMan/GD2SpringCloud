package ru.scarletredman.accountbackups.controller;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.scarletredman.accountbackups.controller.request.LoadBackupRequest;
import ru.scarletredman.accountbackups.controller.request.SaveBackupRequest;
import ru.scarletredman.accountbackups.controller.response.LoadBackupResponse;
import ru.scarletredman.accountbackups.controller.response.SaveBackupResponse;
import ru.scarletredman.accountbackups.exception.BackupNotFound;
import ru.scarletredman.accountbackups.service.AccountService;
import ru.scarletredman.accountbackups.service.BackupService;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/database/accounts")
public class BackupController {

    private final AccountService accountService;
    private final BackupService backupService;

    @PostMapping("/backupGJAccountNew.php")
    SaveBackupResponse save(SaveBackupRequest request) {
        try {
            var account = accountService.auth(request.accountID(), request.gjp2());
            backupService.save(account, request.saveData());
            return SaveBackupResponse.success();
        } catch (FeignException.Forbidden ex) {
            return SaveBackupResponse.fail();
        }
    }

    @PostMapping("/syncGJAccountNew.php")
    LoadBackupResponse load(LoadBackupRequest request) {
        try {
            var account = accountService.auth(request.accountID(), request.gjp2());
            return LoadBackupResponse.success(backupService.load(account));
        } catch (BackupNotFound | FeignException.Forbidden ex) {
            return LoadBackupResponse.fail();
        }
    }
}
