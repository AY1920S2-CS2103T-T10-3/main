package seedu.address.logic.parser.report;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.logic.commands.general.HelpCommand;
import seedu.address.logic.commands.report.ViewReportCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Report;

/**
 * Parse view report.
 */
public class ViewReportCommandParser implements Parser<ViewReportCommand> {
    public ViewReportCommandParser() {

    }

    @Override
    public ViewReportCommand parse(String userInput) throws ParseException {
        String userInputTrimmed = userInput.trim();
        String[] userInputArray = userInputTrimmed.split(" ");

        if (userInputArray.length < 3) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewReportCommand.MESSAGE_FAIL));
        }

        String startDateStr = userInputArray[1];
        String endDateStr = userInputArray[2];
        LocalDate startDate;
        LocalDate endDate;

        try {

            startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_DATE);
            endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ISO_DATE);

        } catch (DateTimeParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        if (endDate.isBefore(startDate)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        Report report = new Report(startDate, endDate);

        return new ViewReportCommand(report);
    }

}
