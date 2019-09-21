package languageManager;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import servises.languageManager.LanguageManager;

public class LanguageManagerTest {
    private static LanguageManager lm;

    @BeforeClass
    public static void init() {
        lm = new LanguageManager();
    }

    @AfterClass
    public static void clear() {
        lm = null;
    }

    @Test
    public void setLanguageToUserTest() {
        String language=lm.setLanguageToUser("ua_UA");
        Assert.assertEquals("UA",language);
    }

    @Test
    public void setLanguageToSessionTest() {
        String language=lm.setLanguageToSession("UA");
        Assert.assertEquals("ua_UA",language);
    }
}
