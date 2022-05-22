package scenarios;

import base.BaseComponent;
import entities.User;
import helpers.NavigationHelper;
import helpers.Pauses;
import helpers.Timeouts;
import pages.LoginPage;
import pages.MainPage;

public class LoginScenarios extends BaseComponent {
    
    public static void loginWithCredentials(User user) {
        LoginPage loginPage = new LoginPage();
        loginPage.signIn(user.getUserName(), user.getPassword());
        Pauses.sleep(Timeouts.SLOW);
        new MainPage();
    }
    
    public static MainPage openAppAndLoginWithCredentials(User user) {
        NavigationHelper.navigateToDefaultUrl();
    
        loginWithCredentials(user);
        return new MainPage();
    }
    
}
