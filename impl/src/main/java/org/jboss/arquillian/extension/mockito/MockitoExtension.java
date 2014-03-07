package org.jboss.arquillian.extension.mockito;

import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.core.spi.LoadableExtension;

public class MockitoExtension implements LoadableExtension {

	@Override
	public void register(ExtensionBuilder builder) {
		builder.service(AuxiliaryArchiveAppender.class, MockitoArchiveAppender.class);
		
	}

}
