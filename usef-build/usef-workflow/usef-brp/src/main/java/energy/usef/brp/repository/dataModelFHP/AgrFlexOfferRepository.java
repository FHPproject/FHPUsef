/*
 * Copyright 2015-2016 USEF Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package energy.usef.brp.repository.dataModelFHP;

import energy.usef.brp.model.dataModelFHP.AgrFlexOffer;
import energy.usef.core.model.DocumentStatus;
import energy.usef.core.repository.BaseRepository;
import energy.usef.core.util.DateTimeUtil;

import javax.ejb.Stateless;
import javax.persistence.TemporalType;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * @author TECNALIA
 */
/**
 * AgrFlexOffer Repository for BRP.
 */
@Stateless
public class AgrFlexOfferRepository extends BaseRepository<AgrFlexOffer> {

    /**
     * Create AgrFlexOffer.
     * 
     * @param agrId
     * @param agrDomain
     * @param ptuDuration
     * @param numberOfPtusPerDay
     * @param agrFlexRequestSequence
     * @param agrFlexRequestId
     * @param messageId
     * @param currency
     * @param status
     * @return created AgrFlexOffer ID.
     */
    public long create(Long agrId, String agrDomain, Integer ptuDuration, int numberOfPtusPerDay,
        Long agrFlexRequestSequence, Long agrFlexRequestId, String messageId, String currency,
        DocumentStatus status) {
        
        AgrFlexOffer agrFlexOffer = new AgrFlexOffer();
        agrFlexOffer.setAgrDomain(agrDomain);
        agrFlexOffer.setAgrId(agrId);
        LocalDateTime now = DateTimeUtil.getCurrentDateTime();
        agrFlexOffer.setDatetime(now.toDateTime().toDate());
        agrFlexOffer.setNumberPtus(numberOfPtusPerDay);
        agrFlexOffer.setPtuDurationMins(ptuDuration);
        agrFlexOffer.setMessageID(messageId);
        agrFlexOffer.setAgrFlexRequestId(agrFlexRequestId);
        agrFlexOffer.setAgrFlexRequestSequence(agrFlexRequestSequence);
        agrFlexOffer.setCurrency(currency);
        agrFlexOffer.setStatus(status);
        
        persist(agrFlexOffer);
        return agrFlexOffer.getId();
    }

    /**
     * Update AgrFlexOffer dates.
     * 
     * @param agrFlexOfferId
     * @param startDateTime
     * @param endDateTime
     * @return 
     */
    public int updateDates(long agrFlexOfferId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LocalDate startDate = new LocalDate(startDateTime.getYear(), startDateTime.getMonthOfYear(), startDateTime.getDayOfMonth());
        LocalDate endDate = new LocalDate(startDateTime.getYear(), startDateTime.getMonthOfYear(), startDateTime.getDayOfMonth());
        StringBuilder queryString = new StringBuilder("UPDATE AgrFlexOffer");
        queryString.append(" SET startDatetime = :startDateTime,");
        queryString.append("  endDatetime = :endDateTime,");
        queryString.append("  startDate = :startDate,");
        queryString.append("  endDate = :endDate");
        queryString.append(" WHERE id = :agrFlexOfferId");
         return entityManager.createQuery(queryString.toString())
                .setParameter("agrFlexOfferId", agrFlexOfferId)
                .setParameter("startDateTime", startDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .setParameter("endDateTime", endDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .setParameter("startDate", startDate.toDateMidnight().toDate(), TemporalType.DATE)
                .setParameter("endDate", endDate.toDateMidnight().toDate(), TemporalType.DATE)
                .executeUpdate();
    }

    /**
     * Update AgrFlexOffer status.
     * 
     * @param agrDomain
     * @param messageId
     * @param period
     * @param status
     * @return 
     */
    public int updateStatus(String agrDomain, String messageId, LocalDate period, DocumentStatus status) {
        StringBuilder queryString = new StringBuilder("UPDATE AgrFlexOffer");
        queryString.append(" SET  status = :status");
        queryString.append(" WHERE agrDomain = :agrDomain");
        queryString.append(" AND messageId = :messageId");
        queryString.append(" AND startDate = :startDate");
        return entityManager.createQuery(queryString.toString())
                .setParameter("agrDomain", agrDomain)
                .setParameter("messageId", messageId)
                .setParameter("startDate", period.toDateMidnight().toDate(), TemporalType.DATE)
                .setParameter("status", status)
                .executeUpdate();
    }
}
