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
@Table(name = "CUR_ALG_PTU")
public class CurAlgPtu {  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
//	public BIGINT ID;
    @Column(name = "CUR_ALG_ID", nullable = false)
    private Long curAlgId;
//	public BIGINT CUR_ALG_ID;
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
    @Column(name = "PORTFOLIO_ENERGY")
    private float portfolioEnergy;
//	public FLOAT IMBALANCE_ENERGY;
    @Column(name = "PORTFOLIO_PAYMENT")
    private float portfolioPayment;

    public Long getCurAlgId() {
        return curAlgId;
    }

    public void setCurAlgId(Long curAlgId) {
        this.curAlgId = curAlgId;
    }

    public float getPortfolioEnergy() {
        return portfolioEnergy;
    }

    public void setPortfolioEnergy(float portfolioEnergy) {
        this.portfolioEnergy = portfolioEnergy;
    }

    public float getPortfolioPayment() {
        return portfolioPayment;
    }

    public void setPortfolioPayment(float portfolioPayment) {
        this.portfolioPayment = portfolioPayment;
    }

    public float getAgrEnergy() {
        return agrEnergy;
    }

    public void setAgrEnergy(float agrEnergy) {
        this.agrEnergy = agrEnergy;
    }

    public float getAgrPayment() {
        return agrPayment;
    }

    public void setAgrPayment(float agrPayment) {
        this.agrPayment = agrPayment;
    }

    public float getPortfolioRemainingCurtailment() {
        return portfolioRemainingCurtailment;
    }

    public void setPortfolioRemainingCurtailment(float portfolioRemainingCurtailment) {
        this.portfolioRemainingCurtailment = portfolioRemainingCurtailment;
    }
//	public FLOAT IMBALANCE_PAYMENT;
    @Column(name = "AGR_ENERGY")
    private float agrEnergy;
//	public FLOAT AGR_ENERGY;
    @Column(name = "AGR_PAYMENT")
    private float agrPayment;
//	public FLOAT AGR_PAYMENT;
    @Column(name = "CASH_FLOW")
    private float cashFlow;
//	public FLOAT CASH_FLOW;
    @Column(name = "PORTFOLIO_REMAINING_CURTAILMENT")
    private float portfolioRemainingCurtailment;
    
	public CurAlgPtu(){
	}

	public void finalize() throws Throwable {

	}

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

    public float getCashFlow() {
        return cashFlow;
    }

    public void setCashFlow(float cashFlow) {
        this.cashFlow = cashFlow;
    }

}