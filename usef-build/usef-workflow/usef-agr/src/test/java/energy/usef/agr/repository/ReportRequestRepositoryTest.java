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

package energy.usef.agr.repository;

import static org.powermock.reflect.Whitebox.setInternalState;

import energy.usef.agr.model.ReportRequest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.joda.time.LocalDate;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportRequestRepositoryTest {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private ReportRequestRepository repository;

    @BeforeClass
    public static void initTestFixture() throws Exception {
        // Get the entity manager for the tests.
        entityManagerFactory = Persistence.createEntityManagerFactory("test");
        entityManager = entityManagerFactory.createEntityManager();
    }

    /**
     * Cleans up the session.
     */
    @AfterClass
    public static void closeTestFixture() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Before
    public void setUp() {
        repository = new ReportRequestRepository();
        setInternalState(repository, "entityManager", entityManager);

        // clear the entity manager to avoid unexpected results
        repository.getEntityManager().clear();
    }

    @Test
    public void testSimpleFind() {
        String id = "0f0cd5f8-9c49-442d-9cba-3ac4bffb1ace";
        ReportRequest reportRequest = repository.find(id);
        Assert.assertNotNull(reportRequest);
        Assert.assertEquals("0f0cd5f8-9c49-442d-9cba-3ac4bffb1ace", reportRequest.getId());
        Assert.assertEquals(new LocalDate(2015, 6, 11), reportRequest.getPeriod());
        Assert.assertNotNull(reportRequest.getDeviceMessage());
        Assert.assertEquals(4, reportRequest.getDtuIndexes().size());
        Assert.assertEquals(29, reportRequest.getDtuIndexes().get(0).intValue());
        Assert.assertEquals(30, reportRequest.getDtuIndexes().get(1).intValue());
        Assert.assertEquals(31, reportRequest.getDtuIndexes().get(2).intValue());
        Assert.assertEquals(32, reportRequest.getDtuIndexes().get(3).intValue());
    }
}
