import java.util.*;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class MarketBot extends TelegramLongPollingBot {
	
	ProductsList market = new ProductsList(200);
	walletList wallets = new walletList(200);
	String[] listado = new String[200];
	int i, nelems, user_id;
	int price, coins;
	String description, publisher, identifier, username;
	
	//Auxiliary variables
	boolean aux1;
	String aux;
	Products auxproducts;
	wallet auxwallet;
	
	//Method returns if a String is a number
	private boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }

	public void onUpdateReceived(Update arg0) {
		//Receive message and text
		if (arg0.hasMessage()) { 
			Message message= arg0.getMessage();
			
			if (message.hasText()) {
				//Getting arguments
				String text = arg0.getMessage().getText();
				String[] descom = text.split(" ");
				
				
				switch(descom[0]) {
				
				case "/walmarket": //Display market products
					aux = market.ListMarket();
					nelems = market.getNelems();
					
						
					//Sending market products list
						
					SendMessage botmessage = new SendMessage().setChatId(arg0.getMessage().getChatId());
					botmessage.setText(aux);
						
					try {
						sendMessage(botmessage);
					}
					catch (TelegramApiException e) {
						e.printStackTrace();
						}
					
					
					break;
					
				case "/addproduct": //Add a product to the product list
					if(descom.length != 3) {  //Checking arguments errors
						SendMessage botmessage22 = new SendMessage().setChatId(arg0.getMessage().getChatId());
						botmessage22.setText("Error de sintaxis. Faltan argumentos, /markethelp para ver los argumentos."); 
						try {
							sendMessage(botmessage22);
						}
						catch (TelegramApiException e) {
							e.printStackTrace();
						}
					} else if (isNumeric(descom[1]) == true) { //Checking if the price arguments is valid
						
						price = Integer.parseInt(descom[1]);
						description = descom[2];
						publisher = message.getFrom().getUserName();
						market.AddProduct(price,publisher,description);
						
			        } else {
			        		SendMessage botmessage1 = new SendMessage().setChatId(arg0.getMessage().getChatId());
			            botmessage1.setText("Error de sintaxis. El argumento precio no es valido.");
			            try {
							sendMessage(botmessage1);
						}
						catch (TelegramApiException e) {
							e.printStackTrace();
						}
			            
			          
			        }

					
					break;
					
				case "/delproduct": //Delete a product of the market list
					SendMessage botmessage11 = new SendMessage().setChatId(arg0.getMessage().getChatId());
					if (descom.length != 2) { //Checking arguments errors
						botmessage11.setText("Error de sintaxis. Faltan argumentos, /markethelp para ver los argumentos.");
						try {
							sendMessage(botmessage11);
						}
						catch (TelegramApiException e) {
							e.printStackTrace();
						}
					} else {
						identifier = descom[1];
						if(!market.DelProduct(identifier)) { //Checking the ID is in the list
			        			
							botmessage11.setText("No se pudo eliminar el producto, ID no encontrado.");
							try {
								sendMessage(botmessage11);
							}
							catch (TelegramApiException e) {
								e.printStackTrace();
							}
						}
					}
					
					break;
				
				case "/wallet": //Show your coins balance and create a wallet if you don't have one.
					user_id = message.getFrom().getId();
					if(!wallets.isbyUserid(user_id)) { 
						username = message.getFrom().getUserName();
						wallets.addWallet(user_id,0,username);	
					}
	        			SendMessage botmessage4 = new SendMessage().setChatId(arg0.getMessage().getChatId());
					botmessage4.setText(message.getFrom().getUserName() + " balance is: " + wallets.walletCoins(user_id) + " coins");
					try {
						sendMessage(botmessage4);
					}
					catch (TelegramApiException e) {
						e.printStackTrace();
					}
				
					break;
					
				/*case "/topwallet": //Shows the top 3 users with more coins
					wallet[] aux4 = wallets.topCoins();
					aux = aux4[0].getUsername() + ": " + aux4[0].getCoins() + " coins\n" + aux4[1].getUsername() + ": " + aux4[1].getCoins() + " coins\n" + aux4[2].getUsername() + ": " + aux4[2].getCoins() + " coins";
					SendMessage botmessage2 = new SendMessage().setChatId(arg0.getMessage().getChatId());
					botmessage2.setText(aux);
					try {
						sendMessage(botmessage2);
					}
					catch (TelegramApiException e) {
						e.printStackTrace();
					}
					break;*/
					
				case "/addcoins": //Adds coins to a wallet
					if(descom.length != 3) { 
						
	        				SendMessage botmessage1 = new SendMessage().setChatId(arg0.getMessage().getChatId());
						botmessage1.setText("Error de sintaxis. Faltan argumentos, /markethelp para ver los argumentos.");
						try {
							sendMessage(botmessage1);
						}
						catch (TelegramApiException e) {
							e.printStackTrace();
						}
						
					} else if (isNumeric(descom[2]) == true) {
						
						user_id = message.getFrom().getId();
						username = descom[1];
						coins = Integer.parseInt(descom[2]);
						SendMessage botmessage1 = new SendMessage().setChatId(arg0.getMessage().getChatId());
						if(!wallets.isRegistered(username)) {
							botmessage1.setText("Wallet not registered, please register with /wallet");
							try {
								sendMessage(botmessage1);
							}
							catch (TelegramApiException e) {
								e.printStackTrace();
							}
							
						} else {
							aux1 = wallets.walletaddCoins(username, coins);
							if(aux1) {
								botmessage1.setText("Coins added successfully!");
								try {
									sendMessage(botmessage1);
								}
								catch (TelegramApiException e) {
									e.printStackTrace();
								}
							}else {
								botmessage1.setText("Failed to add coins. Check the username.");
								try {
									sendMessage(botmessage1);
								}
								catch (TelegramApiException e) {
									e.printStackTrace();
								}
							}
						}
						
			        } else {
			        		SendMessage botmessage1 = new SendMessage().setChatId(arg0.getMessage().getChatId());
			        		botmessage1.setText("Error de sintaxis. El argumento coins no es valido.");
			        		try {
								sendMessage(botmessage1);
							}
							catch (TelegramApiException e) {
								e.printStackTrace();
							}
			          
			        }
					
					break;
					
				case "/delcoins": //Delete coins of a wallet
					SendMessage botmessage1 = new SendMessage().setChatId(arg0.getMessage().getChatId());
					if(descom.length != 3) { 
						
						botmessage1.setText("Error de sintaxis. Faltan argumentos, /markethelp para ver los argumentos.");
						try {
							sendMessage(botmessage1);
						}
						catch (TelegramApiException e) {
							e.printStackTrace();
						}
						
					} else if (isNumeric(descom[2])) {
						
						user_id = message.getFrom().getId();
						username = descom[1];
						coins = Integer.parseInt(descom[2]);
						if(!wallets.isRegistered(username)) {
							
							botmessage1.setText("Wallet not registered, please register with /wallet");
							try {
								sendMessage(botmessage1);
							}
							catch (TelegramApiException e) {
								e.printStackTrace();
							}
						} else {
							wallets.walletdelCoins(username, coins);
							if(aux1) {
								botmessage1.setText("Coins deleted successfully!");
								try {
									sendMessage(botmessage1);
								}
								catch (TelegramApiException e) {
									e.printStackTrace();
								}
							}else {
								botmessage1.setText("Failed to delete coins. Check the username.");
								try {
									sendMessage(botmessage1);
								}
								catch (TelegramApiException e) {
									e.printStackTrace();
								}
							}
						}
			        } else {
			            botmessage1.setText("Error de sintaxis. El argumento coins no es valido.");
			            try {
							sendMessage(botmessage1);
						}
						catch (TelegramApiException e) {
							e.printStackTrace();
						}
			          
			        }
					
					
					break;
					
				case "/markethelp": //Help command
					SendMessage botmessage3 = new SendMessage().setChatId(arg0.getMessage().getChatId());
					
					botmessage3.setText("Mercado de WAL:\n /walmarket - Muestra los productos del mercado \n /wallet - Muestra tus coins y crea una wallet si no tienes \n /addcoins - Añade coins a una wallet (Args: username, coins) \n /delcoins - Elimina coins a una wallet (Args: username, coins) \n /addproduct - Añade un producto al mercado (Args: precio, descripcion con los espacios con '_')\n /delproduct - Elimina un producto del mercado (Arg: Id)");
					
					try {
						sendMessage(botmessage3);
					}
					catch (TelegramApiException e) {
						e.printStackTrace();
					}
					break;
					
				case "/start": //Help command
					SendMessage botmessage5 = new SendMessage().setChatId(arg0.getMessage().getChatId());
					
					botmessage5.setText("Mercado de WAL:\n /walmarket - Muestra los productos del mercado \n /wallet - Muestra tus coins y crea una wallet si no tienes \n /addcoins - Añade coins a una wallet (Args: username, coins) \n /delcoins - Elimina coins a una wallet (Args: username, coins) \n /addproduct - Añade un producto al mercado (Args: precio, descripcion con los espacios con '_')\n /delproduct - Elimina un producto del mercado (Arg: Id)");
					
					try {
						sendMessage(botmessage5);
					}
					catch (TelegramApiException e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}
		
		
		
		
	}

	public String getBotToken() {
		// TODO Auto-generated method stub
		return "429237750:AAHfBykwArKKcCTLuOGWWVHVv5GjRIHCr3A";
	}

	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return null;
	}

}
