package com.smis.view;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import com.flowingcode.vaadin.addons.gridexporter.GridExporter;
//import com.identity.views.CheckBox;
import com.smis.dbservice.Dbservice;
import com.smis.entity.Block;
import com.smis.entity.Constituency;
import com.smis.entity.Installment;
import com.smis.entity.Scheme;
import com.smis.entity.Work;
import com.smis.entity.Year;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("MLA Schemes")
@Route(value = "mlaschemes", layout = MainLayout.class)
@RolesAllowed({ "USER", "SUPER", "ADMIN" })
//@CssImport(value = "../components/vaadin-grid.css", themeFor = "vaadin-grid")
public class WorkView extends VerticalLayout {
	Dbservice service;
	Grid<Work> grid = new Grid<>(Work.class);
	TextField filterText = new TextField();
	ComboBox<Block> block = new ComboBox();
	ComboBox<Constituency> consti = new ComboBox();
	ComboBox<Year> year = new ComboBox();
	ComboBox<Scheme> scheme = new ComboBox();
	// Checkbox displayFilter= new Checkbox("Show More Filters");
	WorkForm workform;
	boolean isAdmin;
	boolean isUser;

	public WorkView(Dbservice service) {
		this.service = service;
		setSizeFull();
		isAdmin = service.isAdmin();
		isUser = service.isUser();
		// displayFilter.addValueChangeListener(e-> displayFilters());
		configureGrid();
		configureForm();
		add(getToolbar(), getContent());
		updateGrid();
		closeEditor();

	}

	private void configureGrid() {
		block.setItems(service.getAllBlocks());
		// block.setClearButtonVisible(true);
		consti.setItems(service.getAllConstituencies());
		scheme.setItems(service.getAllSchemes());
		year.setItems(service.getAllYears());
		block.setClearButtonVisible(true);
		year.setClearButtonVisible(true);
		scheme.setClearButtonVisible(true);
		consti.setClearButtonVisible(true);
		block.setItemLabelGenerator(Block::getBlockName);
		year.setItemLabelGenerator(Year::getYearName);
		scheme.setItemLabelGenerator(Scheme::getSchemeName);
		consti.setItemLabelGenerator(
				constituency -> constituency.getConstituencyName() + "-" + constituency.getConstituencyMLA());
		block.setWidthFull();
		scheme.setWidthFull();
		year.setWidthFull();
		consti.setWidthFull();
		block.addValueChangeListener(e -> filterGrid());
		consti.addValueChangeListener(e -> filterGrid());
		year.addValueChangeListener(e -> filterGrid());
		scheme.addValueChangeListener(e -> filterGrid());
		grid.setSizeFull();
		// grid.setClassNameGenerator(work -> work.getWorkAmount().intValue() > 500 ?
		// "warn" : null);
		// grid.getColumns().forEach(col-> col.setAutoWidth(true));

		grid.setColumns("workCode");
		grid.addColumn(work -> work.getWorkName()).setHeader("Name of The Work").setWidth("20%").setResizable(true)
				.setSortable(true);
		grid.addColumn(work -> work.getWorkAmount()).setHeader("Sanc. Amount").setResizable(true).setSortable(true)
				.setAutoWidth(true);
		Grid.Column<Work> yearColumn = grid.addColumn(work -> work.getYear().getYearName()).setAutoWidth(true)
				.setHeader("Year").setSortable(true).setResizable(true);
		Grid.Column<Work> blockColumn = grid.addColumn(work -> work.getBlock().getBlockName()).setAutoWidth(true)
				.setHeader("Block/MB").setSortable(true).setResizable(true);
		Grid.Column<Work> schemeColumn = grid.addColumn(work -> work.getScheme().getSchemeName()).setAutoWidth(true)
				.setHeader("Scheme").setSortable(true).setResizable(true);
		Grid.Column<Work> constiColumn = grid
				.addColumn(work -> work.getConstituency().getConstituencyNo() + "-"
						+ work.getConstituency().getConstituencyName() + "-"
						+ work.getConstituency().getConstituencyMLA())
				.setWidth("20%").setHeader("Constituency").setSortable(true).setResizable(true);
		grid.addColumn(work -> work.getSanctionNo()).setHeader("Sanc. No").setResizable(true).setSortable(true)
				.setAutoWidth(true);
		grid.addColumn(work -> work.getSanctionDate()).setHeader("Sanc. Date").setResizable(true).setSortable(true)
				.setAutoWidth(true);
		grid.addColumn(work -> work.getNoOfInstallments()).setHeader("Installments").setResizable(true)
				.setSortable(true).setAutoWidth(true);
		/// grid.addColumn(work ->
		/// work.getConstituency().getConstituencyNo()+"-"+work.getConstituency().getConstituencyName()+"-"+work.getConstituency().getConstituencyMLA()).setHeader("Assembly
		/// Constituency").setResizable(true).setSortable(true).setAutoWidth(true);
		grid.addColumn(work -> work.getWorkStatus()).setHeader("Status").setResizable(true).setSortable(true)
				.setAutoWidth(true);
		grid.addColumn(work -> work.getEnteredBy()).setHeader("Entered By").setResizable(true).setSortable(true)
				.setAutoWidth(true);
		grid.addColumn(work -> work.getEnteredOn()).setHeader("Entered On").setResizable(true).setSortable(true)
				.setAutoWidth(true);
		grid.asSingleSelect().addValueChangeListener(e -> editWork(e.getValue()));
		grid.getHeaderRows().clear();
		HeaderRow headerRow = grid.appendHeaderRow();
		headerRow.getCell(blockColumn).setComponent(block);
		headerRow.getCell(constiColumn).setComponent(consti);
		headerRow.getCell(schemeColumn).setComponent(scheme);
		headerRow.getCell(yearColumn).setComponent(year);
		grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
		grid.setClassNameGenerator(work -> {
			if (work.getWorkStatus().equals("Completed"))
				return "high-rating";
			if (work.getWorkStatus().equals("Entered"))
				return "low-rating";
			return null;
		});
		/*
		GridExporter<Work> exporter = GridExporter.createFor(grid, "/custom-template.xlsx", "/custom-template.docx");
		    HashMap<String,String> placeholders = new HashMap<>();
		    placeholders.put("${date}", new SimpleDateFormat().format(Calendar.getInstance().getTime()));
		    exporter.setExportColumn(yearColumn, false);
		    //exporter.setExportColumn(lastNameColumn, true);
		    exporter.setAdditionalPlaceHolders(placeholders);
		    exporter.setSheetNumber(1);
		    exporter.setCsvExportEnabled(false);
		    exporter.setTitle("People information");
		    exporter.setFileName("GridExport" + new SimpleDateFormat("yyyyddMM").format(Calendar.getInstance().getTime()));
		    */
	}

