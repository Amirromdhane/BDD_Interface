
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

/**
 * This is a skeleton for realizing a very simple database user interface in
 * java. The interface is an Applet, and it implements the interface
 * ActionListener. If the user performs an action (for example he presses a
 * button), the procedure actionPerformed is called. Depending on his actions,
 * one can implement the database connection (disconnection), querying or
 * insert.
 * 
 * @author zmiklos
 *
 */
public class DatabaseUserInterface extends java.applet.Applet implements ActionListener {

	private static final long serialVersionUID = 1L;

	// textfields & textareas
	private TextField mStat, m1, m2, m3;
	private TextArea mRes, mquery;
	private JFormattedTextField date_deb, date_fin;

	// buttons
	private Button b1, b2, b3, b4, btn_submit, btn_add;
	private Button btn_etudiant, btn_enseingnant, btn_salle, btn_matiere, btn_groupe, btn_seance, btn_affiliation;
	private Button aff_etudiant, aff_enseingnant, aff_salle, aff_matiere, aff_groupe, aff_seance, aff_affiliation;

	// Labels
	Label mquery_label = new Label("Query results: ", Label.CENTER);
	Label input_label = new Label("Input fields: ", Label.CENTER);

	// JcombBoxs
	JComboBox<String> liste_enseignant;
	JComboBox<String> liste_salle;
	JComboBox<String> liste_etudiant;
	JComboBox<String> liste_matiere;
	JComboBox<String> liste_groupe;
	JComboBox<String> liste_type;

	// Panels
	JPanel query_panel, insert_panel, insert_fields_panel,affiche_panel;

	// Database variables
	Connection conn;
	Statement st;
	private String table;

