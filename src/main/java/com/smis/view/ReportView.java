package com.smis.view;

import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
//import javax.xml.transform.stream.StreamSource;

import org.apache.catalina.webresources.FileResource;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateConverter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import com.smis.dbservice.Dbservice;
import com.smis.dbservice.DbserviceMp;
import com.smis.entity.Block;
import com.smis.entity.Constituency;
import com.smis.entity.Constituencymp;
import com.smis.entity.District;
import com.smis.entity.Impldistrict;
import com.smis.entity.Installment;
import com.smis.entity.Installmentmp;
import com.smis.entity.Scheme;
import com.smis.entity.Work;
import com.smis.entity.Workmp;
import com.smis.entity.Year;
import com.vaadin.componentfactory.pdfviewer.PdfViewer;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinResponse;
import com.vaadin.flow.server.frontend.installer.DefaultFileDownloader;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.view.JasperViewer;

@PageTitle("Reports")
@Route(value = "printing", layout = MainLayout.class)
@PermitAll
public class ReportView extends VerticalLayout {
	ComboBox<Block> block = new ComboBox("Block/MB");
	ComboBox<Scheme> scheme = new ComboBox("Scheme");
	ComboBox<Year> year = new ComboBox("Financial Year");
	ComboBox<Constituency> consti = new ComboBox("Constituency");
	ComboBox reportTypemla = new ComboBox("Select Report Type");
	ComboBox reportTypemp = new ComboBox("Select Report Type");
	ComboBox<Year> yearmp = new ComboBox("Year");
	ComboBox<Constituencymp> constituencymp = new ComboBox("Constituency");
	ComboBox<Impldistrict> implDistrict = new ComboBox("Implementing District");
	DatePicker fromDate = new DatePicker("Print By Dates");
	DatePicker toDate = new DatePicker();
	Notification notify = new Notification();
	private Dbservice service;
	private DbserviceMp dbservice;
	HorizontalLayout hl4 = new HorizontalLayout();
	DatePicker fromDatep = new DatePicker("Print By Dates");
	DatePicker toDatep = new DatePicker();
	StreamResource resourcerange;
	PdfViewer pdfViewerrange;
	String user;

	public ReportView(Dbservice service, DbserviceMp dbservice) {
		this.dbservice = dbservice;
		this.service = service;
		initializeMlaItems();
		initializeMpItems();
		reportTypemla.setItems("General Report", "Detailed Report");
		reportTypemp.setItems("General Report", "Detailed Report");
		// candi.addValueChangeListener(e-> removePdfViewer());
		add(createFinalPanel(), hl4);
	}

	private void initializeMpItems() {
		constituencymp.setItems(dbservice.getAllConstituencies());
		implDistrict.setItems(dbservice.getAllImplDistricts());
		yearmp.setItems(dbservice.getAllYears());
		constituencymp.setItemLabelGenerator(Constituencymp::getConstituencyType);
		implDistrict.setItemLabelGenerator(Impldistrict::getDistrictName);
		yearmp.setItemLabelGenerator(Year::getYearName);
		constituencymp.addValueChangeListener(e->removePdfViewer());
		implDistrict.addValueChangeListener(e->removePdfViewer());
		year.addValueChangeListener(e->removePdfViewer());
		constituencymp.setClearButtonVisible(true);
		implDistrict.setClearButtonVisible(true);
		yearmp.setClearButtonVisible(true);
	}

	public void initializeMlaItems() {
		block.setItems(service.getAllBlocks());
		scheme.setItems(service.getAllSchemes());
		consti.setItems(service.getAllConstituencies());
		year.setItems(service.getAllYears());
		block.setItemLabelGenerator(Block::getBlockName);
		scheme.setItemLabelGenerator(Scheme::getSchemeName);
		year.setItemLabelGenerator(Year::getYearName);
		consti.setItemLabelGenerator(Constituency::getConstituencyName);
		block.addValueChangeListener(e -> removePdfViewer());
		scheme.addValueChangeListener(e -> removePdfViewer());
		year.addValueChangeListener(e -> removePdfViewer());
		consti.addValueChangeListener(e -> removePdfViewer());
		block.setClearButtonVisible(true);
		scheme.setClearButtonVisible(true);
		block.setClearButtonVisible(true);
		consti.setClearButtonVisible(true);
	}

