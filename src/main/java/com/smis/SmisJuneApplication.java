package com.smis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;


@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
//@Theme (variant= Lumo.LIGHT)
//@JsModule("@vaadin/vaadin-lumo-styles/presets/compact.js")
@Theme(value = "my-theme")
public class SmisJuneApplication extends SpringBootServletInitializer implements AppShellConfigurator{
	
	@Override
	protected SpringApplicationBuilder configure (SpringApplicationBuilder builder) {
		return builder.sources(SmisJuneApplication.class);
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SmisJuneApplication.class, args);
	}

}
