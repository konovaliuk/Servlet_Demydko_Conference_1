package commands.impl;

import commands.Command;
import commands.commandHelpers.impl.ShowOfferedReportsHelper;
import servises.configManager.ConfigManager;

import javax.servlet.http.HttpServletRequest;

public class ShowOfferedReportsCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {

        String requestButton = request.getParameter("button");
        String requestMaxCount = request.getParameter("maxCount");
        Integer sessionButton = (Integer) request.getSession().getAttribute("offeredButton");
        Integer sessionOffset = (Integer) request.getSession().getAttribute("offsetOffered");
        Integer sessionMaxCount = (Integer) request.getSession().getAttribute("maxCountOffered");

        ShowOfferedReportsHelper helper = new ShowOfferedReportsHelper(
                requestButton, requestMaxCount, sessionButton, sessionOffset, sessionMaxCount);
        String result = helper.handle();

        request.getSession().setAttribute("offeredReportList", helper.getOfferedConferenceList());
        request.getSession().setAttribute("maxCountOffered", helper.getMaxCount());
        request.getSession().setAttribute("offsetOffered", helper.getOffset());
        request.getSession().setAttribute("offeredButton", helper.getCurrentButton());
        request.getSession().setAttribute("buttonsOffered", helper.getButtons());

        return ConfigManager.getProperty(result);
    }
}
