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
import energy.usef.brp.model.dataModelFHP.DerCurtailmentType;
import energy.usef.brp.model.dataModelFHP.DerProductionForecast;
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
 * DerProfitabilityThreshold Repository for BRP.
 */
@Stateless
public class DerProductionForecastRepository extends BaseRepository<DerProductionForecast> {
    
    public DerProductionForecast get(long derId, LocalDateTime startDateTime,
        LocalDateTime endDateTime){
        StringBuilder queryString = new StringBuilder("SELECT c FROM DerProductionForecast c");
        queryString.append(" WHERE c.derId = :derId");        
        queryString.append(" AND c.startDatetime <= :startDateTime");
        queryString.append(" AND c.endDatetime >= :endDateTime");
        List<DerProductionForecast> result = entityManager.createQuery(queryString.toString(), DerProductionForecast.class)
                .setParameter("derId", derId)
                .setParameter("startDateTime", startDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .setParameter("endDateTime", endDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);
    }    

    /**
     * Return the DerProductionForecast for a determined date and DER.
     * 
     * @param derId
     * @param startDate
     * @return DerProductionForecast entities
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)    
    public DerProductionForecast fetch(long derId, LocalDateTime startDateTime,
        LocalDateTime endDateTime, Integer ptuDuration, int numberOfPtusPerDay){
        // The same as get but in case there is no coincident register it creates it from stub data
        DerProductionForecast derProductionForecast = 
                get(derId, startDateTime, endDateTime);
        if ((derProductionForecast == null) || (derProductionForecast.getId() == 0))
        {
            create(derId, startDateTime,
                endDateTime, ptuDuration, numberOfPtusPerDay);
            derProductionForecast = get(derId, startDateTime,
                                endDateTime);                
        }          

        if(derProductionForecast == null)
            return null;
                
        return derProductionForecast;
    }    
    
    
    /**
     * Create ProductionForecast.
     * 
     * @param derId
     * @param startDateTime
     * @param endDateTime
     * @param ptuDuration
     * @param numberOfPtusPerDay
     * @return created DerProductionForecast ID.
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public long create(long derId, LocalDateTime startDateTime,
        LocalDateTime endDateTime, Integer ptuDuration, int numberOfPtusPerDay) {
        
        DerProductionForecast derProductionForecast = new DerProductionForecast();
        derProductionForecast.setDerId(derId);
        derProductionForecast.setNumberPtus(numberOfPtusPerDay);
        derProductionForecast.setPtuDurationMins(ptuDuration);
        LocalDateTime now = DateTimeUtil.getCurrentDateTime();
        derProductionForecast.setDatetime(now.toDateTime().toDate());       
        derProductionForecast.setStartDate(startDateTime.toLocalDate().toDateTimeAtStartOfDay().toDate());        
        derProductionForecast.setEndDate(endDateTime.toLocalDate().toDateTimeAtStartOfDay().toDate());        
        derProductionForecast.setStartDatetime(startDateTime);
        derProductionForecast.setEndDatetime(endDateTime);
       
        persist(derProductionForecast);
        return derProductionForecast.getId();
    }    
}