	public void filterGrid() {
		// System.out.println("Executed");
		// selected
		// filterText.setValue("");
		grid.setItems(
				service.getFilteredWorks(scheme.getValue(), consti.getValue(), block.getValue(), year.getValue()));
	}

	private Component getContent() {
		HorizontalLayout content = new HorizontalLayout(grid, workform);
		content.setFlexGrow(1, grid);
		content.setFlexGrow(1, workform);
		content.addClassName("content");
		content.setSizeFull();
		return content;
	}

	public void updateGrid() {

		grid.setItems(service.getAllWorks());
		// System.out.println("Works: "+service.getAllWorks());
	}

	private Component getToolbar() {
		filterText.setPlaceholder("Filter By Work Name or Sanction Number");
		filterText.setClearButtonVisible(true);
		filterText.setValueChangeMode(ValueChangeMode.LAZY);
		filterText.addValueChangeListener(e -> updateList());
		filterText.setWidth("30%");

		Button addButton = new Button("New Work");
		addButton.setIcon(new Icon(VaadinIcon.PLUS_CIRCLE));
		addButton.addClickListener(e -> addWork());
		// for testing purpose: generate dummy data
		Button testButton = new Button("Generate Test Data");
		testButton.addClickListener(e -> generateTestData());
		
		
		HorizontalLayout toolbar = new HorizontalLayout(filterText, addButton, testButton);
		toolbar.setWidthFull();
		return toolbar;
	}

	
	private void generateTestData() {
		// TODO Auto-generated method stub
		try {
			
			for (int a = 0; a < service.getAllSchemes().size(); a++) {
				
				// for (int a = service.getAllSchemes().size()-1; a <
				// service.getAllSchemes().size(); a++) {
				Scheme scheme=service.getAllSchemes().get(a);
				for (int b = 0; b < service.getAllConstituencies().size();b++) {
					Constituency consti= service.getAllConstituencies().get(b);
					for (int c = 0; c < service.getAllBlocks().size(); c++) {
						// for (int c = service.getAllBlocks().size()-1; c <
						// service.getAllBlocks().size(); c++) {
						Block block=service.getAllBlocks().get(c);
						// System.out.println(service.getAllBlocks().get(1));
						for (int d = 0; d < service.getAllYears().size(); d++) {
							// service.getAllYears().size(); d++) {
							Year year=service.getAllYears().get(d); 
							Work testwork = new Work();
							testwork.setBlock(block);
							testwork.setVillage(service.getVillage(service.getAllBlocks().get(1)).get(1));
							testwork.setConstituency(consti);
							testwork.setScheme(scheme);
							testwork.setYear(year);
							testwork.setNoOfInstallments(2);
							testwork.setWorkAmount(BigDecimal.valueOf(10000));
							testwork.setWorkName("Construction at "+consti.getConstituencyName()+" for "+scheme.getSchemeName()+"");
							testwork.setWorkCode(service.getWorkCode() + 1);
							testwork.setDistrict(service.getDistrict());
							testwork.setSanctionNo("ABC/SANC/"+scheme.getSchemeName());
							testwork.setWorkStatus("Entered");
							// testwork.setSanctionDate(new LocalDateTime());
							service.saveWork(testwork);
						}
					}
				}
			}
			updateGrid();
		} catch (Exception e) {
			System.err.println("exception");
			e.printStackTrace(System.err);
			System.err.flush();
			// e.printStackTrace();
		}
	}

