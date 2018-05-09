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

import energy.usef.brp.model.dataModelFHP.FlexAlgPtu;
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
 * FlexAlgPtu Repository for BRP.
 */
@Stateless
public class FlexAlgPtuRepository extends BaseRepository<FlexAlgPtu> {

    /**
     * Create FlexAlgPtu.
     * 
     * @param flexAlgId
     * @param startPtu
     * @param numPtus
     * @param startDateTime
     * @param endDateTime
     * @return created CurtailmentPtu ID.
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)    
    public long create(long flexAlgId, int startPtu, int numPtus, 
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        FlexAlgPtu flexAlgPtu = new FlexAlgPtu();
        flexAlgPtu.setFlexAlgId(flexAlgId);
        flexAlgPtu.setStartPtu(startPtu);
        flexAlgPtu.setNumberPtus(numPtus);
        LocalDate startDate = new LocalDate(startDateTime.getYear(), startDateTime.getMonthOfYear(), startDateTime.getDayOfMonth());
        flexAlgPtu.setStartDate(startDate.toDateMidnight().toDate());
        LocalDate endDate = new LocalDate(endDateTime.getYear(), endDateTime.getMonthOfYear(), endDateTime.getDayOfMonth());
        flexAlgPtu.setEndDate(endDate.toDateMidnight().toDate());
        flexAlgPtu.setStartDatetime(startDateTime);
        flexAlgPtu.setEndDatetime(endDateTime);
        flexAlgPtu.setImbalanceEnergy(0);
        flexAlgPtu.setImbalancePayment(0);
        flexAlgPtu.setFlexPayment(0);
        flexAlgPtu.setCashFlow(0);
        flexAlgPtu.setFlexEnergy(0);
 
        persist(flexAlgPtu);
        return flexAlgPtu.getId();
    }
    
    
    /**
     * Update PortfolioRemainingCurtailment.
     * 
     * @param flexAlgPtuId
     * @return 
     */
    public int updateCurtailment(long flexAlgPtuId) {
        StringBuilder queryString = new StringBuilder("UPDATE FlexAlgPtu");
        queryString.append(" WHERE id = :flexAlgPtuId");
        return entityManager.createQuery(queryString.toString())                
                .setParameter("flexAlgPtuId", flexAlgPtuId)
                .executeUpdate();
    }
    
    /**
     * Return the FlexAlgPtu for a determined start and end dates and a determined
     * loop number.
     * 
     * @param ptuStartDateTime
     * @param ptuEndDateTime
     * @return List of MeterDataCompany entities
     */
    public FlexAlgPtu get( LocalDateTime ptuStartDateTime, LocalDateTime ptuEndDateTime) {
        StringBuilder queryString = new StringBuilder("SELECT ap FROM FlexAlgPtu ap, ");
        queryString.append(" FlexAlg a");
        queryString.append(" WHERE ap.flexAlgId = a.id");
        queryString.append(" AND ap.startDatetime <= :ptuStartDateTime");
        queryString.append(" AND ap.endDatetime >= :ptuEndDateTime");
        queryString.append(" AND a.startDatetime <= :ptuStartDateTime");
        queryString.append(" AND a.endDatetime >= :ptuEndDateTime");
       List<FlexAlgPtu> result = entityManager.createQuery(queryString.toString(), FlexAlgPtu.class)
                .setParameter("ptuStartDateTime", ptuStartDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .setParameter("ptuEndDateTime", ptuEndDateTime.toDateTime().toDate(), TemporalType.TIMESTAMP)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);
    }
    

}
