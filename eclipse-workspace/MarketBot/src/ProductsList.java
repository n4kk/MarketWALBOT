import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
public class ProductsList {

	private Products[] products;
	private int nelems;
	
	
	public ProductsList(int length) {
		
		this.products = new Products[length];
		this.nelems = 0;
		// MYSQL insert
		Connection con = getSimpleConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("INSERT INTO bdmarket1 VALUES (?)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.setInt(1, 0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.executeUpdate();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/** Uses DriverManager.  */
	 @SuppressWarnings("deprecation")
	private Connection getSimpleConnection() {
	    //See your driver documentation for the proper format of this string :
	    String DB_CONN_STRING = "jdbc:mysql://localhost:3306/bdmarket";
	    //Provided by your driver documentation. In this case, a MySql driver is used : 
	    String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	    String USER_NAME = "prueba";
	    String PASSWORD = "prueba12345";
	    
	    Connection result = null;
	    try {
	      Class.forName(DRIVER_CLASS_NAME).newInstance();
	    }
	    catch (Exception ex){
	      log("Check classpath. Cannot load db driver: " + DRIVER_CLASS_NAME);
	    }

	    try {
	      result = DriverManager.getConnection(DB_CONN_STRING, USER_NAME, PASSWORD);
	    }
	    catch (SQLException e){
	      log( "Driver loaded, but cannot connect to db: " + DB_CONN_STRING);
	    }
	    return result;
	  }

	  private static void log(Object aObject){
	    System.out.println(aObject);
	  }
	  
	public int getNelems() {
		return this.nelems;

	}
	public void setNelems(int nelems) {
		this.nelems = nelems;
	}

	public void AddProduct(int price, String publisher, String description) {
		//App sin DB
		/*this.products[nelems] = new Products(price,publisher,description);
		this.nelems += 1;*/
		
		// MYSQL update
				Connection con = getSimpleConnection();
				PreparedStatement stmt = null;
				try {
					stmt = con.prepareStatement("UPDATE bdmarket1 SET nelem=nelem+1");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					stmt.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}
	
	public boolean DelProduct(String id) {
		//App sin DB
		//int i,j;
		//for(i = 0; i <= this.nelems; i++) {
			
			//if (this.products[i].getId().equals(id)) {
		//
		//		for(j = i; j < this.nelems-1; j++) {
		//		this.products[j] = this.products[j+1].copiarInstancia();
		//}
		//this.nelems -= 1;
		
		//MySQL check by id
		int aux = 0;
		Connection con = getSimpleConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("SELECT EXISTS(Select 1 FROM bdmarket WHERE id = (?))");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.setString(1,id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet r1 = null;
		try {
			r1 = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			aux = r1.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(aux == 1) {
			// MYSQL delete
			
			try {
				stmt = con.prepareStatement("DELETE FROM bdmarket WHERE id = (?)");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				stmt.setString(1, id);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				stmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// MYSQL UPDATE nelem
			
			try {
				stmt = con.prepareStatement("UPDATE bdmarket1 SET nelem=nelem-1");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				stmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}

		return false;
	}
	
	public String ListMarket() {
		// MYSQL get data
		Connection con = getSimpleConnection();
		PreparedStatement stmt = null;
		String dades = "";
		try {
			stmt = con.prepareStatement("SELECT * FROM bdmarket");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet r1 = null;
		try {
			r1 = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (r1.next()) {
				dades = dades + "Date: " + new SimpleDateFormat("MM-dd-yyyy").format(r1.getDate("fecha")) + "⚡⚡Description: " + r1.getString("description") + "⚡⚡Price: " + r1.getInt("precio") + "$⚡⚡Vendor: " + r1.getString("vendor") + "⚡⚡Id: " + r1.getString("id") + "\n\n";
				
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dades;
		
	}

}
