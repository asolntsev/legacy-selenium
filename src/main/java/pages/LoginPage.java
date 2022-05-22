package pages;

import base.BasePage;
import elements.ButtonElement;
import elements.TextElement;
import logging.Log;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    
    private static final By PASSWORD_LOCATOR = By.id("password");
    private static final By USER_NAME_LOCATOR = By.id("username");
    private static final By LOGIN_BUTTON_LOCATOR = By.id("submit-button");
    
    public LoginPage() {
        super("Login page", USER_NAME_LOCATOR);
    }
    
    private void setUserName(String userName) {
        new TextElement("Login", USER_NAME_LOCATOR).setValue(userName);
    }
    
    private void setPassword(String password) {
        new TextElement("Password", PASSWORD_LOCATOR).setValue(password);
    }
    
    private void clickLogin() {
        new ButtonElement("Login button", LOGIN_BUTTON_LOCATOR).click();
    }
    
    public void signIn(String username, String password) {
        Log.logInfo("Signing in with: " + username + "\\" + password);
        setUserName(username);
        setPassword(password);
        clickLogin();
    }
}
