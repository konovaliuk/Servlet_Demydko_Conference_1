package servises.paginationManager;

import java.util.ArrayList;
import java.util.List;

public class PaginationManager {

    public List<Integer> getButtons(int amountOfReports, int maxCountPerPage) {
        double buttons = amountOfReports / (double) maxCountPerPage;
        buttons = Math.ceil(buttons);
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= buttons; i++) {
            list.add(i);
        }
        return list;
    }
}
