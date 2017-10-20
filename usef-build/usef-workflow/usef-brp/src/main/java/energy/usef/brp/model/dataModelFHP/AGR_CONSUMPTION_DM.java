package energy.usef.brp.model.dataModelFHP;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author 104903
 * @version 1.0
 * @created 20-oct-2017 12:00:44
 */
/**
 * Entity for the AGR_CONSUMPTION_DM Table.
 */
@Entity
@Table(name = "AGR_CONSUMPTION_DM")
public class AGR_CONSUMPTION_DM extends AGR{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
	//public BIGINT ID;

    //Hereda de AGR
	//public BIGINT AGR_ID;
    
    @Column(name = "DATETIME", nullable = true)
    private Date datetime;
	//public TIMESTAMP DATETIME;
    
    //Hereda de AGR
	//public VARCHAR TYPE;
        
    @Column(name = "PTU_DURATION_MINS", nullable = false)
    private int ptuDurationMins;    
	//public INT PTU_DURATION_MINS;
    
    @Column(name = "NUMBER_PTUS", nullable = false)
    private int numberPTUs; 
	//public INT NUMBER_PTUS;
    
    @Column(name = "START_DATETIME", nullable = true)
    private Date startDatetime;
	//public TIMESTAMP START_DATETIME;
    
    @Column(name = "START_DATE", nullable = true)
    private Date startDate;
	//public DATE START_DATE;
    
    @Column(name = "END_DATETIME", nullable = true)
    private Date endDatetime;
	//public TIMESTAMP END_DATETIME;
    
    @Column(name = "END_DATE", nullable = true)
    private Date endDate;
	//public DATE END_DATE;


}