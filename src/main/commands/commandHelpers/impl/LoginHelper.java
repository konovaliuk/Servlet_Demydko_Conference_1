package commands.commandHelpers.impl;

import commands.commandHelpers.CommandHelper;
import entity.User;
import org.apache.log4j.Logger;
import servises.languageManager.LanguageManager;
import servises.parameterManager.ParameterManager;
import servises.userManager.UserManager;

public class LoginHelper implements CommandHelper {
    private Logger logger = Logger.getLogger(LoginHelper.class);
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
            logger.info("Email was imputed incorrectly: " + email);
            return "errorEmailForm";
        }
        if (!pm.isPasswordCorrect(password)) {
            logger.info("Password was imputed incorrectly");
            return "errorPassword";
        }
        UserManager userManager = new UserManager();
        user = userManager.getUserByEmail(email);

        if (user == null || !password.equals(user.getPassword())) {
            logger.info("User input incorrect dada to login");
            return "errorUserNotExists";
        }
        user.setPassword(null);
        LanguageManager languageManager = new LanguageManager();
        language = languageManager.setLanguageToSession(user.getLanguage());
        logger.info("User with email: " + email + " successfully login");
        return "success";
    }

    public User getUser() {
        return user;
    }

    public String getLanguage() {
        return language;
    }
}
