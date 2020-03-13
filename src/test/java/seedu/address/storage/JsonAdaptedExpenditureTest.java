package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedExpenditure.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalExpenditures.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.expenditure.Amount;
import seedu.address.model.expenditure.Date;
import seedu.address.model.expenditure.Id;
import seedu.address.model.expenditure.Info;

public class JsonAdaptedExpenditureTest {
    private static final String INVALID_INFO = "R@chel";
    private static final String INVALID_ID = "+651234";
    private static final String INVALID_DATE = " ";
    private static final double INVALID_AMOUNT = -1;
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_INFO = BENSON.getInfo().toString();
    private static final String VALID_ID = BENSON.getId().toString();
    private static final double VALID_AMOUNT = BENSON.getAmount().value;
    private static final String VALID_DATE = BENSON.getDate().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedExpenditure person = new JsonAdaptedExpenditure(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidInfo_throwsIllegalValueException() {
        JsonAdaptedExpenditure person =
                new JsonAdaptedExpenditure(INVALID_INFO, VALID_ID, VALID_AMOUNT, VALID_DATE, VALID_TAGS);
        String expectedMessage = Info.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullInfo_throwsIllegalValueException() {
        JsonAdaptedExpenditure person = new JsonAdaptedExpenditure(null, VALID_ID,
                VALID_AMOUNT, VALID_DATE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Info.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        JsonAdaptedExpenditure person =
                new JsonAdaptedExpenditure(VALID_INFO, INVALID_ID, VALID_AMOUNT, VALID_DATE, VALID_TAGS);
        String expectedMessage = Id.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test

    public void toModelType_nullId_throwsIllegalValueException() {
        JsonAdaptedExpenditure person = new JsonAdaptedExpenditure(VALID_INFO, null,
                VALID_AMOUNT, VALID_DATE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Id.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedExpenditure person =
                new JsonAdaptedExpenditure(VALID_INFO, VALID_ID, INVALID_AMOUNT, VALID_DATE, VALID_TAGS);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedExpenditure person =
                new JsonAdaptedExpenditure(VALID_INFO, VALID_ID, INVALID_AMOUNT, VALID_DATE, VALID_TAGS);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedExpenditure person =
                new JsonAdaptedExpenditure(VALID_INFO, VALID_ID, VALID_AMOUNT, INVALID_DATE, VALID_TAGS);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedExpenditure person = new JsonAdaptedExpenditure(VALID_INFO, VALID_ID,
                VALID_AMOUNT, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedExpenditure person =
                new JsonAdaptedExpenditure(VALID_INFO, VALID_ID, VALID_AMOUNT, VALID_DATE, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
