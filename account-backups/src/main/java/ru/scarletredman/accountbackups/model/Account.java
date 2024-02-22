package ru.scarletredman.accountbackups.model;

import java.util.Set;

public record Account(long id, String username, Set<String> authorities) {}
