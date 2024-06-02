

import javax.swing.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
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

class LaminaMarcoCliente extends JPanel{
	
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

	}
	private class EnviarTexto implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(campo1.getText());
			try {
				//tenes que comunicarte con el servidor papa 
				Socket misocket = new Socket("localhost",9999);
				//los datos salen del cliente que estamos progrmando
				//el flujo de datos va a a siruclar por mi socket
				DataOutputStream flujo_salida= new DataOutputStream(misocket.getOutputStream());
				//en el flijko de datos de salida viaja el texto del campo1 escribe en el texto lo que hay en el campo 1
				flujo_salida.writeUTF(campo1.getText());
				//tenemos que cerrar el flujo
				flujo_salida.close();

			} catch (IOException e1) {
				System.out.println(e1.getMessage());
			}
			
		}
		
	}
	
		
		
		
	private JTextField campo1, nick,ip;
	//creamos campo de texto 
	private JTextArea campochat;
	
	private JButton miboton;
	
}