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
 * @created 20-oct-2017 12:02:35
 */
@Entity
@Table(name = "DER_PRODUCTION_DM_PTU")
public class DerProfitabilityThresholdPtu { 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
        //public BIGINT ID;
    
    @Column(name = "DER_PROFITABILITY_THRESHOLD_ID", nullable = false)
    private Long derProfitabilityThresholdId;
	//public BIGINT DER_CONSUMPTION_DM_ID;

    @Column(name = "START_DATE")
    private Date startDate;
    
    @Column(name = "END_DATE")
    private Date endDate;
    
	/**
	 * Number of the first PTU in this schedule step with regard to the schedule (the
	 * first one is 1)
	 */
    @Column(name = "START_PTU")
    private int startPtu;
	//public INT START_PTU;
	/**
	 * The number of the PTUs this element represents. Optional, default value is 1
	 */
    @Column(name = "NUMBER_PTUS")
    private int numberPTUs;
	//public INT NUMBER_PTUS;
    @Column(name = "START_DATETIME")
    private Date startDatetime;
	//public TIMESTAMP START_DATETIME;
    @Column(name = "END_DATETIME")
    private Date endDatetime;
        //public TIMESTAMP END_DATETIME;
    @Column(name = "PROFITABILITY_THRESHOLD")
    private float profitabilityThreshold;
	//public FLOAT ACTIVE_POWER;
    
    public DerProfitabilityThresholdPtu(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getStartPtu() {
        return startPtu;
    }

    public void setStartPtu(int startPtu) {
        this.startPtu = startPtu;
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

    public Date getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Date endDatetime) {
        this.endDatetime = endDatetime;
    }

    public float getProfitabilityThreshold() {
        return profitabilityThreshold;
    }

    public Long getDerProfitabilityThresholdId() {
        return derProfitabilityThresholdId;
    }

    public void setDerProfitabilityThresholdId(Long derProfitabilityThresholdId) {
        this.derProfitabilityThresholdId = derProfitabilityThresholdId;
    }

    public void setProfitabilityThreshold(float profitabilityThreshold) {
        this.profitabilityThreshold = profitabilityThreshold;
    }

    public void finalize() throws Throwable {
    }

}