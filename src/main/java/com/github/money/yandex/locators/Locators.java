package com.github.money.yandex.locators;

public final class Locators {

    private Locators() {
    }

    public static final String loginButton = "[title = 'Войти']";
    public static final String submitButton = "[type = 'submit']";

    public static final String loginField = "[name = 'login']";
    public static final String passwdField = "[name = 'passwd']";

    public static final String userLogin = "//*[contains(@class, 'qa-user-name')]//*[contains(@class, 'Text')]";

    public static final String expandArrow = ".gYDBuY";
    public static final String logout = ".bvvVVE";


}
