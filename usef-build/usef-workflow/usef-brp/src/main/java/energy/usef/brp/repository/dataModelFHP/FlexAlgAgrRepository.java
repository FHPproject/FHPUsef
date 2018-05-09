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

import energy.usef.brp.model.dataModelFHP.CurAlgAgr;
import energy.usef.brp.model.dataModelFHP.FlexAlgAgr;
import energy.usef.core.repository.BaseRepository;
import energy.usef.core.util.DateTimeUtil;

import java.util.List;

import javax.ejb.Stateless;
import javax.transaction.Transactional;
import org.joda.time.LocalDateTime;

/**
 * @author TECNALIA
 */
/**
 * CurAlgDer Repository for BRP.
 */
@Stateless
public class FlexAlgAgrRepository extends BaseRepository<FlexAlgAgr> {

    
    /**
     * Create CurAlgDer.
     * 
     * @param flexAlgId
     * @param agrId
     * @param agrFlexOfferId
     * @return created CurAlgDer ID.
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public long create(long flexAlgId, long agrId, long agrFlexOfferId) {
        FlexAlgAgr flexAlgAgr = new FlexAlgAgr();
        LocalDateTime now = DateTimeUtil.getCurrentDateTime();
        flexAlgAgr.setFlexAlgId(flexAlgId);
        flexAlgAgr.setAgrId(agrId);
        flexAlgAgr.setAgrFlexOfferId(Long.valueOf(0));
        flexAlgAgr.setFlexPayment(0);
        persist(flexAlgAgr);
        return flexAlgAgr.getId();
    }
    
    /**
     * Return the PTU of the Curtailment Algorithm for a determined loop number, AGR and DER
     * and for a determined start and end step dates.
     * 
     * @param flexAlgId
     * @param flexAlgAgrId
     * @param agrId
     * @return CurAlgPtuAgrAgr entity
     */
    public FlexAlgAgr get(long flexAlgId,long flexAlgAgrId, long agrId) {
        StringBuilder queryString = new StringBuilder("SELECT apad FROM FlexAlgAgr apad ");
        queryString.append(" WHERE apad.agrId = :agrId");
        queryString.append(" WHERE apad.flexAlgAgrId = :flexAlgAgrId");
        queryString.append(" AND apad.flexAlgId = :flexAlgId");
       List<FlexAlgAgr> result = entityManager.createQuery(queryString.toString(), FlexAlgAgr.class)
                .setParameter("agrId", agrId)
                .setParameter("flexAlgAgrId", flexAlgAgrId)
                .setParameter("flexAlgId", flexAlgId)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);
    }  
    
    /**
     * Update PortfolioRemainingCurtailment.
     * 
     * @param flexAlgAgrId
     * @param flexPayment
     * @return 
     */
    public int updateAgrPayment(long flexAlgAgrId, float flexPayment) {
        StringBuilder queryString = new StringBuilder("UPDATE FelxAlgAgr");
        queryString.append(" SET  agrPayment = :agrPayment");
        queryString.append(" WHERE id = :flexAlgAgrId");
        return entityManager.createQuery(queryString.toString())
                .setParameter("flexPayment", flexPayment)
                .setParameter("flexAlgAgrId", flexAlgAgrId)
                .executeUpdate();
    }
    

}
