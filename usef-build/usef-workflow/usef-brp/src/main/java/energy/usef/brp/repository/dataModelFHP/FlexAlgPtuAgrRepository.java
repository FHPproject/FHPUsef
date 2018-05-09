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


import energy.usef.brp.model.dataModelFHP.FlexAlgPtuAgr;
//import energy.usef.brp.model.dataModelFHP.AgrProductionType;
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
 * CurAlgPtuAgr Repository for BRP.
 */
@Stateless
public class FlexAlgPtuAgrRepository extends BaseRepository<FlexAlgPtuAgr> {

    /**
     * Create FlexAlgPtuAgr.
     * 
     * @param flexAlgAgrId
     * @param flexAlgPtuId
     * @param agrId
     * @return created CurtailmentPtu ID.
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)    
    public long create(long flexAlgAgrId, long flexAlgPtuId, long agrId) {
        FlexAlgPtuAgr flexAlgPtuAgr = new FlexAlgPtuAgr();
        flexAlgPtuAgr.setFlexAlgAgrId(flexAlgAgrId);
        flexAlgPtuAgr.setFlexAlgPtuId(flexAlgPtuId);
        flexAlgPtuAgr.setAgrId(agrId);
        flexAlgPtuAgr.setFlexEnergy(0);
        flexAlgPtuAgr.setFlexPayment(0);
        
        persist(flexAlgPtuAgr);
        return flexAlgPtuAgr.getId();
    }
    
    
    
     /**
     * Return the PTU of the Curtailment Algorithm for a determined loop number, AGR and DER
     * and for a determined start and end step dates.
     * 
     * @param flexAlgAgrId
     * @param flexAlgPtuId
     * @return CurAlgPtuAgrAgr entity
     */
    public FlexAlgPtuAgr get(long flexAlgAgrId, long flexAlgPtuId) {
        StringBuilder queryString = new StringBuilder("SELECT apad FROM FlexAlgPtuAgr apad ");
        queryString.append(" WHERE apad.flexAlgPtuId = :flexAlgPtuId");
        queryString.append(" AND apad.flexAlgAgrId = :flexAlgAgrId");
       List<FlexAlgPtuAgr> result = entityManager.createQuery(queryString.toString(), FlexAlgPtuAgr.class)
                .setParameter("flexAlgPtuId", flexAlgPtuId)
                .setParameter("flexAlgAgrId", flexAlgAgrId)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);
    }
    
    /**
     * Update PortfolioRemainingCurtailment.
     * 
     * @param flexAlgPtuAgrId
     * @param flexEnergy
     * @param flexPayment
     * @return 
     */
    public int updateAgrEnergyPayment(long flexAlgPtuAgrId, float flexEnergy, float flexPayment) {
        StringBuilder queryString = new StringBuilder("UPDATE FlexAlgPtuPgr");
        queryString.append(" SET  flexPayment = :flexPayment");
        queryString.append(" SET  flexEnergy = :flexEnergy");
        queryString.append(" WHERE id = :flexAlgPtuAgrId");
        return entityManager.createQuery(queryString.toString())
                .setParameter("flexEnergy", flexEnergy)
                .setParameter("flexPayment", flexPayment)
                .setParameter("flexAlgPtuAgrId", flexAlgPtuAgrId)
                .executeUpdate();
    }
}
