package com.openweathermap.pages;

//import com.sun.javafx.binding.StringFormatter;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BodyPageObject extends BaseClass {

    @FindBy(xpath = "//input[@placeholder='Search city']")
    private WebElement searchBox;

    @FindBy(xpath = "//button[text()='Search']")
    private WebElement searchBtn;

    @FindBy(className = "current-container mobile-padding")
    private WebElement generalPanel;

    @FindBy(className = "chart-component")
    private WebElement hourlyForecastPanel;

    @FindBy(className = "daily-container block mobile-padding")
    private WebElement eightDaysPanel;

    @FindBy(xpath = "//*[@class='search-dropdown-menu']//span[1]")
    private WebElement firstSearchResult;

    @FindBy(xpath = "//button[text()='Allow all']")
    private WebElement allowAll;

    private static String XPATH_GENERAL_PANEL = "//*[@class='current-container mobile-padding']";
    private static String XPATH_8DAY_PANEL = "//*[contains(@class,'daily-container block')]";

    private static String HOURLY_HEADER = "Hourly forecast";
    private static String EIGHT_DAYS_HEADER = "8-day forecast";

    private String city = "";
    private int recordth = 0;

    public BodyPageObject() {
        PageFactory.initElements(driver, this);
    }

    public void inputCityToSearchBox(String city) {
        this.city = city;
        waitUntilElementToBeClickable(searchBox);
        searchBox.click();
        searchBox.clear();
        searchBox.sendKeys(city);
    }

    public void clickBntSearch() {
        searchBtn.click();
        waitForPageLoaded();
        short_sleep();
    }

    public void checkGeneralPanelInfo() {
        String actualDisplayedDatetime = driver.findElement(By.xpath(XPATH_GENERAL_PANEL + "//span[@class='orange-text']")).getText();
        String actualDisplayedCityName = driver.findElement(By.xpath(XPATH_GENERAL_PANEL + "//h2")).getText();

//        Verify daytime, city name, weather-icon, and other weather items
        String patternString = "[\\d]+:[\\d]+(a|p)m, [a-zA-Z\\s]+[\\d]+";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(actualDisplayedDatetime);
        Assert.assertTrue(matcher.matches());

        Assert.assertTrue(actualDisplayedCityName.contains(this.city));
        Assert.assertTrue(driver.findElement(By.xpath(XPATH_GENERAL_PANEL + "//*[@class='weather-icon']")).isEnabled());
        Assert.assertTrue(driver.findElement(By.xpath(XPATH_GENERAL_PANEL + "//*[@class='heading']")).isEnabled());
        Assert.assertTrue(driver.findElement(By.xpath(XPATH_GENERAL_PANEL + "//*[contains(text(),'Feels like')]")).isEnabled());
        Assert.assertTrue(driver.findElement(By.xpath(XPATH_GENERAL_PANEL + "//*[@class='wind-line']")).isEnabled());
        Assert.assertTrue(driver.findElement(By.xpath(XPATH_GENERAL_PANEL + "//*[@class='icon-pressure']")).isEnabled());
        Assert.assertTrue(driver.findElement(By.xpath(XPATH_GENERAL_PANEL + "//*[text()='Humidity:']")).isEnabled());
        Assert.assertTrue(driver.findElement(By.xpath(XPATH_GENERAL_PANEL + "//*[text()='UV:']")).isEnabled());
        Assert.assertTrue(driver.findElement(By.xpath(XPATH_GENERAL_PANEL + "//*[text()='Dew point:']")).isEnabled());
        Assert.assertTrue(driver.findElement(By.xpath(XPATH_GENERAL_PANEL + "//*[text()='Visibility:']")).isEnabled());
    }

    public void checkHourlyForecastPanelInfo() {
        Assert.assertTrue(hourlyForecastPanel.findElement(By.xpath(".//*[@class='header-container']")).getText().contains(HOURLY_HEADER));
        Assert.assertTrue(hourlyForecastPanel.findElement(By.xpath(".//canvas[@class='chartjs-render-monitor']")).isDisplayed());
    }

    public void checkEightDaysListPanel() {
        Assert.assertTrue(driver.findElement(By.xpath(XPATH_8DAY_PANEL)).getText().contains(EIGHT_DAYS_HEADER));
        Assert.assertEquals(8, driver.findElements(By.xpath(XPATH_8DAY_PANEL + "//li")).size());
    }

    public void clickDaythIn8DaysPanel(String dayth) {
        recordth = Integer.parseInt(String.valueOf(dayth.charAt(0)));
        WebElement element = driver.findElement(By.xpath(String.format(XPATH_8DAY_PANEL + "//li[%d]", recordth)));
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
        short_sleep();
        element.click();
    }

    public void checkEightDaysDetailsPanel(String dayth) {
        WebElement optionScrollerEle = driver.findElement(By.xpath(XPATH_8DAY_PANEL + "//*[@class='options_scroller']"));

//        verify there are 8 elements in option Scroller, and the element dayth is active
        Assert.assertEquals(8, optionScrollerEle.findElements(By.xpath("./li")).size());
        Assert.assertEquals("active", optionScrollerEle.findElement(By.xpath(String.format("./li[%d]", recordth))).getAttribute("class"));

//        verify top section
        WebElement topSectionEle = driver.findElement(By.xpath(XPATH_8DAY_PANEL + "//*[@class='top-section']"));
        Assert.assertTrue(topSectionEle.findElement(By.xpath(String.format("./img", recordth))).isDisplayed());

//        Verify weather items is displayed
        WebElement weatherItemEle = driver.findElement(By.xpath(XPATH_8DAY_PANEL + "//*[contains(@class,'weather-items')]"));
        Assert.assertTrue(weatherItemEle.isDisplayed());

//        Verify details table is displayed
        WebElement detailsTableEle = driver.findElement(By.xpath(XPATH_8DAY_PANEL + "//table"));
        Assert.assertTrue(detailsTableEle.isDisplayed());

//        Verify item container is displayed
        WebElement itemContainerEle = driver.findElement(By.xpath(XPATH_8DAY_PANEL + "//*[@class='item-container']"));
        Assert.assertTrue(itemContainerEle.isDisplayed());
    }

    public void clickFirstSearchingResultSet() {
        waitUntilElementToBeClickable(firstSearchResult);
        firstSearchResult.click();
        waitForPageLoaded();
        short_sleep();
    }

    public void checkErrorMessage(String errorMessage) {
        String actualMessage = driver.findElement(By.xpath("//*[@class='sub not-found notFoundOpen']")).getText();
        Assert.assertEquals(actualMessage, errorMessage);
    }

    public void goToHomePage() {
        driver.get(prop.getProperty("url"));
    }

    public void waitForPageLoaded() {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(1000);
            wait.until(expectation);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }

    public void allowCookies() {
        allowAll.click();
    }

    private void short_sleep() {
        try {
            Thread.sleep(1000);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }

    private void waitUntilElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void closeBrowser(){
        driver.close();
        driver.quit();
    }
}
