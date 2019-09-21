package commands.commandHelpers;

import commands.Command;

public interface CommandHelper {
    /**
     * This method is used in {@link Command} to encapsulate some logic.
     * @return string that can be used either to identify the target page
     * or define some message.
     */
    String handle();
}
