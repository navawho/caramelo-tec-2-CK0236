package com.api.caramelo.selenium;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NewPetTest {
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
    public void newPet() {
        driver.get("http://localhost:3000/sign-in");
        driver.manage().window().setSize(new Dimension(1920, 1053));
        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).sendKeys("teste");
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElement(By.cssSelector(".action-button")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector(".sc-hBMVcZ:nth-child(3) p")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector(".action-button")).click();
        driver.findElement(By.xpath("/html/body/div[3]/div/div/form/div[1]/input")).sendKeys("teste");
        driver.findElement(By.xpath("/html/body/div[3]/div/div/form/div[2]/input")).sendKeys("Nome teste");
        driver.findElement(By.xpath("/html/body/div[3]/div/div/form/div[3]/input")).sendKeys("Grande");
        driver.findElement(By.xpath("/html/body/div[3]/div/div/form/div[4]/input")).sendKeys("Macho");
        driver.findElement(By.xpath("/html/body/div[3]/div/div/form/div[5]/input")).sendKeys("Cachorro");
        driver.findElement(By.xpath("/html/body/div[3]/div/div/form/div[6]/input")).sendKeys("2020-02-02");
        driver.findElement(By.xpath("/html/body/div[3]/div/div/form/div[7]/input")).sendKeys("Descrição teste");
        driver.findElement(By.cssSelector(".action-button:nth-child(9)")).click();
        assertEquals("Nome teste", driver.findElement(By.cssSelector("h3")).getText());
        assertEquals("Grande", driver.findElement(By.cssSelector("div:nth-child(2) > span")).getText());
        driver.findElement(By.cssSelector("div:nth-child(3) > span")).click();
        assertEquals("Cachorro", driver.findElement(By.cssSelector("div:nth-child(3) > span")).getText());
        assertEquals("Macho", driver.findElement(By.cssSelector("div:nth-child(4) > span")).getText());
        assertEquals("Descrição teste", driver.findElement(By.cssSelector("p:nth-child(3)")).getText());
        driver.findElement(By.cssSelector(".action-button:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".sc-hBMVcZ > p")).click();
    }
}
