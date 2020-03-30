package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ReportCommand;
import seedu.address.logic.commands.ReportCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.TopLevelParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.report.ReportWindowParser;
import seedu.address.model.expenditure.BaseExp;
import seedu.address.model.Model;

import seedu.address.model.ReadOnlyAccountList;
import seedu.address.model.expenditure.Expenditure;

import seedu.address.model.expenditure.Repeat;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final TopLevelParser topLevelParser;
    private final ReportWindowParser reportWindowParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        topLevelParser = new TopLevelParser();
        reportWindowParser = new ReportWindowParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = topLevelParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAccountList());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReportCommandResult executeReportWindowCommand(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        ReportCommandResult commandResult;
        ReportCommand command = reportWindowParser.parse(commandText);
        commandResult = command.execute(model);
        return commandResult;
    }

    @Override
    public ReadOnlyAccountList getAddressBook() {
        return model.getAccountList();
    }

    @Override
    public ObservableList<Expenditure> getFilteredExpenditureList() {
        return model.getFilteredExpenditureList();
    }

    @Override
    public ObservableList<Repeat> getFilteredRepeatList() {
        return model.getFilteredRepeatList();
    }

    @Override
    public ObservableList<BaseExp> getFilteredBaseExpList() {
        return model.getFilteredBaseExpList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
