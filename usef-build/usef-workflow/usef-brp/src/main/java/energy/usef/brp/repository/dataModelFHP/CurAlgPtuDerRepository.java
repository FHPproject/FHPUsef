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

import energy.usef.brp.model.dataModelFHP.CurAlgPtuDer;
import energy.usef.brp.model.dataModelFHP.CurAlgPtuDer;
import energy.usef.brp.model.dataModelFHP.DerProductionType;
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
 * CurAlgPtuDer Repository for BRP.
 */
@Stateless
public class CurAlgPtuDerRepository extends BaseRepository<CurAlgPtuDer> {

    /**
     * Create CurAlgPtuDer.
     * 
     * @param curAlgId
     * @param startPtu
     * @param numPtus
     * @param startDateTime
     * @param endDateTime
     * @return created CurtailmentPtu ID.
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)    
    public long create(long curAlgDerId, long curAlgPtuId, long derId, float derRemainingCurtailmentPtu) {
        CurAlgPtuDer curAlgPtuDer = new CurAlgPtuDer();
        curAlgPtuDer.setCurAlgDerId(curAlgDerId);
        curAlgPtuDer.setCurAlgPtuId(curAlgPtuId);
        curAlgPtuDer.setDerId(derId);
        curAlgPtuDer.setDerRemainingCurtailment(derRemainingCurtailmentPtu);
        curAlgPtuDer.setDerEnergy(0);
        curAlgPtuDer.setDerPayment(derRemainingCurtailmentPtu);
        curAlgPtuDer.setAgrPayment(0);
        
        persist(curAlgPtuDer);
        return curAlgPtuDer.getId();
    }
}
