
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ApplicationScoped
@ServerEndpoint("/websocket/{client-id}")
public class SocketServer {
	
	private static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
	public static final LinkedList<Session> clients = new LinkedList<Session>();
		
	@OnMessage
	public void recebe(String mensagem,@PathParam("client-id") String clientId)
	{
		 for (Session client : clients) {
	            try {
	            	System.out.println("enviando " + client+" = "+clientId+": "+mensagem);
	                client.getBasicRemote().sendObject(mensagem);            
	 
	            } catch (IOException e) {
	                e.printStackTrace();
	            } catch (EncodeException e) {
	                e.printStackTrace();
	            }
	        }
	}
	
	@OnOpen
    public void onOpen(Session session) {
//		System.out.println("onOpen session:"+session);
        clients.add(session);
    }
	
	@OnClose
    public void onClose(Session session) {
//		System.out.println("onClose session:"+session);
        clients.remove(session);
    }
	
	@OnError
    public void onError(Session session, Throwable t) {
		t.printStackTrace();
//		System.out.println("onError session:"+session);
        clients.remove(session);
    }
	
	public static void  notifica(String mensagem)
	{
		for (Session client : clients) {
            try {
                client.getBasicRemote().sendObject(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DD_MM_YYYY_HH_MM_SS))+" "+ mensagem);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
        }
	}

}
