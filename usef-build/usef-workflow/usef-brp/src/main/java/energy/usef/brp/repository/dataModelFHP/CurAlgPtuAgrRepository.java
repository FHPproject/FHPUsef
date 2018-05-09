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

import energy.usef.brp.model.dataModelFHP.CurAlgPtuAgr;
//import energy.usef.brp.model.dataModelFHP.AgrProductionType;
import energy.usef.core.repository.BaseRepository;
import java.util.List;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

/**
 * @author TECNALIA
 */
/**
 * CurAlgPtuAgr Repository for BRP.
 */
@Stateless
public class CurAlgPtuAgrRepository extends BaseRepository<CurAlgPtuAgr> {

    /**
     * Create CurAlgPtuAgr.
     * 
     * @param curAlgAgrId
     * @param curAlgPtuId
     * @param agrId
     * @return created CurtailmentPtu ID.
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)    
    public long create(long curAlgAgrId, long curAlgPtuId, long agrId) {
        CurAlgPtuAgr curAlgPtuAgr = new CurAlgPtuAgr();
        curAlgPtuAgr.setCurAlgAgrId(curAlgAgrId);
        curAlgPtuAgr.setCurAlgPtuId(curAlgPtuId);
        curAlgPtuAgr.setAgrId(agrId);
        curAlgPtuAgr.setAgrEnergy(0);
        curAlgPtuAgr.setAgrPayment(0);
        
        persist(curAlgPtuAgr);
        return curAlgPtuAgr.getId();
    }
    
    
    
     /**
     * Return the PTU of the Curtailment Algorithm for a determined loop number, AGR and DER
     * and for a determined start and end step dates.
     * 
     * @param curAlgAgrId
     * @param curAlgPtuId
     * @return CurAlgPtuAgrAgr entity
     */
    public CurAlgPtuAgr get(long curAlgAgrId, long curAlgPtuId) {
        StringBuilder queryString = new StringBuilder("SELECT apad FROM CurAlgPtuAgr apad ");
        queryString.append(" WHERE apad.curAlgPtuId = :curAlgPtuId");
        queryString.append(" AND apad.curAlgAgrId = :curAlgAgrId");
       List<CurAlgPtuAgr> result = entityManager.createQuery(queryString.toString(), CurAlgPtuAgr.class)
                .setParameter("curAlgPtuId", curAlgPtuId)
                .setParameter("curAlgAgrId", curAlgAgrId)
                .getResultList();
        if(result == null || result.isEmpty())
            return null;
                
        return result.get(0);
    }
    
    /**
     * Update PortfolioRemainingCurtailment.
     * 
     * @param curAlgPtuAgrId
     * @param agrEnergy
     * @param agrPayment
     * @return 
     */
    public int updateAgrEnergyPayment(long curAlgPtuAgrId, float agrEnergy, float agrPayment) {
        StringBuilder queryString = new StringBuilder("UPDATE CurAlgPtuPgr");
        queryString.append(" SET  agrPayment = :agrPayment");
        queryString.append(" SET  agrEnergy = :agrEnergy");
        queryString.append(" WHERE id = :curAlgPtuAgrId");
        return entityManager.createQuery(queryString.toString())
                .setParameter("agrPayment", agrPayment)
                .setParameter("agrEnergy", agrEnergy)
                .setParameter("curAlgPtuAgrId", curAlgPtuAgrId)
                .executeUpdate();
    }
}
