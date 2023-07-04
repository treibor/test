package com.smis.view;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.smis.dbservice.Dbservice;
import com.smis.entity.District;
import com.smis.entity.State;
import com.smis.entity.Users;
import com.smis.security.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.NoTheme;
import com.vaadin.flow.theme.Theme;

@PermitAll
public class MainLayout extends AppLayout{
	Dbservice service;
	@Autowired
	SecurityService securityService;
	Dialog dialog;
	Dialog userdialog;
	Dialog aboutdialog;
	Notification notify;
	PasswordField oldpwd;
    PasswordField newpwd;
    PasswordField confirmpwd;
    Button cancelButton=new Button("Cancel");
    Button saveButton=new Button("Save");
    TextField userName = new TextField("User Name");
	String userType;
	ComboBox<State> state = new ComboBox("State");
	ComboBox<District> district = new ComboBox("District");
	final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	ComboBox usertype=new ComboBox("Role");
	private Users user;
	boolean isUser;
	boolean isAdmin;
	boolean isSuper;
	Anchor anchor =new Anchor("", "SMIS 2.0");
	public MainLayout(Dbservice dbservice) {
		this.service=dbservice;
		usertype.setItems("ADMIN", "USER");
		isAdmin=service.isAdmin();
		isSuper=service.isSuperAdmin();
		isUser=service.isUser();
		createHeader();
		createDrawer();
		
		//setPrimarySection(Section.DRAWER);
	}
	public void populateDistricts() {
		district.setItems(service.getAllDistricts(state.getValue()));
	}
	private void createDrawer() {
		Icon homeicon=new Icon(VaadinIcon.HOME);
		homeicon.setSize("5%");
		Icon workicon = new Icon(VaadinIcon.LOCATION_ARROW_CIRCLE);
		workicon.setSize("5%");
		Icon releaseordericon =new Icon(VaadinIcon.PAPERPLANE);
		releaseordericon.setSize("5%");
		Icon mastericon =new Icon(VaadinIcon.COG);
		mastericon.setSize("5%");
		Icon mpworkicon =new Icon(VaadinIcon.LOCATION_ARROW_CIRCLE_O);
		mpworkicon.setSize("5%");
		Icon printmpicon =new Icon(VaadinIcon.PAPERPLANE_O);
		printmpicon.setSize("5%");
		Icon mastermpicon =new Icon(VaadinIcon.COG_O);
		mastermpicon.setSize("5%");
		Icon reporticon =new Icon(VaadinIcon.TASKS);
		reporticon.setSize("5%");
		Icon supericon =new Icon(VaadinIcon.COGS);
		supericon.setSize("5%");
		RouterLink workView=new RouterLink();
		RouterLink printView=new RouterLink();
		RouterLink masterView=new RouterLink();
		RouterLink workViewmp=new RouterLink();
		RouterLink printViewmp=new RouterLink();
		RouterLink mastermpView=new RouterLink();
		RouterLink reportView=new RouterLink();
		RouterLink superMasterView=new RouterLink();
		RouterLink homeView=new RouterLink();
		
		homeView.add(homeicon);
		homeView.add(" Home");
		homeView.setRoute(HomeView.class);
		workView.add(workicon);
		workView.add(" MLA Schemes");
		workView.setRoute(WorkView.class);
		//workView.setVisible(isUser);
		printView.add(releaseordericon);
		printView.add(" MLA Release Order");
		printView.setRoute(PrintView.class);
		printView.setVisible(isUser);
		masterView.add(mastericon);
		masterView.add(" MLA Master Data");
		masterView.setRoute(MasterView.class);
		masterView.setVisible(isAdmin);
		workViewmp.add(mpworkicon);
		workViewmp.add(" MP Schemes");
		workViewmp.setRoute(WorkMpView.class);
		//workViewmp.setVisible(isUser);
		printViewmp.add(printmpicon);
		printViewmp.add(" MP Release Order");
		printViewmp.setRoute(PrintViewMp.class);
		printViewmp.setVisible(isUser);
		mastermpView.add(mastermpicon);
		mastermpView.add(" MP Master Data");
		mastermpView.setRoute(MastermpView.class);
		mastermpView.setVisible(isAdmin);
		reportView.add(reporticon);
		reportView.add(" Reports");
		reportView.setRoute(ReportView.class);
		superMasterView.add(supericon);
		superMasterView.add(" States || Districts");
		superMasterView.setRoute(SuperMasterView.class);
		superMasterView.setVisible(isSuper);
		//workView.setHighlightCondition(HighlightConditions.sameLocation());
		addToDrawer(new VerticalLayout(homeView,workView,  printView, workViewmp, printViewmp,masterView, mastermpView,  superMasterView,reportView));
	}
	
