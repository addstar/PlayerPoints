package org.black_ixx.playerPoints.storage.imports;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import lib.PatPeter.SQLibrary.SQLite;

import org.black_ixx.playerPoints.PlayerPoints;
import org.black_ixx.playerPoints.storage.IStorage;
import org.black_ixx.playerPoints.storage.StorageType;

public class SQLiteImport extends DatabaseImport {

   /**
    * SQLite reference.
    */
   private SQLite sqlite;

   public SQLiteImport(PlayerPoints plugin) {
      super(plugin);
      sqlite = new SQLite(plugin.getLogger(), " ", "storage", plugin.getDataFolder().getAbsolutePath());
      sqlite.open();
   }

   @Override
   void doImport() {
      plugin.getLogger().info("Importing SQLite to MySQL");
      IStorage mysql = generator.createStorageHandlerForType(StorageType.MYSQL);
      ResultSet query = null;
      try {
         sqlite = new SQLite(plugin.getLogger(), " ", "storage", plugin.getDataFolder().getAbsolutePath());
         query = sqlite.query("SELECT * FROM playerpoints");
         if(query.next()) {
            do {
               mysql.setPoints(query.getString("playername"), query.getInt("points"));
            } while(query.next());
         }
         query.close();
      } catch(SQLException e) {
         plugin.getLogger().log(Level.SEVERE, "SQLException on SQLite import", e);
      } finally {
      }
   }

}
