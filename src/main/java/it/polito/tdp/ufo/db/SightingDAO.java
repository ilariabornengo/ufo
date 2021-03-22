package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SightingDAO {
	String jdbcURL= "jdbc:mysql://localhost/ufo_sightings?user=root&password=Borni001" ;
	
	public List<String> readShapes()
	{	
		try {
			Connection conn =DBConnect.getConnection();
		
		String sql="SELECT DISTINCT shape\r\n"
				+ "FROM sighting "
				+ "WHERE shape <>'' "
				+ "ORDER BY shape ASC" ;
		PreparedStatement st=conn.prepareStatement(sql);
		ResultSet res=st.executeQuery(sql);
		
		List<String> formeUFO=new ArrayList<>();
		while(res.next())
		{
			String forma=res.getString("shape");
			formeUFO.add(forma);
		}
		st.close();
		conn.close();
		return formeUFO;
		}catch(SQLException e)
		{
			throw new RuntimeException("Database error in readShape",e); //se ottengo un'eccezione
			//ribalto l'eccezione al mio chiamante
		}
	}
	
	public int countByShapes(String shape)
	{	
		try {

			Connection conn =DBConnect.getConnection();
			String sql2="SELECT COUNT(*) AS cnt FROM sighting WHERE shape=?";
			String shapeScelta=shape;
			PreparedStatement st2=conn.prepareStatement(sql2);
			st2.setString(1, shapeScelta);
			ResultSet res2=st2.executeQuery() ;
			res2.first();
			int count=res2.getInt("cnt"); //estrai dalla colonna cnt
			st2.close();
			conn.close();
			return count;
		}
		catch(SQLException e)
		{
			throw new RuntimeException("Database error in countByShape",e);
		}
	}

}
