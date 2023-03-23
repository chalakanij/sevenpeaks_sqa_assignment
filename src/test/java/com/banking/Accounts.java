package com.banking;

/*Why Selenium is used?
1. A set of UI validations needs to be performed
2. Button click and user input actions have to be captured*/

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Accounts {

    private WebDriver webDriver;
    String BASEURL = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";

    @BeforeClass
    public void beforeClass(){
        webDriver = new FirefoxDriver();
    }

    @AfterClass
    public void afterClass(){
        //webDriver.quit();
    }

    @Test
    public void loginToXyz() throws InterruptedException{
        //Navigate to login
        webDriver.navigate().to(BASEURL);
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        //Click on Bank manager Login
        webDriver.findElement(By.xpath("//button[contains(.,'Bank Manager Login')]")).click();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(webDriver.findElement(By.xpath("//button[contains(.,'Add Customer')]"))));

        //Validate link url
        System.out.println(webDriver.getCurrentUrl());
        Assert.assertEquals("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager",webDriver.getCurrentUrl(),"URL Not Found!");

        //Click on Add customer
        webDriver.findElement(By.xpath("//button[contains(.,'Add Customer')]")).click();
        wait.until(ExpectedConditions.visibilityOf(webDriver.findElement(By.xpath("//input[@type='text']"))));

        //Fill information & add customer
        webDriver.findElement(By.xpath("//input[@type='text']")).sendKeys("Chal");
        webDriver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("Jay");
        webDriver.findElement(By.xpath("(//input[@type='text'])[3]")).sendKeys("10400");
        webDriver.findElement(By.xpath("//button[@type='submit']")).click();

        //Accept alert
        Alert alert = webDriver.switchTo().alert();
        System.out.println("Alert : "+alert.getText());
        alert.accept();

        //Go to customer table &  validate new record
        webDriver.findElement(By.xpath("//button[contains(.,'Customers')]")).click();
        Assert.assertTrue(webDriver.findElement(By.xpath("//td[contains(.,'Chal')]")).isDisplayed(),"Customer Not Found!");

        //Open Accounts
        webDriver.findElement(By.xpath("//button[contains(.,'Open Account')]")).click();

        //Select newly added customer
        new Select(webDriver.findElement(By.xpath("//select[@id='userSelect']"))).selectByVisibleText("Chal Jay");

        //Select Pound currency
        new Select(webDriver.findElement(By.xpath("//select[@id='currency']"))).selectByVisibleText("Pound");

        //Click on Process
        webDriver.findElement(By.xpath("//button[contains(.,'Process')]")).click();

        //Verify pop up message
        String text = webDriver.switchTo().alert().getText();
        System.out.println("Alert : "+ text);
        Assert.assertTrue(text.contains("Account created successfully with account Number :1016"),"Text Not found!");
    }
}