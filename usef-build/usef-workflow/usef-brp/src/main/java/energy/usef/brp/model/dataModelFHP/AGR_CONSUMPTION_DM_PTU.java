package energy.usef.brp.model.dataModelFHP;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;


/**
 * @author 104903
 * @version 1.0
 * @created 20-oct-2017 12:00:51
 */
/**
 * Entity for the SYNCHRONISATION_CONNECTION Table.
 */
@Entity
@Table(name = "AGR_CONSUMPTION_DM_PTU")
public class AGR_CONSUMPTION_DM_PTU extends AGR_CONSUMPTION_DM{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
        //public BIGINT ID;
    
    //Hereda de AGR
	//public BIGINT AGR_CONSUMPTION_DM_ID;
    
    /**
    * Number of the first PTU in this schedule step with regard to the schedule (the
    * first one is 1)
     */
    @Column(name = "START_PTU", nullable = false)
    private int startPtu;  
    //public INT START_PTU;
	
    /**
     * The number of the PTUs this element represents. Optional, default value is 1
     */
    @Column(name = "NUMBER_PTUS", nullable = false)
    private int numberPtus;     
	//public INT NUMBER_PTUS;
    
    @Column(name = "START_DATETIME", nullable = false)
    private Date startDatetime; 
	//public TIMESTAMP START_DATETIME;
    
    @Column(name = "START_DATE", nullable = false)
    private Date startDate; 
	//public DATE START_DATE;
    
    @Column(name = "END_DATETIME", nullable = false)
    private Date endDatetime; 
	//public TIMESTAMP END_DATETIME;
	
    @Column(name = "END_DATE", nullable = false)
    private Date endDate; 
        //public DATE END_DATE;
    
    @Column(name = "ACTIVE_POWER", nullable = false)
    private Float activePower; 
	//public FLOAT ACTIVE_POWER;



}