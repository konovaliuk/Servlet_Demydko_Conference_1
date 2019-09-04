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
    ASSIGNPOSITION {
        {
            this.command = new AssignPositionCommand();
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
    },
    OFFERREPORT {
        {
            this.command = new OfferReportCommand();
        }
    },
    SHOWOFFEREDREPORTS {
        {
            this.command = new ShowOfferedReportsCommand();
        }
    },
    EDITREPORT {
        {
            this.command = new EditReportCommand();
        }
    },
    DELETEOFFEREDREPORT {
        {
            this.command = new DeleteOfferedReportCommand();
        }
    },
    CONFERENCEREGISTER {
        {
            this.command = new ConferenceRegisterCommand();
        }
    },
    ADDSPEAKERRATING {
        {
            this.command = new AddSpeakerRatingCommand();
        }
    },
    PASTREPORTS {
        {
            this.command = new PastReportsCommand();
        }
    },
    ADDPRESENCE {
        {
            this.command = new AddPresenceCommand();
        }
    },
    DELETEPASTREPORT {
        {
            this.command = new DeletePastReportCommand();
        }
    },
    ADDBONUSES {
        {
            this.command = new AddBonusesCommand();
        }
    },
    SHOWBONUSES {
        {
            this.command = new ShowBonusesCommand();
        }
    },
    CHANGELANGUAGE {
        {
            this.command = new ChangeLanguageCommand();
        }
    },
    REPORTINDEX {
        {
            this.command = new ReportIndexCommand();
        }
    };


    Command command;

    public Command getCurrentCommand() {
        return command;
    }
}
