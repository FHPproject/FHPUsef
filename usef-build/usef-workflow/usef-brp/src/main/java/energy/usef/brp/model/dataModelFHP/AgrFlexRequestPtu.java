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
 * @created 20-oct-2017 12:02:08
 */
@Entity
@Table(name = "AGR_FLEX_REQUEST_PTU")
public class AgrFlexRequestPtu{  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
//	public BIGINT ID;
    @Column(name = "AGR_FLEX_REQUEST_ID", nullable = false)
    private Long agrFlexRequestId;
//	public BIGINT AGR_FLEX_REQUEST_ID;
//	/**
//	 * Number of the first PTU in this schedule step with regard to the schedule (the
//	 * first one is 1)
//	 */
    @Column(name = "START_PTU")
    private Long startPtu;
//	public INT START_PTU;
//	/**
//	 * The number of the PTUs this element represents. Optional, default value is 1
//	 */
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
    @Column(name = "DISPOSITION")
    private String disposition;
//	public VARCHAR DISPOSITION;

	public AgrFlexRequestPtu(){
	}

	public void finalize() throws Throwable {

	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAgrFlexRequestId() {
        return agrFlexRequestId;
    }

    public void setAgrFlexRequestId(Long agrFlexRequestId) {
            this.agrFlexRequestId = agrFlexRequestId;
    }

    public Long getStartPtu() {
        return startPtu;
    }

    public void setStartPtu(Long startPtu) {
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

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getActivePower() {
        return activePower;
    }

    public void setActivePower(float activePower) {
        this.activePower = activePower;
    }

    public String getDisposition() {
        return disposition;
    }

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

}