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

import energy.usef.brp.model.dataModelFHP.DerProfitabilityThresholdPtu;
import energy.usef.core.repository.BaseRepository;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TemporalType;
import org.joda.time.LocalDateTime;

/**
 * @author TECNALIA
 */
/**
 * DerProfitabilityThresholdPtu Repository for BRP.
 */
@Stateless
public class DerProfitabilityThresholdPtuRepository extends BaseRepository<DerProfitabilityThresholdPtu> {

    /**
     * Return the DerProfitabilityThresholdPtu.
     * 
     * @param derId
     * @param ptuStartDateTime
     * @param ptuEndDateTime
     * @return DerProfitabilityThresholdPtu entities
     */
    public DerProfitabilityThresholdPtu get(long derId, LocalDateTime ptuStartDateTime,
            LocalDateTime ptuEndDateTime) {
        StringBuilder queryString = new StringBuilder("SELECT ptu FROM DerProfitabilityThreshold p, DerProfitabilityThresholdPtu ptu");
        queryString.append(" WHERE p.derId = :derId");
        queryString.append(" AND ptu.derProfitabilityThresholdId = p.id");
        queryString.append(" AND ptu.startDatetime <= :ptuStartDateTime");
        queryString.append(" AND ptu.endDatetime >= :ptuEndDateTime");
        List<DerProfitabilityThresholdPtu> result = entityManager.createQuery(queryString.toString(), DerProfitabilityThresholdPtu.class)
                .setParameter("derId", derId)
                .setParameter("ptuStartDateTime", ptuStartDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .setParameter("ptuEndDateTime", ptuEndDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
               .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);
    }
    
    /**
     * Return the DerProfitabilityThresholdPtu-s for a determined DerProfitabilityThreshold.
     * 
     * @param derProfitabilityThresholdId
     * @return DerProfitabilityThreshold entities
     */
    public List<DerProfitabilityThresholdPtu> get(long derProfitabilityThresholdId) {
        StringBuilder queryString = new StringBuilder("SELECT p FROM DerProfitabilityThresholdPtu p");
        queryString.append(" WHERE derProfitabilityThresholdId = :derProfitabilityThresholdId");
        queryString.append(" ORDER BY startDatetime ASC");
        List<DerProfitabilityThresholdPtu> result = entityManager.createQuery(queryString.toString(), DerProfitabilityThresholdPtu.class)
                .setParameter("derProfitabilityThresholdId", derProfitabilityThresholdId)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result;
    }
    
    
}
