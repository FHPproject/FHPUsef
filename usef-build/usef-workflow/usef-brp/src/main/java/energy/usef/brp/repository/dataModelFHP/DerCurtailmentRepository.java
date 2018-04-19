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

import energy.usef.brp.model.dataModelFHP.DerCurtailment;
//import energy.usef.brp.model.dataModelFHP.DerCurtailmentPtu;
import energy.usef.brp.model.dataModelFHP.DerCurtailmentType;
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
 * DerCurtailment Repository for BRP.
 */
@Stateless
public class DerCurtailmentRepository extends BaseRepository<DerCurtailment> {

    /**
     * Create Curtailment.
     * 
     * @param derId
     * @param derProductionType
     * @param startDateTime
     * @param endDateTime
     * @param ptuDuration
     * @param numberOfPtusPerDay
     * @return created DerCurtailment ID.
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public long create(long derId, DerCurtailmentType derCurtailmentType, LocalDateTime startDateTime,
        LocalDateTime endDateTime, Integer ptuDuration, int numberOfPtusPerDay) {
        
        DerCurtailment derCurtailment = new DerCurtailment();
        derCurtailment.setDerId(derId);
        derCurtailment.setType(derCurtailmentType);
        derCurtailment.setNumberPtus(numberOfPtusPerDay);
        derCurtailment.setPtuDurationMins(ptuDuration);
        LocalDateTime now = DateTimeUtil.getCurrentDateTime();
        derCurtailment.setDatetime(now.toDateTime().toDate());
        LocalDate startDate = new LocalDate(startDateTime.getYear(), startDateTime.getMonthOfYear(), startDateTime.getDayOfMonth());
        derCurtailment.setStartDate(startDate.toDateMidnight().toDate());
        LocalDate endDate = new LocalDate(endDateTime.getYear(), endDateTime.getMonthOfYear(), endDateTime.getDayOfMonth());
        derCurtailment.setEndDate(endDate.toDateMidnight().toDate());
        derCurtailment.setStartDatetime(startDateTime);
        derCurtailment.setEndDatetime(endDateTime);
       
        persist(derCurtailment);
        return derCurtailment.getId();
    }
    
    public DerCurtailment get(long derId, DerCurtailmentType derCurtailmentType, LocalDateTime startDateTime,
        LocalDateTime endDateTime){
        StringBuilder queryString = new StringBuilder("SELECT c FROM DerCurtailment c");
        queryString.append(" WHERE c.type = :type");
        queryString.append(" AND c.derId = :derId");        
        queryString.append(" AND c.startDatetime <= :startDateTime");
        queryString.append(" AND c.endDatetime >= :endDateTime");
        List<DerCurtailment> result = entityManager.createQuery(queryString.toString(), DerCurtailment.class)
                .setParameter("type", derCurtailmentType)
                .setParameter("derId", derId)
                .setParameter("startDateTime", startDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .setParameter("endDateTime", endDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);
    } 
    
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)    
    public DerCurtailment fetch(long derId, DerCurtailmentType derCurtailmentType, LocalDateTime startDateTime,
        LocalDateTime endDateTime, Integer ptuDuration, int numberOfPtusPerDay){
        // The same as get but in case there is no coincident register it creates it from stub data
        DerCurtailment derCurtailment = get(derId, derCurtailmentType, startDateTime,
                    endDateTime);   
        if ((derCurtailment == null) || (derCurtailment.getId() == 0))            
        {       
            // If there is no register in the DER_CURTAILMENT table for that day, I create it
            create(derId, derCurtailmentType, startDateTime,
                    endDateTime, ptuDuration, numberOfPtusPerDay);  
            derCurtailment = get(derId, derCurtailmentType, startDateTime,
                                endDateTime);                
        }          

        if(derCurtailment == null)
            return null;
                
        return derCurtailment;
    } 
    
       
}
