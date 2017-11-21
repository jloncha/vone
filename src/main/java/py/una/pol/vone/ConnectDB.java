package py.una.pol.vone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB {

	public static void main(String[] argv) {
		Connection conn = ConnectDB.connection();
		try {
			
			for (int j = 96; j <= 103; j++) {

				
				Statement st = conn.createStatement();
				Statement st2 = conn.createStatement();
				//Statement st3 = conn.createStatement();
				ResultSet rsNetwork = st.executeQuery(
						"SELECT idvirtualnetworkentity FROM virtualnetworkentity" + " where lista_id =" + j);
				//ResultSet rsNetwork2 = st3.executeQuery(
					//	"SELECT idvirtualnetworkentity FROM virtualnetworkentity" + " where lista_id =" + j);
				String SQLUpdte = "UPDATE virtualedgeentity " + "SET identificador = ? "
						+ "WHERE idvirtualedgeentity = ?";
				String SQLUpdteNode = "UPDATE virtualnodeentity " + "SET identificador = ? "
						+ "WHERE idvirtualnodeentity = ?";
				Integer i = 0;
				int affectedrows = 0;
				PreparedStatement pstmt = conn.prepareStatement(SQLUpdte);
				PreparedStatement pstmt1 = conn.prepareStatement(SQLUpdteNode);
				// ResultSet rsEdge = null;
				//while edge
				while (rsNetwork.next()) {
					Long idVirtual = rsNetwork.getLong("idvirtualnetworkentity");
					ResultSet rsEdge = st2.executeQuery("SELECT * FROM virtualedgeentity "
							+ "where redvirtual_idvirtualnetworkentity = " + idVirtual);
					i = 0;
					while (rsEdge.next()) {

						pstmt.setInt(1, i);
						pstmt.setLong(2, rsEdge.getLong("idvirtualedgeentity"));

						affectedrows = pstmt.executeUpdate();
						i++;
					}
					rsEdge.close();
					ResultSet rsNode = st2.executeQuery("SELECT * FROM virtualnodeentity "
							+ "where redvirtual_idvirtualnetworkentity = " + idVirtual);
					i = 0;
					while (rsNode.next()) {

						pstmt1.setInt(1, i);
						pstmt1.setLong(2, rsNode.getLong("idvirtualnodeentity"));

						affectedrows = pstmt1.executeUpdate();
						i++;
					}
					rsNode.close();
					// System.out.println("FIN UNO");
				}
				//while node
				/*while (rsNetwork2.next()) {
					Long idVirtual = rsNetwork2.getLong("idvirtualnetworkentity");
					ResultSet rsEdge = st2.executeQuery("SELECT * FROM virtualnodeentity "
							+ "where redvirtual_idvirtualnetworkentity = " + idVirtual);
					i = 0;
					while (rsEdge.next()) {

						pstmt1.setInt(1, i);
						pstmt1.setLong(2, rsEdge.getLong("idvirtualnodeentity"));

						affectedrows = pstmt1.executeUpdate();
						i++;
					}
					rsEdge.close();
					// System.out.println("FIN UNO");
				}*/
				rsNetwork.close();
				//rsNetwork2.close();
				
				st.close();
			}
			/*for (int j = 22; j <= 22; j++) {

				
				Statement st = conn.createStatement();
				ResultSet rsNetwork = st.executeQuery(
						"SELECT idsustrateedgeentity FROM sustrateedgeentity" + " where redsustrato_idsustratenetworkentity"
								+ " = " +  j);
				String SQLUpdte = "UPDATE sustrateedgeentity " + "SET identificador = ? "
						+ "WHERE idsustrateedgeentity = ?";
				Integer i = 0;
				int affectedrows = 0;
				PreparedStatement pstmt = conn.prepareStatement(SQLUpdte);
				// ResultSet rsEdge = null;
				while (rsNetwork.next()) {
					Long idVirtual = rsNetwork.getLong("idsustrateedgeentity");
					pstmt.setInt(1, i);
					pstmt.setLong(2, idVirtual);

					affectedrows = pstmt.executeUpdate();
					i++;
					
					// System.out.println("FIN UNO");
				}
				rsNetwork.close();
				st.close();
			}*/
		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}

	public static Connection connection() {
		System.out.println("-------- PostgreSQL " + "JDBC Connection Testing ------------");

		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
			e.printStackTrace();
			return null;
		}

		System.out.println("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;

		try {

			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/vonesimulator", "vonesimulator",
					"vonesimulator");

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		return connection;
	}
}
