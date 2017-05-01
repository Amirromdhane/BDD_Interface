
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

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;


/**
 * This is a skeleton for realizing a very simple database user interface in java. 
 * The interface is an Applet, and it implements the interface ActionListener. 
 * If the user performs an action (for example he presses a button), the procedure actionPerformed 
 * is called. Depending on his actions, one can implement the database connection (disconnection), 
 * querying or insert. 
 * 
 * @author zmiklos
 *
 */
public class DatabaseUserInterface extends java.applet.Applet implements ActionListener {

private static final long serialVersionUID = 1L; 

// textfields & textareas
 private TextField mStat, m1, m2, m3, m4, m5, m6;
 private TextArea mRes,mquery;
 
// buttons
 private Button b1, b2, b3, b4,btn_submit,btn_add;
 private Button btn_etudiant, btn_enseingnant, btn_salle, btn_matiere, btn_parcour, btn_seance;
 
 // Labels
 Label mquery_label=new Label("Query results: ", Label.CENTER);
 Label input_label = new Label("Input fields: ", Label.CENTER);
 Label lname= new Label ("Name : ");
 
 // JcombBoxs
 JComboBox liste_enseignat = new JComboBox();
 JComboBox liste_salle = new JComboBox();
 
// Panels
JPanel query_panel;
 
// Database variables
 Connection conn;
 Statement st;
 private String table;
 
