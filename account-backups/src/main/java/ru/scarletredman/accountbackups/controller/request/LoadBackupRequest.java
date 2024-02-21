package ru.scarletredman.accountbackups.controller.request;

public record LoadBackupRequest(int gameVersion, int binaryVersion, String udid, String uuid, long accountID, String gjp2, String secret) {}
