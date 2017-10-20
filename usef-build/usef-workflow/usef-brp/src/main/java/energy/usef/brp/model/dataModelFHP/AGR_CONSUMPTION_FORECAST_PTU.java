package energy.usef.brp.model.dataModelFHP;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author 104903
 * @version 1.0
 * @created 20-oct-2017 12:00:57
 */
/**
 * Entity for the SYNCHRONISATION_CONNECTION Table.
 */
@Entity
@Table(name = "AGR_CONSUMPTION_FORECAST_PTU")
public class AGR_CONSUMPTION_FORECAST_PTU {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
    //public BIGINT ID;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AGR_CONSUMPTION_FORECAST_ID", nullable = false)
    private Long agrConsumptionForecastId;
    //public BIGINT AGR_CONSUMPTION_FORECAST_ID;
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

	public AGR_CONSUMPTION_FORECAST_PTU(){

	}

	public void finalize() throws Throwable {

	}

}