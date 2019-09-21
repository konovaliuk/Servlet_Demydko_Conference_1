package commands.impl;

import commands.Command;
import commands.commandHelpers.impl.PastReportsHelper;
import servises.configManager.ConfigManager;

import javax.servlet.http.HttpServletRequest;


public class PastReportsCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {

        String requestButton = request.getParameter("button");
        String requestMaxCount = request.getParameter("maxCountPast");
        Integer sessionButton = (Integer) request.getSession().getAttribute("pastButton");
        Integer sessionOffset = (Integer) request.getSession().getAttribute("offsetPast");
        Integer sessionMaxCount = (Integer) request.getSession().getAttribute("maxCountPast");

        PastReportsHelper helper = new PastReportsHelper(requestButton, sessionButton, requestMaxCount, sessionOffset, sessionMaxCount);
        String result = helper.handle();

        request.getSession().setAttribute("pastReportList", helper.getPastConferenceList());
        request.getSession().setAttribute("maxCountPast", helper.getMaxCount());
        request.getSession().setAttribute("offsetPast", helper.getOffset());
        request.getSession().setAttribute("pastButton", helper.getCurrentButton());
        request.getSession().setAttribute("pastReportPresence", helper.getPastReportPresence());
        request.getSession().setAttribute("buttonsPast", helper.getButtons());

        return ConfigManager.getProperty(result);
    }

}

