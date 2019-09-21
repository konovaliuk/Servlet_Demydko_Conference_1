package commands;



import javax.servlet.http.HttpServletRequest;

public interface Command {
    /**
     * This method is used by {@link Command} to execute some logic.
     * @return string that defines the target page.
     */
    String execute(HttpServletRequest request);
}
