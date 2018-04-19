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

import energy.usef.brp.model.dataModelFHP.AgrFlexRequest;
import energy.usef.core.model.DocumentStatus;
import energy.usef.core.repository.BaseRepository;
import energy.usef.core.util.DateTimeUtil;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * @author TECNALIA
 */
/**
 * AgrFlexRequest Repository for BRP.
 */
@Stateless
public class AgrFlexRequestRepository extends BaseRepository<AgrFlexRequest> {

    /**
     * Create AgrFlexRequest.
     * 
     * @param agrId
     * @param agrDomain
     * @param startDateTime
     * @param endDateTime
     * @param ptuDuration
     * @param numberOfPtusPerDay
     * @param prognosisSequence
     * @param PrognosisId
     * @param messageId
     * @return created AgrFlexRequest ID.
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)    
    public long create(Long agrId, String agrDomain, LocalDateTime startDateTime,
        LocalDateTime endDateTime, Integer ptuDuration, int numberOfPtusPerDay,
        Long prognosisSequence, Long PrognosisId, String messageId) {
        
        AgrFlexRequest agrFlexRequest = new AgrFlexRequest();
        agrFlexRequest.setAgrDomain(agrDomain);
        agrFlexRequest.setAgrId(agrId);
        LocalDateTime now = DateTimeUtil.getCurrentDateTime();
        agrFlexRequest.setDatetime(now.toDateTime().toDate());
        agrFlexRequest.setNumberPtus(numberOfPtusPerDay);
        agrFlexRequest.setPtuDurationMins(ptuDuration);
        LocalDate startDate = new LocalDate(startDateTime.getYear(), startDateTime.getMonthOfYear(), startDateTime.getDayOfMonth());
        agrFlexRequest.setStartDate(startDate.toDateMidnight().toDate());
        LocalDate endDate = new LocalDate(endDateTime.getYear(), endDateTime.getMonthOfYear(), endDateTime.getDayOfMonth());
        agrFlexRequest.setEndDate(endDate.toDateMidnight().toDate());
        agrFlexRequest.setStartDatetime(startDateTime);
        agrFlexRequest.setEndDatetime(endDateTime);
        agrFlexRequest.setMessageID(messageId);
        agrFlexRequest.setPrognosisId(PrognosisId);
        agrFlexRequest.setPrognosisSequence(prognosisSequence);
        agrFlexRequest.setStatus(DocumentStatus.SENT);
        
        persist(agrFlexRequest);
        return agrFlexRequest.getId();
    }

    /**
     * Update AgrFlexRequest status.
     * 
     * @param agrDomain
     * @param messageId
     * @param period
     * @param status
     * @return 
     */
    public int updateStatus(String agrDomain, String messageId, LocalDate period, DocumentStatus status) {
        StringBuilder queryString = new StringBuilder("UPDATE AgrFlexRequest");
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
    
    /**
     * Return an AgrFlexRequest
     * 
     * @param agrId
     * @param period
     * @return AgrFlexRequest entity
     */
    public AgrFlexRequest get(long agrId, LocalDate period) {
        StringBuilder queryString = new StringBuilder("SELECT r FROM AgrFlexRequest r, ");
        queryString.append(" Message m");
        queryString.append(" WHERE r.agrId = :agrId");
        queryString.append(" AND r.startDate = :period");
        queryString.append(" ORDER BY datetime DESC");
         List<AgrFlexRequest> result = entityManager.createQuery(queryString.toString(), AgrFlexRequest.class)
                .setParameter("agrId", agrId)
                .setParameter("startDate", period.toDateMidnight().toDate(), TemporalType.DATE)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);        
    }}
