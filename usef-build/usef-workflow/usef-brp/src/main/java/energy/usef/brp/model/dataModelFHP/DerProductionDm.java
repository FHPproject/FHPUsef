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
 * @created 20-oct-2017 12:02:32
 */
@Entity
@Table(name = "DER_PRODUCTION_DM")
public class DerProductionDm {  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
	//public BIGINT ID;
    
    @Column(name = "DER_ID", nullable = false)
    private Long derId;
        //public BIGINT DER_ID;
    
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
    private int numberPtus;
	//public INT NUMBER_PTUS;
    
    @Column(name = "START_DATETIME")
    private Date startDatetime;
	//public TIMESTAMP START_DATETIME;
    
    @Column(name = "START_DATE")
    private Date startDate;
	//public DATE START_DATE;
    
    @Column(name = "END_DATETIME")
    private Date endDatetime;
	//public TIMESTAMP END_DATETIME;
    
    @Column(name = "END_DATE")
    private Date endDate;
	//public DATE END_DATE;

    public DerProductionDm(){
    }

    public void finalize() throws Throwable {
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPtuDurationMins() {
        return ptuDurationMins;
    }

    public void setPtuDurationMins(int ptuDurationMins) {
        this.ptuDurationMins = ptuDurationMins;
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

    public Long getDerId() {
        return derId;
    }

    public void setDerId(Long derId) {
        this.derId = derId;
    }
    
    

}