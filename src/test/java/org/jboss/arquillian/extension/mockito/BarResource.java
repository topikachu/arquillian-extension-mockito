package org.jboss.arquillian.extension.mockito;

import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public class BarResource {
	public String getText(){
		return "hello";
	}
}