 /**
  * This procedure is called when the Applet is initialized.
  * 
  */
 public void init ()
 {    
	//configuration
	 setSize(1100,600);
	 
	 //initialiting textfields & textareas
     mStat = new TextField(150);
     mStat.setEditable(false);
     
     m1 = new TextField(120);
     m2 = new TextField(120);
     m3 = new TextField(120);
     m4 = new TextField(120);
     m5 = new TextField(120);
     m6 = new TextField(120);
     mquery = new TextArea(10,150);
     mRes = new TextArea(10,150);
     mRes.setEditable(false);
    
   //initialiting Buttons
     b1 = new Button("CONNECT");
     b2 = new Button("DISCONNECT");
     b3 = new Button("QUERY");
     b4 = new Button("INSERT");
     btn_enseingnant = new Button("Enseignant");
     btn_etudiant = new Button("Etudiant");
     btn_matiere = new Button("Matiere");
     btn_parcour = new Button("Parcour");
     btn_salle = new Button("Salle");
     btn_seance = new Button("Seance");
     btn_submit= new Button("Executer");
     btn_add = new Button("Ajouter");
     // buttons_sizes
     b1.setPreferredSize(new Dimension(100,40));
     b2.setPreferredSize(new Dimension(100,40));
     b3.setPreferredSize(new Dimension(100,40));
     b4.setPreferredSize(new Dimension(100,40));
     btn_add.setPreferredSize(new Dimension(100,40));
     btn_enseingnant.setPreferredSize(new Dimension(100,40));
     btn_etudiant.setPreferredSize(new Dimension(100,40));
     btn_matiere.setPreferredSize(new Dimension(100,40));
     btn_parcour.setPreferredSize(new Dimension(100,40));
     btn_salle.setPreferredSize(new Dimension(100,40));
     btn_seance.setPreferredSize(new Dimension(100,40));
     btn_submit.setPreferredSize(new Dimension(100,40));
     //buttons actionListner
     b1.addActionListener(this);
     b2.addActionListener(this);
     b3.addActionListener(this);
     b4.addActionListener(this);
     
     
     // Top panel 
     JPanel top = new JPanel();
     top.setBackground(Color.white);
     top.add(b1) ;
     top.add(b2) ;
     top.add(b3) ;
     top.add(b4);
    
     
     // initializing the applet
     add(top,BorderLayout.NORTH);
     add(mStat,BorderLayout.CENTER);
     b2.setEnabled(false);
     b3.setEnabled(false);
     b4.setEnabled(false);
     conn=null;
     setStatus("Waiting for user actions.");
     
     
     
     
     
     
     
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
		btn_submit.addActionListener(this);
		query_panel.add(submit_panel);
     
                
 }
 
 
 /**
  * This procedure is called upon a user action.
  * 
  *  @param event The user event. 
  */
  public void actionPerformed(ActionEvent event)
 {
  
	 // Extract the relevant information from the action (i.e. which button is pressed?)
	 Object cause = event.getSource();

	 // Act depending on the user action
	 // Button CONNECT
     if (cause == b1)
     {
        connectToDatabase();
     }
     
     // Button DISCONNECT
     if (cause == b2)
     {
    	 disconnectFromDatabase();
    	 
     }
     
     //Button QUERY
     if (cause == b3)
     {
    	 queryDatabase();
    	 
     }
     
     //Button INSERT
     if (cause == b4)
     {
         insertDatabase();
     }
     
     //Button execute dans querry
     if (cause == btn_submit)
     {
    	 execute_query();         
     }
     
     //Button insertion enseignant
     if (cause == btn_enseingnant)
     {
    	 remove_all();
    	 
    	 insertDatabase();
    	 m2.setText("First Name"); 
    	 m1.setText("Last Name");
    	 add(m2);
    	 add(m1);
    	 add(btn_add);
    	 table="Enseignant";
    	 validate();
     }
     
   //Button insertion etudiant
     if (cause == btn_etudiant)
     {
    	 remove_all();
    	 insertDatabase();
    	 m1.setText("Student Number");
    	 m3.setText("First Name");
    	 add(m1);
    	 add(m3);
    	 add(m2);
    	 add(m4);
    	 add(btn_add);
    	 table="Etudiant";
    	 validate();
     }

   //Button insertion matiere
     if (cause == btn_matiere)
     {
    	 remove_all();
    	 insertDatabase();
    	 add(m1);
    	 add(btn_add);
    	 table="Matiere";
    	 validate();
     }
     
   //Button insertion parcour
     if (cause == btn_parcour)
     {
    	 remove_all();
    	 insertDatabase();
    	 add(m1);
    	 add(btn_add);
    	 table="Parcours";
    	 validate();
     }
     
   //Button insertion salle
     if (cause == btn_salle)
     {
    	 remove_all();
    	 insertDatabase();
    	 add(m1);
    	 add(btn_add);
    	 table="Salle";
    	 validate();
     }
     
   //Button insertion séance
     if (cause == btn_seance)
     {
    	 remove_all();
    	 insertDatabase();
    	 add(liste_salle);
    	 add(m2);
    	 add(m3);
    	 add(m4);
    	 add(m5);
    	 add(m6);
    	 add(btn_add);
    	 table="Seance";
    	 combobox_update();
    	 validate();
     }
     
     
   //Button Executer l'insertion
     if (cause == btn_add)
     {
    	 execute_insertion();
     }
    
 }
 

/**
 * Set the status text. 
 * 
 * @param text The text to set. 
 */
private void setStatus(String text){
	    mStat.setText("Status: " + text);
  }

/**
 * Procedure, where the database connection should be implemented. 
 */
private void connectToDatabase(){
	
	try{
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
	} catch(Exception e){
		System.err.println(e.getMessage());
		setStatus("Connection failed");
	}
}


/**
 * Procedure, where the database connection should be implemented. 
 */
private void disconnectFromDatabase(){
	remove_all();
	
	try{
		conn.close();
		conn=null;
	setStatus("Disconnected from the database");
	b1.setEnabled(true);
	b2.setEnabled(false);
	b3.setEnabled(false);
	b4.setEnabled(false);
	} catch(Exception e){
		System.err.println(e.getMessage());
		setStatus("Disconnection failed");
	}
}

/**
 * Execute a query and display the results. Implement the database querying and the 
 * display of the results here 
 */
private void queryDatabase(){
	if(conn==null)
	{
		setStatus("CONNECT DATABASE FIRST !! ");
	}
	else
	{
		setStatus("Querying the database");
		remove_all();
		add(query_panel,BorderLayout.AFTER_LAST_LINE);
		validate();
				
	}
}

/**
 * Insert tuples to the database. 
 */
private void insertDatabase(){
	
	
	if(conn==null)
	{
		setStatus("CONNECT DATABASE FIRST !! ");
	}
	else
	{
	
		setStatus("Choisissez une table");
		remove_all();
		add(btn_seance);
		add(btn_etudiant);
		add(btn_enseingnant);
		add(btn_matiere);
		add(btn_salle);
		add(btn_parcour);
		btn_enseingnant.addActionListener(this);
		btn_etudiant.addActionListener(this);
		btn_matiere.addActionListener(this);
		btn_parcour.addActionListener(this);
		btn_salle.addActionListener(this);
		btn_seance.addActionListener(this);
		validate();
			
	}
	
}

private void remove_all()
{
	remove(query_panel);
	remove(m1);
	remove(m2);
	remove(m3);
	remove(m4);
	remove(m5);
	remove(m6);
	remove(btn_add);
	remove(btn_enseingnant);
	remove(btn_etudiant);
	remove(btn_matiere);
	remove(btn_parcour);
	remove(btn_salle);
	remove(btn_seance);
	remove(lname);
	remove(liste_enseignat);
	remove(liste_salle);
	
	
	
}


private void execute_query()
{
	try {
			st=conn.createStatement();
			String sql=mquery.getText();
			ResultSet rs=st.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			mRes.setText("");
			
			for(int i=1;i<=rsmd.getColumnCount();i++)
			mRes.append(rsmd.getColumnName(i)+"\t");
			
			while(rs.next())
			{	
				mRes.append("\n");
				for(int i=1;i<=rsmd.getColumnCount();i++)
				{
					mRes.append(rs.getString(i)+"\t");
				}
			}
		} catch (SQLException e) {
			setStatus("DATABASE CONNEXION PROBLEM !!");
			System.err.println(e.getMessage());
		}
		
}

private void execute_insertion()
{
	try {
		st=conn.createStatement();
		String sql="Insert into "+table+" values()";
		st.executeUpdate(sql);
		setStatus("Insertion to table '"+table+"' successful");
	} catch (SQLException e) {
		setStatus("Error Insertion");
		e.printStackTrace();
	}
	
}

private void combobox_update()
{
	try {
		st=conn.createStatement();
		String sql="select nom, prenom from Enseignant";
		ResultSet rs=st.executeQuery(sql);
		while(rs.next())
		{	
			liste_enseignat.addItem(rs.getString(1)+" "+rs.getString(2));
		}
	} catch (SQLException e) {
		setStatus("DATABASE CONNEXION PROBLEM !! combobox_update");
		System.err.println(e.getMessage());
	}
	
//************************************************	
	
	try {
		st=conn.createStatement();
		String sql="select * from Salle";
		ResultSet rs=st.executeQuery(sql);
		while(rs.next())
		{	
			liste_salle.addItem(rs.getString(2));
		}
	} catch (SQLException e) {
		setStatus("DATABASE CONNEXION PROBLEM !! combobox_update");
		System.err.println(e.getMessage());
	}
	
	
	
}




}
