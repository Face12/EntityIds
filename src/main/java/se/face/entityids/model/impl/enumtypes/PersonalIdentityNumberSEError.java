/**
 * 
 */
package se.face.entityids.model.impl.enumtypes;

/**
 * @author samweisz
 *
 */
public enum PersonalIdentityNumberSEError {
	WRONG_FORMAT(1),
	WRONG_DATE(2),
	WRONG_CHECKDIGIT(3),
	;
	private final int code;
	
	private PersonalIdentityNumberSEError(final int code){
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	public static PersonalIdentityNumberSEError byCode(int code){
		for (PersonalIdentityNumberSEError error : PersonalIdentityNumberSEError.values()){
			if (error.code == code){
				return error;
			}
		}
		return null;
	}
}