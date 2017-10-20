package energy.usef.brp.model.dataModelFHP;


/**
 * @author 104903
 * @version 1.0
 * @created 20-oct-2017 12:02:46
 */
public class FLEX_ALG {

	public BIGINT ID;
	public TIMESTAMP DATETIME;
	public VARCHAR TYPE;
	public INT PTU_DURATION_MINS;
	public INT NUMBER_PTUS;
	public TIMESTAMP START_DATETIME;
	public DATE START_DATE;
	public TIMESTAMP END_DATETIME;
	public DATE END_DATE;
	public FLOAT IMBALANCE_PAYMENT;
	public FLOAT FLEX_PAYMENT;
	public FLOAT CASH_FLOW;

	public FLEX_ALG(){

	}

	public void finalize() throws Throwable {

	}

}