package servises.presenceManager;

import databaseLogic.dao.PresenceDao;
import databaseLogic.factory.DaoFactory;
import entity.Report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresenceManager {

    PresenceDao presenceDao;

    public Map<Long, Integer> getPresence(List<Report> pastReportsList) {
        presenceDao = DaoFactory.getPresenceDao();
        Map<Long, Integer> pastReportPresence = new HashMap<>();
        for (
                Report report : pastReportsList) {
            int presence = presenceDao.getPresence(report.getId());
            pastReportPresence.put(report.getId(), presence);
        }
        presenceDao.closeConnection();
        return pastReportPresence;
    }


    public int addPresence(Long id, int count) {
        presenceDao = DaoFactory.getPresenceDao();
        int result = presenceDao.addPresence(id, count);
        presenceDao.closeConnection();
        return result;
    }
}
