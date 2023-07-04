package com.smis.dbservice;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.smis.entity.Block;
import com.smis.entity.Constituencymp;
import com.smis.entity.District;
import com.smis.entity.Impldistrict;
import com.smis.entity.Installmentmp;
import com.smis.entity.Users;
import com.smis.entity.Workmp;
import com.smis.entity.Year;
import com.smis.repository.BlockRepository;
import com.smis.repository.ConstituencympRepository;
import com.smis.repository.ConstituencyRepository;
import com.smis.repository.DistrictRepository;
import com.smis.repository.ImpldistrictRepository;
import com.smis.repository.InstallmentmpRepository;
import com.smis.repository.InstallmentmpRepository;
import com.smis.repository.SchemeRepository;
import com.smis.repository.UserRepository;
import com.smis.repository.WorkmpRepository;
import com.smis.repository.WorkRepository;
import com.smis.repository.YearRepository;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;

@Service
public class DbserviceMp {
	private final WorkmpRepository wrepo;
	private final YearRepository yrepo;
	private final ConstituencympRepository crepo;
	private final BlockRepository brepo;
	private final DistrictRepository drepo;
	private final InstallmentmpRepository irepo;
	private final ImpldistrictRepository idrepo;
	private final UserRepository urepo;
	Notification notify=new Notification();
	public DbserviceMp(UserRepository urepo,WorkmpRepository workrepo, YearRepository yrepo, ConstituencympRepository crepo,BlockRepository brepo,DistrictRepository drepo, InstallmentmpRepository irepo, ImpldistrictRepository idrepo) {
		this.wrepo = workrepo;
		this.yrepo = yrepo;
		this.idrepo=idrepo;
		this.crepo=crepo;
		this.brepo=brepo;
		this.drepo=drepo;
		this.irepo=irepo;
		this.urepo=urepo;
	}
	
