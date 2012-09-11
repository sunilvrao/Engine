package collabrite.test.appliance.config.spring;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import collabrite.appliance.Appliance;

public class ApplianceSpringConfigTestCase {

    @Test
    public void testAppliance() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("appliance/config/spring/appliance.xml");
        BeanFactory factory = context;
        Appliance appliance = (Appliance) factory.getBean("appliance");
        assertNotNull(appliance);

        appliance.setUp();
        appliance.execute();
        appliance.tearDown();
    }
}