	/**
	 * This procedure is called when the Applet is initialized.
	 * 
	 */
	public void init() {
		// configuration
		setSize(1100, 600);

		// initialiting textfields & textareas
		mStat = new TextField(150);
		mStat.setEditable(false);

		m1 = new TextField(120);
		m2 = new TextField(120);
		m3 = new TextField(120);
		date_deb = new JFormattedTextField();
		date_fin = new JFormattedTextField();
		mquery = new TextArea(10, 150);
		mRes = new TextArea(10, 150);
		mRes.setEditable(false);

		// initialiting Buttons
		b1 = new Button("CONNECT");
		b2 = new Button("DISCONNECT");
		b3 = new Button("QUERY");
		b4 = new Button("INSERT");
		btn_enseingnant = new Button("Enseignant");
		btn_etudiant = new Button("Etudiant");
		btn_matiere = new Button("Matiere");
		btn_groupe = new Button("Groupe");
		btn_salle = new Button("Salle");
		btn_seance = new Button("Seance");
		btn_affiliation = new Button("Affiliation Etudiant");
		aff_enseingnant = new Button("Enseignant");
		aff_etudiant = new Button("Etudiant");
		aff_matiere = new Button("Matiere");
		aff_groupe = new Button("Groupe");
		aff_salle = new Button("Salle");
		aff_seance = new Button("Seance");
		aff_affiliation = new Button("Affiliation Etudiant");
		btn_submit = new Button("Executer");
		btn_add = new Button("Ajouter");
		// buttons_sizes
		Dimension btn_size = new Dimension(110, 40);
		b1.setPreferredSize(btn_size);
		b2.setPreferredSize(btn_size);
		b3.setPreferredSize(btn_size);
		b4.setPreferredSize(btn_size);
		btn_add.setPreferredSize(btn_size);
		btn_enseingnant.setPreferredSize(btn_size);
		btn_etudiant.setPreferredSize(btn_size);
		btn_matiere.setPreferredSize(btn_size);
		btn_groupe.setPreferredSize(btn_size);
		btn_salle.setPreferredSize(btn_size);
		btn_affiliation.setPreferredSize(btn_size);
		btn_seance.setPreferredSize(btn_size);
		aff_enseingnant.setPreferredSize(btn_size);
		aff_etudiant.setPreferredSize(btn_size);
		aff_matiere.setPreferredSize(btn_size);
		aff_groupe.setPreferredSize(btn_size);
		aff_salle.setPreferredSize(btn_size);
		aff_affiliation.setPreferredSize(btn_size);
		aff_seance.setPreferredSize(btn_size);
		btn_submit.setPreferredSize(btn_size);
		// buttons actionListner
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		btn_enseingnant.addActionListener(this);
		btn_etudiant.addActionListener(this);
		btn_matiere.addActionListener(this);
		btn_groupe.addActionListener(this);
		btn_salle.addActionListener(this);
		btn_seance.addActionListener(this);
		btn_affiliation.addActionListener(this);
		aff_enseingnant.addActionListener(this);
		aff_etudiant.addActionListener(this);
		aff_matiere.addActionListener(this);
		aff_groupe.addActionListener(this);
		aff_salle.addActionListener(this);
		aff_seance.addActionListener(this);
		aff_affiliation.addActionListener(this);
		btn_submit.addActionListener(this);
		btn_add.addActionListener(this);

		// initialiting JComboBoxs

		liste_enseignant = new JComboBox<String>();
		liste_salle = new JComboBox<String>();
		liste_etudiant = new JComboBox<String>();
		liste_matiere = new JComboBox<String>();
		liste_groupe = new JComboBox<String>();
		liste_type = new JComboBox<String>();
		// JComboBox_size
		Dimension jcombo_size = new Dimension(500, 20);
		liste_enseignant.setPreferredSize(jcombo_size);
		liste_etudiant.setPreferredSize(jcombo_size);
		liste_groupe.setPreferredSize(jcombo_size);
		liste_matiere.setPreferredSize(jcombo_size);
		liste_salle.setPreferredSize(jcombo_size);
		liste_type.setPreferredSize(jcombo_size);
		// initializing liste type
		liste_type.addItem("CM");
		liste_type.addItem("TD");
		liste_type.addItem("TP");

		// Top panel
		JPanel top = new JPanel();
		top.setBackground(Color.white);
		top.add(b1);
		top.add(b2);
		top.add(b3);
		top.add(b4);

		// initializing the applet
		add(top, BorderLayout.NORTH);
		add(mStat, BorderLayout.CENTER);
		b2.setEnabled(false);
		b3.setEnabled(false);
		b4.setEnabled(false);
		conn = null;
		setStatus("Waiting for user actions.");

		// Query panel
		query_panel = new JPanel();
		query_panel.setBackground(Color.white);
		query_panel.setLayout(new BoxLayout(query_panel, BoxLayout.PAGE_AXIS));
		query_panel.add(input_label);
		query_panel.add(mquery);
		query_panel.add(mquery_label);
		query_panel.add(mRes);
		mRes.setText("Query results");
		JPanel submit_panel = new JPanel();
		submit_panel.setBackground(Color.white);
		submit_panel.add(btn_submit);

		query_panel.add(submit_panel);
		// affiche buttons Panel
		affiche_panel = new JPanel();
		affiche_panel.setBackground(Color.white);
		affiche_panel.setLayout(new BoxLayout(affiche_panel, BoxLayout.X_AXIS));
		affiche_panel.add(new Label("Racourcis affichage : "));
		affiche_panel.add(aff_seance);
		affiche_panel.add(Box.createHorizontalStrut(10));
		affiche_panel.add(aff_etudiant);
		affiche_panel.add(Box.createHorizontalStrut(10));
		affiche_panel.add(aff_enseingnant);
		affiche_panel.add(Box.createHorizontalStrut(10));
		affiche_panel.add(aff_matiere);
		affiche_panel.add(Box.createHorizontalStrut(10));
		affiche_panel.add(aff_salle);
		affiche_panel.add(Box.createHorizontalStrut(10));
		affiche_panel.add(aff_groupe);
		affiche_panel.add(Box.createHorizontalStrut(10));
		affiche_panel.add(aff_affiliation);
		affiche_panel.add(Box.createVerticalStrut(10));
		// Insert buttons Panel
		insert_panel = new JPanel();
		insert_panel.setBackground(Color.white);
		insert_panel.setLayout(new BoxLayout(insert_panel, BoxLayout.X_AXIS));
		insert_panel.add(btn_seance);
		insert_panel.add(Box.createHorizontalStrut(10));
		insert_panel.add(btn_etudiant);
		insert_panel.add(Box.createHorizontalStrut(10));
		insert_panel.add(btn_enseingnant);
		insert_panel.add(Box.createHorizontalStrut(10));
		insert_panel.add(btn_matiere);
		insert_panel.add(Box.createHorizontalStrut(10));
		insert_panel.add(btn_salle);
		insert_panel.add(Box.createHorizontalStrut(10));
		insert_panel.add(btn_groupe);
		insert_panel.add(Box.createHorizontalStrut(10));
		insert_panel.add(btn_affiliation);
		insert_panel.add(Box.createVerticalStrut(10));
		// Insert fields Panel
		insert_fields_panel = new JPanel();
		insert_fields_panel.setBackground(Color.white);
		insert_fields_panel.setLayout(new BoxLayout(insert_fields_panel, BoxLayout.Y_AXIS));

	}

