package com.apress.calendar.web.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebMvc
@Import({ThymeleafConfig.class})
@ComponentScan(basePackages = {
        "com.apress.calendar.web.controllers",
        "com.apress.calendar.web.model"
})
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
                .setCachePeriod(0) //Set to 0 in order to send cache headers that prevent caching
        ;
    }

    @Override
    public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
        configurer
                .ignoreAcceptHeader(false)
                .favorPathExtension(true) // .html / .json / .ms
                .defaultContentType(MediaType.TEXT_HTML) // text/html
                .mediaTypes(
                        new HashMap<>(){
                            {
                                put("html", MediaType.TEXT_HTML);
                                put("xml", MediaType.APPLICATION_XML);
                                put("json", MediaType.APPLICATION_JSON);
                            }
                        });
    }

    @Bean
    public MappingJackson2JsonView jacksonView() {
        MappingJackson2JsonView jacksonView = new MappingJackson2JsonView();
        jacksonView.setExtractValueFromSingleKeyModel(true);

        Set<String> modelKeys = new HashSet<>();
        modelKeys.add("events");
        modelKeys.add("event");
        jacksonView.setModelKeys(modelKeys);

        return jacksonView;
    }

    @Override
    public void configureViewResolvers(final ViewResolverRegistry registry) {
        registry.viewResolver(thymeleafViewResolver);
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
        resource.setBasenames("/WEB-INF/locales/messages");
        resource.setDefaultEncoding("UTF-8");
        resource.setFallbackToSystemLocale(Boolean.TRUE);
        return resource;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
        configurer.enable();
    }
}
