package com.openweathermap.steps;

import com.openweathermap.pages.BodyPageObject;
import cucumber.api.java.en.And;

public class StepDefinitions {
    private BodyPageObject bodyPageObject;
    private static String[] PANEL_NAME = {"General Panel", "Hourly Forecast", "8-day Forecast"};

    @And("User opens browser")
    public void openBrowser() {
        bodyPageObject = new BodyPageObject();
        bodyPageObject.goToHomePage();
    }

    @And("Wait until page loads completely")
    public void waitPageLoad() {
        bodyPageObject.waitForPageLoaded();
        try {
            bodyPageObject.allowCookies();
        }catch (Exception ex){

        }
    }

    @And("User inputs '(.*)' into search box")
    public void inputCity(String city) {
        bodyPageObject.inputCityToSearchBox(city);
    }

    @And("User clicks button Search")
    public void clickBtn() {
        bodyPageObject.clickBntSearch();

    }

    @And("User selects the first result return from search box")
    public void selectFirstResultRow() {
        bodyPageObject.clickFirstSearchingResultSet();
    }

    @And("Verify '(.*)' displays property")
    public void verifyPanelDisplay(String panelName) {
        int index = 0;
        for (; index < PANEL_NAME.length; index++) {
            if (panelName.equals(PANEL_NAME[index]))
                break;
        }
        if (index < PANEL_NAME.length) {
            switch (index) {
                case 0:
                    bodyPageObject.checkGeneralPanelInfo();
                    break;
                case 1:
                    bodyPageObject.checkHourlyForecastPanelInfo();
                    break;
                case 2:
                    bodyPageObject.checkEightDaysListPanel();
                    break;
            }
        }
    }

    @And("User clicks the '(.*)' day in 8-day forecast")
    public void clickDayth(String dayth) {
        bodyPageObject.clickDaythIn8DaysPanel(dayth);

    }

    @And("Verify details for '(.*)' day in 8-day forecast displays property")
    public void verifyDaythDetailsInfo(String dayth) {
        bodyPageObject.checkEightDaysDetailsPanel(dayth);
    }

    @And("Verify error message is displayed under searchbox with content '(.*)'")
    public void verifyErrorMessage(String errorMessage) {
        bodyPageObject.checkErrorMessage(errorMessage);
    }

    @And("User closes browser")
    public void closeBrowser() {
        bodyPageObject.closeBrowser();
    }

}
