

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;


public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoCliente mimarco=new MarcoCliente();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}


class MarcoCliente extends JFrame{
	
	public MarcoCliente(){
		
		setBounds(600,300,280,350);
				
		LaminaMarcoCliente milamina=new LaminaMarcoCliente();
		
		add(milamina);
		
		setVisible(true);
		}	
	
}

class LaminaMarcoCliente extends JPanel implements Runnable{
	
	public LaminaMarcoCliente(){
		nick = new JTextField(5);
		add(nick);
	
		JLabel texto=new JLabel("CHAT-");
		
		add(texto);
		ip= new JTextField(10);
		add(ip);
		//CONSTRUIMOS NUESTRA AREA DE TEXTO
		campochat= new JTextArea(12,20);
		add(campochat);
	
		campo1=new JTextField(20);
	
		add(campo1);		
	
		miboton=new JButton("Enviar");
		EnviarTexto mievento= new EnviarTexto();
		miboton.addActionListener(mievento);
		add(miboton);	
		Thread mihilo=new Thread(this);
		mihilo.start();
	}
	private class EnviarTexto implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			//System.out.println(campo1.getText());
			campochat.append("\n"+campo1.getText());
			try {
				//tenes que comunicarte con el servidor papa 
				Socket misocket = new Socket("192.168.1.53",9999);
				//los datos salen del cliente que estamos progrmando
				//el flujo de datos va a a siruclar por mi socket
				//DataOutputStream flujo_salida= new DataOutputStream(misocket.getOutputStream());
				//en el flijko de datos de salida viaja el texto del campo1 escribe en el texto lo que hay en el campo 1
				//flujo_salida.writeUTF(campo1.getText());
				//tenemos que cerrar el flujo
				//flujo_salida.close();
				//nuevo video empaquetamos el objeto
				PaqueteEnvio datos = new PaqueteEnvio();
				datos.setNick(nick.getText());
				datos.setIp(ip.getText());
				datos.setMensaje(campo1.getText());
				//para enviar un paquete utiliamos creamos el flujo desalida para enviar nuestro paquete
				ObjectOutputStream paquete_datos= new ObjectOutputStream(misocket.getOutputStream());
				paquete_datos.writeObject(datos);
				misocket.close();

				

			} catch (IOException e1) {
				System.out.println(e1.getMessage());
			}
			
		}
		
	}
	
		
		
		
	private JTextField campo1, nick,ip;
	//creamos campo de texto 
	private JTextArea campochat;
	
	private JButton miboton;
	//tenemos que codificar para que este a la escucha
	@Override
	public void run() {
		try{
			ServerSocket servidor_cliente= new ServerSocket(9090);
			Socket cliente;
			PaqueteEnvio paqueteRecibido;
			while (true) {
				cliente=servidor_cliente.accept();
				ObjectInputStream flujoEntrada=new ObjectInputStream(cliente.getInputStream());
				paqueteRecibido=(PaqueteEnvio) flujoEntrada.readObject();
				campochat.append("\n"+paqueteRecibido.getNick()+": "+paqueteRecibido.getMensaje());
				
			}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
}

//clase para crear el objeto para enviar el paquete con toda la info

class PaqueteEnvio implements Serializable{

	private String nick,ip,mensaje;

    // Getter para nick
    public String getNick() {
        return nick;
    }

    // Setter para nick
    public void setNick(String nick) {
        this.nick = nick;
    }

    // Getter para ip
    public String getIp() {
        return ip;
    }

    // Setter para ip
    public void setIp(String ip) {
        this.ip = ip;
    }

    // Getter para mensaje
    public String getMensaje() {
        return mensaje;
    }

    // Setter para mensaje
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}