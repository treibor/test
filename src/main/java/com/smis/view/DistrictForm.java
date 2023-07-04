package com.smis.view;

import com.smis.dbservice.Dbservice;
import com.smis.dbservice.DbserviceMp;
import com.smis.entity.District;
import com.smis.entity.Impldistrict;
import com.smis.entity.District;
import com.smis.entity.State;
import com.smis.view.DistrictForm.ConstiFormEvent;
import com.smis.view.DistrictForm.DeleteEvent;
import com.smis.view.DistrictForm.SaveEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;


public class DistrictForm extends FormLayout {
	Dbservice service;
	Binder<District> binder=new BeanValidationBinder<>(District.class);
	ComboBox<State> state=new ComboBox();
	TextField districtName=new TextField("District Name");
	TextField deputyCommissioner=new TextField("DC Label");
	TextField deputyCommissionerName=new TextField("DC Name");
	TextField districtAddress=new TextField("Address");
	TextField districtPin=new TextField("Pin");
	TextField districtFax=new TextField("Fax");
	TextField districtHq=new TextField("HQ/Capital");
	TextField districtPhone=new TextField("Phone No");
	EmailField districtEmail=new EmailField("Email");
	TextField districtLabel=new TextField("Label");
	Button save= new Button("Save");
	Button delete= new Button("Delete");
	Notification notify=new Notification();
	private District district;
	private Impldistrict impldist;
	public DistrictForm(Dbservice service) {
		this.service=service;
		state.setItems(service.getAllStates());
		state.setItemLabelGenerator(state-> state.getStateName());
		binder.bindInstanceFields(this);
		
		add(state,districtName, deputyCommissioner,districtHq,deputyCommissionerName,districtAddress,districtPin, districtEmail,districtPhone,districtFax, createButtonsLayout());
	
	}

	private Component createButtonsLayout() {
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		save.addClickShortcut(Key.ENTER);
		save.addClickListener(event-> validateandSave());
		delete.addClickListener(event-> fireEvent(new DeleteEvent(this, district)));
		///delete.setEnabled(service.isAdmin());
		return new HorizontalLayout(save, delete);
	}
	
	private void validateandSave() {
		try {
			long dist_code=service.getMaxDistrictCode(state.getValue())+1;
			binder.writeBean(district);
			//System.out.println("A");
			//check if new entry
			if (district.getDistrictCode() < 1) {
				//System.out.println("B");
				district.setDistrictCode(dist_code);
				if (service.getImpldistrict(dist_code-1) == null) {
					//System.out.println("C");
					impldist = new Impldistrict();
					impldist.setDistrictCode(dist_code);
					//impldist.setDistrictId(service.getMaxDistrictCode());
					impldist.setState(state.getValue());
					impldist.setDistrictName(districtName.getValue());
					impldist.setDeputyCommissioner(deputyCommissioner.getValue());
					impldist.setDistrictHq(districtHq.getValue());
					service.saveImplDistrict(impldist);
				}
			}else {
				if (service.getImpldistrict(dist_code-1) == null) {
					//System.out.println("C");
					impldist = new Impldistrict();
					impldist.setDistrictCode(district.getDistrictCode());
					//impldist.setDistrictId(service.getMaxDistrictCode());
					impldist.setState(state.getValue());
					impldist.setDistrictName(districtName.getValue());
					impldist.setDeputyCommissioner(deputyCommissioner.getValue());
					impldist.setDistrictHq(districtHq.getValue());
					service.saveImplDistrict(impldist);
				}
			}
			fireEvent(new SaveEvent(this, district));
		} catch (ValidationException e) {
			notify.show("Please Enter All Required Fields", 3000, Position.TOP_CENTER);
			// e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setDistrict(District district) {
		this.district=district;
		binder.readBean(district);
	}
	
	public static abstract class ConstiFormEvent extends ComponentEvent<DistrictForm> {
		private District district;

		protected ConstiFormEvent(DistrictForm source, District district) {
			super(source, false);
			this.district = district;
		}

		public District getDistrict() {
			return district;
		}
	}

	public static class SaveEvent extends ConstiFormEvent {
		SaveEvent(DistrictForm source, District district) {
			super(source, district);
		}
	}

	public static class DeleteEvent extends ConstiFormEvent {
		DeleteEvent(DistrictForm source, District district) {
			super(source, district);
		}

	}

	public static class CloseEvent extends ConstiFormEvent {
		CloseEvent(DistrictForm source) {
			super(source, null);
		}
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) {
		return getEventBus().addListener(eventType, listener);
	}

	
}
