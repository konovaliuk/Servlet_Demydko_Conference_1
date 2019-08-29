package servises.messageManager;

public class BuildMessageManager {

    private BuildMessageManager() {
    }

    public static String buildMessage(String message, String... args) {
        String[] arr = message.split(" ");
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals("$")) {
                arr[i] = args[count++];
            }
            sb.append(arr[i]).append(" ");
        }
        return sb.toString();
    }
}
