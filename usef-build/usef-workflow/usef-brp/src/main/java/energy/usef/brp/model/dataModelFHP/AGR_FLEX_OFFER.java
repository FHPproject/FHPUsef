package energy.usef.brp.model.dataModelFHP;


/**
 * @author 104903
 * @version 1.0
 * @created 20-oct-2017 12:01:05
 */
public class AGR_FLEX_OFFER {

	public BIGINT ID;
	public BIGINT MESSAGE_ID;
	public BIGINT AGR_ID;
	public BIGINT AGR_FLEX_REQUEST_ID;
	public BIGINT AGR_FLEX_REQUEST_SEQUENCE;
	public TIMESTAMP DATETIME;
	public VARCHAR AGR_DOMAIN;
	public TIMESTAMP START_DATETIME;
	public DATE START_DATE;
	public TIMESTAMP END_DATETIME;
	public DATE END_DATE;
	public VARCHAR STATUS;
	public INT PTU_DURATION_MINS;
	public INT NUMBER_PTUS;
	public VARCHAR CURRENCY;

	public AGR_FLEX_OFFER(){

	}

	public void finalize() throws Throwable {

	}

}