package energy.usef.brp.model.dataModelFHP;


/**
 * @author 104903
 * @version 1.0
 * @created 20-oct-2017 12:03:11
 */
public class IMBALANCE_PRICE_SURPLUS_TSO_PTU {

	public BIGINT ID;
	public VARCHAR TYPE;
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
	public FLOAT PRICE;

	public IMBALANCE_PRICE_SURPLUS_TSO_PTU(){

	}

	public void finalize() throws Throwable {

	}

}