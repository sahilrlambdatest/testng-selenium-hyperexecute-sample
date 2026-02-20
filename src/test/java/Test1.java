import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Test1 {
  RemoteWebDriver driver = null;
  public static String status = "passed";
  public static String username = System.getenv("LT_USERNAME");
  public static String access_key = System.getenv("LT_ACCESS_KEY");

  String testURL = "https://todomvc.com/examples/react/dist/";
  String testURLTitle = "TodoMVC: React";
  @BeforeMethod
  @Parameters(value = { "browser", "version", "platform", "resolution" })
  public void testSetUp(String browser, String version, String platform, String resolution) throws Exception {
    String platformName = System.getenv("HYPEREXECUTE_PLATFORM") != null ? System.getenv("HYPEREXECUTE_PLATFORM") : platform;

    DesiredCapabilities capabilities = new DesiredCapabilities();

    capabilities.setCapability("build", "[HyperExecute - 1] Demonstration of the TestNG Framework");
    capabilities.setCapability("name", "[HyperExecute - 1] Demonstration of the TestNG Framework");
    capabilities.setCapability("platform", System.getenv("HYPEREXECUTE_PLATFORM"));
    capabilities.setCapability("browserName", browser);
    capabilities.setCapability("version", version);
    capabilities.setCapability("selenium_version", "4.0.0");

    capabilities.setCapability("tunnel", false);
    capabilities.setCapability("network", true);
    HashMap<String, Object> networkConfig = new HashMap<>();
    networkConfig.put("bypassWebsocket", true);
    capabilities.setCapability("networkConfig", networkConfig);
    capabilities.setCapability("console", true);
    capabilities.setCapability("visual", true);

    try {
      driver = new RemoteWebDriver(new URL("https://" + username + ":" + access_key + "@hub.lambdatest.com/wd/hub"), capabilities);
    } catch (MalformedURLException e) {
      System.out.println("Invalid grid URL");
    }
    System.out.println("Started session");
  }

  @Test(description = "To Do App on React App")
  public void test1_element_addition_1() throws InterruptedException {
    ExtentReports extent = new ExtentReports("target/surefire-reports/html/extentReport.html");
    ExtentTest test1 = extent.startTest("demo application test 1", "To Do App test 1");

    driver.get(testURL);
    Thread.sleep(8000);
    test1.log(LogStatus.PASS, "URL is opened");
    WebDriverWait wait = new WebDriverWait(driver, 5);
    test1.log(LogStatus.PASS, "Wait created");
    /* Click on the Link */
    By elem_new_item_locator = By.xpath("//input[@class='new-todo']");
    WebElement elem_new_item = driver.findElement(elem_new_item_locator);

    /* Add 5 items in the list */
    Integer item_count = 5;

    for (int count = 1; count <= item_count; count++)
    {
      /* Enter the text box for entering the new item */
      elem_new_item.click();
      elem_new_item.sendKeys("Adding a new item " + count + Keys.ENTER);
      test1.log(LogStatus.PASS,"New item No. "+count+" is added");
      Thread.sleep(5000);
    }

    extent.endTest(test1);
    extent.flush();

    WebElement temp_element;

    /* Now that the items are added, we mark the top three items as completed */
    for (int count = 1; count <= item_count; count++)
    {
      Integer fixed_cta_count = 1;

      /* Enter the text box for entering the new item */
      /* Create a varying string to create a new XPath */
      String xpath_str = "//ul[@class='todo-list']/li[" + fixed_cta_count + "]" + "//input[@class='toggle']";
      temp_element = driver.findElement(By.xpath(xpath_str));

      temp_element.click();
      Thread.sleep(2000);
      /* Toggle button to destroy */
      driver.findElement(By.xpath("//li[@class='completed']//button[@class='destroy']")).click();
      Thread.sleep(3000);
    }

    /* Once you are outside this code, the list would be empty */
  }

  @Test(description = "To Do App on React App", groups="search")
  public void test1_element_addition_2() throws InterruptedException {
    driver.get(testURL);
    Thread.sleep(5000);

    /* Selenium Java 3.141.59 */
    WebDriverWait wait = new WebDriverWait(driver, 5);

    /* Click on the Link */
    By elem_new_item_locator = By.xpath("//input[@class='new-todo']");
    WebElement elem_new_item = driver.findElement(elem_new_item_locator);

    /* Add 5 items in the list */
    Integer item_count = 5;

    for (int count = 1; count <= item_count; count++)
    {
      /* Enter the text box for entering the new item */
      elem_new_item.click();
      elem_new_item.sendKeys("Adding a new item " + count + Keys.ENTER);
      Thread.sleep(2000);
    }

    WebElement temp_element;

    /* Now that the items are added, we mark the top three items as completed */
    for (int count = 1; count <= item_count; count++)
    {
      Integer fixed_cta_count = 1;

      /* Enter the text box for entering the new item */
      /* Create a varying string to create a new XPath */
      String xpath_str = "//ul[@class='todo-list']/li[" + fixed_cta_count + "]" + "//input[@class='toggle']";
      temp_element = driver.findElement(By.xpath(xpath_str));

      temp_element.click();
      Thread.sleep(2000);
      /* Toggle button to destroy */
      driver.findElement(By.xpath("//li[@class='completed']//button[@class='destroy']")).click();
      Thread.sleep(1000);
    }

    /* Once you are outside this code, the list would be empty */
  }

  @AfterMethod
    public void tearDown() {
      if (driver != null) {
        ((JavascriptExecutor) driver).executeScript("lambda-status=failed");
        driver.quit();
      }
    }

}
