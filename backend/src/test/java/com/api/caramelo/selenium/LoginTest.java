package com.api.caramelo.selenium;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;

import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest {
  private WebDriver driver;

  @BeforeAll
  public void setUp() {
    System.setProperty("webdriver.chrome.driver", "/home/navarrotheus/Documents/Projetos/ufc/caramelo-tec-2-CK0236/backend/chromedriver");
    driver = new ChromeDriver();
  }

  @AfterAll
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void login() {
    driver.get("http://localhost:3000/sign-in");
    driver.manage().window().setSize(new Dimension(1920, 1053));
    driver.findElement(By.id("username")).click();
    driver.findElement(By.id("username")).sendKeys("teste");
    driver.findElement(By.id("password")).sendKeys("12345");
    driver.findElement(By.cssSelector(".action-button")).click();
    assertEquals( "Entrar", driver.findElement(By.cssSelector(".action-button")).getText());
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).sendKeys("6");
    driver.findElement(By.cssSelector(".action-button")).click();
    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    assertEquals("Porte", driver.findElement(By.cssSelector(".sc-pNWxx:nth-child(2) > h4")).getText());
    driver.findElement(By.cssSelector(".sc-hBMVcZ > p")).click();
    assertEquals("Entrar", driver.findElement(By.cssSelector(".action-button")).getText());
  }
}
