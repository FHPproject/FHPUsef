package energy.usef.brp.model.dataModelFHP;

import energy.usef.core.model.DocumentStatus;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.joda.time.LocalDateTime;


/**
 * @author 104903
 * @version 1.0
 * @created 20-oct-2017 12:01:55
 */
@Entity
@Table(name = "AGR_FLEX_ORDER")
public class AgrFlexOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;
//	public BIGINT ID;
    @Column(name = "AGR_ID", nullable = false)
    private Long agrId;
//	public BIGINT AGR_ID;
    @Column(name = "MESSAGE_ID", nullable = false)
    private String messageId;
//	public BIGINT MESSAGE_ID;
    @Column(name = "AGR_FLEX_OFFER_ID", nullable = false)
    private Long agrFlexOfferId;
//	public BIGINT AGR_FLEX_OFFER_ID;
    @Column(name = "AGR_FLEX_OFFER_SEQUENCE")
    private Long agrFlexOfferSequence;
//	public BIGINT AGR_FLEX_OFFER_SEQUENCE;
    @Column(name = "DATETIME")
    private Date datetime;
//	public TIMESTAMP DATETIME;
    @Column(name = "AGR_DOMAIN")
    private String agrDomain;
//	public VARCHAR AGR_DOMAIN;
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
    @Column(name = "STATUS")
    @Enumerated(value = EnumType.STRING)
    private DocumentStatus status;
//	public VARCHAR STATUS;
    @Column(name = "PTU_DURATION_MINS")
    private int ptuDurationMins;
//	public INT PTU_DURATION_MINS;
    @Column(name = "NUMBER_PTUS")
    private int numberPtus;
//	public INT NUMBER_PTUS;
    @Column(name = "CURRENCY")
    private String currency;
//	public VARCHAR CURRENCY;

    public AgrFlexOrder(){
    }

    public void finalize() throws Throwable {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAgrId() {
        return agrId;
    }

    public void setAgrId(Long agrId) {
        this.agrId = agrId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Long getAgrFlexOfferId() {
        return agrFlexOfferId;
    }

    public void setAgrFlexOfferId(Long agrFlexOfferId) {
        this.agrFlexOfferId = agrFlexOfferId;
    }

    public Long getAgrFlexOfferSequence() {
        return agrFlexOfferSequence;
    }

    public void setAgrFlexOfferSequence(Long agrFlexOfferSequence) {
        this.agrFlexOfferSequence = agrFlexOfferSequence;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getAgrDomain() {
        return agrDomain;
    }

    public void setAgrDomain(String agrDomain) {
        this.agrDomain = agrDomain;
    }

    public LocalDateTime getStartDatetime() {
        if (startDatetime == null) {
            return null;
        }
        return new LocalDateTime(startDatetime);
    } 

    public void setStartDatetime(LocalDateTime startDatetime) {
        if (startDatetime == null) {
            this.startDatetime = null;
        } else {
            this.startDatetime = startDatetime.toDateTime().toDate();
        }
    } 

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDatetime() {
        if (endDatetime == null) {
            return null;
        }
        return new LocalDateTime(endDatetime);
    }  

    /*public void setEndDatetime(Date endDatetime) {
        this.endDatetime = endDatetime;
    }*/
    
    public void setEndDatetime(LocalDateTime endDatetime) {
        if (endDatetime == null) {
            this.endDatetime = null;
        } else {
            this.endDatetime = endDatetime.toDateTime().toDate();
        }
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}