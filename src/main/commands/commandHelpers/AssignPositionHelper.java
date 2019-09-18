package commands.commandHelpers;

import entity.User;
import servises.mailManager.MailManager;
import servises.messageManager.MessageManager;
import servises.parameterManager.ParameterManager;
import servises.userManager.UserManager;

public class AssignPositionHelper implements CommandHelper {

    private String email;
    private String position;
    private User currentUser;

    public AssignPositionHelper(String email, String position,User user) {
        this.email = email;
        this.position = position;
        currentUser = user;
    }

    @Override
    public String handle() {
        ParameterManager parameterManager = new ParameterManager();
        if (!parameterManager.isEmailCorrect(email)) {
            return "errorEmailForm";
        }
        UserManager userManager = new UserManager();
        User user = userManager.getUserByEmail(email);
        if (user == null) {
            return "errorUserNotExists";
        }
        if (user.getPosition().equals(position)) {
            return "errorPosition";
        }
        int result = userManager.setUserPosition(user, position);
        MailManager mail = new MailManager();
        if (result != 0) {
            if (!currentUser.equals(user)) {
                user.setPosition(position);
                mail.assignment(user);
            } else {
                currentUser.setPosition(position);
            }
        }
        return "successfulChanges";
    }
}
