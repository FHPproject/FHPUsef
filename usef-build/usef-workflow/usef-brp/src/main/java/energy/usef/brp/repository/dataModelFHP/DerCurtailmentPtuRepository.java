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
import energy.usef.brp.model.dataModelFHP.DerCurtailmentType;
import energy.usef.core.repository.BaseRepository;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;
import org.joda.time.LocalDateTime;

/**
 * @author TECNALIA
 */
/**
 * DerCurtailmentPtu Repository for BRP.
 */
@Stateless
public class DerCurtailmentPtuRepository extends BaseRepository<DerCurtailmentPtu> {

    /**
     * Create CurtailmentPtu.
     * 
     * @param derCurtailmentId
     * @param startPtu
     * @param numPtus
     * @param startDateTime
     * @param endDateTime
     * @param activePower
     * @return created CurtailmentPtu ID.
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public long create(long derCurtailmentId, int startPtu, int numPtus, 
            LocalDateTime startDateTime, LocalDateTime endDateTime, float activePower) {
        DerCurtailmentPtu derCurtailmentPtu = new DerCurtailmentPtu();
        derCurtailmentPtu.setDerCurtailmentId(derCurtailmentId);
        derCurtailmentPtu.setStartPtu(startPtu);
        derCurtailmentPtu.setNumberPTUs(numPtus);
        derCurtailmentPtu.setActivePower(activePower);

        derCurtailmentPtu.setStartDate(startDateTime.toLocalDate().toDateTimeAtStartOfDay().toDate());      
        derCurtailmentPtu.setEndDate(endDateTime.toLocalDate().toDateTimeAtStartOfDay().toDate()); 
        
        derCurtailmentPtu.setStartDatetime(startDateTime);
        derCurtailmentPtu.setEndDatetime(endDateTime);
        
        persist(derCurtailmentPtu);
        return derCurtailmentPtu.getId();
    }

    /**
     * Return the DerCurtailmentPtu.
     * 
     * @param derId
     * @param ptuStartDateTime
     * @param ptuEndDateTime
     * @param type
     * @return DerCurtailmentPtu entities
     */
    public DerCurtailmentPtu get(long derId, LocalDateTime ptuStartDateTime,
            LocalDateTime ptuEndDateTime, DerCurtailmentType type) {
        StringBuilder queryString = new StringBuilder("SELECT ptu FROM DerCurtailment c, DerCurtailmentPtu ptu");
        queryString.append(" WHERE c.derId = :derId");
        queryString.append(" AND c.type = :type");
        queryString.append(" AND ptu.derCurtailmentId = c.id");
        queryString.append(" AND ptu.startDatetime <= :ptuStartDateTime");
        queryString.append(" AND ptu.endDatetime >= :ptuEndDateTime");
        List<DerCurtailmentPtu> result = entityManager.createQuery(queryString.toString(), DerCurtailmentPtu.class)
                .setParameter("derId", derId)
                .setParameter("type", type)
                .setParameter("ptuStartDateTime", ptuStartDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .setParameter("ptuEndDateTime", ptuEndDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);
    }


    @Transactional(value = Transactional.TxType.REQUIRES_NEW)    
    public DerCurtailmentPtu fetch(long derId, long derCurtailmentId, 
            int startPtu, int numPtus, 
            LocalDateTime ptuStartDateTime, LocalDateTime ptuEndDateTime, 
            float activePower, DerCurtailmentType type){
        // The same as get but in case there is no coincident register it creates it from stub data
        DerCurtailmentPtu derCurtailmentPtu = 
                get(derId, ptuStartDateTime, ptuEndDateTime, type);
        if ((derCurtailmentPtu == null) || (derCurtailmentPtu.getId() == 0))
        {
            create(derCurtailmentId, startPtu, numPtus, 
            ptuStartDateTime, ptuEndDateTime, activePower);
            derCurtailmentPtu = get(derId, ptuStartDateTime,
            ptuEndDateTime, type);                
        }          

        if(derCurtailmentPtu == null)
            return null;
                
        return derCurtailmentPtu;
    } 
}
