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

import energy.usef.brp.model.dataModelFHP.CurAlgPtu;
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
 * CurAlgPtu Repository for BRP.
 */
@Stateless
public class CurAlgPtuRepository extends BaseRepository<CurAlgPtu> {

    /**
     * Create CurAlgPtu.
     * 
     * @param curAlgId
     * @param startPtu
     * @param numPtus
     * @param startDateTime
     * @param endDateTime
     * @return created CurtailmentPtu ID.
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)    
    public long create(long curAlgId, int startPtu, int numPtus, 
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        CurAlgPtu curAlgPtu = new CurAlgPtu();
        curAlgPtu.setCurAlgId(curAlgId);
        curAlgPtu.setStartPtu(startPtu);
        curAlgPtu.setNumberPtus(numPtus);
        LocalDate startDate = new LocalDate(startDateTime.getYear(), startDateTime.getMonthOfYear(), startDateTime.getDayOfMonth());
        curAlgPtu.setStartDate(startDate.toDateMidnight().toDate());
        LocalDate endDate = new LocalDate(endDateTime.getYear(), endDateTime.getMonthOfYear(), endDateTime.getDayOfMonth());
        curAlgPtu.setEndDate(endDate.toDateMidnight().toDate());
        curAlgPtu.setStartDatetime(startDateTime);
        curAlgPtu.setEndDatetime(endDateTime);
        curAlgPtu.setPortfolioRemainingCurtailment(0);
        curAlgPtu.setAgrEnergy(0);
        curAlgPtu.setAgrPayment(0);
        curAlgPtu.setCashFlow(0);
        curAlgPtu.setPortfolioEnergy(0);
        curAlgPtu.setPortfolioPayment(0);
        
        persist(curAlgPtu);
        return curAlgPtu.getId();
    }
    
    
    /**
     * Update PortfolioRemainingCurtailment.
     * 
     * @param curAlgPtuId
     * @param portfolioRemainingCurtailment
     * @return 
     */
    public int updateCurtailment(long curAlgPtuId, float portfolioRemainingCurtailment) {
        StringBuilder queryString = new StringBuilder("UPDATE CurAlgPtu");
        queryString.append(" SET  portfolioRemainingCurtailment = :portfolioRemainingCurtailment");
        queryString.append(" WHERE id = :curAlgPtuId");
        return entityManager.createQuery(queryString.toString())
                .setParameter("portfolioRemainingCurtailment", portfolioRemainingCurtailment)
                .setParameter("curAlgPtuId", curAlgPtuId)
                .executeUpdate();
    }
    
    /**
     * Return the CurAlgPtu for a determined start and end dates and a determined
     * loop number.
     * 
     * @param curtailmentAlgLoopNumber
     * @param ptuStartDateTime
     * @param ptuEndDateTime
     * @return List of MeterDataCompany entities
     */
    public CurAlgPtu get(int curtailmentAlgLoopNumber,
            LocalDateTime ptuStartDateTime, LocalDateTime ptuEndDateTime) {
        StringBuilder queryString = new StringBuilder("SELECT ap FROM CurAlgPtu ap, ");
        queryString.append(" CurAlg a");
        queryString.append(" WHERE ap.curAlgId = a.id");
        queryString.append(" AND ap.startDatetime <= :ptuStartDateTime");
        queryString.append(" AND ap.endDatetime >= :ptuEndDateTime");
        queryString.append(" AND a.loop = :curtailmentAlgLoopNumber");
        queryString.append(" AND a.startDatetime <= :ptuStartDateTime");
        queryString.append(" AND a.endDatetime >= :ptuEndDateTime");
       List<CurAlgPtu> result = entityManager.createQuery(queryString.toString(), CurAlgPtu.class)
                .setParameter("curtailmentAlgLoopNumber", curtailmentAlgLoopNumber)
                .setParameter("ptuStartDateTime", ptuStartDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .setParameter("ptuEndDateTime", ptuEndDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);
    }
    

}
