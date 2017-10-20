package energy.usef.brp.model.dataModelFHP;


/**
 * @author 104903
 * @version 1.0
 * @created 20-oct-2017 12:01:59
 */
public class AGR_FLEX_ORDER_PTU {

	public BIGINT ID;
	public BIGINT AGR_FLEX_ORDER_ID;
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
	public DATE START_DATE;
	public TIMESTAMP END_DATETIME;
	public DATE END_DATE;
	public FLOAT ACTIVE_POWER;
	public FLOAT PRICE;

	public AGR_FLEX_ORDER_PTU(){

	}

	public void finalize() throws Throwable {

	}

}