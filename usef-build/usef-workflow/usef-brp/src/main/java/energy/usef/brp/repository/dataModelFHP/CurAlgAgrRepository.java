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
public class CurAlgAgrRepository extends BaseRepository<CurAlgAgr> {

    
    /**
     * Create CurAlgDer.
     * 
     * @param curAlgId
     * @param agrId
     * @param agrFlexRequestId
     * @return created CurAlgDer ID.
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public long create(long curAlgId, long agrId, long agrFlexRequestId) {
        CurAlgAgr curAlgAgr = new CurAlgAgr();
        LocalDateTime now = DateTimeUtil.getCurrentDateTime();
        curAlgAgr.setCurAlgId(curAlgId);
        curAlgAgr.setAgrId(agrId);
        curAlgAgr.setAgrPayment(0);
        curAlgAgr.setAgrFlexOfferId(Long.valueOf(0));
        curAlgAgr.setAgrFlexRequestId(agrFlexRequestId);
        persist(curAlgAgr);
        return curAlgAgr.getId();
    }
    
    /**
     * Return the PTU of the Curtailment Algorithm for a determined loop number, AGR and DER
     * and for a determined start and end step dates.
     * 
     * @param curAlgId
     * @param agrId
     * @return CurAlgPtuAgrAgr entity
     */
    public CurAlgAgr get(long curAlgId, long agrId) {
        StringBuilder queryString = new StringBuilder("SELECT apad FROM CurAlgAgr apad ");
        queryString.append(" WHERE apad.agrId = :agrId");
        queryString.append(" AND apad.curAlgId = :curAlgId");
       List<CurAlgAgr> result = entityManager.createQuery(queryString.toString(), CurAlgAgr.class)
                .setParameter("agrId", agrId)
                .setParameter("curAlgId", curAlgId)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);
    }  
    
    /**
     * Update PortfolioRemainingCurtailment.
     * 
     * @param curAlgAgrId
     * @param agrPayment
     * @return 
     */
    public int updateAgrPayment(long curAlgAgrId, float agrPayment) {
        StringBuilder queryString = new StringBuilder("UPDATE CurAlgAgr");
        queryString.append(" SET  agrPayment = :agrPayment");
        queryString.append(" WHERE id = :curAlgAgrId");
        return entityManager.createQuery(queryString.toString())
                .setParameter("agrPayment", agrPayment)
                .setParameter("curAlgAgrId", curAlgAgrId)
                .executeUpdate();
    }
    

}
