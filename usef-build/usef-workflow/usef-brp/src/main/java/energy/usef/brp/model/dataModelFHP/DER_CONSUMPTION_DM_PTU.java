package energy.usef.brp.model.dataModelFHP;


/**
 * @author 104903
 * @version 1.0
 * @created 20-oct-2017 12:02:35
 */
public class DER_CONSUMPTION_DM_PTU {

	public BIGINT ID;
	public BIGINT DER_CONSUMPTION_DM_ID;
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
	public FLOAT ACTIVE_POWER;

	public DER_CONSUMPTION_DM_PTU(){

	}

	public void finalize() throws Throwable {

	}

}