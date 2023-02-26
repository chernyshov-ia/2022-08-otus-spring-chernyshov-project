package ru.chia2k.security.client.principal;

public interface CommonPrincipal {
    Object getAccountNumber();
    String getFirstName();
    String getLastName();
    String getPatronymic();
    String getEmail();
}
