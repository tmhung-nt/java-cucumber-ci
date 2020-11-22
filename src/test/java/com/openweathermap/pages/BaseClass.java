package com.openweathermap.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseClass {
    public static WebDriver driver;
    public WebDriverWait wait;
    public static Map<String, String> envData = new HashMap();
    private String url;
    public Properties prop = new Properties();

    public BaseClass() {
        try {
            loadProperties();
        } catch (IOException ex) {
            System.out.println("Error during load file config.properties");
        }

        String browser = prop.getProperty("browser");
        System.setProperty("webdriver.gecko.driver", "./src/test/resources/drivers/geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/drivers/chromedriver.exe");
        if (browser.equals("chrome")) {
            driver = new ChromeDriver();
        }
        if (browser.equals("firefox")) {
            driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 30);
    }

    public void loadProperties() throws IOException {
        String propFileName = "config.properties";
        InputStream inputStream;
        inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }
    }

    public String getUrl() {
        return url;
    }
}
