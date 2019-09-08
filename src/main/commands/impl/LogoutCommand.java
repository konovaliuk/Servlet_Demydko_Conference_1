package commands.impl;

import commands.Command;
import servises.configManager.ConfigManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
//        Enumeration<String> attributes = session.getAttributeNames();
//        while (attributes.hasMoreElements()) {
//            session.removeAttribute(attributes.nextElement());
//        }
       // session.removeAttribute("user");
        return ConfigManager.getProperty("index");
    }
}
