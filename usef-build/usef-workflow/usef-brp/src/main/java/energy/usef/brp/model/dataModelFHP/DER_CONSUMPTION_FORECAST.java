package energy.usef.brp.model.dataModelFHP;


/**
 * @author 104903
 * @version 1.0
 * @created 20-oct-2017 12:02:38
 */
public class DER_CONSUMPTION_FORECAST {

	public BIGINT ID;
	public BIGINT DER_ID;
	public TIMESTAMP DATETIME;
	public VARCHAR TYPE;
	public INT PTU_DURATION_MINS;
	public INT NUMBER_PTUS;
	public TIMESTAMP START_DATETIME;
	public DATE START_DATE;
	public TIMESTAMP END_DATETIME;
	public DATE END_DATE;

	public DER_CONSUMPTION_FORECAST(){

	}

	public void finalize() throws Throwable {

	}

}