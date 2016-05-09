/**
 * 
 */
package hr.fer.zemris.java.hw11.servleti;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Servlet u kojem se radi inicijaliazacija.
 * 
 * @author Ivan
 * 
 */
@WebServlet("/init")
public class InitServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {

		String dbName = "votingDB";
		String connectionURL = "jdbc:derby://localhost:1527/" + dbName;

		Connection con = null;
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
			System.exit(0);
		}

		cpds.setJdbcUrl(connectionURL);
		cpds.setUser("ivica");
		cpds.setPassword("ivo");
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);

		try {
			con = cpds.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		checkAndCreateBand(con);
		checkAndCreateSinger(con);
		try {
			con.close();
			cpds.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metoda koja provjerava je li postoje zapisi pjevača.
	 * 
	 * @param con
	 */
	private static void checkAndCreateSinger(Connection con) {
		try {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM Polls WHERE Polls.title=?");
			pst.setString(1, "Glasanje za omiljenog pjevača:");
			ResultSet rst = pst.executeQuery();
			if (!rst.next()) {
				int id;
				pst = con.prepareStatement("INSERT INTO Polls (title, message) VALUES(?,?)",
						Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, "Glasanje za omiljenog pjevača:");
				pst.setString(2,
						"Od sljedećih pjevača, koji vam je najdraži? Kliknite na link kako biste glasali!");
				pst.executeUpdate();
				ResultSet keys = pst.getGeneratedKeys();
				keys.next();
				id = keys.getInt(1);

				createSingerOption(con, id);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metoda koja stvara pjevače za koje se glasa.
	 * 
	 * @param con
	 *            Veza na bazu podataka.
	 * @param id
	 *            Id pod kojim je spremljen opis pjevača u bazu podataka.
	 */
	private static void createSingerOption(Connection con, int id) {
		PreparedStatement pst;
		try {

			pst = con.prepareStatement(
					"INSERT INTO PollOptions(optionTitle,optionLink,pollID,votesCount) VALUES (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, "Mate Bulić");
			pst.setString(2, "http://www.youtube.com/watch?v=mcW5Rupj39Y");
			pst.setInt(3, id);
			pst.setInt(4, 125);
			pst.executeUpdate();

			pst = con.prepareStatement(
					"INSERT INTO PollOptions(optionTitle,optionLink,pollID,votesCount) VALUES (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, "Mišo Kovač");
			pst.setString(2, "http://www.youtube.com/watch?v=93XbM3HrdN8");
			pst.setInt(3, id);
			pst.setInt(4, 400);
			pst.executeUpdate();

			pst = con.prepareStatement(
					"INSERT INTO PollOptions(optionTitle,optionLink,pollID,votesCount) VALUES (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, "Jole");
			pst.setString(2, "http://www.youtube.com/watch?v=FkKFfrHbWsg");
			pst.setInt(3, id);
			pst.setInt(4, 70);
			pst.executeUpdate();

			pst = con.prepareStatement(
					"INSERT INTO PollOptions(optionTitle,optionLink,pollID,votesCount) VALUES (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, "Dalmatino");
			pst.setString(2, "http://www.youtube.com/watch?v=uJ_2Yuo0zFg");
			pst.setInt(3, id);
			pst.setInt(4, 20);
			pst.executeUpdate();
			pst.executeUpdate();

			pst = con.prepareStatement(
					"INSERT INTO PollOptions(optionTitle,optionLink,pollID,votesCount) VALUES (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, "Klapa Intrade");
			pst.setString(2, "http://www.youtube.com/watch?v=qrtvMkZ6-4w");
			pst.setInt(3, id);
			pst.setInt(4, 120);
			pst.executeUpdate();

			pst = con.prepareStatement(
					"INSERT INTO PollOptions(optionTitle,optionLink,pollID,votesCount) VALUES (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, "Dječaci");
			pst.setString(2, "http://www.youtube.com/watch?v=TKv7lyIJts0");
			pst.setInt(3, id);
			pst.setInt(4, 140);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Metoda koja provjerava je li postoje zapisani bendovi.
	 * 
	 * @param con
	 *            Veza na bazu podataka.
	 */
	private static void checkAndCreateBand(Connection con) {
		try {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM Polls WHERE Polls.title=?");
			pst.setString(1, "Glasanje za omiljeni bend:");
			ResultSet rst = pst.executeQuery();
			if (!rst.next()) {
				int id;
				pst = con.prepareStatement("INSERT INTO Polls (title, message) VALUES(?,?)",
						Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, "Glasanje za omiljeni bend:");
				pst.setString(2,
						"Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!");
				pst.executeUpdate();
				ResultSet keys = pst.getGeneratedKeys();
				keys.next();
				id = keys.getInt(1);

				createBendOption(con, id);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metoda koja stvara bendove za koje se glasa.
	 * 
	 * @param con
	 *            Veza na bazu podataka.
	 * @param id
	 *            Id pod kojim se spremljen opis benda u bazu podataka.
	 */
	private static void createBendOption(Connection con, int id) {
		PreparedStatement pst;
		try {

			pst = con.prepareStatement(
					"INSERT INTO PollOptions(optionTitle,optionLink,pollID,votesCount) VALUES (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, "The Beatles");
			pst.setString(2, "http://www.geocities.com/~goldenoldies/TwistAndShout-Beatles.mid");
			pst.setInt(3, id);
			pst.setInt(4, 150);
			pst.executeUpdate();

			pst = con.prepareStatement(
					"INSERT INTO PollOptions(optionTitle,optionLink,pollID,votesCount) VALUES (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, "The Platters");
			pst.setString(2, "http://www.geocities.com/~goldenoldies/SmokeGetsInYourEyes-Platters-ver2.mid");
			pst.setInt(3, id);
			pst.setInt(4, 60);
			pst.executeUpdate();

			pst = con.prepareStatement(
					"INSERT INTO PollOptions(optionTitle,optionLink,pollID,votesCount) VALUES (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, "The Beach Boys");
			pst.setString(2, "http://www.geocities.com/~goldenoldies/SurfinUSA-BeachBoys.mid");
			pst.setInt(3, id);
			pst.setInt(4, 150);
			pst.executeUpdate();

			pst = con.prepareStatement(
					"INSERT INTO PollOptions(optionTitle,optionLink,pollID,votesCount) VALUES (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, "The Four Seasons");
			pst.setString(2, "http://www.geocities.com/~goldenoldies/BigGirlsDontCry-FourSeasons.mid");
			pst.setInt(3, id);
			pst.setInt(4, 20);
			pst.executeUpdate();
			pst.executeUpdate();

			pst = con.prepareStatement(
					"INSERT INTO PollOptions(optionTitle,optionLink,pollID,votesCount) VALUES (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, "The Marcels");
			pst.setString(2, "http://www.geocities.com/~goldenoldies/Bluemoon-Marcels.mid");
			pst.setInt(3, id);
			pst.setInt(4, 33);
			pst.executeUpdate();

			pst = con.prepareStatement(
					"INSERT INTO PollOptions(optionTitle,optionLink,pollID,votesCount) VALUES (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, "The Everly Brothers");
			pst.setString(2,
					"	http://www.geocities.com/~goldenoldies/All.I.HaveToDoIsDream-EverlyBrothers.mid");
			pst.setInt(3, id);
			pst.setInt(4, 25);
			pst.executeUpdate();

			pst = con.prepareStatement(
					"INSERT INTO PollOptions(optionTitle,optionLink,pollID,votesCount) VALUES (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, "The Mamas And The Papas");
			pst.setString(2, "http://www.geocities.com/~goldenoldies/CaliforniaDreaming-Mamas-Papas.mid");
			pst.setInt(3, id);
			pst.setInt(4, 20);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
