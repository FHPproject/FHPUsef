package energy.usef.brp.model.dataModelFHP;


/**
 * @author 104903
 * @version 1.0
 * @created 20-oct-2017 12:03:28
 */
public class PORTFOLIO_IMBALANCE_FORECAST {

	public BIGINT ID;
	public TIMESTAMP DATETIME;
	public INT PTU_DURATION_MINS;
	public INT NUMBER_PTUS;
	public TIMESTAMP START_DATETIME;
	public DATE START_DATE;
	public TIMESTAMP END_DATETIME;
	public DATE END_DATE;

	public PORTFOLIO_IMBALANCE_FORECAST(){

	}

	public void finalize() throws Throwable {

	}

}