	/**
	 * This procedure is called upon a user action.
	 * 
	 * @param event
	 *            The user event.
	 */
	public void actionPerformed(ActionEvent event) {

		// Extract the relevant information from the action (i.e. which button
		// is pressed?)
		Object cause = event.getSource();

		// Button CONNECT
		if (cause == b1) {
			connectToDatabase();
		}

		// Button DISCONNECT
		if (cause == b2) {
			disconnectFromDatabase();

		}

		// Button QUERY
		if (cause == b3) {
			queryDatabase();

		}

		// Button INSERT
		if (cause == b4) {
			insertDatabase();
		}
		
		// Button affiche Enseignant
		if (cause == aff_enseingnant) {
			mquery.setText("Select * from Enseignant");
			execute_query();
		}
		
		// Button affiche Etudiant
		if (cause == aff_etudiant) {
			mquery.setText("Select * from Etudiant");
			execute_query();
		}
		
		// Button affiche Matiere
		if (cause == aff_matiere) {
			mquery.setText("Select * from Matiere");
			execute_query();
		}
		
		// Button affiche Salle
		if (cause == aff_salle) {
			mquery.setText("Select * from Salle");
			execute_query();
		}
		
		// Button affiche Seance
		if (cause == aff_seance) {
			mquery.setText("Select * from Seance");
			execute_query();
		}
		
		// Button affiche groupe
		if (cause == aff_groupe) {
			mquery.setText("Select * from Groupe");
			execute_query();
		}
		
		// Button affiche affiliation
		if (cause == aff_affiliation) {
			mquery.setText("Select * from Affiliation_etudiant");
			execute_query();
		}
		
		
		// Button submit (execute querry)
		if (cause == btn_submit) {
			execute_query();
		}

		// Button insertion enseignant
		if (cause == btn_enseingnant) {
			remove_all();
			insertDatabase();
			insert_fields_panel.add(new Label("Nom ", Label.LEFT));
			insert_fields_panel.add(m1);
			insert_fields_panel.add(Box.createVerticalStrut(10));
			insert_fields_panel.add(new Label("Prenom ", Label.LEFT));
			insert_fields_panel.add(m2);
			insert_fields_panel.add(Box.createVerticalStrut(10));
			JPanel add_panel = new JPanel();
			add_panel.setBackground(Color.white);
			add_panel.add(btn_add);
			insert_fields_panel.add(add_panel);
			add(insert_fields_panel);
			table = "Enseignant";
			validate();
		}

		// Button insertion etudiant
		if (cause == btn_etudiant) {
			remove_all();
			insertDatabase();
			insert_fields_panel.add(new Label("Numero Etudiant ", Label.LEFT));
			insert_fields_panel.add(m1);
			insert_fields_panel.add(Box.createVerticalStrut(10));
			insert_fields_panel.add(new Label("Nom ", Label.LEFT));
			insert_fields_panel.add(m2);
			insert_fields_panel.add(Box.createVerticalStrut(10));
			insert_fields_panel.add(new Label("Prenom ", Label.LEFT));
			insert_fields_panel.add(m3);
			insert_fields_panel.add(Box.createVerticalStrut(10));
			JPanel add_panel = new JPanel();
			add_panel.setBackground(Color.white);
			add_panel.add(btn_add);
			insert_fields_panel.add(add_panel);
			add(insert_fields_panel);
			table = "Etudiant";
			validate();
		}

		// Button insertion matiere
		if (cause == btn_matiere) {
			remove_all();
			insertDatabase();
			insert_fields_panel.add(new Label("Identifiant ", Label.LEFT));
			insert_fields_panel.add(m1);
			insert_fields_panel.add(Box.createVerticalStrut(10));
			JPanel add_panel = new JPanel();
			add_panel.setBackground(Color.white);
			add_panel.add(btn_add);
			insert_fields_panel.add(add_panel);
			add(insert_fields_panel);
			table = "Matiere";
			validate();
		}

		// Button insertion groupe
		if (cause == btn_groupe) {
			remove_all();
			insertDatabase();
			insert_fields_panel.add(new Label("Identifiant ", Label.LEFT));
			insert_fields_panel.add(m1);
			insert_fields_panel.add(Box.createVerticalStrut(10));
			JPanel add_panel = new JPanel();
			add_panel.setBackground(Color.white);
			add_panel.add(btn_add);
			insert_fields_panel.add(add_panel);
			add(insert_fields_panel);
			table = "Groupe";
			validate();
		}

		// Button insertion salle
		if (cause == btn_salle) {
			remove_all();
			insertDatabase();
			insert_fields_panel.add(new Label("Identifiant ", Label.LEFT));
			insert_fields_panel.add(m1);
			insert_fields_panel.add(Box.createVerticalStrut(10));
			insert_fields_panel.add(new Label("Type ", Label.LEFT));
			insert_fields_panel.add(liste_type);
			insert_fields_panel.add(Box.createVerticalStrut(10));
			JPanel add_panel = new JPanel();
			add_panel.setBackground(Color.white);
			add_panel.add(btn_add);
			insert_fields_panel.add(add_panel);
			add(insert_fields_panel);
			table = "Salle";
			validate();
		}

		// Button insertion séance
		if (cause == btn_seance) {
			remove_all();
			insertDatabase();
			insert_fields_panel.add(new Label("Enseignant ", Label.LEFT));
			insert_fields_panel.add(liste_enseignant);
			insert_fields_panel.add(Box.createVerticalStrut(10));
			insert_fields_panel.add(new Label("Matiere ", Label.LEFT));
			insert_fields_panel.add(liste_matiere);
			insert_fields_panel.add(Box.createVerticalStrut(10));
			insert_fields_panel.add(new Label("Salle ", Label.LEFT));
			insert_fields_panel.add(liste_salle);
			insert_fields_panel.add(Box.createVerticalStrut(10));
			insert_fields_panel.add(new Label("Type ", Label.LEFT));
			insert_fields_panel.add(liste_type);
			insert_fields_panel.add(Box.createVerticalStrut(10));
			insert_fields_panel.add(new Label("Groupe ", Label.LEFT));
			insert_fields_panel.add(liste_groupe);
			insert_fields_panel.add(Box.createVerticalStrut(10));
			try {
				MaskFormatter dateMask1 = new MaskFormatter("####-##-## ##:##");
				MaskFormatter dateMask2 = new MaskFormatter("####-##-## ##:##");
				dateMask1.install(date_deb);
				dateMask2.install(date_fin);
			} catch (ParseException e) {
				setStatus("ERROR DATE FORMAT");
			}
			insert_fields_panel.add(new Label("Date début ", Label.LEFT));
			insert_fields_panel.add(date_deb);
			insert_fields_panel.add(Box.createVerticalStrut(10));
			insert_fields_panel.add(new Label("Date fin ", Label.LEFT));
			insert_fields_panel.add(date_fin);
			insert_fields_panel.add(Box.createVerticalStrut(10));
			JPanel add_panel = new JPanel();
			add_panel.setBackground(Color.white);
			add_panel.add(btn_add);
			insert_fields_panel.add(add_panel);
			table = "Seance";
			combobox_update();
			add(insert_fields_panel);
			validate();
		}

		// Button affiliation
		if (cause == btn_affiliation) {
			remove_all();
			insertDatabase();
			insert_fields_panel.add(new Label("Groupe ", Label.LEFT));
			insert_fields_panel.add(liste_groupe);
			insert_fields_panel.add(Box.createVerticalStrut(10));
			insert_fields_panel.add(new Label("Etudiant ", Label.LEFT));
			insert_fields_panel.add(liste_etudiant);
			insert_fields_panel.add(Box.createVerticalStrut(10));
			JPanel add_panel = new JPanel();
			add_panel.setBackground(Color.white);
			add_panel.add(btn_add);
			insert_fields_panel.add(add_panel);
			add(insert_fields_panel);
			table = "Affiliation_etudiant";
			combobox_update();
			validate();
		}

		// Button Executer l'insertion
		if (cause == btn_add) {
			execute_insertion();
		}

	}

