package ru.scarletredman.accountbackups.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.scarletredman.accountbackups.exception.BackupNotFound;
import ru.scarletredman.accountbackups.model.Account;
import ru.scarletredman.accountbackups.service.BackupService;

@Service
@RequiredArgsConstructor
public class BackupServiceImpl implements BackupService {

    @Override
    public void save(Account account, String data) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public String load(Account account) throws BackupNotFound {
        throw new UnsupportedOperationException("Not implemented");
    }
}
