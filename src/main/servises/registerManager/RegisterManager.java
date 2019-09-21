package servises.registerManager;

import databaseLogic.dao.RegisterDao;
import databaseLogic.factory.DaoFactory;
import entity.Report;
import entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class encapsulated some methods from {@link RegisterDao}
 */

public class RegisterManager {
    private RegisterDao registerDao;

    public List<Long> getReportsIdByUserId(User user) {
        registerDao = DaoFactory.getRegisterDao();
        List<Long> registerList = registerDao.getReportsIdByUserId(user.getId());
        registerDao.closeConnection();
        return registerList;
    }

    public void checkRegistrationForUser(List<Report> reportList, List<Long> registerList) {
        for (Long id : registerList) {
            for (Report report : reportList) {
                if (report.getId().equals(id)) {
                    report.setIsUserRegistered(true);
                }
            }
        }

    }

    public Map<Long, Integer> getCountOfVisitors(List<Report> reportList) {
        int count;
        Map<Long, Integer> countOfVisitors = new HashMap<>();
        RegisterDao registerDao = DaoFactory.getRegisterDao();
        for (Report report : reportList) {
            count = registerDao.getCountOfVisitors(report.getId());
            countOfVisitors.put(report.getId(), count);
        }
        registerDao.closeConnection();
        return countOfVisitors;
    }

    public int userRegister(Long userId, Long reportId) {
        registerDao = DaoFactory.getRegisterDao();
        int result = registerDao.userRegister(userId, reportId);
        registerDao.closeConnection();
        return result;
    }

    /**
     * Sets registration for certain {@link User}
     * @param report is a <code>Report</code> where user will be register.
     */
    public void makeUserRegistered(Report report) {
        report.setIsUserRegistered(true);
    }

    public List<User> getAllRegisteredUsers(Long reportId) {
        RegisterDao registerDao = DaoFactory.getRegisterDao();
        List<User> userList = registerDao.getAllRegisteredUsers(reportId);
        registerDao.closeConnection();
        return userList;
    }
}
