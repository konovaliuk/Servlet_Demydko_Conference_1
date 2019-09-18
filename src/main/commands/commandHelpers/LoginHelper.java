package commands.commandHelpers;

import entity.User;
import servises.configManager.ConfigManager;
import servises.languageManager.LanguageManager;
import servises.messageManager.MessageManager;
import servises.parameterManager.ParameterManager;
import servises.userManager.UserManager;

import javax.servlet.http.HttpSession;

public class LoginHelper implements CommandHelper {
    private String email;
    private String password;

    private User user;
    private String language;

    public LoginHelper(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String handle() {
        ParameterManager pm = new ParameterManager();
        if (!pm.isEmailCorrect(email)) {
            return "errorEmailForm";
        }
        if (!pm.isPasswordCorrect(password)) {
            return "errorPassword";
        }
        UserManager userManager = new UserManager();
        user = userManager.getUserByEmail(email);

        if (user == null || !password.equals(user.getPassword())) {
            return "errorUserNotExists";
        }
        user.setPassword(null);
        LanguageManager languageManager = new LanguageManager();
        language = languageManager.setLanguageToSession(user.getLanguage());
        return "success";
    }

    public User getUser() {
        return user;
    }

    public String getLanguage() {
        return language;
    }
}
