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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

    public class CreatingNewUserTest {
        WebDriver driver;

        public CreatingNewUserTest() {
        }

        @BeforeMethod
        public void setup() {
            this.driver = new ChromeDriver();
            this.driver.get("http://shop.pragmatic.bg/admin");
            this.driver.manage().window().maximize();
        }

        @Test
        public void creatingUserTest() {
            this.driver.findElement(By.id("input-username")).sendKeys(new CharSequence[]{"admin"});
            WebElement passwordInputField = this.driver.findElement(By.id("input-password"));
            passwordInputField.sendKeys(new CharSequence[]{"parola123!"});
            passwordInputField.submit();
            WebElement customersDropdown = this.driver.findElement(By.cssSelector("#menu-customer>a"));
            customersDropdown.click();
            WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(3L));

            WebElement customersLabel = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#collapse5 > li:nth-child(1) > a")));
            customersLabel.click();
            WebElement plusButton = this.driver.findElement(By.xpath("//*[@class='fa fa-plus']/ .."));
            plusButton.click();
            this.driver.findElement(By.id("input-firstname")).sendKeys(new CharSequence[]{"Milen"});
            this.driver.findElement(By.id("input-lastname")).sendKeys(new CharSequence[]{"Bozhinov"});
            String prefix = RandomStringUtils.randomAlphabetic(7);
            String sufix = RandomStringUtils.randomAlphabetic(5);
            String domain = RandomStringUtils.randomAlphabetic(3);
            String emailAddress = prefix + "@" + sufix + "." + domain;
            this.driver.findElement(By.id("input-email")).sendKeys(new CharSequence[]{emailAddress});
            WebElement phoneNumberField = driver.findElement(By.id("input-telephone"));
            String number = RandomStringUtils.randomNumeric(6);
            String phoneNumber = "0888" + number;
            phoneNumberField.sendKeys(phoneNumber);
            WebElement passwordField = driver.findElement(By.id("input-password"));
            passwordField.sendKeys("zayo-bayo");
            WebElement confirmPasswordField = driver.findElement(By.id("input-confirm"));
            confirmPasswordField.sendKeys("zayo-bayo");
            WebElement newsLetter = driver.findElement(By.id("input-newsletter"));
            Select newsLetterSelect = new Select(newsLetter);
            newsLetterSelect.selectByValue("1");
            WebElement status = driver.findElement(By.id("input-status"));
            Select statusSelect = new Select(status);
            statusSelect.selectByValue("1");
            WebElement safe = driver.findElement(By.id("input-safe"));
            Select safeSelect = new Select(safe);
            safeSelect.selectByValue("1");
            WebElement confirmRegistration = driver.findElement(By.xpath("//button[@type='submit']"));
            confirmRegistration.click();
            WebElement filterByEmail = driver.findElement(By.id("input-email"));
            filterByEmail.sendKeys(emailAddress);
            WebElement filterButton = driver.findElement(By.id("button-filter"));
            filterButton.click();
            WebElement assertEmail = driver.findElement(By.xpath("//input[@name='selected[]']//parent::td//following-sibling::td[2]"));
            String assertEmail1 = assertEmail.getText();
            Assert.assertEquals(assertEmail1, emailAddress);
        }

        @AfterMethod
        public void tearDown(){
            driver.quit();
        }
    }