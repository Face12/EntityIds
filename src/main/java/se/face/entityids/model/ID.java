/**
 * 
 */
package se.face.entityids.model;

import java.io.Serializable;

/**
 * @author Samuel Edqvist
 *
 */
public abstract class ID implements Serializable{

	private static final long serialVersionUID = -4308750764814030084L;
	
	private final String originalId;

	protected ID(String id){
		this.originalId = id;
	}
		
	public String getOriginalId() {
		return originalId;
	}
	
	@Override
	public String toString() {
		return "originalId=" + originalId;
	}

	@Override
	public int hashCode() {
		return originalId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ID other = (ID) obj;
		if (originalId == null) {
			if (other.originalId != null) {
				return false;
			}
		} else if (!originalId.equals(other.originalId)) {
			return false;
		}
		return true;
	}
}
