package seedu.saveit.model;

import javafx.collections.ObservableList;
import seedu.saveit.model.expenditure.Date;
import seedu.saveit.model.expenditure.Repeat;
import seedu.saveit.model.expenditure.UniqueExpenditureList;

import java.time.LocalDate;
import java.util.Map;

/**
 * An interface containing methods that allow expenditure data to be read from an account.
 */
public interface ReportableAccount {
    /**
     * @param date the date
     * @return a UniqueExpenditureList containing the expenditures on that date
     */
    UniqueExpenditureList getExpByDate(String date);

    /**
     * @param date the date
     * @return a UniqueExpenditureList containing the expenditures on that date
     */
    UniqueExpenditureList getExpByDate(LocalDate date);

    /**
     * returns expenditures in the interval specified
     * @param startDate the inclusive start date
     * @param endDate the inclusive end date
     * @return a Map of key: date string & value: UniqueExpenditureList
     */
    Map<String, UniqueExpenditureList> getExpFromToInclusive(String startDate, String endDate);

    /**
     * returns expenditures in the interval specified
     * @param startDate the inclusive start date
     * @param endDate the inclusive end date
     * @return a Map of key: date string & value: UniqueExpenditureList
     */
    Map<String, UniqueExpenditureList> getExpFromToInclusive(Date startDate, Date endDate);

    /**
     * @param date the date
     * @return a list containing the repeats on that date
     */
    ObservableList<Repeat> getRepeatByDate(LocalDate date);

    Map<Repeat, Double> getRepeatFromToInclusive(Date startDate, Date endDate);
}
