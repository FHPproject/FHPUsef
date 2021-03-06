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

import energy.usef.brp.model.dataModelFHP.DerProfitabilityThreshold;
import energy.usef.core.repository.BaseRepository;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TemporalType;
import org.joda.time.LocalDate;

/**
 * @author TECNALIA
 */
/**
 * DerProfitabilityThreshold Repository for BRP.
 */
@Stateless
public class DerProfitabilityThresholdRepository extends BaseRepository<DerProfitabilityThreshold> {

    /**
     * Return the DerProfitabilityThreshold for a determined date and DER.
     * 
     * @param derId
     * @param startDate
     * @return DerProfitabilityThreshold entity
     */
    public DerProfitabilityThreshold get(long derId, LocalDate startDate) {
        StringBuilder queryString = new StringBuilder("SELECT p FROM DerProfitabilityThreshold p");
        queryString.append(" WHERE startDate = :startDate");
        queryString.append(" AND derId = :derId");
        queryString.append(" ORDER BY datetime DESC");
        List<DerProfitabilityThreshold> result = entityManager.createQuery(queryString.toString(), DerProfitabilityThreshold.class)
                .setParameter("startDate", startDate.toDateMidnight().toDate(), TemporalType.DATE)
                .setParameter("derId", derId)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);
    }
}
