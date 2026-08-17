package ru.yandex.practicum.didukh.courier;

public class Authorization {
    private final String login;
    private final String password;

    public Authorization(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static Authorization from(Courier courier) {
        return new Authorization(courier.getLogin(), courier.getPassword());
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
