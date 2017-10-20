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
 * @created 20-oct-2017 12:00:54
 */
/**
 * Entity for the SYNCHRONISATION_CONNECTION Table.
 */
@Entity
@Table(name = "AGR_CONSUMPTION_FORECAST")
public class AGR_CONSUMPTION_FORECAST {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
    //public BIGINT ID;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AGR_ID", nullable = false)
    private Long agr_id;
    //public BIGINT AGR_ID;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PROGNOSIS_ID", nullable = false)
    private Long prognosis_id;
    //public BIGINT PROGNOSIS_ID;

    public TIMESTAMP DATETIME;
	public VARCHAR TYPE;
	public INT PTU_DURATION_MINS;
	public INT NUMBER_PTUS;
	public TIMESTAMP START_DATETIME;
	public DATE START_DATE;
	public TIMESTAMP END_DATETIME;
	public DATE END_DATE;

	public AGR_CONSUMPTION_FORECAST(){

	}

	public void finalize() throws Throwable {

	}

}