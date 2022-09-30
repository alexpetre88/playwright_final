import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pages.AccountEntries;

public class SignInTest extends PlaywrightRunner{


    @Test
    @Disabled
    public void signInExistingUser() {
        homePage.navigate();
        accountNavigationPage.navigateTo(AccountEntries.SIGN_IN);
        signInPage.signIn(getProperty("email"), getProperty("password"));
        homePage.checkWeAreOnTheHomePage();
    }

}