	/**
	 * Set the status text.
	 * 
	 * @param text
	 *            The text to set.
	 */
	private void setStatus(String text) {
		mStat.setText("Status: " + text);
	}

	/**
	 * Procedure the database connection should be implemented.
	 */
	private void connectToDatabase() {

		try {
			String URL = "jdbc:mysql://mysql.istic.univ-rennes1.fr/base_17011071";
			String USER = "user_17011071";
			String PASS = "amir-msk";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASS);

			setStatus("Connected to the database");
			b1.setEnabled(false);
			b2.setEnabled(true);
			b3.setEnabled(true);
			b4.setEnabled(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			setStatus("Connection failed");
		}
	}

	/**
	 * Procedure, where the database connection should be implemented.
	 */
	private void disconnectFromDatabase() {
		remove_all();

		try {
			conn.close();
			conn = null;
			setStatus("Disconnected from the database");
			b1.setEnabled(true);
			b2.setEnabled(false);
			b3.setEnabled(false);
			b4.setEnabled(false);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			setStatus("Disconnection failed");
		}
	}

	/**
	 * Execute a query and display the results. Implement the database querying
	 * and the display of the results here
	 */
	private void queryDatabase() {
		if (conn == null) {
			setStatus("CONNECT DATABASE FIRST !! ");
		} else {
			setStatus("Querying the database");
			remove_all();
			add(affiche_panel);
			add(query_panel, BorderLayout.AFTER_LAST_LINE);
			validate();

		}
	}

