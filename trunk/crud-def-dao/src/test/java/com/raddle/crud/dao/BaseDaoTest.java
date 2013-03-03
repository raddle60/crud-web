package com.raddle.crud.dao;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-dao-test.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional("crudTransactionManager")
public class BaseDaoTest {

}
