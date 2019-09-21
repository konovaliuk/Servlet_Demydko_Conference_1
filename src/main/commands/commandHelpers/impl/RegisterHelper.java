package commands.commandHelpers.impl;

import commands.commandHelpers.CommandHelper;
import entity.User;
import org.apache.log4j.Logger;
import servises.languageManager.LanguageManager;
import servises.parameterManager.ParameterManager;
import servises.userManager.UserManager;

public class RegisterHelper implements CommandHelper {
    private Logger logger = Logger.getLogger(RegisterHelper.class);
    private ParameterManager pm = new ParameterManager();
    private String email;
    private String password;
    private String name;
    private String surname;
    private String position;
    private String language;


    private User user;

    public RegisterHelper(String email, String password, String name, String surname, String position, String language) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.language = language;
    }

    @Override
    public String handle() {
        if (pm.isEmpty(name, surname, position)) {
            logger.info("Form was not filled out");
            return "errorEmptyForm";
        }
        if (!pm.isEmailCorrect(email)) {
            logger.info("Email was imputed incorrectly: " + email);
            return "errorEmailForm";
        }
        if (!pm.isPasswordCorrect(password)) {
            logger.info("Password was imputed incorrectly" + password);
            return "errorPassword";
        }

        if (!pm.isNameAndSurnameCorrect(name, surname)) {
            logger.info("Name or surname were imputed incorrectly" + name + " " + surname);
            return "errorNameOrSurname";
        }

        if (pm.isUserExist(email)) {
            logger.info("User intended to register under email " + email + " that already exists in system");
            return "errorUserExists";
        }

        LanguageManager languageManager = new LanguageManager();
        language = languageManager.setLanguageToUser(language);
        logger.info(language + "language " + " was set to user " + user.getEmail());
        user = new User(name, surname, email, password, position, language);
        language = languageManager.setLanguageToSession(language);
        logger.info(language + "language " + " was set to session");

        UserManager userManager = new UserManager();
        Long id = userManager.addUser(user);
        user.setId(id);
        user.setPassword(null);
        logger.info("Registration of of user with email: " + email + " was successful.");
        return "success";
    }

    public String getLanguage() {
        return language;
    }

    public User getUser() {
        return user;
    }
}
