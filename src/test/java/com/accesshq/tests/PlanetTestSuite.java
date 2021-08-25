package com.accesshq.tests;

import com.accesshq.ui.HomePage;
import com.accesshq.ui.PlanetPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class PlanetTestSuite {
    WebDriver driver;

    @BeforeEach
    public void SetUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://d1iw6mb9di5l9r.cloudfront.net/");
    }

    @Test
    public void PlanetsPage_Earth_Radius_Test() throws Exception {
        new HomePage(driver).clickPlanetButton();

        Assertions.assertEquals(new PlanetPage(driver).getEarthsRadius(),6371);
    }

    @AfterEach
    public void CleanUp() {
        driver.quit();
    }
}
