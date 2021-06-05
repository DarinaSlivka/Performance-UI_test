package pageobject.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static org.openqa.selenium.By.xpath;
import static org.testng.Assert.assertEquals;

public class FunctionalTests {

    private WebDriver driver;
    private static final String BAGS_URL = "http://ec2-3-94-57-213.compute-1.amazonaws.com:8080/";

    @BeforeClass
    public void testsSetUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void profileSetUp() {
        driver.get(BAGS_URL);
    }

    @Test
    public void application() {
        driver.findElement(xpath("//*[@id='main_h']//a")).click();

        WebDriverWait mainWaiter = new WebDriverWait(driver,10);
        WebElement element1 = mainWaiter.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='productsContainer']//a")));
        element1.click();

        driver.findElement(xpath("//*[@class='btn addToCart addToCartButton btn-buy']")).click();
        driver.findElement(xpath("//*[@id='miniCartSummary']")).click();
        WebElement element2 = mainWaiter.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='miniCartDetails']/li[4]/a")));
        element2.click();

        driver.findElement(xpath("//div[@class='wc-proceed-to-checkout']/a")).click();
        driver.findElement(xpath("//*[@id='customer.firstName']")).sendKeys("Darina");
        driver.findElement(xpath("//*[@id='customer.lastName']")).sendKeys("Slivka");
        driver.findElement(xpath("//*[@id='customer.billing.company']")).sendKeys("ABC");
        driver.findElement(xpath("//*[@id='customer.billing.address']")).sendKeys("Peremohy avenue");
        driver.findElement(xpath("//*[@id='customer.billing.city']")).clear();
        driver.findElement(xpath("//*[@id='customer.billing.city']")).sendKeys("Kiev");
        driver.findElement(xpath("//*[@id='customer.billing.country']")).click();
        driver.findElement(xpath("//*[@id='customer.billing.country']/option[9]")).click();
        driver.findElement(xpath("//*[@id='billingStateProvince']")).sendKeys("province");
        driver.findElement(xpath("//*[@id='customer.emailAddress']")).sendKeys("idslivka@gmail.com");
        driver.findElement(xpath("//*[@id='customer.billing.phone']")).sendKeys("0978788368");
        driver.findElement(xpath("//*[@id='billingPostalCode']")).sendKeys("13578");

        String actualResultFillFields =
                driver.findElement(xpath("//*[@id='formErrorMessage']")).getText();
        assertEquals(actualResultFillFields, "The order can be completed");
        driver.findElement(xpath("//*[@id='submitOrder']")).submit();

        driver.findElement(xpath("//*[@id='cbox']")).click();WebElement element3 = mainWaiter.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"customer.clearPassword\"]")));
        element3.sendKeys( "pass"+ Math.random());
        Random r = new Random();
        char pass = (char)(r.nextInt(6) + '*');
        driver.findElement(xpath("//*[@id='submitOrder']")).click();

        String actualResultOrder =
                driver.findElement(xpath("//*[@id='main-content']/h1")).getText();
        assertEquals(actualResultOrder, "Order completed");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}