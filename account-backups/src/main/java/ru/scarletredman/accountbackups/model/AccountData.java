package ru.scarletredman.accountbackups.model;

import java.util.Set;

public record AccountData(long id, String username, Set<String> authorities) {}
