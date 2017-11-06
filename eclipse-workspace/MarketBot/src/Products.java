import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


public class Products {

	private java.sql.Date fecha;
	private int precio;
	private String id;
	private String vendor,description;
	public Products(int precio, String vendor, String description) {
		this.description = description;
		this.precio = precio;
		this.vendor = vendor;
		
		this.fecha = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		id = shortUUID();
		
		// MYSQL insert
		Connection con = getSimpleConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("INSERT INTO bdmarket VALUES (?,?,?,?,?)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.setDate(1,fecha);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.setInt(2,this.precio);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.setString(3,this.vendor);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.setString(4, this.description);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		try {
			stmt.setString(5, this.id);
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
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public Date getFecha() {
		return fecha;
	}



	public int getPrecio() {
		return precio;
	}


	public String getVendor() {
		return vendor;
	}

	public Products copiarInstancia() {
		Products aux=new Products(this.precio, this.vendor, this.description);
		return aux;
	}
	
	public String SalidaPantalla() {
		return ("Date: " + new SimpleDateFormat("MM-dd-yyyy").format(this.fecha) + "⚡⚡Description: " + this.description + "⚡⚡Price: " + this.precio + "$⚡⚡Vendor: " + this.vendor + "⚡⚡Id: " + this.id);
	}
	
	private String shortUUID() {
		  UUID uuid = UUID.randomUUID();
		  long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
		  return Long.toString(l, Character.MAX_RADIX);
		}
}
