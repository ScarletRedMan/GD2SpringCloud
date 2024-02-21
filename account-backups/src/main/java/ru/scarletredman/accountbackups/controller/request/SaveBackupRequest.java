package ru.scarletredman.accountbackups.controller.request;

public record SaveBackupRequest(int gameVersion, int binaryVersion, String udid, String uuid, int accountID, String gjp2, String saveData, String secret) {}
