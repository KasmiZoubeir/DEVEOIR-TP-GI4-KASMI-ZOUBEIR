package com.ensa.gi4.datatabase.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.ensa.gi4.modele.Materiel;
import com.ensa.gi4.modele.Utilisateur;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

public abstract class GenericDAO<T> implements InitializingBean {

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Override
    public void afterPropertiesSet() { // from InitializingBean
        jdbcTemplate = new JdbcTemplate(dataSource);
    } 

    protected List<T> findAll(String query) {
        return jdbcTemplate.query(query, getRowMapper());
    }

    protected T findone(String query, Long id) {
       Materiel t=null;
        		try {
        			t = (Materiel) jdbcTemplate.queryForObject(query, getRowMapper(), id);
        		}catch(DataAccessException ex){
        			System.out.println("Materiel Not Found") ;
        			
        		}
        		return (T) t;
        		
    }

    protected abstract RowMapper<T> getRowMapper();
    public void createExe(String query,String name,String Code){
        int insert =	jdbcTemplate.update(query,name,Code);
        if(insert == 1) {
       	 System.out.println("New Materiel Created Succesfly" + name);
        }else {
       	 System.out.println("error" );
       }
       }
    public void updateExe(String query,String Code,Long id){
        int update =	jdbcTemplate.update(query,Code,id);
        if(update == 1) {
       	 System.out.println("Materiel Updated Succesfly " );
        }else {
       	 System.out.println("error" );
       }
       }
    
public List<T> MaterelAlloueExe(String query,Long id){
	return jdbcTemplate.query(query, getRowMapper(),id);
}
	public void allouerExe(String query,String dure,Long id_user,String user_name,Long id_M) {
		int alloer =	jdbcTemplate.update(query,dure,id_user,user_name,id_M);
        if(alloer == 1) {
       	 System.out.println("New Materiel alloue Succesfly pour " + user_name);
        }else {
        	 System.out.println("error" );
        }
	}

	public void EXE(String query, Long id_M) {
		int good = jdbcTemplate.update(query,id_M);
		if(good != 1) {
			System.out.println("error");
		}
	}
	public void EXERender(String query, Long id_M,Long id_user) {
		int good = jdbcTemplate.update(query,id_M,id_user);
		if(good != 1) {
			System.out.println("vous ete pas deja alouer ce materiel");
		}else {
			  System.out.println("Merciiii le matériel est rendu...");
		}
	}
	public void EXECree(String query, String username, String password, String role) {
		int good = jdbcTemplate.update(query,username,password,role);
		if(good == 1) {
			System.out.println("vous ete cr�e un utilisateur " + role+" avec suces => nom : "+ username);
		}else {
			  System.out.println("Error lord de creation utilisateur repeter!!!!!");
		}
	}
	
public Utilisateur TrouverModeDePasse(String query,String username) {
	
	        return (Utilisateur) jdbcTemplate.queryForObject(query, getRowMapper(), username);
	    
}

public int EXEQuery(String query) {
	return jdbcTemplate.queryForObject(query,Integer.class);
}

	

}
