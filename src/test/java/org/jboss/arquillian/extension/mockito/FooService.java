package org.jboss.arquillian.extension.mockito;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public class FooService {

	@Inject
	private BarResource barResource;
	
	public void execute(){
		barResource.getText();
	}
	
}