	private void createHeader() {
	
		Avatar avatarImage = new Avatar(service.getloggeduser());
		avatarImage.setColorIndex(2);
		//avatarImage.addThemeVariants(AvatarVariant.LUMO_LARGE);
		MenuBar menuBar = new MenuBar();
		menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
		MenuItem item = menuBar.addItem(avatarImage);
		SubMenu subMenu = item.getSubMenu();
		subMenu.addItem("About", e -> openAboutDialog());
		subMenu.addItem("Change Password", e->openPasswordDialog());
		subMenu.addItem("Create User", e -> createUser()).setVisible(isAdmin);
		subMenu.addItem("Logout", e -> securityService.logout());
		//SubMenu shareSubMenu = share.getSubMenu();
		//anchor.setTarget("/");
		H3 logo = new H3("SMIS 2.0  || "+service.getDistrict().getDistrictName().toUpperCase());
		//logo.addClassNames("text-s", "m-m");
		HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, menuBar);
		header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
		header.expand(logo);
		header.setWidthFull();
		header.addClassNames("py-0","px-m");
		addToNavbar(header);
	}
	
	private void openAboutDialog() {
		if (aboutdialog != null) {
			aboutdialog = null;
		}
		aboutdialog = new Dialog();
		VerticalLayout dialogLayout1 = createAboutDialog(aboutdialog);
        aboutdialog.add(dialogLayout1);
        aboutdialog.open();
	}
	private VerticalLayout createAboutDialog(Dialog dialog2) {
		H2 headline = new H2("About");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0").set("font-size", "1.5em").set("font-weight", "bold");
        Label text1=new Label("Designed and Developed By NIC, Meghalaya");
        Label text2=new Label("Contact: aiban.m@nic.in");
        Button close=new Button("Close");
        close.addClickListener(e->dialog2.close());
        VerticalLayout dialogLayout1 = new VerticalLayout(headline, text1, text2, close);
        dialogLayout1.setPadding(false);
        dialogLayout1.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout1.getStyle().set("width", "300px").set("max-width", "100%");
        //clearDialog();
        return dialogLayout1;
	}
	private void openPasswordDialog() {
		if (dialog != null) {
			dialog = null;
		}
		dialog = new Dialog();
		VerticalLayout dialogLayout = createDialogLayout(dialog);
        dialog.add(dialogLayout);
        dialog.open();
	}
	private VerticalLayout createDialogLayout(Dialog dialog) {
        H2 headline = new H2("Change Password");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0").set("font-size", "1.5em").set("font-weight", "bold");
        
        oldpwd=new PasswordField("Old Password");
        newpwd=new PasswordField("New Password");
        confirmpwd=new PasswordField("Confirm New Password");
        oldpwd.setRevealButtonVisible(false);
        newpwd.setRevealButtonVisible(false);
        confirmpwd.setRevealButtonVisible(false);
        //oldpwd.setValue("");
        cancelButton.addClickListener(e -> dialog.close());
        saveButton.addClickListener( e -> changePassword());
        VerticalLayout fieldLayout = new VerticalLayout(oldpwd, newpwd, confirmpwd);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, saveButton);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout,buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");
        clearDialog();
        return dialogLayout;
    }
	public void clearDialog() {
		oldpwd.setValue("");
		confirmpwd.setValue("");
		newpwd.setValue("");
	}
	private void changePassword() {
		// notify.show("Under Development", 3000, Position.TOP_CENTER);
		if(oldpwd.getValue()==""||newpwd.getValue()==""||confirmpwd.getValue()=="") {
			notify.show("Error: Enter All Values, Please", 3000, Position.TOP_CENTER);
		}else if(newpwd.getValue().trim().length()<7) {
			notify.show("Error: New Password is too Weak", 3000, Position.TOP_CENTER);
		} else {
			if (newpwd.getValue().trim().equals(confirmpwd.getValue().trim())) {
				String pwd = oldpwd.getValue();
				
				if (passwordEncoder.matches(pwd, service.getLoggedUser().getPassword())) {
					user = service.getLoggedUser();
					user.setPassword(passwordEncoder.encode(newpwd.getValue().trim()));
					service.saveUser(user);
					notify.show("Password Changed Successfully for User:" + user.getUserName(), 3000,
							Position.TOP_CENTER);
					//dialog.close();
					clearDialog();	
				
				} else {
					
					notify.show("Unauthorised User", 3000, Position.TOP_CENTER);
				}
			}else {
				notify.show("Please check and confirm your passwords", 3000, Position.TOP_CENTER);
			}
		}
	}
	
	private void createUser() {
		// TODO Auto-generated method stub
		System.out.println("Executed");
		if(isAdmin==true || isSuper==true) {
			userdialog = new Dialog();
			VerticalLayout dialogLayout1 = createUserDialog(userdialog);
	        userdialog.add(dialogLayout1);
	        userdialog.open();
			
		}else {
			notify.show("Please Contact Your Administrator", 3000, Position.TOP_CENTER);
		}
	}
	private VerticalLayout createUserDialog(Dialog userdialog) {
        H2 headline = new H2("Create User");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0").set("font-size", "1.5em").set("font-weight", "bold");
        state.setItems(service.getAllStates());
        state.addValueChangeListener(e->populateDistricts());
        
		if (isSuper) {
			usertype.setEnabled(true);
			//state.setEnabled(true);
		} else if (isAdmin) {
			state.setValue(service.getLoggedUser().getDistrict().getState());
			district.setValue(service.getLoggedUser().getDistrict());
			//state.setValue(null);
			usertype.setValue("USER");
			usertype.setEnabled(false);
		}
		state.setEnabled(isSuper);
		district.setEnabled(isSuper);
        state.setItemLabelGenerator(State::getStateName);
        district.setItemLabelGenerator(District:: getDistrictName);
        cancelButton.addClickListener(e -> userdialog.close());
        saveButton.addClickListener( e -> saveNewUser());
        newpwd= new PasswordField("Password");
        confirmpwd=new PasswordField("Confirm Password");
        VerticalLayout fieldLayout1 = new VerticalLayout(state, district,userName, newpwd, confirmpwd, usertype);
        fieldLayout1.setSpacing(false);
        fieldLayout1.setPadding(false);
        fieldLayout1.setAlignItems(FlexComponent.Alignment.STRETCH);
        
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout1 = new HorizontalLayout(cancelButton, saveButton);
        buttonLayout1.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        VerticalLayout dialogLayout1 = new VerticalLayout(headline, fieldLayout1, buttonLayout1);
        dialogLayout1.setPadding(false);
        dialogLayout1.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout1.getStyle().set("width", "300px").set("max-width", "100%");
        //clearDialog();
        return dialogLayout1;
    }
	
	private void saveNewUser() {
		// TODO Auto-generated method stub
		if (district.getValue() == null || state.getValue() == null || usertype.getValue() == null
				|| userName.getValue().equals("") || newpwd.getValue() == "" || confirmpwd.getValue() == "") {
			notify.show(" Enter All Values, Please", 3000, Position.TOP_CENTER);
		}else if(userName.getValue().trim().length()<7) {
			notify.show("Error : Username is too short", 3000, Position.TOP_CENTER);
		}else if(newpwd.getValue().trim().length()<7) {
			notify.show("Security Error: New Password is too weak", 3000, Position.TOP_CENTER);
		} else {
			if (!newpwd.getValue().equals(confirmpwd.getValue())) {
				notify.show("Check Your Passwords, Please", 3000, Position.TOP_CENTER);
			} else {
				try {
					if (service.findUser(userName.getValue()) == null) {
						Users users = new Users();
						//System.out.println("UserID:" + service.findMaxUserSerial());
						//users.setUserId(service.findMaxUserSerial() + 1);
						users.setDistrict(district.getValue());
						//users.setState(state.getValue());
						users.setRole(usertype.getValue().toString());
						users.setUserName(userName.getValue());
						users.setPassword(passwordEncoder.encode(newpwd.getValue().trim()));
						service.saveUser(users);
						clearUserFields();
						notify.show("User Created Successfully", 3000, Position.TOP_CENTER);
					}else {
						notify.show("Username Already Taken. Enter Another Username", 3000, Position.TOP_CENTER);
						userName.setValue("");
						userName.focus();
					}
					//notify.show("Wat Leh Kamkai. Enter All Values, Please", 3000, Position.TOP_CENTER);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
	public void clearUserFields() {
		district.setValue(null);
		state.setValue(null);
		userName.setValue("");
		newpwd.setValue("");
		confirmpwd.setValue("");
	}
}

