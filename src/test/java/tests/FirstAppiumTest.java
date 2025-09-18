package tests;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.TestDataUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class FirstAppiumTest {

    private AppiumDriver driver;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceName", "Medium Phone API 36.0"); // match AVD name
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("appPackage", "com.google.android.contacts");
        caps.setCapability("appActivity", "com.google.android.apps.contacts.activities.PeopleActivity");
        caps.setCapability("appWaitActivity", "*");
        caps.setCapability("noReset", false);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);

    }

    @Test
    public void testAddContactButtonOpensCreateContactScreen(){
        String uniqueFirstName = TestDataUtils.generateUniqueName("Chirag");
        String uniqueLastName = TestDataUtils.generateUniqueName("Dwivedi");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

        WebElement addContactButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Create contact")));
        addContactButton.click();

        WebElement createContactTitle = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//android.widget.TextView[@text=\"Create contact\"]")));

        Assert.assertTrue(createContactTitle.getText().contains("Create contact"),
                "Create contact screen not displayed!");

        WebElement firstName = driver.findElement(By.xpath("//android.widget.EditText[@text=\"First name\"]"));
        firstName.sendKeys(uniqueFirstName);
        WebElement lastName = driver.findElement(By.xpath("//android.widget.EditText[@text=\"Last name\"]"));
        lastName.sendKeys(uniqueLastName);
        WebElement phoneNumber = driver.findElement(By.xpath("//android.widget.EditText[@text=\"+1\"]"));
        phoneNumber.sendKeys("2345678910");
        WebElement saveContact = driver.findElement(By.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[2]/android.view.View[2]/android.widget.Button"));
        saveContact.click();
        WebElement deleteContact = driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))" +
                ".scrollIntoView(new UiSelector().text(\"Delete\"));"));
        deleteContact.click();

        WebElement prompt = driver.findElement(By.id("android:id/button1"));
        prompt.click();

    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
