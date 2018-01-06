package Cliente_Servidor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Cliente_Princiapl {

	public static void main(String[] args) {

		marco m1 = new marco();
	}

}

class marco extends JFrame {

	public marco() {
		setTitle("Cliente_1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 200, 500, 500);
		add(new lamina());
		pack();
		setResizable(false);
		setVisible(true);
	}

}

class lamina extends JPanel implements Runnable {

	public lamina() {
		setLayout(new BorderLayout());

		JPanel diseno = new JPanel();
		area = new JTextArea(10, 15);
		area.setLineWrap(true);
		area.setEditable(false);
		barra = new JScrollPane(area);
		add(barra, BorderLayout.NORTH);
		cadena = new JLabel("Inserte frases :");

		diseno.add(cadena, BorderLayout.CENTER);

		texto_1 = new JTextField(20);

		diseno.add(texto_1, BorderLayout.CENTER);

		boton_1 = new JButton("Enviar");

		boton_1.addActionListener(new accionBoton());
		diseno.add(boton_1);
		add(diseno, BorderLayout.SOUTH);

		Thread hilo = new Thread(this);
		hilo.start();
	}

	private class accionBoton implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {
				Socket sock = new Socket("192.168.1.8", 9999);

				DataOutputStream flujo = new DataOutputStream(sock.getOutputStream());
				String mensaje = texto_1.getText().equals("") ? "" : "\n" + (valor++) + "- " + texto_1.getText();
				area.append(mensaje);
				flujo.writeUTF(mensaje);
				texto_1.setText("");
				flujo.close();

			} catch (Exception e1) {

				System.out.println("Ocurrio un error: :$");
			}

		}

	}

	public void run() {

		try {
			ServerSocket servidor = new ServerSocket(8888);

			while (true) {

				Socket sock = servidor.accept();

				DataInputStream receptor = new DataInputStream(sock.getInputStream());

				area.append(receptor.readUTF());

				sock.close();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JButton boton_1;
	private JTextField texto_1;
	private JLabel cadena;
	private JScrollPane barra;
	private JTextArea area;
	private int valor = 1;
}
