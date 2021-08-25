package com.accesshq.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.text.NumberFormat;

public class PlanetPage {
    private WebDriver driver;
    public PlanetPage(WebDriver driver) {
        this.driver = driver;
    }

    public float getEarthsRadius() throws Exception {
        var planets = driver.findElements(By.className("planet"));

        for (var planet: planets) {
            if(planet.findElement(By.className("name")).getText().equals("Earth")) {
                var radiusString = planet.findElement(By.className("radius")).getText();
                return NumberFormat.getNumberInstance().parse(radiusString.split(" ")[0]).floatValue();
            }
        }
        throw new Exception("Couldn't find Earth");
    }
}
