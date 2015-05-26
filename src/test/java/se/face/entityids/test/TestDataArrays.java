/**
 * 
 */
package se.face.entityids.test;

/**
 * @author Samuel
 *
 */
public final class TestDataArrays {
	private TestDataArrays(){}
	

	public static final String[] PINSE_VALID_IDS =
			new String[]{
							"190001010107", 
							"19000101-0107",
							"19000101 0107",
							"198405140677", 
							"19840514-0677",
							"19840514 0677",
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
}
