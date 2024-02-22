package ru.scarletredman.accountbackups.service;

import ru.scarletredman.accountbackups.exception.BackupNotFound;
import ru.scarletredman.accountbackups.model.Account;

public interface BackupService {

    void save(Account account, String data);

    String load(Account account) throws BackupNotFound;
}
