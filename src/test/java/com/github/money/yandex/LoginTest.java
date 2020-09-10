package com.github.money.yandex;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.github.money.yandex.base.TestBase;
import com.github.money.yandex.locators.Locators;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.github.money.yandex.config.Configurations.password;
import static com.github.money.yandex.config.Configurations.username;

@Feature("Authorization actions")
@Severity(SeverityLevel.BLOCKER)
public class LoginTest extends TestBase {

    //TODO create tests
    /**
     * 1. Password hiding/showing
     * 2. login from another systems(with session, without session)
     * 3. Multiple accounts(switching, deleting, adding)
     * 4. Negative tests(wrong login/password)
     * <p>
     * Id: YM-1
     * Title: New account login without email confirmation
     * Preconditions:
     * 1. Create new user
     * 2. Don't confirm account via link in income letter from Yandex
     * 3. Save login and password as 'account'
     * Variables:
     * 1. 'account'
     * Steps:
     * 1. Go to main login page https://passport.yandex.ru/ | Login form is displayed
     * 2. Enter 'account'.login and submit it | Confirmation email in your account mail
     * .
     * .
     * n.Check user name tab | User name is displayed
     * <p>
     * Id: YM-2
     * Title: New account second login without linked phone number
     * Preconditions:
     * 1. Create new user
     * 2. Don't confirm account via link in income letter from Yandex
     * 3. Save login and password as 'account'
     * 4. Execute test with id 1
     * Variables:
     * 1. 'account'
     * Steps:
     * 1. Go to main login page https://passport.yandex.ru/ | Login form is displayed
     * 2. Enter 'account'.login and submit it | Form with number linking is displayed
     * .
     * .
     * n.Check user name tab | User name is displayed
     */

    @Test
    @Story("Successful login")
    @Description("Base success login")
    public void loginTest() {
        SelenideElement loginButton = $(Locators.loginButton);
        loginButton.click();

        step("Enter credentials", () -> {
            SelenideElement submitButton = $(Locators.submitButton);

            $(Locators.loginField).sendKeys(username);
            submitButton.click();

            $(Locators.passwdField).sendKeys(password);
            submitButton.click();
        });

        step("Check is user logged in", () -> {
            $(Locators.expandArrow).click();
            $(By.xpath(Locators.userLogin)).shouldHave(Condition.text(username));
        });
    }

    @Test
    @Story("Successful logout")
    @Description("Base success logout")
    public void logoutTest() {
        //TODO need to create API precondition for user login
        loginAsDefaultUser();

        step("Logout", () -> {
            $(Locators.expandArrow).click();
            $(Locators.logout).click();
        });

        step("Check is user logged in", () -> $(Locators.loginButton)
                .should(Condition.match("Checking href attribute",
                        s -> s.getAttribute("href").contains("https://passport.yandex.ru/auth?origin=money"))));
    }

}