	public void configureForm() {
		workform = new WorkForm(service);
		workform.setWidth("35%");
		workform.addListener(WorkForm.SaveEvent.class, this::saveWork);
		workform.addListener(WorkForm.DeleteEvent.class, this::deleteWork);
		workform.addListener(WorkForm.CloseEvent.class, e -> closeEditor());
	}

	public void saveWork(WorkForm.SaveEvent event) {
		long a = event.getWork().getWorkCode();

		service.saveWork(event.getWork());
		updateList();
		long b = service.getWorkCode();
		// System.out.println("Work Code:"+a);
		// System.out.println("Max Work Code:"+b);

		// closeEditor();
		if (a == b) {
			addWorkAgain(event.getWork());
		} else {
			closeEditor();
		}
	}

	private void addWorkAgain(Work work) {
		// workform.save.setEnabled(true);
		grid.asSingleSelect().clear();
		workform.installaccordion.setEnabled(false);
		workform.ucaccordion.setEnabled(false);
		// System.out.println("Lah Poi Hangne mE");
		Work newWork = new Work();
		newWork.setBlock(work.getBlock());
		newWork.setConstituency(work.getConstituency());
		newWork.setSanctionDate(work.getSanctionDate());
		newWork.setScheme(work.getScheme());
		newWork.setYear(work.getYear());
		newWork.setSanctionDate(work.getSanctionDate());
		newWork.setSanctionNo(work.getSanctionNo());
		editWork(newWork);

	}

	public void deleteWork(WorkForm.DeleteEvent event) {
		// service.deleteInstallments(event.getWork());
		service.deleteWork(event.getWork());
		updateList();
		closeEditor();

	}

	private void updateList() {
		block.clear();
		scheme.clear();
		consti.clear();
		year.clear();
		grid.setItems(service.getFilteredWorks(filterText.getValue()));
		// configureGrid();
	}

	private void closeEditor() {
		workform.setWork(null);
		workform.setVisible(false);

	}

	private void addWork() {
		// workform.save.setEnabled(true);
		grid.asSingleSelect().clear();
		workform.installaccordion.setEnabled(false);
		workform.ucaccordion.setEnabled(false);
		// System.out.println("Lah Poi Hangne mE");
		editWork(new Work());
		
	}

