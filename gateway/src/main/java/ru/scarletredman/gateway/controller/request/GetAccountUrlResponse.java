package ru.scarletredman.gateway.controller.request;

public record GetAccountUrlResponse(long accountID, int type, String secret) {}
