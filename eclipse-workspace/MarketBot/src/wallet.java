
public class wallet {
	
	private int id;
	private String username = "";
	private int coins;
	
	public wallet(int id, int coins, String username){
		this.id = id;
		this.coins = coins;
		this.username = username;
	}
	
	public void addCoins(int numCoins){
		this.coins += numCoins;
	}
	
	public void delCoins(int numCoins){
		this.coins -= numCoins;
		if(this.coins < 0) this.coins = 0;
	}
	
	public wallet Duplicate(){
		
		wallet aux = new wallet(this.id, this.coins, this.username);
		return aux;
		
	}
	
	
	public int getId() {
		return id;
	}


	public String getUsername() {
		return username;
	}



	public int getCoins(){
		return this.coins;
	}
	
	
}
