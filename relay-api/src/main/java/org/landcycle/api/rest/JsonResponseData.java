package org.landcycle.api.rest;


public class JsonResponseData<E> extends JsonResponseSingleData<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ariaHpVersion = "1.0";
	private String entityUUID = "";
	private String errorCode = "SUCC";
	private String errorDescription = "";
	private String errorData = null;
	private int total = 0;
	private JsonResponseSingleData<E>[] entities = null;

	private int nmr = 0;
	private long serverTime;

	public JsonResponseData() {
		super();
		serverTime = System.currentTimeMillis();
	}

	public String getAriaHpVersion() {
		return ariaHpVersion;
	}

	public long getServerTime() {
		return serverTime;
	}

	public void setAriaHpVersion(String ariaHpVersion) {
		this.ariaHpVersion = ariaHpVersion;
	}

	public String getEntityUUID() {
		return entityUUID;
	}

	public void setEntityUUID(String entityUUID) {
		this.entityUUID = entityUUID;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public JsonResponseSingleData<E>[] getEntities() {
		return entities;
	}

	public void setEntities(JsonResponseSingleData<E>[] entities) {
		if (entities != null && entities.length > 0) {
			total = entities.length;
		}
		this.entities = entities;
	}

	public void setAttributes(E attributes) {
		super.setAttributes(attributes);
		if (attributes != null) {
			total = 1;
		}
	}

	public int getTotal() {
		return total;
	}

	public int getNmr() {
		return nmr;
	}

	public void setNmr(int nmr) {
		this.nmr = nmr;
	}

	public String getErrorData() {
		return errorData;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}

}
