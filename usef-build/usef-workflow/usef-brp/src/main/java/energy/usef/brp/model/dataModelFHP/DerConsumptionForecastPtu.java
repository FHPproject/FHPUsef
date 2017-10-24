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
 * @created 20-oct-2017 12:02:41
 */
@Entity
@Table(name = "DER_CONSUMPTION_FORECAST_PTU")
public class DerConsumptionForecastPtu { 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
	//public BIGINT ID;    
    @Column(name = "DER_CONSUMPTION_FORECAST_ID", nullable = false)
    private Long derConsumptionForecastId;  
	//public BIGINT DER_CONSUMPTION_FORECAST_ID;
    @Column(name = "DAY")
    private Date day;
	//public DATE DAY;
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
    private int numbrePtus;
	//public INT NUMBER_PTUS;
    @Column(name = "START_DATETIME")
    private Date startDatetime;
	//public TIMESTAMP START_DATETIME;
    @Column(name = "END_DATETIME")
    private Date endDatetime;
	//public TIMESTAMP END_DATETIME;
    @Column(name = "ACTIVE_POWER")
    private float activePower;
	//public FLOAT ACTIVE_POWER;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public int getStartPtu() {
        return startPtu;
    }

    public void setStartPtu(int startPtu) {
        this.startPtu = startPtu;
    }

    public int getNumbrePtus() {
        return numbrePtus;
    }

    public void setNumbrePtus(int numbrePtus) {
        this.numbrePtus = numbrePtus;
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

    public float getActivePower() {
        return activePower;
    }

    public void setActivePower(float activePower) {
        this.activePower = activePower;
    }

    public DerConsumptionForecastPtu(){
    }

    public void finalize() throws Throwable {
    }

    public Long getDerConsumptionForecastId() {
        return derConsumptionForecastId;
    }

    public void setDerConsumptionForecastId(Long derConsumptionForecastId) {
        this.derConsumptionForecastId = derConsumptionForecastId;
    }


}