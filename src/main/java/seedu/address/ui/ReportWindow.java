package seedu.address.ui;

import java.io.File;
import java.util.logging.Logger;

import javafx.event.EventHandler;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ReportCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.exceptions.PrinterException;


/**
 * The Report Window. Provides statistics on expenditure
 * based on the user input. Supports export feature as well
 * in the future.
 */
public class ReportWindow extends UiPart<Stage> {

    //TODO: extract out the change scene code and make it a method

    public static final String REPORT_MESSAGE = "Generating report...";

    private static final Logger logger = LogsCenter.getLogger(ReportWindow.class);
    private static final String FXML = "ReportWindow.fxml";

    private Logic logic;
    private ReportCommandBox box;
    private ResultDisplay display;
    private MenuBar menuBar;
    private Graph currentGraph;
    private CommandResult firstResult = null;
    private ReportCommandResult currentGraphResult = null;

    /**
     * Creates a new Report Window.
     *
     * @param root Stage to use as the root of the Report Window.
     */
    public ReportWindow(Stage root) {
        super(FXML, root);
        root.initModality(Modality.APPLICATION_MODAL);

    }

    /**
     * Creates a new HelpWindow.
     */
    public ReportWindow() {
        this(new Stage());
        init();
    }

    /**
     * Initialise the report window
     * with necessary components.
     */
    private void init() {
        this.box = new ReportCommandBox(this::executeReportWindowCommand);
        this.display = new ResultDisplay();
        this.menuBar = new MenuBar();
        initMenu();
        initStyle();
        initCloseHandler();
    }

    /**
     * Initialise the menu
     * component of the report window.
     */
    private void initMenu() {
        Label label = new Label("Export");
        label.setFont(new Font("Segoe UI Light", 14));
        label.setOnMouseClicked(click -> {

        });
        Menu menu = new Menu("", label);
        menuBar.getMenus().add(menu);

        Label label1 = new Label("Print");
        label.setFont(new Font("Segoe UI Light", 14));
        label1.setOnMouseClicked(click -> {
            try {
                print();
            } catch (PrinterException e) {
                logger.info("Invalid printer");
                display.setFeedbackToUser(e.getMessage());
            }
        });

        Menu menu1 = new Menu("", label1);
        menuBar.getMenus().add(menu1);
    }

    private void initStyle() {
        getRoot().initStyle(StageStyle.UTILITY);
    }