	private void editWork(Work work) {
		//System.out.println("Hello "+service.getA);
		//List<Installment> a=work.
		try {
			int workinstallment = 0;
			if (work == null) {
				closeEditor();
			} else {
				workform.setWork(work);
				workform.setVisible(true);
				workform.save.setEnabled(isUser);
				enableFields();
				workinstallment = work.getNoOfInstallments();
				// System.out.println("No of Inst:"+workinstallment);
				if (work.getWorkAmount() != null) {
					// check if work is entered or not by checking if installment is greater than 0
					int tablecount = service.getInstallmentCount(work);
					int toEnter = tablecount + 1;
					// System.out.println("A");
					if (tablecount > 0) {
						// System.out.println("B");
						// check if any installment is entered
						List<Installment> installments = service.getInstallments(work);
						workform.delete.setEnabled(isAdmin);
						disableFields();
						// workform.setEnabled(isAdmin);
						int tablecountindex = tablecount - 1;
						if (workinstallment == tablecount) {
							// check if all installments are entered, (if yes check if uc is enetered
							// System.out.println("C");
							if (installments.get(tablecountindex).getInstallmentLetter() == null) {
								closeAllAccordion();
								// System.out.println("D");
								// workform.ucmaster.setText("UC: " + tablecount);
							} else if (installments.get(tablecountindex).getUcLetter() == null) {
								openUcAccordion();
								workform.ucmaster.setText("UC: " + tablecount);
							} else {
								// Work is completed
								closeAllAccordion();
								workform.save.setEnabled(false);
							}
						} else {
							// Not All Installments are entered
							// System.out.println("E");
							if (installments.get(tablecountindex).getInstallmentLetter() == null) {
								// check if release order is not printed
								closeAllAccordion();
							} else if (installments.get(tablecountindex).getUcLetter() == null) {
								// System.out.println("F");
								openUcAccordion();
								workform.ucmaster.setText("UC: " + tablecount);

							} else {
								openInstallAccordion();
								workform.installmentAmount.setValue(work.getWorkAmount()
										.subtract(installments.get(tablecountindex).getInstallmentAmount()));
								workform.installmentmaster.setText("Installment: " + toEnter);
							}
						}
					} else {
						// "No Installments In the table"-Enter New Installment
						workform.delete.setEnabled(true);
						openInstallAccordion();
						workform.installmentAmount
								.setValue(work.getWorkAmount().divide(new BigDecimal(work.getNoOfInstallments())));
						workform.installmentmaster.setText("Installment: " + toEnter);
					}

				} else {
					// System.out.println("Work is Null, Entering new Work");
					closeAllAccordion();
					// workform.delete.setEnabled(isA);
					enableFields();
				}
			}
		} catch (ArithmeticException aE) {
			aE.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeAllAccordion() {
		workform.workaccordion.setOpened(true);
		workform.installaccordion.setEnabled(false);
		workform.installaccordion.setOpened(false);
		workform.ucaccordion.setEnabled(false);
		workform.ucaccordion.setOpened(false);
	}

	public void openInstallAccordion() {
		workform.workaccordion.setOpened(false);
		workform.installaccordion.setEnabled(true);
		workform.installaccordion.setOpened(true);
		workform.ucaccordion.setEnabled(false);
		workform.ucaccordion.setOpened(false);
		workform.workaccordion.setOpened(false);
	}

	public void openUcAccordion() {
		workform.workaccordion.setOpened(false);
		workform.installaccordion.setEnabled(false);
		workform.installaccordion.setOpened(false);
		workform.ucaccordion.setEnabled(true);
		workform.ucaccordion.setOpened(true);
		workform.workaccordion.setOpened(false);
	}

	public void enableFields() {
		workform.scheme.setEnabled(true);
		workform.constituency.setEnabled(true);
		workform.block.setEnabled(true);
		workform.year.setEnabled(true);
		workform.workAmount.setEnabled(true);
		workform.noOfInstallments.setEnabled(true);
	}

	public void disableFields() {
		workform.scheme.setEnabled(false);
		workform.constituency.setEnabled(false);
		workform.block.setEnabled(false);
		workform.year.setEnabled(false);
		workform.workAmount.setEnabled(false);
		workform.noOfInstallments.setEnabled(false);
	}
}
