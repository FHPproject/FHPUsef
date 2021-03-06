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

import energy.usef.brp.model.dataModelFHP.CurAlg;
import energy.usef.brp.model.dataModelFHP.MarketType;
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
 * CurAlg Repository for BRP.
 */
@Stateless
public class CurAlgRepository extends BaseRepository<CurAlg> {

    /**
     * Return the last step of the Curtailment Algorithm for a determined date.
     * 
     * @param startDate
     * @return List of MeterDataCompany entities
     */
    public CurAlg getLastCurAlgForDate(LocalDate startDate, MarketType type) {
        StringBuilder queryString = new StringBuilder("SELECT c FROM CurAlg c");
        queryString.append(" WHERE startDate = :startDate");
        queryString.append(" AND type = :type");        
        queryString.append(" ORDER BY loop DESC");
        List<CurAlg> result = entityManager.createQuery(queryString.toString(), CurAlg.class)
                .setParameter("startDate", startDate.toDateMidnight().toDate(), TemporalType.DATE)
                .setParameter("type", type)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);
    }
    
    /**
     * Create CurAlg.
     * 
     * @param curtailmentAlgLoopNumber
     * @param startDateTime
     * @param endDateTime
     * @param ptuDuration
     * @param numberOfPtusPerDay
     * @return created CurAlg ID.
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public long create(int curtailmentAlgLoopNumber, LocalDateTime startDateTime,
        LocalDateTime endDateTime, Integer ptuDuration, int numberOfPtusPerDay, MarketType type) {
        CurAlg curAlg = new CurAlg();

        LocalDateTime now = DateTimeUtil.getCurrentDateTime();
        curAlg.setDatetime(now.toDateTime().toDate());
        curAlg.setLoop(curtailmentAlgLoopNumber);
        curAlg.setNumberPtus(numberOfPtusPerDay);
        curAlg.setPtuDurationMins(ptuDuration);
        LocalDate startDate = new LocalDate(startDateTime.getYear(), startDateTime.getMonthOfYear(), startDateTime.getDayOfMonth());
        curAlg.setStartDate(startDate.toDateMidnight().toDate());
        LocalDate endDate = new LocalDate(endDateTime.getYear(), endDateTime.getMonthOfYear(), endDateTime.getDayOfMonth());
        curAlg.setEndDate(endDate.toDateMidnight().toDate());
        curAlg.setStartDatetime(startDateTime);
        curAlg.setEndDatetime(endDateTime);
        curAlg.setPortfolioPayment(0);
        curAlg.setAgrPayment(0);
        curAlg.setCashFlow(0);
        curAlg.setType(type);
       
        persist(curAlg);
        return curAlg.getId();
    }
    

}
