/**
 * 
 */
package se.face.entityids.test;

import java.util.stream.Stream;

/**
 * @author Samuel
 *
 */
public final class TestDataArrays {
	private TestDataArrays(){}
	
	//***************************** Swedish personal identification numbers ******************************
	public static final String[] PINSE_VALID_IDS =
			new String[]{
							"190001010107", 
							"19000101-0107",
							"19000101 0107",
							"194301013787", 
							"19430101-3787",
							"19430101 3787",
							"198402290103", //leap year
							"200001010107",
							"200101010205"
						};
	
	public static final String[][] PINSE_INVALID_FORMAT_AND_REASON = 
			new String[][]{
							{"DET12323010",		"it contains letters"},
							{"19000101010",		"it's too short, only 11 digits"},
							{"19000101--0107",	"there are two dashes before last four digits"},
							{"1900-0101-0107",	"there is a dash after the year (and last four digits)"},
							{"1900-01010107",	"there is a dash after year"},
							{"1900 01010107",	"there is a blank after year"}
						};
	public static final String[][] PINSE_INVALID_DATE_AND_REASON = 
			new String[][]{
							{"198405320107",	"day is 32"},
							{"198404310108",	"it's april and day is 30"},
							{"198404000108",	"day is zero"},
							{"198400010108",	"month is zero"},
							{"198413010108",	"month is 13"},
							{"190002290104",	"it's 29 February, but 1900 is not a leap year"},
							{"22000101-0107",	"it's a future date"}
						};
	
	public static final String[] PINSE_INVALID_CHECK_DIGIT = 
			new String[]{
							"198405140678",
							"200101010206"
						};
	

	public static final String[][] PINSE_GUESSABLE_AND_VALID_NO_CENTURY_IDS_AND_EXPECTED_CENTURY =
			new String[][]{
							{"0001010107",	"20"},
							{"000101-0107",	"20"},
							{"000101 0107", "20"},
							{"200101 0103", "19"},
							{"4301013787",  "19"},
							{"430101-3787", "19"},
							{"430101 3787", "19"},
							{"8402290103",  "19"}//leap year
						};
	
	public static final String[][] PINSE_INVALID_AFTER_GUESS_NO_CENTURY_IDS =
			new String[][]{
							{"8405320107",	"day is 32"},
							{"8404310108",	"it's april and day is 30"},
							{"4301013788", 	"wrong check digit"},
							{"0101010206", 	"wrong check digit"},
							{"DET12323010", "it contains letters"},
							{"000101010", "it's too short"}
						};
	//*************************** Swedish personal identification numbers - END *****************************
	
	//******************************* Swedish company registration numbers **********************************
	public static final String[] CRNSE_NON_PERSON_VALID = new String[]{
			"5555555555",
			"555555-5555",
			"555555 5555",
			"165555555555",
			"16555555-5555",
			"16555555 5555",
	};

	public static final String[] CRNSE_NON_PERSON_INVALID_CHECK_DIGIT = new String[]{
			"5555555556", 
			"165555555556",
	};
	//***************************** Swedish company registration numbers - END *******************************
	
	public static String[] arrayConcat(String[]...arrays){
		return Stream.of(arrays).flatMap(Stream::of).toArray(String[]::new);
	}
}
