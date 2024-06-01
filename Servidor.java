

import javax.swing.*;

import java.awt.*;
import java.io.IOException;
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
			ServerSocket servidor = new ServerSocket(9999);
			//tenemos que hacer que acepte cual quier conexion que le venga del esterior
			Socket misocket=servidor.accept();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
