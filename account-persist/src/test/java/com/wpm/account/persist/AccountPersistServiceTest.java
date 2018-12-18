package com.wpm.account.persist;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class AccountPersistServiceTest {
    private AccountPersistService service;

    @Before
    public void prepare() throws Exception{
        File persistDataFile = new File("target/test-classes/persist-data.xml");
        if(persistDataFile.exists()){
            persistDataFile.delete();
        }

        ApplicationContext ctx = new ClassPathXmlApplicationContext("account-persist.xml");
        service = (AccountPersistService) ctx.getBean("accountPersistService");

        Account account = new Account();
        account.setId("pm");
        account.setName("wpm");
        account.setEmail("resolvewang@rookiefly.cn");
        account.setPassword("mypasswd");
        account.setActivated(true);

        service.createAccount(account);
    }

    @Test
    public void testReadAccount() throws Exception{
        Account account = service.readAccount("pm");

        assertNotNull(account);
        assertEquals("pm", account.getId());
        assertEquals("wpm", account.getName());
        assertEquals("resolvewang@rookiefly.cn", account.getEmail());
        assertEquals("mypasswd", account.getPassword());
        assertTrue(account.isActivated());
    }
}
