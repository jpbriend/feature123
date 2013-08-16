package fr.mudak.spring.feature.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/fr/mudak/spring/feature/test/testFeature1.xml" })
public class FeatureTest {

    @Autowired
    private MyBean myBean;

    @Test
    public void test() {
        myBean.methodToBeFeatured("1", "param2");

        myBean.methodToBeFeatured("2", "param2");


    }

    @Test
    public void testNoParameters() {
        myBean.methodToBeFeaturedWithoutParameters("DEFAULT", "toto");
    }
}