	private void removePdfViewer() {

		if (hl4 != null) {
			hl4.removeAll();
		}

	}

	public Component createFinalPanel() {
		Accordion accordion = new Accordion();
		accordion.add("MLA Schemes", createMlaPanel());
		accordion.add("MP Schemes", createMpPanel());
		// accordion.set
		accordion.setWidthFull();
		return accordion;
	}

	public Component createMlaPanel() {
		FormLayout fl1 = new FormLayout();
		Button printMla = new Button("Print");
		printMla.addClickListener(e -> printReport());
		fl1.add(reportTypemla, 2);
		fl1.add(consti, 2);
		fl1.add(block, 2);
		fl1.add(scheme, 2);
		fl1.add(year, 2);
		fl1.add(printMla, 2);
		fl1.setSizeFull();
		fl1.setResponsiveSteps(new ResponsiveStep("0", 12),
				// Use two columns, if layout's width exceeds 500px
				new ResponsiveStep("500px", 12));
		// Details details=new Details("Election Personnel", fl1);
		// details.setOpened(false);
		// return details;
		return fl1;
	}

	public Component createMpPanel() {
		FormLayout fl2 = new FormLayout();
		Button printMp = new Button("Print");
		printMp.addClickListener(e -> printMpReport());
		fl2.add(reportTypemp, 2);
		fl2.add(implDistrict, 2);
		fl2.add(constituencymp, 2);
		fl2.add(yearmp, 2);
		fl2.add(printMp, 2);
		fl2.setSizeFull();
		fl2.setResponsiveSteps(new ResponsiveStep("0", 12),
				// Use two columns, if layout's width exceeds 500px
				new ResponsiveStep("500px", 12));
		// Details details=new Details("Election Personnel", fl1);
		// details.setOpened(false);
		// return details;
		return fl2;
	}

