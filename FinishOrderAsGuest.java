import java.time.Duration;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FinishOrderAsGuest {
    WebDriver driver;


    private String chubbyCats;

    public FinishOrderAsGuest() {
    }

    @BeforeMethod
    public void setup() {
        this.driver = new ChromeDriver();
        this.driver.get("http://shop.pragmatic.bg/index.php?route=product/product&product_id=40");
        this.driver.manage().window().maximize();
    }

    @Test
    public void placeOrderAsGuestTest() {
        this.driver.findElement(By.id("button-cart")).click();
        WebElement cartButton = this.driver.findElement(By.xpath("//span[@id='cart-total']/ .."));
        cartButton.click();
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(3L));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='dropdown-menu pull-right']//i[@class='fa fa-share']/ ..")));
        WebElement checkoutLink = this.driver.findElement(By.xpath("//ul[@class='dropdown-menu pull-right']//i[@class='fa fa-share']/ .."));
        checkoutLink.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[value='guest']")));
        this.driver.findElement(By.cssSelector("[value='guest']")).click();
        this.driver.findElement(By.id("button-account")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-payment-firstname")));
        this.driver.findElement(By.id("input-payment-firstname")).sendKeys(new CharSequence[]{"Milen"});
        this.driver.findElement(By.id("input-payment-lastname")).sendKeys(new CharSequence[]{"Bozhinov"});
        String prefix = RandomStringUtils.randomAlphabetic(7);
        String sufix = RandomStringUtils.randomAlphabetic(5);
        String domain = RandomStringUtils.randomAlphabetic(3);
        String emailAddress = prefix + "@" + sufix + "." + domain;
        this.driver.findElement(By.id("input-payment-email")).sendKeys(new CharSequence[]{emailAddress});
        String phoneNumber = RandomStringUtils.randomNumeric(10);
        this.driver.findElement(By.id("input-payment-telephone")).sendKeys(new CharSequence[]{phoneNumber});
        this.driver.findElement(By.id("input-payment-address-1")).sendKeys(new CharSequence[]{RandomStringUtils.randomAlphabetic(5)});
        this.driver.findElement(By.id("input-payment-city")).sendKeys(new CharSequence[]{RandomStringUtils.randomAlphabetic(5)});
        this.driver.findElement(By.id("input-payment-postcode")).sendKeys(new CharSequence[]{RandomStringUtils.randomNumeric(4)});
        WebElement countrySelect = this.driver.findElement(By.id("input-payment-country"));
        Select countryDropdown = new Select(countrySelect);
        countryDropdown.selectByValue("33");
        WebElement regionSelect = this.driver.findElement(By.id("input-payment-zone"));
        Select regionDropdown = new Select(regionSelect);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#input-payment-zone option[value='505']")));
        regionDropdown.selectByValue("479");
        WebElement continueButton = driver.findElement(By.id("button-guest"));
        continueButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button-shipping-method")));
        WebElement continueButton2 = driver.findElement(By.id("button-shipping-method"));
        continueButton2.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("agree")));
        WebElement agreementCheckbox = driver.findElement(By.name("agree"));
        if (!agreementCheckbox.isSelected()){
            agreementCheckbox.click();
        }
        WebElement continueButton3 = driver.findElement(By.id("button-payment-method"));
        continueButton3.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button-confirm")));
        WebElement confirmOderButton = driver.findElement(By.id("button-confirm"));
        confirmOderButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[@id='content']/h1[text()='Your order has been placed!']"))));
        WebElement orderConfirmation = driver.findElement(By.xpath("//div[@id='content']/h1[text()='Your order has been placed!']"));
        String orderConfirmationAssert = orderConfirmation.getText();
        Assert.assertEquals(orderConfirmationAssert, "Your order has been placed!");
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}
