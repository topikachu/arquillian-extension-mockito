package org.jboss.arquillian.extension.mockito;

import org.jboss.arquillian.container.test.spi.client.deployment.CachedAuxilliaryArchiveAppender;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

public class MockitoArchiveAppender extends CachedAuxilliaryArchiveAppender {

	@Override
	protected Archive<?> buildArchive() {

		 JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "arquillian-mockito.jar")
                 .addPackages(
                       true, 
                       "org.mockito", 
                       "org.objenesis",
                       CDIExtension.class.getPackage().getName()
                      )
                 .addAsManifestResource(
                        new StringAsset(CDIExtension.class.getName()), 
                        "services/javax.enterprise.inject.spi.Extension");
return archive;
	}

	
}
