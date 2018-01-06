package Servidor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Servidor_Principal {

	public static void main(String[] args) {

		marco2 m1 = new marco2();
	}

}

class marco2 extends JFrame {

	public marco2() {
		setTitle("Cliente_2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 200, 500, 500);
		add(new lamina2());
		pack();
		setResizable(false);
		setVisible(true);
	}

}

class lamina2 extends JPanel implements Runnable {

	public lamina2() {

		setLayout(new BorderLayout());

		JPanel diseno = new JPanel();
		textoArea = new JTextArea(10, 15);
		textoArea.setLineWrap(true);

		textoArea.setEditable(false);
		barra = new JScrollPane(textoArea);
		add(barra, BorderLayout.NORTH);
		cadena = new JLabel("Inserte frases :");

		diseno.add(cadena, BorderLayout.CENTER);

		texto_1 = new JTextField(20);

		diseno.add(texto_1, BorderLayout.CENTER);

		boton_1 = new JButton("Enviar");

		boton_1.addActionListener(new botonaccion());
		diseno.add(boton_1);
		add(diseno, BorderLayout.SOUTH);

		Thread hilo = new Thread(this);
		hilo.start();
	}

	private class botonaccion implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			try {
				Socket sock = new Socket("192.168.1.11", 8888);

				DataOutputStream flujo = new DataOutputStream(sock.getOutputStream());

				String mensaje = texto_1.getText().equals("") ? "" : "\n" + (valor++) + "- " + texto_1.getText();

				textoArea.append(mensaje);
				flujo.writeUTF(mensaje);

				texto_1.setText("");
				flujo.close();

			} catch (Exception e1) {

				System.out.println("Ocurrio un error: :$");
			}

		}

	}

	private JTextArea textoArea;
	private JScrollPane barra;
	private JButton boton_1;
	private JTextField texto_1;
	private JLabel cadena;
	private int valor = 1;

	public void run() {

		try {
			ServerSocket servidor = new ServerSocket(9999);

			while (true) {

				Socket sock = servidor.accept();

				InetAddress localizacion = sock.getInetAddress();

				String ip = localizacion.getHostAddress();

				System.out.println(ip);

				DataInputStream receptor = new DataInputStream(sock.getInputStream());

				textoArea.append(receptor.readUTF());

				sock.close();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
