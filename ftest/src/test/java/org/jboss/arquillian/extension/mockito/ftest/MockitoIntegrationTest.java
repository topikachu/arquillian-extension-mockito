package org.jboss.arquillian.extension.mockito.ftest;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.extension.mockito.ftest.BarResource;
import org.jboss.arquillian.extension.mockito.ftest.FooService;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.Resolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;

import static org.mockito.Mockito.*;

@RunWith(Arquillian.class)
public class MockitoIntegrationTest {
	@Deployment
	public static WebArchive createDeployment() {

		return ShrinkWrap
				.create(WebArchive.class, "ROOT.war")
				.addClass(BarResource.class)
				.addClass(FooService.class)
				.addAsLibraries(
						Resolvers
								.use(MavenResolverSystem.class)
								.loadPomFromFile("pom.xml", "tomcat-embedded-7")
								.resolve("org.jboss.weld.servlet:weld-servlet")
								.withTransitivity().asFile())
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.setWebXML("test-web.xml");
	}

	@Inject
	private FooService fooService;

	@Inject
	@Spy
	private BarResource barResource;

	@Before
	public void beforeTest() {
		reset(barResource);
	}

	@Test
	public void testFoo() {		
		when(barResource.getText()).thenReturn("hello world");
		fooService.execute();
		verify(barResource).getText();

	}

}
