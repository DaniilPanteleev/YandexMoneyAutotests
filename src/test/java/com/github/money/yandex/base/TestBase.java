package com.github.money.yandex.base;


import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.automatedowl.tools.AllureEnvironmentWriter;
import com.github.money.yandex.config.Configurations;
import com.github.money.yandex.interfaces.Step;
import com.github.money.yandex.locators.Locators;
import com.google.common.collect.ImmutableMap;
import io.qameta.allure.Allure;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.github.money.yandex.config.Configurations.password;
import static com.github.money.yandex.config.Configurations.username;

public abstract class TestBase {

    @BeforeAll
    static void init() throws IOException {
        SelenideLogger.addListener("Allure", new AllureSelenide());

        Properties properties = new Properties();
        properties.load(TestBase.class.getClassLoader().getResourceAsStream(Configurations.selenidePropFileName));

        if (!properties.isEmpty()) properties.keySet()
                .forEach(p -> System.setProperty(String.valueOf(p), properties.getProperty(String.valueOf(p))));
    }

    @AfterAll
    static void cleanup() {
        ImmutableMap.Builder<String, String> envMapBuilder = ImmutableMap.builder();

        System.getProperties().forEach((key, value) -> {
            if (key.toString().matches("^(selenide|allure).*"))
                envMapBuilder.put(key.toString(), value.toString());
        });

        AllureEnvironmentWriter.allureEnvironmentWriter(envMapBuilder.build());
    }

    @BeforeEach
    public void openBasePage() {
        step("Open base page", () -> open("/"));
        getWebDriver().manage().window().maximize();
    }

    @AfterEach
    public void closeDriver() {
        if (WebDriverRunner.hasWebDriverStarted()) WebDriverRunner.closeWebDriver();
    }

    protected void step(String name, Step stepsToExecute) {
        Allure.getLifecycle().startStep(UUID.randomUUID().toString(), new StepResult().setName(name));
        stepsToExecute.doStep();
        Allure.getLifecycle().stopStep();
    }

    protected void loginAsDefaultUser() {
        step("Login", () -> {
            $(Locators.loginButton).click();

            SelenideElement submitButton = $(Locators.submitButton);

            $(Locators.loginField).sendKeys(username);
            submitButton.click();

            $(Locators.passwdField).sendKeys(password);
            submitButton.click();
        });
    }

}
