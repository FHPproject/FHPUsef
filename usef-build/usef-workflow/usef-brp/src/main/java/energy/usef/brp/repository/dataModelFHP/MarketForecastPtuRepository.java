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

import energy.usef.brp.model.dataModelFHP.MarketForecastPtu;
import energy.usef.core.repository.BaseRepository;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TemporalType;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * @author TECNALIA
 */
/**
 * MarketForecastPtu Repository for BRP.
 */
@Stateless
public class MarketForecastPtuRepository extends BaseRepository<MarketForecastPtu> {

    /**
     * Return the MarketForecastPtu.
     * 
     * @param marketForecastId
     * @param ptuStartDateTime
     * @param ptuEndDateTime
     * @return MarketForecastPtu entities
     */
    public MarketForecastPtu get(long marketForecastId, LocalDateTime ptuStartDateTime,
            LocalDateTime ptuEndDateTime) {
        StringBuilder queryString = new StringBuilder("SELECT ptu FROM MarketForecastPtu ptu");
        queryString.append(" WHERE ptu.marketForecastId = :marketForecastId");
        queryString.append(" AND ptu.startDatetime <= :ptuStartDateTime");
        queryString.append(" AND ptu.endDatetime >= :ptuEndDateTime");
        List<MarketForecastPtu> result = entityManager.createQuery(queryString.toString(), MarketForecastPtu.class)
                .setParameter("marketForecastId", marketForecastId)
                .setParameter("ptuStartDateTime", ptuStartDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .setParameter("ptuEndDateTime", ptuEndDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);
    }
}
