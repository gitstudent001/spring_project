package com.worldsnack.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringConfigClass extends AbstractAnnotationConfigDispatcherServletInitializer{
	
	// DispatcherServlet 에 Mapping 할 request url (요청주소) 을 설정함
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	// Spring MVC Project 설정을 하는 클래스를 지정함
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {ServletAppContext.class};
	}
	
	// Project 에서 사용하는 Bean class 들을 정의하는 클래스를 지정함
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {RootAppContext.class};
	}
	
	// Parameter Encoding 설정 (한글 처리)
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		return new Filter[] {encodingFilter};
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		super.customizeRegistration(registration);
		
		MultipartConfigElement config1 
		  = new MultipartConfigElement(null, 52428800, 52428800, 0);
		registration.setMultipartConfig(config1);
	}
	
	
}



/*
public class SpringConfigClass implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
	  
		// Spring MVC 프로젝트에 관련된 설정을 하는 클래스의 객체 생성하기
		AnnotationConfigWebApplicationContext servletAppContext = 
				                        new AnnotationConfigWebApplicationContext();
		servletAppContext.register(ServletAppContext.class);
		
		// client 로부터 request 가 오면
		// 이 request 를 처리하는 Servlet 을
		// DispathcerServlet 으로 설정함
		DispatcherServlet dispatcherServlet = 
				                        new DispatcherServlet(servletAppContext);
		ServletRegistration.Dynamic servlet = 
            servletContext.addServlet("dispatcher", dispatcherServlet);   
		
		// 추가 설정하기
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
		
		
		// Bean 을 정의하는 클래스를 지정함
		
		AnnotationConfigWebApplicationContext  rootAppContext =
		                           new AnnotationConfigWebApplicationContext();
		rootAppContext.register(RootAppContext.class);
		
		ContextLoaderListener listener = new	ContextLoaderListener(rootAppContext);
		servletContext.addListener(listener);
		
    // parameter encoding setting (한글처리)
		FilterRegistration.Dynamic filter =
				  servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
		filter.setInitParameter("encoding", "UTF-8");
		filter.addMappingForServletNames(null, false, "dispatcher");
		
	}

}
*/



