package org.jboss.arquillian.extension.mockito.ftest;

import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public class BarResource {
	public String getText(){
		return "hello";
	}
}
