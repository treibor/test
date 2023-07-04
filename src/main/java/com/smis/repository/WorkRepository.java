package com.smis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smis.entity.Block;
import com.smis.entity.Constituency;
import com.smis.entity.District;
import com.smis.entity.Scheme;
import com.smis.entity.Work;
import com.smis.entity.Year;


@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {
	
	List<Work> findByDistrictOrderByWorkCodeDesc(District district);
	List<Work> findByConstituency(Constituency constituency);	
	List<Work> findByScheme(Scheme scheme);	
	@Query("select coalesce (Max(c.workCode),0) from Work c where c.district= :district")
	Long findMaxWorkCode(@Param ("district") District district);
	
	/*
	@Query("select  c, d, e,f,g, h, i  from Work c join c.installment d join c.year e  join c.scheme f join c.constituency g join c.district h join c.block i where c.block=:block and c.district=:district and c.scheme=:scheme  and c.constituency=:consti and c.scheme=:scheme and c.year=:year and d.installmentNo=:installment order by c.workCode ASC")
	List<Work> getFilteredWorks(@Param("scheme") Scheme scheme, @Param("consti") Constituency consti,  @Param ("block") Block block ,  @Param ("district") District district, @Param ("year") Year year, @Param ("installment") int installment);
	*/
	@Query("select  c from Work c where  c.district=:district and c.scheme=:scheme and c.year=:year ")
	List<Work> getWorksForCalculation(@Param("scheme") Scheme scheme, @Param("district") District district, @Param("year") Year year);
	
	@Query("select  c from Work c where  c.district=:district and (c.scheme=:scheme or :scheme is null or :scheme=0) and (c.year=:year or :year is null or :year=0) and (c.block=:block or :block is null or :block=0) and (c.constituency=:consti or :consti is null or :consti=0)  order by c.workCode Desc")
	List<Work> getFilteredWorks(@Param("scheme") Scheme scheme, @Param("district") District district, @Param("year") Year year,@Param("consti") Constituency consti, @Param("block") Block block);
	
	
	@Query("select  c, d, e, f, g, h from Work c join c.constituency d join c.block e join c.scheme f join c.year g join c.district h where  c.district=:district and (c.scheme=:scheme or :scheme is null or :scheme=0) and (c.year=:year or :year is null or :year=0) and (c.block=:block or :block is null or :block=0) and (c.constituency=:consti or :consti is null or :consti=0) order by d.constituencyName, e.blockName, f.schemeName, g.yearName, c.workCode Desc")
	List<Work> getReportWorks(@Param("scheme") Scheme scheme, @Param("district") District district, @Param("year") Year year,@Param("consti") Constituency consti, @Param("block") Block block);
	
	String a1="select c from Work c where c.district= :district and(";
	String a2="lower(c.workName) like lower(concat('%', :searchTerm, '%')) ";
	String a3="or lower(c.sanctionNo) like lower(concat('%', :searchTerm, '%')))";
	//@Query(a1+a2+a3)
	@Query("select c from Work c where c.district= :district and(lower(c.workName) like lower(concat('%', :searchTerm, '%'))or lower(c.sanctionNo) like lower(concat('%', :searchTerm, '%'))) order by c.workCode Desc")
	List<Work> search(@Param("searchTerm") String searchTerm, @Param("district") District district);
}
