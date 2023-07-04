package com.smis.view;

import javax.annotation.security.PermitAll;


import org.springframework.beans.factory.annotation.Autowired;
//import org.vaadin.addons.tatu.TabSheet;

import com.smis.dbservice.Dbservice;
import com.storedobject.chart.BarChart;
import com.storedobject.chart.CategoryData;
import com.storedobject.chart.Data;
import com.storedobject.chart.DataType;
import com.storedobject.chart.Font.Size;
import com.storedobject.chart.NightingaleRoseChart;
import com.storedobject.chart.Position;
import com.storedobject.chart.RectangularCoordinate;
import com.storedobject.chart.SOChart;
import com.storedobject.chart.Title;
import com.storedobject.chart.Toolbox;
import com.storedobject.chart.XAxis;
import com.storedobject.chart.YAxis;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.component.tabs.Tab;
@PageTitle("Home")
@Route(value="", layout=MainLayout.class)
@PermitAll
public class HomeView extends VerticalLayout {
	Dbservice service;
    public HomeView(Dbservice service) {
    	this.service=service;
    	addClassName("homeLayout");
        setSpacing(true);
        Image img = new Image("images/plant.png", "image");
        img.setWidth("200px");
        //add(img);
      
        //add(new H2("Scheme Management & Information System 2.0"));
        //add(new Paragraph("National Informatics Centre, Meghalaya"));
        
        add(getCharts(), getCharts2(), getCharts());
        //add(getTabs());
        setSizeFull();
        //setJustifyContentMode(JustifyContentMode.CENTER);
        //setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        //getStyle().set("text-align", "center");
    }
    public Component getTabs() {
    	Tab details = new Tab("Details");
        Tab payment = new Tab("Payment");
        Tab shipping = new Tab("Shipping");
        Tab tabs = new Tab(details, payment, shipping);
        /*TabSheet t = new TabSheet();
        t.setHeight("200px");
        t.setWidth("400px");
        t.addTab("details", getCharts(), VaadinIcon.TAB);*/
        //details.add(getCharts());
        //payment.add(getCharts2());
        return tabs;
    }
    public Component getCharts() {
    	SOChart soChart = new SOChart();
    	SOChart soChart2 = new SOChart();
    	CategoryData labels = new CategoryData();
    	Data data = new Data();
        //soChart.setSizeFull();
        //soChart2.setSizeFull();
        int i=service.getAllConstituencies().size();
        for(int index=0; index<i; index++) {
        	//System.out.println(service.getAllConstituencies().get(index).getConstituencyName());
        	labels.add(service.getAllConstituencies().get(index).getConstituencyName());
        	//service.getWorkCount(null)
        	data.add(service.getWorkCount(service.getAllConstituencies().get(index)));
        }
        BarChart bc = new BarChart(labels, data);
        RectangularCoordinate rc;
        rc  = new RectangularCoordinate(new XAxis(DataType.CATEGORY), new YAxis(DataType.NUMBER));
        Position p = new Position();
        //p.setBottom(Size.percentage(55));
        //rc.setPosition(p); // Position it leaving 55% space at the bottom
        bc.plotOn(rc); // Bar chart needs to be plotted on a coordinate system
        bc.setName("Works");
        Toolbox toolbox = new Toolbox();
        toolbox.addButton(new Toolbox.Download(), new Toolbox.Zoom());
        // Let's add some titles.
        Title title = new Title("Constituency Wise Works");
        //title.setSubtext("Please Test");
        // We are going to create a couple of charts. So, each chart should be positioned
        // appropriately.
        // Create a self-positioning chart.
        NightingaleRoseChart nc = new NightingaleRoseChart(labels, data);
        nc.setName("Works");
        //Position p = new Position();
        //p.setTop(Size.percentage(50));
        nc.setPosition(p); // Position it leaving 50% space at the top
        //soChart.s
        soChart.add(nc, toolbox);
        soChart2.add(bc, toolbox, title);
        HorizontalLayout getCharts=new HorizontalLayout();
        //getCharts.
        getCharts.addClassName("chartsLayout1");
        getCharts.setWidthFull();
        //getCharts.setHeight("100px");
        getCharts.add(soChart2, soChart);
        return getCharts;
    }
    
    public Component getCharts2() {
    	SOChart soChart = new SOChart();
    	SOChart soChart2 = new SOChart();
    	CategoryData labels = new CategoryData();
    	Data data = new Data();
        //soChart.setSizeFull();
        //soChart2.setSizeFull();
        int i=service.getAllSchemes().size();
        for(int index=0; index<i; index++) {
        	//System.out.println(service.getAllConstituencies().get(index).getConstituencyName());
        	labels.add(service.getAllSchemesWIthNotInUse().get(index).getSchemeName());
        	//service.getWorkCount(null)
        	data.add(service.getWorkCount(service.getAllSchemes().get(index)));
        }
        BarChart bc = new BarChart(labels, data);
        bc.setName("Works");
        RectangularCoordinate rc;
        rc  = new RectangularCoordinate(new XAxis(DataType.CATEGORY), new YAxis(DataType.NUMBER));
        Position p = new Position();
        //p.setBottom(Size.percentage(55));
        //rc.setPosition(p); // Position it leaving 55% space at the bottom
        bc.plotOn(rc); // Bar chart needs to be plotted on a coordinate system
        Toolbox toolbox = new Toolbox();
        toolbox.addButton(new Toolbox.Download(), new Toolbox.Zoom());
        
        // Let's add some titles.
        Title title = new Title("Scheme Wise Works");
        //title.setSubtext("Please Test");
        // We are going to create a couple of charts. So, each chart should be positioned
        // appropriately.
        // Create a self-positioning chart.
        NightingaleRoseChart nc = new NightingaleRoseChart(labels, data);
        nc.setName("Works");
        //Position p = new Position();
        //p.setTop(Size.percentage(50));
        nc.setPosition(p); // Position it leaving 50% space at the top
        //soChart.s
        soChart.add(nc, toolbox);
        soChart2.add(bc, toolbox, title);
        HorizontalLayout getCharts=new HorizontalLayout();
        getCharts.add( soChart2,soChart);
        
        getCharts.addClassName("chartsLayout1");
        getCharts.setWidthFull();
        return getCharts;
    }

}
