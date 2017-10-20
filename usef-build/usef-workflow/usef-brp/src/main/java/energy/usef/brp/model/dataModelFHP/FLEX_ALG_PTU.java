package energy.usef.brp.model.dataModelFHP;


/**
 * @author 104903
 * @version 1.0
 * @created 20-oct-2017 12:02:57
 */
public class FLEX_ALG_PTU {

	public BIGINT ID;
	public BIGINT FLEX_ALG_ID;
	public DATE DAY;
	/**
	 * Number of the first PTU in this schedule step with regard to the schedule (the
	 * first one is 1)
	 */
	public INT START_PTU;
	/**
	 * The number of the PTUs this element represents. Optional, default value is 1
	 */
	public INT NUMBER_PTUS;
	public TIMESTAMP START_DATETIME;
	public TIMESTAMP END_DATETIME;
	public FLOAT IMBALANCE_ENERGY;
	public FLOAT IMBALANCE_PAYMENT;
	public FLOAT FLEX_ENERGY;
	public FLOAT FLEX_PAYMENT;
	public FLOAT CASH_FLOW;

	public FLEX_ALG_PTU(){

	}

	public void finalize() throws Throwable {

	}

}