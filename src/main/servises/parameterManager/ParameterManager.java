package servises.parameterManager;

public class ParameterManager {

    public static boolean isAllEmpty(String... parameters) {
        int count = 0;
        for (String p : parameters) {
            if (p == null || p.isEmpty())
                count++;
        }
        if (count == parameters.length) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String... parameters) {
        for (String p : parameters) {
            if (p == null || p.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}

