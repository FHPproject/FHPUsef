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

import energy.usef.brp.model.dataModelFHP.DerCurtailmentPtu;
import energy.usef.brp.model.dataModelFHP.DerProductionForecast;
import energy.usef.brp.model.dataModelFHP.DerProductionForecastPtu;
import energy.usef.core.repository.BaseRepository;

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
 * DerProductionForecastPtu Repository for BRP.
 */
@Stateless
public class DerProductionForecastPtuRepository extends BaseRepository<DerProductionForecastPtu> {

    /**
     * Create ProductionForecastPtu.
     * 
     * @param derProductionForecastId
     * @param startPtu
     * @param numPtus
     * @param startDateTime
     * @param endDateTime
     * @param activePower
     * @return created ProductionForecastPtu ID.
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public long create(long derProductionForecastId, int startPtu, int numPtus, 
            LocalDateTime startDateTime, LocalDateTime endDateTime, float activePower) {
        DerProductionForecastPtu derProductionForecastPtu = new DerProductionForecastPtu();
        derProductionForecastPtu.setDerProductionForecastId(derProductionForecastId);
        derProductionForecastPtu.setStartPtu(startPtu);
        derProductionForecastPtu.setNumberPtus(numPtus);
        derProductionForecastPtu.setActivePower(activePower);
        
        derProductionForecastPtu.setStartDate(startDateTime.toLocalDate().toDateTimeAtStartOfDay().toDate());      
        derProductionForecastPtu.setEndDate(endDateTime.toLocalDate().toDateTimeAtStartOfDay().toDate());      
        
        derProductionForecastPtu.setStartDatetime(startDateTime);
        derProductionForecastPtu.setEndDatetime(endDateTime);
        
        persist(derProductionForecastPtu);
        return derProductionForecastPtu.getId();
    }   
    
    /**
     * Return the DerProductionForecastPtu.
     * 
     * @param derId
     * @param ptuStartDateTime
     * @param ptuEndDateTime
     * @return DerProductionForecastPtu entities
     */
    public DerProductionForecastPtu get(long derId, LocalDateTime ptuStartDateTime,
            LocalDateTime ptuEndDateTime) {
        StringBuilder queryString = new StringBuilder("SELECT ptu FROM DerProductionForecast p, DerProductionForecastPtu ptu");
        queryString.append(" WHERE p.derId = :derId");
        queryString.append(" AND ptu.derProductionForecastId = p.id");
        queryString.append(" AND ptu.startDatetime <= :ptuStartDateTime");
        queryString.append(" AND ptu.endDatetime >= :ptuEndDateTime");
        List<DerProductionForecastPtu> result = entityManager.createQuery(queryString.toString(), DerProductionForecastPtu.class)
                .setParameter("derId", derId)
                .setParameter("ptuStartDateTime", ptuStartDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .setParameter("ptuEndDateTime", ptuEndDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);
    }
    
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)    
    public DerProductionForecastPtu fetch(long derId, long derProductionForecastId, 
            int startPtu, int numPtus, 
            LocalDateTime ptuStartDateTime, LocalDateTime ptuEndDateTime, 
            float activePower){
        // The same as get but in case there is no coincident register it creates it from stub data
        DerProductionForecastPtu derProductionForecastPtu = 
                get(derId, ptuStartDateTime, ptuEndDateTime);
        if ((derProductionForecastPtu == null) || (derProductionForecastPtu.getId() == 0))
        {
            create(derProductionForecastId, startPtu, numPtus, 
            ptuStartDateTime, ptuEndDateTime, activePower);
            derProductionForecastPtu = get(derId, ptuStartDateTime,
            ptuEndDateTime);                
        }          

        if(derProductionForecastPtu == null)
            return null;
                
        return derProductionForecastPtu;
    }           
}
