package org.jboss.arquillian.extension.mockito;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.ProcessBean;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.enterprise.inject.spi.ProcessProducer;
import javax.enterprise.inject.spi.Producer;




import org.mockito.Mockito;
import org.mockito.Spy;

public class CDIExtension implements Extension {
	private Set<Type> spyedClasses = new HashSet<Type>();

	public <X> void processBean(@Observes ProcessBean<X> event) {
		for (InjectionPoint injectionPoint : event.getBean()
				.getInjectionPoints()) {
			Spy spy = injectionPoint.getAnnotated().getAnnotation(Spy.class);
			if (spy != null) {
				spyedClasses.add(injectionPoint.getAnnotated().getBaseType());
			}
		}

	}

	

	@SuppressWarnings("unchecked")
	public <T> void processInejctTarget(
			@Observes final ProcessInjectionTarget<T> event) {
		event.setInjectionTarget((InjectionTarget<T>) Proxy.newProxyInstance(
				CDIExtension.class.getClassLoader(),
				new Class[] { InjectionTarget.class },
				new ProducerInvocationHandler(event.getInjectionTarget())));
	}

	@SuppressWarnings("unchecked")
	public <T, X> void processProducer(
			@Observes final ProcessProducer<T, X> event) {

		event.setProducer((Producer<X>) Proxy.newProxyInstance(
				CDIExtension.class.getClassLoader(),
				new Class[] { Producer.class },
				new ProducerInvocationHandler(event.getProducer())));
	}
	
	class ProducerInvocationHandler implements InvocationHandler {
		private Producer<?> producer;

		public ProducerInvocationHandler(Producer<?> producer) {
			this.producer = producer;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			Object o = method.invoke(producer, args);
			if (method.getName().equals("produce")) {
				for (Type spyedClasses : CDIExtension.this.spyedClasses) {
					if (spyedClasses instanceof Class
							&& ((Class) spyedClasses).isInstance(o)) {
						o = Mockito.spy(o);
						break;
					}
				}

			}
			return o;
		}

	}
}