	/**
	 * Insert tuples to the database.
	 */
	private void insertDatabase() {

		if (conn == null) {
			setStatus("CONNECT DATABASE FIRST !! ");
		} else {

			setStatus("Choisissez une table");
			remove_all();
			add(insert_panel);
			validate();

		}

	}

	private void remove_all() {
		remove(query_panel);
		remove(insert_panel);
		remove(insert_fields_panel);
		remove(affiche_panel);
		insert_fields_panel.removeAll();
		m1.setText("");
		m2.setText("");
		m3.setText("");

	}

	private void execute_query() {
		mRes.setText("");
		try {
			st = conn.createStatement();
			String sql = mquery.getText();
			ResultSet rs = st.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			for (int i = 1; i <= rsmd.getColumnCount(); i++)
				mRes.append(rsmd.getColumnName(i) + "\t");

			while (rs.next()) {
				mRes.append("\n");
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					mRes.append(rs.getString(i) + "\t");
				}
			}
		} catch (SQLException e) {
			setStatus("QUERY PROBLEM !!");
			mRes.append(e.getMessage());
		}

	}

	private void execute_insertion() {
		String sql = new String();
		switch (table) {
		case "Enseignant":
			sql = "Insert into " + table + "(idEnseignant,nom,prenom) values(default,\"" + m1.getText() + "\",\""
					+ m2.getText() + "\");";
			break;
		case "Etudiant":
			sql = "Insert into " + table + "(idEtudiant,nom,prenom) values(" + m1.getText() + ",\"" + m2.getText()
					+ "\",\"" + m3.getText() + "\");";
			break;
		case "Matiere":
			sql = "Insert into " + table + "(idMatiere) values(\"" + m1.getText() + "\");";
			break;
		case "Salle":
			sql = "Insert into " + table + "(idSalle,type) values(\"" + m1.getText() + "\",\""
					+ liste_type.getSelectedItem() + "\");";
			break;
		case "Groupe":
			sql = "Insert into " + table + "(idGroupe) values(\"" + m1.getText() + "\");";
			break;
		case "Affiliation_etudiant":
			String student = (String) liste_etudiant.getSelectedItem();
			sql = "Insert into " + table + "(Groupe_idGroupe,Etudiant_idEtudiant) values(\""
					+ liste_groupe.getSelectedItem() + "\",\""
					+ student.substring(student.indexOf("(") + 1, student.indexOf(")")) + "\");";
			break;
		case "Seance":
			String teacher = (String) liste_enseignant.getSelectedItem();
			sql = "Insert into " + table + "(idEnseignant,idMatiere,idSalle,Type,idGroupe,Date_deb,Date_fin) values("
					+ teacher.substring(teacher.indexOf("(") + 1, teacher.indexOf(")")) + ",\""
					+ liste_matiere.getSelectedItem() + "\",\"" + liste_salle.getSelectedItem() + "\",\""
					+ liste_type.getSelectedItem() + "\",\"" + liste_groupe.getSelectedItem() + "\",'"
					+ date_deb.getText() + "','" + date_fin.getText() + "');";
			System.out.println(sql);
			break;

		}
		try {
			st = conn.createStatement();
			st.executeUpdate(sql);
			setStatus("Insertion to table '" + table + "' successful");
		} catch (SQLException e) {
			setStatus("Error Insertion");
			e.printStackTrace();
		}

	}

	private void combobox_update() {
		try {
			st = conn.createStatement();
			String sql = "select * from Enseignant";
			ResultSet rs = st.executeQuery(sql);
			liste_enseignant.removeAllItems();
			while (rs.next()) {
				liste_enseignant.addItem(rs.getString(2) + " " + rs.getString(3) + "(" + rs.getString(1) + ")");
			}
		} catch (SQLException e) {
			setStatus("DATABASE CONNEXION PROBLEM !! combobox_update");
			System.err.println(e.getMessage());
		}

		// ************************************************

		try {
			st = conn.createStatement();
			String sql = "select * from Etudiant";
			ResultSet rs = st.executeQuery(sql);
			liste_etudiant.removeAllItems();
			while (rs.next()) {
				liste_etudiant.addItem(rs.getString(2) + " " + rs.getString(3) + "(" + rs.getString(1) + ")");
			}
		} catch (SQLException e) {
			setStatus("DATABASE CONNEXION PROBLEM !! combobox_update");
			System.err.println(e.getMessage());
		}

		// ************************************************

		try {
			st = conn.createStatement();
			String sql = "select * from Groupe";
			ResultSet rs = st.executeQuery(sql);
			liste_groupe.removeAllItems();
			while (rs.next()) {
				liste_groupe.addItem(rs.getString(1));
			}
		} catch (SQLException e) {
			setStatus("DATABASE CONNEXION PROBLEM !! combobox_update");
			System.err.println(e.getMessage());
		}

		// ************************************************

		try {
			st = conn.createStatement();
			String sql = "select * from Matiere";
			ResultSet rs = st.executeQuery(sql);
			liste_matiere.removeAllItems();
			while (rs.next()) {
				liste_matiere.addItem(rs.getString(1));
			}
		} catch (SQLException e) {
			setStatus("DATABASE CONNEXION PROBLEM !! combobox_update");
			System.err.println(e.getMessage());
		}

		// ************************************************

		try {
			st = conn.createStatement();
			String sql = "select * from Salle";
			ResultSet rs = st.executeQuery(sql);
			liste_salle.removeAllItems();
			while (rs.next()) {
				liste_salle.addItem(rs.getString(1));
			}
		} catch (SQLException e) {
			setStatus("DATABASE CONNEXION PROBLEM !! combobox_update");
			System.err.println(e.getMessage());
		}

	}

}