    /**
     * TODO: ADD DOC
     */
    private void initCloseHandler() {
        getRoot().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                display.clear();
                getRoot().hide();
            }
        });
    }

    private void setScene() {
        VBox topBox = new VBox(menuBar, box.getRoot());
        VBox vbox = new VBox(topBox, display.getRoot(), (Node) currentGraph.getGraph());
        Scene scene = new Scene(vbox);
        scene.getStylesheets().addAll(new File("src/main/resources/view/DarkTheme.css").toURI().toString());
        getRoot().setScene(scene);
        getRoot().show();
    }

    /**
     * Shows the report window.
     * Method is called when the report
     * button in Main Window in clicked.
     *
     * @throws IllegalStateException <ul>
     *                               <li>
     *                               if this method is called on a thread other than the JavaFX Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    public void showEmpty() {
        logger.fine("Showing empty report page.");
        this.currentGraph = new Pie();
        setScene();
    }


    /**
     * Shows the expenditure breakdown
     * in user inputted graph type.
     * Method is called when input is from
     * Main Window.
     */
    public void showResult(CommandResult result) {
        logger.fine("Showing report page.");

        this.firstResult = result;

        if (result.isPieGraph()) {
            this.currentGraph = new Pie();

        } else if (result.isBarGraph()) {
            this.currentGraph = new Bar();
        }

        assert currentGraph != null;
        currentGraph.constructGraph(result);
        setScene();

    }

    /**
     * Shows the expenditure breakdown
     * in user inputted graph type.
     * Method is called when input is from
     * Report Window.
     */
    public void showResult(ReportCommandResult result) {
        logger.fine("Showing report page.");

        if (result.isPieGraph()) {
            this.currentGraph = new Pie();

        } else if (result.isBarGraph()) {
            this.currentGraph = new Bar();
        }

        assert currentGraph != null;
        currentGraph.constructGraph(result);
        setScene();
    }

    /**
     * Sends a print job of report to printer.
     *
     * @param result command result of user input.
     * @throws PrinterException is thrown when printer cannot
     *                          successfully finish a job.
     */
    public void print(CommandResult result) throws PrinterException {
        logger.fine("Exporting");
        Graph toPrint = null;
        Node graph;

        if (result.isPieGraph()) {
            toPrint = new Pie();
        } else if (result.isBarGraph()) {
            toPrint = new Bar();
        }

        assert toPrint != null;
        toPrint.constructGraph(result);
        graph = (Node) toPrint.getGraph();
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4,
                PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if (printerJob != null) {
            display.setFeedbackToUser("Printing");
            boolean jobStatus = printerJob.printPage(pageLayout, graph);
            if (jobStatus) {
                printerJob.endJob();
            } else {
                printerJob.cancelJob();
                throw new PrinterException("Set available printer as default printer");
            }
        } else {
            display.setFeedbackToUser("Construct a graph before printing.");
        }
    }

    /**
     * Sends a print job of report to printer.
     *
     * @throws PrinterException is thrown when printer cannot
     *                          successfully finish a job.
     */
    public void print() throws PrinterException {
        logger.fine("Exporting");

        if (currentGraphResult == null && firstResult != null) {

            print(firstResult);

        } else if (currentGraphResult != null) {
            Graph toPrint = null;
            Node graph;

            if (currentGraphResult.isPieGraph()) {
                toPrint = new Pie();
            } else if (currentGraphResult.isBarGraph()) {
                toPrint = new Bar();
            }

            assert toPrint != null;
            toPrint.constructGraph(currentGraphResult);
            graph = (Node) toPrint.getGraph();
            Printer printer = Printer.getDefaultPrinter();
            PageLayout pageLayout = printer.createPageLayout(Paper.A4,
                    PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
            PrinterJob printerJob = PrinterJob.createPrinterJob();

            if (printerJob != null) {
                display.setFeedbackToUser("Printing");
                boolean jobStatus = printerJob.printPage(pageLayout, graph);
                if (jobStatus) {
                    printerJob.endJob();
                } else {
                    printerJob.cancelJob();
                    throw new PrinterException("Set available printer as default printer");
                }
            }

        } else {
            display.setFeedbackToUser("Construct a graph before printing.");
        }
    }


    //TODO: ask can use SwinfFXutils

    /**
     * Export report (in progress not done)
     * @param result
     */
    public void export(CommandResult result) {
        Graph toPrint = null;
        Node graph;

        if (result.isPieGraph()) {
            toPrint = new Pie();
        } else if (result.isBarGraph()) {
            toPrint = new Bar();
        }

        assert toPrint != null;
        toPrint.constructGraph(result);
        graph = (Node) toPrint.getGraph();
        WritableImage img = graph.snapshot(new SnapshotParameters(), null);

    }

    /**
     * Returns true if the report window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the report window.
     */
    public void hide() {
        getRoot().hide();
    }


    public void addLogic(Logic logic) {
        this.logic = logic;
    }

    /**
     * Executor method for report command box.
     */
    private ReportCommandResult executeReportWindowCommand(String commandText)
            throws CommandException, ParseException, PrinterException {

        ReportCommandResult result = null;
        try {
            result = logic.executeReportWindowCommand(commandText);
            logger.info("command executed " + commandText);
            display.setFeedbackToUser(result.getFeedbackToUser());

            if (result.isExitReport()) {
                display.clear();
                getRoot().hide();
            } else if (result.isPrintReport()) {
                print();
            } else {
                this.currentGraphResult = result;
                showResult(result);
            }

        } catch (CommandException | ParseException | PrinterException e) {

            if (e instanceof CommandException || e instanceof ParseException) {
                logger.info("Invalid command :" + commandText);
            } else if (e instanceof  PrinterException) {
                logger.info("Invalid printer");
            }

            display.setFeedbackToUser(e.getMessage());
            throw e;
        }
        return result;
    }
}
