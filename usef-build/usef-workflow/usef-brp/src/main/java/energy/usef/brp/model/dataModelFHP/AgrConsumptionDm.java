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
public class AgrConsumptionDm { 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
	//public BIGINT ID;

    @Column(name = "AGR_ID", nullable = false)
    private Long agrId;  
	//public BIGINT AGR_ID;
    
    @Column(name = "DATETIME")
    private Date datetime;
	//public TIMESTAMP DATETIME;
    
    @Column(name = "TYPE")
    private String type;
	//public VARCHAR TYPE;
        
    @Column(name = "PTU_DURATION_MINS")
    private int ptuDurationMins;    
	//public INT PTU_DURATION_MINS;
    
    @Column(name = "NUMBER_PTUS")
    private int numberPTUs; 
	//public INT NUMBER_PTUS;
    
    @Column(name = "START_DATETIME")
    private Date startDatetime;
	//public TIMESTAMP START_DATETIME;
    
    @Column(name = "START_DATE")
    private Date startDate;
	//public DATE START_DATE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public int getPtuDurationMins() {
        return ptuDurationMins;
    }

    public void setPtuDurationMins(int ptuDurationMins) {
        this.ptuDurationMins = ptuDurationMins;
    }

    public int getNumberPTUs() {
        return numberPTUs;
    }

    public void setNumberPTUs(int numberPTUs) {
        this.numberPTUs = numberPTUs;
    }

    public Date getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(Date startDatetime) {
        this.startDatetime = startDatetime;
    }

    public Long getAgrId() {
        return agrId;
    }

    public void setAgrId(Long agrId) {
        this.agrId = agrId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Date endDatetime) {
        this.endDatetime = endDatetime;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    @Column(name = "END_DATETIME", nullable = true)
    private Date endDatetime;
	//public TIMESTAMP END_DATETIME;
    
    @Column(name = "END_DATE", nullable = true)
    private Date endDate;
	//public DATE END_DATE;

    public AgrConsumptionDm(){
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}