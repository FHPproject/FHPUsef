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
 * @created 20-oct-2017 12:02:57
 */
@Entity
@Table(name = "FLEX_ALG_PTU")
public class FlexAlgPtu {  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
//	public BIGINT ID;
    @Column(name = "FLEX_ALG_ID", nullable = false)
    private Long flexAlgId;
//	public BIGINT FLEX_ALG_ID;
    @Column(name = "DAY")
    private Date day;
//	public DATE DAY;
//	/**
//	 * Number of the first PTU in this schedule step with regard to the schedule (the
//	 * first one is 1)
//	 */
    @Column(name = "START_PTU")
    private int startPtu;
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
    @Column(name = "END_DATETIME")
    private Date endDatetime;
//	public TIMESTAMP END_DATETIME;
    @Column(name = "IMBALANCE_ENERGY")
    private float imbalanceEnergy;
//	public FLOAT IMBALANCE_ENERGY;
    @Column(name = "IMBALANCE_PAYMENT")
    private float imbalancePayment;
//	public FLOAT IMBALANCE_PAYMENT;
    @Column(name = "FLEX_ENERGY")
    private float flexEnergy;
//	public FLOAT FLEX_ENERGY;
    @Column(name = "FLEX_PAYMENT")
    private float flexPayment;
//	public FLOAT FLEX_PAYMENT;
    @Column(name = "CASH_FLOW")
    private float cashFlow;
//	public FLOAT CASH_FLOW;

	public FlexAlgPtu(){
	}

	public void finalize() throws Throwable {

	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFlexAlgId() {
        return flexAlgId;
    }

    public void setFlexAlgId(Long flexAlgId) {
        this.flexAlgId = flexAlgId;
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

    public Date getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Date endDatetime) {
        this.endDatetime = endDatetime;
    }

    public float getImbalanceEnergy() {
        return imbalanceEnergy;
    }

    public void setImbalanceEnergy(float imbalanceEnergy) {
        this.imbalanceEnergy = imbalanceEnergy;
    }

    public float getImbalancePayment() {
        return imbalancePayment;
    }

    public void setImbalancePayment(float imbalancePayment) {
        this.imbalancePayment = imbalancePayment;
    }

    public float getFlexEnergy() {
        return flexEnergy;
    }

    public void setFlexEnergy(float flexEnergy) {
        this.flexEnergy = flexEnergy;
    }

    public float getFlexPayment() {
        return flexPayment;
    }

    public void setFlexPayment(float flexPayment) {
        this.flexPayment = flexPayment;
    }

    public float getCashFlow() {
        return cashFlow;
    }

    public void setCashFlow(float cashFlow) {
        this.cashFlow = cashFlow;
    }

}