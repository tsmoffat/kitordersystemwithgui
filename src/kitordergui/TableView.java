package kitordergui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.util.Scanner;
import java.io.IOException;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.stage.Stage;
import javafx.util.Callback;
/**
 *
 * @author tsmoffat
 */
public class TableView{
    private ObservableList<ObservableList> data;
    private javafx.scene.control.TableView tableview;
        public void buildData() throws SQLException, IOException {
		Connection c;
		data = FXCollections.observableArrayList();
		try {
			c = getConnection.getConnection();
			String SQL = "USE mydb";
			ResultSet rs = c.createStatement().executeQuery(SQL);
			// SQL FOR SELECTING TABLES
			
			SQL = "select o.ID, o.CustomerID, c.Name, c.Email_Address, c.Squad, o.Order, o.OrderSize, o.OrderNumber, o.NameOnGarment, i.Item, o.PaidFor from Orders o INNER JOIN Customers c ON o.CustomerID = c.ID INNER JOIN Items i ON i.idItems=o.Order;";
			
			// ResultSet

			rs = c.createStatement().executeQuery(SQL);

			/**********************************
			 * 
			 * TABLE COLUMN ADDED DYNAMICALLY *
			 * 
			 **********************************/

			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				// We are using non property style for making dynamic table
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
				col.setCellValueFactory(
						new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});
				tableview.getColumns().addAll(col);
				
			}
			/********************************
			 * 
			 * Data added to ObservableList *
			 * 
			 ********************************/
			while (rs.next()) {
				// Iterate Row
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					// Iterate Column
					row.add(rs.getString(i));
				}
				System.out.println("Row [1] added " + row);
				data.add(row);
			}
			// FINALLY ADDED TO TableView
			tableview.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}
}
