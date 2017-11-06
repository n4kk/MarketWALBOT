import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class walletList {
	
	private int nElem;
	private wallet[] lista;
	Connection con = null;
	
	
	public walletList(int n){
		
		/*this.lista = new wallet[n];
		this.nElem = 0;*/
		// MYSQL insert
				Connection con = getSimpleConnection();
				PreparedStatement stmt = null;
				try {
					stmt = con.prepareStatement("INSERT INTO wallets1 VALUES (?)");
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
	    String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
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
	
	
	public void addWallet(int user_id, int coins, String username){
		
		this.lista[nElem] = new wallet(user_id,coins,username);
		this.nElem++;
		// MYSQL add wallet to wallet table
				Connection con = getSimpleConnection();
				PreparedStatement stmt = null;
				try {
					stmt = con.prepareStatement("INSERT INTO wallets VALUES (?,?,?)");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					stmt.setInt(1, user_id);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					stmt.setString(2,username);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					stmt.setInt(3, coins);
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
	public boolean isRegistered(String username){
		//App sin DB
		/*int i;
		for(i=0; i < this.nElem; i++) {
			if(lista[i].getUsername().equals(username)) {
				return true;
			}
		}
		
		return false;*/
		
		//MySQL check by username
		int aux = 0;
		Connection con = getSimpleConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("SELECT EXISTS(Select 1 FROM wallets WHERE username = (?))");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.setString(1,username);
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
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(aux == 1) return true;
		return false;
	}
	public boolean isbyUserid(int user_id) {
		
		/*int i;
		for(i=0; i < this.nElem; i++) {
			if(lista[i].getId() == user_id) {
				return true;
			}
		}
		
		return false;*/
		
		//MySQL check by id
				int aux = 0;
				Connection con = getSimpleConnection();
				PreparedStatement stmt = null;
				ResultSet r1 = null;
				try {
					stmt = con.prepareStatement("SELECT EXISTS(Select 1 FROM wallets WHERE id = (?))");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					stmt.setInt(1,user_id);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					r1 = stmt.executeQuery();
					System.out.println(r1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					aux = r1.getInt(1);
					System.out.println(aux);
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
				if(aux == 1) return true;
				return false;
		
	}
	public int walletCoins(int id){
		
		/*int i;
		for(i=0; i < this.nElem; i++){
			if(this.lista[i].getId() == id){
				return this.lista[i].getCoins();
			}
		}
		return -1;
		*/
		
		//Check id on DB
		Connection con = getSimpleConnection();
		PreparedStatement stmt = null;
		int aux = 0;
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
			aux = r1.getInt("coins");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aux;
		
		
	}
	public boolean walletaddCoins(String username, int coins) {
		/*int i;
		for(i=0; i < this.nElem; i++){
			if(this.lista[i].getUsername().equals(username)){
				this.lista[i].addCoins(coins);
			}
		}*/
		//Check wallet in DB
		Connection con = getSimpleConnection();
		PreparedStatement stmt = null;
		int aux = 0;
		try {
			stmt = con.prepareStatement("SELECT EXISTS(Select 1 FROM wallets WHERE username = (?))");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.setString(1,username);
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
			
			//MySQL add coins to username
			
			try {
				stmt = con.prepareStatement("UPDATE wallets SET coins=coins+(?) WHERE username=(?)");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				stmt.setString(2,username);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				stmt.setInt(1, coins);
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
	
	public boolean walletdelCoins(String username, int coins) {
	/*	int i;
		for(i=0; i < this.nElem; i++){
			if(this.lista[i].getUsername().equals(username)){
					this.lista[i].delCoins(coins);
				
				
			}*/
		
		//Check username in DB
		
		int aux = 0;
		Connection con = getSimpleConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("SELECT EXISTS(Select 1 FROM wallets WHERE username = (?))");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.setString(1,username);
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
			//MySQL delete coins to username
			
			try {
				stmt = con.prepareStatement("UPDATE wallets SET coins= CASE WHEN coins>=(?) THEN coins-(?) ELSE 0 END WHERE username=(?)");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				stmt.setString(3,username);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				stmt.setInt(1, coins);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				stmt.setInt(2, coins);
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
}
	/*public wallet[] topCoins() {
		int p1 = 0;
		int p2 = 1;
		int p3 = 2;
		int i;
		for(i=0; i < this.nElem; i++) {
			if(lista[i].getCoins() > lista[p1].getCoins()) {
				p3 = p2;
				p2 = p1;
				p1 = i;
				
			}else if(lista[i].getCoins() > lista[p2].getCoins()) {
				p3 = p2;
				p2 = i;
			}else if(lista[i].getCoins() > lista[p3].getCoins()) {
				p3 = i;
			}
			
		}
		walletList top = new walletList(3);
		top.lista[0] = this.lista[p1].Duplicate();
		top.lista[1] = this.lista[p2].Duplicate();
		top.lista[2] = this.lista[p3].Duplicate();
		
		return top.lista;
		
	}
	
}*/
