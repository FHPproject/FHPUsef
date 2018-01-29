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

import energy.usef.brp.model.dataModelFHP.CurAlgDer;
import energy.usef.core.repository.BaseRepository;
import energy.usef.core.util.DateTimeUtil;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TemporalType;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * @author TECNALIA
 */
/**
 * CurAlgDer Repository for BRP.
 */
@Stateless
public class CurAlgDerRepository extends BaseRepository<CurAlgDer> {

    
    /**
     * Create CurAlgDer.
     * 
     * @param curAlgId
     * @param derId
     * @return created CurAlgDer ID.
     */
    public long create(long curAlgId, long derId) {
        CurAlgDer curAlgDer = new CurAlgDer();
        LocalDateTime now = DateTimeUtil.getCurrentDateTime();
        curAlgDer.setCurAlgId(curAlgId);
        curAlgDer.setDerId(derId);
        curAlgDer.setDerPayment(0);
       
        persist(curAlgDer);
        return curAlgDer.getId();
    }
    

}
