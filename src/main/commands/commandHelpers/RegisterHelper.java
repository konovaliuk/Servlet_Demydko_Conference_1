package commands.commandHelpers;

import entity.User;
import servises.languageManager.LanguageManager;
import servises.parameterManager.ParameterManager;
import servises.userManager.UserManager;

public class RegisterHelper implements CommandHelper {
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
            return "errorEmptyForm";
        }
        if (!pm.isEmailCorrect(email)) {
            return "errorEmailForm";
        }
        if (!pm.isPasswordCorrect(password)) {
            return "errorPassword";
        }

        if (!pm.isNameAndSurnameCorrect(name, surname)) {
            return "errorNameOrSurname";
        }

        if (pm.isUserExist(email)) {
            return "errorUserExists";
        }

        LanguageManager languageManager = new LanguageManager();
        language = languageManager.setLanguageToUser(language);
        user = new User(name, surname, email, password, position, language);
        language = languageManager.setLanguageToSession(language);

        UserManager userManager = new UserManager();
        Long id = userManager.addUser(user);
        user.setId(id);
        user.setPassword(null);

        return "success";
    }

    public String getLanguage() {
        return language;
    }

    public User getUser() {
        return user;
    }
}