	//Development Phase only
	public District getDistrict() {
		return drepo.findByDistrictId(getLoggedUser().getDistrict().getDistrictId());
	}
	public boolean isUser() {
		if (getLoggedUser().getRole().equals("USER")||getLoggedUser().getRole().equals("SUPER")) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isAdmin() {
		if(getLoggedUser().getRole().equals("ADMIN") ||getLoggedUser().getRole().equals("SUPER")) {
			return true;
		}else {
			return false;
		}
		
	}
	public boolean isSuperAdmin() {
		if(getLoggedUser().getRole().equals("SUPER")) {
			return true;
		}else {
			return false;
		}
	}
	public Users findUser(String username) {
		return urepo.findByUserName(username);
	}
	
	public Users getLoggedUser() {
		return urepo.findByUserName(getloggeduser());
	}
	
	public String getloggeduser(){
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}
	
	public void saveUser(Users user) {
		if (user == null) {
			Notification notification = Notification.show("Fail Fail Fail-7734");
			return;
		}
		try {
			urepo.save(user);
		} catch (Exception e) {
			notify.show("Unable to Save User. Error:" + e, 5000, Position.TOP_CENTER);
		}
	}
	
	
	// InstallmentMp Service
	public int getInstallmentMpCount(Workmp work) {
		return irepo.countByWorkmp(work);
	}
	
	public List<Installmentmp> getInstallmentMps(Workmp work){
		return irepo.findByWorkmp(work);
	}
	public List<Installmentmp> getMaxInstallmentMp(Workmp work, int inst_no){
		return irepo.findByWorkmpAndInstallmentNo(work, inst_no);
	}
	
	public List<Installmentmp> getFilteredInstallmentMps( Constituencymp consti,  Year year, int installment, Impldistrict impldistrict){
		return irepo.getFilteredInstallment( consti,  getDistrict(), year, installment, impldistrict);
	}
	
	public List<Installmentmp> getFilteredInstallmentMpForReport( Constituencymp consti,  Year year,  Impldistrict impldistrict){
		return irepo.getReportData(getDistrict(), year,consti, impldistrict);
	}
	
	public Installmentmp getInstallmentByWorkAndNo(int insallment, Workmp workmp) {
		return irepo.getInstallmentByNoAndWork(insallment, workmp);
	}
	public void saveInstallmentMp(Installmentmp install) {
		if(install==null) {
			System.err.println("Work Is Null");
			return;
		}
		try {
			irepo.save(install);
		}catch(Exception e) {
			notify.show("Unable to Save Installment. Error:" + e, 5000, Position.TOP_CENTER);
		}
	}
	
	public void deleteInstallmentMps(Workmp work) {
		try {
			irepo.deleteByWorkmp(work);
		}catch(Exception e) {
			notify.show("Unable to Delete Installment. Error:" + e, 5000, Position.TOP_CENTER);
		}
	}
	
	//Works Queries
	public List<Workmp> getFilteredWorks(String searchTerm){
		try {
			return wrepo.search(searchTerm, getDistrict());
		} catch (Exception e) {
			 return Collections.emptyList();
		}
	}
	public List<Workmp> getFilteredWorks(Impldistrict impldistrict, Year year, Constituencymp consti){
		try {
			return wrepo.getFilteredWorks(getDistrict(),impldistrict,year, consti);
		} catch (Exception e) {
			 return Collections.emptyList();
		}
	}
	public List<Workmp> getFilteredWorksForReport(Impldistrict impldistrict, Year year, Constituencymp consti){
		try {
			return wrepo.getFilteredWorksForReport(getDistrict(),impldistrict,year, consti);
		} catch (Exception e) {
			 return Collections.emptyList();
		}
	}
	
	public long getWorkCode() {
		return wrepo.findMaxWorkCode(getDistrict());	
	}
	
	public void saveWork(Workmp work) {
		if(work==null) {
			System.err.println("Work Is Null");
			return;
		}
		try {
			wrepo.save(work);
		}catch(Exception e) {
			notify.show("Unable to Save Work. Error:" + e, 5000, Position.TOP_CENTER);
		}
		
	}
	public void deleteWork(Workmp work) {
		//irepo.deleteByWork(work);
		try {
			wrepo.delete(work);
		}catch(Exception e) {
			notify.show("Unable to delete Work. Error:" + e, 5000, Position.TOP_CENTER);
		}
	}
	
	//save & Delete Constituency
	public void saveConstituencymp(Constituencymp consti) {
		if(consti==null) {
			System.err.println("Work Is Null");
			return;
		}
		crepo.save(consti);
	}
	public void deleteConstituencymp(Constituencymp consti) {
		try {
			crepo.delete(consti);
		}catch(Exception e) {
			notify.show("Unable to Delete Constituency "+e, 5000, Position.TOP_CENTER);
		}
	}

	// save & Delete Year
	public void saveYear(Year year) {
		if (year == null) {
			System.err.println("Year Is Null");
			return;
		}
		yrepo.save(year);
	}

	public void deleteYear(Year year) {
		try {
		yrepo.delete(year);
		}catch (Exception e) {
			notify.show("Unable to Delete Year "+e, 5000, Position.TOP_CENTER);
		}
	}

			
	// save & Delete blocks
	public void saveBlock(Block block) {
		if (block == null) {
			System.err.println("Block Is Null");
			return;
		}
		brepo.save(block);
	}

	public void deleteBlock(Block block) {
		try {
			brepo.delete(block);
		} catch (Exception e) {
			notify.show("Unable to Delete Constituency " + e, 5000, Position.TOP_CENTER);
		}
	}
	
	//Methods to Get All Data from Individual Tables
	public List<Workmp> getAllWorks() {
		if (isSuperAdmin()) {
			return wrepo.findAll();
		}else {
			//return wrepo.findByDistrict(getDistrict());
			return wrepo.findByDistrictOrderByWorkCodeDesc(getDistrict());
		}
	}
	public List<Year> getAllYears(){
		if (isSuperAdmin()) {
			return yrepo.findAll();
		}else {
			return yrepo.findByDistrictAndInUseOrderByYearNameAsc(getDistrict(), true);
		}
		
	}
	public List<Year> getAllYearsWIthNotInUse(){
		if (isSuperAdmin()) {
			return yrepo.findAll();
		}else {
			return yrepo.findByDistrict(getDistrict());
		}
		
	}
	
	public List <Constituencymp> getAllConstituencies(){
		if (isSuperAdmin()) {
			return crepo.findAll();
		}else {
			return crepo.findByDistrictAndInUseOrderByConstituencyTypeAsc(getDistrict(), true);
		}
		//return crepo.findAll();
	}
	public List <Constituencymp> getAllConstituenciesWIthNotInUse(){
		if (isSuperAdmin()) {
			return crepo.findAll();
		}else {
			return crepo.findByDistrict(getDistrict());
		}
		//return crepo.findAll();
	}
	public List <Block> getAllBlocks(){
		if (isSuperAdmin()) {
			return brepo.findAll();
		}else {
			return brepo.findByDistrict(getDistrict());
		}
		
	}
	public List<District> getAllDistricts(){
		return drepo.findAll();
	}
	public List<Installmentmp> getAllInstallmentMps(){
		return irepo.findAll();
	}
	public List<Impldistrict> getAllImplDistricts(){
		return idrepo.findAllByOrderByDistrictName();
	}
}
