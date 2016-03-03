/**
 * 
 */
package se.face.entityids.model.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.face.entityids.exception.InvalidIdException;
import se.face.entityids.model.ValidID;
import se.face.entityids.model.impl.enumtypes.IdentityNumberError;
import se.face.entityids.validation.Modul10;

/**
 * @author Samuel
 *
 */
public class CompanyRegistrationNumberSE extends ValidID {

	private static final long serialVersionUID = -2131631902038084290L;

	private PersonalIdentityNumberSE personalIdentityNumberSE;	
	
	private static final Pattern validFormatNonPersonOrgnr= 
			Pattern.compile("^(16)?\\d{2}+[2-9]\\d{3}+[- ]?+\\d{4}+$");
	
	/**
	 * Creates a valid Swedish company registration number.
	 * The registration number must either be a valid {@link PersonalIdentityNumberSE} or
	 * conform with the pattern:<br>
	 * 
	 * (16)?nn[2-9]nnn[- ]?nnnk<br>
	 * 
	 * where n are numbers and k is a valid module 10 check number.
	 * 
	 * @param id
	 * @throws InvalidIdException
	 */
	public CompanyRegistrationNumberSE(String id) throws InvalidIdException {
		super(id);
	}

	@Override
	protected void validate() throws InvalidIdException {
		Matcher matcher = validFormatNonPersonOrgnr.matcher(getOriginalId());
		if (!matcher.find()){
			personalIdentityNumberSE = new PersonalIdentityNumberSE(getOriginalId());
		}
		else if (!Modul10.checkModul10(getNormalizedId())){
			throw new InvalidIdException(IdentityNumberError.WRONG_CHECKDIGIT.getCode());
		}
	}

	@Override
	protected String createNormalizedId() {
		if (isPersonalIdentityNumber()){
			return personalIdentityNumberSE.getNormalizedId();
		}
		return getOriginalId()
				.replaceFirst("^16", "")
				.replaceFirst("[ -]", "");
	}

	public boolean isPersonalIdentityNumber() {
		return personalIdentityNumberSE != null;
	}
	
	/**
	 * The company registration number in the form nnnnnn-nnnn
	 * or yyyyMMdd-nnnn in case of a personal number.
	 */
	public String getFormattedWithDash(){
		if (isPersonalIdentityNumber()){
			return personalIdentityNumberSE.getFormattedWithDash();
		}
		return getNonPersonFormattedWithDash();
	}
	
	/**
	 * The company registration number in the form nnnnnn-nnnn.
	 */
	public String get10DigitFormattedWithDash(){
		if (isPersonalIdentityNumber()){
			return personalIdentityNumberSE.get10DigitFormattedWithDash();
		}
		return getNonPersonFormattedWithDash();
	}

	private String getNonPersonFormattedWithDash() {
		final String normalizedId = getNormalizedId();
		return normalizedId.substring(0, 6)+"-"+normalizedId.substring(6);
	}
	
	public String get12DigitId(){
		if (isPersonalIdentityNumber()){
			return personalIdentityNumberSE.getNormalizedId();
		}
		return "16"+getNormalizedId();
	}
	
	public String get10DigitId(){
		if (isPersonalIdentityNumber()){
			return personalIdentityNumberSE.get10DigitId();
		}
		return getNormalizedId();
	}
	
	/**
	 * Get the {@link PersonalIdentityNumberSE} if such, returns null otherwise.
	 * @return
	 */
	public PersonalIdentityNumberSE getPersonalIdentityNumberSE(){
		return personalIdentityNumberSE;
	}
}
