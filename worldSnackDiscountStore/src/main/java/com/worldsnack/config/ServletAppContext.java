package com.worldsnack.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.worldsnack.dto.UserDTO;
import com.worldsnack.interceptor.CheckWriterInterceptor;
import com.worldsnack.interceptor.LoginCheckInterceptor;
import com.worldsnack.interceptor.TopMenuInterceptor;
import com.worldsnack.mapper.CategoryMapper;
import com.worldsnack.mapper.CommMapper;
import com.worldsnack.mapper.CommentMapper;
import com.worldsnack.mapper.ContentMapper;
import com.worldsnack.mapper.MypageMapper;
import com.worldsnack.mapper.UserMapper;
import com.worldsnack.service.ContentService;


// Spring MVC 프로젝트에 관련된 설정을 하는 클래스
@Configuration
// @Controller 어노테이션이 설정된 클래스를 
// Spring Framework 가 Controller 로 등록함
//                       ㄴ Spring 이 관리하는 Spring Container 의
//                          메모리에 자동으로 Controller 클래스의 객체를 생성함 
@EnableWebMvc
// Controller 클래스가 작성된 package 를 자동으로 scan 함
@ComponentScan(basePackages = {"com.worldsnack.controller", "com.worldsnack.service", "com.worldsnack.dao", "com.worldsnack.config"})
@PropertySource("/WEB-INF/properties/database.properties")
public class ServletAppContext implements WebMvcConfigurer{
	
	@Value("${oracle.classname}")
	private String oracleClassName;
	
	@Value("${oracle.url}")
	private String oracleUrl;
	
	@Value("${oracle.username}")
	private String oracleUserName;
	
	@Value("${oracle.password}")
	private String oraclePassword;
	
	@Value("${path.upload.community}")
  	private String communityUploadPath;
	
	@Value("${path.upload.thumbnails}")
  	private String thumbnailUploadPath;
	
	@Autowired
	private UserDTO loginUserDTO;
	
	@Autowired
	private ContentService contentService;

	// Controller 의 메소드가 반환하는 jsp(view) 이름 앞뒤로
	// 있는 경로의 접두사, 접미사 설정하기
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		WebMvcConfigurer.super.configureViewResolvers(registry);
		registry.jsp("/WEB-INF/views/", ".jsp");
	}
  	
	// 이미지, 음악파일, js, css 파일 등을 저장하는 경로 지정하기
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		// 외부 파일 시스템에 저장된 커뮤니티 이미지 파일 핸들러 설정(hs_comm)
		registry.addResourceHandler("/uploads/community/**")
    .addResourceLocations("file:///" + communityUploadPath);
		
		registry.addResourceHandler("/uploads/thumbnails/**")
    .addResourceLocations("file:///" + thumbnailUploadPath);
		
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/**").addResourceLocations("/resources/");
	}
	
	// database 접속 정보 관리
	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource source = new BasicDataSource();
		source.setDriverClassName(oracleClassName);
		source.setUrl(oracleUrl);
		source.setUsername(oracleUserName);
		source.setPassword(oraclePassword);
		
		return source;
	}
	
	// query 와 접속 정보 관리
	@Bean
	public SqlSessionFactory factory(BasicDataSource source) throws Exception{
		SqlSessionFactoryBean factoryBean = 
				new SqlSessionFactoryBean();
		factoryBean.setDataSource(source);
		SqlSessionFactory factory = factoryBean.getObject();
		return factory;
	}

	//Mapper 관리 - query 실행 : MapperFactoryBean 사용 (java)
	@Bean
	public MapperFactoryBean<UserMapper> getUserMapper(SqlSessionFactory factory) throws Exception{
		MapperFactoryBean<UserMapper> factoryBean = 
				new MapperFactoryBean<>(UserMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}	
	
	@Bean
	public MapperFactoryBean<ContentMapper> getContentMapper(SqlSessionFactory factory) throws Exception{
		MapperFactoryBean<ContentMapper> factoryBean = 
				new MapperFactoryBean<>(ContentMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}
	
	@Bean
	public MapperFactoryBean<CategoryMapper> getCategoryMapper(SqlSessionFactory factory) throws Exception{
		MapperFactoryBean<CategoryMapper> factoryBean = 
				new MapperFactoryBean<>(CategoryMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}
	
	@Bean
	public MapperFactoryBean<MypageMapper> getMypageMapper(SqlSessionFactory factory) throws Exception{
		MapperFactoryBean<MypageMapper> factoryBean = 
				new MapperFactoryBean<>(MypageMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}		
	
	@Bean
	public MapperFactoryBean<CommMapper> getCommMapper(SqlSessionFactory factory) throws Exception{
		MapperFactoryBean<CommMapper> factoryBean = 
				new MapperFactoryBean<>(CommMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}
	
	@Bean
	public MapperFactoryBean<CommentMapper> getCommentMapper(SqlSessionFactory factory) throws Exception{
		MapperFactoryBean<CommentMapper> factoryBean = 
				new MapperFactoryBean<>(CommentMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}

	/*
  // Interceptor 등록하기
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		WebMvcConfigurer.super.addInterceptors(registry);
		
    TopMenuInterceptor topMenuInterceptor = new TopMenuInterceptor(topMenuService);
		InterceptorRegistration regi1 = registry.addInterceptor(topMenuInterceptor);
		
		regi1.addPathPatterns("/**"); 
	}
  */

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		WebMvcConfigurer.super.addInterceptors(registry);
		
		/* TopMenu에서 세션스코프에 있는 loginUserDTO의 정보를 가져옴 */
		TopMenuInterceptor topMenuInterceptor = 
				new TopMenuInterceptor(loginUserDTO);
		InterceptorRegistration regi1 = registry.addInterceptor(topMenuInterceptor);
		regi1.addPathPatterns("/**");
		
		/* 로그인 여부 확인하여 접근 가능 url 지정 */
		LoginCheckInterceptor loginCheckInterceptor = 
				new LoginCheckInterceptor(loginUserDTO);
		InterceptorRegistration regi2 = registry.addInterceptor(loginCheckInterceptor);
	  // 로그인하지 않았을 때 접근 못하게 하는 Url Pattern 을 지정함
		regi2.addPathPatterns("/mypage/*", "/user/logout", "/content/*");
		// 로그인하지 않아도 접근할 수 있는 url pattern  
		regi2.excludePathPatterns("/content/list");	
		
		/* 작성자 확인하여 작성자가 아닐경우 수정 / 삭제 불가 */
		CheckWriterInterceptor checkWriterInterceptor = 
				new CheckWriterInterceptor(loginUserDTO, contentService);
		InterceptorRegistration regi3 = registry.addInterceptor(checkWriterInterceptor);
		regi3.addPathPatterns("/content/modify", "/content/delete");
	}
	
	// errors.properties 파일과 database.properties 파일이 충돌되지 않도록 함
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	
	// errors.properties 파일 등록하기
	// /WEB-INF/properties/errors.properties
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource res = 
				new ReloadableResourceBundleMessageSource();
		res.setBasenames("/WEB-INF/properties/errors");
		return res;				
	}
	
	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
	
}



