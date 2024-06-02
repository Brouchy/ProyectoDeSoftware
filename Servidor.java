

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;


public class Servidor  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoServidor mimarco=new MarcoServidor();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}	
}

class MarcoServidor extends JFrame implements Runnable {
	
	public MarcoServidor(){
		
		setBounds(1200,300,280,350);				
			
		JPanel milamina= new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto=new JTextArea();
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina);
	
		setVisible(true);
		//creamos un hilo cuando carga la aplicacion ejecuta el hilo
		Thread mihilo= new Thread(this);
		 mihilo.start();
		
		}
	
	private	JTextArea areatexto;

	@Override
	//metodo que va a estar a la escucha
	public void run() {
		// System.out.println("estoy a la escucha");
		//ahora tenemos a la escucha a nuestra aplicaicon
		try {
			//tiene un serversocket para estar siempre a la escucha
			ServerSocket servidor = new ServerSocket(9999);
			String nick,ip,mensaje;
			//queremos armar el paquete serializado
			PaqueteEnvio paquete_recibido;


			while(true)
			{
			//tenemos que hacer que acepte cual quier conexion que le venga del esterior
			Socket misocket=servidor.accept();
			//ahora va a ver un flujo de datos que va a utilizar como medio de transporte este socket
			//DataInputStream flujo_entrada= new DataInputStream(misocket.getInputStream());
			//saber leer lo que viene en ese flujo de entrada
			//String mensaje_texto= flujo_entrada.readUTF();
			//areatexto.append("\n"+mensaje_texto);
			//cerrar la conexion
			//misocket.close();
				//creamos el flijo de datos de entrada
			ObjectInputStream paquete_datos=new ObjectInputStream(misocket.getInputStream());
			//tenemos que meter lo que nos llega por lared
			paquete_recibido=(PaqueteEnvio) paquete_datos.readObject();
			nick=paquete_recibido.getNick();
			ip=paquete_recibido.getIp();
			mensaje=paquete_recibido.getMensaje();
			areatexto.append("\n"+nick+" : "+mensaje+" para "+ip);
			misocket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
