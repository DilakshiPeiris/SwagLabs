import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

@Listeners(TestResultListener.class)
public class Dataprovider {

    WebDriver driver;
    SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void launchURL(){

        System.setProperty("webdriver.gecko.driver","src/main/resources/geckodriver.exe");
        Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);

        FirefoxOptions options = new FirefoxOptions();
        options.setCapability("moz:debuggerAddress", true);
        Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");
    }

    @DataProvider(name = "loginData")
    public Object[][] provideLoginData() {
        return new Object[][] {
                {"standard_user", "secret_sauce"},    // Valid credentials
                {"standard_user1", "secret_sauce2"}, // Invalid password
                {"", "secret_sauce"},    // Empty username
                {"standard_user", ""}     // Empty password
        };
    }

    @Test(dataProvider ="loginData")
    public void loginTest(String username,String password){

        WebElement usernameField = driver.findElement(By.id("user-name"));
        usernameField.sendKeys(username);

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys(password);

        WebElement loginBtn = driver.findElement(By.id("login-button"));
        loginBtn.click();

        try {
            WebElement products = driver.findElement(By.xpath("//span[@data-test='title']"));
            softAssert.assertTrue(products.isDisplayed(), "Products");
            System.out.println("Login passed for: " + username + "," + password);
        } catch (Exception e) {
            System.out.println("Login failed for: " + username + "," + password);
            softAssert.fail("Login failed for user: " + username + "," + password);
        }

    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }

}
