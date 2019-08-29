package servises.parameterManager;

public class ParameterManager {

    public static boolean isEmpty(String... parameters) {
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
}
