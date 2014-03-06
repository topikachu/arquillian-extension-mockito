package org.jboss.arquillian.extension.mockito;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import static org.mockito.Mockito.*;

@RunWith(Arquillian.class)
public class MockitoIntegrationTest {
	@Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClass(BarResource.class)
            .addClass(FooService.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
	@Inject
	private FooService fooService;
	
	@Inject @Spy
	private BarResource barResource;
	
	
	@Before
	public void beforeTest(){
		
	}
	
	@Test
	public void testFoo() {
		reset(barResource);
	when(barResource.getText()).thenReturn("hello world");
		fooService.execute();
		
		verify(barResource).getText();
		
	}

}
