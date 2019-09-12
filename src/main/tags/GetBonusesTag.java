package tags;

import databaseLogic.dao.SpeakerDao;
import databaseLogic.factory.DaoFactory;
import entity.Speaker;
import entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class GetBonusesTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {

        User user = (User) pageContext.getSession().getAttribute("user");
        SpeakerDao speakerDao = DaoFactory.getSpeakerDao();
        Speaker speaker = speakerDao.getSpeakerById(user.getId());
        int bonuses = speakerDao.getSpeakerBonuses(speaker);
        String str = Integer.toString(bonuses);
        speakerDao.closeConnection();

        try {
            JspWriter writer = pageContext.getOut();
            writer.write(str);
        } catch (IOException e) {
            e.printStackTrace();                //todo logging
        }
        return SKIP_BODY;
    }
}
