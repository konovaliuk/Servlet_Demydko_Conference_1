package commands.commandEnum;

import commands.Command;
import commands.impl.*;

public enum CommandEnum {
    REGISTER {
        {
            this.command = new RegisterCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    ASSIGNMODERATOR {
        {
            this.command = new AssignModeratorCommand();
        }
    },
    ADDREPORT {
        {
            this.command = new AddReportCommand();
        }
    },
    FUTUREREPORTS {
        {
            this.command = new FutureReportsCommand();
        }
    },
    UPDATEREPORT {
        {
            this.command = new UpdateReportCommand();
        }
    };


    Command command;

    public Command getCurrentCommand() {
        return command;
    }
}