	private void printMpReport() {

		if (reportTypemp.getValue() == null || reportTypemp.getValue() == "") {
			notify.show("Please Select The Type of Report", 5000, Position.TOP_CENTER);
		} else {
			removePdfViewer();
			try {
				//String reportPath = "D:";
				URL res = getClass().getClassLoader().getResource("report/Detailsmp.jrxml");
				File file = Paths.get(res.toURI()).toFile();
				String absolutePath = file.getAbsolutePath();
				String reportPath = absolutePath.substring(0, absolutePath.length() - 15);

				if (reportTypemp.getValue() == "Detailed Report") {
					List<Installmentmp> installment = dbservice.getFilteredInstallmentMpForReport(
							constituencymp.getValue(), yearmp.getValue(), implDistrict.getValue());
					Resource resource = new ClassPathResource("report/Detailsmp.jrxml");
					InputStream employeeReportStream = resource.getInputStream();
					JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
					JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(installment);
					Map<String, Object> parameters = new HashMap<>();
					JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
							jrBeanCollectionDataSource);
					JasperExportManager.exportReportToPdfFile(jasperPrint, reportPath + "//detailreport.pdf");
					File a = new File(reportPath + "//detailreport.pdf");
					StreamResource resourcerange = new StreamResource("DetailedReport.pdf", () -> createResource(a));
					PdfViewer pdfViewerrange = new PdfViewer();
					pdfViewerrange.setSrc(resourcerange);
					hl4.setVisible(true);
					hl4.setSizeFull();
					hl4.add(pdfViewerrange);
				} else if (reportTypemp.getValue() == "General Report") {
					List<Workmp> works = dbservice.getFilteredWorksForReport(implDistrict.getValue(), year.getValue(),
							constituencymp.getValue());
					Resource resource = new ClassPathResource("report/Generalmp.jrxml");
					InputStream employeeReportStream = resource.getInputStream();
					JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
					JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(works);
					Map<String, Object> parameters = new HashMap<>();
					JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
							jrBeanCollectionDataSource);
					JasperExportManager.exportReportToPdfFile(jasperPrint, reportPath + "//generalreport.pdf");
					File a = new File(reportPath + "//generalreport.pdf");
					StreamResource resourcerange = new StreamResource("GeneralReport.pdf", () -> createResource(a));
					PdfViewer pdfViewerrange = new PdfViewer();
					pdfViewerrange.setSrc(resourcerange);
					hl4.setVisible(true);
					hl4.setSizeFull();
					hl4.add(pdfViewerrange);
				} else {
					System.out.println(reportTypemp.getValue());
					notify.show("Fatal Error: Contact Programmer ");
				}

				// removePdfViewer();

			} catch (Exception e) {
				notify.show("Error:" + e, 5000, Position.TOP_CENTER);
				e.printStackTrace();
			}
		}
	}

	private void printReport() {

		if (reportTypemla.getValue() == null || reportTypemla.getValue() == "") {
			notify.show("Please Select The Type of Report", 5000, Position.TOP_CENTER);
		} else {
			removePdfViewer();
			try {
				//String reportPath = "D:";
				URL res = getClass().getClassLoader().getResource("report/Detailsmp.jrxml");
				File file = Paths.get(res.toURI()).toFile();
				String absolutePath = file.getAbsolutePath();
				String reportPath = absolutePath.substring(0, absolutePath.length() - 15);

				if (reportTypemla.getValue() == "Detailed Report") {
					List<Installment> installment = service.getInstallmentForReport(scheme.getValue(), year.getValue(),
							consti.getValue(), block.getValue());
					Resource resource = new ClassPathResource("report/Detailsmla.jrxml");
					InputStream employeeReportStream = resource.getInputStream();
					JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
					JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(installment);
					Map<String, Object> parameters = new HashMap<>();
					JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
							jrBeanCollectionDataSource);
					JasperExportManager.exportReportToPdfFile(jasperPrint, reportPath + "//detailreport.pdf");
					File a = new File(reportPath + "//detailreport.pdf");
					StreamResource resourcerange = new StreamResource("DetailedReport.pdf", () -> createResource(a));
					PdfViewer pdfViewerrange = new PdfViewer();
					pdfViewerrange.setSrc(resourcerange);
					hl4.setVisible(true);
					hl4.setSizeFull();
					hl4.add(pdfViewerrange);
				} else if (reportTypemla.getValue() == "General Report") {
					List<Work> works = service.getReportWorks(scheme.getValue(), consti.getValue(), block.getValue(),
							year.getValue());
					Resource resource = new ClassPathResource("report/Generalmla.jrxml");
					InputStream employeeReportStream = resource.getInputStream();
					JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
					JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(works);
					Map<String, Object> parameters = new HashMap<>();
					JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
							jrBeanCollectionDataSource);
					JasperExportManager.exportReportToPdfFile(jasperPrint, reportPath + "//generalreport.pdf");
					File a = new File(reportPath + "//generalreport.pdf");
					StreamResource resourcerange = new StreamResource("GeneralReport.pdf", () -> createResource(a));
					PdfViewer pdfViewerrange = new PdfViewer();
					pdfViewerrange.setSrc(resourcerange);
					hl4.setVisible(true);
					hl4.setSizeFull();
					hl4.add(pdfViewerrange);
				}

				// removePdfViewer();

			} catch (Exception e) {
				notify.show("Error:" + e, 5000, Position.TOP_CENTER);
				e.printStackTrace();
			}
		}
	}

	private InputStream createResource(File path) {// get generated pdf file and create Resource
		try {
			return FileUtils.openInputStream(path);
		} catch (Exception ex) {
		}
		return null;
	}

}