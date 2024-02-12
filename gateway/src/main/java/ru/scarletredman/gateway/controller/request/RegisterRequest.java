package ru.scarletredman.gateway.controller.request;

public record RegisterRequest(String userName, String password, String email, String secret) {}
