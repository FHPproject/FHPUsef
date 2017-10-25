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
 * @created 20-oct-2017 12:00:57
 */
/**
 * Entity for the SYNCHRONISATION_CONNECTION Table.
 */
@Entity
@Table(name = "AGR_CONSUMPTION_FORECAST_PTU")
public class AgrConsumptionForecastPtu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
    //public BIGINT ID;
    @Column(name = "AGR_CONSUMPTION_FORECAST_ID", nullable = false)
    private Long agrConsumptionForecastId;
    //public BIGINT AGR_CONSUMPTION_FORECAST_ID;
	/**
	 * Number of the first PTU in this schedule step with regard to the schedule (the
	 * first one is 1)
	 */
    @Column(name = "START_PTU")
    private int startPtu;
    //  public INT START_PTU;
	/**
	 * The number of the PTUs this element represents. Optional, default value is 1
	 */
    @Column(name = "NUMBER_PTUS")
    private int numberPtus;
    //	public INT NUMBER_PTUS;
    @Column(name = "START_DATETIME")
    private Date startDatetime;
    //	public TIMESTAMP START_DATETIME;
    @Column(name = "START_DATE")
    private Date startDate;
    //	public DATE START_DATE;
    @Column(name = "END_DATETIME")
    private Date endDatetime;
    //	public TIMESTAMP END_DATETIME;
    @Column(name = "END_DATE")
    private Date endDate;
    //	public DATE END_DATE;
    @Column(name = "ACTIVE_POWER")
    private float activePower;
    //	public FLOAT ACTIVE_POWER;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStartPtu() {
        return startPtu;
    }

    public void setStartPtu(int startPtu) {
        this.startPtu = startPtu;
    }

    public int getNumberPtus() {
        return numberPtus;
    }

    public void setNumberPtus(int numberPtus) {
        this.numberPtus = numberPtus;
    }

    public Date getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(Date startDatetime) {
        this.startDatetime = startDatetime;
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

    public void setEndDate(Date EndDate) {
        this.endDate = EndDate;
    }

    public float getActivePower() {
        return activePower;
    }

    public void setActivePower(float activePower) {
        this.activePower = activePower;
    }

    public AgrConsumptionForecastPtu(){
	}

    public void finalize() throws Throwable {
	}

    public Long getAgrConsumptionForecastId() {
        return agrConsumptionForecastId;
    }

    public void setAgrConsumptionForecastId(Long agrConsumptionForecastId) {
        this.agrConsumptionForecastId = agrConsumptionForecastId;
    }

}